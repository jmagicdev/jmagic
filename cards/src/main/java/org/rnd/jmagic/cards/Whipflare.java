package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Whipflare")
@Types({Type.SORCERY})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class Whipflare extends Card
{
	public Whipflare(GameState state)
	{
		super(state);

		// Whipflare deals 2 damage to each nonartifact creature.
		SetGenerator nonArtifactCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasType.instance(Type.ARTIFACT));
		this.addEffect(spellDealDamage(2, nonArtifactCreatures, "Whipflare deals 2 damage to each nonartifact creature."));
	}
}
