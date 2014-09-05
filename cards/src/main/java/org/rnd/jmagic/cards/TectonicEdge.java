package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Tectonic Edge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class TectonicEdge extends Card
{
	public static final class Waste extends ActivatedAbility
	{
		public Waste(GameState state)
		{
			super(state, "(1), (T), Sacrifice Tectonic Edge: Destroy target nonbasic land. Activate this ability only if an opponent controls four or more lands.");
			this.setManaCost(new ManaPool("1"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Wasteland"));

			Target target = this.addTarget(RelativeComplement.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC)), "target nonbasic land");
			this.addEffect(destroy(targetedBy(target), "Destroy target nonbasic land."));

			SetGenerator opponentsLands = SplitOnController.instance(Intersect.instance(LandPermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))));
			SetGenerator mostLands = Count.instance(LargestSet.instance(opponentsLands));
			this.addActivateRestriction(Intersect.instance(Between.instance(0, 3), mostLands));
		}
	}

	public TectonicEdge(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (1), (T), Sacrifice Tectonic Edge: Destroy target nonbasic land.
		// Activate this ability only if an opponent controls four or more
		// lands.
		this.addAbility(new Waste(state));
	}
}
