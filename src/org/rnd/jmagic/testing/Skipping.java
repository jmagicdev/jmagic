package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class Skipping extends JUnitTest
{
	@Test
	public void meditate()
	{
		this.addDeck(Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class);
		this.addDeck(Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class, Meditate.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(Meditate.class);

		assertEquals(player(0).ID, this.game.physicalState.currentTurn().ownerID);

		goToStep(Step.StepType.CLEANUP);
		// discard
		respondWith(pullChoice(Meditate.class), pullChoice(Meditate.class), pullChoice(Meditate.class));
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals(player(1).ID, this.game.physicalState.currentTurn().ownerID);

		goToStep(Step.StepType.CLEANUP);
		// discard
		respondWith(pullChoice(Meditate.class));
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals(player(1).ID, this.game.physicalState.currentTurn().ownerID);

		goToStep(Step.StepType.CLEANUP);
		// discard
		respondWith(pullChoice(Meditate.class));
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals(player(0).ID, this.game.physicalState.currentTurn().ownerID);
	}

	@Test
	public void eonHub()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, EonHub.class, EonHub.class, EonHub.class, EonHub.class);
		this.addDeck(Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class, Swamp.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// upkeep
		pass();
		pass();

		// main phase
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(EonHub.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));

		// resolve eon hub
		pass();
		pass();

		// finish main phase
		pass();
		pass();

		// beginning of combat
		pass();
		pass();

		// declare attackers
		pass();
		pass();

		// end of combat
		pass();
		pass();

		// second main phase
		pass();
		pass();

		// eot
		pass();
		pass();

		// at this point, player 1 should be in their draw step with another
		// card in hand
		// since their upkeep was skipped
		assertEquals(8, getHand(1).objects.size());

		// sanity check -- if they drew the card, it shouldn't be there
		assertEquals(0, getLibrary(1).objects.size());
	}
}
