package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import java.lang.reflect.*;

import org.junit.*;
import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.sanitized.*;

public class CopyTest extends JUnitTest
{
	@Test
	public void cloneIona() throws NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		this.addDeck(Clone.class, IonaShieldofEmeria.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Shock.class, ReachThroughMists.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(IonaShieldofEmeria.class);
		respondWith(Color.BLUE);

		// REMOVE LEGENDARY FROM IONA. REFLECTION IS KING.
		{
			Method method = GameObject.class.getDeclaredMethod("removeSuperTypes", java.util.Collection.class);
			method.setAccessible(true);
			method.invoke(this.game.physicalState.battlefield().objects.get(0), java.util.Collections.singleton(SuperType.LEGENDARY));
		}

		castAndResolveSpell(Clone.class);
		respondWith(Answer.YES);
		respondWith(Color.RED);

		pass();

		assertEquals(0, this.choices.size());
		assertEquals(player(1).ID, this.choosingPlayerID);
	}

	@Test
	public void cloneComesIntoPlayReplacementEffect()
	{
		this.addDeck(Clone.class, VoiceofAll.class, Boomerang.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(VoiceofAll.class));
		addMana("2WW");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(Color.BLUE);

		respondWith(getSpellAction(Clone.class));
		addMana("3U");
		donePlayingManaAbilities();
		pass();
		pass();
		// copy a creature:
		respondWith(Answer.YES);
		// auto-choose voice

		respondWith(Color.GREEN);

		// Assert the original's power & toughness
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getToughness());

		// Assert the copy's power & toughness
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		// auto-target the one that has green chosen
		castAndResolveSpell(Boomerang.class);

