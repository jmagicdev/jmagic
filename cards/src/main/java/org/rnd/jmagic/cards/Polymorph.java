package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Polymorph")
@Types({Type.SORCERY})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Mirage.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Polymorph extends Card
{
	public Polymorph(GameState state)
	{
		super(state);

		// Destroy target creature. It can't be regenerated.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffects(bury(this, targetedBy(target), "Destroy target creature. It can't be regenerated."));

		// Its controller reveals cards from the top of his or her library until
		// he or she reveals a creature card.
		SetGenerator controller = ControllerOf.instance(targetedBy(target));
		SetGenerator library = LibraryOf.instance(controller);
		SetGenerator cardsToReveal = TopMost.instance(library, numberGenerator(1), HasType.instance(Type.CREATURE));

		EventType.ParameterMap revealParameters = new EventType.ParameterMap();
		revealParameters.put(EventType.Parameter.CAUSE, This.instance());
		revealParameters.put(EventType.Parameter.OBJECT, cardsToReveal);
		this.addEffect(new EventFactory(EventType.REVEAL, revealParameters, "Its controller reveals cards from the top of his or her library until he or she reveals a creature card."));

		// The player puts that card onto the battlefield,
		SetGenerator firstCreature = Intersect.instance(cardsToReveal, HasType.instance(Type.CREATURE));
		EventType.ParameterMap ontoFieldParameters = new EventType.ParameterMap();
		ontoFieldParameters.put(EventType.Parameter.CAUSE, This.instance());
		ontoFieldParameters.put(EventType.Parameter.CONTROLLER, controller);
		ontoFieldParameters.put(EventType.Parameter.OBJECT, firstCreature);
		this.addEffect(new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, ontoFieldParameters, "The player puts that card onto the battlefield,"));

		// then shuffles all other cards revealed this way into his or her
		// library.
		// Since the "other cards" never left the library... this is just a
		// standard shuffle, not a shuffle-into.
		this.addEffect(shuffleLibrary(controller, "then shuffles all other cards revealed this way into his or her library."));
	}
}
