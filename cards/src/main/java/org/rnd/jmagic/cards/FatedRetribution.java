package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fated Retribution")
@Types({Type.INSTANT})
@ManaCost("4WWW")
@Printings({@Printings.Printed(ex = Expansion.BORN_OF_THE_GODS, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class FatedRetribution extends Card
{
	public FatedRetribution(GameState state)
	{
		super(state);


		// Destroy all creatures and planeswalkers.
		this.addEffect(destroy(Intersect.instance(Permanents.instance(), HasType.instance(Type.CREATURE, Type.PLANESWALKER)), "Destroy all creatures and planeswalkers."));

		// If it's your turn, scry 2.
		SetGenerator itsYourTurn = Intersect.instance(You.instance(), OwnerOf.instance(CurrentTurn.instance()));

		EventFactory scryIfItsYourTurn = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If it's your turn, scry 2.");
		scryIfItsYourTurn.parameters.put(EventType.Parameter.IF, itsYourTurn);
		scryIfItsYourTurn.parameters.put(EventType.Parameter.THEN, Identity.instance(scry(2, "Scry 2.")));
		this.addEffect(scryIfItsYourTurn);
	}
}
