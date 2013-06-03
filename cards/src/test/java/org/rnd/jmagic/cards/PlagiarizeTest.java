package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class PlagiarizeTest extends JUnitTest
{
	@Test
	public void plagiarize()
	{
		this.addDeck(Inspiration.class, Inspiration.class, Inspiration.class, Inspiration.class, Inspiration.class, Plagiarize.class, Plagiarize.class, Plagiarize.class, Plagiarize.class, Plagiarize.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(Inspiration.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("3U");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(6, this.getHand(0).objects.size());
		assertEquals(9, this.getHand(1).objects.size());

		this.respondWith(this.getSpellAction(Inspiration.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("3U");
		this.donePlayingManaAbilities();

		assertEquals(5, this.getHand(0).objects.size());
		assertEquals(9, this.getHand(1).objects.size());

		this.respondWith(this.getSpellAction(Plagiarize.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("3U");
		this.donePlayingManaAbilities();

		assertEquals(4, this.getHand(0).objects.size());
		assertEquals(9, this.getHand(1).objects.size());

		// Resolve the plagiarize
		this.pass();
		this.pass();

		assertEquals(4, this.getHand(0).objects.size());
		assertEquals(9, this.getHand(1).objects.size());

		// Resolve the inspiration
		this.pass();
		this.pass();

		assertEquals(6, this.getHand(0).objects.size());
		assertEquals(9, this.getHand(1).objects.size());

		this.goToStep(Step.StepType.DRAW);

		assertTrue(this.winner.ID == this.player(0).ID);
	}
}