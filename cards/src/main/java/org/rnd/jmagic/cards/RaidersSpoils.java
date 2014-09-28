package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Raiders' Spoils")
@Types({Type.ENCHANTMENT})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class RaidersSpoils extends Card
{
	public static final class RaidersSpoilsAbility0 extends StaticAbility
	{
		public RaidersSpoilsAbility0(GameState state)
		{
			super(state, "Creatures you control get +1/+0.");
			this.addEffectPart(modifyPowerAndToughness(CREATURES_YOU_CONTROL, +1, +0));
		}
	}

	public static final class RaidersSpoilsAbility1 extends EventTriggeredAbility
	{
		public RaidersSpoilsAbility1(GameState state)
		{
			super(state, "Whenever a Warrior you control deals combat damage to a player, you may pay 1 life. If you do, draw a card.");

			SetGenerator yourWarriors = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.WARRIOR));
			this.addPattern(whenDealsCombatDamageToAPlayer(yourWarriors));

			EventFactory payLife = youMay(payLife(You.instance(), 1, "Pay 1 life"));
			this.addEffect(ifThen(payLife, drawACard(), "You may pay 1 life. If you do, draw a card."));
		}
	}

	public RaidersSpoils(GameState state)
	{
		super(state);

		// Creatures you control get +1/+0.
		this.addAbility(new RaidersSpoilsAbility0(state));

		// Whenever a Warrior you control deals combat damage to a player, you
		// may pay 1 life. If you do, draw a card.
		this.addAbility(new RaidersSpoilsAbility1(state));
	}
}
