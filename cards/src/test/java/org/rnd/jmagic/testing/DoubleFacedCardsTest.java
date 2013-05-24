package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class DoubleFacedCardsTest extends JUnitTest
{
	@Test
	public void everything()
	{
		this.addDeck(CloisteredYouth.class, Moonmist.class, Clone.class, Ixidron.class, Clone.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(CloisteredYouth.class);
		castAndResolveSpell(Clone.class);
		respondWith(Answer.YES);
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals(Clone.class, this.game.actualState.battlefield().objects.get(0).getClass());
		castAndResolveSpell(Moonmist.class);

		// first clone doesn't transform
		assertEquals("Cloistered Youth", this.game.actualState.battlefield().objects.get(0).getName());
		assertNull(this.game.actualState.battlefield().objects.get(0).getBackFace());

		castAndResolveSpell(Clone.class);
		respondWith(Answer.YES);
		respondWith(pullChoice(CloisteredYouth.class));

		// second clone doesn't have a back face but still copies the back face
		assertEquals("Unholy Fiend", this.game.actualState.battlefield().objects.get(0).getName());
		assertNull(this.game.actualState.battlefield().objects.get(0).getBackFace());

		castAndResolveSpell(Ixidron.class);

		// face up transformed original
		// 2 face down copies
		// Ixy is 2/2
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
	}
}
