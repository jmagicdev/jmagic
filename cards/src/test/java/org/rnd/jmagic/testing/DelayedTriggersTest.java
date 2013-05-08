package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class DelayedTriggersTest extends JUnitTest
{
	@Test
	public void obzedat()
	{
		this.addDeck(ObzedatGhostCouncil.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class);
		this.addDeck(OnewithNothing.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class, ObzedatGhostCouncil.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(ObzedatGhostCouncil.class);
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);

		// auto select target
		pass();
		pass();

		assertEquals(22, player(0).lifeTotal);
		assertEquals(18, player(1).lifeTotal);

		goToStep(Step.StepType.END);

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());

		pass();
		pass();
		respondWith(Answer.YES);

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());

		// go to player 1's turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// get rid of hand so we don't have to deal with discarding
		castAndResolveSpell(OnewithNothing.class);

		// and back to player 0's turn
		goToPhase(Phase.PhaseType.BEGINNING);

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());

		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(22, player(0).lifeTotal);
		assertEquals(18, player(1).lifeTotal);

		// auto select target
		pass();
		pass();

		assertEquals(24, player(0).lifeTotal);
		assertEquals(16, player(1).lifeTotal);
	}

	@Test
	public void pactOfTheTitan()
	{
		this.addDeck(PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class);
		this.addDeck(PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class, PactoftheTitan.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(PactoftheTitan.class));
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Giant", this.game.actualState.battlefield().objects.get(0).getName());

		goToStep(Step.StepType.END);
		pass();
		pass();

		// in player 1's upkeep
		respondWith(getSpellAction(PactoftheTitan.class));
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Giant", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Giant", this.game.actualState.battlefield().objects.get(1).getName());

		goToStep(Step.StepType.END);
		pass();
		pass();

		// in player 0's upkeep
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		addMana("4R");
		donePlayingManaAbilities();
		// player 0 doesn't lose b/c he can pay

		goToStep(Step.StepType.END);
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		donePlayingManaAbilities();
		// player 1 loses b/c he can't pay

		assertTrue(this.winner.ID == player(0).ID);
	}

	@Test
	public void stoneGiant()
	{
		this.addDeck(StoneGiant.class, GrizzlyBears.class, Sprout.class, RuneclawBear.class, GiantGrowth.class, ChaosCharm.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(StoneGiant.class, "2RR");
		castAndResolveSpell(GrizzlyBears.class, "1G");
		castAndResolveSpell(Sprout.class, "G");
		castAndResolveSpell(RuneclawBear.class, "1G");

		castAndResolveSpell(GiantGrowth.class, "G", GrizzlyBears.class);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(StoneGiant.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(StoneGiant.Toss.class));

		// The legal targets are a Saproling, and the Runeclaw Bear
		assertEquals(2, this.choices.size());

		respondWith(getTarget(RuneclawBear.class));
		pass();
		pass();

		assertEquals("Runeclaw Bear", this.game.actualState.battlefield().objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).hasAbility(org.rnd.jmagic.abilities.keywords.Flying.class));

		goToStep(Step.StepType.END);

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(4, this.game.actualState.battlefield().objects.size());

		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Stone Giant", this.game.actualState.battlefield().objects.get(2).getName());

	}

	@Test
	public void stoneIdolTrap()
	{
		addDeck(StoneIdolTrap.class, StoneIdolTrap.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.ENDING);
		castAndResolveSpell(StoneIdolTrap.class, "5R");
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		GameObject idol = this.game.actualState.battlefield().objects.get(0);
		assertEquals(6, idol.getPower());
		assertEquals(12, idol.getToughness());

		// Player 1's turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(Plains.class));
		goToPhase(Phase.PhaseType.ENDING);

		// Player 0's turn
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.ENDING);

		// Player 1's Plains, Stone Idol Trap token
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		// Stone Idol Trap trigger
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		// Player 1's Plains
		assertEquals(1, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void turnToMist_declaredAttackerDontSkipCombat()
	{
		// This test makes sure that declaring an attacker and removing him from
		// combat still causes the game to enter the declare blockers step
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class);
		this.addDeck(Mountain.class, RagingGoblin.class, TurntoMist.class, BlackLotus.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		// Pass Main
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

		// Second Main Phase
		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Pass End of Turn
		pass();
		pass();

		// Player 1's Turn

		// Pass Upkeep
		pass();
		pass();

		// Pass Draw
		pass();
		pass();

		// Main
		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(RagingGoblin.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(RagingGoblin.class));

		respondWith(getSpellAction(TurntoMist.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(W)"));
		respondWith(getTarget(RagingGoblin.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE));
		pass();
		pass();

		// Pass Declare Attackers
		pass();
		pass();

		// We should be in the declare blockers step now. Even though there are
		// no attacking creatures, an attacker was declared.
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.DECLARE_BLOCKERS);

	}

	@Test
	public void turnToMist_normalUse()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, TurntoMist.class, TurntoMist.class, TurntoMist.class, SoulWarden.class, SoulWarden.class, SoulWarden.class);
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

		respondWith(getSpellAction(SoulWarden.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE));
		pass();
		pass();

		respondWith(getSpellAction(TurntoMist.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(W)"));
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(2, getLibrary(0).objects.size());
		assertEquals(4, getHand(0).objects.size());
		assertEquals(2, getGraveyard(0).objects.size());

		// Go To EoT step
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

		assertTrue(this.game.actualState.currentStep().type == Step.StepType.END);
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(2, getLibrary(0).objects.size());
		assertEquals(4, getHand(0).objects.size());
		assertEquals(2, getGraveyard(0).objects.size());

		// let the trigger resolve
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(2, getLibrary(0).objects.size());
		assertEquals(4, getHand(0).objects.size());
		assertEquals(2, getGraveyard(0).objects.size());

		assertEquals(20, player(0).lifeTotal);

	}

	@Test
	public void turnToMist_removedFromCombat()
	{
		// This test makes sure that removing creatures from combat at various
		// times accomplishes the correct damage assignments.
		this.addDeck(RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, TurntoMist.class, TurntoMist.class, TurntoMist.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Meditate.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(Meditate.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(RagingGoblin.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		Identified RagingGoblinA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		Identified RagingGoblinB = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass Main Phase
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class));

		respondWith(getSpellAction(TurntoMist.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(W)"));
		respondWith(getTarget(RagingGoblinA));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE));
		pass();
		pass();

		// Pass Declare Attackers
		pass();
		pass();

		// Declare Blockers
		respondWith(getSpellAction(TurntoMist.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(W)"));
		respondWith(getTarget(RagingGoblinB));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE));
		pass();
		pass();

		// Pass Declare Blockers
		pass();
		pass();

		// Combat Damage
		respondWith(getSpellAction(TurntoMist.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(W)"));
		// Auto-Target Raging Goblin C
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass Combat Damage
		pass();
		pass();

		// End of Combat
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(19, player(1).lifeTotal);
	}

	@Test
	public void TurnToMist_removingAToken()
	{
		// This test makes sure that a token doesn't come back from being Mist'd
		this.addDeck(Sprout.class, Sprout.class, Sprout.class, BlackLotus.class, BlackLotus.class, TurntoMist.class, TurntoMist.class);
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(Sprout.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		Identified SaprolingA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(Sprout.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		respondWith(getSpellAction(TurntoMist.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(U)"));
		respondWith(getTarget(SaprolingA));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.BLUE));
		pass();
		pass();

		respondWith(getSpellAction(TurntoMist.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(U)"));
		// Auto-Choose Saproling B
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass Main
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.delayedTriggers.size());
		pass();
		pass();

		// Pass Beginning of Combat
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.delayedTriggers.size());
		pass();
		pass();

		// Pass Declare Attackers
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.delayedTriggers.size());
		pass();
		pass();

		// Pass End of Combat
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.delayedTriggers.size());
		pass();
		pass();

		// Pass Main
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.delayedTriggers.size());
		pass();
		pass();

		// End of Turn

		// Stack the Mist triggers
		respondWith(pullChoice(EventTriggeredAbility.class), pullChoice(EventTriggeredAbility.class));
		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.delayedTriggers.size());

		// Resolve them Both
		pass();
		pass();
		pass();
		pass();

		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.delayedTriggers.size());

		// Pass End of Turn
		pass();
		pass();

		// Player 1's turn

		// Upkeep
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.delayedTriggers.size());
	}
}
