package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lay Bare")
@Types({Type.INSTANT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class LayBare extends Card
{
	public LayBare(GameState state)
	{
		super(state);

		// Counter target spell. Look at its controller's hand.
		SetGenerator target = targetedBy(this.addTarget(Spells.instance(), "target spell"));

		this.addEffect(counter(target, "Counter target spell."));

		EventFactory factory = new EventFactory(EventType.LOOK, "Look at its controller's hand.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, InZone.instance(HandOf.instance(ControllerOf.instance(target))));
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(factory);
	}
}
