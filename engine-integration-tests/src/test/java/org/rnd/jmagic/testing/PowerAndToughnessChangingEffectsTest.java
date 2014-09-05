package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

public class PowerAndToughnessChangingEffectsTest extends JUnitTest
{
	@Test
	public void characteristicDefiningAbility()
	{
		addDeck(AngelicChorus.class, Tarmogoyf.class, GiantGrowth.class, SimicInitiate.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);

		respondWith(getSpellAction(AngelicChorus.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		pass();
		pass();

		respondWith(getSpellAction(Tarmogoyf.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.WHITE));
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		// angelic chorus triggers
		respondWith(getSpellAction(GiantGrowth.class));
		// auto-choose Tarmogoyf
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(20, player(0).lifeTotal);

		// resolve chorus trigger:
		pass();
		pass();
		// tarmogoyf's toughness is (1 base) + (2 artifact and instant in
		// graveyard) + (3 giant growth)
		assertEquals(26, player(0).lifeTotal);

		respondWith(getSpellAction(SimicInitiate.class));
		donePlayingManaAbilities();
		// auto-choose G
		pass();
		pass();

		// angelic chorus triggers; resolve it:
		pass();
		pass();
		assertEquals(27, player(0).lifeTotal);

		// omg timestamps
	}

	@Test
	public void insideOut()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, InsideOut.class, InsideOut.class, AmbassadorLaquatus.class, AmbassadorLaquatus.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(AmbassadorLaquatus.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		// Will automatically pick all the U in player0's mana-pool
		pass();
		pass();

		// Ambassador
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(InsideOut.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(R)"));
		// Will automatically target Ambassador Laquatus
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED));
		pass();
		pass();

		// Ambassador
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(4, getHand(0).objects.size());

		// finish main phase
		pass();
		pass();

		// beginning of combat
		pass();
		pass();

		// declare attackers
		pass();
		pass();

		// end of combat
		pass();
		pass();

		// second main phase
		pass();
		pass();

		// Ambassador
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());

		// eot
		pass();
		pass();

		// Ambassador
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getToughness());
	}

	// First test uses a 1/1
	@Test
	public void noEffects()
	{
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BrionStoutarm.class, MoggFanatic.class, Mountain.class, ChaosCharm.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(BrionStoutarm.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(BrionStoutarm.class));
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(BrionStoutarm.Fling.class));
		respondWith(getTarget(player(1)));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(19, player(1).lifeTotal);
		assertEquals(21, player(0).lifeTotal);
		assertEquals(0, this.game.actualState.stack().objects.size());
	}

	// Second test uses a 1/1 with a +1/+1 counter
	@Test
	public void plusOnePlusOneCounter()
	{
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BrionStoutarm.class, IronshellBeetle.class, ChaosCharm.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(IronshellBeetle.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.GREEN));
		pass();
		pass();

		// Resolve Beetle's CIP Ability
		pass();
		pass();

		respondWith(getSpellAction(BrionStoutarm.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(BrionStoutarm.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getAbilityAction(BrionStoutarm.Fling.class));
		respondWith(getTarget(player(1)));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertEquals(18, player(1).lifeTotal);
		assertEquals(22, player(0).lifeTotal);
		assertEquals(0, this.game.actualState.stack().objects.size());
	}

	// Third test uses a 1/1 with a +1/+1 counter and a +3/+3 floating
	// continuous effect
	@Test
	public void plusOnePlusOneCounterWithFloatingContinuousEffect()
	{
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BrionStoutarm.class, IronshellBeetle.class, GiantGrowth.class, ChaosCharm.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BrionStoutarm.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(IronshellBeetle.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.GREEN));
		pass();
		pass();

		// Target the Beetle with it's CIP ability
		respondWith(getTarget(IronshellBeetle.class));

		// Ironshell Beetle, Brion Stoutarm
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getSpellAction(GiantGrowth.class));
		respondWith(getTarget(IronshellBeetle.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		// Ironshell Beetle, Brion Stoutarm
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());

		// Resolve Beetle's CIP Ability
		pass();
		pass();

		// Ironshell Beetle, Brion Stoutarm
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(5, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(5, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(BrionStoutarm.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getAbilityAction(BrionStoutarm.Fling.class));
		respondWith(getTarget(player(1)));
		donePlayingManaAbilities();

		assertEquals(20, player(1).lifeTotal);
		assertEquals(20, player(0).lifeTotal);
		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve Brion's ability
		pass();
		pass();

		assertEquals(15, player(1).lifeTotal);
		assertEquals(25, player(0).lifeTotal);
		assertEquals(0, this.game.actualState.stack().objects.size());

		// Make sure the pool is used up
		assertEquals(0, player(0).pool.converted());
	}

	@Test
	public void staticPTChangeAbility()
	{
		addDeck(CaptainoftheWatch.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(CaptainoftheWatch.class));
		addMana("4WW");
		donePlayingManaAbilities();
		pass();
		pass();
		pass();
		pass();

		assertEquals("Soldier", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals("Vigilance", this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().get(0).getName());
	}

	// Fourth case uses a 1/3 creature with a p/t switch effect. This testcase
	// uses stacked because compensating for Inside Out's cantrip is a pain
	@Test
	public void switchPowerToughness()
	{
		addDeck(ChaosCharm.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BrionStoutarm.class, AmbassadorLaquatus.class, InsideOut.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(AmbassadorLaquatus.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		// Auto-select all 3 Blue mana
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BrionStoutarm.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(InsideOut.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(R)"));
		respondWith(getTarget(AmbassadorLaquatus.class));
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(BrionStoutarm.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getAbilityAction(BrionStoutarm.Fling.class));
		respondWith(getTarget(player(1)));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertEquals(17, player(1).lifeTotal);
		assertEquals(23, player(0).lifeTotal);
		assertEquals(0, this.game.actualState.stack().objects.size());
	}
}
