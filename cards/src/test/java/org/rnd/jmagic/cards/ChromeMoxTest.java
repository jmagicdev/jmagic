package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class ChromeMoxTest extends JUnitTest
{
	@Test
	public void chromeMox()
	{
		this.addDeck(Forest.class, Forest.class, Island.class, Mountain.class, Plains.class, ChromeMox.class, KhalniHeartExpedition.class, KhalniHeartExpedition.class, Harrow.class, Forest.class, Forest.class, ChromeMox.class, SummonersPact.class);
		this.addDeck(Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class);
		this.startGame(GameTypes.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(ChromeMox.class));
		this.pass();
		this.pass();

		// Resolve chrome mox trigger
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();

		// Remove a khalni heart expedition to mox
		this.respondWith(Answer.YES);
		this.respondWith(this.pullChoice(KhalniHeartExpedition.class));

		this.respondWith(this.getAbilityAction(ChromeMox.ImprintMana.class));
		{
			ManaPool pool = this.player(0).pool;
			java.util.Collection<Color> symbolColors = pool.iterator().next().colors;
			assertEquals(1, pool.converted());
			assertEquals(1, symbolColors.size());
			assertEquals(Color.GREEN, symbolColors.iterator().next());
		}

		// This should use up the green mana we got from chrome mox
		this.castAndResolveSpell(KhalniHeartExpedition.class, "U");

		this.respondWith(this.getLandAction(Forest.class));

		assertEquals(1, this.game.actualState.stack().objects.size());

		this.castAndResolveSpell(Harrow.class, "2G");

		this.respondWith(this.pullChoice(Plains.class), this.pullChoice(Mountain.class));

		// Order the triggers on the stack
		assertEquals(this.choiceType, PlayerInterface.ChoiceType.TRIGGERS);
		this.respondArbitrarily();

		// Resolve the 3 Khalni Heart triggers
		for(int i = 0; i < 3; i++)
		{
			this.pass();
			this.pass();
			this.respondWith(Answer.YES);
		}

		assertEquals("Khalni Heart Expedition", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(3, this.game.actualState.battlefield().objects.get(2).counters.size());

		this.respondWith(this.getAbilityAction(KhalniHeartExpedition.SoIHeardYouLikeLandfall.class));
		// order costs
		this.pass();
		this.pass();

		this.respondWith(this.pullChoice(Forest.class), this.pullChoice(Forest.class));

		assertEquals(5, this.game.actualState.battlefield().objects.size());
		for(int i = 0; i < 4; i++)
			assertTrue(this.game.actualState.battlefield().objects.get(i).getTypes().contains(Type.LAND));
		assertEquals("Chrome Mox", this.game.actualState.battlefield().objects.get(4).getName());
	}
}