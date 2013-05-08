package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class AdditionalLandsTest extends JUnitTest
{
	@Test
	public void azusaDoesntAffectWhenYouCanPlayLands()
	{
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, BlackLotus.class, Forest.class, AzusaLostbutSeeking.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(Forest.class));
		castAndResolveSpell(AzusaLostbutSeeking.class, "2G");

		respondWith(getSpellAction(BlackLotus.class));
		// with a spell on the stack, we should not be able to play any lands
		// just the mana ability from the Forest on the battlefield:
		assertEquals(1, this.choices.size());
	}

	@Test
	public void cloneOracleofMulDaya()
	{
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, OracleofMulDaya.class, Clone.class, Concentrate.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(Concentrate.class, "2UU");

		respondWith(getLandAction(Forest.class));

		castAndResolveSpell(OracleofMulDaya.class, "3G");

		castAndResolveSpell(Clone.class, "3U");

		respondWith(Answer.YES);
		// auto-choose the Oracle

		respondWith(getLandAction(Forest.class));
		respondWith(getLandAction(Forest.class));
	}

	@Test
	public void oneTurn()
	{
		// Play Black Lotus, Play Azusa, saccing Lotus for green, then use Azusa
		// to play 2 mountains, then tap a mountain for red and play a Mogg
		// Fanatic
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, AzusaLostbutSeeking.class, Mountain.class, Mountain.class, MoggFanatic.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(AzusaLostbutSeeking.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getLandAction(Mountain.class));

		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));

		respondWith(getSpellAction(MoggFanatic.class));
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(0, player(0).pool.converted());
		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertEquals(1, getGraveyard(0).objects.size());
		assertEquals(20, player(1).lifeTotal);

	}

	// test to make sure you can't use her too much X:_)
	@Test
	public void twoTurns()
	{

		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, AzusaLostbutSeeking.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(Forest.class));

		// play azusa, tap forest for mana
		assertEquals(2, this.choices.size());

		respondWith(getSpellAction(AzusaLostbutSeeking.class));
		addMana("2G");
		donePlayingManaAbilities();
		pass();
		pass();

		// play forest x 5 (out of seven, one is in play and one is in the
		// deck), tap forest for mana
		assertEquals(6, this.choices.size());

		respondWith(getLandAction(Forest.class));
		respondWith(getLandAction(Forest.class));

		// tap forest for mana x 3
		assertEquals(3, this.choices.size());

		// pass through opponents' turn
		goToPhase(Phase.PhaseType.BEGINNING);
		// discard a card at cleanup:
		goToStep(Step.StepType.CLEANUP);
		respondWith(pullChoice(Plains.class));

		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandForTheTurnAction(Forest.class));

		// play forest x 3 (have played 4 of 7 so far), tap forest for mana x 4
		assertEquals(7, this.choices.size());
		respondWith(getLandAction(Forest.class));
		respondWith(getLandAction(Forest.class));
		// tap forest for mana x 6
		assertEquals(6, this.choices.size());
	}
}
