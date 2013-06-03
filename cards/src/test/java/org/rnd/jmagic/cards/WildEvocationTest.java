package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class WildEvocationTest extends JUnitTest
{
	@Test
	public void wildEvocation()
	{
		this.addDeck(WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class, WildEvocation.class);
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, WheelofFortune.class, WheelofFortune.class, WheelofFortune.class, WheelofFortune.class, WheelofFortune.class, WheelofFortune.class, WheelofFortune.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(WildEvocation.class, "5R");
		castAndResolveSpell(WildEvocation.class, "5R");

		goToPhase(Phase.PhaseType.BEGINNING);

		// Stack the wild evocation triggers
		respondArbitrarily();

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		// Resolve a wild evocation trigger (p1 casts wheel of fortune)
		pass();
		pass();

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		// Resolve Wheel of Fortune
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.size());

		// Resolve a wild evocation trigger (p1 puts forest into play)
		pass();
		pass();

		Zone battlefield = this.game.actualState.battlefield();
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, battlefield.objects.size());
		assertEquals("Forest", battlefield.objects.get(0).getName());
		assertEquals("Wild Evocation", battlefield.objects.get(1).getName());
		assertEquals("Wild Evocation", battlefield.objects.get(2).getName());
	}
}