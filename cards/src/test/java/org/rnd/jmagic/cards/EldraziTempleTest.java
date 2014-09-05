package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;
import org.rnd.jmagic.gameTypes.*;

public class EldraziTempleTest extends JUnitTest
{
	@Test
	public void eldraziTemple()
	{
		this.addDeck(EldraziTemple.class, KozilekButcherofTruth.class, HowlingMine.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getLandAction(EldraziTemple.class));
		this.respondWith(this.getAbilityAction(EldraziTemple.EldraziTempleAbility1.class));

		this.respondWith(this.getSpellAction(HowlingMine.class));
		this.donePlayingManaAbilities();
		// should fail

		this.respondWith(this.getSpellAction(KozilekButcherofTruth.class));
		this.addMana("8");
		this.donePlayingManaAbilities();

		// kozilek and his trigger:
		assertEquals(2, this.game.physicalState.stack().objects.size());
	}
}