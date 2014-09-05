package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

public class StateTriggeredAbilitiesTest extends JUnitTest
{
	@Test
	public void phylacteryLich()
	{
		this.addDeck(PhylacteryLich.class, BottleGnomes.class, BeaconofDestruction.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(BottleGnomes.class, "3");
		castAndResolveSpell(PhylacteryLich.class, "BBB");
		// Automatically choose the Bottle Gnomes to put a phylactery counter on

		// Phylactery Lich, Bottle Gnomes
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		castAndResolveSpell(BeaconofDestruction.class, "3RR", PhylacteryLich.class);

		// Make sure the Phylactery Lich didn't die
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(0, getGraveyard(0).objects.size());
		assertEquals(4, getHand(0).objects.size());
		assertEquals(1, getLibrary(0).objects.size());

		respondWith(getAbilityAction(BottleGnomes.SacForLife.class));

		// Bottle Gnome activated ability, Phylactery Lich's triggered ability
		assertEquals(2, this.game.actualState.stack().objects.size());
	}

	@Test
	public void plagueBoiler()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, PlagueBoiler.class, Swamp.class, Swamp.class, PhyrexianWalker.class, PhyrexianWalker.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getSpellAction(PhyrexianWalker.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(PlagueBoiler.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.BLACK, Color.GREEN));
		pass();
		pass();

		// Plague Boiler, Phyrexian Walker, Swamp
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getAbilityAction(PlagueBoiler.Tock.class));
		donePlayingManaAbilities();
		pass();
		pass();

		// Plague Boiler, Phyrexian Walker, Swamp
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).counters.size());

		// End the turn
		// Pass 1st Main Phase
		pass();
		pass();
		// Pass Beginning of Combat
		pass();
		pass();
		// Pass Declare Attackers
		pass();
		pass();
		// Pass End of Combat
		pass();
		pass();
		// Pass 2nd Main Phase
		pass();
		pass();
		// Pass End of Turn
		pass();
		pass();

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		// End the turn discarding any card
		// Pass 1st Main Phase
		pass();
		pass();
		// Pass Beginning of Combat
		pass();
		pass();
		// Pass Declare Attackers
		pass();
		pass();
		// Pass End of Combat
		pass();
		pass();
		// Pass 2nd Main Phase
		pass();
		pass();
		// Pass End of Turn
		pass();
		pass();
		respondWith(pullChoice(Plains.class));

		assertEquals(1, this.game.actualState.stack().objects.size());

		// Plague Boiler, Phyrexian Walker,
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).counters.size());

		// resolve counter trigger
		pass();
		pass();

		// Plague Boiler, Phyrexian Walker
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).counters.size());

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getLandAction(Swamp.class));

		respondWith(getAbilityAction(PlagueBoiler.Tock.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		respondWith(getIntrinsicAbilityAction(SubType.SWAMP));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.GREEN, Color.GREEN));

		// Swamp, Plague Boiler, Phyrexian Walker
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).counters.size());
		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve the ability, which triggers the state trigger for having 3
		// counters
		pass();
		pass();
		respondWith(getEvent(EventType.PUT_COUNTERS));

		// Swamp, Plague Boiler, Phyrexian Walker
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).counters.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve State Trigger
		pass();
		pass();

		// Verify that the only thing left in play is the land
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Swamp"));

		// Verify that the walker is on top of the graveyard, and that the
		// boiler is beneath it
		assertTrue(getGraveyard(0).objects.get(0).getName().equals("Phyrexian Walker"));
		assertTrue(getGraveyard(0).objects.get(1).getName().equals("Plague Boiler"));
	}
}
