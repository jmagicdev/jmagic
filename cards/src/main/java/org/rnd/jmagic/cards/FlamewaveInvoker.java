package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flamewave Invoker")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.MUTANT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FlamewaveInvoker extends Card
{
	public static final class Wave extends ActivatedAbility
	{
		public Wave(GameState state)
		{
			super(state, "(7)(R): Flamewave Invoker deals 5 damage to target player.");

			this.setManaCost(new ManaPool("7R"));

			Target target = this.addTarget(Players.instance(), "target player");
			this.addEffect(permanentDealDamage(5, targetedBy(target), "Flamewave Invoker deals 5 damage to target player."));
		}
	}

	public FlamewaveInvoker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Wave(state));
	}
}
