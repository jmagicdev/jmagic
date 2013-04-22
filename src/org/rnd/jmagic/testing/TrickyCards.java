package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;

public class TrickyCards extends JUnitTest
{
	@Test
	public void academyResearchers()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, SeasClaim.class, UnholyStrength.class, AcademyResearchers.class, AcademyResearchers.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Plains.class));

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(AcademyResearchers.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Make sure the trigger is on the stack, then resolve it
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();

		this.respondWith(Answer.YES);
		this.respondWith(this.pullChoice(SeasClaim.class));

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Academy Researchers"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getAttachments().size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(3, this.getHand(0).objects.size());

		this.respondWith(this.getSpellAction(AcademyResearchers.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.BLUE, Color.BLUE));
		this.pass();
		this.pass();

		// Make sure the trigger is on the stack, then resolve it
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();

		this.respondWith(Answer.YES);
		this.respondWith(this.pullChoice(UnholyStrength.class));

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Unholy Strength"));

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Academy Researchers"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getAttachments().size());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Academy Researchers"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getAttachments().size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getToughness());

		assertEquals(1, this.getHand(0).objects.size());

		this.respondWith(this.getSpellAction(SeasClaim.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(4).getName().equals("Plains"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(4).getSubTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(4).getSubTypes().contains(org.rnd.jmagic.engine.SubType.ISLAND));
	}

	@Test
	public void agonizingMemories()
	{
		this.addDeck(AgonizingMemories.class, AgonizingMemories.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Forest.class, Swamp.class, Swamp.class, Island.class, Mountain.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(AgonizingMemories.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("2BB");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Choose the two cards
		this.respondWith(this.pullChoice(Swamp.class), this.pullChoice(Forest.class));

		// Order them
		this.respondWith(this.pullChoice(Forest.class), this.pullChoice(Swamp.class));

		{
			Zone library = getLibrary(1);
			assertEquals(5, this.getHand(1).objects.size());
			assertEquals(2, library.objects.size());
			assertEquals(Swamp.class, library.objects.get(0).getClass());
			assertEquals(Forest.class, library.objects.get(1).getClass());
		}

		this.respondWith(this.getSpellAction(AgonizingMemories.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("2BB");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Choose the two cards
		this.respondWith(this.pullChoice(Plains.class), this.pullChoice(Plains.class));

		// Order them
		this.respondArbitrarily();

		{
			Zone library = getLibrary(1);
			assertEquals(3, this.getHand(1).objects.size());
			assertEquals(4, library.objects.size());
			assertEquals(Plains.class, library.objects.get(0).getClass());
			assertEquals(Plains.class, library.objects.get(1).getClass());
			assertEquals(Swamp.class, library.objects.get(2).getClass());
			assertEquals(Forest.class, library.objects.get(3).getClass());
		}
	}

	@Test
	public void animateDead()
	{
		this.addDeck(AnimateDead.class, SleeperAgent.class, Disenchant.class, CabalTherapy.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(CabalTherapy.class, "B", player(0));
		respondWith("Sleeper Agent");

		this.castAndResolveSpell(AnimateDead.class);

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve Animate Dead's trigger to animate the Sleeper Agent
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve Sleeper Agent's ETB trigger
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());

		// Disenchant the Animate Dead
		castAndResolveSpell(Disenchant.class);

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve Animate Dead's die trigger
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
	}

	/**
	 * At one point, triggered abilities with both enters-the-battlefield and
	 * leaves-the-battlefield trigger conditions were crashing the game when
	 * determining whether they should trigger when other objects entered the
	 * battlefield.
	 */
	@Test
	public void avenRiftwatcher()
	{
		this.addDeck(AvenRiftwatcher.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(AvenRiftwatcher.class, "2W");

		// resolve 2 life trigger:
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Plains.class));
	}

	@Test
	public void backFromTheBrink()
	{
		this.addDeck(BackfromtheBrink.class, RagingGoblin.class, OnewithNothing.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(BackfromtheBrink.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(BackfromtheBrink.class);

		respondWith(getAbilityAction(BackfromtheBrink.BackfromtheBrinkAbility0.class));
		assertEquals(0, this.game.actualState.stack().objects.size());

		castAndResolveSpell(OnewithNothing.class);
		respondWith(getAbilityAction(BackfromtheBrink.BackfromtheBrinkAbility0.class));
		assertEquals(1, this.game.actualState.stack().objects.size());
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(0).getName());
	}

	@Test
	public void blazingTorch()
	{
		this.addDeck(BlazingTorch.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(BlazingTorch.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(RagingGoblin.class, "R");
		this.castAndResolveSpell(BlazingTorch.class, "1");

		this.respondWith(this.getAbilityAction(org.rnd.jmagic.abilities.keywords.Equip.EquipAbility.class));
		// auto-target goblin
		this.addMana("1");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(BlazingTorch.ThrowTorch.class));
		this.respondWith(this.getTarget(RagingGoblin.class));
		assertEquals(1, this.getGraveyard(0).objects.size());
		assertEquals("Blazing Torch", this.getGraveyard(0).objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());

		this.pass();
		this.pass();
		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void boseiju()
	{
		this.addDeck(BoseijuWhoSheltersAll.class, Counterspell.class, StoneRain.class, Twiddle.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(BoseijuWhoSheltersAll.class));
		castAndResolveSpell(Twiddle.class);
		respondWith(Answer.YES);

		respondWith(getSpellAction(StoneRain.class));
		respondWith(getAbilityAction(BoseijuWhoSheltersAll.BoseijuWhoSheltersAllAbility1.class));
		addMana("1R");
		donePlayingManaAbilities();

		castAndResolveSpell(Counterspell.class);
		assertEquals(1, this.game.actualState.stack().objects.size());
	}

	@Test
	public void bridgeFromBelow()
	{
		this.addDeck(
		// mill off:
		BridgefromBelow.class, Plains.class, DreadReturn.class, Narcomoeba.class, Narcomoeba.class, Narcomoeba.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class,
		// opening seven:
		GlimpsetheUnthinkable.class, MoggFanatic.class, GlimpsetheUnthinkable.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.addDeck(GlimpsetheUnthinkable.class, GlimpsetheUnthinkable.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// glimpse myself:
		this.respondWith(this.getSpellAction(GlimpsetheUnthinkable.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("UB");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// order narcomoeba triggers:
		this.respondArbitrarily();

		// put narcomoebas into play:
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		// flashback dread return:
		this.respondWith(this.getSpellAction(DreadReturn.class));
		// auto-target raging goblin

		// order triggers:
		this.respondArbitrarily();

		// zombie tokens (three triggers total):
		this.pass();
		this.pass();

		this.pass();
		this.pass();

		this.pass();
		this.pass();

		// dread return hasn't resolved yet, so it's just the tokens in play:
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("Zombie", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Zombie", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Zombie", this.game.actualState.battlefield().objects.get(2).getName());
	}

	@Test
	public void chromeMox()
	{
		this.addDeck(Forest.class, Forest.class, Island.class, Mountain.class, Plains.class, ChromeMox.class, KhalniHeartExpedition.class, KhalniHeartExpedition.class, Harrow.class, Forest.class, Forest.class, ChromeMox.class, SummonersPact.class);
		this.addDeck(Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class);
		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(ChromeMox.class));
		this.pass();
		this.pass();

		// Resolve chrome mox trigger
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();

		// Remove a khalni heart expedition to mox
		this.respondWith(Answer.YES);
		this.respondWith(this.pullChoice(KhalniHeartExpedition.class));

		this.respondWith(this.getAbilityAction(ChromeMox.ImprintMana.class));
		{
			ManaPool pool = this.player(0).pool;
			java.util.Collection<Color> symbolColors = pool.iterator().next().colors;
			assertEquals(1, pool.converted());
			assertEquals(1, symbolColors.size());
			assertEquals(Color.GREEN, symbolColors.iterator().next());
		}

		// This should use up the green mana we got from chrome mox
		this.castAndResolveSpell(KhalniHeartExpedition.class, "U");

		this.respondWith(this.getLandAction(Forest.class));

		assertEquals(1, this.game.actualState.stack().objects.size());

		this.castAndResolveSpell(Harrow.class, "2G");

		this.respondWith(this.pullChoice(Plains.class), this.pullChoice(Mountain.class));

		// Order the triggers on the stack
		assertEquals(this.choiceType, PlayerInterface.ChoiceType.TRIGGERS);
		this.respondArbitrarily();

		// Resolve the 3 Khalni Heart triggers
		for(int i = 0; i < 3; i++)
		{
			this.pass();
			this.pass();
			this.respondWith(Answer.YES);
		}

		assertEquals("Khalni Heart Expedition", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(3, this.game.actualState.battlefield().objects.get(2).counters.size());

		this.respondWith(this.getAbilityAction(KhalniHeartExpedition.SoIHeardYouLikeLandfall.class));
		// order costs
		this.pass();
		this.pass();

		this.respondWith(this.pullChoice(Forest.class), this.pullChoice(Forest.class));

		assertEquals(5, this.game.actualState.battlefield().objects.size());
		for(int i = 0; i < 4; i++)
			assertTrue(this.game.actualState.battlefield().objects.get(i).getTypes().contains(Type.LAND));
		assertEquals("Chrome Mox", this.game.actualState.battlefield().objects.get(4).getName());
	}

	@Test
	public void communeWithNature()
	{
		this.addDeck(
		// Cards on bottom:
		Forest.class, Mountain.class, Island.class,
		// Cards looked at with Commune:
		CompositeGolem.class, Knighthood.class, SpinelessThug.class, HolyDay.class, BlackLotus.class,
		// Opening seven:
		Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, CommunewithNature.class);

		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(GameType.STACKED);
		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(CommunewithNature.class));
		this.addMana("G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// choose to reveal a creature card:
		this.respondWith(Answer.YES);

		// two of the five cards seen are creatures:
		assertEquals(2, this.choices.size());
		this.respondWith(this.pullChoice(SpinelessThug.class));

		// order the cards on the bottom:
		this.respondArbitrarily();

		// 15 card deck, 7 card opener, one card removed by commune
		assertEquals(15 - 7 - 1, this.getLibrary(0).objects.size());
		assertEquals("Spineless Thug", this.getHand(0).objects.get(0).getName());

		// four cards between the island/mountain/forest and the bottom:
		assertEquals("Forest", this.getLibrary(0).objects.get(15 - 7 - 1 - 4 - 1).getName());
		assertEquals("Mountain", this.getLibrary(0).objects.get(15 - 7 - 1 - 4 - 2).getName());
		assertEquals("Island", this.getLibrary(0).objects.get(15 - 7 - 1 - 4 - 3).getName());

	}

	@Test
	public void crucibleOfWorlds()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(AzusaLostbutSeeking.class, CrucibleofWorlds.class, OnewithNothing.class, BlackLotus.class, BlackLotus.class, Swamp.class, Swamp.class, Swamp.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		// Player 0 passes the turn
		// Pass 1st Main Phase
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main Phase
		this.pass();
		this.pass();
		// Pass End of Turn
		this.pass();
		this.pass();

		// pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		// Player 1 does the tests
		this.respondWith(this.getLandAction(Swamp.class));
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(AzusaLostbutSeeking.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(CrucibleofWorlds.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(OnewithNothing.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.SWAMP));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Swamp.class));
		this.respondWith(this.getLandAction(Swamp.class));

		// Three Swamps, Azusa, Crucible0
		assertEquals(5, this.game.actualState.battlefield().objects.size());

		// One With Nothing, 2 Lotuses
		assertEquals(3, this.getGraveyard(1).objects.size());

		// Empty Hand & Library
		assertEquals(0, this.getHand(1).objects.size());
		assertEquals(0, this.getLibrary(1).objects.size());
	}

	@Test
	public void darkDepths()
	{
		this.addDeck(UrborgTombofYawgmoth.class, BloodMoon.class, Plains.class, Plains.class, Plains.class, DarkDepths.class, VampireHexmage.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(VampireHexmage.class, "BB");
		this.respondWith(this.getLandAction(DarkDepths.class));

		this.respondWith(this.getAbilityAction(VampireHexmage.TriggerDarkDepths.class));
		this.respondWith(this.getTarget(DarkDepths.class));
		// resolve hexmage ability
		this.pass();
		this.pass();

		// resolve depths ability
		this.pass();
		this.pass();
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Marit Lage", this.game.actualState.battlefield().objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getSuperTypes().contains(SuperType.LEGENDARY));
	}

	@Test
	public void diligentFarmhand()
	{
		this.addDeck(DiligentFarmhand.class, DiligentFarmhand.class, DiligentFarmhand.class, MuscleBurst.class, MuscleBurst.class, MuscleBurst.class, TomeScour.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, RuneclawBear.class, RuneclawBear.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(TomeScour.class, "U", this.player(0));

		this.castAndResolveSpell(RuneclawBear.class, "1G");

		// Muscle Burst will auto-target the bear.
		this.castAndResolveSpell(MuscleBurst.class, "1G");

		// Bears base power + 3 + Muscle Bursts in yard + Diligent Farmhands in
		// yard
		assertEquals(2 + 3 + 2 + 3, this.game.actualState.battlefield().objects.get(0).getPower());
	}

	@Test
	public void djinnOfWishes()
	{
		this.addDeck(Forest.class, Fireball.class, Forest.class, Sprout.class, DjinnofWishes.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(DjinnofWishes.class, "3UU");

		// This will cast a Sprout
		this.respondWith(this.getAbilityAction(DjinnofWishes.Wish.class));
		this.addMana("2UU");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals("Sprout", this.game.actualState.stack().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Djinn of Wishes", this.game.actualState.battlefield().objects.get(0).getName());

		// This will play a Forest
		this.respondWith(this.getAbilityAction(DjinnofWishes.Wish.class));
		this.addMana("2UU");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals("Sprout", this.game.actualState.stack().objects.get(0).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Forest", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Djinn of Wishes", this.game.actualState.battlefield().objects.get(1).getName());

		// This will play a Fireball
		this.respondWith(this.getAbilityAction(DjinnofWishes.Wish.class));
		this.addMana("2UU");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		// Declare the target
		this.respondWith(this.getTarget(this.player(1)));

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals("Fireball", this.game.actualState.stack().objects.get(0).getName());
		assertEquals("Sprout", this.game.actualState.stack().objects.get(1).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Forest", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Djinn of Wishes", this.game.actualState.battlefield().objects.get(1).getName());

		this.pass();
		this.pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals("Sprout", this.game.actualState.stack().objects.get(0).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Forest", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Djinn of Wishes", this.game.actualState.battlefield().objects.get(1).getName());

		this.pass();
		this.pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Forest", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Djinn of Wishes", this.game.actualState.battlefield().objects.get(2).getName());

		// This will attempt to play a Forest and fail
		this.respondWith(this.getAbilityAction(DjinnofWishes.Wish.class));
		this.addMana("2UU");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// The player can't play the forest, so they will not be given the
		// option to.
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);
	}

	@Test
	public void doublingCube()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(ColossusofSardia.class, SoulWarden.class, DoublingCube.class, CompositeGolem.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		// End the turn
		// Pass 1st Main Phase
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main Phase
		this.pass();
		this.pass();
		// Pass End of Turn
		this.pass();
		this.pass();

		// pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(CompositeGolem.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(DoublingCube.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE));
		this.pass();
		this.pass();

		// this is just to use up the extra white
		this.respondWith(this.getSpellAction(SoulWarden.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DoublingCube.DoubleMana.class));
		this.respondWith(this.getAbilityAction(CompositeGolem.SacForRainbow.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.donePlayingManaAbilities();
		assertEquals(8, this.player(1).pool.converted());
		this.respondWith(this.getMana(Color.BLACK, Color.BLACK, Color.BLACK));

		assertEquals(10, this.player(1).pool.converted());

		this.respondWith(this.getSpellAction(ColossusofSardia.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.BLUE, Color.BLUE, Color.BLACK, Color.BLACK, Color.RED, Color.RED, Color.GREEN));
		this.pass();
		this.pass();

		assertTrue(this.player(1).pool.toArray(new ManaSymbol[0])[0].isColor(org.rnd.jmagic.engine.Color.GREEN));

	}

	@Test
	public void doublingCubeDoesntCopyRestrictions()
	{
		this.addDeck(PrimalBeyond.class, DoublingCube.class, SparkElemental.class, SparkElemental.class, Shock.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(PrimalBeyond.class));
		this.respondWith(Answer.YES);
		this.respondWith(this.pullChoice(SparkElemental.class));

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(DoublingCube.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED, Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(SparkElemental.class));
		this.donePlayingManaAbilities();
		// Automatically choose R
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DoublingCube.DoubleMana.class));
		this.respondWith(this.getAbilityAction(PrimalBeyond.MakeElementalMana.class));
		this.respondWith(Color.RED);
		ManaSymbol elementalMana = this.player(0).getPhysical().pool.toArray(new ManaSymbol[0])[0];
		elementalMana.name = "P";
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLUE, Color.BLUE, Color.BLUE));

		// Attempt to play a shock with the elemental mana
		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.donePlayingManaAbilities();
		this.respondWith(elementalMana);
		// Can't pay for Shock with P, so have to pick R
		this.pullChoice(elementalMana);
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		// Make sure the elemental-mana is left
		assertTrue(this.player(0).pool.toArray(new ManaSymbol[0])[0].name.equals("P"));

		this.respondWith(this.getSpellAction(SparkElemental.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.pass();

		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Spark Elemental"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Spark Elemental"));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Doubling Cube"));
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Primal Beyond"));

		assertEquals(18, this.player(1).lifeTotal);

		assertEquals(0, this.player(0).pool.converted());
	}

	@Test
	public void dragonWhelp()
	{
		this.addDeck(DragonWhelp.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(DragonWhelp.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(DragonWhelp.class));
		this.addMana("2RR");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToStep(Step.StepType.END);
		assertEquals(1, this.game.actualState.stack().objects.size());

		// opponent's turn:
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.respondWith(this.getSpellAction(DragonWhelp.class));
		this.addMana("2RR");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToStep(Step.StepType.END);
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.getGraveyard(0).objects.size());
		assertEquals(0, this.getGraveyard(1).objects.size());
		assertEquals("Dragon Whelp", this.getGraveyard(0).objects.get(0).getName());
	}

	@Test
	public void eldraziTemple()
	{
		this.addDeck(EldraziTemple.class, KozilekButcherofTruth.class, HowlingMine.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getLandAction(EldraziTemple.class));
		this.respondWith(this.getAbilityAction(EldraziTemple.EldraziTempleAbility1.class));

		this.respondWith(this.getSpellAction(HowlingMine.class));
		this.donePlayingManaAbilities();
		// should fail

		this.respondWith(this.getSpellAction(KozilekButcherofTruth.class));
		this.addMana("8");
		this.donePlayingManaAbilities();

		// kozilek and his trigger:
		assertEquals(2, this.game.physicalState.stack().objects.size());
	}

	@Test
	public void empyrialArchangelCombatAndNoncombatDamage()
	{
		// This test makes sure both combat and noncombat damage are redirected
		// to the angel
		this.addDeck(EmpyrialArchangel.class, Shock.class, Mountain.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Mountain.class));

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(EmpyrialArchangel.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.GREEN, Color.GREEN, Color.GREEN, Color.WHITE, Color.WHITE, Color.BLUE, Color.BLUE, Color.BLUE));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.MOUNTAIN));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Empyrial Archangel"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getDamage());

		// End the turn
		// Pass Main
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main
		this.pass();
		this.pass();
		// Pass EoT
		this.pass();
		this.pass();

		// Pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Pass Main
		this.pass();
		this.pass();

		// Pass Beginning of Combat
		this.pass();
		this.pass();

		// Declare Attackers (all 6 goblins attack)
		this.respondWith(this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class));
		this.pass();
		this.pass();

		// Declare Blockers (none)
		this.declareNoBlockers();
		this.pass();
		this.pass();

		// Resolve Combat Damage
		this.pass();
		this.pass();

		// no combat damage was dealt to player
		assertEquals(20, this.player(0).lifeTotal);
		assertEquals("Empyrial Archangel", this.game.actualState.battlefield().objects.get(6).getName());
		assertEquals(6, this.game.actualState.battlefield().objects.get(6).getDamage());

	}

	@Test
	public void empyrialArchangelTwoAngelsReplacingOneCombatDamage()
	{
		// Mana needed for Player 0 - 2U (Meditate) + 8GGWWWWUU (2 Angels)
		// The lotuses will produce UUU, UUU, GGG, WWW, WWW, WWW
		// The plains will produce the final mana needed for the angels
		// The swamp is filler for the deck
		this.addDeck(Swamp.class, EmpyrialArchangel.class, EmpyrialArchangel.class, Plains.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Meditate.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		this.startGame(GameType.STACKED);

		// This test makes sure that all of combat damage is dealt to a single
		// angel, even if there are two in play
		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);

		this.respondWith(this.getSpellAction(Meditate.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLUE, Color.BLUE, Color.BLUE));
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Plains.class));

		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));

		this.respondWith(this.getSpellAction(EmpyrialArchangel.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.GREEN, Color.GREEN, Color.BLUE));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(EmpyrialArchangel.class));
		this.donePlayingManaAbilities();
		// Auto-choose the following: #W#W#W#W#W#G#U#U
		this.pass();
		this.pass();

		// End the turn
		// Pass Main
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main
		this.pass();
		this.pass();
		// Pass EoT
		this.pass();
		this.pass();

		// Pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Pass Main
		this.pass();
		this.pass();

		// Pass Beginning of Combat
		this.pass();
		this.pass();

		// Declare Attackers (all 6 goblins attack)
		this.respondWith(this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class));
		this.pass();
		this.pass();

		// Declare Blockers (none)
		this.declareNoBlockers();
		this.pass();
		this.pass();

		// Choose either replacement effect to apply (there should be two)
		assertEquals(2, this.choices.size());
		this.respondWith(this.getDamageAssignmentReplacement("All damage that would be dealt to you is dealt to Empyrial Archangel instead"));

		// 6x Raging Goblin, 2x Empyrial Archangel, 1x Plains
		assertEquals(9, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(6).getName().equals("Empyrial Archangel"));
		assertTrue(this.game.actualState.battlefield().objects.get(7).getName().equals("Empyrial Archangel"));

		// Assert that one of the angels took all the damage, and the other took
		// none

		assertTrue((this.game.actualState.battlefield().objects.get(6).getDamage() == 6 && this.game.actualState.battlefield().objects.get(7).getDamage() == 0) || (this.game.actualState.battlefield().objects.get(6).getDamage() == 0 && this.game.actualState.battlefield().objects.get(7).getDamage() == 6));
	}

	@Test
	public void fireball()
	{
		this.addDeck(Fireball.class, GrizzlyBears.class, MoggFanatic.class, MoggFanatic.class, Fireball.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(MoggFanatic.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(GrizzlyBears.class));
		this.addMana("1G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Fireball.class));
		// choose X:
		this.respondWith(3);
		// choose targets:
		this.respondWith(this.getTarget(GrizzlyBears.class), this.getTarget(MoggFanatic.class));
		// 3R + (2 - 1) = 4R
		this.addMana("4R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// 3 / 2 rounded down = 1 damage -- fanatic dies; bears lives
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getDamage());

		this.respondWith(this.getSpellAction(MoggFanatic.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Fireball.class));
		// choose X:
		this.respondWith(1);
		// targets:
		this.respondWith(this.getTarget(GrizzlyBears.class), this.getTarget(MoggFanatic.class));
		// 1R + (2 - 1) = 2R
		this.addMana("2R");
		this.donePlayingManaAbilities();

		// fireball on the stack, kill the fanatic:
		this.respondWith(this.getAbilityAction(MoggFanatic.SacPing.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.pass();
		this.pass();

		// resolve fireball
		this.pass();
		this.pass();

		// fireball has one legal target -- the bears, so its 1 damage divided
		// evenly rounded down should kill the bears:
		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void flashOfInsight()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Mountain.class, Swamp.class, Island.class, OnewithNothing.class, Opt.class, Opt.class, Opt.class, Opt.class, Opt.class, FlashofInsight.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(FlashofInsight.class));
		this.respondWith(3);
		this.addMana("4U");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.pullChoice(Island.class));
		this.respondWith(this.pullChoice(Mountain.class), this.pullChoice(Swamp.class));

		assertEquals(7, this.getHand(0).objects.size());
		assertEquals(5, this.getLibrary(0).objects.size());
		assertEquals(1, this.getGraveyard(0).objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		this.castAndResolveSpell(OnewithNothing.class, "B");

		this.respondWith(this.getSpellAction(FlashofInsight.class));
		this.respondWith(4);
		this.respondWith(this.pullChoice(Opt.class), this.pullChoice(Opt.class), this.pullChoice(Opt.class), this.pullChoice(Opt.class));
		this.addMana("1U");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.pullChoice(Mountain.class));
		this.respondWith(this.pullChoice(Plains.class), this.pullChoice(Plains.class), this.pullChoice(Plains.class));

		assertEquals(1, this.getHand(0).objects.size());
		assertEquals(4, this.getLibrary(0).objects.size());
		assertEquals(3, this.getGraveyard(0).objects.size());
		assertEquals(5, this.game.actualState.exileZone().objects.size());
	}

	@Test
	public void gaddockTeeg()
	{
		this.addDeck(GaddockTeeg.class, BlackLotus.class, WrathofGod.class, Fireball.class, AntQueen.class, ProteanHydra.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(GaddockTeeg.class, "GW");

		// noncreature that costs < 4 and has no X
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		// creature that costs >= 4
		this.castAndResolveSpell(AntQueen.class, "3GG");

		// creature that costs X
		this.respondWith(this.getSpellAction(ProteanHydra.class));
		this.respondWith(4);
		this.addMana("4G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// noncreature that costs >= 4
		this.confirmCantBePlayed(WrathofGod.class);

		// noncreature that costs X
		this.confirmCantBePlayed(Fireball.class);

		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);
	}

	@Test
	public void graftedExoskeleton()
	{
		this.addDeck(GraftedExoskeleton.class, GrizzlyBears.class, Shatter.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(GraftedExoskeleton.class);
		castAndResolveSpell(GrizzlyBears.class);

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Equip.EquipAbility.class));
		addMana("2");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Grafted Exoskeleton", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(this.game.actualState.battlefield().objects.get(0).ID, this.game.actualState.battlefield().objects.get(1).getAttachedTo());
		assertEquals(0, this.game.actualState.stack().objects.size());

		castAndResolveSpell(Shatter.class);

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
	}

	@Test
	public void guile()
	{
		this.addDeck(Guile.class, GrizzlyBears.class, Counterspell.class, Counterspell.class, Counterspell.class, Counterspell.class, Counterspell.class);
		this.addDeck(PactofNegation.class, PactofNegation.class, Sprout.class, Sprout.class, ElvishWarrior.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Play and resolve Guile
		this.castAndResolveSpell(Guile.class, "3UUU");

		// Play Grizzly Bears
		this.respondWith(this.getSpellAction(GrizzlyBears.class));
		this.addMana("1G");
		this.donePlayingManaAbilities();

		// Counter Grizzly Bears
		this.castAndResolveSpell(Counterspell.class, "UU");

		// Choose to cast Grizzly Bears
		this.respondWith(this.pullChoice(GrizzlyBears.class));

		// Resolve the Grizzly Bears
		this.pass();
		this.pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Guile", this.game.actualState.battlefield().objects.get(1).getName());

		this.pass();

		// Opp casts Sprout
		this.respondWith(this.getSpellAction(Sprout.class));
		this.addMana("G");
		this.donePlayingManaAbilities();
		this.pass();

		// Counterspell @ Sprout
		this.respondWith(this.getSpellAction(Counterspell.class));
		this.addMana("UU");
		this.donePlayingManaAbilities();
		this.pass();

		// Pact @ Counterspell
		this.respondWith(this.getSpellAction(PactofNegation.class));
		this.respondWith(this.getTarget(Counterspell.class));
		this.pass();

		// Counterspell @ Pact
		this.respondWith(this.getSpellAction(Counterspell.class));
		this.respondWith(this.getTarget(PactofNegation.class));
		this.addMana("UU");
		this.donePlayingManaAbilities();

		// Counter pact, recast @ counterspell
		this.pass();
		this.pass();
		this.respondWith(this.pullChoice(PactofNegation.class));

		// There are actually 2 counterspells on the stack right now, the one
		// resolving at pact, and the one waiting to counter sprout
		GameObject counterspell = this.game.actualState.stack().objects.get(2);
		assertEquals("Counterspell", counterspell.getName());
		this.respondWith(this.getTarget(counterspell));

		// Counter counterspell, dont recast
		this.pass();
		this.pass();
		this.respondWith();

		// Original sprout resolves, uncountered
		this.pass();
		this.pass();

		// Go to player 1's turn
		this.goToPhase(Phase.PhaseType.BEGINNING);
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Cast Elvish Warrior
		this.respondWith(this.getSpellAction(ElvishWarrior.class));
		this.addMana("GG");
		this.donePlayingManaAbilities();
		this.pass();

		// Counterspell @ Elvish Warrior
		this.respondWith(this.getSpellAction(Counterspell.class));
		this.addMana("UU");
		this.donePlayingManaAbilities();
		this.pass();

		// Pact @ Counterspell
		this.respondWith(this.getSpellAction(PactofNegation.class));
		this.respondWith(this.getTarget(Counterspell.class));
		this.pass();
		this.pass();

		this.pass();

		// Counterspell @ Elvish Warrior
		this.castAndResolveSpell(Counterspell.class, "UU");

		// Choose to cast Elvish Warrior
		this.respondWith(this.pullChoice(ElvishWarrior.class));
		this.pass();
		this.pass();

		int player0 = this.player(0).ID;
		int player1 = this.player(1).ID;

		assertEquals(4, this.game.actualState.battlefield().objects.size());

		assertEquals("Guile", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Elvish Warrior", this.game.actualState.battlefield().objects.get(0).getName());

		assertEquals(player0, this.game.actualState.battlefield().objects.get(3).controllerID);
		assertEquals(player0, this.game.actualState.battlefield().objects.get(2).controllerID);
		assertEquals(player1, this.game.actualState.battlefield().objects.get(1).controllerID);
		assertEquals(player0, this.game.actualState.battlefield().objects.get(0).controllerID);

		assertEquals(player0, this.game.actualState.battlefield().objects.get(3).ownerID);
		assertEquals(player0, this.game.actualState.battlefield().objects.get(2).ownerID);
		// Tokens are owned by the controller of the effect that created them:
		assertEquals(player1, this.game.actualState.battlefield().objects.get(1).ownerID);
		assertEquals(player1, this.game.actualState.battlefield().objects.get(0).ownerID);

		// Go to player 0's upkeep to make sure his stolen pact triggers
		this.goToPhase(Phase.PhaseType.BEGINNING);
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(org.rnd.jmagic.engine.DelayedTrigger.class, this.game.actualState.stack().objects.get(0).getClass());
	}

	@Test
	public void hailOfArrows()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, HailofArrows.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		// End the turn
		// Pass 1st Main Phase
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main Phase
		this.pass();
		this.pass();
		// Pass End of Turn
		this.pass();
		this.pass();

		// pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		GameObject ragingGoblinA = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		GameObject ragingGoblinB = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		GameObject ragingGoblinC = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		GameObject ragingGoblinD = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		GameObject ragingGoblinE = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		GameObject ragingGoblinF = this.game.physicalState.battlefield().objects.get(0);

		// Pass Main
		this.pass();
		this.pass();

		// Pass Beginning of Combat
		this.pass();
		this.pass();

		// Declare Attackers
		this.respondWith(this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class));
		this.pass();

		this.respondWith(this.getSpellAction(HailofArrows.class));

		// Choose X
		this.respondWith(17);

		// Choose the targets
		assertEquals(6, this.choices.size());
		assertEquals(ragingGoblinF, this.game.actualState.battlefield().objects.get(0));
		assertEquals(ragingGoblinE, this.game.actualState.battlefield().objects.get(1));
		assertEquals(ragingGoblinD, this.game.actualState.battlefield().objects.get(2));
		assertEquals(ragingGoblinC, this.game.actualState.battlefield().objects.get(3));
		assertEquals(ragingGoblinB, this.game.actualState.battlefield().objects.get(4));
		assertEquals(ragingGoblinA, this.game.actualState.battlefield().objects.get(5));
		boolean targetingRagingGoblins[] = {false, false, false, false, false, false};
		for(SanitizedTarget t: this.choices.getAll(SanitizedTarget.class))
			for(int i = 0; i < 6; ++i)
				if(t.targetID == this.game.actualState.battlefield().objects.get(0).ID)
					targetingRagingGoblins[i] = true;
		for(int i = 0; i < 6; ++i)
			assertTrue("Raging Goblin " + i + " not found in choice of targets", targetingRagingGoblins[i]);

		// Target #1
		this.respondWith(this.getTarget(ragingGoblinA), this.getTarget(ragingGoblinB), this.getTarget(ragingGoblinC), this.getTarget(ragingGoblinD), this.getTarget(ragingGoblinE));

		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		divisions.put(ragingGoblinA.ID, 4);
		divisions.put(ragingGoblinB.ID, 3);
		divisions.put(ragingGoblinC.ID, 3);
		divisions.put(ragingGoblinD.ID, 3);
		divisions.put(ragingGoblinE.ID, 4);
		this.divide(divisions);

		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		// Order the Raging Goblins in the graveyard

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(ragingGoblinF, this.game.actualState.battlefield().objects.get(0));

	}

	@Test
	public void harmsWay()
	{
		this.addDeck(MoggFanatic.class, ChaosCharm.class, LightningBolt.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(HarmsWay.class, Sprout.class, HarmsWay.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(MoggFanatic.class, "R");

		this.respondWith(this.getSpellAction(ChaosCharm.class));
		this.respondWith(this.getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// auto-target Mogg Fanatic
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToStep(Step.StepType.DECLARE_ATTACKERS);
		this.respondWith(this.pullChoice(MoggFanatic.class));
		this.pass();

		this.respondWith(this.getSpellAction(HarmsWay.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("W");
		this.donePlayingManaAbilities();

		this.castAndResolveSpell(Sprout.class, "G");

		this.pass();
		this.pass();

		this.respondWith(this.pullChoice(MoggFanatic.class));

		this.goToStep(Step.StepType.END_OF_COMBAT);

		assertEquals(19, this.player(0).lifeTotal);

		this.respondWith(this.getAbilityAction(MoggFanatic.SacPing.class));
		this.respondWith(this.getTarget(Token.class));
		this.pass();
		this.pass();

		assertEquals(18, this.player(0).lifeTotal);
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(0).getName());

		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(Token.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();

		this.respondWith(this.getSpellAction(HarmsWay.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("W");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(this.pullChoice(LightningBolt.class));

		// resolve the lightning bolt
		this.pass();
		this.pass();

		assertEquals(16, this.player(0).lifeTotal);
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void headGames()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, HeadGames.class, HeadGames.class, HeadGames.class);
		this.addDeck(Island.class, Island.class, Swamp.class, Swamp.class, Mountain.class, Mountain.class, Forest.class, Forest.class);
		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(HeadGames.class));
		// auto-choose player 1
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));

		assertEquals(1, this.getLibrary(1).objects.size());
		assertTrue(this.getLibrary(1).objects.get(0).getName().equals("Island"));
		assertEquals(7, this.getHand(1).objects.size());
		assertTrue(this.getHand(1).objects.get(0).getName().equals("Island"));
		assertTrue(this.getHand(1).objects.get(1).getName().equals("Swamp"));
		assertTrue(this.getHand(1).objects.get(2).getName().equals("Swamp"));
		assertTrue(this.getHand(1).objects.get(3).getName().equals("Mountain"));
		assertTrue(this.getHand(1).objects.get(4).getName().equals("Mountain"));
		assertTrue(this.getHand(1).objects.get(5).getName().equals("Forest"));
		assertTrue(this.getHand(1).objects.get(6).getName().equals("Forest"));

		this.pass();
		this.pass();

		// order the cards on top of the library
		this.respondWith(this.pullChoice(Island.class), this.pullChoice(Swamp.class), this.pullChoice(Mountain.class), this.pullChoice(Forest.class), this.pullChoice(Swamp.class), this.pullChoice(Mountain.class), this.pullChoice(Forest.class));
		assertEquals(8, this.getLibrary(1).objects.size());
		assertTrue(this.getLibrary(1).objects.get(0).getName().equals("Forest"));
		assertTrue(this.getLibrary(1).objects.get(1).getName().equals("Mountain"));
		assertTrue(this.getLibrary(1).objects.get(2).getName().equals("Swamp"));
		assertTrue(this.getLibrary(1).objects.get(3).getName().equals("Forest"));
		assertTrue(this.getLibrary(1).objects.get(4).getName().equals("Mountain"));
		assertTrue(this.getLibrary(1).objects.get(5).getName().equals("Swamp"));
		assertTrue(this.getLibrary(1).objects.get(6).getName().equals("Island"));
		assertTrue(this.getLibrary(1).objects.get(7).getName().equals("Island"));
		assertEquals(0, this.getHand(1).objects.size());
		assertEquals(8, this.choices.size());

		// Head Games resolves; choose cards for the player's hand:
		this.respondWith(this.pullChoice(Island.class), this.pullChoice(Island.class), this.pullChoice(Swamp.class), this.pullChoice(Swamp.class), this.pullChoice(Mountain.class), this.pullChoice(Mountain.class), this.pullChoice(Forest.class));

		assertEquals(1, this.getLibrary(1).objects.size());
		assertTrue(this.getLibrary(1).objects.get(0).getName().equals("Forest"));
		assertEquals(7, this.getHand(1).objects.size());
	}

	@Test
	public void highGround()
	{
		this.addDeck(HighGround.class, WhiteKnight.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(WhiteKnight.class));
		this.addMana("WW");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(HighGround.class));
		this.addMana("W");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToPhase(Phase.PhaseType.ENDING);
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		Identified RagingGoblinA = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		Identified RagingGoblinB = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		Identified RagingGoblinC = this.game.physicalState.battlefield().objects.get(0);

		this.goToStep(Step.StepType.DECLARE_ATTACKERS);
		this.respondWith(this.getChoice(RagingGoblinA), this.getChoice(RagingGoblinB), this.getChoice(RagingGoblinC));

		this.goToStep(Step.StepType.DECLARE_BLOCKERS);
		this.respondWith(this.pullChoice(WhiteKnight.class));
		this.respondWith(this.getChoice(RagingGoblinA), this.getChoice(RagingGoblinB));
		this.respondArbitrarily();

		this.goToStep(Step.StepType.COMBAT_DAMAGE);
		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		divisions.put(RagingGoblinA.ID, 1);
		divisions.put(RagingGoblinB.ID, 1);
		this.divide(divisions);
		// order the goblins in the yard

		this.goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		assertEquals(19, this.player(0).lifeTotal);
		// one goblin, high ground, white knight
		assertEquals(3, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void highTide()
	{
		this.addDeck(AzusaLostbutSeeking.class, HighTide.class, HighTide.class, HighTide.class, Island.class, Island.class, Island.class);
		this.addDeck(Plains.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(AzusaLostbutSeeking.class, "2G");

		this.respondWith(this.getLandAction(Island.class));
		this.respondWith(this.getLandAction(Island.class));
		this.respondWith(this.getLandAction(Island.class));

		int[] islands = new int[2];

		// Tap for U
		SanitizedCastSpellOrActivateAbilityAction ability = this.getAbilityAction(Game.IntrinsicManaAbility.class);
		islands[0] = ability.actOnID;
		this.respondWith(ability);

		assertEquals(1, this.player(0).pool.converted());

		this.respondWith(this.getSpellAction(HighTide.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(0, this.player(0).pool.converted());

		ability = null;
		do
		{
			ability = this.getAbilityAction(Game.IntrinsicManaAbility.class);
			this.pullChoice(ability);
			if(islands[0] != ability.actOnID)
				break;
		}
		while(true);
		islands[1] = ability.actOnID;
		this.respondWith(ability);

		assertEquals(2, this.player(0).pool.converted());

		this.respondWith(this.getSpellAction(HighTide.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLUE));
		this.pass();
		this.pass();

		assertEquals(1, this.player(0).pool.converted());

		this.respondWith(this.getSpellAction(HighTide.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(0, this.player(0).pool.converted());

		ability = null;
		do
		{
			ability = this.getAbilityAction(Game.IntrinsicManaAbility.class);
			this.pullChoice(ability);
			if(islands[0] != ability.actOnID && islands[1] != ability.actOnID)
				break;
		}
		while(true);
		this.respondWith(ability);

		// Stack the three triggers
		this.respondArbitrarily();

		assertEquals(4, this.player(0).pool.converted());
	}

	@Test
	public void humble()
	{
		this.addDeck(BlackLotus.class, SoulWarden.class, SoulWarden.class, Humble.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(SoulWarden.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Humble.class));
		this.donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Soul Warden", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());

		// Resolve Humble
		this.pass();
		this.pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Soul Warden", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());

		this.respondWith(this.getLandAction(Plains.class));

		this.respondWith(this.getSpellAction(SoulWarden.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Soul Warden (normal), Plains, Soul Warden (humbled)
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getNonStaticAbilities().size());

	}

	@Test
	public void humility()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, SoulWarden.class, SoulWarden.class, Humility.class);
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(SoulWarden.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Humility.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));

		// Soul Warden (normal)
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Soul Warden", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());

		// Resolve Humility
		this.pass();
		this.pass();

		// Humility, Soul Warden (humiliated)
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Soul Warden", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getKeywordAbilities().size());

		this.respondWith(this.getSpellAction(SoulWarden.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Soul Warden (humiliated), Humility, Soul Warden (humiliated)
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getNonStaticAbilities().size());

	}

	@Test
	public void huntedWumpus()
	{
		this.addDeck(HuntedWumpus.class, HuntedWumpus.class, GrizzlyBears.class, GrizzlyBears.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(HuntedWumpus.class, HuntedWumpus.class, MoggFanatic.class, MoggFanatic.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();
		this.keep();

		int playerZeroID = this.player(0).ID;
		int playerOneID = this.player(1).ID;
		int playerOneHandID = this.getHand(1).ID;
		int playerZeroHandID = this.getHand(0).ID;

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(HuntedWumpus.class));
		this.addMana("3G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.pass();

		// Resolve Wumpus ability
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();
		this.pass();

		// Make sure player 1 has 4 choices, and they're all from their hand
		this.respondWith(Answer.YES);
		java.util.Iterator<SanitizedGameObject> iter = this.choices.getAll(SanitizedGameObject.class).iterator();
		assertEquals(4, this.choices.size());
		for(int i = 0; i < 4; i++)
			assertEquals(playerOneHandID, iter.next().zoneID);
		assertFalse(iter.hasNext());

		// Player 1 chooses Mogg Fanatic
		this.respondWith(this.pullChoice(MoggFanatic.class));

		this.respondWith(this.getSpellAction(HuntedWumpus.class));
		this.addMana("3G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.pass();

		// Resolve Wumpus ability
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();
		this.pass();

		// Player 1 puts a Wumpus onto the battlefield
		this.respondWith(Answer.YES);
		this.respondWith(this.pullChoice(HuntedWumpus.class));

		// Resolve Wumpus ability
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();
		this.pass();

		// Player 0 should have 2 choices
		this.respondWith(Answer.YES);
		iter = this.choices.getAll(SanitizedGameObject.class).iterator();
		assertEquals(2, this.choices.size());
		for(int i = 0; i < 2; i++)
			assertEquals(playerZeroHandID, iter.next().zoneID);
		assertFalse(iter.hasNext());

		// Player 0 chooses Grizzly Bears
		this.respondWith(this.pullChoice(GrizzlyBears.class));

		java.util.List<GameObject> stuff = this.game.actualState.battlefield().objects;

		assertEquals(5, stuff.size());

		assertEquals("Grizzly Bears", stuff.get(0).getName());
		assertEquals(playerZeroID, stuff.get(0).controllerID);

		assertEquals("Hunted Wumpus", stuff.get(1).getName());
		assertEquals(playerOneID, stuff.get(1).controllerID);

		assertEquals("Hunted Wumpus", stuff.get(2).getName());
		assertEquals(playerZeroID, stuff.get(2).controllerID);

		assertEquals("Mogg Fanatic", stuff.get(3).getName());
		assertEquals(playerOneID, stuff.get(3).controllerID);

		assertEquals("Hunted Wumpus", stuff.get(4).getName());
		assertEquals(playerZeroID, stuff.get(4).controllerID);
	}

	@Test
	public void kalastriaHighborn()
	{
		this.addDeck(KalastriaHighborn.class, Terminate.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.castAndResolveSpell(KalastriaHighborn.class, "BB");
		this.castAndResolveSpell(Terminate.class, "BR");

		this.respondWith(this.getTarget(this.player(1)));
		this.pass();
		this.pass();

		this.addMana("B");
		this.donePlayingManaAbilities();
		this.respondWith(Answer.YES);

		assertEquals(22, this.player(0).lifeTotal);
		assertEquals(18, this.player(1).lifeTotal);
	}

	@Test
	public void lilianaVessTutor()
	{
		this.addDeck(
		// 10 cards on top, one unique
		Island.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class,
		// opening seven
		LilianaVess.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(GameType.STACKED);
		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(LilianaVess.class, "3BB");

		this.respondWith(this.getAbilityAction(LilianaVess.Tutor.class));
		this.pass();
		this.pass();

		this.respondWith(this.pullChoice(Island.class));

		assertEquals("Island", this.getLibrary(0).objects.get(0).getName());
		assertEquals(10, this.getLibrary(0).objects.size());
	}

	@Test
	public void loxodonGatekeeper()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, LoxodonGatekeeper.class, LoxodonGatekeeper.class, LoxodonGatekeeper.class, LoxodonGatekeeper.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// upkeep
		this.pass();
		this.pass();

		// main phase
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(LoxodonGatekeeper.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		this.pass();
		this.pass();

		// finish main phase
		this.pass();
		this.pass();

		// beginning of combat
		this.pass();
		this.pass();

		// declare attackers
		this.pass();
		this.pass();

		// end of combat
		this.pass();
		this.pass();

		// second main phase
		this.pass();
		this.pass();

		// eot
		this.pass();
		this.pass();

		// player 1's upkeep
		this.pass();
		this.pass();

		// draw step
		this.pass();
		this.pass();

		// main phase
		this.respondWith(this.getLandAction(Plains.class));
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());
		this.pass();
		this.pass();

		// beginning of combat
		this.pass();
		this.pass();

		// declare attackers
		this.pass();
		this.pass();

		// end of combat
		this.pass();
		this.pass();

		// second main phase
		this.pass();
		this.pass();

		// eot
		this.pass();
		this.pass();

		// player 0's upkeep
		this.pass();
		this.pass();

		// draw step
		this.pass();
		this.pass();

		// main phase
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		assertTrue(!this.game.actualState.battlefield().objects.get(0).isTapped());
	}

	@Test
	public void lurkingPredators()
	{
		this.addDeck(
		// left in library
		Plains.class, Plains.class, RagingGoblin.class, Righteousness.class, MoggFanatic.class,
		// opening seven
		LurkingPredators.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.addDeck(LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class);

		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(LurkingPredators.class, "4GG");

		this.pass();
		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("R");
		this.donePlayingManaAbilities();

		// lurking predators triggers with mogg fanatic on top
		this.pass();
		this.pass();
		assertEquals("Mogg Fanatic", this.game.actualState.battlefield().objects.get(0).getName());
		this.pass();
		this.pass();

		this.pass();
		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("R");
		this.donePlayingManaAbilities();

		// lurking predators triggers with righteousness on top
		this.pass();
		this.pass();
		this.respondWith(Answer.NO);
		this.pass();
		this.pass();

		this.pass();
		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("R");
		this.donePlayingManaAbilities();

		// lurking predators triggers with righteousness on top
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);
		this.pass();
		this.pass();

		this.pass();
		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("R");
		this.donePlayingManaAbilities();

		// lurking predators triggers with raging goblin on top
		this.pass();
		this.pass();
		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(0).getName());
		this.pass();
		this.pass();

	}

	@Test
	public void m10Lands()
	{
		this.addDeck(DragonskullSummit.class, Swamp.class, DragonskullSummit.class, AzusaLostbutSeeking.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getLandAction(DragonskullSummit.class));
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());

		this.respondWith(this.getSpellAction(AzusaLostbutSeeking.class));
		this.addMana("2G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Swamp.class));
		this.respondWith(this.getLandAction(DragonskullSummit.class));
		assertFalse(this.game.actualState.battlefield().objects.get(0).isTapped());

		ActivatedAbility ability = (ActivatedAbility)this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().get(0);

		this.respondWith(this.getAbilityAction(ability));

		assertTrue(this.choices.contains(Color.BLACK));
		assertTrue(this.choices.contains(Color.RED));
	}

	@Test
	public void manabarbs()
	{
		this.addDeck(Manabarbs.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(Manabarbs.class));
		this.addMana("3R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Plains.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();
		assertEquals(19, this.player(0).lifeTotal);

		this.goToPhase(Phase.PhaseType.ENDING);
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getLandAction(Plains.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();
		assertEquals(19, this.player(1).lifeTotal);
	}

	@Test
	public void marchOfTheMachines()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, ChromaticStar.class);
		this.addDeck(MarchoftheMachines.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, PhyrexianWalker.class, Island.class, ChromaticStar.class, AngelicWall.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Plains.class));

		this.respondWith(this.getSpellAction(ChromaticStar.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// End the turn
		// Pass 1st Main Phase
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main Phase
		this.pass();
		this.pass();
		// Pass End of Turn
		this.pass();
		this.pass();

		// pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(PhyrexianWalker.class));
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Island.class));

		this.respondWith(this.getSpellAction(AngelicWall.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(ChromaticStar.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(MarchoftheMachines.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.respondWith(this.getIntrinsicAbilityAction(SubType.ISLAND));
		this.donePlayingManaAbilities();

		// Leave the March on the stack while we make some assertions

		// Star, Wall, Island, Walker, Lotus, Star, Plains
		assertEquals(7, this.game.actualState.battlefield().objects.size());

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Chromatic Star"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Angelic Wall"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Island"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(2).getTypes().contains(org.rnd.jmagic.engine.Type.LAND));

		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Phyrexian Walker"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(3).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));
		assertTrue(this.game.actualState.battlefield().objects.get(3).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(3).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(4).getName().equals("Black Lotus"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(4).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(4).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.game.actualState.battlefield().objects.get(5).getName().equals("Chromatic Star"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(5).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(5).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.game.actualState.battlefield().objects.get(6).getName().equals("Plains"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(6).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(6).getTypes().contains(org.rnd.jmagic.engine.Type.LAND));

		// 2x Black Lotus
		assertEquals(2, this.getGraveyard(1).objects.size());

		assertTrue(this.getGraveyard(1).objects.get(0).getName().equals("Black Lotus"));
		assertEquals(1, this.getGraveyard(1).objects.get(0).getTypes().size());
		assertTrue(this.getGraveyard(1).objects.get(0).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.getGraveyard(1).objects.get(1).getName().equals("Black Lotus"));
		assertEquals(1, this.getGraveyard(1).objects.get(1).getTypes().size());
		assertTrue(this.getGraveyard(1).objects.get(1).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		// Resolve the March of the Machines
		this.pass();
		this.pass();

		// March, Star, Wall, Island, Walker, Star, Plains
		assertTrue(this.game.actualState.battlefield().objects.size() == 7.0);

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("March of the Machines"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getTypes().contains(org.rnd.jmagic.engine.Type.ENCHANTMENT));

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Chromatic Star"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Angelic Wall"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(2).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(2).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Island"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(3).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(3).getTypes().contains(org.rnd.jmagic.engine.Type.LAND));

		assertTrue(this.game.actualState.battlefield().objects.get(4).getName().equals("Phyrexian Walker"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(4).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(4).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));
		assertTrue(this.game.actualState.battlefield().objects.get(4).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(0, this.game.actualState.battlefield().objects.get(4).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(4).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(5).getName().equals("Chromatic Star"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(5).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(5).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));
		assertTrue(this.game.actualState.battlefield().objects.get(5).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(1, this.game.actualState.battlefield().objects.get(5).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(5).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(6).getName().equals("Plains"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(6).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(6).getTypes().contains(org.rnd.jmagic.engine.Type.LAND));

		// 3x Black Lotus
		assertEquals(3, this.getGraveyard(1).objects.size());

		assertTrue(this.getGraveyard(1).objects.get(0).getName().equals("Black Lotus"));
		assertEquals(1, this.getGraveyard(1).objects.get(0).getTypes().size());
		assertTrue(this.getGraveyard(1).objects.get(0).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.getGraveyard(1).objects.get(1).getName().equals("Black Lotus"));
		assertEquals(1, this.getGraveyard(1).objects.get(1).getTypes().size());
		assertTrue(this.getGraveyard(1).objects.get(1).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.getGraveyard(1).objects.get(2).getName().equals("Black Lotus"));
		assertEquals(1, this.getGraveyard(1).objects.get(2).getTypes().size());
		assertTrue(this.getGraveyard(1).objects.get(2).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));
	}

	@Test
	public void myrSuperion()
	{
		this.addDeck(MyrSuperion.class, AzusaLostbutSeeking.class, Plains.class, Plains.class, GreenweaverDruid.class, ChaosCharm.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(Plains.class));
		GameObject plains1 = this.game.actualState.battlefield().objects.get(0);
		castAndResolveSpell(AzusaLostbutSeeking.class);
		respondWith(getLandAction(Plains.class));
		GameObject plains2 = this.game.actualState.battlefield().objects.get(0);

		respondWith(getSpellAction(MyrSuperion.class));
		respondWith(getAbilityAction((ActivatedAbility)this.game.actualState.<GameObject>get(plains1.ID).getNonStaticAbilities().iterator().next()));
		respondWith(getAbilityAction((ActivatedAbility)this.game.actualState.<GameObject>get(plains2.ID).getNonStaticAbilities().iterator().next()));
		donePlayingManaAbilities();
		assertEquals(this.game.actualState.stack().objects.size(), 0);
		assertEquals(player(0).pool.size(), 0);

		castAndResolveSpell(GreenweaverDruid.class);
		GameObject druid = this.game.actualState.battlefield().objects.get(0);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(GreenweaverDruid.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction((ActivatedAbility)this.game.actualState.<GameObject>get(druid.ID).getNonStaticAbilities().iterator().next()));
		assertEquals(player(0).pool.size(), 2);
		respondWith(getSpellAction(MyrSuperion.class));
		donePlayingManaAbilities();
		assertEquals(this.game.actualState.stack().objects.size(), 1);
		assertEquals(player(0).pool.size(), 0);
	}

	@Test
	public void meliraStopsEntersBattlefieldWithCounters()
	{
		this.addDeck(MeliraSylvokOutcast.class, CarnifexDemon.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(MeliraSylvokOutcast.class);
		castAndResolveSpell(CarnifexDemon.class);

		GameObject ghost = this.game.actualState.battlefield().objects.get(0);
		assertEquals(0, ghost.counters.size());
	}

	@Test
	public void momentaryBlink()
	{
		this.addDeck(MomentaryBlink.class, Sprout.class, GrizzlyBears.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		this.castAndResolveSpell(Sprout.class, "G");

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		this.castAndResolveSpell(MomentaryBlink.class, "1W");

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		this.castAndResolveSpell(GrizzlyBears.class, "1G");

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		this.castAndResolveSpell(MomentaryBlink.class, "3U");

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals("Momentary Blink", this.game.actualState.exileZone().objects.get(0).getName());
	}

	@Test
	public void necroticPlague()
	{
		addDeck(Plains.class, Plains.class, TormodsCrypt.class, SleeperAgent.class, MoggFanatic.class, NecroticPlague.class, MoggFanatic.class, TundraWolves.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(SleeperAgent.class);
		// Automatically choose the opponent to send the Sleeper Agent to
		pass();
		pass();

		castAndResolveSpell(MoggFanatic.class);
		castAndResolveSpell(NecroticPlague.class, MoggFanatic.class);

		respondWith(getAbilityAction(MoggFanatic.SacPing.class));
		respondWith(getTarget(player(1)));
		// Automatically choose the Sleeper Agent for the Necrotic Plague

		// Resolve the Necrotic Plague trigger
		pass();
		pass();
		// Resolve the Mogg Fanatic ability
		pass();
		pass();

		castAndResolveSpell(MoggFanatic.class);
		castAndResolveSpell(TundraWolves.class);

		// Cast and resolve Tormod's Crypt
		respondWith(getSpellAction(TormodsCrypt.class));
		pass();
		pass();

		// Player1's turn
		goToPhase(Phase.PhaseType.BEGINNING);
		respondWith(pullChoice(NecroticPlague.SacrificeThisAtTheBeginningOfYourUpkeep.class), pullChoice(SleeperAgent.Betray.class));
		// Player1 loses 2 life
		pass();
		pass();
		// Let the Necrotic Plague kill the Sleeper Agent
		pass();
		pass();

		// Player1 should be choosing the target for Necrotic Plague's triggered
		// ability
		assertEquals(this.choosingPlayerID, player(1).ID);
		respondWith(getTarget(TundraWolves.class));

		// Play a land to prevent discarding at the end of the turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(Plains.class));

		// Player0's turn
		goToPhase(Phase.PhaseType.BEGINNING);
		// Let the Necrotic Plague kill the Tundra Wolves
		pass();
		pass();

		// Necrotic Plague trigger stacks and automatically targets Mogg Fanatic

		// In response to the Necrotic Plague trigger, empty Player0's graveyard
		respondWith(getAbilityAction(TormodsCrypt.GraveyardWipe.class));
		respondWith(getTarget(player(0)));
		pass();
		pass();

		// Resolve the Necrotic Plague trigger
		pass();
		pass();

		// Plains, Mogg Fanatic
		assertEquals(2, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void nomadMythmaker()
	{
		this.addDeck(NomadMythmaker.class, MindRot.class, HolyStrength.class, UnholyStrength.class, GrizzlyBears.class, ChaosCharm.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(NomadMythmaker.class));
		this.addMana("2W");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(ChaosCharm.class));
		this.respondWith(this.getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// Auto-target Mythmaker
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(GrizzlyBears.class));
		this.addMana("1G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(MindRot.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("2B");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(this.pullChoice(UnholyStrength.class), this.pullChoice(HolyStrength.class));

		this.respondWith(this.getAbilityAction(NomadMythmaker.PutAuraOntoTheBattlefield.class));
		this.respondWith(this.getTarget(UnholyStrength.class));
		this.addMana("W");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(this.pullChoice(GrizzlyBears.class));

		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getToughness());
	}

	@Test
	public void omnathLocusOfMana()
	{
		this.addDeck(OmnathLocusofMana.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.castAndResolveSpell(OmnathLocusofMana.class, "2G");

		this.addMana("GGWW");
		this.pass();
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		this.addMana("GGWW");

		this.goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		assertEquals(2, this.player(0).pool.converted());
		assertEquals(0, this.player(1).pool.converted());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());

		this.goToStep(Step.StepType.UPKEEP);
		assertEquals(2, this.player(0).pool.converted());
		assertEquals(0, this.player(1).pool.converted());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
	}

	@Test
	public void plagiarize()
	{
		this.addDeck(Inspiration.class, Inspiration.class, Inspiration.class, Inspiration.class, Inspiration.class, Plagiarize.class, Plagiarize.class, Plagiarize.class, Plagiarize.class, Plagiarize.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(Inspiration.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("3U");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(6, this.getHand(0).objects.size());
		assertEquals(9, this.getHand(1).objects.size());

		this.respondWith(this.getSpellAction(Inspiration.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("3U");
		this.donePlayingManaAbilities();

		assertEquals(5, this.getHand(0).objects.size());
		assertEquals(9, this.getHand(1).objects.size());

		this.respondWith(this.getSpellAction(Plagiarize.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("3U");
		this.donePlayingManaAbilities();

		assertEquals(4, this.getHand(0).objects.size());
		assertEquals(9, this.getHand(1).objects.size());

		// Resolve the plagiarize
		this.pass();
		this.pass();

		assertEquals(4, this.getHand(0).objects.size());
		assertEquals(9, this.getHand(1).objects.size());

		// Resolve the inspiration
		this.pass();
		this.pass();

		assertEquals(6, this.getHand(0).objects.size());
		assertEquals(9, this.getHand(1).objects.size());

		this.goToStep(Step.StepType.DRAW);

		assertTrue(this.winner.ID == this.player(0).ID);
	}

	@Test
	public void polymorph()
	{
		this.addDeck(
		// cards still in library
		Plains.class, Plains.class, Vigor.class, Plains.class, Plains.class,
		// opening seven
		RagingGoblin.class, Polymorph.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.castAndResolveSpell(RagingGoblin.class, "R");
		this.castAndResolveSpell(Polymorph.class, "3U"); // auto-target goblin

		assertEquals("Vigor", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(4, this.getLibrary(0).objects.size());
		assertEquals("Plains", this.getLibrary(0).objects.get(0).getName());
		assertEquals("Plains", this.getLibrary(0).objects.get(1).getName());
		assertEquals("Plains", this.getLibrary(0).objects.get(2).getName());
		assertEquals("Plains", this.getLibrary(0).objects.get(3).getName());
	}

	@Test
	public void preyUpon()
	{
		this.addDeck(PreyUpon.class, PreyUpon.class, SleeperAgent.class, WallofAir.class, Terminate.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(SleeperAgent.class);

		// resolve trigger
		pass();
		pass();

		castAndResolveSpell(WallofAir.class);

		assertEquals(2, this.game.actualState.battlefield().objects.size());

		GameObject battleRampart = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Wall of Air", battleRampart.getName());
		assertEquals(1, battleRampart.getPower());
		assertEquals(5, battleRampart.getToughness());
		assertEquals(0, battleRampart.getDamage());

		GameObject sleeperAgent = this.game.actualState.battlefield().objects.get(1);
		assertEquals("Sleeper Agent", sleeperAgent.getName());
		assertEquals(3, sleeperAgent.getPower());
		assertEquals(3, sleeperAgent.getToughness());
		assertEquals(0, sleeperAgent.getDamage());

		// targets chosen automatically
		castAndResolveSpell(PreyUpon.class);

		assertEquals(2, this.game.actualState.battlefield().objects.size());

		battleRampart = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Wall of Air", battleRampart.getName());
		assertEquals(1, battleRampart.getPower());
		assertEquals(5, battleRampart.getToughness());
		assertEquals(3, battleRampart.getDamage());

		sleeperAgent = this.game.actualState.battlefield().objects.get(1);
		assertEquals("Sleeper Agent", sleeperAgent.getName());
		assertEquals(3, sleeperAgent.getPower());
		assertEquals(3, sleeperAgent.getToughness());
		assertEquals(1, sleeperAgent.getDamage());

		respondWith(getSpellAction(PreyUpon.class));
		// targets chosen automatically
		addMana("G");
		donePlayingManaAbilities();

		assertEquals(2, this.game.actualState.battlefield().objects.size());

		battleRampart = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Wall of Air", battleRampart.getName());
		assertEquals(1, battleRampart.getPower());
		assertEquals(5, battleRampart.getToughness());
		assertEquals(3, battleRampart.getDamage());

		sleeperAgent = this.game.actualState.battlefield().objects.get(1);
		assertEquals("Sleeper Agent", sleeperAgent.getName());
		assertEquals(3, sleeperAgent.getPower());
		assertEquals(3, sleeperAgent.getToughness());
		assertEquals(1, sleeperAgent.getDamage());

		castAndResolveSpell(Terminate.class, WallofAir.class);

		assertEquals(1, this.game.actualState.battlefield().objects.size());

		sleeperAgent = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Sleeper Agent", sleeperAgent.getName());
		assertEquals(3, sleeperAgent.getPower());
		assertEquals(3, sleeperAgent.getToughness());
		assertEquals(1, sleeperAgent.getDamage());

		// resolve prey upon (should do nothing, but not be countered)
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());

		sleeperAgent = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Sleeper Agent", sleeperAgent.getName());
		assertEquals(3, sleeperAgent.getPower());
		assertEquals(3, sleeperAgent.getToughness());
		assertEquals(1, sleeperAgent.getDamage());
	}

	@Test
	public void primalBeyondElementalAbilities()
	{
		this.addDeck(PrimalBeyond.class, CharRumbler.class, CharRumbler.class, BlackLotus.class, BlackLotus.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(PrimalBeyond.class));
		this.respondWith(Answer.YES);
		// Choose which char rumbler to reveal
		this.respondWith(this.pullChoice(CharRumbler.class));

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(CharRumbler.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED, Color.RED, Color.RED, Color.RED));
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Char-Rumbler"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getPower() == -1);

		this.respondWith(this.getAbilityAction(org.rnd.jmagic.abilities.Firebreathing.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Char-Rumbler"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getPower());

		this.respondWith(this.getAbilityAction(org.rnd.jmagic.abilities.Firebreathing.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Char-Rumbler"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());

		this.respondWith(this.getAbilityAction(org.rnd.jmagic.abilities.Firebreathing.class));
		this.respondWith(this.getAbilityAction(PrimalBeyond.MakeElementalMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Char-Rumbler"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());

	}

	@Test
	public void primalBeyondElementalSpells()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, PrimalBeyond.class, SparkElemental.class, Shock.class, Twitch.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		// Play a primal beyond, tapped
		this.respondWith(this.getLandAction(PrimalBeyond.class));
		this.respondWith(Answer.NO);

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Primal Beyond"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Twitch.class));
		this.respondWith(this.getTarget(PrimalBeyond.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Primal Beyond"));
		assertTrue(!this.game.actualState.battlefield().objects.get(0).isTapped());

		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.respondWith(this.getAbilityAction(PrimalBeyond.MakeElementalMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.player(0).pool.converted());

		this.respondWith(this.getSpellAction(SparkElemental.class));
		this.respondWith(this.getAbilityAction(PrimalBeyond.MakeElementalMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.player(0).pool.converted());

		this.pass();
		this.pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.player(0).pool.converted());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Spark Elemental"));
	}

	@Test
	public void proteanHydra()
	{
		this.addDeck(ProteanHydra.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Excruciator.class, ChaosCharm.class, Shock.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(ProteanHydra.class));
		this.respondWith(17);
		this.addMana("(17)G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(17, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());

		this.goToPhase(Phase.PhaseType.BEGINNING);
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(Excruciator.class, "6RR");

		this.respondWith(this.getSpellAction(ChaosCharm.class));
		this.respondWith(this.getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		this.respondWith(this.getTarget(Excruciator.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToStep(Step.StepType.DECLARE_ATTACKERS);
		this.respondWith(this.pullChoice(Excruciator.class));

		this.goToStep(Step.StepType.DECLARE_BLOCKERS);
		this.respondWith(this.pullChoice(ProteanHydra.class));

		this.goToStep(Step.StepType.COMBAT_DAMAGE);

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(10, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(7, this.game.actualState.battlefield().objects.get(0).getDamage());

		// Stack all the triggers
		this.respondArbitrarily();

		this.castAndResolveSpell(Shock.class, "R", ProteanHydra.class);

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(8, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(7, this.game.actualState.battlefield().objects.get(0).getDamage());

		// Stack the triggers the shock caused too
		this.respondArbitrarily();

		this.goToStep(Step.StepType.END);

		// Stack the 9 triggers that happened this turn
		assertEquals(9, this.choices.size());
		this.respondArbitrarily();

		for(int i = 0; i < 9; i++)
		{
			assertEquals(9 - i, this.game.actualState.stack().objects.size());
			assertEquals(1, this.game.actualState.battlefield().objects.size());
			assertEquals(8 + (2 * i), this.game.actualState.battlefield().objects.get(0).counters.size());
			assertEquals(7, this.game.actualState.battlefield().objects.get(0).getDamage());

			this.pass();
			this.pass();
		}

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(26, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(7, this.game.actualState.battlefield().objects.get(0).getDamage());
	}

	@Test
	public void questForPureFlame()
	{
		this.addDeck(QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, MoggFanatic.class);
		this.addDeck(Shock.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, MoggFanatic.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.respondWith(this.getSpellAction(MoggFanatic.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.respondWith(this.getSpellAction(QuestforPureFlame.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// mogg fan pings 1 (will trigger quest)
		this.respondWith(this.getAbilityAction(MoggFanatic.SacPing.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.pass();

		// 1 shocks himself (will NOT trigger quest)
		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("R");
		this.donePlayingManaAbilities();

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		this.pass();
		this.pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(18, this.player(1).lifeTotal);
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		this.pass();
		this.pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(17, this.player(1).lifeTotal);
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(17, this.player(1).lifeTotal);
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).counters.size());
	}

	@Test
	public void relentlessRats()
	{
		this.addDeck(RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);

		this.respondWith(this.getSpellAction(RelentlessRats.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLACK, Color.BLACK, Color.BLACK));
		this.pass();
		this.pass();

		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		this.respondWith(this.getSpellAction(RelentlessRats.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLACK, Color.BLACK, Color.BLACK));
		this.pass();
		this.pass();

		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getToughness());

		this.respondWith(this.getSpellAction(RelentlessRats.class));
		this.donePlayingManaAbilities();
		// auto-choose BBB
		this.pass();
		this.pass();

		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(4, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(2).getToughness());

	}

	@Test
	public void rhox()
	{
		// AKA Super Tramp(le)
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, IndomitableAncients.class, IndomitableAncients.class, IndomitableAncients.class);
		this.addDeck(ChaosCharm.class, Rhox.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, GiantGrowth.class, GiantGrowth.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(IndomitableAncients.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		this.pass();
		this.pass();

		Identified IndomitableAncientsA = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(IndomitableAncients.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		this.pass();
		this.pass();

		Identified IndomitableAncientsB = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(IndomitableAncients.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		// auto-select remaining WWWW
		this.pass();
		this.pass();

		Identified IndomitableAncientsC = this.game.physicalState.battlefield().objects.get(0);

		// End the turn
		// Pass 1st Main Phase
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main Phase
		this.pass();
		this.pass();
		// Pass End of Turn
		this.pass();
		this.pass();

		// pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Rhox.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED, Color.RED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(ChaosCharm.class));
		this.respondWith(this.getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		this.respondWith(this.getTarget(Rhox.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(GiantGrowth.class));
		this.respondWith(this.getTarget(Rhox.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.GREEN));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(GiantGrowth.class));
		this.respondWith(this.getTarget(Rhox.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Rhox"));
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getToughness());

		assertEquals(IndomitableAncientsC, this.game.actualState.battlefield().objects.get(1));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(1).getToughness());

		assertEquals(IndomitableAncientsB, this.game.actualState.battlefield().objects.get(2));
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(2).getToughness());

		assertEquals(IndomitableAncientsA, this.game.actualState.battlefield().objects.get(3));
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(3).getToughness());

		// pass main
		this.pass();
		this.pass();

		// pass beginning of combat
		this.pass();
		this.pass();

		// declare attackers
		this.respondWith(this.pullChoice(Rhox.class));
		this.pass();
		this.pass();

		// declare blockers
		this.respondWith(this.pullChoice(IndomitableAncients.class), this.pullChoice(IndomitableAncients.class), this.pullChoice(IndomitableAncients.class));
		this.respondArbitrarily();
		this.pass();
		this.pass();

		assertEquals("Rhox", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());

		assertEquals(IndomitableAncientsC, this.game.actualState.battlefield().objects.get(1));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getDamage());

		assertEquals(IndomitableAncientsB, this.game.actualState.battlefield().objects.get(2));
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());

		assertEquals(IndomitableAncientsA, this.game.actualState.battlefield().objects.get(3));
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(3).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).getDamage());

		assertEquals(20, this.player(0).lifeTotal);

		// combat damage
		// assign 1 to the player and 5 to two ancients (illegal assignment)
		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		divisions.put(IndomitableAncientsA.ID, 5);
		divisions.put(IndomitableAncientsB.ID, 5);
		divisions.put(IndomitableAncientsC.ID, 0);
		divisions.put(this.player(0).ID, 1);
		this.divide(divisions);

		// go ahead and assign all 11 to player 0 (as though Rhox were
		// unblocked)
		divisions.put(IndomitableAncientsA.ID, 0);
		divisions.put(IndomitableAncientsB.ID, 0);
		divisions.put(IndomitableAncientsC.ID, 0);
		divisions.put(this.player(0).ID, 11);
		this.divide(divisions);

		assertEquals("Rhox", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getDamage());

		assertEquals(IndomitableAncientsC, this.game.actualState.battlefield().objects.get(1));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getDamage());

		assertEquals(IndomitableAncientsB, this.game.actualState.battlefield().objects.get(2));
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());

		assertEquals(IndomitableAncientsA, this.game.actualState.battlefield().objects.get(3));
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(3).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).getDamage());

		assertEquals(9, this.player(0).lifeTotal);
	}

	@Test
	public void seedbornMuse()
	{
		this.addDeck(SeedbornMuse.class, Forest.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Forest.class));

		this.respondWith(this.getSpellAction(SeedbornMuse.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.GREEN, Color.GREEN, Color.BLACK, Color.BLACK, Color.BLACK));
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(1, this.player(0).pool.converted());

		this.respondWith(this.getIntrinsicAbilityAction(SubType.FOREST));

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(2, this.player(0).pool.converted());

		// End the turn
		// Pass 1st Main Phase
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main Phase
		this.pass();
		this.pass();
		// Pass End of Turn
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(0, this.player(0).pool.converted());

		this.pass();
		this.respondWith(this.getIntrinsicAbilityAction(SubType.FOREST));

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(1, this.player(0).pool.converted());

	}

	@Test
	public void sleep()
	{
		this.addDeck(Sleep.class, Sleep.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Sprout.class, Sprout.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.pass();
		this.castAndResolveSpell(Sprout.class, "G");

		this.respondWith(this.getSpellAction(Sleep.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("2UU");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToPhase(Phase.PhaseType.ENDING);

		// player(1)'s turn 1:
		this.goToPhase(Phase.PhaseType.BEGINNING);
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());
		this.goToPhase(Phase.PhaseType.ENDING);

		// player(0)'s turn 2:
		this.goToPhase(Phase.PhaseType.BEGINNING);
		this.goToPhase(Phase.PhaseType.ENDING);

		// player(1)'s turn 2:
		this.goToPhase(Phase.PhaseType.BEGINNING);
		assertFalse(this.game.actualState.battlefield().objects.get(0).isTapped());
	}

	@Test
	public void sorinMarkov()
	{
		this.addDeck(SorinMarkov.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(LightningBolt.class, MoggFanatic.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(SorinMarkov.class));
		this.addMana("3BBB");
		this.donePlayingManaAbilities();
		this.game.physicalState.stack().objects.get(0).setPrintedLoyalty(8);
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(SorinMarkov.Mindslaver.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.pass();
		this.pass();

		this.goToPhase(Phase.PhaseType.BEGINNING);
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals(this.player(0).ID, this.choosingPlayerID);

		this.respondWith(this.getLandAction(Plains.class));
		assertEquals("Plains", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(this.player(1).ID, this.game.actualState.battlefield().objects.get(0).controllerID);

		this.respondWith(this.getSpellAction(MoggFanatic.class));
		this.player(1).pool.addAll(new ManaPool("R"));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals("Mogg Fanatic", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(this.player(1).ID, this.game.actualState.battlefield().objects.get(0).controllerID);

		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(MoggFanatic.class));
		this.player(1).pool.addAll(new ManaPool("R"));
		this.donePlayingManaAbilities();
		assertEquals(this.player(1).ID, this.game.actualState.stack().objects.get(0).controllerID);
		this.pass();
		this.pass();
	}

	@Test
	public void soulblast()
	{

		this.addDeck(IronshellBeetle.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Soulblast.class, Soulblast.class, Soulblast.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(IronshellBeetle.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.GREEN, Color.GREEN));
		this.pass();
		this.pass();

		// Resolve beetle cip ability
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Ironshell Beetle"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());

		this.respondWith(this.getSpellAction(Soulblast.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED, Color.RED, Color.RED, Color.BLUE, Color.BLUE, Color.BLUE));
		this.pass();
		this.pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(18, this.player(1).lifeTotal);
	}

	@Test
	public void teferi()
	{
		this.addDeck(TeferiMageofZhalfir.class, GrizzlyBears.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, ElvishWarrior.class, BenalishKnight.class, LightningBolt.class, LightningBolt.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.pass();

		// Player 1 has 3 choices (knight, bolt, bolt)
		assertEquals(3, this.choices.size());
		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();

		// Respond with Teferi
		assertEquals(1, this.choices.size());
		this.castAndResolveSpell(TeferiMageofZhalfir.class, "2UUU");

		// Resolve the bolt
		this.pass();
		this.pass();

		this.pass();

		// Player 1 has no actions
		assertEquals(0, this.choices.size());

		// Player 1's turn
		this.goToPhase(Phase.PhaseType.BEGINNING);
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Player 1 has 7 actions
		assertEquals(7, this.choices.size());

		this.respondWith(this.getSpellAction(ElvishWarrior.class));
		this.addMana("GG");
		this.donePlayingManaAbilities();

		// Player 1 has no actions
		assertEquals(0, this.choices.size());
		this.pass();

		// Player 0 has 1 action
		assertEquals(1, this.choices.size());
		this.castAndResolveSpell(GrizzlyBears.class, "1G");
	}

	@Test
	public void terramorphicExpanseFindNothing()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, TerramorphicExpanse.class, TerramorphicExpanse.class, TerramorphicExpanse.class, TerramorphicExpanse.class, TerramorphicExpanse.class, TerramorphicExpanse.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, TerramorphicExpanse.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getLandAction(TerramorphicExpanse.class));

		this.respondWith(this.getAbilityAction(TerramorphicExpanse.Terraform.class));
		this.pass();
		this.pass();

		// choose to find nothing
		this.respondWith();
		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void thrullSurgeon()
	{
		this.addDeck(ThrullSurgeon.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.respondWith(this.getSpellAction(ThrullSurgeon.class));
		this.addMana("1B");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(ThrullSurgeon.ThrullSurgeonAbility.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("1B");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(7, this.choices.size());
		this.respondWith(this.pullChoice(RagingGoblin.class));

		assertEquals(1, this.getGraveyard(1).objects.size());
	}

	@Test
	public void timeStopMakeSureExileHappens()
	{
		// Make sure things are removed from the game properly and that the turn
		// is ended
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, Shock.class, Shock.class, MoggFanatic.class, TimeStop.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(MoggFanatic.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));

		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));

		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.donePlayingManaAbilities();
		// Auto-choose R

		this.respondWith(this.getSpellAction(TimeStop.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		// Auto-choose UUUUUU
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(0, this.player(0).pool.converted());
		assertEquals(0, this.getHand(0).objects.size());
		assertEquals(0, this.getLibrary(0).objects.size());
		assertEquals(3, this.getGraveyard(0).objects.size());
		assertTrue(this.getGraveyard(0).objects.get(0).getName().equals("Black Lotus"));
		assertTrue(this.getGraveyard(0).objects.get(1).getName().equals("Black Lotus"));
		assertTrue(this.getGraveyard(0).objects.get(2).getName().equals("Black Lotus"));

		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.player(1).pool.converted());
		assertEquals(7, this.getHand(1).objects.size());
		assertEquals(0, this.getLibrary(1).objects.size());
		assertEquals(0, this.getGraveyard(1).objects.size());

		assertEquals(4, this.game.actualState.exileZone().objects.size());

		assertTrue(this.game.actualState.currentStep().type == Step.StepType.UPKEEP);

	}

	@Test
	public void timeStopTriggeredAbilitiesDuringCleanup()
	{
		// Make sure triggered abilities that trigger during cleanup still occur
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, Megrim.class, TimeStop.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, BlackLotus.class, BlackLotus.class, Meditate.class, Meditate.class, Mountain.class, Shock.class);
		this.startGame(GameType.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Megrim.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// End the turn (normally)
		// Pass 1st Main Phase
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main Phase
		this.pass();
		this.pass();
		// Pass End of Turn
		this.pass();
		this.pass();

		// pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Meditate.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Meditate.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(12, this.getHand(1).objects.size());

		this.respondWith(this.getLandAction(Mountain.class));

		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.MOUNTAIN));
		this.donePlayingManaAbilities();

		assertEquals(10, this.getHand(1).objects.size());

		this.pass();
		this.respondWith(this.getSpellAction(TimeStop.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(10, this.getHand(1).objects.size());

		// Discard 3 cards
		this.respondWith(this.pullChoice(Plains.class), this.pullChoice(Plains.class), this.pullChoice(Plains.class));

		// Stack the 3 megrim triggers
		this.respondWith(this.pullChoice(Megrim.MegrimShock.class), this.pullChoice(Megrim.MegrimShock.class), this.pullChoice(Megrim.MegrimShock.class));

		assertEquals(3, this.game.actualState.stack().objects.size());
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.CLEANUP);
		assertEquals(20, this.player(1).lifeTotal);

		// Resolve the triggers
		this.pass();
		this.pass();
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.CLEANUP);
		assertEquals(14, this.player(1).lifeTotal);

		// Exit the cleanup step
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.currentStep().type == Step.StepType.UPKEEP);

	}

	@Test
	public void upwelling()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Upwelling.class, Upwelling.class, Upwelling.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameType.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Upwelling.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED, Color.RED, Color.GREEN, Color.GREEN));
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertTrue(this.player(0).pool.toArray(new ManaSymbol[0])[0].colors.contains(org.rnd.jmagic.engine.Color.RED));
		assertTrue(this.player(0).pool.toArray(new ManaSymbol[0])[1].colors.contains(org.rnd.jmagic.engine.Color.GREEN));

		this.goToPhase(Phase.PhaseType.COMBAT);
		assertEquals(20, this.player(0).lifeTotal);
		assertTrue(this.player(0).pool.toArray(new ManaSymbol[0])[0].colors.contains(org.rnd.jmagic.engine.Color.RED));
		assertTrue(this.player(0).pool.toArray(new ManaSymbol[0])[1].colors.contains(org.rnd.jmagic.engine.Color.GREEN));
	}

	@Test
	public void wildEvocation()
	{
		this.addDeck(WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class);
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, WheelofFortune.class, WheelofFortune.class, WheelofFortune.class, WheelofFortune.class, WheelofFortune.class, WheelofFortune.class, WheelofFortune.class);
		startGame(GameType.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(WildEvocation.class, "5R");
		castAndResolveSpell(WildEvocation.class, "5R");

		goToPhase(Phase.PhaseType.BEGINNING);

		// Stack the wild evocation triggers
		respondArbitrarily();

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		// Resolve a wild evocation trigger (p1 casts wheel of fortune)
		pass();
		pass();

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		// Resolve Wheel of Fortune
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		// Resolve a wild evocation trigger (p1 puts forest into play)
		pass();
		pass();

		Zone battlefield = this.game.actualState.battlefield();
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, battlefield.objects.size());
		assertEquals("Forest", battlefield.objects.get(0).getName());
		assertEquals("Wild Evocation", battlefield.objects.get(1).getName());
		assertEquals("Wild Evocation", battlefield.objects.get(2).getName());
	}

	@Test
	public void yawgmothsWill() // ... dawg
	{
		this.addDeck(YawgmothsWill.class, OnewithNothing.class, Forest.class, LightningBolt.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(YawgmothsWill.class));
		addMana("2B");
		donePlayingManaAbilities();

		castAndResolveSpell(OnewithNothing.class);
		assertEquals(6, getGraveyard(0).objects.size());

		// resolve yawgdawg's will:
		pass();
		pass();
		// will of yawgadawg exiles itself
		assertEquals(6, getGraveyard(0).objects.size());

		respondWith(getLandAction(Forest.class));
		assertEquals(5, getGraveyard(0).objects.size());

		castAndResolveSpell(LightningBolt.class, "R", player(1));
		assertEquals(4, getGraveyard(0).objects.size());
	}
}
