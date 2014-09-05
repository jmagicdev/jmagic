package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Wasteland")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Tempest.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Wasteland extends Card
{
	public static final class Waste extends ActivatedAbility
	{
		public Waste(GameState state)
		{
			super(state, "(T), Sacrifice Wasteland: Destroy target nonbasic land.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Wasteland"));

			Target target = this.addTarget(RelativeComplement.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC)), "target nonbasic land");
			this.addEffect(destroy(targetedBy(target), "Destroy target nonbasic land."));
		}
	}

	public Wasteland(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (T), Sacrifice Wasteland: Destroy target nonbasic land.
		this.addAbility(new Waste(state));
	}
}
