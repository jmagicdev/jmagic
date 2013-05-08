package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class RestartingTheGame extends JUnitTest
{
	@Test
	public void karnLiberated()
	{
		this.addDeck(Plains.class, Plains.class, KarnLiberated.class, LightningBolt.class, ElvishVisionary.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		// Player 0's turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(KarnLiberated.class);

		respondWith(getAbilityAction(KarnLiberated.KarnLiberatedAbility0.class));
		respondWith(getTarget(player(0)));
		pass();
		pass();
		respondWith(pullChoice(LightningBolt.class));

		assertEquals(5, getHand(0).objects.size());
		assertEquals(1, this.game.physicalState.exileZone().objects.size());

		// Player 1's turn
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(Plains.class));

		// Player 0's turn
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getAbilityAction(KarnLiberated.KarnLiberatedAbility0.class));
		respondWith(getTarget(player(0)));
		pass();
		pass();
		respondWith(pullChoice(ElvishVisionary.class));

		assertEquals(5, getHand(0).objects.size());
		assertEquals(2, this.game.physicalState.exileZone().objects.size());

		// Player 1's turn
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(Plains.class));

		// Player 0's turn
		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getAbilityAction(KarnLiberated.KarnLiberatedAbility2.class));
		pass();
		pass();
		keep();
		keep();

		assertEquals(0, this.game.physicalState.exileZone().objects.size());
		assertEquals(1, this.game.physicalState.battlefield().objects.size());
		assertEquals(1, this.game.physicalState.stack().objects.size());
		assertEquals(7, getHand(0).objects.size());
		assertEquals(1, getLibrary(0).objects.size());
		assertEquals(7, getHand(1).objects.size());
		assertEquals(2, getLibrary(1).objects.size());
	}
}
