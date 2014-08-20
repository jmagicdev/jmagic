package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Khalni Gem")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class KhalniGem extends Card
{
	public static final class LandfallShenanigans extends EventTriggeredAbility
	{
		public LandfallShenanigans(GameState state)
		{
			super(state, "When Khalni Gem enters the battlefield, return two lands you control to their owner's hand.");

			this.addPattern(whenThisEntersTheBattlefield());

			EventFactory factory = new EventFactory(EventType.PUT_INTO_HAND_CHOICE, "Return two lands you control to their owner's hand");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			factory.parameters.put(EventType.Parameter.CHOICE, Intersect.instance(HasType.instance(Type.LAND), ControlledBy.instance(You.instance())));
			this.addEffect(factory);
		}
	}

	public static final class KhalniMana extends ActivatedAbility
	{
		public KhalniMana(GameState state)
		{
			super(state, "(T): Add two mana of any one color to your mana pool.");

			this.costsTap = true;

			EventType.ParameterMap manaParameters = new EventType.ParameterMap();
			manaParameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			manaParameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("(WUBRG)")));
			manaParameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			manaParameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(new EventFactory(EventType.ADD_MANA, manaParameters, "Add two mana of any one color to your mana pool."));
		}
	}

	public KhalniGem(GameState state)
	{
		super(state);

		this.addAbility(new LandfallShenanigans(state));

		this.addAbility(new KhalniMana(state));
	}
}