		assertEquals(1, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void cloneComesIntoPlayTrigger()
	{
		this.addDeck(Clone.class, RavenousRats.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(RavenousRats.class));
		addMana("1B");
		donePlayingManaAbilities();
		pass();
		pass();

		// auto-target Player 1
		pass();
		pass();
		respondWith(pullChoice(Plains.class));

		respondWith(getSpellAction(Clone.class));
		addMana("3U");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(Answer.YES);

		assertEquals("Ravenous Rats", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getStaticAbilities().size());

		assertEquals("Ravenous Rats", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getStaticAbilities().size());

		assertEquals("Clone", this.game.physicalState.battlefield().objects.get(0).getName());
		assertEquals(0, this.game.physicalState.battlefield().objects.get(0).getPower());
		assertEquals(0, this.game.physicalState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.physicalState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(1, this.game.physicalState.battlefield().objects.get(0).getStaticAbilities().size());

		assertEquals("Ravenous Rats", this.game.physicalState.battlefield().objects.get(1).getName());
		assertEquals(1, this.game.physicalState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.physicalState.battlefield().objects.get(1).getToughness());
		assertEquals(1, this.game.physicalState.battlefield().objects.get(1).getNonStaticAbilities().size());
		assertEquals(0, this.game.physicalState.battlefield().objects.get(1).getStaticAbilities().size());

		assertEquals(1, this.game.actualState.stack().objects.size());

		// auto-target Player 1
		pass();
		pass();
		respondWith(pullChoice(Plains.class));

		assertEquals(5, getHand(1).objects.size());

	}

	@Test
	public void cloneConditionalStaticAbility()
	{
		// cloning a briarberry cohort means both are 2/2
		this.addDeck(Clone.class, BriarberryCohort.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(BriarberryCohort.class));
		addMana("1U");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getSpellAction(Clone.class));
		addMana("3U");
		donePlayingManaAbilities();
		pass();
		pass();
		// copy a creature:
		respondWith(Answer.YES);
		// auto-choose cohort

		// Assert the original's power & toughness
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getToughness());

		// Assert the copy's power & toughness
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());
	}

	@Test
	public void clonePrimalClay()
	{
		this.addDeck(Clone.class, PrimalClay.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(PrimalClay.class);
		respondWith("2/2 artifact creature with flying");

		castAndResolveSpell(Clone.class);
		respondWith(Answer.YES);
		respondWith("3/3 artifact creature");

		GameObject clone = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Primal Clay", clone.getName());
		assertEquals(1, clone.getKeywordAbilities().size());
	}

	@Test
	public void cloneWithKeywords()
	{
		this.addDeck(Clone.class, VirulentSliver.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		this.addDeck(Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(VirulentSliver.class));
		addMana("G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Clone.class));
		addMana("3U");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(Answer.YES);
		// automatically choose Virulent Sliver

		assertTrue(this.game.physicalState.battlefield().objects.get(0).getName().equals("Clone"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals("Virulent Sliver", this.game.actualState.battlefield().objects.get(0).getName());

		java.util.Iterator<Keyword> iter = this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().iterator();
		assertTrue(iter.next() instanceof org.rnd.jmagic.abilities.keywords.Poisonous);
		assertTrue(iter.next() instanceof org.rnd.jmagic.abilities.keywords.Poisonous);
		assertFalse(iter.hasNext());
	}

	// this test currently exposes the bug where zone-change replacement effects
	// are not properly applied to something that "enters the battlefield as a
	// copy of" something.
	@Test
	public void cloneWithReplacementEffects()
	{
		this.addDeck(Clone.class, ScarwoodTreefolk.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(ScarwoodTreefolk.class, "3G");

		castAndResolveSpell(Clone.class, "3U");
		respondWith(Answer.YES);
		// auto-choose treefolk

		// The original
		assertEquals("Scarwood Treefolk", this.game.actualState.battlefield().objects.get(1).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(1).isTapped());

		// The clone
		assertEquals("Scarwood Treefolk", this.game.actualState.battlefield().objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());
	}

	@Test
	public void copyingLayer8Abilities()
	{
		this.addDeck(RingsofBrighthearth.class, CraftyPathmage.class, Sprout.class, Sprout.class, BurstofSpeed.class, Plains.class, Plains.class);
		this.addDeck(Sprout.class, Sprout.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RingsofBrighthearth.class, "3");
		castAndResolveSpell(CraftyPathmage.class, "2U");
		castAndResolveSpell(Sprout.class, "G");
		castAndResolveSpell(Sprout.class, "G");
		castAndResolveSpell(BurstofSpeed.class, "R");

		Identified attackerA = this.game.physicalState.battlefield().objects.get(0);
		Identified attackerB = this.game.physicalState.battlefield().objects.get(1);

		pass();
		castAndResolveSpell(Sprout.class, "G");
		pass();
		castAndResolveSpell(Sprout.class, "G");

		Identified blockerA = this.game.physicalState.battlefield().objects.get(0);
		Identified blockerB = this.game.physicalState.battlefield().objects.get(1);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(getChoice(attackerA), getChoice(attackerB));
		respondWith(getAbilityAction(CraftyPathmage.Cloak.class));
		respondWith(getTarget(attackerA));

		// pathmage's ability, and the rings trigger
		assertEquals(2, this.game.actualState.stack().objects.size());

		pass();
		pass();

		addMana("2");
		donePlayingManaAbilities();
		// Copy the ability
		respondWith(Answer.YES);
		// Change the target
		respondWith(Answer.YES);
		respondWith(getTarget(attackerB));

		goToStep(Step.StepType.DECLARE_BLOCKERS);

		// First, try to block them both
		assertEquals(PlayerInterface.ChoiceType.BLOCK, this.choiceType);
		respondWith(getChoice(blockerA), getChoice(blockerB));
		respondWith(getChoice(attackerA));
		respondWith(getChoice(attackerB));

		// Second, both block attacker A
		assertEquals(PlayerInterface.ChoiceType.BLOCK, this.choiceType);
		respondWith(getChoice(blockerA), getChoice(blockerB));
		respondWith(getChoice(attackerA));
		respondWith(getChoice(attackerA));

		// Third, both block attacker B
		assertEquals(PlayerInterface.ChoiceType.BLOCK, this.choiceType);
		respondWith(getChoice(blockerA), getChoice(blockerB));
		respondWith(getChoice(attackerB));
		respondWith(getChoice(attackerB));

		// Finally, give up and let them both through
		assertEquals(PlayerInterface.ChoiceType.BLOCK, this.choiceType);
		respondWith();

		// This block was accepted
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);

		goToStep(Step.StepType.COMBAT_DAMAGE);

		assertEquals(18, player(1).lifeTotal);
	}

	@Test
	public void copySpellChosenInformation()
	{
		this.addDeck(Twincast.class, BurstLightning.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(BurstLightning.class));
		this.respondWith(this.getCostCollection(Kicker.COST_TYPE, "4"));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("4R");
		this.donePlayingManaAbilities();

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(20, this.player(1).lifeTotal);

		// The Twincast will auto-target the Burst Lightning
		this.castAndResolveSpell(Twincast.class, "UU");

		// Choose not to change targets
		this.respondWith(Answer.NO);

		// Resolve the copy of Burst Lightning
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(16, this.player(1).lifeTotal);

		// Resolve the original Burst Lightning
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(12, this.player(1).lifeTotal);
	}

	@Test
	public void retainCharacteristics()
	{
		this.addDeck(Tarmogoyf.class, QuicksilverGargantuan.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(Tarmogoyf.class);
		castAndResolveSpell(QuicksilverGargantuan.class);
		respondWith(Answer.YES);

		GameObject gargantuan = this.game.actualState.battlefield().objects.get(0);
		assertEquals(7, gargantuan.getToughness());
		assertEquals(7, gargantuan.getPower());
		assertEquals(0, gargantuan.getAbilityIDsInOrder().size());
	}

	@Test
	public void ringsOfBrighthearth()
	{
		this.addDeck(RingsofBrighthearth.class, MoggFanatic.class, MoggFanatic.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RingsofBrighthearth.class, "3");
		castAndResolveSpell(MoggFanatic.class, "R");

		respondWith(getAbilityAction(MoggFanatic.SacPing.class));
		respondWith(getTarget(player(1)));

		// There should be two objects on the stack, the mogg fanatics ability
		// on bottom, and the rings trigger on top.
		assertEquals(2, this.game.actualState.stack().objects.size());

		pass();
		pass();

		// activating mana abilities for rings
		addMana("2");
		donePlayingManaAbilities();

		// Do you want to copy the ability?
		respondWith(Answer.YES);

		// Do you want to change the target(s)?
		respondWith(Answer.NO);

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(20, player(1).lifeTotal);

		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(19, player(1).lifeTotal);

		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(18, player(1).lifeTotal);

		castAndResolveSpell(MoggFanatic.class, "R");
		respondWith(getAbilityAction(MoggFanatic.SacPing.class));
		respondWith(getTarget(player(1)));
		pass();
		pass();
		addMana("2");
		donePlayingManaAbilities();
		respondWith(Answer.YES);
		respondWith(Answer.YES);
		// automatically change the target to player 0, the only other legal
		// target

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(20, player(0).lifeTotal);
		assertEquals(18, player(1).lifeTotal);

		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(19, player(0).lifeTotal);
		assertEquals(18, player(1).lifeTotal);

		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(19, player(0).lifeTotal);
		assertEquals(17, player(1).lifeTotal);
	}

	@Test
	public void sculptingSteel()
	{
		this.addDeck(WurmsTooth.class, SculptingSteel.class, BlackLotus.class, AngelsFeather.class, BottleGnomes.class, PlatinumAngel.class, TreasureHunter.class, TolarianAcademy.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(PlatinumAngel.class));
		addMana("7");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(BottleGnomes.class));
		addMana("3");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(AngelsFeather.class));
		addMana("2");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);

		respondWith(getSpellAction(TreasureHunter.class));
		donePlayingManaAbilities();

		// resolve angel's feather
		pass();
		pass();
		respondWith(Answer.NO);

		// resolve treasure hunter
		pass();
		pass();

		// resolve treasure hunter ability, auto-target black lotus
		pass();
		pass();
		respondWith(Answer.YES);

		respondWith(getSpellAction(SculptingSteel.class));
		addMana("3");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(Answer.YES);

		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Treasure Hunter"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Angel's Feather"));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Bottle Gnomes"));
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Platinum Angel"));

		assertEquals(1, getLibrary(0).objects.size());
		assertTrue(getLibrary(0).objects.get(0).getName().equals("Wurm's Tooth"));

		assertEquals(2, getHand(0).objects.size());

		assertEquals(3, this.choices.size());
		assertTrue(this.getChoice(this.game.actualState.battlefield().objects.get(1)) != null);
		assertTrue(this.getChoice(this.game.actualState.battlefield().objects.get(2)) != null);
		assertTrue(this.getChoice(this.game.actualState.battlefield().objects.get(3)) != null);

		respondWith(pullChoice(BottleGnomes.class));

		respondWith(getLandAction(TolarianAcademy.class));

		respondWith(getAbilityAction(BottleGnomes.SacForLife.class));
		pass();
		pass();

		respondWith(getAbilityAction(BottleGnomes.SacForLife.class));
		pass();
		pass();

		assertEquals(26, player(0).lifeTotal);

		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Tolarian Academy"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Treasure Hunter"));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Angel's Feather"));
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Platinum Angel"));

		assertEquals(1, getLibrary(0).objects.size());
		assertTrue(getLibrary(0).objects.get(0).getName().equals("Wurm's Tooth"));

		assertEquals(1, getHand(0).objects.size());
		assertTrue(getHand(0).objects.get(0).getName().equals("Black Lotus"));

		assertEquals(2, getGraveyard(0).objects.size());
		String graveyard0Name = getGraveyard(0).objects.get(0).getName();
		String graveyard1Name = getGraveyard(0).objects.get(1).getName();
		assertTrue((graveyard0Name.equals("Bottle Gnomes") && graveyard1Name.equals("Sculpting Steel")) || (graveyard1Name.equals("Bottle Gnomes") && graveyard0Name.equals("Sculpting Steel")));
	}

	@Test
	public void shapesharer()
	{
		this.addDeck(Shapesharer.class, RagingGoblin.class, GiantGrowth.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		addMana("3UURG");

		respondWith(getSpellAction(Shapesharer.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.BLUE));
		pass();
		pass();

		Identified ragingGoblin = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(GiantGrowth.class));
		respondWith(getTarget(RagingGoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		respondWith(getAbilityAction(Shapesharer.ResidualSelfImage.class));
		// auto-target Shapesharer
		respondWith(getTarget(RagingGoblin.class));
		donePlayingManaAbilities();
		// auto-choose 2U
		pass();
		pass();

		Identified ragingGoblinOriginal = this.game.physicalState.battlefield().objects.get(0);

		// Raging Goblin original, Raging Goblin (Shapesharer)
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(ragingGoblinOriginal, this.game.actualState.battlefield().objects.get(0));
		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getStaticAbilities().size());
		assertEquals(ragingGoblin, this.game.actualState.battlefield().objects.get(1));
		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getKeywordAbilities().size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getStaticAbilities().size());

		respondWith(getLandAction(Plains.class));

		// Plains, Raging Goblin original, Raging Goblin (Shapesharer)
		assertEquals(ragingGoblinOriginal, this.game.actualState.battlefield().objects.get(1));
		assertEquals(ragingGoblin, this.game.actualState.battlefield().objects.get(2));

	}

	@Test
	public void spelltwine()
	{
		this.addDeck(Forest.class, Forest.class, Spelltwine.class, Spelltwine.class, Inspiration.class, OnewithNothing.class, WitsEnd.class, LightningBolt.class, VeilbornGhoul.class, TouchoftheEternal.class, GiantGrowth.class);
		this.addDeck(Spelltwine.class, Spelltwine.class, Inspiration.class, OnewithNothing.class, WitsEnd.class, LightningBolt.class, VeilbornGhoul.class, TouchoftheEternal.class, GiantGrowth.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(WitsEnd.class, "5BB", player(1));

		respondWith(getSpellAction(Inspiration.class));
		respondWith(getTarget(player(0)));
		addMana("3U");
		donePlayingManaAbilities();

		castAndResolveSpell(OnewithNothing.class);

		// Resolve Inspiration
		pass();
		pass();

		respondWith(getSpellAction(Spelltwine.class));

		// first target card in 0's yard
		assertEquals(5, this.choices.size());
		respondWith(getTarget(LightningBolt.class));

		// second target card in 1's yard
		assertEquals(5, this.choices.size());
		respondWith(getTarget(Inspiration.class));

		addMana("5U");
		donePlayingManaAbilities();

		// resolve spelltwine
		pass();
		pass();

		SanitizedIdentified lightningBoltCopy = null;
		for(SanitizedIdentified choice: this.choices.getAll(SanitizedIdentified.class))
		{
			if(choice.name.equals("Lightning Bolt"))
			{
				lightningBoltCopy = choice;
				break;
			}
		}

		respondWith(pullChoice(lightningBoltCopy), pullChoice(SpellCopy.class));

		// HERE WE SHOULD BE GETTING ASKED FOR A TARGET FOR THE LIGHTNING BOLT
		// COPY. OTHERWISE SOMETHING IS WRONG WITH THE COPY
		assertEquals(PlayerInterface.ChoiceType.TARGETS, this.choiceType);

		// target for lightning bolt
		respondWith(getTarget(player(1)));

		// target for inspiration
		respondWith(getTarget(player(0)));

		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);

		// resolve inspiration
		assertEquals(1, player(0).getHand(this.game.actualState).objects.size());
		pass();
		pass();
		assertEquals(3, player(0).getHand(this.game.actualState).objects.size());

		// resolve lightning bolt
		assertEquals(20, player(1).lifeTotal);
		pass();
		pass();
		assertEquals(17, player(1).lifeTotal);
	}

	@Test
	public void twincastHellfire()
	{
		this.addDeck(Twincast.class, Hellfire.class, ZealousGuardian.class, GrizzlyBears.class, GrizzlyBears.class, Plains.class, Plains.class);
		this.addDeck(Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(GrizzlyBears.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(GrizzlyBears.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Hellfire.class));
		addMana("2BBB");
		donePlayingManaAbilities();

		respondWith(getSpellAction(Twincast.class));
		// auto-target Hellfire
		addMana("UU");
		donePlayingManaAbilities();

		// Resolve Twincast
		pass();
		pass();

		assertEquals(20, player(0).lifeTotal);
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Grizzly Bears"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Grizzly Bears"));

		// Resolve the copy of Hellfire
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(15, player(0).lifeTotal);

	}

	@Test
	public void twincastTargetedSpell()
	{
		this.addDeck(Twincast.class, LightningBolt.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(player(1)));
		addMana("R");
		donePlayingManaAbilities();

		// cast twincast, auto-targeting lightning bolt:
		castAndResolveSpell(Twincast.class, "UU");
		// change the target:
		respondWith(Answer.YES);
		// auto-target player 0

		// resolve copy of bolt at player 0:
		pass();
		pass();
		assertEquals(17, player(0).lifeTotal);

		// resolve bolt at player 1:
		pass();
		pass();
		assertEquals(17, player(1).lifeTotal);
	}

	@Test
	public void twincastWithPaintersServant()
	{
		this.addDeck(PaintersServant.class, Twincast.class, LightningBolt.class, Shatter.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Cast Painter's Servant and set it to White, since none of the cards
		// in this test are white
		castAndResolveSpell(PaintersServant.class, "2");
		respondWith(Color.WHITE);

		// Painters Servant
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getColors().size());

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(player(1)));
		addMana("R");
		donePlayingManaAbilities();

		// Painters Servant
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getColors().size());
		// Lightning Bolt
		assertEquals(2, this.game.actualState.stack().objects.get(0).getColors().size());

		castAndResolveSpell(Twincast.class, "UU");
		respondWith(Answer.NO);

		// Painters Servant
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getColors().size());
		// Lightning Bolt original
		assertEquals(2, this.game.actualState.stack().objects.get(1).getColors().size());
		// Lightning Bolt copy
		assertEquals(2, this.game.actualState.stack().objects.get(0).getColors().size());

		castAndResolveSpell(Shatter.class, "1R");

		// Lightning Bolt original
		assertEquals(1, this.game.actualState.stack().objects.get(1).getColors().size());
		// Lightning Bolt copy (if this is 2, the spell copy is copying the
		// original's actual characteristics instead of its copiable values)
		assertEquals(1, this.game.actualState.stack().objects.get(0).getColors().size());

	}

}
