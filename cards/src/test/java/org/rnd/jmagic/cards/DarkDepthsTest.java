package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;
import org.rnd.jmagic.gameTypes.*;

public class DarkDepthsTest extends JUnitTest
{
	@Test
	public void darkDepths()
	{
		this.addDeck(UrborgTombofYawgmoth.class, BloodMoon.class, Plains.class, Plains.class, Plains.class, DarkDepths.class, VampireHexmage.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Stacked());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(VampireHexmage.class, "BB");
		this.respondWith(this.getLandAction(DarkDepths.class));

		this.respondWith(this.getAbilityAction(VampireHexmage.TriggerDarkDepths.class));
		this.respondWith(this.getTarget(DarkDepths.class));
		// resolve hexmage ability
		this.pass();
		this.pass();

		// resolve depths ability
		this.pass();
		this.pass();
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Marit Lage", this.game.actualState.battlefield().objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getSuperTypes().contains(SuperType.LEGENDARY));
	}
}