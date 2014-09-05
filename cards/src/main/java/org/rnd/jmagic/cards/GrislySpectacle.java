package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Grisly Spectacle")
@Types({Type.INSTANT})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GrislySpectacle extends Card
{
	public GrislySpectacle(GameState state)
	{
		super(state);

		// Destroy target nonartifact creature. Its controller puts a number of
		// cards equal to that creature's power from the top of his or her
		// library into his or her graveyard.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), HasType.instance(Type.ARTIFACT)), "target nonartifact creature"));

		this.addEffect(destroy(target, "Destroy target nonartifact creature."));
		this.addEffect(millCards(ControllerOf.instance(target), PowerOf.instance(target), "Its controller puts a number of cards equal to that creature's power from the top of his or her library into his or her graveyard."));
	}
}
