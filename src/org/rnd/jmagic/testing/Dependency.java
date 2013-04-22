package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class Dependency extends JUnitTest
{
	@Test
	public void abilitiesKeepApplyingWithoutASource()
	{
		this.addDeck(
		// Library
		Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class,

		// Hand
		Plains.class, Plains.class, TurntoFrog.class, TurntoFrog.class, ZulaportEnforcer.class, PossessedNomad.class, Traumatize.class);

		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(ZulaportEnforcer.class);

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.LevelUp.LevelUpAbility.class));
		addMana("4");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.LevelUp.LevelUpAbility.class));
		addMana("4");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.LevelUp.LevelUpAbility.class));
		addMana("4");
		donePlayingManaAbilities();
		pass();
		pass();

		// Targets the only creature automatically
		castAndResolveSpell(TurntoFrog.class);

		GameObject zulaport = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Zulaport Enforcer", zulaport.getName());
		assertEquals(1, zulaport.getPower());
		assertEquals(1, zulaport.getToughness());
		assertEquals(0, zulaport.getAbilityIDsInOrder().size());
		assertEquals(1, zulaport.getColors().size());
		assertEquals(Color.BLUE, zulaport.getColors().iterator().next());
		assertEquals(1, zulaport.getSubTypes().size());
		assertEquals(SubType.FROG, zulaport.getSubTypes().iterator().next());
		assertEquals(1, zulaport.getTypes().size());
		assertEquals(Type.CREATURE, zulaport.getTypes().iterator().next());

		castAndResolveSpell(PossessedNomad.class);

		castAndResolveSpell(Traumatize.class, "3UU", player(0));

		castAndResolveSpell(TurntoFrog.class, PossessedNomad.class);

		GameObject nomad = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Possessed Nomad", nomad.getName());
		assertEquals(2, nomad.getPower());
		assertEquals(2, nomad.getToughness());
		assertEquals(0, nomad.getAbilityIDsInOrder().size());
		assertEquals(1, nomad.getColors().size());
		assertEquals(Color.BLUE, nomad.getColors().iterator().next());
		assertEquals(1, nomad.getSubTypes().size());
		assertEquals(SubType.FROG, nomad.getSubTypes().iterator().next());
		assertEquals(1, nomad.getTypes().size());
		assertEquals(Type.CREATURE, nomad.getTypes().iterator().next());
	}

	@Test
	public void bludgeonBrawlMarchOfTheMachines()
	{
		// using open the vaults instead of a second march to reduce the number
		// of effects to debug
		this.addDeck(TimeVault.class, BludgeonBrawl.class, MarchoftheMachines.class, Naturalize.class, Clone.class, OpentheVaults.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(TimeVault.class);
		castAndResolveSpell(BludgeonBrawl.class);
		castAndResolveSpell(MarchoftheMachines.class);

		// bludgeon brawl is dependent on march of the machines, since if march
		// of the machines applies it changes whether bludgeon brawl applies.
		// with both out, time vault should be an artifact creature but not an
		// equipment
		assertEquals("Time Vault", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getTypes().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getSubTypes().size());

		castAndResolveSpell(Clone.class);
		respondWith(Answer.YES);

		// same effects are applying to the copy of time vault
		assertEquals("Time Vault", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getTypes().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getSubTypes().size());

		castAndResolveSpell(Naturalize.class, MarchoftheMachines.class);

		// with march gone, time vault is an artifact - equipment
		assertEquals("Time Vault", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getTypes().size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getSubTypes().size());

		castAndResolveSpell(OpentheVaults.class);

		// copy of time vault should be an artifact creature (non-equipment)
		// again
		assertEquals("Time Vault", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getTypes().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getSubTypes().size());
	}

	@Test
	public void confiscateBall()
	{
		// the "confiscate ball" -- this forms a dependency loop
		this.addDeck(SimicGuildmage.class, Confiscate.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Confiscate.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getSpellAction(SimicGuildmage.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(U)(U)"));
		addMana("UU");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Confiscate.class));
		// auto-target guildmage
		addMana("4UU");
		donePlayingManaAbilities();
		pass();
		pass();

		Identified confiscateA = this.game.physicalState.battlefield().objects.get(0);

		// player 1's turn:
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Confiscate.class));
		respondWith(getTarget(Confiscate.class));
		addMana("4UU");
		donePlayingManaAbilities();
		pass();
		pass();

		Identified confiscateB = this.game.physicalState.battlefield().objects.get(0);

		// player 1 controls the first confiscate, which means he controls the
		// guildmage:
		assertEquals("Confiscate", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Confiscate", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(player(1).ID, this.game.actualState.battlefield().objects.get(1).controllerID);
		assertEquals("Simic Guildmage", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(player(1).ID, this.game.actualState.battlefield().objects.get(2).controllerID);

		// Confiscate A is enchanting the guildmage; move it to Confiscate B:
		respondWith(getAbilityAction(SimicGuildmage.MoveAura.class));
		respondWith(getTarget(confiscateA));
		addMana("1U");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(pullChoice(Confiscate.class));

		// Each Confiscate now has an effect that's dependent on the other,
		// which means they'll apply in timestamp order. Since Confiscate A was
		// just moved, it has a later timestamp than Confiscate B. This means
		// that Confiscate B applies first, giving Player 1 control of
		// Confiscate A, then Confiscate A applies, giving player 1 control of
		// Confiscate B (which he already had):
		assertEquals(confiscateB, this.game.actualState.battlefield().objects.get(0));
		assertEquals(player(1).ID, this.game.actualState.battlefield().objects.get(0).controllerID);
		assertEquals(confiscateA, this.game.actualState.battlefield().objects.get(1));
		assertEquals(player(1).ID, this.game.actualState.battlefield().objects.get(1).controllerID);

		// Simic Guildmage no longer has a change of control effect applying to
		// it, so it should be back on Player 0's side:
		assertEquals("Simic Guildmage", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(player(0).ID, this.game.actualState.battlefield().objects.get(2).controllerID);
	}

	@Test
	public void removingAbilities()
	{
		this.addDeck(MeliraSylvokOutcast.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Phyresis.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(MeliraSylvokOutcast.class);
		goToPhase(Phase.PhaseType.ENDING);

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(RagingGoblin.class);
		castAndResolveSpell(Phyresis.class, RagingGoblin.class);

		// timestamps have infect being removed before it's added so the goblin
		// would have infect
		// since phyresis gives melira something to do, melira is dependent on
		// phyresis
		// the goblin should never have infect since phyresis should always
		// apply first
		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(1).getName());
		// (just haste)
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getKeywordAbilities().size());
	}

	@Test
	public void sanityCheck()
	{
		this.addDeck(UrborgTombofYawgmoth.class, UrborgTombofYawgmoth.class, UrborgTombofYawgmoth.class, UrborgTombofYawgmoth.class, UrborgTombofYawgmoth.class, UrborgTombofYawgmoth.class, UrborgTombofYawgmoth.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(UrborgTombofYawgmoth.class));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Urborg, Tomb of Yawgmoth"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getSubTypes().contains(org.rnd.jmagic.engine.SubType.SWAMP));

	}

	@Ignore
	@Test
	public void stressTest()
	{
		this.addDeck(Swamp.class, SeasClaim.class, LushGrowth.class, AnHavvaTownship.class, ConvincingMirage.class, BloodMoon.class, AzusaLostbutSeeking.class);
		this.addDeck(Swamp.class, SeasClaim.class, LushGrowth.class, AnHavvaTownship.class, ConvincingMirage.class, BloodMoon.class, AzusaLostbutSeeking.class, DoomBlade.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		for(int repeat = 0; repeat < 2; repeat++)
		{
			goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

			castAndResolveSpell(AzusaLostbutSeeking.class, "2G");

			respondWith(getLandAction(Swamp.class));
			respondWith(getLandAction(AnHavvaTownship.class));

			castAndResolveSpell(SeasClaim.class, "U", Swamp.class);

			castAndResolveSpell(LushGrowth.class, "G", Swamp.class);

			castAndResolveSpell(ConvincingMirage.class, "1U", AnHavvaTownship.class);
			respondWith(SubType.SWAMP);

			castAndResolveSpell(BloodMoon.class, "2R");

			if(repeat == 0)
			{
				goToStep(Step.StepType.DRAW);

				// auto-target
				castAndResolveSpell(DoomBlade.class, "1B");
			}
			else
				goToPhase(Phase.PhaseType.COMBAT);
		}
	}

	@Test
	public void timestampAgainstDependencies()
	{
		// test where dependency order is opposite timestamp order
		this.addDeck(AzusaLostbutSeeking.class, UrborgTombofYawgmoth.class, BloodMoon.class, AdarkarWastes.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(AzusaLostbutSeeking.class));
		addMana("2G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getLandAction(UrborgTombofYawgmoth.class));

		respondWith(getSpellAction(BloodMoon.class));
		addMana("2R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getLandAction(AdarkarWastes.class));

		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Urborg, Tomb of Yawgmoth"));
		assertTrue(!this.game.actualState.battlefield().objects.get(2).getSubTypes().contains(org.rnd.jmagic.engine.SubType.SWAMP));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getSubTypes().contains(org.rnd.jmagic.engine.SubType.MOUNTAIN));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Adarkar Wastes"));
		assertTrue(!this.game.actualState.battlefield().objects.get(0).getSubTypes().contains(org.rnd.jmagic.engine.SubType.SWAMP));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getSubTypes().contains(org.rnd.jmagic.engine.SubType.MOUNTAIN));

	}

	@Test
	public void timestampWithDependencies()
	{
		// test where dependency order (blood moon first) is the same as
		// timestamp order
		this.addDeck(AzusaLostbutSeeking.class, UrborgTombofYawgmoth.class, BloodMoon.class, AdarkarWastes.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(AzusaLostbutSeeking.class));
		addMana("2G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(BloodMoon.class));
		addMana("2R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getLandAction(UrborgTombofYawgmoth.class));
		respondWith(getLandAction(AdarkarWastes.class));

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Urborg, Tomb of Yawgmoth"));
		assertTrue(!this.game.actualState.battlefield().objects.get(1).getSubTypes().contains(org.rnd.jmagic.engine.SubType.SWAMP));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getSubTypes().contains(org.rnd.jmagic.engine.SubType.MOUNTAIN));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Adarkar Wastes"));
		assertTrue(!this.game.actualState.battlefield().objects.get(0).getSubTypes().contains(org.rnd.jmagic.engine.SubType.SWAMP));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getSubTypes().contains(org.rnd.jmagic.engine.SubType.MOUNTAIN));

	}

	@Test
	public void typelessPermanent()
	{
		// LOOK MA, NO TYPES
		this.addDeck(NeurokTransmuter.class, MarchoftheMachines.class, PhyrexianVault.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(MarchoftheMachines.class));
		addMana("3U");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(NeurokTransmuter.class));
		addMana("2U");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(PhyrexianVault.class));
		addMana("3");
		donePlayingManaAbilities();
		pass();
		pass();
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());

		respondWith(getAbilityAction(NeurokTransmuter.MakeBlue.class));
		// auto-target vault
		addMana("U");
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getTypes().isEmpty());
	}
}
