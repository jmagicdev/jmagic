package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class NoMaximumHandSize extends JUnitTest
{
	@Test
	public void spellbook()
	{
		// Make sure you don't have to discard with a spellbook in play.
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Meditate.class, BlackLotus.class, Spellbook.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(Spellbook.class));
		pass();
		pass();

		respondWith(getSpellAction(Meditate.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		pass();
		pass();

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
		assertEquals(8, getHand(0).objects.size());
		pass();
		pass();

		// it should know be player 1's upkeep step, not asking player 0 to
		// discard a card
		assertEquals(8, getHand(0).objects.size());
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.UPKEEP);

		// End the turn
		// Pass Upkeep
		pass();
		pass();
		// Pass Draw
		pass();
		pass();
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
		assertEquals(8, getHand(1).objects.size());
		pass();
		pass();

		// it should now be player 1's cleanup step, and they should be being
		// asked to discard a card
		assertEquals(8, getHand(1).objects.size());
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.CLEANUP);

		// discard a card
		respondWith(pullChoice(Plains.class));

		// now it should be player 1's upkeep step (a turn was skipped because
		// of meditate)
		assertEquals(8, getHand(0).objects.size());
		assertEquals(7, getHand(1).objects.size());
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.UPKEEP);
	}
}
