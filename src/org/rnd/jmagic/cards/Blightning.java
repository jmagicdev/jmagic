package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blightning")
@Types({Type.SORCERY})
@ManaCost("1BR")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class Blightning extends Card
{
	public Blightning(GameState state)
	{
		super(state);

		// Blightning deals 3 damage to target player.
		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(spellDealDamage(3, targetedBy(target), "Blightning deals 3 damage to target player."));

		// That player discards two cards.
		this.addEffect(discardCards(targetedBy(target), 2, "That player discards two cards."));
	}
}
