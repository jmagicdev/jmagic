package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class ManaSomethingCouldProduce extends JUnitTest
{
	@Test
	public void fellwarStoneTolarianAcademy()
	{
		this.addDeck(BlackLotus.class, AzusaLostbutSeeking.class, AzusaLostbutSeeking.class, BlackLotus.class, TolarianAcademy.class, AnHavvaTownship.class, AnHavvaTownship.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, FellwarStone.class, FellwarStone.class, FellwarStone.class, FellwarStone.class, FellwarStone.class, FellwarStone.class);
		startGame(GameType.OPEN);

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

		respondWith(getLandAction(TolarianAcademy.class));
		respondWith(getLandAction(AnHavvaTownship.class));

		respondWith(getAbilityAction(TolarianAcademy.TolarianAcademyMana.class));
		assertEquals(0, player(0).pool.converted());

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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(FellwarStone.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.BLACK));
		pass();
		pass();

		respondWith(getAbilityAction(FellwarStone.FellwarStoneAbility.class));
		// White, Red, and Green, no Blue, no Colorless
		assertEquals(3, this.choices.size());
		respondWith(Color.WHITE);

		assertEquals(2, player(1).pool.converted());

	}

	@Test
	public void reflectingPoolForest()
	{

		this.addDeck(ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, Forest.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(ReflectingPool.class));

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

		// End the turn, discarding a card
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

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		respondWith(getLandAction(Forest.class));

		respondWith(getAbilityAction(ReflectingPool.ReflectingPoolAbility.class));
		// No choice was given since it can only make green
		assertEquals(1, player(0).pool.converted());

	}

	@Test
	public void reflectingPoolNimbusMaze()
	{

		this.addDeck(ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, NimbusMaze.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(ReflectingPool.class));

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

		// End the turn, discarding a card
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

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		respondWith(getLandAction(NimbusMaze.class));

		respondWith(getAbilityAction(ReflectingPool.ReflectingPoolAbility.class));
		// Choices should be given for white, blue, and colorless
		assertEquals(3, this.choices.size());

	}

	@Test
	public void reflectingPoolSpringjackPasture()
	{
		this.addDeck(ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, SpringjackPasture.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(ReflectingPool.class));

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

		// End the turn, discarding a card
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

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		respondWith(getLandAction(SpringjackPasture.class));

		respondWith(getAbilityAction(ReflectingPool.ReflectingPoolAbility.class));
		// Every color should be represented, as well as colorless
		assertEquals(6, this.choices.size());

	}

	@Test
	public void reflectingPoolTolarianAcademy()
	{

		this.addDeck(ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, ReflectingPool.class, TolarianAcademy.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(ReflectingPool.class));

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

		// End the turn, discarding a card
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

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		respondWith(getLandAction(TolarianAcademy.class));

		respondWith(getAbilityAction(ReflectingPool.ReflectingPoolAbility.class));
		// No choice was given, no mana was added, because Tolarian Academy
		// can't make mana
		assertEquals(2, this.choices.size());
		assertEquals(0, player(0).pool.converted());

	}
}
