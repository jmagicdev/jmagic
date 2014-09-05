package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Foundry Champion")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.ELEMENTAL})
@ManaCost("4RW")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class FoundryChampion extends Card
{
	public static final class FoundryChampionAbility0 extends EventTriggeredAbility
	{
		public FoundryChampionAbility0(GameState state)
		{
			super(state, "When Foundry Champion enters the battlefield, it deals damage to target creature or player equal to the number of creatures you control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(Count.instance(CREATURES_YOU_CONTROL), target, "It deals damage to target creature or player equal to the number of creatures you control."));
		}
	}

	public static final class FoundryChampionAbility1 extends ActivatedAbility
	{
		public FoundryChampionAbility1(GameState state)
		{
			super(state, "(R): Foundry Champion gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +0, "Foundry Champion gets +1/+0 until end of turn."));
		}
	}

	public static final class FoundryChampionAbility2 extends ActivatedAbility
	{
		public FoundryChampionAbility2(GameState state)
		{
			super(state, "(W): Foundry Champion gets +0/+1 until end of turn.");
			this.setManaCost(new ManaPool("(W)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +0, +1, "Foundry Champion gets +0/+1 until end of turn."));
		}
	}

	public FoundryChampion(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// When Foundry Champion enters the battlefield, it deals damage to
		// target creature or player equal to the number of creatures you
		// control.
		this.addAbility(new FoundryChampionAbility0(state));

		// (R): Foundry Champion gets +1/+0 until end of turn.
		this.addAbility(new FoundryChampionAbility1(state));

		// (W): Foundry Champion gets +0/+1 until end of turn.
		this.addAbility(new FoundryChampionAbility2(state));
	}
}
