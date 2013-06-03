package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class YawgmothsWillTest extends JUnitTest
{
	@Test
	public void yawgmothsWill() // ... dawg
	{
		this.addDeck(YawgmothsWill.class, OnewithNothing.class, Forest.class, LightningBolt.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(YawgmothsWill.class));
		addMana("2B");
		donePlayingManaAbilities();

		castAndResolveSpell(OnewithNothing.class);
		assertEquals(6, getGraveyard(0).objects.size());

		// resolve yawgdawg's will:
		pass();
		pass();
		// will of yawgadawg exiles itself
		assertEquals(6, getGraveyard(0).objects.size());

		respondWith(getLandAction(Forest.class));
		assertEquals(5, getGraveyard(0).objects.size());

		castAndResolveSpell(LightningBolt.class, "R", player(1));
		assertEquals(4, getGraveyard(0).objects.size());
	}
}