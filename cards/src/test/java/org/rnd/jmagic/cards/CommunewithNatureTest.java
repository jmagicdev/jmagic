package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;
import org.rnd.jmagic.gameTypes.*;

public class CommunewithNatureTest extends JUnitTest
{
	@Test
	public void communeWithNature()
	{
		this.addDeck(
		// Cards on bottom:
		Forest.class, Mountain.class, Island.class,
		// Cards looked at with Commune:
		CompositeGolem.class, Knighthood.class, SpinelessThug.class, HolyDay.class, BlackLotus.class,
		// Opening seven:
		Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, CommunewithNature.class);

		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(new Stacked());
		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(CommunewithNature.class));
		this.addMana("G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// choose to reveal a creature card:
		this.respondWith(Answer.YES);

		// two of the five cards seen are creatures:
		assertEquals(2, this.choices.size());
		this.respondWith(this.pullChoice(SpinelessThug.class));

		// order the cards on the bottom:
		this.respondArbitrarily();

		// 15 card deck, 7 card opener, one card removed by commune
		assertEquals(15 - 7 - 1, this.getLibrary(0).objects.size());
		assertEquals("Spineless Thug", this.getHand(0).objects.get(0).getName());

		// four cards between the island/mountain/forest and the bottom:
		assertEquals("Forest", this.getLibrary(0).objects.get(15 - 7 - 1 - 4 - 1).getName());
		assertEquals("Mountain", this.getLibrary(0).objects.get(15 - 7 - 1 - 4 - 2).getName());
		assertEquals("Island", this.getLibrary(0).objects.get(15 - 7 - 1 - 4 - 3).getName());
	}
}