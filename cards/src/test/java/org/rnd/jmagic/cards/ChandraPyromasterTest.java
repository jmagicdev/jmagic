package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class ChandraPyromasterTest extends JUnitTest
{
	@Test
	public void chandraPyromaster()
	{
		this.addDeck(LightningBolt.class, // 1 card library
				ChandraPyromaster.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		this.addDeck(GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);

		this.startGame(GameTypes.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(ChandraPyromaster.class);

		GameObject chandra = this.game.physicalState.battlefield().objects.get(0);
		// cheat three more counters onto her
		chandra.counters.add(new Counter(Counter.CounterType.LOYALTY, chandra.ID));
		chandra.counters.add(new Counter(Counter.CounterType.LOYALTY, chandra.ID));
		chandra.counters.add(new Counter(Counter.CounterType.LOYALTY, chandra.ID));

		// get the state to refresh
		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		// ultimate
		respondWith(getAbilityAction(ChandraPyromaster.ChandraPyromasterAbility2.class));
		pass();
		pass();

		// auto-choose the one lightning bolt for what to copy

		// decide to cast the copies
		respondWith(Answer.YES);
		// target player 1 with each copy
		respondWith(getTarget(player(1)));
		respondWith(getTarget(player(1)));
		respondWith(getTarget(player(1)));

		pass();
		pass();
		pass();
		pass();
		pass();
		pass();

		assertEquals(11, player(1).lifeTotal);
	}
}