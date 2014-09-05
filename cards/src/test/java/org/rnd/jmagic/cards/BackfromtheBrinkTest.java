package org.rnd.jmagic.cards;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.engine.Phase;
import org.rnd.jmagic.testing.JUnitTest;

@RunWith(JUnit4.class)
public class BackfromtheBrinkTest extends JUnitTest
{
	@Test
	public void backFromTheBrink()
	{
		this.addDeck(BackfromtheBrink.class, RagingGoblin.class, OnewithNothing.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(BackfromtheBrink.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(new Open());

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
}
