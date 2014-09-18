package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.sanitized.*;

public class KeywordsNonCombatTest extends JUnitTest
{
	@Test
	public void absorb()
	{
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, LymphSliver.class, LightningBolt.class, VirulentSliver.class, ChaosCharm.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);

		respondWith(getSpellAction(LymphSliver.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.GREEN, Color.GREEN));
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Lymph Sliver"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());

		// This is the "All sliver creatures gain Absorb 1" ability and the
		// ability granted by absorb
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getStaticAbilities().size());

		// This is Absorb 1
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(LymphSliver.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Lymph Sliver"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getDamage());

		respondWith(getSpellAction(VirulentSliver.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Virulent Sliver"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Lymph Sliver"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getDamage());

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.DEAL_DAMAGE_EVENLY));
		respondWith(getTarget(VirulentSliver.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Virulent Sliver"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Lymph Sliver"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getDamage());
	}

	@Test
	public void bestow()
	{
		addDeck(NyxbornEidolon.class, NyxbornEidolon.class, Terminate.class, Terminate.class, Terminate.class, GrizzlyBears.class, GrizzlyBears.class);
		addDeck(GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(GrizzlyBears.class);

		respondWith(getCastSpellOrActivateAbilityAction(NyxbornEidolon.class));
		respondWith(getChoiceByToString("Bestow"));
		addMana("4B");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());

		GameObject eidolon = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Nyxborn Eidolon", eidolon.getName());
		assertTrue(eidolon.getSubTypes().contains(SubType.AURA));

		GameObject daBears = this.game.actualState.battlefield().objects.get(1);
		assertEquals("Grizzly Bears", daBears.getName());
		assertEquals(4, daBears.getPower());
		assertEquals(3, daBears.getToughness());

		// kill the bears, the eidolon should become a creature instantly
		// only one legal target for terminate
		castAndResolveSpell(Terminate.class);

		eidolon = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Nyxborn Eidolon", eidolon.getName());

		// ... if this ISN'T true, we've got bigger problems!
		assertTrue(eidolon.getTypes().contains(Type.CREATURE));

		// now kill the eidolon too, just to clean up the board.
		castAndResolveSpell(Terminate.class);

		// do it all again, except this time cast terminate in response to the
		// eidolon
		castAndResolveSpell(GrizzlyBears.class);

		// bestow the eidolon onto the bears
		respondWith(getCastSpellOrActivateAbilityAction(NyxbornEidolon.class));
		respondWith(getChoiceByToString("Bestow"));
		addMana("4B");
		donePlayingManaAbilities();

		// here's the fun part: eidolon on the stack, kill the bears
		castAndResolveSpell(Terminate.class);

		// resolve eidolon
		pass();
		pass();

		// it should be a creature on the battlefield
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		eidolon = this.game.actualState.battlefield().objects.get(0);
		// again, if we fail this, what the hell type is the eidolon?
		assertTrue(eidolon.getTypes().contains(Type.CREATURE));
	}

	@Test
	public void bloodthirstNoDamageDoesntTriggerCombatDamageDoesTrigger()
	{
		// Make sure no damage doesn't cause bloodthirst, and that combat damage
		// _does_ cause bloodthirst
		addDeck(BogardanLancer.class, BogardanLancer.class, BlackLotus.class, BlackLotus.class, ChaosCharm.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(BogardanLancer.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Bogardan Lancer"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// Auto-choose Bogardan Lancer
		donePlayingManaAbilities();
		// Auto-choose remaining R
		pass();
		pass();

		// Pass Main
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(BogardanLancer.class));
		pass();
		pass();

		// Declare Blockers
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Bogardan Lancer"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(19, player(1).lifeTotal);

		// Pass Combat Damage
		pass();
		pass();

		// Pass End of Combat
		pass();
		pass();

		// 2nd Main

		respondWith(getSpellAction(BogardanLancer.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Bogardan Lancer"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Bogardan Lancer"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).counters.size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());
	}

	@Test
	public void bloodthirstNonCombatDamage()
	{
		// Make sure non-combat damage also causes bloodthirst, unless its not
		// to an opponent
		addDeck(BogardanLancer.class, BogardanLancer.class, BlackLotus.class, BlackLotus.class, Shock.class, Shock.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(player(0)));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(BogardanLancer.class));
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Bogardan Lancer"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(player(1)));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(BogardanLancer.class));
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Bogardan Lancer"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).counters.size());

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Bogardan Lancer"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).counters.size());

	}

	@Test
	public void cascade()
	{
		addDeck(
		// Don't hit with cascade:
		BlackLotus.class, Terror.class, DoomBlade.class, LightningBolt.class,
		// Hit with cascade:
		ProdigalPyromancer.class,
		// Miss with cascade:
		Forest.class, DragonskullSummit.class, PhagetheUntouchable.class,
		// Opening hand:
		BloodbraidElf.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(BloodbraidElf.class));
		addMana("2RG");
		donePlayingManaAbilities();

		// resolve cascade:
		pass();
		pass();

		// phage, summit, forest, pyromancer:
		assertEquals(4, this.game.actualState.exileZone().objects.size());

		respondWith(pullChoice(ProdigalPyromancer.class));
		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals("Prodigal Pyromancer", this.game.actualState.stack().objects.get(0).getName());
	}

	@Test
	public void cantHaveAbility()
	{
		addDeck(SleeperAgent.class, ChorusofMight.class, ArchetypeofAggression.class, Terminate.class, ChorusofMight.class, ArchetypeofAggression.class, GrizzlyBears.class);
		addDeck(PrimalRage.class, Terminate.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(SleeperAgent.class);
		pass();
		pass();

		// give sleeper agent trample temporarily
		castAndResolveSpell(ChorusofMight.class);
		GameObject sleeper = this.game.actualState.battlefield().objects.get(0);
		assertEquals(1, sleeper.getKeywordAbilities().size());

		// sleeper agent can't have trample
		castAndResolveSpell(ArchetypeofAggression.class);
		sleeper = this.game.actualState.battlefield().objects.get(1);
		assertEquals(0, sleeper.getKeywordAbilities().size());

		// when we resolved the archetype, it should have ended the effect
		// giving sleeper agent trample -- it still shouldn't have trample after
		// we kill it...
		castAndResolveSpell(Terminate.class, ArchetypeofAggression.class);
		sleeper = this.game.actualState.battlefield().objects.get(0);
		assertEquals(0, sleeper.getKeywordAbilities().size());

		// ... but it should be able to gain it again
		castAndResolveSpell(ChorusofMight.class);
		sleeper = this.game.actualState.battlefield().objects.get(0);
		assertEquals(1, sleeper.getKeywordAbilities().size());

		// one more time, for the last test
		castAndResolveSpell(ArchetypeofAggression.class);
		sleeper = this.game.actualState.battlefield().objects.get(1);
		assertEquals(0, sleeper.getKeywordAbilities().size());

		// pass the turn
		goToPhase(Phase.PhaseType.ENDING);
		// this'll automatically resolve the upkeep trigger
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// static ability granting trample
		castAndResolveSpell(PrimalRage.class);
		sleeper = this.game.actualState.battlefield().objects.get(2);
		assertEquals(0, sleeper.getKeywordAbilities().size());

		// kill the archetype -- static ability should be able to grant trample
		castAndResolveSpell(Terminate.class, ArchetypeofAggression.class);
		sleeper = this.game.actualState.battlefield().objects.get(1);
		assertEquals(1, sleeper.getKeywordAbilities().size());
	}

	@Test
	public void championChooseNotToRemove()
	{

		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, LightningCrafter.class, LightningCrafter.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(LightningCrafter.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.BLUE, Color.BLUE, Color.BLUE));
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		// Resolve Champion Remove Ability choosing not to champion
		pass();
		pass();
		respondWith(Answer.NO);

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mogg Fanatic"));
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(3, getGraveyard(0).objects.size());
		assertTrue(getGraveyard(0).objects.get(0).getName().equals("Lightning Crafter"));
		assertTrue(getGraveyard(0).objects.get(1).getName().equals("Black Lotus"));
		assertTrue(getGraveyard(0).objects.get(2).getName().equals("Black Lotus"));

		// Resolve Return Ability with no effect
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mogg Fanatic"));
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(3, getGraveyard(0).objects.size());
		assertTrue(getGraveyard(0).objects.get(0).getName().equals("Lightning Crafter"));
		assertTrue(getGraveyard(0).objects.get(1).getName().equals("Black Lotus"));
		assertTrue(getGraveyard(0).objects.get(2).getName().equals("Black Lotus"));

	}

	@Test
	public void championCloneAChampion()
	{
		// Clone/Champion
		addDeck(Clone.class, ChangelingHero.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, Terror.class, Terror.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(GrizzlyBears.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(ChangelingHero.class));
		addMana("4W");
		donePlayingManaAbilities();
		pass();
		pass();

		// Resolve Changeling Hero Remove Ability
		pass();
		pass();
		respondWith(Answer.YES);
		respondWith(pullChoice(GrizzlyBears.class));

		respondWith(getSpellAction(Clone.class));
		addMana("3U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Answer.YES);
		respondWith(pullChoice(ChangelingHero.class));

		assertEquals("Changeling Hero", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Changeling Hero", this.game.actualState.battlefield().objects.get(1).getName());

		Identified changlingHeroOriginal = this.game.physicalState.battlefield().objects.get(1);

		// Resolve Clone's Remove Ability
		pass();
		pass();
		respondWith(Answer.YES);
		respondWith(pullChoice(GrizzlyBears.class));

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("Changeling Hero", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(changlingHeroOriginal, this.game.actualState.battlefield().objects.get(1));
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(2).getName());

		assertEquals(2, this.game.actualState.exileZone().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.exileZone().objects.get(0).getName());
		assertEquals("Grizzly Bears", this.game.actualState.exileZone().objects.get(1).getName());

		respondWith(getSpellAction(Terror.class));
		respondWith(getTarget(Clone.class));
		addMana("1B");
		donePlayingManaAbilities();
		pass();
		pass();

		// Resolve Changeling Hero Return Ability
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(changlingHeroOriginal, this.game.actualState.battlefield().objects.get(1));
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(2).getName());

		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.exileZone().objects.get(0).getName());
	}

	@Test
	public void championGameStateReversions()
	{
		addDeck(ChangelingHero.class, ChangelingHero.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, Terror.class, Terror.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(GrizzlyBears.class, "1G");

		castAndResolveSpell(ChangelingHero.class, "4W");

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		// Revert the game state
		respondWith(getSpellAction(Terror.class));
		respondWith(getTarget(GrizzlyBears.class));
		donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		pass();
		pass();
		respondWith(Answer.YES);

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());

		// Revert the game state
		respondWith(getSpellAction(Terror.class));
		donePlayingManaAbilities();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());

		castAndResolveSpell(Terror.class, "1B");

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());

		// Revert the game state
		respondWith(getSpellAction(Terror.class));

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());

		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void championNoValidChoice()
	{

		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, LightningCrafter.class, LightningCrafter.class, LightningCrafter.class, LightningCrafter.class, LightningCrafter.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(LightningCrafter.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.BLUE, Color.BLUE, Color.BLUE));
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		// Resolve Champion Remove Ability without a valid choice
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(3, getGraveyard(0).objects.size());
		assertTrue(getGraveyard(0).objects.get(0).getName().equals("Lightning Crafter"));
		assertTrue(getGraveyard(0).objects.get(1).getName().equals("Black Lotus"));
		assertTrue(getGraveyard(0).objects.get(2).getName().equals("Black Lotus"));

		// Resolve Return Ability with no effect
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(3, getGraveyard(0).objects.size());
		assertTrue(getGraveyard(0).objects.get(0).getName().equals("Lightning Crafter"));
		assertTrue(getGraveyard(0).objects.get(1).getName().equals("Black Lotus"));
		assertTrue(getGraveyard(0).objects.get(2).getName().equals("Black Lotus"));

	}

	@Test
	public void championSimple()
	{
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, LightningCrafter.class, LightningCrafter.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(LightningCrafter.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.BLUE, Color.BLUE, Color.BLUE));
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		// Resolve Champion Remove Ability (Mogg is automatically chosen)
		pass();
		pass();
		respondWith(Answer.YES);

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Lightning Crafter", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals("Mogg Fanatic", this.game.actualState.exileZone().objects.get(0).getName());

		// End the turn
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.ENDING);

		pass();
		pass();

		// Discard any card
		respondWith(pullChoice(Plains.class));

		// During upkeep Lightning Crafter kills himself
		respondWith(getAbilityAction(LightningCrafter.TapForThreeDamage.class));
		respondWith(getTarget(LightningCrafter.class));
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve Champion Return Ability
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Mogg Fanatic", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(3, getGraveyard(0).objects.size());
		assertEquals("Lightning Crafter", getGraveyard(0).objects.get(0).getName());
		assertEquals("Black Lotus", getGraveyard(0).objects.get(1).getName());
		assertEquals("Black Lotus", getGraveyard(0).objects.get(2).getName());
	}

	@Test
	public void cipher()
	{
		addDeck(LastThoughts.class, LastThoughts.class, LastThoughts.class, LastThoughts.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, Terminate.class);
		addDeck(LastThoughts.class, LastThoughts.class, LastThoughts.class, LastThoughts.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(RagingGoblin.class);
		castAndResolveSpell(LastThoughts.class);
		// encode the spell:
		respondWith(Answer.YES);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(RagingGoblin.class));
		goToStep(Step.StepType.COMBAT_DAMAGE);
		// resolve the cipher trigger:
		pass();
		pass();
		// copy the spell card:
		respondWith(Answer.YES);
		// cast the copy:
		respondWith(pullChoice(SpellCopy.class));

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals("Last Thoughts", this.game.actualState.stack().objects.get(0).getName());

		castAndResolveSpell(Terminate.class);
		// ...assert(the game hasn't crashed)
	}

	@Test
	public void clash()
	{
		addDeck(Mountain.class, Forest.class, Ringskipper.class, Ringskipper.class, Ringskipper.class, Ringskipper.class, Terminate.class, Terminate.class, Terminate.class, Plains.class);
		addDeck(Swamp.class, Island.class, MoggFanatic.class, Ringskipper.class, Ringskipper.class, Ringskipper.class, Terminate.class, Terminate.class, Terminate.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(Ringskipper.class);
		castAndResolveSpell(Terminate.class);

		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		// CLASH!
		// Player 0: Ringskipper (bottom)
		// Player 1: Mogg Fanatic (top)
		respondWith(Answer.YES);
		respondWith(Answer.NO);

		assertEquals("Forest", this.player(0).getLibrary(this.game.actualState).objects.get(0).getName());
		assertEquals("Mogg Fanatic", this.player(1).getLibrary(this.game.actualState).objects.get(0).getName());

		// Player 0 gets the ringskipper back, making for 6 cards.
		assertEquals(6, this.player(0).getHand(this.game.actualState).objects.size());

		castAndResolveSpell(Ringskipper.class);

		// Player 1's turn
		goToPhase(Phase.PhaseType.BEGINNING);

		castAndResolveSpell(Terminate.class);

		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		// CLASH!
		// Player 1: Mogg Fanatic (top)
		// Player 0: Forest (bottom)
		respondWith(Answer.NO);
		respondWith(Answer.YES);

		assertEquals("Mountain", this.player(0).getLibrary(this.game.actualState).objects.get(0).getName());
		assertEquals("Mogg Fanatic", this.player(1).getLibrary(this.game.actualState).objects.get(0).getName());

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals("Mountain", this.player(0).getLibrary(this.game.actualState).objects.get(0).getName());
		assertEquals("Island", this.player(1).getLibrary(this.game.actualState).objects.get(0).getName());

		castAndResolveSpell(Ringskipper.class);
		castAndResolveSpell(Terminate.class);

		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		// CLASH!
		// Player 1: Island (bottom)
		// Player 0: Mountain (top)
		respondWith(Answer.YES);
		respondWith(Answer.NO);

		assertEquals("Mountain", this.player(0).getLibrary(this.game.actualState).objects.get(0).getName());
		assertEquals("Swamp", this.player(1).getLibrary(this.game.actualState).objects.get(0).getName());

		// 7 - ring - term + ring - ring
		assertEquals(5, this.player(0).getHand(this.game.actualState).objects.size());

		// 7 - term + draw - ring - term
		assertEquals(5, this.player(1).getHand(this.game.actualState).objects.size());
	}

	@Test
	public void convoke()
	{
		addDeck(ScattertheSeeds.class, ScattertheSeeds.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(ScattertheSeeds.class, ScattertheSeeds.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(ScattertheSeeds.class));
		// we are paying full price so make enough mana to pay it all:
		addMana("3GG");
		donePlayingManaAbilities();
		// we will tap no creatures, but we won't even be asked because we have
		// none to tap.
		pass();
		pass();

		respondWith(getSpellAction(ScattertheSeeds.class));
		// we are going to reduce the cost to 2G and fail to pay it, so make
		// enough mana but not enough green:
		addMana("3");
		donePlayingManaAbilities();
		// tap one green creature:
		respondWith(pullChoice(Token.class));
		// tap one "other" creature:
		respondWith(pullChoice(Token.class));

		// game state will reverse, allowing me to cast the scatter again:
		respondWith(getSpellAction(ScattertheSeeds.class));
		// total cost will be 2G, pay it correctly:
		addMana("2G");
		donePlayingManaAbilities();
		// pay G with a creature:
		respondWith(pullChoice(Token.class));
		// pay 1 with a creature:
		respondWith(pullChoice(Token.class));
		pass();
		pass();

		assertEquals(6, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void cumulativeUpkeepPayAndChooseNotToPay()
	{
		addDeck(PhyrexianEtchings.class, PhyrexianEtchings.class, PhyrexianEtchings.class, PhyrexianEtchings.class, PhyrexianEtchings.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(PhyrexianEtchings.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(20, player(0).lifeTotal);
		assertEquals(4, getHand(0).objects.size());

		// End the turn
		// Pass 1st Main Phase
		assertEquals(Phase.PhaseType.PRECOMBAT_MAIN, this.game.actualState.currentPhase().type);
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
		assertEquals(Phase.PhaseType.POSTCOMBAT_MAIN, this.game.actualState.currentPhase().type);
		pass();
		pass();

		assertEquals(20, player(0).lifeTotal);
		assertEquals(4, getHand(0).objects.size());

		// Resolve the etchings trigger
		assertEquals(Step.StepType.END, this.game.actualState.currentStep().type);
		pass();
		pass();

		// pass eot
		assertEquals(Step.StepType.END, this.game.actualState.currentStep().type);
		pass();
		pass();

		assertEquals(20, player(0).lifeTotal);
		assertEquals(4, getHand(0).objects.size());

		// pass upkeep & draw
		pass();
		pass();
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.DRAW);
		pass();
		pass();

		// End the turn, discarding a card
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
		respondWith(pullChoice(Plains.class));

		// Pay the cumulative upkeep
		pass();
		pass();
		respondWith(Answer.YES);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK));

		assertEquals(20, player(0).lifeTotal);
		assertEquals(4, getHand(0).objects.size());

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		assertEquals(5, getHand(0).objects.size());

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

		assertEquals(20, player(0).lifeTotal);
		assertEquals(5, getHand(0).objects.size());

		// Resolve the etchings trigger
		pass();
		pass();
		pass();
		pass();

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		assertEquals(20, player(0).lifeTotal);
		assertEquals(6, getHand(0).objects.size());

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
		respondWith(pullChoice(Plains.class));

		// Fail to pay the upkeep
		pass();
		pass();
		respondWith(Answer.NO);

		assertEquals(20, player(0).lifeTotal);
		assertEquals(6, getHand(0).objects.size());

		// Resolve the lose life trigger
		pass();
		pass();

		assertEquals(16, player(0).lifeTotal);
		assertEquals(6, getHand(0).objects.size());
	}

	@Test
	public void cumulativeUpkeepUnableToPay()
	{
		addDeck(PhyrexianEtchings.class, PhyrexianEtchings.class, PhyrexianEtchings.class, PhyrexianEtchings.class, PhyrexianEtchings.class, PhyrexianEtchings.class, PhyrexianEtchings.class, PhyrexianEtchings.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(PhyrexianEtchings.class));
		addMana("BBB");
		donePlayingManaAbilities();
		pass();
		pass();

		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		GameObject etchings = this.game.physicalState.battlefield().objects.get(0);
		etchings.getName().equals("Phyrexian Etchings");
		etchings.counters.add(new org.rnd.jmagic.engine.Counter(org.rnd.jmagic.engine.Counter.CounterType.AGE, etchings.ID));
		etchings.counters.add(new org.rnd.jmagic.engine.Counter(org.rnd.jmagic.engine.Counter.CounterType.AGE, etchings.ID));
		etchings.counters.add(new org.rnd.jmagic.engine.Counter(org.rnd.jmagic.engine.Counter.CounterType.AGE, etchings.ID));
		etchings.counters.add(new org.rnd.jmagic.engine.Counter(org.rnd.jmagic.engine.Counter.CounterType.AGE, etchings.ID));

		respondWith(getLandAction(Plains.class));

		goToPhase(Phase.PhaseType.BEGINNING);

		addMana("BBBRRR");

		pass();
		pass();

		respondWith(Answer.YES);
		donePlayingManaAbilities();

		assertEquals(0, this.choices.size());

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertTrue(this.game.actualState.stack().objects.get(0) instanceof PhyrexianEtchings.LoseLifeTrigger);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Player 0 should have lost 10 life to
		// phyrexian etchings (2 life per counter for 5 age counters)
		assertEquals(10, player(0).lifeTotal);
	}

	@Test
	public void cyclingTriggerOnDiscard()
	{
		// Make sure Stoic Champion triggers and pumps correctly
		addDeck(Plains.class, Plains.class, Plains.class, StoicChampion.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlastedLandscape.class, BlastedLandscape.class, BlastedLandscape.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		assertEquals(0, player(0).pool.converted());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);

		assertEquals(3, player(0).pool.converted());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);

		assertEquals(6, player(0).pool.converted());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);

		assertEquals(9, player(0).pool.converted());

		respondWith(getSpellAction(StoicChampion.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE));
		pass();
		pass();

		assertEquals(7, player(0).pool.converted());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Cycling.CyclingAbility.class));

		// Cycle
		assertEquals(1, this.game.actualState.stack().objects.size());

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 3x Blasted Landscape
		assertEquals(3, getHand(0).objects.size());
		// 3x Black Lotus
		assertEquals(3, getGraveyard(0).objects.size());
		// 3x Plains
		assertEquals(3, getLibrary(0).objects.size());

		donePlayingManaAbilities();

		// Pay any two mana
		respondWith(getMana(Color.WHITE, Color.WHITE));

		// Pump, Cycle
		assertEquals(2, this.game.actualState.stack().objects.size());

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 2x Blasted Landscape
		assertEquals(2, getHand(0).objects.size());
		// 3x Black Lotus, 1x Blasted Landscape
		assertEquals(4, getGraveyard(0).objects.size());
		// 3x Plains
		assertEquals(3, getLibrary(0).objects.size());

		// Resolve the pump ability
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 2x Blasted Landscape
		assertEquals(2, getHand(0).objects.size());
		// 3x Black Lotus, 1x Blasted Landscape
		assertEquals(4, getGraveyard(0).objects.size());
		// 3x Plains
		assertEquals(3, getLibrary(0).objects.size());

		// Resolve cycle
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 2x Blasted Landscape, 1x Plains
		assertEquals(3, getHand(0).objects.size());
		// 3x Black Lotus, 1x Blasted Landscape
		assertEquals(4, getGraveyard(0).objects.size());
		// 2x Plains
		assertEquals(2, getLibrary(0).objects.size());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Cycling.CyclingAbility.class));

		// Cycle
		assertEquals(1, this.game.actualState.stack().objects.size());

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 2x Blasted Landscape, 1x Plains
		assertEquals(3, getHand(0).objects.size());
		// 3x Black Lotus, 1x Blasted Landscape
		assertEquals(4, getGraveyard(0).objects.size());
		// 2x Plains
		assertEquals(2, getLibrary(0).objects.size());

		donePlayingManaAbilities();

		// Pay any two mana
		respondWith(getMana(Color.WHITE, Color.WHITE));

		// Pump, Cycle
		assertEquals(2, this.game.actualState.stack().objects.size());

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 1x Blasted Landscape, 1x Plains
		assertEquals(2, getHand(0).objects.size());
		// 3x Black Lotus, 2x Blasted Landscape
		assertEquals(5, getGraveyard(0).objects.size());
		// 2x Plains
		assertEquals(2, getLibrary(0).objects.size());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Cycling.CyclingAbility.class));

		// Cycle, Pump, Cycle
		assertEquals(3, this.game.actualState.stack().objects.size());

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 2x Plains
		assertEquals(2, getHand(0).objects.size());
		// 3x Black Lotus, 2x Blasted Landscape
		assertEquals(5, getGraveyard(0).objects.size());
		// 2x Plains
		assertEquals(2, getLibrary(0).objects.size());

		donePlayingManaAbilities();

		// Pay any two mana
		respondWith(getMana(Color.WHITE, Color.WHITE));

		// Pump, Cycle, Pump, Cycle
		assertEquals(4, this.game.actualState.stack().objects.size());

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 1x Plains
		assertEquals(1, getHand(0).objects.size());
		// 3x Black Lotus, 3x Blasted Landscape
		assertEquals(6, getGraveyard(0).objects.size());
		// 2x Plains
		assertEquals(2, getLibrary(0).objects.size());

		// Resolve the pump ability
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 1x Plains
		assertEquals(1, getHand(0).objects.size());
		// 3x Black Lotus, 3x Blasted Landscape
		assertEquals(6, getGraveyard(0).objects.size());
		// 2x Plains
		assertEquals(2, getLibrary(0).objects.size());

		// Resolve cycle
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 2x Plains
		assertEquals(2, getHand(0).objects.size());
		// 3x Black Lotus, 3x Blasted Landscape
		assertEquals(6, getGraveyard(0).objects.size());
		// 1x Plains
		assertEquals(1, getLibrary(0).objects.size());

		// Resolve the pump ability
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(8, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(8, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 2x Plains
		assertEquals(2, getHand(0).objects.size());
		// 3x Black Lotus, 3x Blasted Landscape
		assertEquals(6, getGraveyard(0).objects.size());
		// 1x Plains
		assertEquals(1, getLibrary(0).objects.size());

		// Resolve cycle
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(8, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(8, this.game.actualState.battlefield().objects.get(0).getToughness());

		// 3x Plains
		assertEquals(3, getHand(0).objects.size());
		// 3x Black Lotus, 3x Blasted Landscape
		assertEquals(6, getGraveyard(0).objects.size());
		// {Empty}
		assertEquals(0, getLibrary(0).objects.size());

	}

	@Test
	public void cyclingTriggersOnSelfCycle()
	{
		// Make sure cycling resounding thunder triggers itself
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ResoundingThunder.class, ResoundingThunder.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Cycling.CyclingAbility.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		// Make sure the cycle is still the only thing on the stack:
		assertEquals(1, this.game.actualState.stack().objects.size());
		respondWith(getMana(Color.BLACK, Color.BLACK, Color.BLACK, Color.RED, Color.RED, Color.GREEN, Color.GREEN, Color.GREEN));
		// Player 0 is now asked to choose a target for the trigger, which is
		// now on the stack:
		assertEquals(2, this.game.actualState.stack().objects.size());
		// Declare the target for Resounding Thunder's trigger:
		respondWith(getTarget(player(1)));

		assertEquals(3, getHand(0).objects.size());
		assertEquals(20, player(1).lifeTotal);

		// Resolve damage trigger:
		pass();
		pass();

		assertEquals(3, getHand(0).objects.size());
		assertEquals(14, player(1).lifeTotal);

		// Resolve cycle
		pass();
		pass();

		assertEquals(4, getHand(0).objects.size());
		assertEquals(14, player(1).lifeTotal);

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Cycling.CyclingAbility.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		// Make sure the cycle is still the only thing on the stack:
		assertEquals(1, this.game.actualState.stack().objects.size());
		respondWith(getMana(Color.BLACK, Color.BLACK, Color.BLACK, Color.RED, Color.RED, Color.GREEN, Color.GREEN, Color.GREEN));
		// Player 0 is now asked to choose a target for the trigger, which is
		// now on the stack:
		assertEquals(2, this.game.actualState.stack().objects.size());
		// Declare the target for Resounding Thunder's trigger:
		respondWith(getTarget(player(1)));

		assertEquals(0, getHand(0).objects.size());
		assertEquals(14, player(1).lifeTotal);

		// Resolve damage trigger:
		pass();
		pass();

		assertEquals(0, getHand(0).objects.size());
		assertEquals(8, player(1).lifeTotal);

		// Resolve cycle
		pass();
		pass();

		// Player 0 loses due to drawing from an empty library
		assertTrue(this.winner.ID == player(1).ID);

	}

	@Test
	public void detain()
	{
		addDeck(Plains.class, Plains.class, Plains.class, LeylineofAnticipation.class, InactionInjunction.class, InactionInjunction.class, RuneclawBear.class, SleeperAgent.class, GrizzlyBears.class, BurstofSpeed.class, CylianElf.class);
		addDeck(Plains.class, Plains.class, Plains.class, MoggFanatic.class, InactionInjunction.class, InactionInjunction.class, RuneclawBear.class, SleeperAgent.class, GrizzlyBears.class, BurstofSpeed.class, CylianElf.class, LeylineofAnticipation.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		// Player 1 starts with the Leyline (Player 0 doesn't draw theirs until
		// after casting Inaction Injunction)
		respondWith(pullChoice(LeylineofAnticipation.class));

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RuneclawBear.class);
		castAndResolveSpell(GrizzlyBears.class);
		castAndResolveSpell(CylianElf.class);
		castAndResolveSpell(SleeperAgent.class);

		// Resolve Sleeper Agent's ability
		pass();
		pass();

		castAndResolveSpell(BurstofSpeed.class);

		// Player 0 detains Sleeper Agent (target automatically chosen)
		castAndResolveSpell(InactionInjunction.class);

		castAndResolveSpell(LeylineofAnticipation.class);

		pass();
		// Player 1 detains Grizzly Bears
		castAndResolveSpell(InactionInjunction.class, GrizzlyBears.class);

		assertEquals("Leyline of Anticipation", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Sleeper Agent", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Cylian Elf", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals("Runeclaw Bear", this.game.actualState.battlefield().objects.get(4).getName());
		assertEquals("Leyline of Anticipation", this.game.actualState.battlefield().objects.get(5).getName());

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		assertEquals(PlayerInterface.ChoiceType.ATTACK, this.choiceType);

		// Attack will fail
		respondWith(pullChoice(GrizzlyBears.class));
		assertEquals(PlayerInterface.ChoiceType.ATTACK, this.choiceType);

		// Attack will pass
		respondWith(pullChoice(CylianElf.class));
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		assertEquals(PlayerInterface.ChoiceType.BLOCK, this.choiceType);

		// Block will fail
		respondWith(pullChoice(SleeperAgent.class));
		assertEquals(PlayerInterface.ChoiceType.BLOCK, this.choiceType);

		// Block will pass
		respondWith();
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		assertEquals(20, player(0).lifeTotal);
		assertEquals(18, player(1).lifeTotal);

		// Go to Player 1's turn.
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Account for the shock from sleeper agent
		assertEquals(20, player(0).lifeTotal);
		assertEquals(16, player(1).lifeTotal);

		castAndResolveSpell(GrizzlyBears.class);
		castAndResolveSpell(BurstofSpeed.class);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		assertEquals(PlayerInterface.ChoiceType.ATTACK, this.choiceType);

		// Attack will fail
		respondWith(pullChoice(SleeperAgent.class));
		assertEquals(PlayerInterface.ChoiceType.ATTACK, this.choiceType);

		// Attack will pass
		respondWith(pullChoice(GrizzlyBears.class));
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		assertEquals(PlayerInterface.ChoiceType.BLOCK, this.choiceType);

		// Block will pass
		respondWith(pullChoice(GrizzlyBears.class));
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		// Player 1's Grizzly Bears died in combat
		assertEquals("Leyline of Anticipation", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Sleeper Agent", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Cylian Elf", this.game.actualState.battlefield().objects.get(2).getName());
		// Player 0's Grizzly Bears died in combat
		assertEquals("Runeclaw Bear", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals("Leyline of Anticipation", this.game.actualState.battlefield().objects.get(4).getName());

		assertEquals(20, player(0).lifeTotal);
		assertEquals(16, player(1).lifeTotal);

		castAndResolveSpell(MoggFanatic.class);

		pass();
		castAndResolveSpell(InactionInjunction.class, MoggFanatic.class);

		for(SanitizedCastSpellOrActivateAbilityAction choice: this.choices.getAll(SanitizedCastSpellOrActivateAbilityAction.class))
			if(MoggFanatic.SacPing.class.isAssignableFrom(this.game.actualState.get(choice.toBePlayed).getClass()))
				fail("Mogg Fanatic's ability should have been restricted.");
	}

	@Test
	public void devotion()
	{
		addDeck(NyleasDisciple.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		addDeck(GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(GrizzlyBears.class); // G
		castAndResolveSpell(NyleasDisciple.class); // GG

		// trigger on the stack, resolve it:
		pass();
		pass();

		// GGG makes 3:
		assertEquals(23, player(0).lifeTotal);
	}

	@Test
	public void devour()
	{
		addDeck(GluttonousSlime.class, MoggFanatic.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(MoggFanatic.class, "R");

		castAndResolveSpell(GluttonousSlime.class, "2G");
		respondWith(pullChoice(MoggFanatic.class));
		assertEquals("Gluttonous Slime", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
	}

	@Test
	public void dredgeBasics()
	{
		// Stacked GameTypes read the deck starting at the bottom. With this
		// deck, after drawing, the only thing left in the library will be two
		// plains.
		addDeck(Plains.class, Plains.class, GolgariBrownscale.class, Swamp.class, Swamp.class, Swamp.class, OnewithNothing.class, OnewithNothing.class, OnewithNothing.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Swamp.class));

		respondWith(getSpellAction(OnewithNothing.class));
		respondWith(getIntrinsicAbilityAction(SubType.SWAMP));
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
		// Discard a card
		respondWith(pullChoice(Plains.class));

		// pass upkeep
		pass();
		pass();

		// The draw doesn't use the stack, so neither should its replacement
		assertEquals(0, this.game.actualState.stack().objects.size());

		// We should be getting a Yes/No choice right now
		assertEquals(2, this.choices.size());

		assertEquals(2, getLibrary(0).objects.size());
		assertEquals(6, getGraveyard(0).objects.size());
		assertEquals(0, getHand(0).objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());

		// Choose to dredge
		respondWith(Answer.YES);

		assertEquals(20, player(0).lifeTotal);
		assertEquals(0, getLibrary(0).objects.size());
		assertTrue(getGraveyard(0).objects.size() == 6 + 2 - 1);
		assertEquals(1, getHand(0).objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve the golgari gain-life trigger
		pass();
		pass();

		assertEquals(22, player(0).lifeTotal);
		assertEquals(0, getLibrary(0).objects.size());
		assertTrue(getGraveyard(0).objects.size() == 6 + 2 - 1);
		assertEquals(1, getHand(0).objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());

		// Pass draw step
		pass();
		pass();

		// Play Golgari Brownscale, Add B from Swamp
		assertEquals(2, this.choices.size());

	}

	@Test
	public void dredgeNotEnoughCardsToDredge()
	{
		addDeck(BlackLotus.class, BlackLotus.class, GraveShellScarab.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(GraveShellScarab.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.BLACK, Color.BLACK, Color.GREEN, Color.GREEN));
		pass();
		pass();

		respondWith(getAbilityAction(GraveShellScarab.SacDraw.class));
		donePlayingManaAbilities();

		// Without enough cards in the library, the player won't get the option
		// to dredge
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(3, getGraveyard(0).objects.size());
		assertEquals(4, getHand(0).objects.size());
		assertEquals(0, getLibrary(0).objects.size());

		pass();
		pass();

		assertTrue(this.winner.ID == player(1).ID);
	}

	@Test
	public void echo()
	{
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, MoggWarMarshal.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, MoggWarMarshal.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		// Player 0's turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(MoggWarMarshal.class, "1R");

		// Resolve ETB trigger
		pass();
		pass();

		goToPhase(Phase.PhaseType.ENDING);

		// Player 1's turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(MoggWarMarshal.class, "1R");

		// Resolve ETB trigger
		pass();
		pass();

		goToPhase(Phase.PhaseType.ENDING);

		// Player 0's turn
		goToStep(Step.StepType.UPKEEP);

		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();
		// Activate mana abilities
		addMana("1R");
		donePlayingManaAbilities();
		// Do you want to pay the echo cost?
		respondWith(Answer.YES);

		// Player 1's Goblin token, Player 1's Mogg War Marshal, Player 0's
		// Goblin token, Player 0's Mogg War Marshal
		assertEquals(4, this.game.actualState.battlefield().objects.size());

		goToPhase(Phase.PhaseType.ENDING);

		// Player 1's turn
		goToStep(Step.StepType.UPKEEP);

		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();
		// Activate mana abilities
		addMana("1R");
		donePlayingManaAbilities();
		// Do you want to pay the echo cost?
		respondWith(Answer.NO);

		// Player 1's Goblin token, Player 0's Goblin token, Player 0's Mogg War
		// Marshal
		assertEquals(3, this.game.actualState.battlefield().objects.size());

		// Player 1's Mogg War Marshal's dies trigger
		assertEquals(1, this.game.actualState.stack().objects.size());
	}

	@Test
	public void enchant()
	{
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, RavenousRats.class, RavenousRats.class, UnholyStrength.class, UnholyStrength.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RavenousRats.class, "1B");

		// Resolve Rats' CIP discard, discarding anything
		pass();
		pass();
		respondWith(pullChoice(Plains.class));

		castAndResolveSpell(UnholyStrength.class, "B");
		// autotarget rats

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(0, getGraveyard(0).objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Unholy Strength", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(this.game.actualState.battlefield().objects.get(0).getAttachedTo(), this.game.actualState.battlefield().objects.get(1).ID);
		assertEquals("Ravenous Rats", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getAttachments().size());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getAttachments().contains(this.game.actualState.battlefield().objects.get(0).ID));
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getToughness());

	}

	@Test
	public void enchantNotResolving()
	{
		addDeck(
		// top five:
		Plains.class, Plains.class, Plains.class, Plains.class, UnholyStrength.class,
		// opening seven:
		Plains.class, Plains.class, Plains.class, TomeScour.class, RagingGoblin.class, MoggFanatic.class, TomeScour.class, OpentheVaults.class);

		addDeck(
		// top five:
		EntanglingVines.class, HolyStrength.class, Plains.class, Plains.class, Plains.class,
		// opening seven:
		Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		startGame(new Stacked());
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

		respondWith(getSpellAction(TomeScour.class));
		respondWith(getTarget(player(1)));
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();

		castAndResolveSpell(MoggFanatic.class, "R");
		castAndResolveSpell(RagingGoblin.class, "R");

		castAndResolveSpell(OpentheVaults.class, "4WW");

		// active player's unholy strength first
		respondWith(pullChoice(MoggFanatic.class));
		// entangling vines should have nowhere to go
		// nonactive player's holy strength
		respondWith(pullChoice(RagingGoblin.class));

		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(2).getToughness());

		assertEquals("Mogg Fanatic", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals(3, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getToughness());
	}

	@Test
	public void entwine()
	{
		addDeck(
		// in library at start of game:
		GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class,
		// in hand at start of game:
		ToothandNail.class, ToothandNail.class, ToothandNail.class, ToothandNail.class, ToothandNail.class, ToothandNail.class, ToothandNail.class);

		addDeck(GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// cast T&N, just the first mode
		respondWith(getSpellAction(ToothandNail.class));
		respondWith(getMode(EventType.SEARCH_LIBRARY_AND_PUT_INTO));
		addMana("5GG");
		donePlayingManaAbilities();
		pass();
		pass();

		// get some creatures
		respondWith(pullChoice(GrizzlyBears.class), pullChoice(GrizzlyBears.class));

		// hand was 7 cards, then we cast T&N, then we added 2 bears:
		assertEquals(7 - 1 + 2, player(0).getHand(this.game.actualState).objects.size());

		// cast T&N, just the second mode
		respondWith(getSpellAction(ToothandNail.class));
		respondWith(getMode(EventType.PUT_ONTO_BATTLEFIELD_CHOICE));
		addMana("5GG");
		donePlayingManaAbilities();
		pass();
		pass();

		// put the bears into play
		respondWith(pullChoice(GrizzlyBears.class), pullChoice(GrizzlyBears.class));
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		// attempt to entwine T&N paying only its mana cost
		// cast T&N, just the second mode
		respondWith(getSpellAction(ToothandNail.class));
		respondArbitrarily(); // both modes, ordered arbitrarily
		addMana("5GG");
		donePlayingManaAbilities();

		// should have failed:
		assertEquals(0, this.game.actualState.stack().objects.size());

		// 7 cards, minus first T&N, plus bears, minus second T&N, minus bears:
		assertEquals(7 - 1 + 2 - 1 - 2, player(0).getHand(this.game.actualState).objects.size());

		// successfully entwine T&N:
		respondWith(getSpellAction(ToothandNail.class));
		respondArbitrarily(); // both modes, ordered arbitrarily
		addMana("7GG");
		donePlayingManaAbilities();
		pass();
		pass();

		// two bears out of the deck, then two bears from the hand
		respondWith(pullChoice(GrizzlyBears.class), pullChoice(GrizzlyBears.class));
		respondWith(pullChoice(GrizzlyBears.class), pullChoice(GrizzlyBears.class));

		// 4 bears on the field and we're good
		assertEquals(4, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void equip()
	{
		addDeck(LeoninScimitar.class, RagingGoblin.class, RagingGoblin.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(RagingGoblin.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		Identified ragingGoblinFirst = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(LeoninScimitar.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertEquals("Leonin Scimitar", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(-1, this.game.actualState.battlefield().objects.get(0).getAttachedTo());
		assertEquals(ragingGoblinFirst, this.game.actualState.battlefield().objects.get(1));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getAttachments().isEmpty());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Equip.EquipAbility.class));
		// auto-target Raging Goblin
		donePlayingManaAbilities();
		// auto-select final R in mana pool
		pass();
		pass();

		assertEquals("Leonin Scimitar", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(this.game.actualState.battlefield().objects.get(0).getAttachedTo(), this.game.actualState.battlefield().objects.get(1).ID);
		assertEquals(ragingGoblinFirst, this.game.actualState.battlefield().objects.get(1));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getAttachments().size());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getAttachments().contains(Integer.valueOf(this.game.actualState.battlefield().objects.get(0).ID)));

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

		Identified ragingGoblinSecond = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Equip.EquipAbility.class));
		respondWith(getTarget(ragingGoblinSecond));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertEquals(ragingGoblinSecond, this.game.actualState.battlefield().objects.get(0));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getAttachments().size());
		assertEquals("Leonin Scimitar", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(this.game.actualState.battlefield().objects.get(1).getAttachedTo(), this.game.actualState.battlefield().objects.get(0).ID);
		assertEquals(ragingGoblinFirst, this.game.actualState.battlefield().objects.get(2));
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertTrue(this.game.actualState.battlefield().objects.get(2).getAttachments().isEmpty());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Equip.EquipAbility.class));
		respondWith(getTarget(ragingGoblinSecond));
		donePlayingManaAbilities();
		// auto-select final R in mana pool
		pass();
		pass();

		assertEquals(ragingGoblinSecond, this.game.actualState.battlefield().objects.get(0));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getAttachments().size());
		assertEquals("Leonin Scimitar", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(this.game.actualState.battlefield().objects.get(1).getAttachedTo(), this.game.actualState.battlefield().objects.get(0).ID);
		assertEquals(ragingGoblinFirst, this.game.actualState.battlefield().objects.get(2));
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertTrue(this.game.actualState.battlefield().objects.get(2).getAttachments().isEmpty());
	}

	@Test
	public void evoke()
	{
		addDeck(Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class);
		addDeck(Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class, Mulldrifter.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getCastSpellOrActivateAbilityAction(Mulldrifter.class));
		respondWith(getChoiceByToString("Mana cost"));
		addMana("(4)(U)");
		donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(0, this.choices.size()); // can't evoke at instant speed

		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(6, player(0).getHand(this.game.actualState).objects.size());

		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(8, player(0).getHand(this.game.actualState).objects.size());

		respondWith(getCastSpellOrActivateAbilityAction(Mulldrifter.class));
		respondWith(getChoiceByToString("Evoke cost"));
		addMana("(2)(U)");
		donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();

		assertEquals(PlayerInterface.ChoiceType.TRIGGERS, this.choiceType);
		respondArbitrarily();

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(7, player(0).getHand(this.game.actualState).objects.size());
		assertEquals(0, player(0).getGraveyard(this.game.actualState).objects.size());

		pass();
		pass();
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(9, player(0).getHand(this.game.actualState).objects.size());
		assertEquals(1, player(0).getGraveyard(this.game.actualState).objects.size());
	}

	@Test
	public void evolve()
	{
		addDeck(CloudfinRaptor.class, CloudfinRaptor.class, MoggFanatic.class, CloudfinRaptor.class, IzzetStaticaster.class, WorldspineWurm.class, CloudfinRaptor.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(CloudfinRaptor.class);
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());

		castAndResolveSpell(CloudfinRaptor.class);
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());

		castAndResolveSpell(MoggFanatic.class);
		assertEquals(2, this.choices.size());
		assertEquals(PlayerInterface.ChoiceType.TRIGGERS, this.choiceType);
		respondArbitrarily();
		for(int i = 0; i < 4; ++i)
			pass();
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getToughness());

		castAndResolveSpell(CloudfinRaptor.class);
		assertFalse(PlayerInterface.ChoiceType.TRIGGERS == this.choiceType);
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getToughness());

		castAndResolveSpell(IzzetStaticaster.class);
		assertEquals(3, this.choices.size());
		assertEquals(PlayerInterface.ChoiceType.TRIGGERS, this.choiceType);
		respondArbitrarily();
		for(int i = 0; i < 6; ++i)
			pass();
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(3).getToughness());
		assertEquals(2, this.game.actualState.battlefield().objects.get(4).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(4).getToughness());

		castAndResolveSpell(WorldspineWurm.class);
		assertEquals(3, this.choices.size());
		assertEquals(PlayerInterface.ChoiceType.TRIGGERS, this.choiceType);
		respondArbitrarily();
		for(int i = 0; i < 6; ++i)
			pass();
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertEquals(3, this.game.actualState.battlefield().objects.get(4).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(4).getToughness());
		assertEquals(3, this.game.actualState.battlefield().objects.get(5).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(5).getToughness());

		castAndResolveSpell(CloudfinRaptor.class);
		assertFalse(PlayerInterface.ChoiceType.TRIGGERS == this.choiceType);
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(3).getToughness());
		assertEquals(3, this.game.actualState.battlefield().objects.get(5).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(5).getToughness());
		assertEquals(3, this.game.actualState.battlefield().objects.get(6).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(6).getToughness());
	}

	@Test
	public void extort()
	{
		addDeck(PlatinumEmperion.class, PlatinumEmperion.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class);
		addDeck(PlatinumEmperion.class, PlatinumEmperion.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class);
		addDeck(PlatinumEmperion.class, PlatinumEmperion.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class);
		addDeck(PlatinumEmperion.class, PlatinumEmperion.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class, BasilicaScreecher.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));

		for(int i = 0; i < 4; ++i)
			keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(PlatinumEmperion.class);

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(BasilicaScreecher.class);

		respondWith(getSpellAction(BasilicaScreecher.class));
		addMana("1B");
		donePlayingManaAbilities();

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);
		assertEquals(20, player(2).lifeTotal);
		assertEquals(20, player(3).lifeTotal);

		for(int i = 0; i < 4; ++i)
			pass();

		addMana("W");
		donePlayingManaAbilities();

		respondWith(Answer.YES);

		assertEquals(20, player(0).lifeTotal);
		assertEquals(22, player(1).lifeTotal);
		assertEquals(19, player(2).lifeTotal);
		assertEquals(19, player(3).lifeTotal);
	}

	@Test
	public void fading()
	{
		addDeck(ParallaxDementia.class, ParallaxDementia.class, ParallaxDementia.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class);
		addDeck(Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(RagingGoblin.class);
		castAndResolveSpell(ParallaxDementia.class);
		assertEquals("Parallax Dementia", this.game.actualState.battlefield().objects.get(0).getName());
		goToPhase(Phase.PhaseType.ENDING);

		// player 1's first turn:
		goToPhase(Phase.PhaseType.BEGINNING);
		goToStep(Step.StepType.CLEANUP);
		respondWith(pullChoice(Island.class));

		// player 0's second turn:
		goToStep(Step.StepType.DRAW);
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());
		goToPhase(Phase.PhaseType.ENDING);

		// player 1's second turn:
		goToPhase(Phase.PhaseType.BEGINNING);
		goToStep(Step.StepType.CLEANUP);
		respondWith(pullChoice(Island.class));

		// player 0's third turn:
		goToStep(Step.StepType.DRAW);
		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void flash()
	{
		addDeck(ZealousGuardian.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, ZealousGuardian.class, ZealousGuardian.class, ZealousGuardian.class, ZealousGuardian.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		// skip the first draw step

		// play an Island to eliminate all play-land-actions and make sure
		// Zealous
		// Guardian can't be played from the library (the only choice should be
		// to Tap
		// the land for mana)
		respondWith(getLandAction(Island.class));
		assertEquals(1, this.choices.size());

		// pass pre-combat main
		pass();
		pass();

		// pass start-of-combat
		pass();
		pass();

		// pass declare-attackers
		pass();
		pass();

		// skip declare-blockers and combat-damage

		// pass end-of-combat
		pass();
		pass();

		// pass post-combat main
		pass();
		pass();

		// pass end-of-turn
		pass();
		pass();

		// player-1's turn

		// pass upkeep
		pass();
		pass();

		// pass draw
		pass();
		pass();

		// play a Plains to eliminate all play-land-actions and make sure
		// Zealous
		// Guardian can be played at all (Tap the land for mana and 4 Play
		// Zealous
		// Guardian)
		respondWith(getLandAction(Plains.class));
		assertEquals(5, this.choices.size());

		// pass pre-combat main
		pass();
		pass();

		// pass start-of-combat
		pass();
		pass();

		// pass declare-attackers
		pass();
		pass();

		// skip declare-blockers and combat-damage

		// pass end-of-combat
		pass();
		pass();

		// pass post-combat main
		pass();
		pass();

		// pass end-of-turn
		pass();
		pass();

		// player-0's turn

		// make sure player-1 can play Zealous Guardian during player-0's upkeep
		pass();
		respondWith(getSpellAction(ZealousGuardian.class));
		// Choose which explosion to pay for
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(W)"));
		respondWith(getIntrinsicAbilityAction(SubType.PLAINS));
		assertEquals(1, this.game.actualState.stack().objects.size());
		donePlayingManaAbilities();
		pass();
		pass();
		assertEquals(3, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void flashback()
	{
		addDeck(OnewithNothing.class, Firebolt.class, Firebolt.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// during upkeep
		castAndResolveSpell(OnewithNothing.class, "B");
		// can't cast the Bolt now since it's a sorcery:
		assertEquals(0, this.choices.size());

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		// cast it from the graveyard in the main phase:
		respondWith(getSpellAction(Firebolt.class));
		respondWith(getTarget(Player.class));
		addMana("4R");
		donePlayingManaAbilities();
		pass();
		pass();

		// find it exiled:
		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals("Firebolt", this.game.actualState.exileZone().objects.get(0).getName());
	}

	@Test
	public void flashbackGranted()
	{
		addDeck(SnapcasterMage.class, DoomBlade.class, WildMongrel.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(WildMongrel.class);

		respondWith(getAbilityAction(WildMongrel.WildMongrelAbility0.class));
		respondWith(pullChoice(DoomBlade.class));
		pass();
		pass();
		respondWith(Color.GREEN);

		castAndResolveSpell(SnapcasterMage.class);
		// auto-target Doom Blade
		// resolve trigger:
		pass();
		pass();

		castAndResolveSpell(DoomBlade.class, WildMongrel.class);
	}

	@Test
	public void fortify()
	{
		addDeck(DryadArbor.class, BlackLotus.class, BlackLotus.class, Shock.class, Shock.class, DarksteelGarrison.class, DarksteelGarrison.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// upkeep
		pass();
		pass();

		// main phase
		respondWith(getLandAction(DryadArbor.class));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(DarksteelGarrison.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Fortify.FortifyAbility.class));
		// auto-target Arbor
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(DryadArbor.class));
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Dryad Arbor"));
	}

	@Test
	public void graftApplyAfterTappedReplacement()
	{

		// This test case tests that the land comes into play correctly when
		// cipt is applied first
		addDeck(LlanowarReborn.class, LlanowarReborn.class, LlanowarReborn.class, LlanowarReborn.class, LlanowarReborn.class, LlanowarReborn.class, LlanowarReborn.class);
		addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		// Play Llanowar Reborn, choosing either replacement effect to apply
		// first (CIP-Tapped, or Graft)
		respondWith(getLandAction(LlanowarReborn.class));

		respondWith(getChoiceByToString("Llanowar Reborn enters the battlefield tapped"));

		// all the lands in hand are unplayable, so the only action is the mana
		// of the land in play
		assertEquals(1, this.choices.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Llanowar Reborn"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped() == true);
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.TapForG.class));

		// since the land is tapped, the action is illegal and gets reversed
		assertTrue(this.choices.size() == 1.0);
		assertEquals(0, player(0).pool.converted());

	}

	@Test
	public void graftApplyBeforeTappedReplacement()
	{
		// This test case tests that the land comes into play correctly when
		// graft is applied first
		addDeck(LlanowarReborn.class, LlanowarReborn.class, LlanowarReborn.class, LlanowarReborn.class, LlanowarReborn.class, LlanowarReborn.class, LlanowarReborn.class);
		addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		// Play Llanowar Reborn, choosing either replacement effect to apply
		// first (CIP-Tapped, or Graft)
		respondWith(getLandAction(LlanowarReborn.class));
		respondWith(getChoiceByToString("This permanent enters the battlefield with a +1/+1 counter on it."));

		// all the lands in hand are unplayable, so the only action is the mana
		// of the land in play
		assertEquals(1, this.choices.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Llanowar Reborn"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped() == true);
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.TapForG.class));

		// since the land is tapped, the action is illegal and gets reversed
		assertTrue(this.choices.size() == 1.0);
		assertEquals(0, player(0).pool.converted());

	}

	@Test
	public void graftBasics()
	{
		// This test case makes sure graft is working
		addDeck(BlackLotus.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, LlanowarReborn.class);
		addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		// Play Llanowar Reborn, choosing either replacement effect to apply
		// first (CIP-Tapped, or Graft)
		respondWith(getLandAction(LlanowarReborn.class));
		respondWith(getChoiceByToString("Llanowar Reborn enters the battlefield tapped"));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(Sprout.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		// make sure the graft ability is on the stack
		assertEquals(1, this.game.actualState.stack().objects.size());

		// resolve graft ability, choosing not to move the counter
		pass();
		pass();
		respondWith(Answer.NO);

		// Saproling, Llanowar Reborn
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).counters.size());

		respondWith(getSpellAction(Sprout.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		// make sure the graft ability is on the stack
		assertEquals(1, this.game.actualState.stack().objects.size());

		// resolve graft ability, choosing to move the counter
		pass();
		pass();
		respondWith(Answer.YES);

		// Saproling, Saproling, Llanowar Reborn
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).counters.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).counters.size());

		respondWith(getSpellAction(Sprout.class));
		donePlayingManaAbilities();
		pass();
		pass();

		// make sure the graft ability is not on the stack
		assertEquals(0, this.game.actualState.stack().objects.size());

		// Saproling, Saproling, Saproling, Llanowar Reborn
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).counters.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).counters.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).counters.size());
	}

	@Test
	public void hideaway()
	{
		addDeck(Plains.class, Plains.class, AbsorbVis.class, Plains.class, Plains.class, Plains.class, Boomerang.class, DarksteelColossus.class, Twiddle.class, Twiddle.class, MosswortBridge.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(MosswortBridge.class));

		// Resolve Mosswort Bridge's triggered ability
		pass();
		pass();

		// Choose the Absorb Vis to be exiled and put the Plains on the bottom
		// in any order
		respondWith(pullChoice(AbsorbVis.class));
		respondArbitrarily();

		// We should be the only one who can see the Absorb Vis
		assertEquals(1, this.game.actualState.exileZone().objects.get(0).getVisibleTo().size());
		assertTrue(this.game.actualState.exileZone().objects.get(0).getVisibleTo().contains(player(0)));

		// Automatically target the Mosswort Bridge and untap it
		castAndResolveSpell(Twiddle.class);
		respondWith(Answer.YES);

		// Play Mosswort Bridge's hideaway activated ability
		respondWith(getAbilityAction(MosswortBridge.MosswortBridgeAbility2.class));
		addMana("G");
		donePlayingManaAbilities();
		pass();
		pass();

		// Make sure we weren't allowed to try and play the Absorb Vis
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);

		// Automatically target the Mosswort Bridge and untap it
		castAndResolveSpell(Twiddle.class);
		respondWith(Answer.YES);

		castAndResolveSpell(DarksteelColossus.class);

		// Play Mosswort Bridge's hideaway activated ability
		respondWith(getAbilityAction(MosswortBridge.MosswortBridgeAbility2.class));
		addMana("G");
		donePlayingManaAbilities();

		// In response, boomerang the Mosswort Bridge
		castAndResolveSpell(Boomerang.class, MosswortBridge.class);

		// We shouldn't be able to see the Absorb Vis now
		assertTrue(this.game.actualState.exileZone().objects.get(0).getVisibleTo().isEmpty());

		// Resolve Mosswort Bridge's activated ability
		pass();
		pass();

		respondWith(pullChoice(AbsorbVis.class));
		respondWith(getTarget(player(1)));
		pass();
		pass();

		assertEquals(24, player(0).lifeTotal);
		assertEquals(16, player(1).lifeTotal);
	}

	@Test
	public void infectCombatDamage()
	{
		addDeck(BlackcleaveGoblin.class, BlackcleaveGoblin.class, GiantGrowth.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(BlackcleaveGoblin.class);
		castAndResolveSpell(BlackcleaveGoblin.class);

		pass();
		castAndResolveSpell(Sprout.class);
		castAndResolveSpell(GiantGrowth.class, Token.class);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondArbitrarily(); // Attack with both goblins

		goToStep(Step.StepType.DECLARE_BLOCKERS);
		respondWith(pullChoice(Token.class));
		respondWith(pullChoice(BlackcleaveGoblin.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);

		Zone battlefield = this.game.actualState.battlefield();
		assertEquals(2, battlefield.objects.size());
		assertEquals("Saproling", battlefield.objects.get(0).getName());
		assertEquals(2, battlefield.objects.get(0).counters.size());
		assertEquals("Blackcleave Goblin", battlefield.objects.get(1).getName());
		assertEquals(20, player(1).lifeTotal);
		assertEquals(2, player(1).countPoisonCounters());
	}

	@Test
	public void kickedPermanent()
	{
		addDeck(GatekeeperofMalakir.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(GatekeeperofMalakir.class));
		respondWith((java.io.Serializable)this.choices.iterator().next());
		addMana("BBB");
		donePlayingManaAbilities();

		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
	}

	@Test
	public void kickedSpell()
	{
		addDeck(BurstLightning.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(BurstLightning.class));
		// Check that it can only be kicked once. The engine should reject this
		// choice and re-ask.
		respondWith((java.io.Serializable)this.choices.iterator().next(), (java.io.Serializable)this.choices.iterator().next());
		// Respond validly this time.
		respondWith((java.io.Serializable)this.choices.iterator().next());
		respondWith(getTarget(player(0)));
		addMana("4R");
		donePlayingManaAbilities();

		pass();
		pass();

		assertEquals(16, player(0).lifeTotal);
	}

	@Test
	public void kickedSpellRumblingAftershocks()
	{
		addDeck(RumblingAftershocks.class, EverflowingChalice.class, EverflowingChalice.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RumblingAftershocks.class, "4R");

		respondWith(getSpellAction(EverflowingChalice.class));
		// no kicks:
		respondWith();
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());

		respondWith(getSpellAction(EverflowingChalice.class));
		// three kicks:
		respondWith(this.choices.getOne(java.io.Serializable.class), this.choices.getOne(java.io.Serializable.class), this.choices.getOne(java.io.Serializable.class));
		addMana("6");
		donePlayingManaAbilities();
		// trigger target:
		respondWith(getTarget(player(1)));
		// resolve trigger:
		pass();
		pass();
		respondWith(Answer.YES);
		assertEquals(17, player(1).lifeTotal);
	}

	@Test
	public void lifelink()
	{
		// make sure lifelink works right after m10 rules change
		addDeck(BrionStoutarm.class, LoxodonWarhammer.class, GrizzlyBears.class, ChaosCharm.class, GrizzlyBears.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(BrionStoutarm.class));
		addMana("2RW");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(LoxodonWarhammer.class));
		addMana("3");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Equip.EquipAbility.class));
		addMana("3");
		donePlayingManaAbilities();
		pass();
		pass();

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

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(BrionStoutarm.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(BrionStoutarm.Fling.class));
		respondWith(pullChoice(GrizzlyBears.class));
		respondWith(getTarget(player(1)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		GameObject BrionStoutarm = this.game.actualState.battlefield().objects.get(2);

		// life link should only gain the life once, even if there are two
		// instances.
		assertEquals(18, player(1).lifeTotal);
		assertEquals(22, player(0).lifeTotal);
		assertEquals("Brion Stoutarm", BrionStoutarm.getName());
		assertEquals(2, Set.fromCollection(BrionStoutarm.getKeywordAbilities()).getAll(org.rnd.jmagic.abilities.keywords.Lifelink.class).size());
	}

	@Test
	public void madness()
	{
		addDeck(FieryTemper.class, OnewithNothing.class, FieryTemper.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(FieryTemper.class, OnewithNothing.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(FieryTemper.class));
		respondWith(getTarget(player(1)));
		addMana("1RR");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(17, player(1).lifeTotal);

		castAndResolveSpell(OnewithNothing.class);

		respondWith(Answer.YES);

		// Resolve Madness Trigger
		pass();
		pass();

		// Cast Fiery Temper for madness cost
		respondWith(Answer.YES);
		respondWith(getTarget(player(1)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(14, player(1).lifeTotal);

		pass();
		pass();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(OnewithNothing.class);

		respondWith(Answer.YES);

		// Resolve Madness Trigger
		pass();
		pass();

		// Dont cast fiery temper
		respondWith(Answer.NO);

		assertEquals(7, getGraveyard(0).objects.size());
		assertEquals(8, getGraveyard(1).objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());
	}

	@Test
	public void miracle()
	{
		addDeck(RevengeoftheHunted.class, RevengeoftheHunted.class, BirdsofParadise.class, VisionsofBeyond.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(RevengeoftheHunted.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(BirdsofParadise.class);

		goToStep(Step.StepType.DRAW);
		// choose to reveal:
		respondWith(Answer.YES);
		// resolve miracle trigger:
		pass();
		pass();
		// choose to cast:
		respondWith(Answer.YES);
		donePlayingManaAbilities();

		assertEquals(0, this.game.actualState.stack().objects.size());

		goToStep(Step.StepType.CLEANUP);
		respondWith(pullChoice(Card.class));

		goToStep(Step.StepType.DRAW);
		// choose to reveal:
		respondWith(Answer.YES);
		// resolve miracle trigger:
		pass();
		pass();
		// choose to cast:
		respondWith(Answer.YES);
		addMana("G");
		donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getPower());

		castAndResolveSpell(VisionsofBeyond.class);
		// no choice to reveal as it was the second card drawn this turn
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);
	}

	@Test
	public void morph()
	{
		addDeck(BatteringCraghorn.class, Jump.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		addDeck(GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		for(SanitizedPlayerAction choice: this.choices.getAll(SanitizedPlayerAction.class))
			if(choice.name.contains("face down"))
			{
				respondWith(choice);
				break;
			}

		addMana("3");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals("", this.game.actualState.battlefield().objects.get(0).getName());

		// we had a bug once where granting an ability to a face-down creature
		// caused a crash
		castAndResolveSpell(Jump.class);

		for(SanitizedPlayerAction choice: this.choices.getAll(SanitizedPlayerAction.class))
			if(choice.name.contains("face up"))
			{
				respondWith(choice);
				break;
			}
		addMana("1RR");
		donePlayingManaAbilities();

		// first strike, morph, flying
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());
	}

	@Test
	public void overload()
	{
		addDeck(Electrickery.class, Electrickery.class, SleeperAgent.class, RuneclawBear.class, AshcoatBear.class, Plains.class, Plains.class);
		addDeck(Electrickery.class, Electrickery.class, SleeperAgent.class, RuneclawBear.class, AshcoatBear.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RuneclawBear.class);
		castAndResolveSpell(SleeperAgent.class);

		pass();
		castAndResolveSpell(AshcoatBear.class);

		// Resolve Sleeper Agent's ability
		pass();
		pass();

		respondWith(getSpellAction(Electrickery.class));
		assertEquals(org.rnd.jmagic.engine.PlayerInterface.ChoiceType.ALTERNATE_COST, this.choiceType);
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(R)"));
		respondWith(getTarget(SleeperAgent.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals("Ashcoat Bear", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertEquals("Sleeper Agent", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getDamage());
		assertEquals("Runeclaw Bear", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());

		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);

		respondWith(getSpellAction(Electrickery.class));
		assertEquals(org.rnd.jmagic.engine.PlayerInterface.ChoiceType.ALTERNATE_COST, this.choiceType);
		respondWith(getCostCollection(org.rnd.jmagic.abilities.keywords.Overload.OVERLOAD_MANA, "(1)(R)"));
		addMana("1R");
		donePlayingManaAbilities();
		pass();
		pass();

		// One from the second Electrickery
		assertEquals("Ashcoat Bear", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getDamage());

		// One from each Electrickery
		assertEquals("Sleeper Agent", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getDamage());

		// Takes no damage because you control it
		assertEquals("Runeclaw Bear", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());

		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);
	}

	@Test
	public void overloadGetsAroundNontargetability()
	{
		addDeck(Progenitus.class, Dynacharge.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(Progenitus.class);

		respondWith(getSpellAction(Dynacharge.class));
		assertEquals(org.rnd.jmagic.engine.PlayerInterface.ChoiceType.ALTERNATE_COST, this.choiceType);
		respondWith(getCostCollection(org.rnd.jmagic.abilities.keywords.Overload.OVERLOAD_MANA, "(2)(R)"));
		addMana("2R");
		donePlayingManaAbilities();
		pass();
		pass();

		GameObject progenitus = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Progenitus", progenitus.getName());
		assertEquals(12, progenitus.getPower());
	}

	@Test
	public void persist()
	{
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, SafeholdElite.class, Shock.class, Shock.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(SafeholdElite.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(G)"));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.GREEN));
		pass();
		pass();

		// Elite, Lotus
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Safehold Elite"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(SafeholdElite.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Resolve Persist Trigger
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Safehold Elite"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(SafeholdElite.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Persist shouldn't have triggered due to intervening-if
		assertEquals(0, this.game.actualState.stack().objects.size());

		// Nothing in play
		assertEquals(0, this.game.actualState.battlefield().objects.size());

		// 2x Shock, 2x Black Lotus, 1x Safehold Elite
		assertEquals(5, getGraveyard(0).objects.size());

	}

	@Test
	public void populate()
	{
		addDeck(CoursersAccord.class, CoursersAccord.class, RuneclawBear.class, Sprout.class, Plains.class, Plains.class, Plains.class);
		addDeck(CoursersAccord.class, CoursersAccord.class, RuneclawBear.class, Sprout.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RuneclawBear.class);
		castAndResolveSpell(Sprout.class);

		castAndResolveSpell(CoursersAccord.class);
		// Runeclaw Bear, Saproling, Centaur
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.choices.size());

		{
			SanitizedGameObject tokenA = pullChoice(Token.class);
			SanitizedGameObject tokenB = pullChoice(Token.class);
			assertEquals(0, this.choices.size());

			if(tokenA.name.equals("Saproling"))
			{
				assertEquals("Centaur", tokenB.name);
				respondWith(tokenA);
			}
			else if(tokenB.name.equals("Saproling"))
			{
				assertEquals("Centaur", tokenA.name);
				respondWith(tokenB);
			}
			else
				fail("Saproling token not found.");
		}

		castAndResolveSpell(CoursersAccord.class);
		// Runeclaw Bear, Saproling, Centaur, Saproling (copy), Centaur
		assertEquals(5, this.game.actualState.battlefield().objects.size());
		assertEquals(4, this.choices.size());

		{
			int saprolings = 0;
			int centaurs = 0;
			SanitizedGameObject response = null;

			for(SanitizedGameObject token: this.choices.getAll(SanitizedGameObject.class))
			{
				if(token.name.equals("Saproling"))
				{
					saprolings++;
				}
				else if(token.name.equals("Centaur"))
				{
					centaurs++;
					response = token;
				}
				else
				{
					fail("Non-saproling, non-centaur found.");
				}
			}

			if(saprolings != 2)
				fail("Wrong number of Saprolings found.");

			if(centaurs != 2)
				fail("Wrong number of Centaurs found.");

			if(response == null)
				fail("...???");

			respondWith(response);
		}

		// Runeclaw Bear, Saproling, Centaur, Saproling (copy), Centaur, Centaur
		// (copy)
		assertEquals(6, this.game.actualState.battlefield().objects.size());
		assertEquals("Centaur", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Centaur", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals("Centaur", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(4).getName());
		assertEquals("Runeclaw Bear", this.game.actualState.battlefield().objects.get(5).getName());
	}

	@Test
	public void proliferate()
	{
		addDeck(ContagionClasp.class, AvenRiftwatcher.class, BlackcleaveGoblin.class, AncestralVision.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(BlackcleaveGoblin.class);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(BlackcleaveGoblin.class));

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		castAndResolveSpell(AvenRiftwatcher.class);
		// life gain trigger:
		pass();
		pass();

		castAndResolveSpell(ContagionClasp.class);
		// clasp's trigger:
		respondWith(getTarget(AvenRiftwatcher.class));
		pass();
		pass();

		SanitizedPlayerAction response = null;
		for(SanitizedPlayerAction action: this.choices.getAll(SanitizedPlayerAction.class))
			if(action.name.equals("Suspend Ancestral Vision"))
			{
				response = action;
				break;
			}
		if(response == null)
			fail("Suspend action not found.");
		respondWith(response);
		addMana("U");
		donePlayingManaAbilities();

		respondWith(getAbilityAction(ContagionClasp.ContagionClaspAbility1.class));
		addMana("4");
		donePlayingManaAbilities();
		pass();
		pass();

		// select the riftwatcher and player 1
		respondArbitrarily();
		// there should be no choice for player 1, they get another poison
		// counter
		// choose a time counter for the riftwatcher:
		respondWith(Counter.CounterType.TIME);
		assertEquals(3, player(1).counters.size());
		assertEquals("Aven Riftwatcher", this.game.actualState.battlefield().objects.get(1).getName());
		// four time counters, one -1/-1 counter
		assertEquals(5, this.game.actualState.battlefield().objects.get(1).counters.size());
	}

	@Test
	public void protectionCantBeAttached()
	{
		addDeck(UnholyStrength.class, SimicGuildmage.class, PaladinenVec.class, Disenchant.class, ShiftingSky.class, Replenish.class, Plains.class);
		addDeck(PaintersServant.class, Replenish.class, OnewithNothing.class, BlanchwoodArmor.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(PaladinenVec.class);

		respondWith(getSpellAction(SimicGuildmage.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(U)(U)"));
		addMana("UU");
		donePlayingManaAbilities();
		pass();
		pass();

		// Unholy Strength can only target Simic Guildmage
		castAndResolveSpell(UnholyStrength.class);

		{
			respondWith(getAbilityAction(SimicGuildmage.MoveAura.class));
			// auto-target Unholy Strength
			addMana("1U");
			donePlayingManaAbilities();
			pass();
			pass();

			// there are no legal choices, no choice is made
			assertEquals(this.choiceType, PlayerInterface.ChoiceType.NORMAL_ACTIONS);

			assertEquals(3, this.game.actualState.battlefield().objects.size());

			GameObject unholy = this.game.actualState.battlefield().objects.get(0);
			GameObject simic = this.game.actualState.battlefield().objects.get(1);
			GameObject paladin = this.game.actualState.battlefield().objects.get(2);

			assertEquals("Unholy Strength", unholy.getName());
			assertEquals("Simic Guildmage", simic.getName());
			assertEquals("Paladin en-Vec", paladin.getName());

			assertEquals(unholy.getAttachedTo(), simic.ID);
		}

		{
			// Disenchant can only target Unholy Strength
			castAndResolveSpell(Disenchant.class);

			castAndResolveSpell(ShiftingSky.class);
			respondWith(Color.BLUE);

			// Even though Shifting Sky would make Unholy Strength blue, Unholy
			// Strength is black and won't be able to attach to Paladin en-Vec,
			// so the only legal choice will be Simic Guildmage and the choice
			// will be made automatically
			castAndResolveSpell(Replenish.class);

			assertEquals(this.choiceType, PlayerInterface.ChoiceType.NORMAL_ACTIONS);

			// Replenish, Disenchant
			assertEquals(2, player(0).getGraveyard(this.game.actualState).objects.size());
			// Unholy Strength, Shifting Sky, Simic Guildmage, Paladin en-Vec
			assertEquals(4, this.game.actualState.battlefield().objects.size());
		}

		goToPhase(Phase.PhaseType.ENDING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		{
			castAndResolveSpell(PaintersServant.class);
			respondWith(Color.BLACK);

			respondWith(getSpellAction(Replenish.class));
			addMana("3W");
			donePlayingManaAbilities();

			// With Replenish on the stack, cast One with Nothing
			castAndResolveSpell(OnewithNothing.class);

			// Resolve Replenish
			pass();
			pass();

			// Even though Painter's Servant is made Blanchwood Armor black,
			// Paladin en-Vec should still be a legal choice to enchant
			respondWith(pullChoice(PaladinenVec.class));

			// Blanchwood Armor should promptly fall off due to Painter's
			// Servant

			// Blanchwood Armor, Replenish, Plains, Plains, Plains, Plains, One
			// with Nothing
			assertEquals(7, player(1).getGraveyard(this.game.actualState).objects.size());
			// Painter's Servant, Unholy Strength, Shifting Sky, Simic
			// Guildmage, Paladin en-Vec
			assertEquals(5, this.game.actualState.battlefield().objects.size());
		}
	}

	@Test
	public void protectionCantBeBlockedCantBeTargetted()
	{
		addDeck(MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class);
		addDeck(Plains.class, Plains.class, Plains.class, PaladinenVec.class, Shock.class, Pyroclasm.class, GrizzlyBears.class, ChaosCharm.class, UnholyStrength.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Player 0 does nothing

		goToStep(Step.StepType.UPKEEP);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(PaladinenVec.class));
		addMana("1WW");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Shock.class));
		// Verify the paladin is not a legal target, then hit player 1
		assertEquals(2, this.choices.size());
		boolean targetingPlayerOne = false;
		boolean targetingPlayerTwo = false;
		for(SanitizedTarget t: this.choices.getAll(SanitizedTarget.class))
		{
			if(t.targetID == player(0).ID)
				targetingPlayerOne = true;
			else if(t.targetID == player(1).ID)
				targetingPlayerTwo = true;
		}
		assertTrue("Player one not found in choice of targets", targetingPlayerOne);
		assertTrue("Player two not found in choice of targets", targetingPlayerTwo);
		respondWith(getTarget(player(1)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(GrizzlyBears.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Pyroclasm.class));
		addMana("1R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Paladin en-Vec"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());

		// End the turn
		goToStep(Step.StepType.UPKEEP);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(MoggFanatic.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		// End the turn
		goToStep(Step.StepType.UPKEEP);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(UnholyStrength.class));
		// Auto-target Mogg Fanatic
		addMana("B");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Unholy Strength"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Mogg Fanatic"));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Paladin en-Vec"));

		// move the unholy strength from the mogg to the paladin without
		// targetting
		this.game.physicalState.battlefield().objects.get(0).setAttachedTo(this.game.physicalState.battlefield().objects.get(2).ID);
		this.game.physicalState.battlefield().objects.get(2).addAttachment(this.game.physicalState.battlefield().objects.get(0).ID);
		this.game.physicalState.battlefield().objects.get(1).removeAttachment(this.game.physicalState.battlefield().objects.get(0).ID);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mogg Fanatic"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Paladin en-Vec"));

		// Attack with the Paladin
		respondWith(pullChoice(PaladinenVec.class));

		goToStep(Step.StepType.DECLARE_BLOCKERS);

		// Attempt to block with Mogg Fanatic
		respondWith(pullChoice(MoggFanatic.class));

		// Since the block failed, player is being re-queried
		assertEquals(1, this.choices.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mogg Fanatic"));
		assertTrue(getChoice(this.game.actualState.battlefield().objects.get(0)) != null);

		// Declare no blocks
		declareNoBlockers();

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		pass();
		respondWith(getAbilityAction(MoggFanatic.SacPing.class));

		// Don't bother finishing playing the ability, make some assertions then
		// exit the test

		assertEquals(3, this.choices.size());
		boolean targetingMoggFanatic = false;
		targetingPlayerOne = false;
		targetingPlayerTwo = false;
		for(SanitizedTarget t: this.choices.getAll(SanitizedTarget.class))
		{
			if(t.targetID == this.game.actualState.battlefield().objects.get(0).ID)
				targetingMoggFanatic = true;
			else if(t.targetID == player(0).ID)
				targetingPlayerOne = true;
			else if(t.targetID == player(1).ID)
				targetingPlayerTwo = true;
		}
		assertTrue("Player one not found in choice of targets", targetingPlayerOne);
		assertTrue("Player two not found in choice of targets", targetingPlayerTwo);
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mogg Fanatic"));
		assertTrue("Mogg Fanatic not found in choice of targets", targetingMoggFanatic);

		// attacked by paladin
		assertEquals(18, player(0).lifeTotal);

		// hit by a shock
		assertEquals(18, player(1).lifeTotal);

		assertEquals(0, getGraveyard(0).objects.size());

		assertEquals(4, getGraveyard(1).objects.size());
		assertTrue(getGraveyard(1).objects.get(0).getName().equals("Unholy Strength"));
		assertTrue(getGraveyard(1).objects.get(1).getName().equals("Grizzly Bears"));
		assertTrue(getGraveyard(1).objects.get(2).getName().equals("Pyroclasm"));
		assertTrue(getGraveyard(1).objects.get(3).getName().equals("Shock"));

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mogg Fanatic"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Paladin en-Vec"));

	}

	@Test
	public void protectionPlayer()
	{
		addDeck(SehtsTiger.class, GrizzlyBears.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(SehtsTiger.class, LightningBolt.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		pass();

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();

		respondWith(getSpellAction(SehtsTiger.class));
		addMana("2WW");
		donePlayingManaAbilities();
		pass();
		pass();

		// Resolve Seht's Tiger's ability
		pass();
		pass();

		assertEquals(5, this.choices.size());
		assertTrue(this.choices.containsAll(org.rnd.jmagic.engine.Color.allColors()));

		respondWith(Color.RED);

		// Attempt to resolve the lightning bolt
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);

		respondWith(getSpellAction(GrizzlyBears.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(GrizzlyBears.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		pass();

		respondWith(getSpellAction(SehtsTiger.class));
		addMana("2WW");
		donePlayingManaAbilities();
		pass();
		pass();

		// Resolve Seht's ability
		pass();
		pass();
		respondWith(Color.GREEN);

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(GrizzlyBears.class));
		pass();
		pass();

		goToStep(Step.StepType.COMBAT_DAMAGE);
		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);
	}

	@Test
	public void rebound()
	{
		addDeck(Staggershock.class, Staggershock.class, Staggershock.class, Staggershock.class, Staggershock.class, Staggershock.class, Staggershock.class, Staggershock.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getSpellAction(Staggershock.class));
		respondWith(getTarget(player(1)));
		addMana("2R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals(18, player(1).lifeTotal);

		// go through player 1's turn:
		goToPhase(Phase.PhaseType.BEGINNING);
		goToStep(Step.StepType.CLEANUP);

		// discard a card (has 8 cards):
		respondWith(pullChoice(Plains.class));

		// the next time a player gets a choice should be player 0 getting to
		// respond to rebound's trigger during the upkeep:
		assertEquals(Step.StepType.UPKEEP, this.game.actualState.currentStep().type);
		assertEquals(1, this.game.actualState.stack().objects.size());

		// resolve the trigger:
		pass();
		pass();

		// choose to cast staggershock, and pick a target:
		respondWith(pullChoice(Staggershock.class));
		respondWith(getTarget(player(1)));

		// resolve staggershock:
		pass();
		pass();

		assertEquals(16, player(1).lifeTotal);
	}

	@Test
	public void recover()
	{
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, GrimHarvest.class, MoggFanatic.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Grim Harvest
		assertEquals(1, getHand(0).objects.size());
		// Black Lotus
		assertEquals(1, getGraveyard(0).objects.size());
		// Black Lotus x 4, Mogg Fanatic
		assertEquals(5, this.game.actualState.battlefield().objects.size());

		respondWith(getAbilityAction(MoggFanatic.SacPing.class));
		respondWith(getTarget(player(1)));
		pass();
		pass();

		// Grim Harvest
		assertEquals(1, getHand(0).objects.size());
		// Black Lotus, Mogg Fanatic
		assertEquals(2, getGraveyard(0).objects.size());
		// Black Lotus x 4
		assertEquals(4, this.game.actualState.battlefield().objects.size());

		respondWith(getSpellAction(GrimHarvest.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.BLACK));
		pass();
		pass();

		// Mogg Fanatic
		assertEquals(1, getHand(0).objects.size());
		// Black Lotus x 2, Grim Harvest
		assertEquals(3, getGraveyard(0).objects.size());
		// Black Lotus x 3
		assertEquals(3, this.game.actualState.battlefield().objects.size());

		respondWith(getSpellAction(MoggFanatic.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// (Nothing)
		assertEquals(0, getHand(0).objects.size());
		// Black Lotus x 2, Grim Harvest
		assertEquals(3, getGraveyard(0).objects.size());
		// Black Lotus x 3, Mogg Fanatic
		assertEquals(4, this.game.actualState.battlefield().objects.size());

		// Stack the mogg ability (grim harvest triggers and stacks)
		respondWith(getAbilityAction(MoggFanatic.SacPing.class));
		respondWith(getTarget(player(1)));

		// (Nothing)
		assertEquals(0, getHand(0).objects.size());
		// Black Lotus x 2, Grim Harvest, Mogg Fanatic
		assertEquals(4, getGraveyard(0).objects.size());
		// Black Lotus x 3
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		// Grim Harvest Trigger, Mogg Ability
		assertEquals(2, this.game.actualState.stack().objects.size());

		// resolve grim harvest trigger
		pass();
		pass();
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		respondWith(Answer.YES);
		respondWith(getMana(Color.BLACK, Color.BLACK, Color.BLACK));

		// Grim Harvest
		assertEquals(1, getHand(0).objects.size());
		// Black Lotus x 3, Mogg Fanatic
		assertEquals(4, getGraveyard(0).objects.size());
		// Black Lotus x 2
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		// Mogg Ability
		assertEquals(1, this.game.actualState.stack().objects.size());

		// leave mogg trigger on the stack for now, cast grim harvest
		respondWith(getSpellAction(GrimHarvest.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.BLACK));
		pass();
		pass();

		// Mogg Fanatic
		assertEquals(1, getHand(0).objects.size());
		// Black Lotus x 4, Grim Harvest
		assertEquals(5, getGraveyard(0).objects.size());
		// Black Lotus x 1
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		// Mogg Ability
		assertEquals(1, this.game.actualState.stack().objects.size());

		// resolve mogg ability
		pass();
		pass();

		// Mogg Fanatic
		assertEquals(1, getHand(0).objects.size());
		// Black Lotus x 4, Grim Harvest
		assertEquals(5, getGraveyard(0).objects.size());
		// Black Lotus x 1
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		// (Nothing)
		assertEquals(0, this.game.actualState.stack().objects.size());

		respondWith(getSpellAction(MoggFanatic.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// (Nothing)
		assertEquals(0, getHand(0).objects.size());
		// Black Lotus x 4, Grim Harvest
		assertEquals(5, getGraveyard(0).objects.size());
		// Black Lotus x 1, Mogg Fanatic
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		// (Nothing)
		assertEquals(0, this.game.actualState.stack().objects.size());

		respondWith(getAbilityAction(MoggFanatic.SacPing.class));
		respondWith(getTarget(player(1)));

		// (Nothing)
		assertEquals(0, getHand(0).objects.size());
		// Black Lotus x 4, Grim Harvest, Mogg Fanatic
		assertEquals(6, getGraveyard(0).objects.size());
		// Black Lotus x 1
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		// Grim Harvest Trigger, Mogg ability
		assertEquals(2, this.game.actualState.stack().objects.size());

		pass();
		pass();
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		respondWith(Answer.YES);
		respondWith(getMana(Color.BLACK, Color.BLACK, Color.BLACK));

		// Grim Harvest
		assertEquals(1, getHand(0).objects.size());
		// Black Lotus x 5, Mogg Fanatic
		assertEquals(6, getGraveyard(0).objects.size());
		// (Nothing)
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		// Mogg ability
		assertEquals(1, this.game.actualState.stack().objects.size());

		respondWith(getSpellAction(GrimHarvest.class));
		donePlayingManaAbilities();
		pass();
		pass();

		// Mogg Fanatic
		assertEquals(1, getHand(0).objects.size());
		// Black Lotus x 5, Grim Harvest
		assertEquals(6, getGraveyard(0).objects.size());
		// (Nothing)
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		// Mogg ability
		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();

		// Mogg Fanatic
		assertEquals(1, getHand(0).objects.size());
		// Black Lotus x 5, Grim Harvest
		assertEquals(6, getGraveyard(0).objects.size());
		// (Nothing)
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		// (Nothing)
		assertEquals(0, this.game.actualState.stack().objects.size());

		assertEquals(17, player(1).lifeTotal);
	}

	@Test
	public void reinforce()
	{
		addDeck(MoggFanatic.class, HuntingTriad.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Reinforce.ReinforceAbility.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.GREEN, Color.GREEN));
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).counters.size());
	}

	@Test
	public void scavenge()
	{
		addDeck(RagingGoblin.class, OnewithNothing.class, ZanikevLocust.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RagingGoblin.class);

		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());

		castAndResolveSpell(OnewithNothing.class);

		assertEquals(6, player(0).getGraveyard(this.game.actualState).objects.size());

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		// Declare no attackers
		respondWith();

		// Can't Scavenge at instant-speed
		assertEquals(0, this.choices.size());

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Scavenge.ExileForCounters.class));
		// Automatically target the Raging Goblin
		addMana("2BB");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(5, player(0).getGraveyard(this.game.actualState).objects.size());
		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());
	}

	@Test
	public void soulbond()
	{
		addDeck(DreadStatuary.class, Wingcrafter.class, Wingcrafter.class, GrizzlyBears.class, DoomBlade.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(DreadStatuary.class));
		respondWith(getAbilityAction(DreadStatuary.Animate.class));
		addMana("(4)");
		donePlayingManaAbilities();
		pass();
		pass();

		castAndResolveSpell(Wingcrafter.class);

		{
			GameObject wingcrafter = this.game.actualState.battlefield().objects.get(0);
			assertEquals("Wingcrafter", wingcrafter.getName());
			assertEquals(1, wingcrafter.getKeywordAbilities().size());
			assertEquals(org.rnd.jmagic.abilities.keywords.Soulbond.class, wingcrafter.getKeywordAbilities().get(0).getClass());

			GameObject statuary = this.game.actualState.battlefield().objects.get(1);
			assertEquals("Dread Statuary", statuary.getName());
			assertEquals(0, statuary.getKeywordAbilities().size());
		}

		// Wingcrafter ability on the stack
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();
		respondWith(Answer.YES);
		// Automatically choose Dread Statuary

		{
			GameObject wingcrafter = this.game.actualState.battlefield().objects.get(0);
			assertEquals("Wingcrafter", wingcrafter.getName());
			assertEquals(2, wingcrafter.getKeywordAbilities().size());
			assertEquals(org.rnd.jmagic.abilities.keywords.Soulbond.class, wingcrafter.getKeywordAbilities().get(0).getClass());
			assertEquals(org.rnd.jmagic.abilities.keywords.Flying.class, wingcrafter.getKeywordAbilities().get(1).getClass());

			GameObject statuary = this.game.actualState.battlefield().objects.get(1);
			assertEquals("Dread Statuary", statuary.getName());
			assertEquals(1, statuary.getKeywordAbilities().size());
			assertEquals(org.rnd.jmagic.abilities.keywords.Flying.class, statuary.getKeywordAbilities().get(0).getClass());
		}

		castAndResolveSpell(Wingcrafter.class);

		// No other unpaired creatures to choose from, so the ability shouldn't
		// have triggered
		assertEquals(0, this.game.actualState.stack().objects.size());

		castAndResolveSpell(GrizzlyBears.class);

		// Second Wingcrafter ability on the stack
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();
		respondWith(Answer.YES);
		// Automatically choose Grizzly Bears

		{
			GameObject bears = this.game.actualState.battlefield().objects.get(0);
			assertEquals(1, bears.getKeywordAbilities().size());
			assertEquals(org.rnd.jmagic.abilities.keywords.Flying.class, bears.getKeywordAbilities().get(0).getClass());

			GameObject wingcrafter2 = this.game.actualState.battlefield().objects.get(1);
			assertEquals(2, wingcrafter2.getKeywordAbilities().size());
			assertEquals(org.rnd.jmagic.abilities.keywords.Soulbond.class, wingcrafter2.getKeywordAbilities().get(0).getClass());
			assertEquals(org.rnd.jmagic.abilities.keywords.Flying.class, wingcrafter2.getKeywordAbilities().get(1).getClass());
		}

		castAndResolveSpell(DoomBlade.class, GrizzlyBears.class);

		{
			GameObject wingcrafter2 = this.game.actualState.battlefield().objects.get(0);
			assertEquals(1, wingcrafter2.getKeywordAbilities().size());
			assertEquals(org.rnd.jmagic.abilities.keywords.Soulbond.class, wingcrafter2.getKeywordAbilities().get(0).getClass());
		}

		goToPhase(Phase.PhaseType.BEGINNING);

		{
			// The Dread Statuary should have stopped being a creature and the
			// first Wingcrafter should be unpaired
			GameObject wingcrafter1 = this.game.actualState.battlefield().objects.get(1);
			assertEquals(1, wingcrafter1.getKeywordAbilities().size());
			assertEquals(org.rnd.jmagic.abilities.keywords.Soulbond.class, wingcrafter1.getKeywordAbilities().get(0).getClass());

			GameObject statuary = this.game.actualState.battlefield().objects.get(2);
			assertEquals(0, statuary.getKeywordAbilities().size());
		}
	}

	@Test
	public void soulshift()
	{
		addDeck(Rootrunner.class, Frostling.class, Frostling.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Island.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(Frostling.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getAbilityAction(Frostling.SacPing.class));
		pass();
		pass();

		respondWith(getSpellAction(Frostling.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getAbilityAction(Frostling.SacPing.class));
		pass();
		pass();

		assertEquals(3, getGraveyard(0).objects.size());

		respondWith(getLandAction(Island.class));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(Rootrunner.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		pass();
		pass();

		respondWith(getAbilityAction(Rootrunner.RootrunnerAbility.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.GREEN));

		// Soulshift Triggers, target one of the Frostlings
		respondWith(getTarget(Frostling.class));

		assertEquals(2, this.game.actualState.stack().objects.size());

		assertEquals(0, getHand(0).objects.size());

		// Resolve soulshift, choosing to return
		pass();
		pass();
		respondWith(Answer.YES);

		assertEquals(1, getHand(0).objects.size());

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(0, getLibrary(0).objects.size());

		// Resolve Rootrunner Ability
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(1, getLibrary(0).objects.size());

	}

	@Test
	public void splice()
	{
		this.addDeck(LavaSpike.class, GlacialRay.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(LavaSpike.class));
		// splice Glacial Ray:
		respondWith(pullChoice(GlacialRay.class));
		// target player 1 with Spike:
		respondWith(getTarget(player(1)));
		// target player 1 with Ray:
		respondWith(getTarget(player(1)));

		addMana("1RR");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(15, player(1).lifeTotal);
	}

	@Test
	public void storm()
	{
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BrainFreeze.class, BrainFreeze.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(24, getLibrary(1).objects.size());

		respondWith(getSpellAction(BrainFreeze.class));
		respondWith(getTarget(player(1)));
		addMana("1U");
		donePlayingManaAbilities();

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(24, getLibrary(1).objects.size());

		// resolve storm trigger
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(24, getLibrary(1).objects.size());

		// resolve brain freeze
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(21, getLibrary(1).objects.size());

		for(int i = 0; i < 5; i++)
		{
			respondWith(getSpellAction(BlackLotus.class));
			pass();
			pass();
		}

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(21, getLibrary(1).objects.size());

		respondWith(getSpellAction(BrainFreeze.class));
		respondWith(getTarget(player(1)));
		addMana("1U");
		donePlayingManaAbilities();

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(21, getLibrary(1).objects.size());

		// resolve storm trigger
		pass();
		pass();

		for(int i = 0; i < 6; i++)
			respondWith(Answer.NO);

		for(int i = 7; i > 0; i--)
		{
			assertEquals(i, this.game.actualState.stack().objects.size());
			assertEquals(21 - 3 * (7 - i), getLibrary(1).objects.size());
			pass();
			pass();
		}

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(0, getLibrary(1).objects.size());
	}

	@Test
	public void sunburstCreature()
	{
		addDeck(SuntouchedMyr.class, SuntouchedMyr.class, SculptingSteel.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(SuntouchedMyr.class, "1WR");

		// Suntouched Myr
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		castAndResolveSpell(SuntouchedMyr.class, "3");

		// Suntouched Myr
		assertEquals(1, this.game.actualState.battlefield().objects.size());

		castAndResolveSpell(SculptingSteel.class, "UBG");
		respondWith(Answer.YES);

		// Sculpting Steel (actually a Suntouched Myr), Suntouched Myr
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getToughness());
	}

	@Test
	public void sunburstNonCreature()
	{
		addDeck(EngineeredExplosives.class, Ornithopter.class, Plains.class, EngineeredExplosives.class, AngelsFeather.class, MarchoftheMachines.class, EngineeredExplosives.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(Plains.class));

		respondWith(getCastSpellOrActivateAbilityAction(Ornithopter.class));
		pass();
		pass();

		respondWith(getSpellAction(EngineeredExplosives.class));
		respondWith(0);
		pass();
		pass();

		// Engineered Explosives, Ornithopter, Plains
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getCastSpellOrActivateAbilityAction(EngineeredExplosives.Explode.class));
		addMana("2");
		donePlayingManaAbilities();
		pass();
		pass();

		// Plains
		assertEquals(1, this.game.actualState.battlefield().objects.size());

		castAndResolveSpell(AngelsFeather.class, "2");

		respondWith(getSpellAction(EngineeredExplosives.class));
		respondWith(2);
		addMana("WR");
		donePlayingManaAbilities();
		pass();
		pass();

		// Engineered Explosives, Angel's Feather, Plains
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(Counter.CounterType.CHARGE, this.game.actualState.battlefield().objects.get(0).counters.get(0).getType());
		assertEquals(Counter.CounterType.CHARGE, this.game.actualState.battlefield().objects.get(0).counters.get(1).getType());

		respondWith(getCastSpellOrActivateAbilityAction(EngineeredExplosives.Explode.class));
		addMana("2");
		donePlayingManaAbilities();
		pass();
		pass();

		// Plains
		assertEquals(1, this.game.actualState.battlefield().objects.size());

		castAndResolveSpell(MarchoftheMachines.class, "3U");

		respondWith(getSpellAction(EngineeredExplosives.class));
		respondWith(1);
		addMana("G");
		donePlayingManaAbilities();
		pass();
		pass();

		// The Engineered Explosives should have died since it got charge
		// counters instead of +1/+1 counters
		// March of the Machines, Plains
		assertEquals(2, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void suspendCreature()
	{
		addDeck(KeldonHalberdier.class, FuryCharm.class, FuryCharm.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(ActofTreason.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		{
			SanitizedPlayerAction response = null;
			for(SanitizedPlayerAction action: this.choices.getAll(SanitizedPlayerAction.class))
				if(action.name.equals("Suspend Keldon Halberdier"))
				{
					response = action;
					break;
				}

			if(response == null)
				fail("Suspend action not found.");

			respondWith(response);
		}

		addMana("R");
		donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals("Keldon Halberdier", this.game.actualState.exileZone().objects.get(0).getName());
		assertEquals(4, this.game.actualState.exileZone().objects.get(0).counters.size());

		respondWith(getSpellAction(FuryCharm.class));
		// only selectable mode, therefore this is actually auto-chosen by the
		// engine -RulesGuru :
		// respondWith(getMode(EventType.REMOVE_COUNTERS));
		// target should be auto-chosen
		addMana("1R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals("Keldon Halberdier", this.game.actualState.exileZone().objects.get(0).getName());
		assertEquals(2, this.game.actualState.exileZone().objects.get(0).counters.size());

		respondWith(getSpellAction(FuryCharm.class));
		// only selectable mode, therefore this is actually auto-chosen by the
		// engine -RulesGuru :
		// respondWith(getMode(EventType.REMOVE_COUNTERS));
		// target should be auto-chosen
		addMana("1R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals("Keldon Halberdier", this.game.actualState.exileZone().objects.get(0).getName());
		assertEquals(0, this.game.actualState.exileZone().objects.get(0).counters.size());

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());

		pass();
		pass();

		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.stack().objects.get(0).getKeywordAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());

		pass();
		pass();

		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Keldon Halberdier", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(player(0).ID, this.game.actualState.battlefield().objects.get(0).controllerID);
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());

		goToPhase(Phase.PhaseType.ENDING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Keldon Halberdier", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(player(0).ID, this.game.actualState.battlefield().objects.get(0).controllerID);
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());

		castAndResolveSpell(ActofTreason.class, "2R");

		pass();
		pass();

		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Keldon Halberdier", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(player(1).ID, this.game.actualState.battlefield().objects.get(0).controllerID);
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());
	}

	@Test
	public void suspendNonCreature()
	{
		addDeck(AncestralVision.class, AncestralVision.class, AncestralVision.class, AncestralVision.class, AncestralVision.class, AncestralVision.class, AncestralVision.class);
		addDeck(AncestralVision.class, AncestralVision.class, AncestralVision.class, AncestralVision.class, AncestralVision.class, AncestralVision.class, AncestralVision.class, AncestralVision.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		assertEquals(0, this.choices.size());

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals(14, this.choices.size());

		{
			SanitizedPlayerAction response = null;
			for(SanitizedPlayerAction action: this.choices.getAll(SanitizedPlayerAction.class))
				if(action.name.equals("Suspend Ancestral Vision"))
				{
					response = action;
					break;
				}

			if(response == null)
				fail("Suspend action not found.");

			respondWith(response);
		}

		addMana("U");
		donePlayingManaAbilities();

		{
			assertEquals(12, this.choices.size());
			assertEquals(1, this.game.actualState.exileZone().objects.size());

			GameObject suspendedCard = this.game.physicalState.exileZone().objects.get(0);

			assertEquals(4, suspendedCard.counters.size());

			// Remove three of the counters manually
			for(int i = 0; i < 3; i++)
				suspendedCard.counters.remove(1);
		}

		goToPhase(Phase.PhaseType.ENDING);
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.ENDING);
		pass();
		pass();
		respondWith(pullChoice(AncestralVision.class));

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(org.rnd.jmagic.abilities.keywords.Suspend.SuspendRemoveCounter.class, this.game.actualState.stack().objects.get(0).getClass());

		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(org.rnd.jmagic.abilities.keywords.Suspend.SuspendCastSpell.class, this.game.actualState.stack().objects.get(0).getClass());

		pass();
		pass();

		respondWith(getTarget(player(0)));

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(AncestralVision.class, this.game.actualState.stack().objects.get(0).getClass());

		pass();
		pass();

		assertEquals(player(1).ID, this.winner.ID);
	}

	@Test
	public void tribute()
	{
		addDeck(ThunderBrute.class, ThunderBrute.class, ThunderBrute.class, ThunderBrute.class, ThunderBrute.class, ThunderBrute.class, ThunderBrute.class);
		addDeck(ThunderBrute.class, ThunderBrute.class, ThunderBrute.class, ThunderBrute.class, ThunderBrute.class, ThunderBrute.class, ThunderBrute.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(ThunderBrute.class);
		// tribute begins to apply, auto-choose the only opponent
		// that opponent chooses to add +1/+1 counters:
		respondWith(Answer.YES);

		GameObject brute = this.game.actualState.battlefield().objects.get(0);
		assertEquals(8, brute.getPower());
		// trample, tribute
		assertEquals(2, brute.getKeywordAbilities().size());

		castAndResolveSpell(ThunderBrute.class);
		// this time no counters
		respondWith(Answer.NO);

		// tribute-not-paid ability on the stack
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		brute = this.game.actualState.battlefield().objects.get(0);
		assertEquals(5, brute.getPower());
		// trample, tribute, haste
		assertEquals(3, brute.getKeywordAbilities().size());
	}

	@Test
	public void typeCycling()
	{
		addDeck(RelentlessRats.class, ReflectingPool.class, Island.class, Island.class, Island.class, Island.class, Island.class, Island.class, IndomitableAncients.class, FieryFall.class, FieryFall.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(IndomitableAncients.class));
		addMana("2WW");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(FieryFall.class));
		// auto-target Indomitable Ancients
		addMana("5R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Indomitable Ancients"));
		assertEquals(5, this.game.actualState.battlefield().objects.get(0).getDamage());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.TypeCycling.TypeCyclingAbility.class));
		addMana("1R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(4, getHand(0).objects.size());
		assertEquals(4, getLibrary(0).objects.size());

		assertEquals(2, this.choices.size());
		assertEquals("Island", this.choices.toArray(new SanitizedIdentified[0])[0].name);
		assertEquals("Island", this.choices.toArray(new SanitizedIdentified[0])[1].name);

		// Choose one of the islands
		respondWith(pullChoice(Island.class));

		assertEquals(5, getHand(0).objects.size());
		assertEquals(3, getLibrary(0).objects.size());
	}

	@Test
	public void typeCyclingTriggers()
	{
		// Make sure triggers looking for Cycling find TypeCycling
		addDeck(StoicChampion.class, StoicChampion.class, StoicChampion.class, FieryFall.class, FieryFall.class, FieryFall.class, FieryFall.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(StoicChampion.class));
		addMana("WW");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.TypeCycling.TypeCyclingAbility.class));
		addMana("1R");
		donePlayingManaAbilities();

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertTrue(this.game.actualState.stack().objects.get(0).getName().equals("Whenever a player cycles a card, Stoic Champion gets +2/+2 until end of turn."));
		assertTrue(this.game.actualState.stack().objects.get(1).getName().startsWith("(1)(R), discard this"));

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());

		// resolve the stoic champion trigger
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertTrue(this.game.actualState.stack().objects.get(0).getName().startsWith("(1)(R), discard this"));

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Stoic Champion"));
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());

	}

	@Test
	public void unearthBasicTest()
	{
		// Make sure unearth works, the creature has haste, and it is rfg'd at
		// eot
		addDeck(VisceraDragger.class, VisceraDragger.class, VisceraDragger.class, Swamp.class, Swamp.class, Swamp.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getLandAction(Swamp.class));

		// Cycle the Viscera Dragger
		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Cycling.CyclingAbility.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED));
		pass();
		pass();

		assertEquals(2, getGraveyard(0).objects.size());
		assertTrue(getGraveyard(0).objects.get(0).getName().equals("Viscera Dragger"));

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Unearth.UnearthAbility.class));
		respondWith(getIntrinsicAbilityAction(SubType.SWAMP));
		donePlayingManaAbilities();
		// Auto-choose the remaining two mana
		pass();
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		assertEquals(0, this.game.actualState.stack().objects.size());

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Viscera Dragger"));
		assertEquals(1, getGraveyard(0).objects.size());

		// Pass Main
		pass();
		pass();

		// Pass Beginning of Combat
		pass();
		pass();

		// Declare Attackers
		respondWith(pullChoice(VisceraDragger.class));
		pass();
		pass();

		// Declare Blockers
		pass();
		pass();
		assertEquals(17, player(1).lifeTotal);

		// Pass Combat Damage
		pass();
		pass();

		// Pass End of Combat
		pass();
		pass();

		// Pass 2nd Main
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(1, getGraveyard(0).objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Viscera Dragger"));
		pass();
		pass();

		// End of Turn
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());
		assertEquals(1, getGraveyard(0).objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Viscera Dragger"));

		assertEquals(1, this.game.actualState.stack().objects.size());

		// Resolve Unearth rfg trigger
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals(1, getGraveyard(0).objects.size());
		assertTrue(this.game.actualState.exileZone().objects.get(0).getName().equals("Viscera Dragger"));

	}

	@Test
	public void unearthReplacementEffectAppliesCorrectly()
	{
		// make sure that killing the creature gets replaced with removing it
		// from the game

		addDeck(VisceraDragger.class, VisceraDragger.class, LightningBolt.class, LightningBolt.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Cycling.CyclingAbility.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.BLACK));
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Unearth.UnearthAbility.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(VisceraDragger.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());

		assertEquals(3, getGraveyard(0).objects.size());
		assertTrue(getGraveyard(0).objects.get(0).getName().equals("Lightning Bolt"));
		assertTrue(getGraveyard(0).objects.get(1).getName().equals("Black Lotus"));
		assertTrue(getGraveyard(0).objects.get(2).getName().equals("Black Lotus"));

		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertTrue(this.game.actualState.exileZone().objects.get(0).getName().equals("Viscera Dragger"));

		assertEquals(0, getLibrary(0).objects.size());

		assertEquals(4, getHand(0).objects.size());

	}

	@Test
	public void unearthReplacementEffectDoesntStopRFG()
	{
		// make sure the replacement effect doesn't mess with turn to mist

		addDeck(VisceraDragger.class, VisceraDragger.class, TurntoMist.class, TurntoMist.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Cycling.CyclingAbility.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.WHITE));
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Unearth.UnearthAbility.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.WHITE));
		pass();
		pass();

		respondWith(getSpellAction(TurntoMist.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(W)"));
		// Auto-choose Viscera Dragger
		donePlayingManaAbilities();
		// Auto-choose #B#W
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());

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

		// End of Turn

		// Stack the turn to mist trigger on top
		respondWith(getTriggeredAbility(EventType.MOVE_OBJECTS), getTriggeredAbility(EventType.PUT_ONTO_BATTLEFIELD));
		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.exileZone().objects.size());

		// Resolve the mist trigger
		pass();
		pass();
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		// Resolve the unearth trigger (should do nothing)
		pass();
		pass();
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

	}

	@Test
	public void witherDamageToPlayer()
	{
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, PunctureBlast.class, PunctureBlast.class, PunctureBlast.class, PunctureBlast.class, PunctureBlast.class, PunctureBlast.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
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

		respondWith(getSpellAction(PunctureBlast.class));
		respondWith(getTarget(player(1)));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(17, player(1).lifeTotal);
	}

	@Test
	public void witherCombatDamage()
	{
		addDeck(Plains.class, Plains.class, Plains.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, IndomitableAncients.class, IndomitableAncients.class, IndomitableAncients.class);
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BoggartRamGang.class, BoggartRamGang.class, BoggartRamGang.class, BoggartRamGang.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Plains.class));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(IndomitableAncients.class));
		respondWith(getIntrinsicAbilityAction(SubType.PLAINS));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
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

		respondWith(getSpellAction(BoggartRamGang.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(R)(R)(R)"));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		pass();
		pass();

		Identified blockMe = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BoggartRamGang.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(R)(R)(R)"));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BoggartRamGang.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(R)(R)(R)"));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BoggartRamGang.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(R)(R)(R)"));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
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
		respondWith(pullChoice(BoggartRamGang.class), pullChoice(BoggartRamGang.class), pullChoice(BoggartRamGang.class), pullChoice(BoggartRamGang.class));
		pass();
		pass();

		// Declare Blockers
		respondWith(pullChoice(IndomitableAncients.class));
		respondWith(getChoice(blockMe));

		assertEquals(20, player(0).lifeTotal);
		assertEquals("Indomitable Ancients", this.game.actualState.battlefield().objects.get(4).getName());
		assertEquals(0, this.game.actualState.battlefield().objects.get(4).counters.size());
		assertEquals(10, this.game.actualState.battlefield().objects.get(4).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(4).getDamage());
		assertEquals(blockMe, this.game.actualState.battlefield().objects.get(3));
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).counters.size());
		assertEquals(3, this.game.actualState.battlefield().objects.get(3).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).getDamage());

		pass();
		pass();

		// End of Combat
		assertEquals(11, player(0).lifeTotal);
		assertEquals("Indomitable Ancients", this.game.actualState.battlefield().objects.get(4).getName());
		assertEquals(3, this.game.actualState.battlefield().objects.get(4).counters.size());
		assertEquals(7, this.game.actualState.battlefield().objects.get(4).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(4).getDamage());
		assertEquals(blockMe, this.game.actualState.battlefield().objects.get(3));
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).counters.size());
		assertEquals(3, this.game.actualState.battlefield().objects.get(3).getToughness());
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getDamage());
	}

	@Test
	public void witherDamageToCreature()
	{
		addDeck(Plains.class, Plains.class, Plains.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, IndomitableAncients.class, IndomitableAncients.class, IndomitableAncients.class);
		addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, PunctureBlast.class, PunctureBlast.class, PunctureBlast.class, PunctureBlast.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Plains.class));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(IndomitableAncients.class));
		respondWith(getIntrinsicAbilityAction(SubType.PLAINS));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
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

		assertEquals(10, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(PunctureBlast.class));
		respondWith(getTarget(IndomitableAncients.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(7, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(PunctureBlast.class));
		respondWith(getTarget(IndomitableAncients.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());

	}
}
