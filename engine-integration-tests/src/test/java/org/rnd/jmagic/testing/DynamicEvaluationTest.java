package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

/**
 * For testing concepts and cards that use
 * {@link org.rnd.jmagic.Convenience.DynamicEvaluation}.
 */
public class DynamicEvaluationTest extends JUnitTest
{
	@Test
	public void conundrumSphinx()
	{
		this.addDeck(Plains.class, ConundrumSphinx.class, GoblinWarPaint.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(ConundrumSphinx.class);
		castAndResolveSpell(GoblinWarPaint.class);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(ConundrumSphinx.class));

		pass();
		pass();

		respondWith("Plains");
		respondWith("Forest");

		assertEquals(0, getLibrary(0).objects.size());
		assertEquals(1, getLibrary(1).objects.size());
	}

	@Test
	public void warpWorld()
	{
		this.addDeck(BlackLotus.class, Fear.class, ViridianShaman.class, ChandraNalaar.class, WarpWorld.class, Sprout.class, Sprout.class, SleeperAgent.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(Plains.class));

		respondWith(getSpellAction(Sprout.class));
		addMana("G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Sprout.class));
		addMana("G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(SleeperAgent.class));
		addMana("B");
		donePlayingManaAbilities();
		pass();
		pass();

		// Auto-target Player 1 with Sleeper Agent's come-into-play effect
		pass();
		pass();

		respondWith(getSpellAction(WarpWorld.class));
		addMana("5RRR");
		donePlayingManaAbilities();
		pass();
		pass();

		// Player 1 puts nothing into his library and shuffles. Since this is
		// stacked, we order the empty set of cards anyway.
		respondArbitrarily();

		assertEquals(3, this.game.actualState.battlefield().objects.size());

		assertEquals(3, getGraveyard(0).objects.size());
		assertEquals("Warp World", getGraveyard(0).objects.get(0).getName());
		assertEquals("Sprout", getGraveyard(0).objects.get(1).getName());
		assertEquals("Sprout", getGraveyard(0).objects.get(2).getName());

		assertEquals(3, getLibrary(0).objects.size());
		String cardOne = getLibrary(0).objects.get(0).getName();
		assertTrue(cardOne.equals("Plains") || cardOne.equals("Sleeper Agent"));
		String cardTwo = getLibrary(0).objects.get(1).getName();
		assertTrue(cardTwo.equals("Sleeper Agent") || cardTwo.equals("Plains"));
		assertEquals("Chandra Nalaar", getLibrary(0).objects.get(2).getName());

		// Viridian Shaman's triggered ability should automatically target the
		// Black Lotus
		pass();
		pass();

		assertEquals(4, getGraveyard(0).objects.size());
		assertEquals("Black Lotus", getGraveyard(0).objects.get(0).getName());
	}
}
