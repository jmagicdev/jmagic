package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Trigon of Thought")
@Types({Type.ARTIFACT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class TrigonofThought extends Card
{
	public static final class TrigonofThoughtAbility1 extends ActivatedAbility
	{
		public TrigonofThoughtAbility1(GameState state)
		{
			super(state, "(U)(U), (T): Put a charge counter on Trigon of Thought.");
			this.setManaCost(new ManaPool("(U)(U)"));
			this.costsTap = true;
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Trigon of Thought"));
		}
	}

	public static final class TrigonofThoughtAbility2 extends ActivatedAbility
	{
		public TrigonofThoughtAbility2(GameState state)
		{
			super(state, "(2), (T), Remove a charge counter from Trigon of Thought: Draw a card.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Trigon of Thought"));
			this.addEffect(drawACard());
		}
	}

	public TrigonofThought(GameState state)
	{
		super(state);

		// Trigon of Thought enters the battlefield with three charge counters
		// on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Trigon of Thought", 3, Counter.CounterType.CHARGE));

		// (U)(U), (T): Put a charge counter on Trigon of Thought.
		this.addAbility(new TrigonofThoughtAbility1(state));

		// (2), (T), Remove a charge counter from Trigon of Thought: Draw a
		// card.
		this.addAbility(new TrigonofThoughtAbility2(state));
	}
}
