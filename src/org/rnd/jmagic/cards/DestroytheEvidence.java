package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Destroy the Evidence")
@Types({Type.SORCERY})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DestroytheEvidence extends Card
{
	public DestroytheEvidence(GameState state)
	{
		super(state);

		// Destroy target land. Its controller reveals cards from the top of his
		// or her library until he or she reveals a land card, then puts those
		// cards into his or her graveyard.
		SetGenerator target = targetedBy(this.addTarget(LandPermanents.instance(), "target land"));
		this.addEffect(destroy(target, "Destroy target land."));

		SetGenerator opponentsLibrary = LibraryOf.instance(ControllerOf.instance(target));
		SetGenerator cardsToReveal = TopMost.instance(opponentsLibrary, numberGenerator(1), HasType.instance(Type.LAND));

		EventFactory revealFactory = new EventFactory(EventType.REVEAL, "Its controller reveals cards from the top of his or her library until he or she reveals a land card,");
		revealFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		revealFactory.parameters.put(EventType.Parameter.OBJECT, cardsToReveal);
		this.addEffect(revealFactory);

		EventFactory millFactory = new EventFactory(EventType.PUT_INTO_GRAVEYARD, "then puts those cards into his or her graveyard.");
		millFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		millFactory.parameters.put(EventType.Parameter.OBJECT, cardsToReveal);
		this.addEffect(millFactory);
	}
}
