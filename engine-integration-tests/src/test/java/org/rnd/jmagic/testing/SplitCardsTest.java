package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

public class SplitCardsTest extends JUnitTest
{
	@Test
	public void assaultBattery()
	{
		this.addDeck(AssaultBattery.class, AssaultBattery.class, AssaultBattery.class, AssaultBattery.class, AssaultBattery.class, AssaultBattery.class, GrizzlyBears.class);
		this.addDeck(GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(GrizzlyBears.class);

		respondWith(getSpellAction(AssaultBattery.class));
		respondWith(getTarget(GrizzlyBears.class));
		addMana("3RG");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());

		GameObject elephant = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Elephant", elephant.getName());
	}

	@Test
	public void deadGone()
	{
		this.addDeck(DeadGone.class, DeadGone.class, DeadGone.class, DeadGone.class, DeadGone.class, SleeperAgent.class, GrizzlyBears.class);
		this.addDeck(GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);

		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(GrizzlyBears.class);
		castAndResolveSpell(SleeperAgent.class);
		pass();
		pass();

		respondWith(getSpellAction(DeadGone.class));
		respondWith(getTarget(GrizzlyBears.class));
		// auto-pick sleeper agent
		addMana("2RR");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		// cast two creatures and dead//gone, sleeper agent returned
		assertEquals(7 - 3 + 1, player(0).getHand(this.game.actualState).objects.size());
	}
}
