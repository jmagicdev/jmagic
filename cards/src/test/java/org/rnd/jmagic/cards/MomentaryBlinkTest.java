package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class MomentaryBlinkTest extends JUnitTest
{
	@Test
	public void momentaryBlink()
	{
		this.addDeck(MomentaryBlink.class, Sprout.class, GrizzlyBears.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		this.castAndResolveSpell(Sprout.class, "G");

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		this.castAndResolveSpell(MomentaryBlink.class, "1W");

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		this.castAndResolveSpell(GrizzlyBears.class, "1G");

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		this.castAndResolveSpell(MomentaryBlink.class, "3U");

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.exileZone().objects.size());
		assertEquals("Momentary Blink", this.game.actualState.exileZone().objects.get(0).getName());
	}
}