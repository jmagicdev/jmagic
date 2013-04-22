package org.rnd.jmagic.testing;

import org.junit.*;
//import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class GrantedAbilities extends JUnitTest
{
	@Test
	public void bearUmbra()
	{
		this.addDeck(BearUmbra.class, RagingGoblin.class, LightningBolt.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(RagingGoblin.class, "R");
		castAndResolveSpell(BearUmbra.class, "2GG"); // auto-target
		castAndResolveSpell(LightningBolt.class, "R", RagingGoblin.class);

		// no asserts here; this is a test to make sure that the umbra going
		// away doesn't crash the game due to the triggered ability still
		// existing in the game's granted abilities map.
	}
}