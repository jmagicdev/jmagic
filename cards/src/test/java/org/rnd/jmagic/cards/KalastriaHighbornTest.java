package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class KalastriaHighbornTest extends JUnitTest
{
	@Test
	public void kalastriaHighborn()
	{
		this.addDeck(KalastriaHighborn.class, Terminate.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.castAndResolveSpell(KalastriaHighborn.class, "BB");
		this.castAndResolveSpell(Terminate.class, "BR");

		this.respondWith(this.getTarget(this.player(1)));
		this.pass();
		this.pass();

		this.addMana("B");
		this.donePlayingManaAbilities();
		this.respondWith(Answer.YES);

		assertEquals(22, this.player(0).lifeTotal);
		assertEquals(18, this.player(1).lifeTotal);
	}
}