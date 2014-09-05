package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class UnderworldDreamsTest extends JUnitTest
{
	@Test
	public void underworldDreams()
	{
		this.addDeck(UnderworldDreams.class, Inspiration.class, Counterspell.class, Counterspell.class, Counterspell.class, Counterspell.class, Counterspell.class);
		this.addDeck(PactofNegation.class, PactofNegation.class, Sprout.class, Sprout.class, ElvishWarrior.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(UnderworldDreams.class);
		castAndResolveSpell(Inspiration.class, "3U", player(1));

		// underworld dreams triggers twice, order the triggers
		respondArbitrarily();

		// resolve one trigger
		pass();
		pass();
		assertEquals(19, player(1).lifeTotal);

		// resolve the other one
		pass();
		pass();
		assertEquals(18, player(1).lifeTotal);
	}
}