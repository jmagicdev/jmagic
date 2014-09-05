package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ray of Revelation")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.COMMON), @Printings.Printed(ex = Judgment.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class RayofRevelation extends Card
{
	public RayofRevelation(GameState state)
	{
		super(state);

		// Destroy target enchantment.
		SetGenerator target = targetedBy(this.addTarget(EnchantmentPermanents.instance(), "target enchantment"));
		this.addEffect(destroy(target, "Destroy target enchantment."));

		// Flashback (G) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(G)"));
	}
}
