package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class SeedbornMuseTest extends JUnitTest
{
	@Test
	public void seedbornMuse()
	{
		this.addDeck(SeedbornMuse.class, Forest.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

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

		this.respondWith(this.getLandAction(Forest.class));

		this.respondWith(this.getSpellAction(SeedbornMuse.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.GREEN, Color.GREEN, Color.BLACK, Color.BLACK, Color.BLACK));
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(1, this.player(0).pool.converted());

		this.respondWith(this.getIntrinsicAbilityAction(SubType.FOREST));

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(2, this.player(0).pool.converted());

		// End the turn
		// Pass 1st Main Phase
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main Phase
		this.pass();
		this.pass();
		// Pass End of Turn
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(0, this.player(0).pool.converted());

		this.pass();
		this.respondWith(this.getIntrinsicAbilityAction(SubType.FOREST));

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(1, this.player(0).pool.converted());
	}
}