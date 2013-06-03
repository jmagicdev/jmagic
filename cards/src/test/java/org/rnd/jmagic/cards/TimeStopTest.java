package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class TimeStopTest extends JUnitTest
{
	@Test
	public void timeStopMakeSureExileHappens()
	{
		// Make sure things are removed from the game properly and that the
		// turn
		// is ended
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, Shock.class, Shock.class, MoggFanatic.class, TimeStop.class);
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

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(MoggFanatic.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));

		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));

		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.donePlayingManaAbilities();
		// Auto-choose R

		this.respondWith(this.getSpellAction(TimeStop.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		// Auto-choose UUUUUU
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(0, this.player(0).pool.converted());
		assertEquals(0, this.getHand(0).objects.size());
		assertEquals(0, this.getLibrary(0).objects.size());
		assertEquals(3, this.getGraveyard(0).objects.size());
		assertTrue(this.getGraveyard(0).objects.get(0).getName().equals("Black Lotus"));
		assertTrue(this.getGraveyard(0).objects.get(1).getName().equals("Black Lotus"));
		assertTrue(this.getGraveyard(0).objects.get(2).getName().equals("Black Lotus"));

		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.player(1).pool.converted());
		assertEquals(7, this.getHand(1).objects.size());
		assertEquals(0, this.getLibrary(1).objects.size());
		assertEquals(0, this.getGraveyard(1).objects.size());

		assertEquals(4, this.game.actualState.exileZone().objects.size());

		assertTrue(this.game.actualState.currentStep().type == Step.StepType.UPKEEP);

	}

	@Test
	public void timeStopTriggeredAbilitiesDuringCleanup()
	{
		// Make sure triggered abilities that trigger during cleanup still
		// occur
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, Megrim.class, TimeStop.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, BlackLotus.class, BlackLotus.class, Meditate.class, Meditate.class, Mountain.class, Shock.class);
		this.startGame(GameTypes.STACKED);

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

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Megrim.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// End the turn (normally)
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

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Meditate.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Meditate.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(12, this.getHand(1).objects.size());

		this.respondWith(this.getLandAction(Mountain.class));

		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.MOUNTAIN));
		this.donePlayingManaAbilities();

		assertEquals(10, this.getHand(1).objects.size());

		this.pass();
		this.respondWith(this.getSpellAction(TimeStop.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(10, this.getHand(1).objects.size());

		// Discard 3 cards
		this.respondWith(this.pullChoice(Plains.class), this.pullChoice(Plains.class), this.pullChoice(Plains.class));

		// Stack the 3 megrim triggers
		this.respondWith(this.pullChoice(Megrim.MegrimShock.class), this.pullChoice(Megrim.MegrimShock.class), this.pullChoice(Megrim.MegrimShock.class));

		assertEquals(3, this.game.actualState.stack().objects.size());
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.CLEANUP);
		assertEquals(20, this.player(1).lifeTotal);

		// Resolve the triggers
		this.pass();
		this.pass();
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.CLEANUP);
		assertEquals(14, this.player(1).lifeTotal);

		// Exit the cleanup step
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.currentStep().type == Step.StepType.UPKEEP);

	}
}