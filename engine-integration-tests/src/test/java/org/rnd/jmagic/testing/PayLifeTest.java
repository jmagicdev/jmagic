package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class PayLifeTest extends JUnitTest
{
	@Test
	public void woodedFoothills()
	{
		this.addDeck(WoodedFoothills.class, TaintedSigil.class, WoodedFoothills.class, WoodedFoothills.class, WoodedFoothills.class, WoodedFoothills.class, WoodedFoothills.class);
		this.addDeck(WoodedFoothills.class, WoodedFoothills.class, WoodedFoothills.class, WoodedFoothills.class, WoodedFoothills.class, WoodedFoothills.class, WoodedFoothills.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(WoodedFoothills.class));
		respondWith(getAbilityAction(org.rnd.jmagic.cardTemplates.FetchLand.Fetch.class));
		assertEquals(19, player(0).lifeTotal);
		pass();
		pass();

		castAndResolveSpell(TaintedSigil.class, "1WB");
		respondWith(getAbilityAction(TaintedSigil.GainLife.class));
		pass();
		pass();

		// paying life counts as losing it:
		assertEquals(20, player(0).lifeTotal);
	}
}
