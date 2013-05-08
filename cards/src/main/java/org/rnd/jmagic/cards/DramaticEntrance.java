package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dramatic Entrance")
@Types({Type.INSTANT})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class DramaticEntrance extends Card
{
	public DramaticEntrance(GameState state)
	{
		super(state);

		EventFactory moveFactory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a green creature card from your hand onto the battlefield.");
		moveFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		moveFactory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		moveFactory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(InZone.instance(HandOf.instance(You.instance())), HasColor.instance(Color.GREEN)));

		this.addEffect(youMay(moveFactory, "You may put a green creature card from your hand onto the battlefield."));
	}
}
