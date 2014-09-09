package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Affa Guard Hound")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class AffaGuardHound extends Card
{
	public static final class AffaShield extends EventTriggeredAbility
	{
		public AffaShield(GameState state)
		{
			super(state, "When Affa Guard Hound enters the battlefield, target creature gets +0/+3 until end of turn.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), +0, +3, "Target creature gets +0/+3 until end of turn."));
		}
	}

	public AffaGuardHound(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// When Affa Guard Hound enters the battlefield, target creature gets
		// +0/+3 until end of turn.
		this.addAbility(new AffaShield(state));
	}
}
