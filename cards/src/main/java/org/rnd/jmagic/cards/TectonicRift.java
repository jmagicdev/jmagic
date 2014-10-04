package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tectonic Rift")
@Types({Type.SORCERY})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class TectonicRift extends Card
{
	public TectonicRift(GameState state)
	{
		super(state);

		// Destroy target land.
		Target target = this.addTarget(LandPermanents.instance(), "target land");
		this.addEffect(destroy(targetedBy(target), "Destroy target land."));

		// Creatures without flying can't block this turn.
		SetGenerator withoutFlying = RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));

		this.addEffect(cantBlockThisTurn(withoutFlying, "Creatures without flying can't block this turn."));
	}
}
