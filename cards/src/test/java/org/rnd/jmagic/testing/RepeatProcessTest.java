package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class RepeatProcessTest extends JUnitTest
{
	@Test
	public void scalpelexis()
	{
		this.addDeck(Scalpelexis.class, Scalpelexis.class, Scalpelexis.class, Scalpelexis.class, ChaosCharm.class, ChaosCharm.class, ChaosCharm.class, ChaosCharm.class);
		this.addDeck(
		// Remaining in library
		Swamp.class,
		// Third repetition (don't repeat)
		Swamp.class, Mountain.class, Forest.class, Island.class,
		// Second repetition (repeat on Forest)
		Forest.class, Island.class, Plains.class, Forest.class,
		// First repetition (repeat on Plains)
		Plains.class, Plains.class, Plains.class, Plains.class,
		// Hand
		Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Scalpelexis.class));
		addMana("4U");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// auto-target Scalpelexis
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);

		respondWith(pullChoice(Scalpelexis.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);

		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve Scalpelexis trigger
		pass();
		pass();

		assertEquals(1, getLibrary(1).objects.size());
		assertTrue(getLibrary(1).objects.get(0).getName().equals("Swamp"));

	}
}
