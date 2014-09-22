package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Putrefy")
@Types({Type.INSTANT})
@ManaCost("1BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class Putrefy extends Card
{
	public Putrefy(GameState state)
	{
		super(state);

		// Destroy target artifact or creature. It can't be regenerated.
		Target t = this.addTarget(Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target artifact or creature");
		this.addEffects(bury(this, targetedBy(t), "Destroy target artifact or creature. It can't be regenerated."));
	}
}
