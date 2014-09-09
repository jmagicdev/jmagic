package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Frantic Salvage")
@Types({Type.INSTANT})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class FranticSalvage extends Card
{
	public FranticSalvage(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(GraveyardOf.instance(You.instance()))), "any number of target artifact cards in your graveyard");
		target.setNumber(0, null);

		// Put any number of target artifact cards from your graveyard on top of
		// your library.
		EventFactory factory = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put any number of target artifact cards from your graveyard on top of your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(factory);

		// Draw a card.
		this.addEffect(drawACard());
	}
}
