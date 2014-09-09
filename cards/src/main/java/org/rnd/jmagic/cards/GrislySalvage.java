package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grisly Salvage")
@Types({Type.INSTANT})
@ManaCost("BG")
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GrislySalvage extends Card
{
	public GrislySalvage(GameState state)
	{
		super(state);

		EventFactory factory = new EventFactory(PUT_ONE_FROM_TOP_N_OF_LIBRARY_INTO_HAND, "Reveal the top five cards of your library. You may put a creature or land card from among them into your hand. Put the rest into your graveyard.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.CARD, TopCards.instance(5, LibraryOf.instance(You.instance())));
		factory.parameters.put(EventType.Parameter.TYPE, HasType.instance(Type.CREATURE, Type.LAND));
		factory.parameters.put(EventType.Parameter.TO, GraveyardOf.instance(You.instance()));
		factory.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		this.addEffect(factory);
	}
}
