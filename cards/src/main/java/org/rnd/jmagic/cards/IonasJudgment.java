package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Iona's Judgment")
@Types({Type.SORCERY})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class IonasJudgment extends Card
{
	public IonasJudgment(GameState state)
	{
		super(state);

		// Exile target creature or enchantment.
		SetGenerator legal = Union.instance(CreaturePermanents.instance(), EnchantmentPermanents.instance());
		SetGenerator target = targetedBy(this.addTarget(legal, "target creature or enchantment"));
		this.addEffect(exile(target, "Exile target creature or enchantment."));
	}
}
