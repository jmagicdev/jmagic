package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Deluge")
@Types({Type.INSTANT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class Deluge extends Card
{
	public Deluge(GameState state)
	{
		super(state);

		SetGenerator affectedCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
		this.addEffect(tap(affectedCreatures, "Tap all creatures without flying."));
	}
}
