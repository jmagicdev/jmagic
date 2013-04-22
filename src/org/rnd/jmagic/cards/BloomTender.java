package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bloom Tender")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.DRUID})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class BloomTender extends Card
{
	public static final class ManaOnion extends ActivatedAbility
	{
		/**
		 * @eparam SOURCE: the source of the mana
		 * @eparam PLAYER: the player choosing the color and getting the mana
		 * @eparam MANA: all colors (this is required since this event reports
		 * that it adds mana!)
		 * @eparam RESULT: the result of the ADD_MANA event
		 */
		public static final EventType BLOOM_TENDER_MANA = new EventType("BLOOM_TENDER_MANA")
		{
			@Override
			public boolean addsMana()
			{
				return true;
			}

			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				Set color = new Set();
				for(Color c: parameters.get(Parameter.MANA).getAll(Color.class))
					color.add(new ManaSymbol(c));

				java.util.Map<EventType.Parameter, Set> manaParameters = new java.util.HashMap<EventType.Parameter, Set>();
				manaParameters.put(EventType.Parameter.SOURCE, parameters.get(EventType.Parameter.SOURCE));
				manaParameters.put(EventType.Parameter.MANA, color);
				manaParameters.put(EventType.Parameter.PLAYER, parameters.get(EventType.Parameter.PLAYER));
				Event manaEvent = createEvent(game, "For each color among permanents you control, add one mana of that color to your mana pool", EventType.ADD_MANA, manaParameters);

				boolean ret = manaEvent.perform(event, true);

				event.setResult(manaEvent.getResult());

				return ret;
			}
		};

		public ManaOnion(GameState state)
		{
			super(state, "(T): For each color among permanents you control, add one mana of that color to your mana pool.");

			this.costsTap = true;

			EventFactory factory = new EventFactory(BLOOM_TENDER_MANA, "For each color among permanents you control, add one mana of that color to your mana pool.");
			factory.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			factory.parameters.put(EventType.Parameter.MANA, ColorsOf.instance(ControlledBy.instance(You.instance())));
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public BloomTender(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new ManaOnion(state));
	}
}
