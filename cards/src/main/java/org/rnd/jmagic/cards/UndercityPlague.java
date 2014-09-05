package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Undercity Plague")
@Types({Type.SORCERY})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class UndercityPlague extends Card
{
	public UndercityPlague(GameState state)
	{
		super(state);

		// Target player loses 1 life, discards a card, then sacrifices a
		// permanent.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(loseLife(target, 1, "Target player loses 1 life,"));
		this.addEffect(discardCards(target, 1, "discards a card,"));
		this.addEffect(sacrifice(target, 1, Permanents.instance(), "then sacrifices a permanent."));

		// Cipher (Then you may exile this spell card encoded on a creature you
		// control. Whenever that creature deals combat damage to a player, its
		// controller may cast a copy of the encoded card without paying its
		// mana cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cipher(state));
	}
}
