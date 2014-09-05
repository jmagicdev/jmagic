package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Cackling Counterpart")
@Types({Type.INSTANT})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class CacklingCounterpart extends Card
{
	public CacklingCounterpart(GameState state)
	{
		super(state);

		// Put a token onto the battlefield that's a copy of target creature you
		// control.
		SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));

		EventFactory factory = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token onto the battlefield that's a copy of target creature you control.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(factory);

		// Flashback (5)(U)(U) (You may cast this card from your graveyard for
		// its flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(5)(U)(U)"));
	}
}
