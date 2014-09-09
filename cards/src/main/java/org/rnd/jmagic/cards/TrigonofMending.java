package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Trigon of Mending")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({Color.WHITE})
public final class TrigonofMending extends Card
{
	public static final class TrigonofMendingAbility1 extends ActivatedAbility
	{
		public TrigonofMendingAbility1(GameState state)
		{
			super(state, "(W)(W), (T): Put a charge counter on Trigon of Mending.");
			this.setManaCost(new ManaPool("(W)(W)"));
			this.costsTap = true;
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Trigon of Mending"));
		}
	}

	public static final class TrigonofMendingAbility2 extends ActivatedAbility
	{
		public TrigonofMendingAbility2(GameState state)
		{
			super(state, "(2), (T), Remove a charge counter from Trigon of Mending: Target player gains 3 life.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Trigon of Mending"));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(gainLife(target, 3, "Target player gains 3 life."));
		}
	}

	public TrigonofMending(GameState state)
	{
		super(state);

		// Trigon of Mending enters the battlefield with three charge counters
		// on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Trigon of Mending", 3, Counter.CounterType.CHARGE));

		// (W)(W), (T): Put a charge counter on Trigon of Mending.
		this.addAbility(new TrigonofMendingAbility1(state));

		// (2), (T), Remove a charge counter from Trigon of Mending: Target
		// player gains 3 life.
		this.addAbility(new TrigonofMendingAbility2(state));
	}
}
