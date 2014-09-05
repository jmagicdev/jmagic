package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class ThrullSurgeonTest extends JUnitTest
{
	@Test
	public void thrullSurgeon()
	{
		this.addDeck(ThrullSurgeon.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.respondWith(this.getSpellAction(ThrullSurgeon.class));
		this.addMana("1B");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(ThrullSurgeon.ThrullSurgeonAbility.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("1B");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(7, this.choices.size());
		this.respondWith(this.pullChoice(RagingGoblin.class));

		assertEquals(1, this.getGraveyard(1).objects.size());
	}
}