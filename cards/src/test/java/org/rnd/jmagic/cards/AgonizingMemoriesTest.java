package org.rnd.jmagic.cards;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rnd.jmagic.engine.Phase;
import org.rnd.jmagic.engine.Zone;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.JUnitTest;

@RunWith(JUnit4.class)
public class AgonizingMemoriesTest extends JUnitTest
{
	@Test
	public void agonizingMemories()
	{
		this.addDeck(AgonizingMemories.class, AgonizingMemories.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Forest.class, Swamp.class, Swamp.class, Island.class, Mountain.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(AgonizingMemories.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("2BB");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Choose the two cards
		this.respondWith(this.pullChoice(Swamp.class), this.pullChoice(Forest.class));

		// Order them
		this.respondWith(this.pullChoice(Forest.class), this.pullChoice(Swamp.class));

		{
			Zone library = getLibrary(1);
			assertEquals(5, this.getHand(1).objects.size());
			assertEquals(2, library.objects.size());
			assertEquals(Swamp.class, library.objects.get(0).getClass());
			assertEquals(Forest.class, library.objects.get(1).getClass());
		}

		this.respondWith(this.getSpellAction(AgonizingMemories.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("2BB");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Choose the two cards
		this.respondWith(this.pullChoice(Plains.class), this.pullChoice(Plains.class));

		// Order them
		this.respondArbitrarily();

		{
			Zone library = getLibrary(1);
			assertEquals(3, this.getHand(1).objects.size());
			assertEquals(4, library.objects.size());
			assertEquals(Plains.class, library.objects.get(0).getClass());
			assertEquals(Plains.class, library.objects.get(1).getClass());
			assertEquals(Swamp.class, library.objects.get(2).getClass());
			assertEquals(Forest.class, library.objects.get(3).getClass());
		}
	}
}
