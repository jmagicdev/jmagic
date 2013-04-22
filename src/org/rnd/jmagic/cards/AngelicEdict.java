package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angelic Edict")
@Types({Type.SORCERY})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class AngelicEdict extends Card
{
	public AngelicEdict(GameState state)
	{
		super(state);

		// Exile target creature or enchantment.
		SetGenerator legal = Union.instance(CreaturePermanents.instance(), EnchantmentPermanents.instance());
		SetGenerator target = targetedBy(this.addTarget(legal, "target creature or enchantment"));
		this.addEffect(exile(target, "Exile target creature or enchantment."));
	}
}
