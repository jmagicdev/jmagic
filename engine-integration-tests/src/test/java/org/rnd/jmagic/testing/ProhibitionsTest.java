package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

public class ProhibitionsTest extends JUnitTest
{
	@Test
	public void angelOfJubilation()
	{
		this.addDeck(AngelofJubilation.class, AngelofJubilation.class, AngelofJubilation.class, AngelofJubilation.class, AngelofJubilation.class, DemonlordofAshmouth.class, Fling.class);
		this.addDeck(AngelofJubilation.class, AngelofJubilation.class, AngelofJubilation.class, AngelofJubilation.class, AngelofJubilation.class, DemonlordofAshmouth.class, Fling.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(AngelofJubilation.class);

		respondWith(getSpellAction(Fling.class));

		// Fail to play the spell since the sacrifice is impossible
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);
		assertEquals(0, this.game.actualState.stack().objects.size());

		castAndResolveSpell(DemonlordofAshmouth.class);

		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();

		// If you get the ENUM choice here, then you have been allowed to choose
		// the sacrifice event, meaning it wasn't prohibited
		assertEquals(PlayerInterface.ChoiceType.ENUM, this.choiceType);
		respondWith(Answer.YES);

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Demonlord of Ashmouth", this.game.actualState.battlefield().objects.get(0).getName());

		pass();
	}

	@Test
	public void indestructible()
	{
		this.addDeck(DarksteelGargoyle.class, LightningBolt.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(DarksteelGargoyle.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Darksteel Gargoyle"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(DarksteelGargoyle.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Lightning Bolt, Black Lotus, Black Lotus, Black Lotus
		assertEquals(4, getGraveyard(0).objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Darksteel Gargoyle"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getDamage());
	}

	@Test
	public void pardicMiner()
	{
		this.addDeck(PardicMiner.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(PardicMiner.class);

		// One action to activate Pardic Miner's ability, 6 actions to play
		// Plains
		assertEquals(7, this.choices.size());

		respondWith(getAbilityAction(PardicMiner.PardicMinerAbility0.class));
		addMana("1R");
		respondWith(getTarget(player(0)));
		// No mana abilities to activate
		pass();
		pass();

		// No more actions to play lands
		assertEquals(0, this.choices.size());
	}

	@Test
	public void platinumAngelFailingToDraw()
	{
		this.addDeck(PlatinumAngel.class, Concentrate.class, Shatterstorm.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getSpellAction(PlatinumAngel.class));
		addMana("7");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals("Platinum Angel", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getSpellAction(Concentrate.class));
		addMana("2UU");
		donePlayingManaAbilities();
		pass();
		pass();

		// make sure player is still alive after plat dies
		respondWith(getSpellAction(Shatterstorm.class));
		addMana("2RR");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.winner == null);
	}

	@Test
	public void ruleOfLaw()
	{
		this.addDeck(RuleofLaw.class, RuleofLaw.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Sprout.class, Sprout.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(Plains.class));

		respondWith(getSpellAction(RuleofLaw.class));
		addMana("2W");
		donePlayingManaAbilities();
		pass();
		pass();

		confirmCantBePlayed(RuleofLaw.class);

		assertEquals(0, this.game.actualState.stack().objects.size());

		pass();

		respondWith(getSpellAction(Sprout.class));
		addMana("G");
		donePlayingManaAbilities();

		confirmCantBePlayed(Sprout.class);
	}

	@Test
	public void silence()
	{
		this.addDeck(RuneclawBear.class, RuneclawBear.class, RuneclawBear.class, RuneclawBear.class, RuneclawBear.class, RuneclawBear.class, RuneclawBear.class);
		this.addDeck(Silence.class, Shock.class, Shock.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// cast and resolve a runeclaw bear
		respondWith(getSpellAction(RuneclawBear.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		// case a runeclaw bear
		respondWith(getSpellAction(RuneclawBear.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();

		// respond with and resolve a silence
		respondWith(getSpellAction(Silence.class));
		addMana("W");
		donePlayingManaAbilities();
		pass();
		pass();

		// Resolve the runeclaw bear
		pass();
		pass();

		confirmCantBePlayed(RuneclawBear.class);
		pass();

		// cast a shock at one of the bears
		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(RuneclawBear.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		// end the main phase
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, getGraveyard(0).objects.size());
		assertEquals(2, getGraveyard(1).objects.size());
		assertEquals(5, getHand(0).objects.size());
		assertEquals(5, getHand(1).objects.size());
	}

	@Test
	public void steelGolem()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, SteelGolem.class, SteelGolem.class, SteelGolem.class, SteelGolem.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(SteelGolem.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		confirmCantBePlayed(SteelGolem.class);

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Black Lotus"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Black Lotus"));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Steel Golem"));
	}
}
