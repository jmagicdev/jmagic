package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gitaxian Probe")
@Types({Type.SORCERY})
@ManaCost("(U/P)")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class GitaxianProbe extends Card
{
	public GitaxianProbe(GameState state)
	{
		super(state);

		// ((u/p) can be paid with either (U) or 2 life.)

		// Look at target player's hand.
		Target target = this.addTarget(Players.instance(), "target player");

		EventFactory look = new EventFactory(EventType.LOOK, "Look at target player's hand.");
		look.parameters.put(EventType.Parameter.CAUSE, This.instance());
		look.parameters.put(EventType.Parameter.OBJECT, InZone.instance(HandOf.instance(targetedBy(target))));
		look.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(look);

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
