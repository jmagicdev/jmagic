package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Noxious Revival")
@Types({Type.INSTANT})
@ManaCost("(G/P)")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class NoxiousRevival extends Card
{
	public NoxiousRevival(GameState state)
	{
		super(state);

		// ((g/p) can be paid with either (G) or 2 life.)

		// Put target card from a graveyard on top of its owner's library.
		SetGenerator target = targetedBy(this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "target card in a graveyard"));

		EventFactory move = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put target card from a graveyard on top of its owner's library.");
		move.parameters.put(EventType.Parameter.CAUSE, This.instance());
		move.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		move.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(move);
	}
}
