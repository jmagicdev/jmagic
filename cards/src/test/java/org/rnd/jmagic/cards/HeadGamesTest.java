package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;
import org.rnd.jmagic.gameTypes.*;

public class HeadGamesTest extends JUnitTest
{
	@Test
	public void headGames()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, HeadGames.class, HeadGames.class, HeadGames.class);
		this.addDeck(Island.class, Island.class, Swamp.class, Swamp.class, Mountain.class, Mountain.class, Forest.class, Forest.class);
		this.startGame(new Stacked());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(HeadGames.class));
		// auto-choose player 1
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));

		assertEquals(1, this.getLibrary(1).objects.size());
		assertTrue(this.getLibrary(1).objects.get(0).getName().equals("Island"));
		assertEquals(7, this.getHand(1).objects.size());
		assertTrue(this.getHand(1).objects.get(0).getName().equals("Island"));
		assertTrue(this.getHand(1).objects.get(1).getName().equals("Swamp"));
		assertTrue(this.getHand(1).objects.get(2).getName().equals("Swamp"));
		assertTrue(this.getHand(1).objects.get(3).getName().equals("Mountain"));
		assertTrue(this.getHand(1).objects.get(4).getName().equals("Mountain"));
		assertTrue(this.getHand(1).objects.get(5).getName().equals("Forest"));
		assertTrue(this.getHand(1).objects.get(6).getName().equals("Forest"));

		this.pass();
		this.pass();

		// order the cards on top of the library
		this.respondWith(this.pullChoice(Island.class), this.pullChoice(Swamp.class), this.pullChoice(Mountain.class), this.pullChoice(Forest.class), this.pullChoice(Swamp.class), this.pullChoice(Mountain.class), this.pullChoice(Forest.class));
		assertEquals(8, this.getLibrary(1).objects.size());
		assertTrue(this.getLibrary(1).objects.get(0).getName().equals("Forest"));
		assertTrue(this.getLibrary(1).objects.get(1).getName().equals("Mountain"));
		assertTrue(this.getLibrary(1).objects.get(2).getName().equals("Swamp"));
		assertTrue(this.getLibrary(1).objects.get(3).getName().equals("Forest"));
		assertTrue(this.getLibrary(1).objects.get(4).getName().equals("Mountain"));
		assertTrue(this.getLibrary(1).objects.get(5).getName().equals("Swamp"));
		assertTrue(this.getLibrary(1).objects.get(6).getName().equals("Island"));
		assertTrue(this.getLibrary(1).objects.get(7).getName().equals("Island"));
		assertEquals(0, this.getHand(1).objects.size());
		assertEquals(8, this.choices.size());

		// Head Games resolves; choose cards for the player's hand:
		this.respondWith(this.pullChoice(Island.class), this.pullChoice(Island.class), this.pullChoice(Swamp.class), this.pullChoice(Swamp.class), this.pullChoice(Mountain.class), this.pullChoice(Mountain.class), this.pullChoice(Forest.class));

		assertEquals(1, this.getLibrary(1).objects.size());
		assertTrue(this.getLibrary(1).objects.get(0).getName().equals("Forest"));
		assertEquals(7, this.getHand(1).objects.size());
	}
}