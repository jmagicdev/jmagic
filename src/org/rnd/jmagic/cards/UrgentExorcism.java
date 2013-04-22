package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Urgent Exorcism")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class UrgentExorcism extends Card
{
	public UrgentExorcism(GameState state)
	{
		super(state);

		// Destroy target Spirit or enchantment.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(Permanents.instance(), Union.instance(HasSubType.instance(SubType.SPIRIT), HasType.instance(Type.ENCHANTMENT))), "target Spirit or enchantment"));
		this.addEffect(destroy(target, "Destroy target Spirit or enchantment."));
	}
}
