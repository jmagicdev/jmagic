package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;
import org.rnd.jmagic.gameTypes.*;

public class DiligentFarmhandTest extends JUnitTest
{
	@Test
	public void diligentFarmhand()
	{
		this.addDeck(DiligentFarmhand.class, DiligentFarmhand.class, DiligentFarmhand.class, MuscleBurst.class, MuscleBurst.class, MuscleBurst.class, TomeScour.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, RuneclawBear.class, RuneclawBear.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Stacked());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(TomeScour.class, "U", this.player(0));

		this.castAndResolveSpell(RuneclawBear.class, "1G");

		// Muscle Burst will auto-target the bear.
		this.castAndResolveSpell(MuscleBurst.class, "1G");

		// Bears base power + 3 + Muscle Bursts in yard + Diligent Farmhands
		// in
		// yard
		assertEquals(2 + 3 + 2 + 3, this.game.actualState.battlefield().objects.get(0).getPower());
	}
}