package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fissure Vent")
@Types({Type.SORCERY})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FissureVent extends Card
{
	public FissureVent(GameState state)
	{
		super(state);

		// Choose one or both \u2014
		this.setNumModes(new Set(new org.rnd.util.NumberRange(1, 2)));

		// Destroy target artifact;
		{
			Target target = this.addTarget(1, ArtifactPermanents.instance(), "target artifact");
			this.addEffect(1, destroy(targetedBy(target), "Destroy target artifact."));
		}

		// destroy target nonbasic land.
		{
			Target target = this.addTarget(2, RelativeComplement.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC)), "target nonbasic land");
			this.addEffect(2, destroy(targetedBy(target), "Destroy target nonbasic land."));
		}
	}
}
