package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Repeal")
@Types({Type.INSTANT})
@ManaCost("XU")
@Printings({@Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Repeal extends Card
{
	public Repeal(GameState state)
	{
		super(state);

		// Return target nonland permanent with converted mana cost X to its
		// owner's hand.
		SetGenerator nonlandPermanents = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
		SetGenerator legalTargets = Intersect.instance(nonlandPermanents, HasConvertedManaCost.instance(ValueOfX.instance(This.instance())));
		Target target = this.addTarget(legalTargets, "target nonland permanent");

		this.addEffect(bounce(targetedBy(target), "Return target nonland permanent with converted mana cost X to its owner's hand."));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
