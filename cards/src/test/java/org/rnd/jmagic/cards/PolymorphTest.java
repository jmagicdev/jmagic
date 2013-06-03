package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class PolymorphTest extends JUnitTest
{
	@Test
	public void polymorph()
	{
		this.addDeck(
		// cards still in library
		Plains.class, Plains.class, Vigor.class, Plains.class, Plains.class,
		// opening seven
		RagingGoblin.class, Polymorph.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.castAndResolveSpell(RagingGoblin.class, "R");
		this.castAndResolveSpell(Polymorph.class, "3U"); // auto-target
															// goblin

		assertEquals("Vigor", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(4, this.getLibrary(0).objects.size());
		assertEquals("Plains", this.getLibrary(0).objects.get(0).getName());
		assertEquals("Plains", this.getLibrary(0).objects.get(1).getName());
		assertEquals("Plains", this.getLibrary(0).objects.get(2).getName());
		assertEquals("Plains", this.getLibrary(0).objects.get(3).getName());
	}
}