package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class FlipCardsTest extends JUnitTest
{
	@Test
	public void basicTestPlusClone()
	{
		this.addDeck(BudokaPupil.class, LavaSpike.class, LavaSpike.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Clone.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(BudokaPupil.class, "1GG");

		castAndResolveSpell(LavaSpike.class, "R", player(1));

		respondWith(Answer.YES);
		pass();
		pass();

		castAndResolveSpell(LavaSpike.class, "R", player(1));

		respondWith(Answer.YES);
		pass();
		pass();

		goToPhase(Phase.PhaseType.ENDING);

		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();
		respondWith(Answer.YES);

		GameObject ichiga = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Ichiga, Who Topples Oaks", ichiga.getName());
		assertNotNull(ichiga.getManaCost());
		assertEquals(3, ichiga.getManaCost().converted());

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// cast clone
		castAndResolveSpell(Clone.class, "3U");
		// choose to copy a creature
		respondWith(Answer.YES);
		// auto-choose Ichiga

		// Clone will become Ichiga, the legend rule will not kick in since they are controlled by different players (M14 update).
		ichiga = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Ichiga, Who Topples Oaks", ichiga.getName());
	}

	@Test
	public void comprehensiveExample()
	{
		this.addDeck(BudokaPupil.class, CunningBandit.class, RuneclawBear.class, LavaSpike.class, LavaSpike.class, TomeScour.class, Plains.class, Plains.class, Plains.class, DimirDoppelganger.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(DimirDoppelganger.class, "1UB");

		castAndResolveSpell(TomeScour.class, "U", player(0));

		respondWith(getAbilityAction(DimirDoppelganger.DimirDoppelgangerAbility0.class));
		respondWith(getTarget(BudokaPupil.class));
		addMana("1UB");
		donePlayingManaAbilities();
		pass();
		pass();

		for(int i = 0; i < 2; ++i)
		{
			castAndResolveSpell(LavaSpike.class, "R", player(1));
			respondWith(Answer.YES);
			pass();
			pass();
		}

		goToPhase(Phase.PhaseType.ENDING);

		pass();
		pass();
		respondWith(Answer.YES);

		assertTrue(this.game.actualState.battlefield().objects.get(0).isFlipped());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals("Ichiga, Who Topples Oaks", this.game.actualState.battlefield().objects.get(0).getName());

		respondWith(getAbilityAction(DimirDoppelganger.DimirDoppelgangerAbility0.class));
		respondWith(getTarget(RuneclawBear.class));
		addMana("1UB");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals("Runeclaw Bear", this.game.actualState.battlefield().objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).isFlipped());

		respondWith(getAbilityAction(DimirDoppelganger.DimirDoppelgangerAbility0.class));
		// auto target cunning bandit
		addMana("1UB");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals("Azamuki, Treachery Incarnate", this.game.actualState.battlefield().objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).isFlipped());
	}

	@Test(expected = AssertionError.class)
	public void namingFlippedCardsForDecks()
	{
		this.addDeck(IchigaWhoTopplesOaks.class, IchigaWhoTopplesOaks.class, IchigaWhoTopplesOaks.class, IchigaWhoTopplesOaks.class, IchigaWhoTopplesOaks.class, IchigaWhoTopplesOaks.class, IchigaWhoTopplesOaks.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		// We'll get an assertion error when player 0's deck is validated.
		startGame(GameTypes.OPEN);
	}
}
