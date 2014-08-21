package org.rnd.jmagic.cards;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rnd.jmagic.engine.GameTypes;
import org.rnd.jmagic.engine.Phase;
import org.rnd.jmagic.testing.JUnitTest;

@RunWith(JUnit4.class)
public class AvenRiftwatcherTest extends JUnitTest
{
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

		this.startGame(GameTypes.OPEN);

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
}
