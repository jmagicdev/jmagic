package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class PutIntoPlayBlocking extends JUnitTest
{
	@Test
	public void flashFoliage()
	{
		this.addDeck(Plains.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, FlashFoliage.class, FlashFoliage.class, FlashFoliage.class, FlashFoliage.class);
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		startGame(GameType.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		// 3 black lotuses
		assertEquals(3, this.choices.size());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		// 2 black lotuses, 1 sac
		assertEquals(3, this.choices.size());

		// pass main
		pass();
		pass();

		// pass beginning of combat
		pass();
		pass();

		// pass declare attackers (1 black lotus sac ability
		assertEquals(1, this.choices.size());
		pass();
		pass();

		// pass end of combat (all 4 flash foliage aren't restricted anymore)
		assertEquals(5, this.choices.size());
		pass();
		pass();

		// pass 2nd main
		pass();
		pass();

		// pass end of turn
		pass();
		pass();

		// pass upkeep
		pass();
		pass();

		// pass draw
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(RagingGoblin.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		assertEquals(1, this.choices.size());
		pass();

		// pass main
		pass();
		pass();

		// pass beginning of combat
		pass();
		pass();

		// declare attackers
		respondWith(pullChoice(RagingGoblin.class));
		pass();
		pass();

		// declare blockers step

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Raging Goblin"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getBlockedByIDs() == null);

		// Player 0 passes
		pass();

		assertEquals(5, this.choices.size());
		respondWith(getSpellAction(FlashFoliage.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Saproling"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getBlockingIDs().size());

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Raging Goblin"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getBlockedByIDs().size());

		// pass declare blockers
		pass();
		pass();

		// resolve combat damage
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mountain"));
	}
}
