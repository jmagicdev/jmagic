package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shape Anew")
@Types({Type.SORCERY})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ShapeAnew extends Card
{
	public ShapeAnew(GameState state)
	{
		super(state);

		// The controller of target artifact sacrifices it, then reveals cards
		// from the top of his or her library until he or she reveals an
		// artifact card. That player puts that card onto the battlefield, then
		// shuffles all other cards revealed this way into his or her library.

		// The controller of target artifact sacrifices it.
		SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
		SetGenerator controller = ControllerOf.instance(target);
		this.addEffects(sacrifice(controller, 1, target, "The controller of target artifact sacrifices it."));

		// Its controller reveals cards from the top of his or her library until
		// he or she reveals an artifact card.
		SetGenerator library = LibraryOf.instance(controller);
		SetGenerator cardsToReveal = TopMost.instance(library, numberGenerator(1), HasType.instance(Type.ARTIFACT));

		EventType.ParameterMap revealParameters = new EventType.ParameterMap();
		revealParameters.put(EventType.Parameter.CAUSE, This.instance());
		revealParameters.put(EventType.Parameter.OBJECT, cardsToReveal);
		this.addEffect(new EventFactory(EventType.REVEAL, revealParameters, "Its controller reveals cards from the top of his or her library until he or she reveals an artifact card."));

		// The player puts that card onto the battlefield,
		SetGenerator firstArtifact = Intersect.instance(cardsToReveal, HasType.instance(Type.ARTIFACT));
		EventType.ParameterMap ontoFieldParameters = new EventType.ParameterMap();
		ontoFieldParameters.put(EventType.Parameter.CAUSE, This.instance());
		ontoFieldParameters.put(EventType.Parameter.CONTROLLER, controller);
		ontoFieldParameters.put(EventType.Parameter.OBJECT, firstArtifact);
		this.addEffect(new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, ontoFieldParameters, "The player puts that card onto the battlefield."));

		// then shuffles all other cards revealed this way into his or her
		// library.
		// Since the "other cards" never left the library... this is just a
		// standard shuffle, not a shuffle-into.
		this.addEffect(shuffleLibrary(controller, "then shuffles all other cards revealed this way into his or her library."));
	}
}
