package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class NecroticPlagueTest extends JUnitTest
{
	@Test
	public void necroticPlague()
	{
		addDeck(Plains.class, Plains.class, TormodsCrypt.class, SleeperAgent.class, MoggFanatic.class, NecroticPlague.class, MoggFanatic.class, TundraWolves.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(SleeperAgent.class);
		// Automatically choose the opponent to send the Sleeper Agent to
		pass();
		pass();

		castAndResolveSpell(MoggFanatic.class);
		castAndResolveSpell(NecroticPlague.class, MoggFanatic.class);

		respondWith(getAbilityAction(MoggFanatic.SacPing.class));
		respondWith(getTarget(player(1)));
		// Automatically choose the Sleeper Agent for the Necrotic Plague

		// Resolve the Necrotic Plague trigger
		pass();
		pass();
		// Resolve the Mogg Fanatic ability
		pass();
		pass();

		castAndResolveSpell(MoggFanatic.class);
		castAndResolveSpell(TundraWolves.class);

		// Cast and resolve Tormod's Crypt
		respondWith(getSpellAction(TormodsCrypt.class));
		pass();
		pass();

		// Player1's turn
		goToPhase(Phase.PhaseType.BEGINNING);
		respondWith(pullChoice(NecroticPlague.SacrificeThisAtTheBeginningOfYourUpkeep.class), pullChoice(SleeperAgent.Betray.class));
		// Player1 loses 2 life
		pass();
		pass();
		// Let the Necrotic Plague kill the Sleeper Agent
		pass();
		pass();

		// Player1 should be choosing the target for Necrotic Plague's
		// triggered
		// ability
		assertEquals(this.choosingPlayerID, player(1).ID);
		respondWith(getTarget(TundraWolves.class));

		// Play a land to prevent discarding at the end of the turn
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(Plains.class));

		// Player0's turn
		goToPhase(Phase.PhaseType.BEGINNING);
		// Let the Necrotic Plague kill the Tundra Wolves
		pass();
		pass();

		// Necrotic Plague trigger stacks and automatically targets Mogg
		// Fanatic

		// In response to the Necrotic Plague trigger, empty Player0's
		// graveyard
		respondWith(getAbilityAction(TormodsCrypt.GraveyardWipe.class));
		respondWith(getTarget(player(0)));
		pass();
		pass();

		// Resolve the Necrotic Plague trigger
		pass();
		pass();

		// Plains, Mogg Fanatic
		assertEquals(2, this.game.actualState.battlefield().objects.size());
	}
}