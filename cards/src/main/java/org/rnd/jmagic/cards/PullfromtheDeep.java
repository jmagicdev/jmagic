package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pull from the Deep")
@Types({Type.SORCERY})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class PullfromtheDeep extends Card
{
	public PullfromtheDeep(GameState state)
	{
		super(state);

		// Return up to one target instant card and up to one target sorcery
		// card from your graveyard to your hand.
		SetGenerator deadInstants = Intersect.instance(HasType.instance(Type.INSTANT), InZone.instance(GraveyardOf.instance(You.instance())));
		SetGenerator instant = targetedBy(this.addTarget(deadInstants, "up to one target instant card from your graveyard").setNumber(0, 1));
		SetGenerator deadSorceries = Intersect.instance(HasType.instance(Type.INSTANT), InZone.instance(GraveyardOf.instance(You.instance())));
		SetGenerator sorcery = targetedBy(this.addTarget(deadSorceries, "up to one target sorcery card from your graveyard").setNumber(0, 1));
		this.addEffect(putIntoHand(Union.instance(instant, sorcery), You.instance(), "Return up to one target instant card and up to one target sorcery card from your graveyard to your hand."));

		// Exile Pull from the Deep.
		this.addEffect(exile(This.instance(), "Exile Pull from the Deep."));
	}
}
