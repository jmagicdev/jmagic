package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Shatterstorm")
@Types({Type.SORCERY})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class Shatterstorm extends Card
{
	public Shatterstorm(GameState state)
	{
		super(state);

		this.addEffects(bury(this, org.rnd.jmagic.engine.generators.ArtifactPermanents.instance(), "Destroy all artifacts. They can't be regenerated."));
	}
}
