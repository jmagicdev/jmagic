package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reclaim")
@Types({Type.INSTANT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class Reclaim extends Card
{
	public Reclaim(GameState state)
	{
		super(state);

		// Put target card from your graveyard on top of your library.
		SetGenerator yard = GraveyardOf.instance(You.instance());
		SetGenerator target = targetedBy(this.addTarget(InZone.instance(yard), "target card from your graveyard"));
		this.addEffect(putOnTopOfLibrary(target, "Put target card from your graveyard on top of your library."));
	}
}
