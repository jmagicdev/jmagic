package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pendelhaven")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.SPECIAL), @Printings.Printed(ex = Expansion.LEGENDS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Pendelhaven extends Card
{
	public static final class PendelhavenAbility1 extends ActivatedAbility
	{
		public PendelhavenAbility1(GameState state)
		{
			super(state, "(T): Target 1/1 creature gets +1/+2 until end of turn.");
			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasPower.instance(1), HasToughness.instance(1)), "target 1/1 creature");
			ptChangeUntilEndOfTurn(targetedBy(target), +1, +2, "Target 1/1 creature gets +1/+2 until end of turn.");
		}
	}

	public Pendelhaven(GameState state)
	{
		super(state);

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));

		// (T): Target 1/1 creature gets +1/+2 until end of turn.
		this.addAbility(new PendelhavenAbility1(state));
	}
}
