package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class DiscardDestinationReplacement extends JUnitTest
{
	@Test
	public void obstinateBaloth()
	{
		this.addDeck(ObstinateBaloth.class, OnewithNothing.class, MindShatter.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(ObstinateBaloth.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(MindShatter.class));
		respondWith(7);
		respondWith(getTarget(player(1)));
		addMana("7BB");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();
		assertEquals(24, player(1).lifeTotal);

		castAndResolveSpell(OnewithNothing.class, "B");
		assertEquals(1, this.game.actualState.battlefield().objects.size());
	}
}