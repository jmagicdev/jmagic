package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class LilianaVessTest extends JUnitTest
{
	@Test
	public void lilianaVessTutor()
	{
		this.addDeck(
		// 10 cards on top, one unique
		Island.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class,
		// opening seven
		LilianaVess.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(GameTypes.STACKED);
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
}