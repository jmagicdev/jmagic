package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Puncture Blast")
@Types({Type.INSTANT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class PunctureBlast extends Card
{
	public PunctureBlast(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		this.addEffect(spellDealDamage(3, targetedBy(target), "Puncture Blast deals 3 damage to target creature or player."));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Wither(state));
	}
}
