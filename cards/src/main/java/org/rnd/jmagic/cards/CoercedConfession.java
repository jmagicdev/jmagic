package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Coerced Confession")
@Types({Type.SORCERY})
@ManaCost("4(U/B)")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class CoercedConfession extends Card
{
	public CoercedConfession(GameState state)
	{
		super(state);

		// Target player puts the top four cards of his or her library into his
		// or her graveyard. You draw a card for each creature card put into
		// that graveyard this way.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		EventFactory mill = millCards(target, 4, "Target player puts the top four cards of his or her library into his or her graveyard.");
		this.addEffect(mill);

		SetGenerator cardsMilled = NewObjectOf.instance(EffectResult.instance(mill));
		this.addEffect(drawCards(You.instance(), Count.instance(Intersect.instance(cardsMilled, HasType.instance(Type.CREATURE))), "You draw a card for each creature card put into that graveyard this way."));
	}
}
