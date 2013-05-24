package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Appetite for Brains")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class AppetiteforBrains extends Card
{
	public AppetiteforBrains(GameState state)
	{
		super(state);

		// Target opponent reveals his or her hand. You choose a card from it
		// with converted mana cost 4 or greater and exile that card.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

		EventFactory reveal = reveal(HandOf.instance(target), "Target opponent reveals his or her hand.");
		this.addEffect(reveal);

		EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "You choose a card from it with converted mana cost 4 or greater and exile that card.");
		exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
		exile.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(EffectResult.instance(reveal), HasConvertedManaCost.instance(Between.instance(4, null))));
		exile.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(exile);
	}
}
