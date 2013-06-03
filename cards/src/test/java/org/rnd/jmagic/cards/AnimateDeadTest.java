package org.rnd.jmagic.cards;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rnd.jmagic.engine.GameTypes;
import org.rnd.jmagic.engine.Phase;
import org.rnd.jmagic.testing.JUnitTest;

@RunWith(JUnit4.class)
public class AnimateDeadTest extends JUnitTest {
	@Test
	public void animateDead() {
		this.addDeck(AnimateDead.class, SleeperAgent.class, Disenchant.class,
				CabalTherapy.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class,
				Plains.class, Plains.class, Plains.class);

		this.startGame(GameTypes.OPEN);

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
}
