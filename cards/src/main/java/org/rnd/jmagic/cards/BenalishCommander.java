package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Benalish Commander")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class BenalishCommander extends Card
{
	public static final class BenalishCommanderAbility0 extends CharacteristicDefiningAbility
	{
		public BenalishCommanderAbility0(GameState state)
		{
			super(state, "Benalish Commander's power and toughness are each equal to the number of Soldiers you control.", Characteristics.Characteristic.POWER, Characteristics.Characteristic.TOUGHNESS);

			SetGenerator pt = Count.instance(Intersect.instance(HasSubType.instance(SubType.SOLDIER), ControlledBy.instance(You.instance())));
			this.addEffectPart(setPowerAndToughness(This.instance(), pt, pt));
		}
	}

	public static final class BenalishCommanderAbility2 extends EventTriggeredAbility
	{
		public BenalishCommanderAbility2(GameState state)
		{
			super(state, "Whenever a time counter is removed from Benalish Commander while it's exiled, put a 1/1 white Soldier creature token onto the battlefield.");
			this.addPattern(whenTimeCounterIsRemovedFromThis());
			this.canTrigger = Intersect.instance(ABILITY_SOURCE_OF_THIS, InZone.instance(ExileZone.instance()));

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Soldier creature token onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.SOLDIER);
			this.addEffect(token.getEventFactory());
		}
	}

	public BenalishCommander(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Benalish Commander's power and toughness are each equal to the number
		// of Soldiers you control.
		this.addAbility(new BenalishCommanderAbility0(state));

		// Suspend X\u2014(X)(W)(W). X can't be 0. (Rather than cast this card
		// from your hand, you may pay (X)(W)(W) and exile it with X time
		// counters on it. At the beginning of your upkeep, remove a time
		// counter. When the last is removed, cast it without paying its mana
		// cost. It has haste.)
		this.addAbility(org.rnd.jmagic.abilities.keywords.Suspend.X(state, "(X)(W)(W)"));

		// Whenever a time counter is removed from Benalish Commander while it's
		// exiled, put a 1/1 white Soldier creature token onto the battlefield.
		this.addAbility(new BenalishCommanderAbility2(state));
	}
}
