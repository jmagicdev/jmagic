package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class CoinFlipping extends JUnitTest
{
	@Test
	public void dontManipulateFlip()
	{
		// make sure games that aren't stacked don't allow flip manipulation :-P
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ScoriaWurm.class, ScoriaWurm.class, ScoriaWurm.class, ScoriaWurm.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep:
		pass();
		pass();

		// main phase:
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);

		respondWith(getSpellAction(ScoriaWurm.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		// finish main:
		pass();
		pass();

		// beg. combat:
		pass();
		pass();

		// attackers:
		pass();
		pass();

		// end combat:
		pass();
		pass();

		// 2nd main:
		pass();
		pass();

		// eot:
		pass();
		pass();

		// ////////////// player 1's turn
		// upkeep:
		pass();
		pass();

		// draw:
		pass();
		pass();

		// main:
		pass();
		pass();

		// beg. combat:
		pass();
		pass();

		// attackers:
		pass();
		pass();

		// end combat:
		pass();
		pass();

		// 2nd main:
		pass();
		pass();

		// eot:
		pass();
		pass();

		// discard during cleanup:
		respondWith(pullChoice(Plains.class));

		// //////////////// player 0's turn
		// upkeep:
		// wurm triggers, resolve ability:
		pass();
		pass();

		// something happened but we don't know what...
		// if the game didn't allow me to manipulate the flip, i'll be given
		// priority:
		pass();
	}

	@Test
	public void manipulateFlip()
	{
		// stacked game type allows manipulation of coin flips
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ScoriaWurm.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep:
		pass();
		pass();

		// main phase:
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);

		respondWith(getSpellAction(ScoriaWurm.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		// finish main:
		pass();
		pass();

		// beg. combat:
		pass();
		pass();

		// attackers:
		pass();
		pass();

		// end combat:
		pass();
		pass();

		// 2nd main:
		pass();
		pass();

		// eot:
		pass();
		pass();

		// ////////////// player 1's turn
		// upkeep:
		pass();
		pass();

		// draw:
		pass();
		pass();

		// main:
		pass();
		pass();

		// beg. combat:
		pass();
		pass();

		// attackers:
		pass();
		pass();

		// end combat:
		pass();
		pass();

		// 2nd main:
		pass();
		pass();

		// eot:
		pass();
		pass();

		// discard during cleanup:
		respondWith(pullChoice(Plains.class));

		// //////////////// player 0's turn
		// upkeep:
		// wurm triggers, resolve ability:
		pass();
		pass();
		// manipulate the flip:
		respondWith(Answer.WIN);

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Scoria Wurm"));

		// finish upkeep:
		pass();
		pass();

		// draw:
		pass();
		pass();

		// main:
		pass();
		pass();

		// beg. combat:
		pass();
		pass();

		// no attackers:
		declareNoAttackers();
		// finish declare attackers step:
		pass();
		pass();

		// end combat:
		pass();
		pass();

		// 2nd main:
		pass();
		pass();

		// eot:
		pass();
		pass();

		// ////////////// player 1's turn
		// upkeep:
		pass();
		pass();

		// draw:
		pass();
		pass();

		// main:
		pass();
		pass();

		// beg. combat:
		pass();
		pass();

		// attackers:
		pass();
		pass();

		// end combat:
		pass();
		pass();

		// 2nd main:
		pass();
		pass();

		// eot:
		pass();
		pass();

		// discard during cleanup:
		respondWith(pullChoice(Plains.class));

		// ///////////////// player 0's turn
		// upkeep:
		// wurm triggers, resolve it:
		pass();
		pass();
		// manipulate the flip:
		respondWith(Answer.LOSE);

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertTrue(getHand(0).objects.get(0).getName().equals("Scoria Wurm"));

	}
}
