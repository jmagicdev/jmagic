package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Esper Charm")
@Types({Type.INSTANT})
@ManaCost("WUB")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK})
public final class EsperCharm extends Card
{
	public EsperCharm(GameState state)
	{
		super(state);

		// Destroy target enchantment;
		{
			Target target = this.addTarget(1, EnchantmentPermanents.instance(), "target enchantment");
			this.addEffect(1, destroy(targetedBy(target), "Destroy target enchantment."));
		}

		// or draw two cards;
		{
			this.addEffect(2, drawCards(You.instance(), 2, "Draw two cards."));
		}

		// or target player discards two cards.
		{
			Target target = this.addTarget(3, Players.instance(), "target player");
			this.addEffect(3, discardCards(targetedBy(target), 2, "Target player discards two cards."));
		}
	}
}
