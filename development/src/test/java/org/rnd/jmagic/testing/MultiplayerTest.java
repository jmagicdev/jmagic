package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class MultiplayerTest extends JUnitTest
{
	@Test
	public void onePlayerWithSevenCards()
	{
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		// Game ends immediately
		assertEquals(player(0), this.winner);
	}

	@Test
	public void onePlayerWithZeroCards()
	{
		addDeck();
		startGame(GameTypes.OPEN);

		// Game ends immediately
		assertEquals(player(0), this.winner);
	}

	@Test
	public void threePlayersWithSevenCards()
	{
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();
		keep();

		// Pass Player 0's Upkeep (lose during draw)
		assertEquals("Player 0", this.game.actualState.getPlayerWithPriority().getName());
		pass();
		assertEquals("Player 1", this.game.actualState.getPlayerWithPriority().getName());
		pass();
		assertEquals("Player 2", this.game.actualState.getPlayerWithPriority().getName());
		pass();

		// Pass Draw
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 1"));
		pass();
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 2"));
		pass();

		// Pass Main
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 1"));
		pass();
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 2"));
		pass();

		// Pass Beginning of Combat
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 1"));
		pass();
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 2"));
		pass();

		// Pass Declare Attackers
		pass();
		pass();

		// Pass End of Combat
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 1"));
		pass();
		assertTrue(this.game.actualState.currentStep().ownerID == player(0).ID);
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.END_OF_COMBAT);
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 2"));
		pass();

		// Pass 2nd Main
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 1"));
		pass();
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 2"));
		pass();

		// Pass End of Turn
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 1"));
		pass();
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 2"));
		pass();

		// Pass Player 1's Upkeep (lose during draw)
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 1"));
		pass();
		assertTrue(this.game.actualState.getPlayerWithPriority().getName().equals("Player 2"));
		pass();

		// Player 2 wins (is the last player) during Player 1's draw step
		assertTrue(this.game.actualState.currentStep().ownerID == player(1).ID);
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.DRAW);
		assertEquals(player(2), this.winner);
	}

	@Test
	public void threePlayersWithThreeCards()
	{
		addDeck(Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();
		keep();

		// Game ends immediately; no one should win
		assertEquals(null, this.winner);
	}

	@Test
	public void threePlayersWithZeroCards()
	{
		addDeck();
		addDeck();
		addDeck();
		startGame(GameTypes.STACKED);

		// Game ends immediately; no one should win
		assertEquals(null, this.winner);
	}
}
