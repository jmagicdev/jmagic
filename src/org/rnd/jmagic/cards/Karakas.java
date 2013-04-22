package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Karakas")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.LEGENDS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Karakas extends Card
{
	public static final class KarakasAbility1 extends ActivatedAbility
	{
		public KarakasAbility1(GameState state)
		{
			super(state, "(T): Return target legendary creature to its owner's hand.");
			this.costsTap = true;

			SetGenerator legends = Intersect.instance(HasSuperType.instance(SuperType.LEGENDARY), CreaturePermanents.instance());
			SetGenerator target = targetedBy(this.addTarget(legends, "target legendary creature"));
			this.addEffect(bounce(target, "Return target legendary creature to its owner's hand."));
		}
	}

	public Karakas(GameState state)
	{
		super(state);

		// (T): Add (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));

		// (T): Return target legendary creature to its owner's hand.
		this.addAbility(new KarakasAbility1(state));
	}
}
