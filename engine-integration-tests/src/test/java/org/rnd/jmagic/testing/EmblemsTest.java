package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

public class EmblemsTest extends JUnitTest
{
	@Test
	public void elspeth()
	{
		this.addDeck(ElspethKnightErrant.class, Plains.class, StoneRain.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(ElspethKnightErrant.class, "2WW");

		java.util.Collection<Counter> eightLoyaltyCounters = new java.util.LinkedList<Counter>();
		GameObject elspeth = this.game.physicalState.battlefield().objects.get(0);
		for(int i = 0; i < 8; i++)
			eightLoyaltyCounters.add(new Counter(Counter.CounterType.LOYALTY, elspeth.ID));
		elspeth.counters.addAll(eightLoyaltyCounters);

		respondWith(getLandAction(Plains.class));

		respondWith(getAbilityAction(ElspethKnightErrant.AntiObliterate.class));
		pass();
		pass();

		castAndResolveSpell(StoneRain.class, "2R"); // auto-target plains
		assertEquals(2, this.game.actualState.battlefield().objects.size());
	}
}
