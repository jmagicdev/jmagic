package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shriekhorn")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Shriekhorn extends Card
{
	public static final class ShriekhornAbility1 extends ActivatedAbility
	{
		public ShriekhornAbility1(GameState state)
		{
			super(state, "(T), Remove a charge counter from Shriekhorn: Target player puts the top two cards of his or her library into his or her graveyard.");
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Shriekhorn"));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(millCards(target, 2, "Target player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public Shriekhorn(GameState state)
	{
		super(state);

		// Shriekhorn enters the battlefield with three charge counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 3, Counter.CounterType.CHARGE));

		// (T), Remove a charge counter from Shriekhorn: Target player puts the
		// top two cards of his or her library into his or her graveyard.
		this.addAbility(new ShriekhornAbility1(state));
	}
}
