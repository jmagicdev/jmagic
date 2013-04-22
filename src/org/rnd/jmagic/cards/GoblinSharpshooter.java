package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Sharpshooter")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class GoblinSharpshooter extends Card
{
	public static final class GoblinSharpshooterAbility1 extends EventTriggeredAbility
	{
		public GoblinSharpshooterAbility1(GameState state)
		{
			super(state, "Whenever a creature dies, untap Goblin Sharpshooter.");

			this.addPattern(whenXDies(CreaturePermanents.instance()));

			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap Goblin Sharpshooter."));
		}
	}

	public static final class GoblinSharpshooterAbility2 extends ActivatedAbility
	{
		public GoblinSharpshooterAbility2(GameState state)
		{
			super(state, "(T): Goblin Sharpshooter deals 1 damage to target creature or player.");
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));

			this.addEffect(permanentDealDamage(1, target, "Goblin Sharpshooter deals 1 damage to target creature or player."));
		}
	}

	public GoblinSharpshooter(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Goblin Sharpshooter doesn't untap during your untap step.
		this.addAbility(new org.rnd.jmagic.abilities.DoesntUntapDuringYourUntapStep(state, this.getName()));

		// Whenever a creature dies, untap Goblin Sharpshooter.
		this.addAbility(new GoblinSharpshooterAbility1(state));

		// (T): Goblin Sharpshooter deals 1 damage to target creature or player.
		this.addAbility(new GoblinSharpshooterAbility2(state));
	}
}
