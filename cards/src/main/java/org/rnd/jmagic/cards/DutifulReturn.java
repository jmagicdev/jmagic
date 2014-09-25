package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dutiful Return")
@Types({Type.SORCERY})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class DutifulReturn extends Card
{
	public DutifulReturn(GameState state)
	{
		super(state);

		// Return up to two target creature cards from your graveyard to your
		// hand.
		SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance())));
		SetGenerator target = targetedBy(this.addTarget(deadThings, "up to two target creature cards from your graveyard").setNumber(0, 2));
		this.addEffect(putIntoHand(target, You.instance(), "Return up to two target creature cards from your graveyard to your hand."));
	}
}
