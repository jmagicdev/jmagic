package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;

public class CombatRequirements extends JUnitTest
{
	// Endpoints in 'determine maximum attacking requirements' loop: 2 ^
	// NUM_ATTACKERS. Endpoints in 'determine maximum attacking requirements'
	// loop: (NUM_ATTACKERS + 1) ^ NUM_BLOCKERS. Increase NUM_PLEDGES and/or
	// NUM_FLURRIES beyond 1 at your own risk. (For added fun, change
	// KICK_PLEDGE to true)
	public static final boolean KICK_PLEDGE = false;
	// Ternary-if is not treated as 'trivial' in Galileo, so it gets a warning
	// for having 'dead code'. Go with a drawn-out if for now instead.
	/* public static final int NUM_ATTACKERS = NUM_PLEDGES * (KICK_PLEDGE ? 12 : 6); */
	public static final int NUM_ATTACKERS;
	public static final int NUM_BLOCKERS;
	public static final int NUM_FLURRIES = 1;
	public static final int NUM_LURES = 2;
	public static final int NUM_PLEDGES = 1;

	static
	{
		if(KICK_PLEDGE)
			NUM_ATTACKERS = NUM_PLEDGES * 12;
		else
			NUM_ATTACKERS = NUM_PLEDGES * 6;
		NUM_BLOCKERS = NUM_FLURRIES * NUM_ATTACKERS;
	}

	@Test
	public void attackingWithAttackingCostAndPlaneswalker()
	{
		this.addDeck(GhostlyPrison.class, JaceBeleren.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(TattermungeManiac.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(GhostlyPrison.class);
		castAndResolveSpell(JaceBeleren.class);

		goToPhase(Phase.PhaseType.ENDING);

		// Player 1's turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(TattermungeManiac.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "R"));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// Auto-target Tattermunge Maniac
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		// Try to declare no attackers
		declareNoAttackers();
		// The game state should revert and ask again since Tattermunge Maniac
		// must attack Jace
		respondWith(pullChoice(TattermungeManiac.class));
		respondWith(getPlayer(0));
		// Don't pay for Ghostly Prison's cost
		donePlayingManaAbilities();
		// The game state should revert and ask again since Tattermunge Maniac
		// must attack Jace
		respondWith(pullChoice(TattermungeManiac.class));
		respondWith(pullChoice(JaceBeleren.class));
		pass();
		pass();

		// We can't get here unless the game accepted the set of attackers
		assertTrue(this.game.actualState.currentStep().type == org.rnd.jmagic.engine.Step.StepType.DECLARE_BLOCKERS);
	}

	@Test
	public void attackingRequirements()
	{
		this.addDeck(Juggernaut.class, Juggernaut.class, ChaosCharm.class, ChaosCharm.class, RelentlessAssault.class, Twiddle.class, Twiddle.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals(0, this.game.actualState.attackingRequirements.size());

		respondWith(getSpellAction(Juggernaut.class));
		addMana("4");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.attackingRequirements.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Juggernaut"));
		assertEquals(5, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getStaticAbilities().size());

		Identified JuggernautA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// Auto-target Juggernaut A
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Twiddle.class));
		// Auto-target Juggernaut A
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Answer.YES);

		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());

		respondWith(getSpellAction(Juggernaut.class));
		addMana("4");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.attackingRequirements.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Juggernaut"));

