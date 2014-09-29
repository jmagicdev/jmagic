package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sultai Charm")
@Types({Type.INSTANT})
@ManaCost("BGU")
@ColorIdentity({Color.BLUE, Color.BLACK, Color.GREEN})
public final class SultaiCharm extends Card
{
	public SultaiCharm(GameState state)
	{
		super(state);

		// Choose one \u2014

		// • Destroy target monocolored creature.
		{
			SetGenerator monocoloredGuys = Intersect.instance(Monocolored.instance(), CreaturePermanents.instance());
			SetGenerator target = targetedBy(this.addTarget(1, monocoloredGuys, "target monocolored creature"));
			this.addEffect(1, destroy(target, "Destroy target monocolored creature."));
		}

		// • Destroy target artifact or enchantment.
		{
			SetGenerator legal = Intersect.instance(HasType.instance(Type.ARTIFACT, Type.ENCHANTMENT), Permanents.instance());
			SetGenerator target = targetedBy(this.addTarget(2, legal, "target artifact or enchantment"));
			this.addEffect(2, destroy(target, "Destroy target artifact or enchantment."));
		}

		// • Draw two cards, then discard a card.
		{
			this.addEffect(3, drawCards(You.instance(), 2, "Draw two cards,"));
			this.addEffect(3, discardCards(You.instance(), 1, "then discard a card."));
		}
	}
}
