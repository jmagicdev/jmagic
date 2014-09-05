package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Shadow Slice")
@Types({Type.SORCERY})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ShadowSlice extends Card
{
	public ShadowSlice(GameState state)
	{
		super(state);

		// Target opponent loses 3 life.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		this.addEffect(loseLife(target, 3, "Target opponent loses 3 life."));

		// Cipher (Then you may exile this spell card encoded on a creature you
		// control. Whenever that creature deals combat damage to a player, its
		// controller may cast a copy of the encoded card without paying its
		// mana cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cipher(state));
	}
}
