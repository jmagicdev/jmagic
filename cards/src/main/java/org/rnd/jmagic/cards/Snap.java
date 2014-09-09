package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Snap")
@Types({Type.INSTANT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Snap extends Card
{
	public Snap(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(bounce(targetedBy(target), "Return target creature to its owner's hand."));

		EventFactory untap = new EventFactory(EventType.UNTAP_CHOICE, "Untap up to two lands.");
		untap.parameters.put(EventType.Parameter.CAUSE, This.instance());
		untap.parameters.put(EventType.Parameter.PLAYER, You.instance());
		untap.parameters.put(EventType.Parameter.NUMBER, Between.instance(0, 2));
		untap.parameters.put(EventType.Parameter.CHOICE, LandPermanents.instance());
		this.addEffect(untap);
	}
}
