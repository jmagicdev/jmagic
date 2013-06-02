package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class ReplacementEffectsTest extends JUnitTest
{
	@Test
	public void dredgeWithThoughtReflection()
	{
		this.addDeck(Swamp.class, Swamp.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ThoughtReflection.class, GraveShellScarab.class);
		this.addDeck(Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		// upkeep
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
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);

		respondWith(getSpellAction(ThoughtReflection.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE));
		pass();
		pass();

		respondWith(getSpellAction(GraveShellScarab.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.GREEN, Color.BLACK, Color.BLUE, Color.BLUE));
		pass();
		pass();

		respondWith(getAbilityAction(GraveShellScarab.SacDraw.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK));
		pass();
		pass();

		respondWith(getChoiceByToString("If you would draw a card, draw two cards instead"));

		// now we're being asked whether we want to dredge
		// don't do it the first time
		respondWith(Answer.NO);

		// dredge
		respondWith(Answer.YES);

		assertTrue(getGraveyard(0).objects.get(0).getName().equals("Swamp"));
	}

	@Test
	public void nullRedirection()
	{
		this.addDeck(KjeldoranRoyalGuard.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Sprout.class, Sprout.class, DoomBlade.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(KjeldoranRoyalGuard.class, "3WW");

		pass();

		castAndResolveSpell(Sprout.class, "G");

		goToPhase(Phase.PhaseType.BEGINNING);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(Token.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		respondWith();

		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(KjeldoranRoyalGuard.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		pass();

		respondWith(getAbilityAction(KjeldoranRoyalGuard.RoyalShield.class));
		pass();
		pass();

		castAndResolveSpell(DoomBlade.class, "1B", KjeldoranRoyalGuard.class);

		goToStep(Step.StepType.COMBAT_DAMAGE);

		assertEquals(19, player(0).lifeTotal);
	}

	@Test
	public void thoughtReflection()
	{
		this.addDeck(ThoughtReflection.class, ThoughtReflection.class, ThoughtReflection.class, ThoughtReflection.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// player 0's first upkeep
		pass();
		pass();

		// player 0's first precombat main phase
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(ThoughtReflection.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE));

		// resolve Thought Reflection
		pass();
		pass();

		// finish main phase
		pass();
		pass();

		// player 0's first beginning of combat step
		pass();
		pass();

		// player 0's first declare attackers step
		pass();
		pass();

		// player 0's first end of combat step
		pass();
		pass();

		// player 0's first postcombat main phase
		pass();
		pass();

		// player 0's first end of turn step
		pass();
		pass();

		// player 1's upkeep
		pass();
		pass();

		// player 1's draw step
		// if pl 1 hasn't lost at this point we're ok
		pass();
		pass();

		// player 1's main phase
		// play a swamp to go to 7 cards
		respondWith(getLandAction(Swamp.class));
		pass();
		pass();

		// player 1's first beginning of combat step
		pass();
		pass();

		// player 1's first declare attackers step
		pass();
		pass();

		// player 1's first end of combat step
		pass();
		pass();

		// player 1's first postcombat main phase
		pass();
		pass();

		// player 1's first end of turn step
		pass();
		pass();

		// player 0's second upkeep
		pass();
		pass();

		// player 0's draw step -- library should be empty
		assertEquals(0, getLibrary(0).objects.size());

	}

	@Test
	public void undeadAlchemist()
	{
		this.addDeck(UndeadAlchemist.class, BlackcleaveGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(UndeadAlchemist.class);
		castAndResolveSpell(BlackcleaveGoblin.class);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(BlackcleaveGoblin.class));

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		assertEquals(20, player(1).lifeTotal);
		assertEquals(2, getGraveyard(1).objects.size());
	}

	/*
	@Test
	public void doublingSeason_Tokens()
	{
		this.addDeck(DoublingSeason.class, Sprout.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(DoublingSeason.class, "4G");

		castAndResolveSpell(Sprout.class, "G");

		assertEquals(3, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void doublingSeason_AddsCounters()
	{
		this.addDeck(DoublingSeason.class, IronshellBeetle.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(DoublingSeason.class, "4G");

		castAndResolveSpell(IronshellBeetle.class, "1G");

		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).power);
		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).power);
	}

	@Test
	public void doublingSeason_EnterWithCounters()
	{
		this.addDeck(DoublingSeason.class, ChandraNalaar.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(DoublingSeason.class, "4G");

		castAndResolveSpell(ChandraNalaar.class, "3RR");

		assertEquals(12, this.game.actualState.battlefield().objects.get(0).counters.size());
	}
	*/
}
