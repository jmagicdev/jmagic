package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

public class PermissionsTest extends JUnitTest
{
	@Test
	public void anyPlayerMayActivate()
	{
		this.addDeck(Squallmonger.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(Squallmonger.class);

		// Player 0
		respondWith(getAbilityAction(org.rnd.jmagic.cards.Squallmonger.SquallmongerAbility0.class));
		addMana("2");
		donePlayingManaAbilities();
		pass();

		// Player 1
		respondWith(getAbilityAction(org.rnd.jmagic.cards.Squallmonger.SquallmongerAbility0.class));
		addMana("2");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(19, player(0).lifeTotal);
		assertEquals(19, player(1).lifeTotal);

		pass();
		pass();

		assertEquals(18, player(0).lifeTotal);
		assertEquals(18, player(1).lifeTotal);
	}

	@Test
	public void bloodshotTrainee()
	{
		this.addDeck(SwordofBodyandMind.class, BurstofSpeed.class, VirulentWound.class, BloodshotTrainee.class, BloodshotTrainee.class, BloodshotTrainee.class, BloodshotTrainee.class);
		this.addDeck(GiantGrowth.class, GiantGrowth.class, GiantGrowth.class, GiantGrowth.class, GiantGrowth.class, GiantGrowth.class, GiantGrowth.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(BloodshotTrainee.class);
		castAndResolveSpell(BurstofSpeed.class);
		castAndResolveSpell(SwordofBodyandMind.class);

		// 3 trainees, 1 equip ability, 1 virulent wound
		assertEquals("Bloodshot Trainee", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(5, this.choices.size());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Equip.EquipAbility.class));
		addMana("2");
		donePlayingManaAbilities();
		pass();
		pass();

		// 3 trainees, 1 equip ability, 1 trainee ability, 1 virulent wound
		assertEquals("Bloodshot Trainee", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(6, this.choices.size());

		castAndResolveSpell(VirulentWound.class);

		// 3 trainees, 1 equip ability
		assertEquals("Bloodshot Trainee", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(4, this.choices.size());
	}

	@Test
	public void mindsDesire()
	{
		this.addDeck(
		// Library
		RuneclawBear.class, LightningBolt.class, Shock.class,
		// Hand
		MindsDesire.class, BlackLotus.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(BlackLotus.class);

		// this resolves the storm trigger
		castAndResolveSpell(MindsDesire.class);

		assertEquals(2, this.game.actualState.stack().objects.size());

		// resolve both minds desire
		pass();
		pass();

		pass();
		pass();

		assertEquals(1, player(0).getLibrary(this.game.actualState).objects.size());
		assertEquals("Runeclaw Bear", player(0).getLibrary(this.game.actualState).objects.get(0).getName());

		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);

		castAndResolveSpell(LightningBolt.class, (String)null, player(1));

		assertEquals(20, player(0).lifeTotal);
		assertEquals(17, player(1).lifeTotal);

		castAndResolveSpell(Shock.class, (String)null, player(0));

		assertEquals(18, player(0).lifeTotal);
		assertEquals(17, player(1).lifeTotal);
	}

	@Test
	public void naturesChosenWithWildMongrel()
	{
		this.addDeck(NaturesChosen.class, WildMongrel.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getSpellAction(WildMongrel.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(NaturesChosen.class));
		addMana("G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getLandAction(Plains.class));

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		// Plains ability, mongrel ability, natures chosen ability #1
		assertEquals(3, this.choices.size());
		assertEquals(false, this.game.actualState.battlefield().objects.get(0).isTapped());
		assertEquals(false, this.game.actualState.battlefield().objects.get(2).isTapped());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getNonStaticAbilities().size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getColors().size());
		assertEquals(Color.GREEN, this.game.actualState.battlefield().objects.get(2).getColors().iterator().next());

		respondWith(getAbilityAction(WildMongrel.WildMongrelAbility0.class));
		respondWith(pullChoice(Plains.class));
		pass();
		pass();
		respondWith(Color.WHITE);

		assertEquals(4, this.choices.size());
		assertEquals(false, this.game.actualState.battlefield().objects.get(0).isTapped());
		assertEquals(false, this.game.actualState.battlefield().objects.get(2).isTapped());
		assertEquals(3, this.game.actualState.battlefield().objects.get(2).getNonStaticAbilities().size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getColors().size());
		assertEquals(Color.WHITE, this.game.actualState.battlefield().objects.get(2).getColors().iterator().next());

		respondWith(getAbilityAction(NaturesChosen.GrantTapExchange.TapExchange.class));
		respondWith(getTarget(Plains.class));
		pass();
		pass();

		assertEquals(3, this.choices.size());
		assertEquals(false, this.game.actualState.battlefield().objects.get(0).isTapped());
		assertEquals(true, this.game.actualState.battlefield().objects.get(2).isTapped());
		assertEquals(3, this.game.actualState.battlefield().objects.get(2).getNonStaticAbilities().size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getColors().size());
		assertEquals(Color.WHITE, this.game.actualState.battlefield().objects.get(2).getColors().iterator().next());

		respondWith(getAbilityAction(NaturesChosen.GrantNaturalUntap.NaturalUntap.class));
		pass();
		pass();

		assertEquals(2, this.choices.size());
		assertEquals(false, this.game.actualState.battlefield().objects.get(0).isTapped());
		assertEquals(false, this.game.actualState.battlefield().objects.get(2).isTapped());
		assertEquals(3, this.game.actualState.battlefield().objects.get(2).getNonStaticAbilities().size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getColors().size());
		assertEquals(Color.WHITE, this.game.actualState.battlefield().objects.get(2).getColors().iterator().next());

		respondWith(getAbilityAction(WildMongrel.WildMongrelAbility0.class));
		respondWith(pullChoice(Plains.class));
		pass();
		pass();
		respondWith(Color.RED);

		assertEquals(2, this.choices.size());
		assertEquals(false, this.game.actualState.battlefield().objects.get(0).isTapped());
		assertEquals(false, this.game.actualState.battlefield().objects.get(2).isTapped());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getNonStaticAbilities().size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getColors().size());
		assertEquals(Color.RED, this.game.actualState.battlefield().objects.get(2).getColors().iterator().next());

		respondWith(getAbilityAction(WildMongrel.WildMongrelAbility0.class));
		pass();
		pass();
		respondWith(Color.WHITE);

		assertEquals(2, this.choices.size());
		assertEquals(false, this.game.actualState.battlefield().objects.get(0).isTapped());
		assertEquals(false, this.game.actualState.battlefield().objects.get(2).isTapped());
		assertEquals(3, this.game.actualState.battlefield().objects.get(2).getNonStaticAbilities().size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getColors().size());
		assertEquals(Color.WHITE, this.game.actualState.battlefield().objects.get(2).getColors().iterator().next());
	}

	@Test
	public void necromancy()
	{
		this.addDeck(Necromancy.class, Necromancy.class, OnewithNothing.class, RagingGoblin.class, RagingGoblin.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Necromancy 1
		respondWith(getSpellAction(Necromancy.class));
		addMana("2B");
		donePlayingManaAbilities();

		// Necromancy 2 in response to Necromancy 1
		respondWith(getSpellAction(Necromancy.class));
		addMana("2B");
		donePlayingManaAbilities();

		// One with Nothing in response to Necromancy 2
		castAndResolveSpell(OnewithNothing.class);

		// Resolve Necromancy 2
		pass();
		pass();
		respondWith(getTarget(RagingGoblin.class));

		// Necromancy 2
		assertEquals(1, this.game.actualState.battlefield().objects.size());

		// Resolve the "enters the battlefield" trigger on Necromancy 2
		pass();
		pass();

		// Raging Goblin, Necromancy 2
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		// Resolve Necromancy 1
		pass();
		pass();

		// Necromancy 1, Raging Goblin, Necromancy 2
		assertEquals(3, this.game.actualState.battlefield().objects.size());

		// Resolve the "enters the battlefield" trigger on Necromancy 1
		pass();
		pass();
		// The last Raging Goblin in the yard is automatically targeted

		// Raging Goblin, Necromancy 1, Raging Goblin, Necromancy 2
		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Necromancy", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals("Necromancy", this.game.actualState.battlefield().objects.get(3).getName());

		goToStep(Step.StepType.CLEANUP);

		// Only Necromancy 2 should have triggered an auto-sacrifice
		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Necromancy", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(2).getName());

		// The trigger on Necromancy 2 to sacrifice the animated creature
		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Necromancy", this.game.actualState.battlefield().objects.get(1).getName());
	}

	@Test
	public void nimbusMaze()
	{
		this.addDeck(NimbusMaze.class, NimbusMaze.class, NimbusMaze.class, Plains.class, Plains.class, Plains.class, Island.class, Island.class, Island.class);
		this.addDeck(NimbusMaze.class, NimbusMaze.class, NimbusMaze.class, NimbusMaze.class, NimbusMaze.class, NimbusMaze.class, NimbusMaze.class, NimbusMaze.class, NimbusMaze.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Island.class));

		// Island
		assertEquals(1, this.choices.size());

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

		respondWith(getLandAction(NimbusMaze.class));

		// Nimbus1
		assertEquals(1, this.choices.size());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.TapFor1.class));

		assertEquals(1, player(1).pool.converted());
		assertEquals(1, player(1).pool.toArray(new ManaSymbol[0])[0].colorless);

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

		respondWith(getLandAction(NimbusMaze.class));

		// Island, NimbusW, Nimbus1
		assertEquals(3, this.choices.size());

		respondWith(getAbilityAction(NimbusMaze.TapForW.class));

		assertEquals(1, player(0).pool.converted());
		assertTrue(player(0).pool.toArray(new ManaSymbol[0])[0].name.equals("(W)"));

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

		respondWith(getLandAction(NimbusMaze.class));

		// Nimbus1 x 2
		assertEquals(2, this.choices.size());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.TapFor1.class));

