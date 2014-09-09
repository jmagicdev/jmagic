package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Sparkmage Apprentice")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class SparkmageApprentice extends Card
{
	public static final class Spark extends EventTriggeredAbility
	{
		public Spark(GameState state)
		{
			super(state, "When Sparkmage Apprentice enters the battlefield, it deals 1 damage to target creature or player.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(1, targetedBy(target), "Sparkmage Apprentice deals 1 damage to target creature or player."));
		}
	}

	public SparkmageApprentice(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Spark(state));
	}
}
