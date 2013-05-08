package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class ControlChangingEffectsTest extends JUnitTest
{
	@Test
	public void persuasionAndThreaten()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, MoggFanatic.class, Threaten.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Persuasion.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(MoggFanatic.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Persuasion.class));
		// auto-target goblin
		addMana("3UU");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Mogg Fanatic"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).controllerID == player(1).ID);

		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Threaten.class));
		// auto-target mogg
		addMana("2R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(1).controllerID == player(0).ID);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(MoggFanatic.class));

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		assertEquals(19, player(1).lifeTotal);

		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		assertTrue(this.game.actualState.battlefield().objects.get(1).controllerID == player(1).ID);
	}
}