		Identified JuggernautB = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(JuggernautB));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		// Try to declare no attackers
		declareNoAttackers();
		// The game state should revert and ask again since the untapped
		// Juggernaut
		// (Juggernaut B) must attack
		respondWith(pullChoice(Juggernaut.class));
		pass();
		pass();

		// We can't get here unless the game accepted the set of attackers
		assertTrue(this.game.actualState.currentStep().type == org.rnd.jmagic.engine.Step.StepType.DECLARE_BLOCKERS);

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		assertEquals(15, player(1).lifeTotal);

		// See if Relentless Assault works
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());
		assertEquals(1, this.game.actualState.currentTurn().phases.size());
		assertTrue(this.game.actualState.currentTurn().phases.get(0).type == org.rnd.jmagic.engine.Phase.PhaseType.ENDING);

		respondWith(getSpellAction(RelentlessAssault.class));
		addMana("2RR");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(!(this.game.actualState.battlefield().objects.get(0).isTapped()));
		assertEquals(3, this.game.actualState.currentTurn().phases.size());
		assertTrue(this.game.actualState.currentTurn().phases.get(0).type == org.rnd.jmagic.engine.Phase.PhaseType.COMBAT);
		assertTrue(this.game.actualState.currentTurn().phases.get(1).type == org.rnd.jmagic.engine.Phase.PhaseType.POSTCOMBAT_MAIN);
		assertTrue(this.game.actualState.currentTurn().phases.get(2).type == org.rnd.jmagic.engine.Phase.PhaseType.ENDING);

		respondWith(getSpellAction(Twiddle.class));
		respondWith(getTarget(JuggernautA));
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Answer.YES);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		// Try to declare no attackers
		declareNoAttackers();
		// The game state should revert and ask again since the Juggernaut A
		// must attack (but Juggernaut B doesn't have to)
		respondWith(getChoice(JuggernautA));
		pass();
		pass();

		// We can't get here unless the game accepted the set of attackers
		assertTrue(this.game.actualState.currentStep().type == org.rnd.jmagic.engine.Step.StepType.DECLARE_BLOCKERS);
	}

	@Ignore
	@Test
	public void benchmark()
	{
		this.addDeck(BurstofSpeed.class, ConquerorsPledge.class, ConquerorsPledge.class, ConquerorsPledge.class, ConquerorsPledge.class, Lure.class, Lure.class);
		this.addDeck(FlurryofWings.class, FlurryofWings.class, FlurryofWings.class, FlurryofWings.class, FlurryofWings.class, FlurryofWings.class, FlurryofWings.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// Add in more cards if we don't have enough
		{
			for(int i = 4; i < NUM_PLEDGES; ++i)
			{
				ConquerorsPledge newCard = new ConquerorsPledge(this.game.physicalState);
				newCard.ownerID = player(0).ID;
				getHand(0).addToTop(newCard);
			}

			for(int i = 2; i < NUM_LURES; ++i)
			{
				Lure newCard = new Lure(this.game.physicalState);
				newCard.ownerID = player(0).ID;
				getHand(0).addToTop(newCard);
			}

			for(int i = 7; i < NUM_FLURRIES; ++i)
			{
				FlurryofWings newCard = new FlurryofWings(this.game.physicalState);
				newCard.ownerID = player(1).ID;
				getHand(1).addToTop(newCard);
			}
		}

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		for(int i = 0; i < NUM_PLEDGES; ++i)
			if(KICK_PLEDGE)
			{
				respondWith(getSpellAction(ConquerorsPledge.class));
				respondWith(getCostCollection(Kicker.COST_TYPE, "6"));
				addMana("8WWW");
				donePlayingManaAbilities();
				pass();
				pass();
			}
			else
				castAndResolveSpell(ConquerorsPledge.class, "2WWW");

		assertEquals(NUM_ATTACKERS, this.game.actualState.battlefield().objects.size());

		java.util.Set<Identified> attackers = new java.util.HashSet<Identified>(this.game.actualState.battlefield().objects);

		for(int i = 0; i < NUM_LURES && i < NUM_ATTACKERS; ++i)
		{
			Identified castLureOn = attackers.iterator().next();
			attackers.remove(castLureOn);
			respondWith(getSpellAction(Lure.class));
			respondWith(getTarget(castLureOn));
			addMana("1GG");
			donePlayingManaAbilities();
			pass();
			pass();
		}

		assertEquals(NUM_ATTACKERS + NUM_LURES, this.game.actualState.battlefield().objects.size());

		castAndResolveSpell(BurstofSpeed.class, "R");

		goToStep(Step.StepType.DECLARE_ATTACKERS);

		// Attack with them ALL... :D
		respondArbitrarily();

		for(int i = 0; i < NUM_FLURRIES; ++i)
		{
			pass();
			castAndResolveSpell(FlurryofWings.class, "GWU");
		}

		assertEquals(NUM_BLOCKERS + NUM_ATTACKERS + NUM_LURES, this.game.actualState.battlefield().objects.size());

		goToStep(Step.StepType.DECLARE_BLOCKERS);
	}

	@Test
	public void blockingRequirements()
	{
		this.addDeck(Island.class, Island.class, Island.class, Island.class, Island.class, MoggFanatic.class, MoggFanatic.class);
		this.addDeck(Island.class, Island.class, Island.class, Island.class, RagingGoblin.class, RagingGoblin.class, CourtlyProvocateur.class, ChaosCharm.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(MoggFanatic.class);
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mogg Fanatic"));

		// Pass the turn so the other player can attack
		goToStep(Step.StepType.END);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RagingGoblin.class);
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Raging Goblin"));

		castAndResolveSpell(CourtlyProvocateur.class);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(CourtlyProvocateur.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(CourtlyProvocateur.CourtlyProvocateurAbility1.class));
		respondWith(getTarget(MoggFanatic.class));
		pass();
		pass();

		// There will be two blocking requirements made for each creature in
		// play (2 Raging Goblin and 2 Mogg Fanatic)
		assertEquals(1, this.game.actualState.blockingRequirements.size());

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(RagingGoblin.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);

		// Try to declare no blockers; the game state should revert
		declareNoBlockers();

		respondWith(pullChoice(MoggFanatic.class));
		// Automatically choose the Raging Goblin as the creature to block

		pass();
		pass();

		// We can't get here unless the game accepted the set of blockers
		assertTrue(this.game.actualState.currentStep().type == org.rnd.jmagic.engine.Step.StepType.COMBAT_DAMAGE);
	}

	@Test
	public void blockingRequirementsForEachDefender()
	{
		this.addDeck(Island.class, Island.class, Island.class, Island.class, Island.class, MoggFanatic.class, MoggFanatic.class);
		this.addDeck(Island.class, Island.class, Island.class, Island.class, RagingGoblin.class, RagingGoblin.class, Lure.class, Lure.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(MoggFanatic.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mogg Fanatic"));

		Identified MoggFanaticA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(MoggFanatic.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		Identified MoggFanaticB = this.game.physicalState.battlefield().objects.get(0);

		// Pass the turn so the other player can attack
		goToStep(Step.StepType.END);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(RagingGoblin.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Raging Goblin"));

		Identified RagingGoblinA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(RagingGoblin.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		Identified RagingGoblinB = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(Lure.class));
		respondWith(getTarget(RagingGoblinA));
		addMana("1GG");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Lure.class));
		respondWith(getTarget(RagingGoblinB));
		addMana("1GG");
		donePlayingManaAbilities();
		pass();
		pass();

		// There will be two blocking requirements made for each creature in
		// play (2 Raging Goblin and 2 Mogg Fanatic)
		assertEquals(8, this.game.actualState.blockingRequirements.size());

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		// Try to declare no blockers; the game state should revert
		declareNoBlockers();
		// Try to declare only a single Mogg Fanatic blocking one of the Raging
		// Goblins. The game should revert
		respondWith(getChoice(MoggFanaticA));
		respondWith(getChoice(RagingGoblinA));
		// Reverted; pick Mogg Fanatic now and have him block Raging Goblin A
		respondWith(getChoice(MoggFanaticA), getChoice(MoggFanaticB));
		respondWith(getChoice(RagingGoblinA));
		respondWith(getChoice(RagingGoblinB));
		pass();
		pass();

		// We can't get here unless the game accepted the set of blockers
		assertTrue(this.game.actualState.currentStep().type == org.rnd.jmagic.engine.Step.StepType.COMBAT_DAMAGE);
	}

	@Test
	public void inescapableBrute()
	{
		this.addDeck(InescapableBrute.class, InescapableBrute.class, InescapableBrute.class, InescapableBrute.class, InescapableBrute.class, InescapableBrute.class, ChaosCharm.class, ChaosCharm.class);
		this.addDeck(Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(InescapableBrute.class));
		addMana("5R");
		donePlayingManaAbilities();
		pass();

		respondWith(getSpellAction(Sprout.class));
		addMana("G");
		donePlayingManaAbilities();

		respondWith(getSpellAction(Sprout.class));
		addMana("G");
		donePlayingManaAbilities();

		// Resolve Sprout
		pass();
		pass();

		// Resolve Sprout
		pass();
		pass();

		// Resolve Brute
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(InescapableBrute.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);

		respondWith(pullChoice(InescapableBrute.class));

		// Depending on the draw, the player either has no actions, or can play
		// a chaos charm
		assertTrue((this.choices.size() == 1 && this.choices.iterator().next().getClass() == SanitizedCastSpellOrActivateAbilityAction.class) || this.choices.size() == 0);

		pass();
		pass();

		assertTrue(this.choices.size() == 2.0);
		assertTrue(this.choices.getOne(SanitizedIdentified.class).name.equals("Saproling"));

		// Attempt to declare no blocks
		declareNoBlockers();

		assertTrue(this.choices.size() == 2.00);
		assertTrue(this.choices.getOne(SanitizedIdentified.class).name.equals("Saproling"));

		// Declare one saproling as blocker
		respondWith(pullChoice(org.rnd.jmagic.engine.Token.class));

		// Depending on the draw, the player either has no actions, or can play
		// a chaos charm
		assertTrue((this.choices.size() == 1 && this.choices.iterator().next().getClass() == SanitizedCastSpellOrActivateAbilityAction.class) || this.choices.size() == 0);

		goToStep(Step.StepType.DRAW);
		goToStep(Step.StepType.UPKEEP);
		goToStep(Step.StepType.DECLARE_ATTACKERS);

		respondWith(pullChoice(InescapableBrute.class));

		pass();

		respondWith(getSpellAction(Sprout.class));
		addMana("G");
		donePlayingManaAbilities();

		respondWith(getSpellAction(Sprout.class));
		addMana("G");
		donePlayingManaAbilities();

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		respondWith(pullChoice(org.rnd.jmagic.engine.Token.class), pullChoice(org.rnd.jmagic.engine.Token.class), pullChoice(org.rnd.jmagic.engine.Token.class));
		respondArbitrarily();

		pass();
		pass();

		// Assign and resolve combat damage
		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		for(SanitizedTarget t: this.choices.getAll(SanitizedTarget.class))
			divisions.put(t.targetID, 1);
		divide(divisions);

		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void recklessBrute()
	{
		this.addDeck(Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, RecklessBrute.class);
		this.addDeck(Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, RecklessBrute.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RecklessBrute.class);

		goToStep(Step.StepType.DECLARE_ATTACKERS);

		assertEquals(1, this.choices.size());
		assertNotNull(pullChoice(RecklessBrute.class));
		assertEquals(PlayerInterface.ChoiceType.ATTACK, this.choiceType);

		respondWith();

		assertEquals(1, this.choices.size());
		assertEquals(PlayerInterface.ChoiceType.ATTACK, this.choiceType);

		respondWith(pullChoice(RecklessBrute.class));

		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);
	}

	@Test
	public void restrictionsVsRequirements()
	{
		// Make sure Lure doesn't try to force a non-flyer to block a flyer

		this.addDeck(Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, MoggFanatic.class);
		this.addDeck(Island.class, Island.class, Island.class, Island.class, Island.class, SuntailHawk.class, ChaosCharm.class, Lure.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(MoggFanatic.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass the turn so the other player can attack
		goToStep(Step.StepType.END);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(SuntailHawk.class));
		addMana("W");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(SuntailHawk.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Lure.class));
		respondWith(getTarget(SuntailHawk.class));
		addMana("1GG");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(SuntailHawk.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		// Declaring no blockers must be legal since the Mogg Fanatic can't
		// block the Suntail Hawk
		declareNoBlockers();
		pass();
		pass();

		// We can't get here unless the game accepted the set of blockers
		assertTrue(this.game.actualState.currentStep().type == org.rnd.jmagic.engine.Step.StepType.COMBAT_DAMAGE);

	}

	@Ignore
	@Test
	public void wayTooManySaprolings()
	{
		this.addDeck(Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Sprout.class);
		this.addDeck(Island.class, Island.class, SuntailHawk.class, SuntailHawk.class, ChaosCharm.class, ChaosCharm.class, Lure.class, Lure.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Sprout.class));
		addMana("G");
		donePlayingManaAbilities();

		// Edit the Sprout to create 100 tokens, not just one
		this.game.physicalState.stack().objects.get(0).getModes().get(0).effects.get(0).parameters.put(EventType.Parameter.NUMBER, org.rnd.jmagic.engine.generators.Identity.instance(100));

		// Pass the turn so the other player can attack
		goToStep(Step.StepType.END);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(SuntailHawk.class));
		addMana("W");
		donePlayingManaAbilities();
		pass();
		pass();

		Identified SuntailHawkA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(SuntailHawkA));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Lure.class));
		respondWith(getTarget(SuntailHawkA));
		addMana("1GG");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(SuntailHawk.class));
		addMana("W");
		donePlayingManaAbilities();
		pass();
		pass();

		Identified SuntailHawkB = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(SuntailHawkB));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Lure.class));
		respondWith(getTarget(SuntailHawkB));
		addMana("1GG");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(SuntailHawk.class), pullChoice(SuntailHawk.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		// Declaring no blockers must be legal since none of the Saprolings
		// can block either Suntail Hawk
		donePlayingManaAbilities();
		pass();
		pass();

		// We can't get here unless the game accepted the set of blockers
		assertTrue(this.game.actualState.currentStep().type == org.rnd.jmagic.engine.Step.StepType.COMBAT_DAMAGE);
	}
}
