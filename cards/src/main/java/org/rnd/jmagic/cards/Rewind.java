package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rewind")
@Types({Type.INSTANT})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class Rewind extends Card
{
	public Rewind(GameState state)
	{
		super(state);

		Target target = this.addTarget(Spells.instance(), "target spell");

		this.addEffect(counter(targetedBy(target), "Counter target spell."));

		EventFactory factory = new EventFactory(EventType.UNTAP_CHOICE, "Untap up to four lands.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		factory.parameters.put(EventType.Parameter.CHOICE, LandPermanents.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 4));
		this.addEffect(factory);
	}
}
