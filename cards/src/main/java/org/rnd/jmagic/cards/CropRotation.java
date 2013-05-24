package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crop Rotation")
@Types({Type.INSTANT})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.URZAS_LEGACY, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class CropRotation extends Card
{
	public CropRotation(GameState state)
	{
		super(state);

		// As an additional cost to cast Crop Rotation, sacrifice a land.
		this.addCost(sacrifice(You.instance(), 1, LandPermanents.instance(), "sacrifice a land"));

		// Search your library for a land card and put that card onto the
		// battlefield. Then shuffle your library.
		EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a land card and put that card onto the battlefield. Then shuffle your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.LAND)));
		this.addEffect(factory);
	}
}
