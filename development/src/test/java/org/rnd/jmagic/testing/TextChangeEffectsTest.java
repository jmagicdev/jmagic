package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class TextChangeEffectsTest extends JUnitTest
{
	@Test
	public void artificialEvolutionPrimalBeyond()
	{
		this.addDeck(PrimalBeyond.class, ArtificialEvolution.class, ArtificialEvolution.class, AkrasanSquire.class, ChangelingHero.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(PrimalBeyond.class));
		respondWith(Answer.YES);
		// auto-choose changeling

		// to make this relatively card-change-proof, i get an actual creature
		// type off of akrasan squire:
		SubType akrasanSquireType = AkrasanSquire.class.getAnnotation(SubTypes.class).value()[0];

		// auto-target primal beyond
		castAndResolveSpell(ArtificialEvolution.class, "U");
		respondWith(SubType.ELEMENTAL);
		respondWith(akrasanSquireType);

		// make a white squire mana:
		respondWith(getAbilityAction(PrimalBeyond.MakeElementalMana.class));
		respondWith(Color.WHITE);

		respondWith(getSpellAction(ArtificialEvolution.class));
		// auto-target primal beyond
		addMana("U");
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE));
		pass();
		pass();
		respondWith(akrasanSquireType);
		respondWith(SubType.SAND);

		// if this works, we're good
		respondWith(getSpellAction(AkrasanSquire.class));
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals("Akrasan Squire", this.game.actualState.battlefield().objects.get(0).getName());
	}

	@Test
	public void crystalSprayFromStackIntoPlay()
	{
		this.addDeck(CrystalSpray.class, CrystalSpray.class, BriarberryCohort.class, BriarberryCohort.class, RagingGoblin.class, RagingGoblin.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(BriarberryCohort.class));
		addMana("1U");
		donePlayingManaAbilities();

		// In response to Briarberry Cohort...
		respondWith(getSpellAction(CrystalSpray.class));
		// Auto-target the Briaryberry Cohort on the stack
		addMana("2U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Color.BLUE);
		respondWith(Color.RED);

		// Let the Briarberry Cohort resolve
		pass();
		pass();

		respondWith(getSpellAction(RagingGoblin.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Briarberry Cohort"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
	}

	@Test
	public void crystalSprayLuminesce()
	{
		this.addDeck(CrystalSpray.class, CrystalSpray.class, Luminesce.class, Luminesce.class, RagingGoblin.class, RagingGoblin.class, Shock.class, Shock.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(RagingGoblin.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Luminesce.class));
		addMana("W");
		donePlayingManaAbilities();

		respondWith(getSpellAction(CrystalSpray.class));
		respondWith(getTarget(Luminesce.class));
		addMana("2U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Color.RED);
		respondWith(Color.BLUE);

		// resolve Luminesce
		pass();
		pass();

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(player(1)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(18, player(1).lifeTotal);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(RagingGoblin.class));

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		assertEquals(17, player(1).lifeTotal);
	}

	@Test
	public void crystalSprayWearsOff()
	{
		this.addDeck(CrystalSpray.class, CrystalSpray.class, BriarberryCohort.class, BriarberryCohort.class, RagingGoblin.class, RagingGoblin.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(BriarberryCohort.class));
		addMana("1U");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(RagingGoblin.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(CrystalSpray.class));
		respondWith(getTarget(BriarberryCohort.class));
		addMana("2U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Color.BLUE);
		respondWith(Color.RED);

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Briarberry Cohort"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());

		goToPhase(Phase.PhaseType.ENDING);
		goToPhase(Phase.PhaseType.BEGINNING);
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
	}

	@Test
	public void mindBendActivatedAbilities()
	{
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, NimbusMaze.class, NimbusMaze.class, NimbusMaze.class, MindBend.class, MindBend.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(Mountain.class));
		goToPhase(Phase.PhaseType.ENDING);

		goToPhase(Phase.PhaseType.BEGINNING);
		goToStep(Step.StepType.CLEANUP);
		respondWith(pullChoice(Plains.class));

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(NimbusMaze.class));

		respondWith(getSpellAction(MindBend.class));
		respondWith(getTarget(NimbusMaze.class));
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(SubType.ISLAND);
		respondWith(SubType.MOUNTAIN);

		respondWith(getAbilityAction(NimbusMaze.TapForW.class));

	}

	@Test
	public void mindBendProtection()
	{
		this.addDeck(WhiteKnight.class, Pyroclasm.class, MindBend.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(WhiteKnight.class));
		addMana("WW");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(MindBend.class));
		// auto-target White Knight
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Color.BLACK);
		respondWith(Color.RED);

		respondWith(getSpellAction(Pyroclasm.class));
		addMana("1R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void mindBendStaticAbilities()
	{
		this.addDeck(MindBend.class, BriarberryCohort.class, MoggFanatic.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getSpellAction(BriarberryCohort.class));
		addMana("1U");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Briarberry Cohort"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getSpellAction(MoggFanatic.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Briarberry Cohort"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());

		respondWith(getSpellAction(MindBend.class));
		respondWith(getTarget(BriarberryCohort.class));
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Color.BLUE);
		respondWith(Color.RED);

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Briarberry Cohort"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getToughness());
	}

	@Test
	public void mindBendTargets()
	{
		this.addDeck(HateWeaver.class, MindBend.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(HateWeaver.class));
		addMana("1B");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(MindBend.class));
		// auto-target Hate Weaver
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Color.RED);
		respondWith(Color.BLACK);

		respondWith(getAbilityAction(HateWeaver.Weave.class));
		// auto-target Hate Weaver
		addMana("2");
		donePlayingManaAbilities();

		// power 2 before:
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Hate Weaver"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());

		// resolve ability:
		pass();
		pass();

		// power 3 after:
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Hate Weaver"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
	}

	@Test
	public void mindBendTriggeredAbilities()
	{
		this.addDeck(StampedingWildebeests.class, StampedingWildebeests.class, StampedingWildebeests.class, StampedingWildebeests.class, MindBend.class, MindBend.class, MindBend.class, MindBend.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getSpellAction(StampedingWildebeests.class));
		addMana("2GG");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(MindBend.class));
		// auto-target Stampeding Wildebeests
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Color.GREEN);
		respondWith(Color.BLUE);

		goToPhase(Phase.PhaseType.BEGINNING);
		goToStep(Step.StepType.CLEANUP);
		respondWith(pullChoice(Plains.class));

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stampeding Wildebeests"));
	}

	@Test
	public void mindBendVoiceOfAllShouldntDoAnything()
	{
		this.addDeck(MindBend.class, VoiceofAll.class, Pyroclasm.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(VoiceofAll.class));
		addMana("2WW");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Color.RED);

		respondWith(getSpellAction(MindBend.class));
		// auto-target voice of all
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Color.RED);
		respondWith(Color.BLACK);

		respondWith(getSpellAction(Pyroclasm.class));
		addMana("1R");
		donePlayingManaAbilities();
		pass();
		pass();

		// The Voice of All's protection ability references "the chosen color",
		// which is immune to text changing effects
		assertEquals(1, this.game.actualState.battlefield().objects.size());
	}
}
