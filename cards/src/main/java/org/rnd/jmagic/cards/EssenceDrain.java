package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Essence Drain")
@Types({Type.SORCERY})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Darksteel.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class EssenceDrain extends Card
{
	public EssenceDrain(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		this.addEffect(spellDealDamage(3, targetedBy(target), "Essence Drain deals 3 damage to target creature or player"));
		this.addEffect(gainLife(You.instance(), 3, "and you gain 3 life."));
	}
}
