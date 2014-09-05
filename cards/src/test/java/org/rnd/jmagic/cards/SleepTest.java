package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class SleepTest extends JUnitTest
{
	@Test
	public void sleep()
	{
		this.addDeck(Sleep.class, Sleep.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Sprout.class, Sprout.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.pass();
		this.castAndResolveSpell(Sprout.class, "G");

		this.respondWith(this.getSpellAction(Sleep.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("2UU");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToPhase(Phase.PhaseType.ENDING);

		// player(1)'s turn 1:
		this.goToPhase(Phase.PhaseType.BEGINNING);
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());
		this.goToPhase(Phase.PhaseType.ENDING);

		// player(0)'s turn 2:
		this.goToPhase(Phase.PhaseType.BEGINNING);
		this.goToPhase(Phase.PhaseType.ENDING);

		// player(1)'s turn 2:
		this.goToPhase(Phase.PhaseType.BEGINNING);
		assertFalse(this.game.actualState.battlefield().objects.get(0).isTapped());
	}
}