package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Creeping Corrosion")
@Types({Type.SORCERY})
@ManaCost("2GG")
@ColorIdentity({Color.GREEN})
public final class CreepingCorrosion extends Card
{
	public CreepingCorrosion(GameState state)
	{
		super(state);

		// Destroy all artifacts.
		this.addEffect(destroy(ArtifactPermanents.instance(), "Destroy all artifacts."));
	}
}
