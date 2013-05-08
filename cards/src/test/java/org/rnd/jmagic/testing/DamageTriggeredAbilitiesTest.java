package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class DamageTriggeredAbilitiesTest extends JUnitTest
{
	@Test
	public void phageTheUntouchable()
	{
		addDeck(WallofAir.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(PhagetheUntouchable.class, PhagetheUntouchable.class, ElvishPiper.class, ChaosCharm.class, ChaosCharm.class, RelentlessAssault.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(WallofAir.class, "1UU");

		goToPhase(Phase.PhaseType.ENDING);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(PhagetheUntouchable.class, "3BBBB");

		// Make sure phage didn't trigger
		assertEquals(0, this.game.actualState.stack().objects.size());

		GameObject phage = this.game.actualState.battlefield().objects.get(0);
		assertEquals(3, phage.getNonStaticAbilities().size());

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(PhagetheUntouchable.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(PhagetheUntouchable.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		respondWith(pullChoice(WallofAir.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);

		// The trigger to destroy the creature should be on the stack
		assertEquals(1, this.game.actualState.stack().objects.size());

		goToStep(Step.StepType.END_OF_COMBAT);

		// The wall should be destroyed at this point
		assertEquals(1, this.game.actualState.battlefield().objects.size());

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		castAndResolveSpell(RelentlessAssault.class, "2RR");

		castAndResolveSpell(ElvishPiper.class, "3G");

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(PhagetheUntouchable.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);

		// The trigger for the opponent to lose the game should be on the stack
		java.util.List<GameObject> stack = this.game.actualState.stack().objects;
		assertEquals(1, stack.size());
		assertEquals(EventType.LOSE_GAME, stack.get(0).getModes().get(0).effects.get(0).type);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(ElvishPiper.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(ElvishPiper.Pipe.class));
		addMana("G");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Answer.YES);

		// Only the Elvish Piper should be left on the battlefield
		java.util.List<GameObject> battlefield = this.game.actualState.battlefield().objects;
		assertEquals(1, battlefield.size());
		assertEquals("Elvish Piper", battlefield.get(0).getName());

		// Two triggers should be on the stack for players losing
		stack = this.game.actualState.stack().objects;
		assertEquals(2, stack.size());
		assertEquals(EventType.LOSE_GAME, stack.get(0).getModes().get(0).effects.get(0).type);
		assertEquals(EventType.LOSE_GAME, stack.get(1).getModes().get(0).effects.get(0).type);

		pass();
		pass();
		assertEquals(this.winner, player(0));
	}

	@Test
	public void umezawasJitte()
	{
		addDeck(UmezawasJitte.class, BallLightning.class, SleeperAgent.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(PhagetheUntouchable.class, PhagetheUntouchable.class, ElvishPiper.class, ChaosCharm.class, ChaosCharm.class, RelentlessAssault.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(UmezawasJitte.class, "2");
		castAndResolveSpell(BallLightning.class, "RRR");
		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Equip.EquipAbility.class));
		// auto-target lightning
		addMana("2");
		donePlayingManaAbilities();
		pass();
		pass();

		castAndResolveSpell(SleeperAgent.class, "B");

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		// attack with ball lightning
		respondWith(pullChoice(BallLightning.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		respondWith(pullChoice(SleeperAgent.class));
		// auto-block ball lightning

		goToStep(Step.StepType.COMBAT_DAMAGE);
		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		divisions.put(this.game.actualState.battlefield().objects.get(0).ID, 3);
		divisions.put(player(1).ID, 3);
		divide(divisions);

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		assertEquals("Umezawa's Jitte", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).counters.size());
	}

	@Test
	public void contestedWarZone()
	{
		addDeck(Plains.class, Plains.class, Plains.class, MirranCrusader.class, MirranCrusader.class, BurstofSpeed.class, BazaarTrader.class, ContestedWarZone.class);
		addDeck(Plains.class, Plains.class, Plains.class, MirranCrusader.class, MirranCrusader.class, BurstofSpeed.class, BazaarTrader.class, ContestedWarZone.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(ContestedWarZone.class));
		int warZone = 0;

		castAndResolveSpell(BazaarTrader.class);
		warZone++;

		castAndResolveSpell(MirranCrusader.class);
		castAndResolveSpell(MirranCrusader.class);
		warZone += 2;

		castAndResolveSpell(BurstofSpeed.class);

		assertEquals("Contested War Zone", this.game.actualState.battlefield().objects.get(warZone).getName());
		assertEquals(player(0).ID, this.game.actualState.battlefield().objects.get(warZone).controllerID);

		respondWith(getAbilityAction(BazaarTrader.BazaarTraderAbility0.class));
		respondWith(getTarget(player(1)));
		respondWith(getTarget(ContestedWarZone.class));

		pass();
		pass();

		assertEquals("Contested War Zone", this.game.actualState.battlefield().objects.get(warZone).getName());
		assertEquals(player(1).ID, this.game.actualState.battlefield().objects.get(warZone).controllerID);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(MirranCrusader.class), pullChoice(MirranCrusader.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);

		assertEquals(PlayerInterface.ChoiceType.TRIGGERS, this.choiceType);
		assertEquals(2, this.choices.size());

		respondArbitrarily();

		assertEquals("Contested War Zone", this.game.actualState.battlefield().objects.get(warZone).getName());
		assertEquals(player(1).ID, this.game.actualState.battlefield().objects.get(warZone).controllerID);
		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(ContestedWarZone.ContestedWarZoneAbility0.class, this.game.actualState.stack().objects.get(0).getClass());
		assertEquals(ContestedWarZone.ContestedWarZoneAbility0.class, this.game.actualState.stack().objects.get(1).getClass());

		for(int i = 0; i < 4; ++i)
			pass();

		assertEquals("Contested War Zone", this.game.actualState.battlefield().objects.get(warZone).getName());
		assertEquals(player(0).ID, this.game.actualState.battlefield().objects.get(warZone).controllerID);

		goToStep(Step.StepType.PRECOMBAT_MAIN);

		castAndResolveSpell(MirranCrusader.class);
		castAndResolveSpell(MirranCrusader.class);
		warZone += 2;
		castAndResolveSpell(BurstofSpeed.class);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(MirranCrusader.class), pullChoice(MirranCrusader.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);

		assertEquals(PlayerInterface.ChoiceType.TRIGGERS, this.choiceType);
		assertEquals(2, this.choices.size());

		respondArbitrarily();

		assertEquals("Contested War Zone", this.game.actualState.battlefield().objects.get(warZone).getName());
		assertEquals(player(0).ID, this.game.actualState.battlefield().objects.get(warZone).controllerID);
		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(ContestedWarZone.ContestedWarZoneAbility0.class, this.game.actualState.stack().objects.get(0).getClass());
		assertEquals(ContestedWarZone.ContestedWarZoneAbility0.class, this.game.actualState.stack().objects.get(1).getClass());

		for(int i = 0; i < 4; ++i)
			pass();

		assertEquals("Contested War Zone", this.game.actualState.battlefield().objects.get(warZone).getName());
		assertEquals(player(1).ID, this.game.actualState.battlefield().objects.get(warZone).controllerID);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(MirranCrusader.class), pullChoice(MirranCrusader.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);

		assertEquals(PlayerInterface.ChoiceType.TRIGGERS, this.choiceType);
		assertEquals(2, this.choices.size());

		respondArbitrarily();

		assertEquals("Contested War Zone", this.game.actualState.battlefield().objects.get(warZone).getName());
		assertEquals(player(1).ID, this.game.actualState.battlefield().objects.get(warZone).controllerID);
		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(ContestedWarZone.ContestedWarZoneAbility0.class, this.game.actualState.stack().objects.get(0).getClass());
		assertEquals(ContestedWarZone.ContestedWarZoneAbility0.class, this.game.actualState.stack().objects.get(1).getClass());

		for(int i = 0; i < 4; ++i)
			pass();

		assertEquals("Contested War Zone", this.game.actualState.battlefield().objects.get(warZone).getName());
		assertEquals(player(0).ID, this.game.actualState.battlefield().objects.get(warZone).controllerID);
	}
}
