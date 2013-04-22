package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Syphon Mind")
@Types({Type.SORCERY})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class SyphonMind extends Card
{
	public SyphonMind(GameState state)
	{
		super(state);

		// Each other player discards a card. You draw a card for each card
		// discarded this way.

		EventFactory discard = discardCards(RelativeComplement.instance(Players.instance(), You.instance()), 1, "Each other player discards a card.");
		this.addEffect(discard);

		SetGenerator discardedThisWay = NewObjectOf.instance(Discarded.instance(EffectResult.instance(discard)));
		this.addEffect(drawCards(You.instance(), Count.instance(discardedThisWay), "You draw a card for each card discarded this way."));
	}
}
