package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;

public class CardsWithCustomEvents extends JUnitTest
{
	@Test
	public void abundance()
	{
		this.addDeck(// Remaining in library
		Swamp.class,
		// Fourth 'Draw'
		Inspiration.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class,
		// Third 'Draw'
		Abundance.class, Plains.class,
		// Second 'Draw'
		Inspiration.class,
		// First 'Draw'
		Plains.class,
		// Starting hand
		Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Abundance.class, Inspiration.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Abundance.class));
		addMana("2GG");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Inspiration.class));
		respondWith(getTarget(player(0)));
		addMana("3U");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(2, this.choices.size());
		assertTrue(this.choices.contains(org.rnd.jmagic.engine.Answer.YES));
		assertTrue(this.choices.contains(org.rnd.jmagic.engine.Answer.NO));

		respondWith(Answer.YES);

		assertEquals(2, this.choices.size());
		assertTrue(this.choices.contains(org.rnd.jmagic.engine.Answer.LAND));
		assertTrue(this.choices.contains(org.rnd.jmagic.engine.Answer.NONLAND));

		// Picks up a plains
		respondWith(Answer.LAND);

		// Picks up an inspiration
		respondWith(Answer.YES);
		respondWith(Answer.NONLAND);

		respondWith(getSpellAction(Inspiration.class));
		respondWith(getTarget(player(0)));
		addMana("3U");
		donePlayingManaAbilities();
		pass();
		pass();

		// Shuffles a plains, picks up an abundance
		respondWith(Answer.YES);
		respondWith(Answer.NONLAND);

		// Shuffles 9 forests, picks up an inspiration
		respondWith(Answer.YES);
		respondWith(Answer.NONLAND);

		// Order the 9 forests
		respondWith(pullChoice(Forest.class), pullChoice(Forest.class), pullChoice(Forest.class), pullChoice(Forest.class), pullChoice(Forest.class), pullChoice(Forest.class), pullChoice(Forest.class), pullChoice(Forest.class), pullChoice(Forest.class));

		respondWith(getLandAction(Plains.class));

		assertEquals(11, getLibrary(0).objects.size());
		assertTrue(getLibrary(0).objects.get(0).getName().equals("Swamp"));
		assertTrue(getLibrary(0).objects.get(1).getName().equals("Plains"));
		assertTrue(getLibrary(0).objects.get(2).getName().equals("Forest"));
		assertTrue(getLibrary(0).objects.get(3).getName().equals("Forest"));
		assertTrue(getLibrary(0).objects.get(4).getName().equals("Forest"));
		assertTrue(getLibrary(0).objects.get(5).getName().equals("Forest"));
		assertTrue(getLibrary(0).objects.get(6).getName().equals("Forest"));
		assertTrue(getLibrary(0).objects.get(7).getName().equals("Forest"));
		assertTrue(getLibrary(0).objects.get(8).getName().equals("Forest"));
		assertTrue(getLibrary(0).objects.get(9).getName().equals("Forest"));
		assertTrue(getLibrary(0).objects.get(10).getName().equals("Forest"));

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Plains"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Abundance"));

		java.util.List<GameObject> objects = getHand(0).objects;
		assertEquals(7, objects.size());
		assertTrue(objects.get(0).getName().equals("Inspiration"));
		assertTrue(objects.get(1).getName().equals("Abundance"));
		assertTrue(objects.get(2).getName().equals("Plains"));
		assertTrue(objects.get(3).getName().equals("Plains"));
		assertTrue(objects.get(4).getName().equals("Plains"));
		assertTrue(objects.get(5).getName().equals("Plains"));
		assertTrue(objects.get(6).getName().equals("Plains"));
	}

	@Test
	public void capriciousEfreet_TargetChoices()
	{
		addDeck(CapriciousEfreet.class, SleeperAgent.class, SleeperAgent.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(SleeperAgent.class));
		addMana("B");
		donePlayingManaAbilities();
		pass();
		pass();

		// auto-target opponent
		pass();
		pass();

		respondWith(getSpellAction(SleeperAgent.class));
		addMana("B");
		donePlayingManaAbilities();
		pass();
		pass();

		// auto-target opponent
		pass();
		pass();

		respondWith(getSpellAction(CapriciousEfreet.class));
		addMana("4RR");
		donePlayingManaAbilities();
		pass();
		pass();

		goToPhase(Phase.PhaseType.ENDING);

		// opponent's turn:
		goToStep(Step.StepType.UPKEEP);
		respondArbitrarily(); // sleeper agent triggers
		goToStep(Step.StepType.CLEANUP);
		respondWith(pullChoice(Plains.class)); // discard

		goToStep(Step.StepType.UPKEEP);

		// capricious trigger should have two valid targets:
		assertEquals(2, this.choices.size());
	}

	@Test
	public void gomazoa()
	{
		addDeck(Gomazoa.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(Gomazoa.class, "2U");
		this.game.physicalState.battlefield().objects.get(0).addAbility(new org.rnd.jmagic.abilities.keywords.Haste(this.game.physicalState));
		goToPhase(Phase.PhaseType.ENDING);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(RagingGoblin.class, "R");

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(RagingGoblin.class));
		goToStep(Step.StepType.DECLARE_BLOCKERS);
		respondWith(pullChoice(Gomazoa.class));

		pass();
		respondWith(getAbilityAction(Gomazoa.ApparentlyJellyfishMakeThingsDisappear.class));
		pass();
		pass();

		assertEquals(1, getLibrary(0).objects.size());
		assertEquals(1, getLibrary(1).objects.size());
	}

	@Test
	public void hauntingEchoes()
	{
		addDeck(HauntingEchoes.class, TomeScour.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(
		// Searched by Haunting Echoes
		Forest.class, Plains.class, RuneclawBear.class, TomeScour.class, MoggFanatic.class,

		// Milled
		Forest.class, Plains.class, GrizzlyBears.class, RuneclawBear.class, TomeScour.class,

		// Hand
		RuneclawBear.class, TomeScour.class, MoggFanatic.class, GrizzlyBears.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(TomeScour.class));
		respondWith(getTarget(player(1)));
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(HauntingEchoes.class));
		respondWith(getTarget(player(1)));
		addMana("3BB");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(2, this.choices.size());
		SanitizedGameObject tomescour = pullChoice(TomeScour.class);
		SanitizedGameObject runeclaw = pullChoice(RuneclawBear.class);
		assertTrue(runeclaw != null);
		assertTrue(tomescour != null);
		assertEquals(0, this.choices.size());
		respondWith(runeclaw);

		assertEquals(4, this.game.actualState.exileZone().objects.size());

		assertEquals(2, getGraveyard(1).objects.size());
		assertEquals(4, getLibrary(1).objects.size());
		assertEquals(7, getHand(1).objects.size());

		assertEquals(2, getGraveyard(0).objects.size());
		assertEquals(0, getLibrary(0).objects.size());
		assertEquals(5, getHand(0).objects.size());
	}

	@Test
	public void hiveMind()
	{
		addDeck(HiveMind.class, Twincast.class, LightningBolt.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(HiveMind.class));
		addMana("5U");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(player(1)));
		addMana("R");
		donePlayingManaAbilities();

		// stack:
		// Hive Mind trigger
		// Player 0's Lightning Bolt targeting Player 1

		pass();
		pass();
		respondWith(Answer.YES);
		// auto-target player 0

		// stack:
		// Player 1's copy of Lightning Bolt targeting Player 0
		// Player 0's Lightning Bolt targeting Player 1

		// player 0 has priority:
		respondWith(getSpellAction(Twincast.class));
		respondWith(getTarget(this.game.actualState.stack().objects.get(2)));
		addMana("UU");
		donePlayingManaAbilities();

		// stack:
		// Hive Mind trigger
		// Player 0's Twincast targeting Lightning Bolt targeting Player 1
		// Player 1's copy of Lightning Bolt targeting Player 0
		// Player 0's Lightning Bolt targeting Player 1

		pass();
		pass();
		respondWith(Answer.NO);

		// Player 1's Twincast targeting Lightning Bolt targeting Player 1
		// Player 0's Twincast targeting Lightning Bolt targeting Player 1
		// Player 1's copy of Lightning Bolt targeting Player 0
		// Player 0's Lightning Bolt targeting Player 1

		pass();
		pass();
		respondWith(Answer.YES);
		// auto-target player 0

		// Player 1's copy of Lightning Bolt targeting Player 0
		// Player 0's Twincast targeting Lightning Bolt targeting Player 1
		// Player 1's copy of Lightning Bolt targeting Player 0
		// Player 0's Lightning Bolt targeting Player 1

		assertEquals("Lightning Bolt", this.game.actualState.stack().objects.get(0).getName());
		pass();
		pass();
		assertEquals(17, player(0).lifeTotal);

		// Player 0's Twincast targeting Lightning Bolt targeting Player 1
		// Player 1's copy of Lightning Bolt targeting Player 0
		// Player 0's Lightning Bolt targeting Player 1

		pass();
		pass();
		respondWith(Answer.NO);

		// Player 0's copy of Lightning Bolt targeting Player 1
		// Player 1's copy of Lightning Bolt targeting Player 0
		// Player 0's Lightning Bolt targeting Player 1

		pass();
		pass();
		assertEquals(17, player(1).lifeTotal);
		pass();
		pass();
		assertEquals(14, player(0).lifeTotal);
		pass();
		pass();
		assertEquals(14, player(0).lifeTotal);
	}

	@Test
	public void masterOfTheWildHunt()
	{
		addDeck(MasteroftheWildHunt.class, DarksteelColossus.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(MasteroftheWildHunt.class, "2GG");
		castAndResolveSpell(DarksteelColossus.class, "(11)");

		// force master to trigger now
		this.game.physicalState.currentPhase().steps.add(new Step(this.game.physicalState.players.get(0), Step.StepType.UPKEEP));
		pass();
		pass();
		// ... i'm a dirty fucking cheater, i know :-P -RulesGuru
		// (OH SNAP, YOU WERE EXPECTING "-Kamikaze" THERE, WEREN'T YOU?!?!)
		// I cleaned this up so it's not quite so dirty, though it's still
		// fucking cheating. - CommsGuy
		// (OH SNAP, YOU WERE EXPECTING "-RulesGuru" THERE, WEREN'T YOU?!?!)

		// ... as long as i'm cheating ...
		// sharpie master's trigger to make 20 wolf tokens
		GameObject stackedTrigger = this.game.physicalState.stack().objects.get(0);
		assertEquals(MasteroftheWildHunt.MakeWolf.class, stackedTrigger.getClass());
		stackedTrigger.getModes().get(0).effects.get(0).parameters.put(EventType.Parameter.NUMBER, org.rnd.jmagic.engine.generators.Identity.instance(20));
		// and just give the fucker haste directly
		this.game.physicalState.battlefield().objects.get(1).addAbility(new org.rnd.jmagic.abilities.keywords.Haste(this.game.physicalState));
		pass();
		pass();

		// master + dsc + 20 tokens
		assertEquals(1 + 1 + 20, this.game.actualState.battlefield().objects.size());

		respondWith(getAbilityAction(MasteroftheWildHunt.Hunt.class));
		respondWith(getTarget(DarksteelColossus.class));
		pass();
		pass();

		// before assigning the colossus' damage, it should have taken damage
		// from all the wolves
		assertEquals("Darksteel Colossus", this.game.actualState.battlefield().objects.get(20).getName());
		assertEquals(2 * 20, this.game.actualState.battlefield().objects.get(20).getDamage());

		java.util.Map<Integer, Integer> damage = new java.util.HashMap<Integer, Integer>();
		damage.put(this.game.actualState.battlefield().objects.get(0).ID, 2);
		damage.put(this.game.actualState.battlefield().objects.get(1).ID, 2);
		damage.put(this.game.actualState.battlefield().objects.get(2).ID, 2);
		damage.put(this.game.actualState.battlefield().objects.get(3).ID, 2);
		damage.put(this.game.actualState.battlefield().objects.get(4).ID, 2);
		damage.put(this.game.actualState.battlefield().objects.get(5).ID, 1);
		damage.put(this.game.actualState.battlefield().objects.get(6).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(7).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(8).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(9).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(10).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(11).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(12).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(13).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(14).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(15).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(16).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(17).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(18).ID, 0);
		damage.put(this.game.actualState.battlefield().objects.get(19).ID, 0);
		divide(damage);

		// master + dsc + 20 tokens - 5 dead tokens
		assertEquals(1 + 1 + 20 - 5, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void riseFromTheGrave()
	{
		this.addDeck(
		// top five
		MoggFanatic.class, Plains.class, Plains.class, Plains.class, Plains.class,
		// opening seven
		TomeScour.class, RisefromtheGrave.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		startGame(GameType.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(TomeScour.class));
		respondWith(getTarget(player(0)));
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();

		castAndResolveSpell(RisefromtheGrave.class, "4B"); // autotarget

		assertEquals("Mogg Fanatic", this.game.actualState.battlefield().objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getColors().contains(Color.RED));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getColors().contains(Color.BLACK));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getSubTypes().contains(SubType.GOBLIN));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getSubTypes().contains(SubType.ZOMBIE));
	}

	@Test
	public void sphinxAmbassador()
	{
		this.addDeck(SphinxAmbassador.class, BurstofSpeed.class, RelentlessAssault.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(SphinxAmbassador.class, "5UU");
		castAndResolveSpell(BurstofSpeed.class, "R");

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(SphinxAmbassador.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);
		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();
		// they name the card we search for:
		respondWith(pullChoice(MoggFanatic.class));
		respondWith(pullChoice("Mogg Fanatic"));
		assertEquals("Sphinx Ambassador", this.game.actualState.battlefield().objects.get(0).getName());

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		castAndResolveSpell(RelentlessAssault.class, "2RR");

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(SphinxAmbassador.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);
		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();
		// they don't name the card we search for:
		respondWith(pullChoice(MoggFanatic.class));
		respondWith(pullChoice("Raging Goblin"));
		respondWith(Answer.YES); // oddly enough, it says "you may..."
		assertEquals("Mogg Fanatic", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(player(0).ID, this.game.actualState.battlefield().objects.get(0).getController(this.game.actualState).ID);
	}

	@Test
	public void tellingTime_OneCard()
	{
		addDeck(
		// look at with Telling Time:
		RushwoodDryad.class,
		// opening seven:
		TellingTime.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		startGame(GameType.STACKED);
		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(TellingTime.class));
		addMana("1U");
		donePlayingManaAbilities();
		pass();
		pass();

		// put dryad in hand (autochoose!)

		assertEquals("Rushwood Dryad", getHand(0).objects.get(0).getName());
	}

	@Test
	public void tellingTime_ThreeOrMoreCards()
	{
		addDeck(
		// bottom cards:
		Plains.class, Plains.class,
		// look at with Telling Time:
		BallistaSquad.class, RushwoodDryad.class, PrimalBeyond.class,
		// opening seven:
		TellingTime.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		startGame(GameType.STACKED);
		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(TellingTime.class));
		addMana("1U");
		donePlayingManaAbilities();
		pass();
		pass();

		// put dryad in hand
		respondWith(pullChoice(RushwoodDryad.class));
		// put primal beyond on top
		respondWith(pullChoice(PrimalBeyond.class));
		// squad goes to bottom automatically

		assertEquals("Rushwood Dryad", getHand(0).objects.get(0).getName());
		assertEquals("Primal Beyond", getLibrary(0).objects.get(0).getName());
		assertEquals("Ballista Squad", getLibrary(0).objects.get(3).getName());
	}

	@Test
	public void tellingTime_TwoCards()
	{
		addDeck(
		// look at with Telling Time:
		RushwoodDryad.class, PrimalBeyond.class,
		// opening seven:
		TellingTime.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		startGame(GameType.STACKED);
		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(TellingTime.class));
		addMana("1U");
		donePlayingManaAbilities();
		pass();
		pass();

		// put dryad in hand
		respondWith(pullChoice(RushwoodDryad.class));
		// put primal beyond on top (autochoose!)

		assertEquals("Rushwood Dryad", getHand(0).objects.get(0).getName());
		assertEquals("Primal Beyond", getLibrary(0).objects.get(0).getName());
	}

	@Test
	public void xathridDemon()
	{
		this.addDeck(XathridDemon.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(XathridDemon.class, XathridDemon.class, Sprout.class, Sprout.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// During player 0's turn, player 1 casts sprout and xathrid demon (with
		// dynamically added Flash), then the turn ends
		pass();

		respondWith(getSpellAction(Sprout.class));
		addMana("G");
		donePlayingManaAbilities();

		// Find Xathrid and give him Flash
		{
			GameObject Xathrid = null;

			for(GameObject card: getHand(1).objects)
				if(card instanceof XathridDemon)
				{
					Xathrid = card;
					break;
				}
			if(Xathrid == null)
				fail("Unable to find Xathrid Demon in hand.");

			Xathrid.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(Xathrid.state));
		}

		// Resolve Sprout
		pass();
		pass();

		// Both players cast Xathrid
		respondWith(getSpellAction(XathridDemon.class));
		addMana("3BBB");
		donePlayingManaAbilities();
		pass();

		respondWith(getSpellAction(XathridDemon.class));
		addMana("3BBB");
		donePlayingManaAbilities();
		pass();
		pass();

		pass();
		pass();

		goToStep(Step.StepType.UPKEEP);

		// resolve Xathrid's ability, saccing a saproling and player 0 loses 1
		// life
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("Xathrid Demon", this.game.actualState.battlefield().objects.get(0).getName());
		assertFalse(this.game.actualState.battlefield().objects.get(0).isTapped());
		assertEquals("Xathrid Demon", this.game.actualState.battlefield().objects.get(1).getName());
		assertFalse(this.game.actualState.battlefield().objects.get(1).isTapped());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(20, player(0).lifeTotal);

		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Xathrid Demon", this.game.actualState.battlefield().objects.get(0).getName());
		assertFalse(this.game.actualState.battlefield().objects.get(0).isTapped());
		assertEquals("Xathrid Demon", this.game.actualState.battlefield().objects.get(1).getName());
		assertFalse(this.game.actualState.battlefield().objects.get(1).isTapped());
		assertEquals(19, player(0).lifeTotal);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		goToStep(Step.StepType.UPKEEP);

		// resolve Xathrid's ability while unable to sacrifice something else,
		// so tap xathrid and lose 7 life
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Xathrid Demon", this.game.actualState.battlefield().objects.get(0).getName());
		assertFalse(this.game.actualState.battlefield().objects.get(0).isTapped());
		assertEquals("Xathrid Demon", this.game.actualState.battlefield().objects.get(1).getName());
		assertFalse(this.game.actualState.battlefield().objects.get(1).isTapped());
		assertEquals(19, player(0).lifeTotal);

		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Xathrid Demon", this.game.actualState.battlefield().objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());
		assertEquals("Xathrid Demon", this.game.actualState.battlefield().objects.get(1).getName());
		assertFalse(this.game.actualState.battlefield().objects.get(1).isTapped());
		assertEquals(12, player(0).lifeTotal);
	}
}
