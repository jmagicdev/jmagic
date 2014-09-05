package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class TerramorphicExpanseTest extends JUnitTest
{
	@Test
	public void terramorphicExpanseFindNothing()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, TerramorphicExpanse.class, TerramorphicExpanse.class, TerramorphicExpanse.class, TerramorphicExpanse.class, TerramorphicExpanse.class, TerramorphicExpanse.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, TerramorphicExpanse.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getLandAction(TerramorphicExpanse.class));

		this.respondWith(this.getAbilityAction(TerramorphicExpanse.Terraform.class));
		this.pass();
		this.pass();

		// choose to find nothing
		this.respondWith();
		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}
}