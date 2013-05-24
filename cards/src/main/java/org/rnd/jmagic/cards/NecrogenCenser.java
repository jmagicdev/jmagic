package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Necrogen Censer")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class NecrogenCenser extends Card
{
	public static final class NecrogenCenserAbility1 extends ActivatedAbility
	{
		public NecrogenCenserAbility1(GameState state)
		{
			super(state, "(T), Remove a charge counter from Necrogen Censer: Target player loses 2 life.");
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Necrogen Censer"));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(loseLife(target, 2, "Target player loses 2 life."));
		}
	}

	public NecrogenCenser(GameState state)
	{
		super(state);

		// Necrogen Censer enters the battlefield with two charge counters on
		// it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 2, Counter.CounterType.CHARGE));

		// (T), Remove a charge counter from Necrogen Censer: Target player
		// loses 2 life.
		this.addAbility(new NecrogenCenserAbility1(state));
	}
}
