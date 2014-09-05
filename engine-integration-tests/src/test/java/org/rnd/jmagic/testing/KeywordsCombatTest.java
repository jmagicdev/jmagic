package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.sanitized.*;

public class KeywordsCombatTest extends JUnitTest
{
	@Test
	public void bushidoAttacking()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class);
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

		respondWith(getSpellAction(RoninHoundmaster.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();

		Identified blocker = this.game.physicalState.battlefield().objects.get(0);

		// End the turn
		// Pass 1st Main Phase
		pass();
		pass();
		// Pass Beginning of Combat
		pass();
		pass();
		// Pass Declare Attackers
		declareNoAttackers();
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

		respondWith(getSpellAction(RoninHoundmaster.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		pass();
		pass();

		Identified attacker = this.game.physicalState.battlefield().objects.get(0);

		// Pass Main Phase
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(RoninHoundmaster.class));
		pass();
		pass();

		// Declare Blockers
		respondWith(pullChoice(RoninHoundmaster.class));
		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(attacker, this.game.actualState.battlefield().objects.get(0));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(blocker, this.game.actualState.battlefield().objects.get(1));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());

		// Resolve Blockers Bushido Trigger
		pass();
		pass();
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(attacker, this.game.actualState.battlefield().objects.get(0));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(blocker, this.game.actualState.battlefield().objects.get(1));
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getPower());

		// Resolve Attackers Bushido Trigger
		pass();
		pass();
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(attacker, this.game.actualState.battlefield().objects.get(0));
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(blocker, this.game.actualState.battlefield().objects.get(1));
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getPower());

		// Pass Declare Blockers
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, player(1).lifeTotal);
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		pass();
		pass();

		// Combat Damage
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, player(1).lifeTotal);
		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void bushidoBlocking()
	{
		// Make sure bushido works when blocked
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Forest.class));

		respondWith(getSpellAction(Sprout.class));
		respondWith(getIntrinsicAbilityAction(SubType.FOREST));
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

		respondWith(getSpellAction(RoninHoundmaster.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
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
		respondWith(pullChoice(RoninHoundmaster.class));
		pass();
		pass();

		// Declare Blockers
		respondWith(pullChoice(org.rnd.jmagic.engine.Token.class));
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Ronin Houndmaster"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());

		// Resolve Bushido Trigger
		pass();
		pass();
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Ronin Houndmaster"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());

		// Pass Declare Blockers
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, player(1).lifeTotal);
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		pass();
		pass();

		// Combat Damage
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, player(1).lifeTotal);
		assertEquals(2, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void bushidoUnblocked()
	{
		// Make sure bushido doesn't do anything for unblocked attackers
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class, RoninHoundmaster.class);
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

		respondWith(getSpellAction(RoninHoundmaster.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
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
		respondWith(pullChoice(RoninHoundmaster.class));
		pass();
		pass();

		// Declare Blockers
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, player(1).lifeTotal);
		pass();
		pass();

		// Combat Damage
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(18, player(1).lifeTotal);

	}

	@Test
	public void deathtouch()
	{
		addDeck(ThornwealdArcher.class, ThornwealdArcher.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class);
		addDeck(GrizzlyBears.class, TrollAscetic.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(ThornwealdArcher.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		goToPhase(Phase.PhaseType.BEGINNING);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(GrizzlyBears.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		GameObject grizzlyBears = this.game.actualState.battlefield().objects.get(0);

		respondWith(getSpellAction(TrollAscetic.class));
		addMana("1GG");
		donePlayingManaAbilities();
		pass();
		pass();

		GameObject trollAscetic = this.game.actualState.battlefield().objects.get(0);

		goToPhase(Phase.PhaseType.BEGINNING);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(ThornwealdArcher.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		respondWith(pullChoice(GrizzlyBears.class), pullChoice(TrollAscetic.class));
		// Order the blockers the attacker intends to damage first
		respondWith(pullChoice(GrizzlyBears.class), pullChoice(TrollAscetic.class));

		pass();
		respondWith(getAbilityAction(org.rnd.jmagic.abilities.Regenerate.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.COMBAT_DAMAGE);

		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		divisions.put(grizzlyBears.ID, 1);
		divisions.put(trollAscetic.ID, 1);
		divide(divisions);

		// Only the Troll Ascetic should have survived
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Troll Ascetic", this.game.actualState.battlefield().objects.get(0).getName());

		goToStep(Step.StepType.END_OF_COMBAT);

		// Make sure the Troll stayed alive after the next state-based action
		// check
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Troll Ascetic", this.game.actualState.battlefield().objects.get(0).getName());
	}

	@Test
	public void deathtouchWithTrample()
	{
		addDeck(ThornwealdArcher.class, ThornwealdArcher.class, LoxodonWarhammer.class, LoxodonWarhammer.class, Forest.class, Forest.class, Forest.class, Forest.class);
		addDeck(GrizzlyBears.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(ThornwealdArcher.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(LoxodonWarhammer.class));
		addMana("3");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Equip.EquipAbility.class));
		// Auto-target Thornweald Archer
		addMana("3");
		donePlayingManaAbilities();
		pass();
		pass();

		goToPhase(Phase.PhaseType.BEGINNING);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(GrizzlyBears.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		GameObject grizzlyBears = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Grizzly Bears", grizzlyBears.getName());
		assertEquals(2, grizzlyBears.getPower());
		assertEquals(2, grizzlyBears.getToughness());

		goToPhase(Phase.PhaseType.BEGINNING);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(ThornwealdArcher.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		respondWith(pullChoice(GrizzlyBears.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);
		{
			java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
			divisions.put(grizzlyBears.ID, 1);
			divisions.put(player(1).ID, 4);
			divide(divisions);
		}
		assertEquals(16, player(1).lifeTotal);
	}

	@Test
	public void defender()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, WallofWood.class, WallofWood.class, WallofWood.class, ChaosCharm.class, ChaosCharm.class);
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

		respondWith(getSpellAction(WallofWood.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Wall of Wood"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Black Lotus"));

		Identified wallOfWoodFast = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(WallofWood.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Wall of Wood"));
		assertEquals(wallOfWoodFast, this.game.actualState.battlefield().objects.get(1));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Black Lotus"));

		Identified wallOfWoodSlow = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(WallofWood.class));
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Wall of Wood"));
		assertEquals(wallOfWoodSlow, this.game.actualState.battlefield().objects.get(1));
		assertEquals(wallOfWoodFast, this.game.actualState.battlefield().objects.get(2));
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Black Lotus"));

		Identified wallOfWoodDead = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(wallOfWoodFast));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(wallOfWoodDead, this.game.actualState.battlefield().objects.get(0));
		assertEquals(wallOfWoodSlow, this.game.actualState.battlefield().objects.get(1));
		assertEquals(wallOfWoodFast, this.game.actualState.battlefield().objects.get(2));

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.DESTROY_PERMANENTS));
		respondWith(getTarget(wallOfWoodDead));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(wallOfWoodSlow, this.game.actualState.battlefield().objects.get(0));
		assertEquals(wallOfWoodFast, this.game.actualState.battlefield().objects.get(1));

		// pass main
		pass();
		pass();

		// pass beginning of combat
		pass();
		pass();

		// declare attackers step

		// the slow wall has summoning sickness so it won't show up, only the
		// fast wall since it has haste
		assertEquals(1, this.choices.size());
		assertEquals(wallOfWoodFast.ID, this.choices.toArray(new SanitizedIdentified[0])[0].ID);

		// declare the fast wall as an attacker (should fail because of
		// defender)
		respondWith(pullChoice(WallofWood.class));

		assertEquals(1, this.choices.size());
		assertEquals(wallOfWoodFast.ID, this.choices.toArray(new SanitizedIdentified[0])[0].ID);

		// declare no attackers
		declareNoAttackers();

		assertEquals(0, this.choices.size());
	}

	@Test
	public void doubleStrikeBlocking()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, TundraWolves.class, TundraWolves.class, TundraWolves.class, TundraWolves.class, TundraWolves.class, TundraWolves.class);
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Plains.class));

		respondWith(getSpellAction(TundraWolves.class));
		respondWith(getIntrinsicAbilityAction(SubType.PLAINS));
		donePlayingManaAbilities();
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

		// End of Combat
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
		pass();
		pass();

		// Declare Blockers
		assertEquals(4, this.game.actualState.battlefield().objects.size());
		respondWith(pullChoice(TundraWolves.class));
		pass();
		pass();

		// First Strike Combat Damage
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		pass();
		pass();

		// Normal Combat Damage
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		pass();
		pass();

		// End of Combat
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);

	}

	@Test
	public void doubleStrikeUnblockedAndBlocking()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, HearthfireHobgoblin.class, HearthfireHobgoblin.class, Mountain.class, Mountain.class, ChaosCharm.class, ChaosCharm.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, ArcanistheOmnipotent.class, Twiddle.class, Twiddle.class, ChaosCharm.class, Plains.class);
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

		respondWith(getSpellAction(HearthfireHobgoblin.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(W)(W)(W)"));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// Auto-Target Hearthfire Hobgoblin
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
		respondWith(pullChoice(HearthfireHobgoblin.class));
		pass();
		pass();

		// Declare Blockers
		pass();
		pass();

		// Pass First-Strike Combat Damage
		assertTrue(this.game.actualState.currentStep().type == org.rnd.jmagic.engine.Step.StepType.COMBAT_DAMAGE);
		assertEquals(18, player(1).lifeTotal);
		assertEquals(0, this.game.actualState.stack().objects.size());
		pass();
		pass();

		// Pass Normal Combat Damage
		assertTrue(this.game.actualState.currentStep().type == org.rnd.jmagic.engine.Step.StepType.COMBAT_DAMAGE);
		assertEquals(16, player(1).lifeTotal);
		assertEquals(0, this.game.actualState.stack().objects.size());
		pass();
		pass();

		// End of Combat
		assertTrue(this.game.actualState.currentStep().type == org.rnd.jmagic.engine.Step.StepType.END_OF_COMBAT);
		assertEquals(16, player(1).lifeTotal);
		assertEquals(0, this.game.actualState.stack().objects.size());
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Pass End of Turn
		pass();
		pass();

		// Player 1's turn
		// Pass Upkeep
		pass();
		pass();

		// Pass Draw
		pass();
		pass();

		// Main
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(ArcanistheOmnipotent.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.RED, Color.RED));
		pass();
		pass();

		// Untap Hearthfire Hobgoblin so he can block
		respondWith(getSpellAction(Twiddle.class));
		respondWith(getTarget(HearthfireHobgoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE));
		pass();
		pass();
		respondWith(Answer.YES);

		// Use up the remaining blue mana doing nothing
		respondWith(getSpellAction(Twiddle.class));
		respondWith(getTarget(HearthfireHobgoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE));
		pass();
		pass();
		respondWith(Answer.NO);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(ArcanistheOmnipotent.class));
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
		respondWith(pullChoice(ArcanistheOmnipotent.class));
		pass();
		pass();

		// Declare Blockers
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Arcanis the Omnipotent"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Hearthfire Hobgoblin"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
		respondWith(pullChoice(HearthfireHobgoblin.class));
		pass();
		pass();

		// First-Strike Combat Damage
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Arcanis the Omnipotent"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Hearthfire Hobgoblin"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
		pass();
		pass();

		// Normal Combat Damage
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mountain"));
		assertEquals(0, this.game.actualState.stack().objects.size());
		pass();
		pass();
	}

	@Test
	public void exaltedBasics()
	{
		this.addDeck(BlackLotus.class, Mountain.class, Mountain.class, AkrasanSquire.class, AkrasanSquire.class, AkrasanSquire.class, ChaosCharm.class);
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

		respondWith(getSpellAction(AkrasanSquire.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE));
		pass();
		pass();

		Identified akrasanSquireA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(AkrasanSquire.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE));
		pass();
		pass();

		respondWith(getSpellAction(AkrasanSquire.class));
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(akrasanSquireA));
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
		respondWith(pullChoice(AkrasanSquire.class));

		// Stack the 3 exalted triggers
		respondWith(pullChoice(org.rnd.jmagic.abilities.keywords.Exalted.ExaltedTrigger.class), pullChoice(org.rnd.jmagic.abilities.keywords.Exalted.ExaltedTrigger.class), pullChoice(org.rnd.jmagic.abilities.keywords.Exalted.ExaltedTrigger.class));
		assertEquals(3, this.game.actualState.stack().objects.size());
		assertEquals(akrasanSquireA, this.game.actualState.battlefield().objects.get(3));
		assertEquals(1, this.game.actualState.battlefield().objects.get(3).getPower());

		// Resolve an exalted trigger
		pass();
		pass();
		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(akrasanSquireA, this.game.actualState.battlefield().objects.get(3));
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getPower());

		// Resolve an exalted trigger
		pass();
		pass();
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(akrasanSquireA, this.game.actualState.battlefield().objects.get(3));
		assertEquals(3, this.game.actualState.battlefield().objects.get(3).getPower());

		// Resolve an exalted trigger
		pass();
		pass();
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(akrasanSquireA, this.game.actualState.battlefield().objects.get(3));
		assertEquals(4, this.game.actualState.battlefield().objects.get(3).getPower());

		// Pass Declare Attackers
		pass();
		pass();

		// Pass Declare Blockers
		assertEquals(20, player(1).lifeTotal);
		pass();
		pass();

		// Resolve Combat Damage
		assertEquals(16, player(1).lifeTotal);
	}

	@Test
	public void exaltedOneAttackerOnly()
	{
		// Make sure exalted doesn't trigger for multiple attackers
		this.addDeck(BlackLotus.class, BlackLotus.class, AkrasanSquire.class, AkrasanSquire.class, AkrasanSquire.class, ChaosCharm.class, ChaosCharm.class);
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

		respondWith(getSpellAction(AkrasanSquire.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE));
		pass();
		pass();

		Identified AkrasanSquireA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(AkrasanSquire.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE));
		pass();
		pass();

		Identified AkrasanSquireB = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(AkrasanSquire.class));
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(AkrasanSquireA));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(AkrasanSquireB));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(AkrasanSquire.class), pullChoice(AkrasanSquire.class));
		assertEquals(0, this.game.actualState.stack().objects.size());
		pass();
		pass();

		// Declare Blockers
		assertEquals(20, player(1).lifeTotal);
		pass();
		pass();

		// Resolve Combat Damage
		assertEquals(18, player(1).lifeTotal);
	}

	@Test
	public void exaltedOnlyCreaturesYouControl()
	{
		// Make sure Exalted doesn't give the enemy bonuses
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, AkrasanSquire.class, AkrasanSquire.class, AkrasanSquire.class, AkrasanSquire.class);
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Plains.class));

		respondWith(getSpellAction(AkrasanSquire.class));
		respondWith(getIntrinsicAbilityAction(SubType.PLAINS));
		donePlayingManaAbilities();
		pass();
		pass();

		// End the turn
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
		// Pass 2nd Main
		pass();
		pass();
		// Pass End of Turn
		pass();
		pass();

		// Pass upkeep and draw
		pass();
		pass();
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

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
		assertEquals(0, this.game.actualState.stack().objects.size());
		pass();
		pass();

		// Pass Declare Blockers
		assertEquals(20, player(0).lifeTotal);
		declareNoBlockers();
		pass();
		pass();

		// Resolve Combat Damage
		assertEquals(19, player(0).lifeTotal);
	}

	@Test
	public void fearArtifactCanBlock()
	{
		this.addDeck(PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class);
		this.addDeck(SeveredLegion.class, BlackLotus.class, Mountain.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getSpellAction(PhyrexianWalker.class));
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

		respondWith(getSpellAction(SeveredLegion.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(SeveredLegion.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass 1st Main Phase
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(SeveredLegion.class));
		pass();
		pass();

		// Declare Blockers

		// this block should pass
		respondWith(pullChoice(PhyrexianWalker.class));

		assertEquals(1, this.choices.size());
		assertTrue(this.choices.iterator().next() instanceof SanitizedCastSpellOrActivateAbilityAction);
		pass();
		pass();

		// Resolve Combat Damage
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mountain"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Severed Legion"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Phyrexian Walker"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getDamage());

		assertEquals(20, player(0).lifeTotal);
	}

	@Test
	public void fearBlackCanBlock()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, SeveredLegion.class, SeveredLegion.class, SeveredLegion.class, SeveredLegion.class);
		this.addDeck(SeveredLegion.class, BlackLotus.class, Mountain.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(SeveredLegion.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
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

		respondWith(getSpellAction(SeveredLegion.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		pass();
		pass();

		Identified SeveredLegionAttacker = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(SeveredLegionAttacker));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass 1st Main Phase
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(SeveredLegion.class));
		pass();
		pass();

		// Declare Blockers

		// this block should pass
		respondWith(pullChoice(SeveredLegion.class));

		assertEquals(1, this.choices.size());
		assertTrue(this.choices.iterator().next() instanceof SanitizedCastSpellOrActivateAbilityAction);

		pass();
		pass();

		// Resolve Combat Damage
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mountain"));

		assertEquals(20, player(0).lifeTotal);

	}

	@Test
	public void fearRedCantBlock()
	{
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class);
		this.addDeck(SeveredLegion.class, BlackLotus.class, Mountain.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
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

		respondWith(getSpellAction(SeveredLegion.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(SeveredLegion.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass 1st Main Phase
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(SeveredLegion.class));
		pass();
		pass();

		// Declare Blockers

		// this block should fail
		respondWith(pullChoice(MoggFanatic.class));

		assertEquals(1, this.choices.size());
		assertTrue(this.choices.toArray(new SanitizedIdentified[0])[0].name.equals("Mogg Fanatic"));

		// this block should pass
		declareNoBlockers();

		assertEquals(1, this.choices.size());
		assertTrue(this.choices.iterator().next() instanceof SanitizedCastSpellOrActivateAbilityAction);

		pass();
		pass();

		// Resolve Combat Damage
		pass();
		pass();

		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mountain"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Severed Legion"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Mogg Fanatic"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Mountain"));

		assertEquals(18, player(0).lifeTotal);
	}

	@Test
	public void firstStrikeBasics()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, PhyrexianWalker.class, GiantGrowth.class, ChaosCharm.class, Knighthood.class, Forest.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		// Main
		respondWith(getLandAction(Forest.class));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(PhyrexianWalker.class));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Phyrexian Walker"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());

		respondWith(getSpellAction(Knighthood.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Phyrexian Walker"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getKeywordAbilities().size());

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// Auto-Choose Phyrexian Walker
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Beginning of Combat
		respondWith(getSpellAction(GiantGrowth.class));
		// Auto-Target Phyrexian Walker
		respondWith(getIntrinsicAbilityAction(SubType.FOREST));
		donePlayingManaAbilities();
		pass();
		pass();

		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(PhyrexianWalker.class));
		pass();
		pass();

		// Declare Blockers
		assertEquals(20, player(1).lifeTotal);
		pass();
		pass();

		// First Strike Step
		assertEquals(17, player(1).lifeTotal);

		// Pass First Strike Step
		pass();
		pass();

		// Resolve Normal Combat Damage
		assertEquals(17, player(1).lifeTotal);
		pass();
		pass();

		// Normal Combat Damage Step
		assertEquals(17, player(1).lifeTotal);
		pass();
		pass();

	}

	@Test
	public void firstStrikeZeroPowerCreature()
	{
		// This tests the fix in the m10 rules so that zero power first strike
		// creatures don't assign damage again during normal combat.
		this.addDeck(BlackLotus.class, BlackLotus.class, PhyrexianWalker.class, GiantGrowth.class, ChaosCharm.class, Knighthood.class, Forest.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		// Main
		respondWith(getLandAction(Forest.class));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(PhyrexianWalker.class));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Phyrexian Walker"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());

		respondWith(getSpellAction(Knighthood.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Phyrexian Walker"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getKeywordAbilities().size());

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// Auto-Choose Phyrexian Walker
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(PhyrexianWalker.class));
		pass();
		pass();

		// Declare Blockers
		assertEquals(20, player(1).lifeTotal);
		pass();
		pass();

		// First Strike Step
		assertEquals(20, player(1).lifeTotal);

		respondWith(getSpellAction(GiantGrowth.class));
		// Auto-Target Phyrexian Walker
		respondWith(getIntrinsicAbilityAction(SubType.FOREST));
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass First Strike Step
		pass();
		pass();

		// Normal Combat Damage Step
		assertEquals(20, player(1).lifeTotal);
		pass();
		pass();
	}

	@Test
	public void flankingBasics()
	{
		// This test makes sure a 2/10 blocking a 2/2 flanker resolves correctly
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, IndomitableAncients.class, IndomitableAncients.class, IndomitableAncients.class, IndomitableAncients.class, IndomitableAncients.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BenalishCavalry.class, BenalishCavalry.class, Mountain.class, Mountain.class, ChaosCharm.class, ChaosCharm.class);
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

		respondWith(getSpellAction(IndomitableAncients.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
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

		// Main Phase

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(BenalishCavalry.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(BenalishCavalry.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(BenalishCavalry.class));
		pass();
		pass();

		// Declare Blockers
		respondWith(pullChoice(IndomitableAncients.class));

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());

		// Resolve Flanking Trigger
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(9, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());

		// Pass Declare Blockers
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(9, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());
		pass();
		pass();

		// Combat Damage
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(9, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getDamage());
	}

	@Test
	public void flankingBlockedByAnotherFlanker()
	{
		// This test makes sure Flanking doesn't trigger when blocked by a
		// creature with flanking
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BenalishCavalry.class, BenalishCavalry.class, BenalishCavalry.class, BenalishCavalry.class, BenalishCavalry.class, BenalishCavalry.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BenalishCavalry.class, BenalishCavalry.class, Mountain.class, Mountain.class, ChaosCharm.class, ChaosCharm.class);
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

		respondWith(getSpellAction(BenalishCavalry.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE));
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

		// Main Phase

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(BenalishCavalry.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE));
		pass();
		pass();

		Identified BenalishCavalryAttacker = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(BenalishCavalryAttacker));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(BenalishCavalry.class));
		pass();
		pass();

		// Declare Blockers
		respondWith(pullChoice(BenalishCavalry.class));
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getPower());
		pass();
		pass();

		// Combat Damage
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
	}

	public void flankingUnblockedAttackerDealsDamage()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BenalishCavalry.class, BenalishCavalry.class, Mountain.class, Mountain.class, ChaosCharm.class, ChaosCharm.class);
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

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(BenalishCavalry.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(BenalishCavalry.class));
		pass();
		pass();

		// Declare Blockers
		pass();
		pass();

		// Combat Damage
		assertEquals(20, player(1).lifeTotal);

		// Resolve the damage
		pass();
		pass();

		assertEquals(18, player(1).lifeTotal);
	}

	@Test
	public void flying()
	{
		this.addDeck(Plains.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, IndomitableAncients.class);
		this.addDeck(BloodiedGhost.class, BlackLotus.class, Mountain.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getLandAction(Plains.class));

		respondWith(getSpellAction(IndomitableAncients.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getIntrinsicAbilityAction(SubType.PLAINS));
		donePlayingManaAbilities();
		pass();
		pass();

		// pass main
		pass();
		pass();

		// pass beginning of combat
		pass();
		pass();

		// pass declare attackers
		pass();
		pass();

		// pass end of combat
		pass();
		pass();

		// pass second main
		pass();
		pass();

		// pass end of turn
		pass();
		pass();

		// pass upkeep
		pass();
		pass();

		// pass draw
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(BloodiedGhost.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(B)(B)"));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(BloodiedGhost.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		// pass main
		pass();
		pass();

		// pass beginning of combat
		pass();
		pass();

		// declare attackers
		respondWith(pullChoice(BloodiedGhost.class));
		pass();
		pass();

		// declare blockers
		respondWith(pullChoice(IndomitableAncients.class));

		// that block should fail and the player gets prompted for another block
		assertEquals(1, this.choices.size());
		assertTrue(this.choices.toArray(new SanitizedIdentified[0])[0].name.equals("Indomitable Ancients"));

		// try the block again
		respondWith(pullChoice(IndomitableAncients.class));

		// that block should fail again
		assertEquals(1, this.choices.size());
		assertTrue(this.choices.toArray(new SanitizedIdentified[0])[0].name.equals("Indomitable Ancients"));

		// declare no blocks
		declareNoBlockers();

		// that block should pass
		assertEquals(1, this.choices.size());
		assertTrue(this.choices.toArray(new SanitizedPlayerAction[0])[0].name.contains("Add (R)"));

		// Verify that the attacker is attacking and unblocked
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Bloodied Ghost"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getBlockedByIDs() == null);
		assertTrue(this.game.actualState.battlefield().objects.get(0).getAttackingID() != -1);

		// Verify that the attempted blocker isn't blocking
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Indomitable Ancients"));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getBlockingIDs().isEmpty());

	}

	@Test
	public void frenzy()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, FrenzySliver.class, FrenzySliver.class, ChaosCharm.class, ChaosCharm.class, Plains.class);
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

		respondWith(getSpellAction(FrenzySliver.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.BLACK));
		pass();
		pass();

		Identified frenzySliverAttacker = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(FrenzySliver.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(frenzySliverAttacker));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(FrenzySliver.class));
		pass();
		pass();

		// Declare Blockers
		// No blockers

		// Stack both frenzy triggers
		respondWith(pullChoice(org.rnd.jmagic.abilities.keywords.Frenzy.FrenzyAbility.class), pullChoice(org.rnd.jmagic.abilities.keywords.Frenzy.FrenzyAbility.class));

		assertEquals(frenzySliverAttacker, this.game.actualState.battlefield().objects.get(1));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());

		// Resolve a frenzy trigger
		pass();
		pass();

		assertEquals(frenzySliverAttacker, this.game.actualState.battlefield().objects.get(1));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());

		// Resolve a frenzy trigger
		pass();
		pass();

		assertEquals(frenzySliverAttacker, this.game.actualState.battlefield().objects.get(1));
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());

		// Pass Declare Blockers
		pass();
		pass();

		assertEquals(17, player(1).lifeTotal);

		// Pass Combat Damage
		pass();
		pass();

		// Pass End of Combat
		pass();
		pass();

		// Pass Main
		assertEquals(frenzySliverAttacker, this.game.actualState.battlefield().objects.get(1));
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());
		pass();
		pass();

		// Pass End of Turn
		assertEquals(frenzySliverAttacker, this.game.actualState.battlefield().objects.get(1));
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());
		pass();
		pass();

		// Player 1's Upkeep
		assertEquals(frenzySliverAttacker, this.game.actualState.battlefield().objects.get(1));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());
	}

	@Test
	public void landwalkAttackingWithMountainOnWrongSide()
	{
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		this.addDeck(Mountain.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, RockBadger.class, RockBadger.class, RockBadger.class, ChaosCharm.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Forest.class));

		respondWith(getSpellAction(Sprout.class));
		respondWith(getIntrinsicAbilityAction(SubType.FOREST));
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
		pass();
		pass();

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(RockBadger.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(RockBadger.class));
		donePlayingManaAbilities();
		pass();
		pass();

		// pass main
		pass();
		pass();

		// pass beginning of combat
		pass();
		pass();

		// declare attackers
		respondWith(pullChoice(RockBadger.class));
		pass();
		pass();

		// declare blockers
		respondWith(pullChoice(org.rnd.jmagic.engine.Token.class));
		assertEquals(1, this.choices.size());
		assertTrue(this.choices.toArray(new SanitizedPlayerAction[0])[0].name.contains("Add (R) to your mana pool"));
		pass();
		pass();

		// resolve combat damage
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Rock Badger"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Mountain"));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Forest"));
		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);

	}

	@Test
	public void landwalkMountainWalkWithAMountain()
	{
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, RockBadger.class, RockBadger.class, RockBadger.class, ChaosCharm.class, ChaosCharm.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(RockBadger.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(RockBadger.class));
		donePlayingManaAbilities();
		pass();
		pass();

		// pass main
		pass();
		pass();

		// pass beginning of combat
		pass();
		pass();

		// declare attackers
		respondWith(pullChoice(RockBadger.class));
		pass();
		pass();

		// declare blockers (should fail)
		respondWith(pullChoice(MoggFanatic.class));
		assertEquals(1, this.choices.size());
		assertTrue(this.choices.toArray(new SanitizedIdentified[0])[0].name.equals("Mogg Fanatic"));

		// declare blockers (should pass)
		declareNoBlockers();
		assertEquals(1, this.choices.size());
		assertTrue(this.choices.toArray(new SanitizedPlayerAction[0])[0].name.equals("Cast Chaos Charm"));

		pass();
		pass();

		// resolve combat damage
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Rock Badger"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Mogg Fanatic"));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Mountain"));
		assertEquals(17, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);
	}

	@Test
	public void landwalkMountainWalkWithNoMountain()
	{
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, RockBadger.class, RockBadger.class, RockBadger.class, ChaosCharm.class, ChaosCharm.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Forest.class));

		respondWith(getSpellAction(Sprout.class));
		respondWith(getIntrinsicAbilityAction(SubType.FOREST));
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(RockBadger.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(RockBadger.class));
		donePlayingManaAbilities();
		pass();
		pass();

		// pass main
		pass();
		pass();

		// pass beginning of combat
		pass();
		pass();

		// declare attackers
		respondWith(pullChoice(RockBadger.class));
		pass();
		pass();

		// declare blockers
		respondWith(pullChoice(org.rnd.jmagic.engine.Token.class));
		assertEquals(1, this.choices.size());
		assertTrue(this.choices.toArray(new SanitizedPlayerAction[0])[0].name.equals("Cast Chaos Charm"));
		pass();
		pass();

		// resolve combat damage
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Rock Badger"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Forest"));
		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);
	}

	@Test
	public void ninjutsu()
	{
		this.addDeck(PhyrexianWalker.class, ChaosCharm.class, NinjaoftheDeepHours.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Mountain.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		// Main

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(PhyrexianWalker.class));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// Phyrexian Walker automatically chosen
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Pass Beginning of Combat
		// choices should be black lotus and mountain abilities
		assertEquals(4, this.choices.size());
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(PhyrexianWalker.class));
		// choices should be black lotus and mountain abilities
		assertEquals(4, this.choices.size());
		pass();
		pass();

		// Declare Blockers

		// choices should now include ninjutsu abilities
		assertEquals(5, this.choices.size());
		assertEquals(1, getHand(0).objects.size());
		assertTrue(getHand(0).objects.get(0).getName().equals("Ninja of the Deep Hours"));
		assertEquals(1, getHand(0).objects.get(0).getVisibleTo().size());
		assertEquals(0, this.game.actualState.stack().objects.size());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Ninjutsu.NinjutsuAbility.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();

		assertEquals(1, getHand(0).objects.size());
		GameObject ninja = this.game.actualState.get(getHand(0).objects.get(0).ID);
		assertTrue(ninja.getName().equals("Ninja of the Deep Hours"));
		// assertEquals(2, ninja.getVisibleTo().size());
		// this doesn't work after the order shortcut change. not sure why.
		// -RulesGuru
		assertEquals(1, this.game.actualState.stack().objects.size());

		respondWith(getMana(Color.BLUE, Color.BLUE));

		assertEquals(2, getHand(0).objects.size());
		ninja = this.game.actualState.get(getHand(0).objects.get(1).ID);
		assertEquals(2, ninja.getVisibleTo().size());
		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Ninja of the Deep Hours"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped() == true);
		assertEquals(player(1).ID, this.game.actualState.battlefield().objects.get(0).getAttackingID());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getVisibleTo().size());

		// Pass Declare Blockers
		pass();
		pass();

		assertEquals(Step.StepType.COMBAT_DAMAGE, this.game.actualState.currentStep().type);
		assertEquals(18, player(1).lifeTotal);
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(0, getLibrary(0).objects.size());

		// Resolve Ninja of the Deep Hours triggered ability
		pass();
		pass();
		respondWith(Answer.YES);

		// Player 0 loses for being unable to draw a card
		assertTrue(this.winner.ID == player(1).ID);
	}

	@Test
	public void ninjutsuTargetGone()
	{
		this.addDeck(AjaniVengeant.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(RagingGoblin.class, NinjaoftheDeepHours.class, LightningBolt.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(AjaniVengeant.class);
		goToPhase(Phase.PhaseType.ENDING);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(RagingGoblin.class);

		// Raging Goblin attacks Ajani:
		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(RagingGoblin.class));
		respondWith(pullChoice(AjaniVengeant.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Ninjutsu.NinjutsuAbility.class));
		addMana("1U");
		donePlayingManaAbilities();

		castAndResolveSpell(LightningBolt.class, "R", player(0));
		// redirect to Ajani:
		respondWith(Answer.YES);

		// resolve ninjutsu:
		pass();
		pass();

		// Ninja should be attacking nothing:
		GameObject ninja = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Ninja of the Deep Hours", ninja.getName());
		assertEquals(-1, ninja.getAttackingID());
	}

	@Test
	public void poisonous()
	{
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, VirulentSliver.class, VirulentSliver.class, VirulentSliver.class, ChaosCharm.class, ChaosCharm.class, ChaosCharm.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Forest.class));

		respondWith(getSpellAction(Sprout.class));
		respondWith(getIntrinsicAbilityAction(SubType.FOREST));
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(VirulentSliver.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		Identified VirulentSliverA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(VirulentSliver.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		Identified VirulentSliverB = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(VirulentSliver.class));
		donePlayingManaAbilities();
		pass();
		pass();
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		Identified VirulentSliverC = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(VirulentSliverA));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(VirulentSliverB));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(VirulentSliverC));
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
		respondWith(pullChoice(VirulentSliver.class), pullChoice(VirulentSliver.class), pullChoice(VirulentSliver.class));
		pass();
		pass();

		// Declare Blockers
		respondWith(pullChoice(org.rnd.jmagic.engine.Token.class));
		respondWith(pullChoice(VirulentSliver.class));
		pass();
		pass();

		// Combat Damage Step

		// There should be six poison triggers on the stack (3 slivers x 2
		// unblocked)
		respondWith(pullChoice(org.rnd.jmagic.abilities.keywords.Poisonous.PoisonousAbility.class), pullChoice(org.rnd.jmagic.abilities.keywords.Poisonous.PoisonousAbility.class), pullChoice(org.rnd.jmagic.abilities.keywords.Poisonous.PoisonousAbility.class), pullChoice(org.rnd.jmagic.abilities.keywords.Poisonous.PoisonousAbility.class), pullChoice(org.rnd.jmagic.abilities.keywords.Poisonous.PoisonousAbility.class), pullChoice(org.rnd.jmagic.abilities.keywords.Poisonous.PoisonousAbility.class));
		assertEquals(6, this.game.actualState.stack().objects.size());
		assertEquals(0, player(0).countPoisonCounters());

		// Resolve a poison trigger
		pass();
		pass();
		assertEquals(5, this.game.actualState.stack().objects.size());
		assertEquals(1, player(0).countPoisonCounters());

		// Resolve a poison trigger
		pass();
		pass();
		assertEquals(4, this.game.actualState.stack().objects.size());
		assertEquals(2, player(0).countPoisonCounters());

		// Resolve a poison trigger
		pass();
		pass();
		assertEquals(3, this.game.actualState.stack().objects.size());
		assertEquals(3, player(0).countPoisonCounters());

		// Resolve a poison trigger
		pass();
		pass();
		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(4, player(0).countPoisonCounters());

		// Resolve a poison trigger
		pass();
		pass();
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(5, player(0).countPoisonCounters());

		// Resolve a poison trigger
		pass();
		pass();
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(6, player(0).countPoisonCounters());

	}

	@Test
	public void provoke()
	{
		this.addDeck(Brontotherium.class, ChaosCharm.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		this.addDeck(Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Brontotherium.class));
		addMana("4GG");
		donePlayingManaAbilities();
		pass();
		pass();

		Identified bronto = this.game.actualState.battlefield().objects.get(0);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// auto-target brontotherium
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		Identified[] saprolings = new Identified[3];

		for(int i = 0; i < 3; i++)
		{
			pass();

			respondWith(getSpellAction(Sprout.class));
			addMana("G");
			donePlayingManaAbilities();
			pass();
			pass();

			saprolings[i] = this.game.actualState.battlefield().objects.get(0);
		}

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(getChoice(bronto));

		respondWith(getTarget(saprolings[0]));
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();
		respondWith(Answer.YES);

		goToStep(Step.StepType.DECLARE_BLOCKERS);

		// Declare an illegal block
		respondWith(getChoice(saprolings[1]), getChoice(saprolings[2]));

		// Declare another illegal block
		respondWith();

		// Declare a legal block
		respondWith(getChoice(saprolings[0]), getChoice(saprolings[2]));

		// Order the blockers
		respondArbitrarily();

		// Assign and resolve combat damage
		goToStep(Step.StepType.COMBAT_DAMAGE);
		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		for(SanitizedTarget t: this.choices.getAll(SanitizedTarget.class))
			divisions.put(t.targetID, 1);
		divisions.put(player(1).ID, 3);
		divide(divisions);

		// Order the tokens on the way to the yard
		respondArbitrarily();

		assertEquals(2, this.game.actualState.battlefield().objects.size());

		assertEquals(bronto.ID, this.game.actualState.battlefield().objects.get(1).ID);
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getDamage());

		assertEquals(saprolings[1].ID, this.game.actualState.battlefield().objects.get(0).ID);

		assertEquals(17, player(1).lifeTotal);
	}

	@Test
	public void rampage()
	{
		this.addDeck(CrawGiant.class, Clone.class, ChaosCharm.class, ChaosCharm.class, CrawGiant.class, ChaosCharm.class, Plains.class);
		this.addDeck(Sprout.class, Sprout.class, Sprout.class, CurtainofLight.class, Sprout.class, CurtainofLight.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(CrawGiant.class));
		addMana("3GGGG");
		donePlayingManaAbilities();
		pass();
		pass();

		Identified CrawGiantA = this.game.actualState.battlefield().objects.get(0);

		respondWith(getSpellAction(CrawGiant.class));
		addMana("3GGGG");
		donePlayingManaAbilities();
		pass();
		pass();

		Identified CrawGiantB = this.game.actualState.battlefield().objects.get(0);

		respondWith(getSpellAction(Clone.class));
		addMana("3U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Answer.YES);
		respondWith(getChoice(CrawGiantA));

		Identified Clone = this.game.actualState.battlefield().objects.get(0);

		assertEquals(3, this.game.actualState.battlefield().objects.size());

		for(Identified target: new Identified[] {CrawGiantA, CrawGiantB, Clone})
		{
			respondWith(getSpellAction(ChaosCharm.class));
			respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
			respondWith(getTarget(target));
			addMana("R");
			donePlayingManaAbilities();
			pass();
			pass();
		}

		assertEquals(3, this.game.actualState.battlefield().objects.size());

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(getChoice(CrawGiantA), getChoice(CrawGiantB), getChoice(Clone));

		assertEquals(3, this.game.actualState.battlefield().objects.size());

		for(int i = 0; i < 3; i++)
		{
			pass();
			respondWith(getSpellAction(Sprout.class));
			addMana("G");
			donePlayingManaAbilities();
			pass();
			pass();
			assertEquals(4 + i, this.game.actualState.battlefield().objects.size());
		}

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		assertEquals(6, this.game.actualState.battlefield().objects.size());
		respondWith(pullChoice(Token.class), pullChoice(Token.class), pullChoice(Token.class));
		respondWith(getChoice(Clone));
		respondWith(getChoice(Clone));
		respondWith(getChoice(Clone));

		// Order the Saprolings blocking Clone
		respondArbitrarily();

		// Resolve the Clone's Rampage ability
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		pass();
		respondWith(getSpellAction(CurtainofLight.class));
		respondWith(getTarget(CrawGiantB));
		addMana("1W");
		donePlayingManaAbilities();
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals("Curtain of Light", this.game.actualState.stack().objects.get(0).getName());
		pass();
		pass();

		// Resolve Craw Giant B's Rampage ability
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		goToStep(Step.StepType.COMBAT_DAMAGE);

		// Assign and resolve combat damage
		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		for(SanitizedTarget t: this.choices.getAll(SanitizedTarget.class))
			divisions.put(t.targetID, 1);
		divisions.put(player(1).ID, 7);
		divide(divisions);

		respondArbitrarily();

		assertEquals(1, player(1).lifeTotal);

		assertEquals(Clone.ID, this.game.actualState.battlefield().objects.get(0).ID);
		assertEquals(10, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getBlockedByIDs().size());

		assertEquals(CrawGiantB.ID, this.game.actualState.battlefield().objects.get(1).ID);
		assertEquals(6, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getBlockedByIDs().size());

		assertEquals(CrawGiantA.ID, this.game.actualState.battlefield().objects.get(2).ID);
		assertEquals(6, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(null, this.game.actualState.battlefield().objects.get(2).getBlockedByIDs());
	}

	@Test
	public void shadowBlockingRequirementWhenAttacking()
	{
		// Test that a non-shadow creature can't block
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class);
		this.addDeck(LooterilKor.class, LooterilKor.class, LooterilKor.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Mountain.class, Mountain.class, ChaosCharm.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
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

		respondWith(getSpellAction(LooterilKor.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(LooterilKor.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Pass 1st Main Phase
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(LooterilKor.class));
		pass();
		pass();

		// Declare Blockers

		// this block should fail
		respondWith(pullChoice(MoggFanatic.class));

		assertEquals(1, this.choices.size());
		assertTrue(this.choices.toArray(new SanitizedIdentified[0])[0].name.equals("Mogg Fanatic"));

		// this block should pass
		declareNoBlockers();

		assertEquals(1, this.choices.size());
		assertTrue(this.choices.iterator().next() instanceof SanitizedCastSpellOrActivateAbilityAction);

		pass();
		pass();

		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mountain"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Looter il-Kor"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Mogg Fanatic"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Mountain"));

		assertEquals(19, player(0).lifeTotal);

		assertEquals(1, this.game.actualState.stack().objects.size());

		assertEquals(1, getLibrary(1).objects.size());
		assertEquals(4, getHand(1).objects.size());
		assertEquals(2, getGraveyard(1).objects.size());

		// Resolve the looter ability, discard anything
		pass();
		pass();
		respondWith(pullChoice(LooterilKor.class));

		assertEquals(0, getLibrary(1).objects.size());
		assertEquals(4, getHand(1).objects.size());
		assertEquals(3, getGraveyard(1).objects.size());

	}

	@Test
	public void shadowBlockingRequirementWhenBlocking()
	{
		// Test that an shadow creature can't block a non-shadow
		this.addDeck(LooterilKor.class, BlackLotus.class, Mountain.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
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

		respondWith(getSpellAction(LooterilKor.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));
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
		pass();
		pass();

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(RagingGoblin.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass 1st Main Phase
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(RagingGoblin.class));
		pass();
		pass();

		// Declare Blockers

		// this block should fail
		respondWith(pullChoice(LooterilKor.class));

		assertEquals(1, this.choices.size());
		assertTrue(this.choices.toArray(new SanitizedIdentified[0])[0].name.equals("Looter il-Kor"));

		// this block should pass
		declareNoBlockers();

		assertEquals(1, this.choices.size());
		assertTrue(this.choices.iterator().next() instanceof SanitizedCastSpellOrActivateAbilityAction);

		pass();
		pass();

		// Resolve Combat Damage
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Raging Goblin"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Mountain"));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Looter il-Kor"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());

		// 1 raging goblin
		assertEquals(19, player(0).lifeTotal);

	}

	@Test
	public void shadowShadowCanBlockShadow()
	{
		// Test that a shadow creature can block a shadow creature
		this.addDeck(LooterilKor.class, BlackLotus.class, Mountain.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(LooterilKor.class, BlackLotus.class, Mountain.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(LooterilKor.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));
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

		respondWith(getSpellAction(LooterilKor.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));
		pass();
		pass();
		Identified LooterIlKorAttacker = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(LooterIlKorAttacker));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Pass 1st Main Phase
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(LooterilKor.class));
		pass();
		pass();

		// Declare Blockers

		// this block should pass
		respondWith(pullChoice(LooterilKor.class));

		assertEquals(1, this.choices.size());
		assertTrue(this.choices.iterator().next() instanceof SanitizedCastSpellOrActivateAbilityAction);

		pass();
		pass();

		// Resolve Combat Damage
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mountain"));

		// no damage dealt
		assertEquals(20, player(0).lifeTotal);
	}

	@Test
	public void trampleDamageSpillsOver()
	{
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class);
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, SparkElemental.class, SparkElemental.class, SparkElemental.class, SparkElemental.class, SparkElemental.class, SparkElemental.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass Upkeep
		pass();
		pass();

		// Main
		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		Identified MoggFan = this.game.physicalState.battlefield().objects.get(0);

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

		// Main Phase
		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(SparkElemental.class));
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
		respondWith(pullChoice(SparkElemental.class));
		pass();
		pass();

		// Declare Blockers
		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertEquals(20, player(0).lifeTotal);
		assertEquals(0, getGraveyard(0).objects.size());
		assertEquals(20, player(1).lifeTotal);
		assertEquals(0, getGraveyard(1).objects.size());

		respondWith(pullChoice(MoggFanatic.class));
		pass();
		pass();

		// Combat Damage
		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		divisions.put(MoggFan.ID, 1);
		divisions.put(player(0).ID, 2);
		divide(divisions);

		// Pass Combat Damage
		pass();
		pass();

		// End of Combat
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(18, player(0).lifeTotal);
		assertEquals(1, getGraveyard(0).objects.size());
		assertEquals(20, player(1).lifeTotal);
		assertEquals(1, getGraveyard(1).objects.size());
	}

	@Test
	public void trampleMustBeFatal()
	{
		this.addDeck(PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class);
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, SparkElemental.class, SparkElemental.class, SparkElemental.class, SparkElemental.class, SparkElemental.class, SparkElemental.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass Upkeep
		pass();
		pass();

		// Main
		respondWith(getSpellAction(PhyrexianWalker.class));
		pass();
		pass();

		Identified Walker = this.game.physicalState.battlefield().objects.get(0);

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

		// Main Phase
		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(SparkElemental.class));
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
		respondWith(pullChoice(SparkElemental.class));
		pass();
		pass();

		// Declare Blockers
		respondWith(pullChoice(PhyrexianWalker.class));
		pass();
		pass();

		// Combat Damage
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(20, player(0).lifeTotal);
		assertEquals(0, getGraveyard(0).objects.size());
		assertEquals(20, player(1).lifeTotal);
		assertEquals(0, getGraveyard(1).objects.size());

		// This division will fail since the blocker didn't die before assigning
		// damage to the player
		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		divisions.put(Walker.ID, 2);
		divisions.put(player(0).ID, 1);
		divide(divisions);

		// This division will pass
		divisions.put(Walker.ID, 3);
		divisions.put(player(0).ID, 0);
		divide(divisions);

		// Pass Combat Damage
		pass();
		pass();

		// End of Combat
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(20, player(0).lifeTotal);
		assertEquals(1, getGraveyard(0).objects.size());
		assertEquals(20, player(1).lifeTotal);
		assertEquals(0, getGraveyard(1).objects.size());
	}

	@Test
	public void unleash()
	{
		this.addDeck(SpawnofRixMaadi.class, SpawnofRixMaadi.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class);
		this.addDeck(MadProphet.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(SpawnofRixMaadi.class);
		respondWith(Answer.NO);
		assertEquals(5, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getToughness());

		castAndResolveSpell(SpawnofRixMaadi.class);
		respondWith(Answer.YES);
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());

		goToPhase(Phase.PhaseType.ENDING);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(MadProphet.class);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(MadProphet.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);

		// Try to block with the Spawn with a +1/+1 counter
		assertEquals(PlayerInterface.ChoiceType.BLOCK, this.choiceType);
		for(SanitizedGameObject o: this.choices.getAll(SanitizedGameObject.class))
			if(o.ID == this.game.actualState.battlefield().objects.get(1).ID)
				respondWith(o);

		// The block should fail so pick the Spawn without a counter
		assertEquals(PlayerInterface.ChoiceType.BLOCK, this.choiceType);
		for(SanitizedGameObject o: this.choices.getAll(SanitizedGameObject.class))
			if(o.ID == this.game.actualState.battlefield().objects.get(2).ID)
				respondWith(o);

		// Make sure the block was accepted
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);
	}
}
