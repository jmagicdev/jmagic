package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

public class StoppingPreventionTest extends JUnitTest
{
	@Test
	public void preventingDamageToCreatures()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Excruciator.class);
		this.addDeck(Vigor.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Excruciator.class));
		addMana("6RR");
		donePlayingManaAbilities();
		pass();
		pass();

		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Vigor.class));
		addMana("3GGG");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(RagingGoblin.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		goToPhase(Phase.PhaseType.BEGINNING);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		// attack:
		respondWith(pullChoice(Excruciator.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		// block:
		respondWith(pullChoice(RagingGoblin.class));

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		assertEquals(1, getGraveyard(1).objects.size());
	}

	@Test
	public void preventingDamageToPlayers()
	{
		this.addDeck(Excruciator.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(AweStrike.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Excruciator.class));
		addMana("6RR");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// auto-target excruciator
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		pass();
		respondWith(getSpellAction(AweStrike.class));
		// auto-target excruciator
		addMana("W");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(Excruciator.class));
		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		assertEquals(13, player(1).lifeTotal);
	}
}
