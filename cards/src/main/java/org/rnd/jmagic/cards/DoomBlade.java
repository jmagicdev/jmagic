package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Doom Blade")
@Types({Type.INSTANT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class DoomBlade extends Card
{
	public DoomBlade(GameState state)
	{
		super(state);

		// Destroy target nonblack creature.
		SetGenerator nonblackCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK));
		Target target = this.addTarget(nonblackCreatures, "target nonblack creature");
		this.addEffect(destroy(targetedBy(target), "Destroy target nonblack creature."));
	}
}
