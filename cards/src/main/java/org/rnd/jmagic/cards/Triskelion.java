package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Triskelion")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ANTIQUITIES, r = Rarity.RARE)})
@ColorIdentity({})
public final class Triskelion extends Card
{
	public static final class SpikeyTrikey extends ActivatedAbility
	{
		public SpikeyTrikey(GameState state)
		{
			super(state, "Remove a +1/+1 counter from Triskelion: Triskelion deals 1 damage to target creature or player.");
			this.addCost(removeCountersFromThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Triskelion"));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(1, targetedBy(target), "Triskelion deals 1 damage to target creature or player."));
		}
	}

	public Triskelion(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Triskelion enters the battlefield with three +1/+1 counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 3, Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// Remove a +1/+1 counter from Triskelion: Triskelion deals 1 damage to
		// target creature or player.
		this.addAbility(new SpikeyTrikey(state));
	}
}
