package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Dust Bowl")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = MercadianMasques.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class DustBowl extends Card
{
	public static final class DustBowlAbility1 extends ActivatedAbility
	{
		public DustBowlAbility1(GameState state)
		{
			super(state, "(3), (T), Sacrifice a land: Destroy target nonbasic land.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;
			this.addCost(sacrifice(You.instance(), 1, LandPermanents.instance(), "Sacrifice a land"));
			SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC)), "target nonbasic land"));
			this.addEffect(destroy(target, "Destroy target nonbasic land."));
		}
	}

	public DustBowl(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (3), (T), Sacrifice a land: Destroy target nonbasic land.
		this.addAbility(new DustBowlAbility1(state));
	}
}
