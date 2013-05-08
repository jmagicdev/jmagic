package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Melt Terrain")
@Types({Type.SORCERY})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class MeltTerrain extends Card
{
	public MeltTerrain(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
		this.addEffect(destroy(target, "Destroy target land."));
		this.addEffect(spellDealDamage(2, ControllerOf.instance(target), "Melt Terrain deals 2 damage to that land's controller."));
	}
}
