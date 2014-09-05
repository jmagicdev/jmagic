package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class LoxodonGatekeeperTest extends JUnitTest
{
	@Test
	public void loxodonGatekeeper()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, LoxodonGatekeeper.class, LoxodonGatekeeper.class, LoxodonGatekeeper.class, LoxodonGatekeeper.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// upkeep
		this.pass();
		this.pass();

		// main phase
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(LoxodonGatekeeper.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		this.pass();
		this.pass();

		// finish main phase
		this.pass();
		this.pass();

		// beginning of combat
		this.pass();
		this.pass();

		// declare attackers
		this.pass();
		this.pass();

		// end of combat
		this.pass();
		this.pass();

		// second main phase
		this.pass();
		this.pass();

		// eot
		this.pass();
		this.pass();

		// player 1's upkeep
		this.pass();
		this.pass();

		// draw step
		this.pass();
		this.pass();

		// main phase
		this.respondWith(this.getLandAction(Plains.class));
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());
		this.pass();
		this.pass();

		// beginning of combat
		this.pass();
		this.pass();

		// declare attackers
		this.pass();
		this.pass();

		// end of combat
		this.pass();
		this.pass();

		// second main phase
		this.pass();
		this.pass();

		// eot
		this.pass();
		this.pass();

		// player 0's upkeep
		this.pass();
		this.pass();

		// draw step
		this.pass();
		this.pass();

		// main phase
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		assertTrue(!this.game.actualState.battlefield().objects.get(0).isTapped());
	}
}