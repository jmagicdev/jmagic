package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class MeliraSylvokOutcastTest extends JUnitTest
{
	@Test
	public void meliraStopsEntersBattlefieldWithCounters()
	{
		this.addDeck(MeliraSylvokOutcast.class, CarnifexDemon.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(MeliraSylvokOutcast.class);
		castAndResolveSpell(CarnifexDemon.class);

		GameObject ghost = this.game.actualState.battlefield().objects.get(0);
		assertEquals(0, ghost.counters.size());
	}
}