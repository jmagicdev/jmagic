package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Terror")
@Types({Type.INSTANT})
@ManaCost("1B")
@ColorIdentity({Color.BLACK})
public final class Terror extends Card
{
	public Terror(GameState state)
	{
		super(state);

		SetGenerator nonartifactCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasType.instance(Type.ARTIFACT));
		SetGenerator nonartifactNonblackCreatures = RelativeComplement.instance(nonartifactCreatures, HasColor.instance(Color.BLACK));
		Target target = this.addTarget(nonartifactNonblackCreatures, "target nonartifact, nonblack creature");
		this.addEffects(bury(this, targetedBy(target), "Destroy target nonartifact, nonblack creature. It can't be regenerated."));
	}
}
