package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chronostutter")
@Types({Type.INSTANT})
@ManaCost("5U")
@ColorIdentity({Color.BLUE})
public final class Chronostutter extends Card
{
	public Chronostutter(GameState state)
	{
		super(state);

		// Put target creature into its owner's library second from the top.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		EventFactory f = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target creature into its owner's library second from the top.");
		f.parameters.put(EventType.Parameter.CAUSE, This.instance());
		f.parameters.put(EventType.Parameter.INDEX, numberGenerator(2));
		f.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(f);
	}
}
