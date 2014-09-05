package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Hallowed Burial")
@Types({Type.SORCERY})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Eventide.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class HallowedBurial extends Card
{
	public HallowedBurial(GameState state)
	{
		super(state);

		EventFactory factory = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put all creatures on the bottom of their owners' libraries.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.INDEX, org.rnd.jmagic.Convenience.numberGenerator(-1));
		factory.parameters.put(EventType.Parameter.OBJECT, CreaturePermanents.instance());
		this.addEffect(factory);
	}
}
