package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grim Guardian")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.ZOMBIE})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class GrimGuardian extends Card
{
	public static final class GrimGuardianAbility0 extends EventTriggeredAbility
	{
		public GrimGuardianAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Grim Guardian or another enchantment enters the battlefield under your control, each opponent loses 1 life.");
			this.addPattern(constellation());
			this.addEffect(loseLife(OpponentsOf.instance(You.instance()), 1, "Each opponent loses 1 life."));
		}
	}

	public GrimGuardian(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Constellation \u2014 Whenever Grim Guardian or another enchantment
		// enters the battlefield under your control, each opponent loses 1
		// life.
		this.addAbility(new GrimGuardianAbility0(state));
	}
}
