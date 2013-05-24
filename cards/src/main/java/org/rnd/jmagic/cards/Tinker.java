package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tinker")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Tinker extends Card
{
	public Tinker(GameState state)
	{
		super(state);

		// As an additional cost to cast Tinker, sacrifice an artifact.
		EventFactory sacrifice = sacrifice(You.instance(), 1, ArtifactPermanents.instance(), "sacrifice an artifact");
		this.addCost(sacrifice);

		// Search your library for an artifact card and put that card onto the
		// battlefield. Then shuffle your library.
		EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for an artifact card and put that card onto the battlefield. Then shuffle your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.ARTIFACT)));
		this.addEffect(factory);
	}
}
