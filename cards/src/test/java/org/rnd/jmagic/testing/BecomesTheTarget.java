package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class BecomesTheTarget extends JUnitTest
{
	@Test
	public void iceCage()
	{
		this.addDeck(IceCage.class, RagingGoblin.class, MoggFanatic.class, GiantGrowth.class, GiantGrowth.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(RagingGoblin.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(MoggFanatic.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(IceCage.class));
		respondWith(getTarget(RagingGoblin.class));
		addMana("1U");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(GiantGrowth.class));
		respondWith(getTarget(MoggFanatic.class));
		addMana("G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(GiantGrowth.class));
		respondWith(getTarget(RagingGoblin.class));
		addMana("G");
		donePlayingManaAbilities();

		// resolve ice cage trigger:
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		// The goblin and the Ice Cage
		assertEquals(2, getGraveyard(0).objects.size());

		pass();
		pass();

		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
	}
}
