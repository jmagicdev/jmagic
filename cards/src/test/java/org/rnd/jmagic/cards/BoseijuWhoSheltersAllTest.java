package org.rnd.jmagic.cards;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rnd.jmagic.engine.Answer;
import org.rnd.jmagic.engine.GameTypes;
import org.rnd.jmagic.engine.Phase;
import org.rnd.jmagic.testing.JUnitTest;

@RunWith(JUnit4.class)
public class BoseijuWhoSheltersAllTest extends JUnitTest {
	@Test
	public void boseiju()
	{
		this.addDeck(BoseijuWhoSheltersAll.class, Counterspell.class, StoneRain.class, Twiddle.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

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

}
