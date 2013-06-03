package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class CrucibleofWorldsTest extends JUnitTest
{
	@Test
	public void crucibleOfWorlds()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(AzusaLostbutSeeking.class, CrucibleofWorlds.class, OnewithNothing.class, BlackLotus.class, BlackLotus.class, Swamp.class, Swamp.class, Swamp.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		// Player 0 passes the turn
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

		// pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		// Player 1 does the tests
		this.respondWith(this.getLandAction(Swamp.class));
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(AzusaLostbutSeeking.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(CrucibleofWorlds.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(OnewithNothing.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.SWAMP));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Swamp.class));
		this.respondWith(this.getLandAction(Swamp.class));

		// Three Swamps, Azusa, Crucible0
		assertEquals(5, this.game.actualState.battlefield().objects.size());

		// One With Nothing, 2 Lotuses
		assertEquals(3, this.getGraveyard(1).objects.size());

		// Empty Hand & Library
		assertEquals(0, this.getHand(1).objects.size());
		assertEquals(0, this.getLibrary(1).objects.size());
	}
}