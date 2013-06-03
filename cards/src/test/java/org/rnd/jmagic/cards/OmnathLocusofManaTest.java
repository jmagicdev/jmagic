package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class OmnathLocusofManaTest extends JUnitTest
{
	@Test
	public void omnathLocusOfMana()
	{
		this.addDeck(OmnathLocusofMana.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.castAndResolveSpell(OmnathLocusofMana.class, "2G");

		this.addMana("GGWW");
		this.pass();
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		this.addMana("GGWW");

		this.goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		assertEquals(2, this.player(0).pool.converted());
		assertEquals(0, this.player(1).pool.converted());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());

		this.goToStep(Step.StepType.UPKEEP);
		assertEquals(2, this.player(0).pool.converted());
		assertEquals(0, this.player(1).pool.converted());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
	}
}