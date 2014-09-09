package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grotag Siege-Runner")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.GOBLIN})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class GrotagSiegeRunner extends Card
{
	public static final class GrotagSiegeRunnerAbility0 extends ActivatedAbility
	{
		public GrotagSiegeRunnerAbility0(GameState state)
		{
			super(state, "(R), Sacrifice Grotag Siege-Runner: Destroy target creature with defender. Grotag Siege-Runner deals 2 damage to that creature's controller.");

			this.setManaCost(new ManaPool("(R)"));
			this.addCost(sacrificeThis("Grotag Siege-Runner"));

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Defender.class)), "target creature with defender"));
			this.addEffect(destroy(target, "Destroy target creature with defender."));
			this.addEffect(permanentDealDamage(2, ControllerOf.instance(target), "Grotag Siege-Runner deals 2 damage to that creature's controller."));
		}
	}

	public GrotagSiegeRunner(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (R), Sacrifice Grotag Siege-Runner: Destroy target creature with
		// defender. Grotag Siege-Runner deals 2 damage to that creature's
		// controller.
		this.addAbility(new GrotagSiegeRunnerAbility0(state));
	}
}
