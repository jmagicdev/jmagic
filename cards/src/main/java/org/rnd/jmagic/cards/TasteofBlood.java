package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Taste of Blood")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class TasteofBlood extends Card
{
	public TasteofBlood(GameState state)
	{
		super(state);

		// Taste of Blood deals 1 damage to target player and you gain 1 life.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(spellDealDamage(1, target, "Taste of Blood deals 1 damage to target player"));
		this.addEffect(gainLife(You.instance(), 1, "and you gain 1 life."));
	}
}
