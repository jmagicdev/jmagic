package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Shattering Blow")
@Types({Type.INSTANT})
@ManaCost("1(R/W)")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class ShatteringBlow extends Card
{
	public ShatteringBlow(GameState state)
	{
		super(state);

		// Exile target artifact.
		SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
		this.addEffect(exile(target, "Exile target artifact."));
	}
}
