package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class LegendaryTest extends JUnitTest
{
	@Test
	public void legendRule()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(ArcanistheOmnipotent.class);
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Arcanis the Omnipotent"));

		goToPhase(Phase.PhaseType.ENDING);

		// player 1's turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(ArcanistheOmnipotent.class);

		// in M14, both Arcanis live since they are controlled by different
		// players:
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		goToPhase(Phase.PhaseType.ENDING);

		// cast one more Arcanis
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(ArcanistheOmnipotent.class);

		// in M14, you choose which one to keep:
		respondWith(getChoice(this.game.actualState.battlefield().objects.get(0)));
		assertEquals(2, this.game.actualState.battlefield().objects.size());
	}
}