		assertEquals(1, player(1).pool.converted());
		assertTrue(player(1).pool.toArray(new ManaSymbol[0])[0].name.equals("(1)"));

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

		respondWith(getLandAction(Plains.class));

		// Island, Plains, NimbusU, NimbusW, Nimbus1
		assertEquals(5, this.choices.size());

		respondWith(getAbilityAction(NimbusMaze.TapForU.class));

		assertEquals(1, player(0).pool.converted());
		assertTrue(player(0).pool.toArray(new ManaSymbol[0])[0].name.equals("(U)"));
	}

	@Test
	public void pithingNeedle()
	{
		GameType gameType = new GameType();
		gameType.addRule(new org.rnd.jmagic.engine.gameTypes.CardPool()
		{
			{
				this.allowSet(org.rnd.jmagic.expansions.TenthEdition.class);
			}
		});

		this.addDeck(PithingNeedle.class, MoggFanatic.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(gameType);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.respondWith(this.getSpellAction(MoggFanatic.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// 5 x Plains, Pithing Needle, Mogg Fanatic's ability
		assertEquals(7, this.choices.size());

		this.respondWith(this.getSpellAction(PithingNeedle.class));
		this.addMana("1");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// There should be 368 cards to choose from since this is limited to
		// 10th Edition by the game type.
		assertEquals(368, this.choices.size());
		this.respondWith(this.pullChoice("Mogg Fanatic"));

		// 5 x Plains
		assertEquals(5, this.choices.size());

		this.respondWith(this.getLandAction(Plains.class));

		// 1 x tap for mana
		assertEquals(1, this.choices.size());
	}

	@Test
	public void rootwalla()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Rootwalla.class, Rootwalla.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getSpellAction(Rootwalla.class));
		addMana("2G");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Rootwalla"));

		respondWith(getAbilityAction(Rootwalla.Pump.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getLandAction(Plains.class));
		// tap plains for w, play rootwalla:
		assertEquals(2, this.choices.size());

		respondWith(getSpellAction(Rootwalla.class));
		addMana("2G");
		donePlayingManaAbilities();
		pass();
		pass();

		// tap plains for w, activate rootwalla:
		assertEquals(2, this.choices.size());

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Rootwalla"));

		respondWith(getAbilityAction(Rootwalla.Pump.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());

		// tap plains for w:
		assertEquals(1, this.choices.size());

		goToPhase(Phase.PhaseType.BEGINNING);
		// discard something:
		goToStep(Step.StepType.CLEANUP);
		respondWith(pullChoice(Plains.class));

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getAbilityAction(Rootwalla.Pump.class));

	}

	@Test
	public void rootwallaCloningLimitedAbilities()
	{
		this.addDeck(Clone.class, Rootwalla.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getSpellAction(Rootwalla.class));
		addMana("2G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(Rootwalla.Pump.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Clone.class));
		addMana("3U");
		donePlayingManaAbilities();
		pass();
		pass();

		// clone resolves, choose to copy something:
		respondWith(Answer.YES);
		// auto-choose rootwalla

		respondWith(getAbilityAction(Rootwalla.Pump.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).getPower());
	}
}
