package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Consign to Dream")
@Types({Type.INSTANT})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class ConsigntoDream extends Card
{
	public ConsigntoDream(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));

		EventFactory bounce = bounce(target, "Return target permanent to its owner's hand");

		EventFactory top = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target permanent on top of its owner's library");
		top.parameters.put(EventType.Parameter.CAUSE, This.instance());
		top.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		top.parameters.put(EventType.Parameter.OBJECT, target);

		// Return target permanent to its owner's hand. If that permanent is red
		// or green, put it on top of its owner's library instead.
		EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Return target permanent to its owner's hand. If that permanent is red or green, put it on top of its owner's library instead.");
		effect.parameters.put(EventType.Parameter.IF, Intersect.instance(HasColor.instance(Color.RED, Color.GREEN), target));
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(top));
		effect.parameters.put(EventType.Parameter.ELSE, Identity.instance(bounce));
		this.addEffect(effect);
	}
}
