package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mardu Heart-Piercer")
@Types({Type.CREATURE})
@SubTypes({SubType.ARCHER, SubType.HUMAN})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class MarduHeartPiercer extends Card
{
	public static final class MarduHeartPiercerAbility0 extends EventTriggeredAbility
	{
		public MarduHeartPiercerAbility0(GameState state)
		{
			super(state, "When Mardu Heart-Piercer enters the battlefield, if you attacked with a creature this turn, Mardu Heart-Piercer deals 2 damage to target creature or player.");
			this.addPattern(whenThisEntersTheBattlefield());

			state.ensureTracker(new org.rnd.jmagic.engine.trackers.AttackTracker());
			this.interveningIf = Raid.instance();

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(2, target, "Mardu Heart-Piercer deals 2 damage to target creature or player."));
		}
	}

	public MarduHeartPiercer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Raid \u2014 When Mardu Heart-Piercer enters the battlefield, if you
		// attacked with a creature this turn, Mardu Heart-Piercer deals 2
		// damage to target creature or player.
		this.addAbility(new MarduHeartPiercerAbility0(state));
	}
}
