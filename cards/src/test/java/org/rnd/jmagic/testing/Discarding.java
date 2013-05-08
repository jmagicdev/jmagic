package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class Discarding extends JUnitTest
{
	@Test
	public void discardAToken()
	{
		this.addDeck(Sprout.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Recoil.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(Sprout.class, "G");
		pass();

		// Can't specify Saproling as a target because it auto-targets
		castAndResolveSpell(Recoil.class, "1UB");
		// Make sure the token isn't in the set of choices
		assertEquals(6, this.choices.size());
	}
}
