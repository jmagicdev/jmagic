package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dawn to Dusk")
@Types({Type.SORCERY})
@ManaCost("2WW")
@ColorIdentity({Color.WHITE})
public final class DawntoDusk extends Card
{
	public DawntoDusk(GameState state)
	{
		super(state);

		// Choose one or both \u2014
		this.setNumModes(new Set(new org.rnd.util.NumberRange(1, 2)));

		{
			// Return target enchantment card from your graveyard to your hand;
			SetGenerator deadThings = Intersect.instance(HasType.instance(Type.ENCHANTMENT), InZone.instance(GraveyardOf.instance(You.instance())));
			SetGenerator target = targetedBy(this.addTarget(deadThings, "target enchantment card from your graveyard"));
			this.addEffect(1, putIntoHand(target, You.instance(), "Return target enchantment card from your graveyard to your hand"));
		}

		{
			// and/or destroy target enchantment.
			SetGenerator target = targetedBy(this.addTarget(EnchantmentPermanents.instance(), "target enchantment"));
			this.addEffect(2, destroy(target, "Destroy target enchantment."));
		}
	}
}
