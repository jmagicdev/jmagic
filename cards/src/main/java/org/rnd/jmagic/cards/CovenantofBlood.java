package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Covenant of Blood")
@Types({Type.SORCERY})
@ManaCost("6B")
@ColorIdentity({Color.BLACK})
public final class CovenantofBlood extends Card
{
	public CovenantofBlood(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Covenant of Blood deals 4 damage to target creature or player
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(4, targetedBy(target), "Covenant of Blood deals 4 damage to target creature or player."));

		// and you gain 4 life.
		this.addEffect(gainLife(You.instance(), 4, "and you gain 4 life."));
	}
}
