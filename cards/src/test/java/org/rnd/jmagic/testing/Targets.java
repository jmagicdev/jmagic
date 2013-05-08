package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class Targets extends JUnitTest
{
	@Test
	public void chooseNoTargets()
	{
		this.addDeck(Gravepurge.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(Gravepurge.class);
		// Gravepurge should NOT be countered; it should resolve and cause
		// player 0 to draw a card
		// since player 0 has no more cards player 1 should win
		assertEquals(player(1), this.winner);
	}

	@Test
	public void glaringSpotlight()
	{
		// Creatures your opponents control with hexproof can be the targets of
		// spells and abilities you control as though they didn't have hexproof.

		this.addDeck(GlaringSpotlight.class, GlaringSpotlight.class, GlaringSpotlight.class, InvisibleStalker.class, InvisibleStalker.class, InvisibleStalker.class, LightningBolt.class, LightningBolt.class, LightningBolt.class);
		this.addDeck(GlaringSpotlight.class, GlaringSpotlight.class, GlaringSpotlight.class, InvisibleStalker.class, InvisibleStalker.class, InvisibleStalker.class, LightningBolt.class, LightningBolt.class, LightningBolt.class);
		this.addDeck(GlaringSpotlight.class, GlaringSpotlight.class, GlaringSpotlight.class, InvisibleStalker.class, InvisibleStalker.class, InvisibleStalker.class, LightningBolt.class, LightningBolt.class, LightningBolt.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();
		keep();

		// player 0's first turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(InvisibleStalker.class);

		// cast lightning bolt, check that 4 targets are legal. (the three
		// players and the creature)
		respondWith(getSpellAction(LightningBolt.class));
		assertEquals(PlayerInterface.ChoiceType.TARGETS, this.choiceType);
		assertEquals(4, this.choices.size());
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();
		pass();

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		// player 1's first turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// cast lightning bolt, check that only 3 targets are legal. (the three
		// players)
		respondWith(getSpellAction(LightningBolt.class));
		assertEquals(PlayerInterface.ChoiceType.TARGETS, this.choiceType);
		assertEquals(3, this.choices.size());
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();
		pass();

		castAndResolveSpell(GlaringSpotlight.class);

		// cast lightning bolt, check that 4 targets are legal. (the three
		// players and the creature)
		respondWith(getSpellAction(LightningBolt.class));
		assertEquals(PlayerInterface.ChoiceType.TARGETS, this.choiceType);
		assertEquals(4, this.choices.size());
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();
		pass();

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		// player 2's first turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// cast lightning bolt, check that only 3 targets are legal. (the three
		// players)
		respondWith(getSpellAction(LightningBolt.class));
		assertEquals(PlayerInterface.ChoiceType.TARGETS, this.choiceType);
		assertEquals(3, this.choices.size());
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(8, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);
		assertEquals(20, player(2).lifeTotal);
	}

	@Test
	public void karplusanStrider()
	{
		// (untargetable by blue or black spells)

		this.addDeck(KarplusanStrider.class, KarplusanStrider.class, Cancel.class, Terror.class, MoggFanatic.class, LightningBolt.class, Evacuation.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(KarplusanStrider.class));
		addMana("3G");
		donePlayingManaAbilities();

		// Karplusan Strider should be able to be countered:
		respondWith(getSpellAction(Cancel.class));
		// auto-target strider
		addMana("1UU");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());

		respondWith(getSpellAction(KarplusanStrider.class));
		addMana("3G");
		donePlayingManaAbilities();
		pass();
		pass();

		// No legal targets for Terror:
		respondWith(getSpellAction(Terror.class));
		// game state reverts

		respondWith(getSpellAction(MoggFanatic.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Terror.class));
		// auto-choose fanatic
		addMana("1B");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Karplusan Strider"));

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(KarplusanStrider.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getDamage());

		respondWith(getSpellAction(Evacuation.class));
		addMana("3UU");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());

	}

	@Test
	public void playerHasShroud()
	{

		this.addDeck(TrueBeliever.class, TrueBeliever.class, TrueBeliever.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// can i bolt myself? answer should be yes:
		respondWith(getSpellAction(LightningBolt.class));
		assertEquals(2, this.choices.size());
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();
		assertEquals(17, player(0).lifeTotal);

		respondWith(getSpellAction(TrueBeliever.class));
		addMana("WW");
		donePlayingManaAbilities();
		pass();
		pass();

		// can i bolt myself? answer should be no:
		respondWith(getSpellAction(LightningBolt.class));
		assertEquals(2, this.choices.size());
		respondWith(getTarget(TrueBeliever.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();
		assertEquals(0, this.game.actualState.battlefield().objects.size());

		// can i bolt myself? answer should be yes:
		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();
		assertEquals(14, player(0).lifeTotal);

		// now try two believers and see if they get two instances of shroud
		respondWith(getSpellAction(TrueBeliever.class));
		addMana("WW");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(TrueBeliever.class));
		addMana("WW");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(2, getActual(player(0)).getStaticAbilities().size());
	}

	@Test
	public void shroud()
	{
		this.addDeck(PincherBeetles.class, Clone.class, WrathofGod.class, Terror.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(PincherBeetles.class));
		addMana("2G");
		donePlayingManaAbilities();
		pass();
		pass();

		// shroud means this should fail:
		respondWith(getSpellAction(Terror.class));
		// game state reverts

		respondWith(getSpellAction(Clone.class));
		addMana("3U");
		donePlayingManaAbilities();
		pass();
		pass();
		// i'd like to copy a creature:
		respondWith(Answer.YES);
		// as clone comes into play, auto-choose pincher beetles
		// this effect doesn't target so it should work fine

		// this should fail again
		respondWith(getSpellAction(Terror.class));
		// game state reverts

		respondWith(getSpellAction(WrathofGod.class));
		addMana("2WW");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(3, getGraveyard(0).objects.size());
	}

	@Test
	public void trollShroud()
	{
		this.addDeck(TrollAscetic.class, GiantGrowth.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(LightningBolt.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(TrollAscetic.class));
		addMana("1GG");
		donePlayingManaAbilities();
		pass();
		pass();

		// controller of troll should be able to target it:
		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// auto-choose troll
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(TrollAscetic.class));

		pass();
		// opponent should not be able to target the troll:
		respondWith(getSpellAction(LightningBolt.class));
		// each player:
		assertEquals(2, this.choices.size());
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();
		assertEquals(17, player(0).lifeTotal);

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		// controller of troll should be able to target it:
		respondWith(getSpellAction(GiantGrowth.class));
		// auto-target Troll Ascetic
		addMana("G");
		donePlayingManaAbilities();
		pass();
		pass();
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Troll Ascetic"));
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(5, this.game.actualState.battlefield().objects.get(0).getToughness());

		// skip to after combat damage:
		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		assertEquals(14, player(1).lifeTotal);
	}
}
