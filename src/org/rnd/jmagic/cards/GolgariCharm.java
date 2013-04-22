package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Golgari Charm")
@Types({Type.INSTANT})
@ManaCost("BG")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class GolgariCharm extends Card
{
	public GolgariCharm(GameState state)
	{
		super(state);

		// Choose one \u2014 All creatures get -1/-1 until end of turn;
		this.addEffect(1, ptChangeUntilEndOfTurn(CreaturePermanents.instance(), -1, -1, "All creatures get -1/-1 until end of turn"));

		// or destroy target enchantment;
		SetGenerator target = targetedBy(this.addTarget(2, EnchantmentPermanents.instance(), "target enchantment"));
		this.addEffect(2, destroy(target, "destroy target enchantment"));

		// or regenerate each creature you control.
		this.addEffect(3, regenerate(CREATURES_YOU_CONTROL, "regenerate each creature you control."));
	}
}
