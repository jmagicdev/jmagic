package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Tarfire")
@Types({Type.TRIBAL, Type.INSTANT})
@SubTypes({SubType.GOBLIN})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class Tarfire extends Card
{
	public Tarfire(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(2, targetedBy(target), "Tarfire deals 2 damage to target creature or player."));
	}
}
