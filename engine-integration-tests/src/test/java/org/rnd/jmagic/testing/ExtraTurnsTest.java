package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

public class ExtraTurnsTest extends JUnitTest
{
	@Test
	public void timeStretch()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Island.class, TimeStretch.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getLandAction(Island.class));

		respondWith(getSpellAction(TimeStretch.class));
		respondWith(getTarget(player(0)));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getIntrinsicAbilityAction(SubType.ISLAND));
		donePlayingManaAbilities();
		pass();
		pass();

		goToPhase(Phase.PhaseType.BEGINNING);
		assertTrue(this.game.actualState.currentTurn().ownerID == player(0).ID);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		goToPhase(Phase.PhaseType.BEGINNING);
		assertTrue(this.game.actualState.currentTurn().ownerID == player(0).ID);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		goToPhase(Phase.PhaseType.BEGINNING);
		assertTrue(this.game.actualState.currentTurn().ownerID == player(1).ID);

	}
}
