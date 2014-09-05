package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Structural Collapse")
@Types({Type.SORCERY})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class StructuralCollapse extends Card
{
	public StructuralCollapse(GameState state)
	{
		super(state);

		// Target player sacrifices an artifact and a land. Structural Collapse
		// deals 2 damage to that player.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(sacrifice(target, 1, ArtifactPermanents.instance(), "Target player sacrifices an artifact"));
		this.addEffect(sacrifice(target, 1, LandPermanents.instance(), "and a land."));
		this.addEffect(spellDealDamage(2, target, "Structural Collapse deals 2 damage to that player."));
	}
}
