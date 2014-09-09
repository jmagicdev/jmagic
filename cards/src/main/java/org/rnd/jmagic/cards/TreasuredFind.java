package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Treasured Find")
@Types({Type.SORCERY})
@ManaCost("BG")
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class TreasuredFind extends Card
{
	public TreasuredFind(GameState state)
	{
		super(state);

		// Return target card from your graveyard to your hand. Exile Treasured
		// Find.
		SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
		SetGenerator inYard = InZone.instance(yourGraveyard);
		SetGenerator target = targetedBy(this.addTarget(inYard, "target card from your graveyard"));
		this.addEffect(putIntoHand(target, You.instance(), "Return target card from your graveyard to your hand."));

		this.addEffect(exile(This.instance(), "Exile Treasured Find."));
	}
}
