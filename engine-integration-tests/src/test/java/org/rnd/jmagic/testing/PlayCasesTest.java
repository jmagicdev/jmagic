package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.abilities.keywords.*;

public class PlayCasesTest extends JUnitTest
{
	@Test
	public void overwhelmingStampede()
	{
		this.addDeck(EchoMage.class, PelakkaWurm.class, SkithiryxtheBlightDragon.class, OnduGiant.class, OverwhelmingStampede.class, BarbedBattlegear.class, BurstofSpeed.class);
		this.addDeck(NemesisTrap.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(EchoMage.class);
		castAndResolveSpell(PelakkaWurm.class);

		// Resolve PelakkaWurm's ability
		pass();
		pass();

		castAndResolveSpell(SkithiryxtheBlightDragon.class);
		castAndResolveSpell(BarbedBattlegear.class);
		castAndResolveSpell(OnduGiant.class);

		// Resolve OnduGiant's ability
		pass();
		pass();
		// Auto-respond with NO

		// Equip the Wurm
		respondWith(getAbilityAction(Equip.EquipAbility.class));
		respondWith(getTarget(PelakkaWurm.class));
		addMana("2");
		donePlayingManaAbilities();
		pass();
		pass();

		// Level the EchoMage
		for(int i = 0; i < 4; i++)
		{
			respondWith(getAbilityAction(LevelUp.LevelUpAbility.class));
			addMana("1U");
			donePlayingManaAbilities();
			pass();
			pass();
		}

		castAndResolveSpell(BurstofSpeed.class);

		respondWith(getSpellAction(OverwhelmingStampede.class));
		addMana("3GG");
		donePlayingManaAbilities();

		respondWith(getAbilityAction(EchoMage.EchoMageAbility6.class));
		// Auto-target
		addMana("UU");
		donePlayingManaAbilities();

		assertEquals(2, this.game.actualState.stack().objects.size());

		// Resolve EchoMage's ability (create 2 copies of OverwhelmingStampede)
		pass();
		pass();

		assertEquals(3, this.game.actualState.stack().objects.size());

		assertEquals("Pelakka Wurm", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals(11, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals("Skithiryx, the Blight Dragon", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(4, this.game.actualState.battlefield().objects.get(2).getPower());

		// Resolve first Stampede
		pass();
		pass();

		assertEquals("Pelakka Wurm", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals(22, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals("Skithiryx, the Blight Dragon", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(15, this.game.actualState.battlefield().objects.get(2).getPower());

		// Resolve second Stampede
		pass();
		pass();

		assertEquals("Pelakka Wurm", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals(44, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals("Skithiryx, the Blight Dragon", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(37, this.game.actualState.battlefield().objects.get(2).getPower());

		// Resolve third Stampede
		pass();
		pass();

		assertEquals("Pelakka Wurm", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals(88, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals("Skithiryx, the Blight Dragon", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(81, this.game.actualState.battlefield().objects.get(2).getPower());

		goToStep(Step.StepType.DECLARE_ATTACKERS);

		respondWith(pullChoice(PelakkaWurm.class), pullChoice(SkithiryxtheBlightDragon.class), pullChoice(OnduGiant.class));

		pass();

		castAndResolveSpell(NemesisTrap.class, PelakkaWurm.class);

		// Resolve Pelakka Wurm's ability
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_BLOCKERS);

		respondWith(pullChoice(Token.class));
		respondWith(pullChoice(OnduGiant.class));

		goToStep(Step.StepType.COMBAT_DAMAGE);

		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		// The Pelakka Wurm
		divisions.put(this.game.actualState.battlefield().objects.get(0).ID, 7);
		// Defending Player
		divisions.put(player(1).ID, 72);
		divide(divisions);

		assertEquals(player(0), this.winner);
		assertEquals(20 + 7 - 72, player(1).lifeTotal);
		assertEquals(81, player(1).countPoisonCounters());
	}
}
