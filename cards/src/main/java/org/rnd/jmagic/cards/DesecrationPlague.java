package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Desecration Plague")
@Types({Type.SORCERY})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class DesecrationPlague extends Card
{
	public DesecrationPlague(GameState state)
	{
		super(state);

		// Destroy target enchantment or land.
		SetGenerator legal = Intersect.instance(HasType.instance(Type.ENCHANTMENT, Type.LAND), Permanents.instance());
		SetGenerator target = targetedBy(this.addTarget(legal, "target enchantment or land"));
		this.addEffect(destroy(target, "Destroy target enchantment or land."));
	}
}
