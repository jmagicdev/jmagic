package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fated Retribution")
@Types({Type.INSTANT})
@ManaCost("4WWW")
@ColorIdentity({Color.WHITE})
public final class FatedRetribution extends Card
{
	public FatedRetribution(GameState state)
	{
		super(state);

		// Destroy all creatures and planeswalkers.
		this.addEffect(destroy(Intersect.instance(Permanents.instance(), HasType.instance(Type.CREATURE, Type.PLANESWALKER)), "Destroy all creatures and planeswalkers."));

		// If it's your turn, scry 2.
		SetGenerator itsYourTurn = Intersect.instance(OwnerOf.instance(CurrentTurn.instance()), You.instance());
		this.addEffect(ifThen(itsYourTurn, scry(2, "Scry 2."), "If it's your turn, scry 2."));
	}
}
