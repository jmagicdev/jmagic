package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Crush")
@Types({Type.INSTANT})
@ManaCost("R")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class Crush extends Card
{
	public Crush(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target noncreature artifact"));

		// Destroy target noncreature artifact.
		this.addEffect(destroy(target, "Destroy target noncreature artifact."));
	}
}
