package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;

public class CombatBasics extends JUnitTest
{
	@Test
	public void attackAPlaneswalker()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ChandraNalaar.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, GiantGrowth.class, GiantGrowth.class, Plains.class);
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(ChandraNalaar.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));
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
		// Pass 2nd Main
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(RagingGoblin.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();
		GameObject RagingGoblinA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();
		GameObject RagingGoblinB = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		pass();
		pass();
		GameObject RagingGoblinC = this.game.physicalState.battlefield().objects.get(0);

		// Pass Main
		pass();
		pass();

		// Beginning of Combat
		respondWith(getSpellAction(GiantGrowth.class));
		respondWith(getTarget(RagingGoblinA));
		addMana("G");
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		assertEquals(player(1).ID, this.choosingPlayerID);
		respondWith(pullChoice(getChoice(RagingGoblinA)), pullChoice(getChoice(RagingGoblinB)), pullChoice(getChoice(RagingGoblinC)));
		assertEquals(player(1).ID, this.choosingPlayerID);
		respondWith(pullChoice(ChandraNalaar.class));
		assertEquals(player(1).ID, this.choosingPlayerID);
		respondWith(pullChoice(ChandraNalaar.class));
		assertEquals(player(1).ID, this.choosingPlayerID);
		respondWith(getPlayer(0));

		respondWith(getSpellAction(GiantGrowth.class));
		respondWith(getTarget(RagingGoblinB));
		addMana("G");
		donePlayingManaAbilities();
		pass();
		pass();

		// Pass Declare Attackers
		pass();
		pass();

		// Pass Declare Blockers
		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertEquals("Chandra Nalaar", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals(6, this.game.actualState.battlefield().objects.get(3).counters.size());
		pass();
		pass();

		// Combat Damage
		pass();
		pass();

		assertEquals(19, player(0).lifeTotal);
		assertEquals(3, this.game.actualState.battlefield().objects.size());

	}

	@Test
	public void attackWithOneCreature()
	{
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
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
		pass();
		pass();

		// Declare Blockers
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, player(1).lifeTotal);
		pass();
		pass();

		// End of Combat
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(19, player(1).lifeTotal);
		pass();
		pass();

	}

	@Test
	public void becomesBlockedByOneCreatureTrigger()
	{
		// This is to test BECOMES_BLOCKED_BY_ONE triggers
		this.addDeck(SylvanBasilisk.class, SylvanBasilisk.class, SylvanBasilisk.class, SylvanBasilisk.class, ChaosCharm.class, ChaosCharm.class, ChaosCharm.class, ChaosCharm.class);
		this.addDeck(Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(SylvanBasilisk.class));
		addMana("3GG");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// Auto-target Basilisk
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);

		respondWith(pullChoice(SylvanBasilisk.class));

		pass();

		respondWith(getSpellAction(Sprout.class));
		addMana("G");
		donePlayingManaAbilities();

		respondWith(getSpellAction(Sprout.class));
		addMana("G");
		donePlayingManaAbilities();

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

		// Resolve Sprout
		pass();
		pass();

		// Resolve Sprout
		pass();
		pass();

		// Pass Declare Attackers
		pass();
		pass();

		// Declare Blockers
		assertEquals(player(1).ID, this.choosingPlayerID);
		respondWith(pullChoice(org.rnd.jmagic.engine.Token.class), pullChoice(org.rnd.jmagic.engine.Token.class), pullChoice(org.rnd.jmagic.engine.Token.class));
		respondArbitrarily();

		// Make sure the ability triggered 3 times, once for each blocker
		// blocking the basilisk
		assertEquals(3, this.choices.size());

		// org.rnd.jmagic.cards.SylvanBasilisk$Deathglare
		assertTrue(this.getTriggeredAbility(EventType.DESTROY_PERMANENTS) != null);

		// Stack the triggers in any order
		respondWith(pullChoice(SylvanBasilisk.Deathglare.class), pullChoice(SylvanBasilisk.Deathglare.class), pullChoice(SylvanBasilisk.Deathglare.class));

		assertEquals(5, this.game.actualState.battlefield().objects.size());
		assertEquals(3, this.game.actualState.stack().objects.size());

		// Resolve a trigger
		pass();
		pass();

		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.stack().objects.size());

		// Resolve a trigger
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve a trigger
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Saproling"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Sylvan Basilisk"));
	}

	@Test
	public void blockAnyNumber()
	{
		this.addDeck(PalaceGuard.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(PalaceGuard.class, "2W");
		goToPhase(Phase.PhaseType.ENDING);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(RagingGoblin.class, "R");
		castAndResolveSpell(RagingGoblin.class, "R");
		castAndResolveSpell(RagingGoblin.class, "R");
		castAndResolveSpell(RagingGoblin.class, "R");
		castAndResolveSpell(RagingGoblin.class, "R");
		castAndResolveSpell(RagingGoblin.class, "R");
		castAndResolveSpell(RagingGoblin.class, "R");
		castAndResolveSpell(RagingGoblin.class, "R");
		assertEquals("Palace Guard", this.game.actualState.battlefield().objects.get(8).getName());

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		// attack with 8 raging goblins
		respondWith(pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		respondWith(pullChoice(PalaceGuard.class));
		// block 4 of them
		respondWith(pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class));
		// guard's damage assignment order:

		SanitizedGameObject[] order = new SanitizedGameObject[] {pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class)};
		respondWith((java.io.Serializable[])order);

		// damage assignment:
		goToStep(Step.StepType.COMBAT_DAMAGE);

		for(int i = 0; i < 4; ++i)
			assertEquals(order[i].ID, this.game.actualState.battlefield().objects.get(8).getBlockingIDs().get(i).intValue());

		java.util.Map<Integer, Integer> illegalDamageAssignments = new java.util.HashMap<Integer, Integer>();
		illegalDamageAssignments.put(this.game.actualState.battlefield().objects.get(8).getBlockingIDs().get(0), 0);
		illegalDamageAssignments.put(this.game.actualState.battlefield().objects.get(8).getBlockingIDs().get(1), 1);
		illegalDamageAssignments.put(this.game.actualState.battlefield().objects.get(8).getBlockingIDs().get(2), 0);
		illegalDamageAssignments.put(this.game.actualState.battlefield().objects.get(8).getBlockingIDs().get(3), 0);
		divide(illegalDamageAssignments);

		java.util.Map<Integer, Integer> legalDamageAssignments = new java.util.HashMap<Integer, Integer>();
		legalDamageAssignments.put(this.game.actualState.battlefield().objects.get(8).getBlockingIDs().get(0), 1);
		legalDamageAssignments.put(this.game.actualState.battlefield().objects.get(8).getBlockingIDs().get(1), 0);
		legalDamageAssignments.put(this.game.actualState.battlefield().objects.get(8).getBlockingIDs().get(2), 0);
		legalDamageAssignments.put(this.game.actualState.battlefield().objects.get(8).getBlockingIDs().get(3), 0);
		divide(legalDamageAssignments);

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		assertEquals("Palace Guard", getGraveyard(0).objects.get(0).getName());
		assertEquals(16, player(0).lifeTotal);
	}

	@Test
	public void overrideChoosers()
	{
		this.addDeck(RagingGoblin.class, RagingGoblin.class, SleeperAgent.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(MasterWarcraft.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(RagingGoblin.class);
		castAndResolveSpell(RagingGoblin.class);
		castAndResolveSpell(SleeperAgent.class);
		respondWith(getTarget(player(1)));

		goToStep(Step.StepType.BEGINNING_OF_COMBAT);
		pass();
		pass();

		respondWith(getSpellAction(MasterWarcraft.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "2(R)(R)"));
		addMana("2(R)(R)");
		donePlayingManaAbilities();
		pass();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		assertEquals(player(2).ID, this.choosingPlayerID);
		respondWith(pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class));
		// Choose who the first Raging Goblin is attacking
		assertEquals(player(2).ID, this.choosingPlayerID);
		respondWith(getPlayer(1));
		// Choose who the second Raging Goblin is attacking
		assertEquals(player(2).ID, this.choosingPlayerID);
		respondWith(getPlayer(1));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		assertEquals(player(2).ID, this.choosingPlayerID);
		respondWith(pullChoice(SleeperAgent.class));
		// Pick either of the attacking Raging Goblins
		assertEquals(player(2).ID, this.choosingPlayerID);
		respondWith(pullChoice(RagingGoblin.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);
		assertEquals(2, this.game.actualState.battlefield().objects.size());
	}
}
