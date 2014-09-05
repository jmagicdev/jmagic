package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Jace's Archivist")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.VEDALKEN})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class JacesArchivist extends Card
{
	public static final class MaxCountInValueFromEachPlayerResult extends SetGenerator
	{
		private SetGenerator map;

		private MaxCountInValueFromEachPlayerResult(SetGenerator map)
		{
			this.map = map;
		}

		public static SetGenerator instance(SetGenerator map)
		{
			return new MaxCountInValueFromEachPlayerResult(map);
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			int max = 0;
			@SuppressWarnings("unchecked") java.util.Map<?, Identity> map = this.map.evaluate(state, thisObject).getOne(java.util.Map.class);
			for(Identity value: map.values())
			{
				Set mapValue = value.evaluate(state, thisObject);
				max = Math.max(max, mapValue.size());
			}
			return new Set(max);
		}
	}

	public static final class JacesArchivistAbility0 extends ActivatedAbility
	{
		public JacesArchivistAbility0(GameState state)
		{
			super(state, "(U), (T): Each player discards his or her hand, then draws cards equal to the greatest number of cards a player discarded this way.");
			this.setManaCost(new ManaPool("(U)"));
			this.costsTap = true;

			DynamicEvaluation eachPlayer = DynamicEvaluation.instance();

			EventFactory eachPlayerDiscards = discardHand(eachPlayer, "That player discards his or her hand");

			EventFactory allDiscard = new EventFactory(FOR_EACH_PLAYER, "Each player discards his or her hand,");
			allDiscard.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
			allDiscard.parameters.put(EventType.Parameter.EFFECT, Identity.instance(eachPlayerDiscards));
			this.addEffect(allDiscard);

			SetGenerator number = MaxCountInValueFromEachPlayerResult.instance(EffectResult.instance(allDiscard));
			this.addEffect(drawCards(Players.instance(), number, "then draws cards equal to the greatest number of cards a player discarded this way."));
		}
	}

	public JacesArchivist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (U), (T): Each player discards his or her hand, then draws cards
		// equal to the greatest number of cards a player discarded this way.
		this.addAbility(new JacesArchivistAbility0(state));
	}
}
