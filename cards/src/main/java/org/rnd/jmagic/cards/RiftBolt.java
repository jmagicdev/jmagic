package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Rift Bolt")
@Types({Type.SORCERY})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RiftBolt extends Card
{
	public RiftBolt(GameState state)
	{
		super(state);

		// Rift Bolt deals 3 damage to target creature or player.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		this.addEffect(spellDealDamage(3, targetedBy(target), "Rift Bolt deals 3 damage to target creature or player."));

		// Suspend 1-(R)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Suspend(state, 1, "(R)"));
	}
}
