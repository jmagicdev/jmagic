package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bonfire of the Damned")
@Types({Type.SORCERY})
@ManaCost("XXR")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class BonfireoftheDamned extends Card
{
	public BonfireoftheDamned(GameState state)
	{
		super(state);

		// Bonfire of the Damned deals X damage to target player and each
		// creature he or she controls.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator takers = Union.instance(target, Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(target)));
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), takers, "Bonfire of the Damned deals X damage to target player and each creature he or she controls."));

		// Miracle (X)(R) (You may cast this card for its miracle cost when you
		// draw it if it's the first card you drew this turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Miracle(state, "(X)(R)"));
	}
}
