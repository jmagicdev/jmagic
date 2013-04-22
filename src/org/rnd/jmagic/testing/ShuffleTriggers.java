package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class ShuffleTriggers extends JUnitTest
{
	@Test
	public void cosisTrickster()
	{
		this.addDeck(CosisTrickster.class, MnemonicNexus.class, BlackLotus.class, BlackLotus.class, TomeScour.class, TomeScour.class, ScaldingTarn.class, ScaldingTarn.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(CosisTrickster.class, "U");

		// Tome Scour is only being cast to put stuff in all players graveyards
		castAndResolveSpell(TomeScour.class, "U", player(1));
		castAndResolveSpell(TomeScour.class, "U", player(2));

		castAndResolveSpell(MnemonicNexus.class, "3U");

		assertEquals(PlayerInterface.ChoiceType.TRIGGERS, this.choiceType);
		assertEquals(2, this.choices.size());
		respondArbitrarily();

		assertEquals("Cosi's Trickster", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.stack().objects.size());

		pass();
		pass();
		pass();

		respondWith(Answer.YES);

		assertEquals("Cosi's Trickster", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();
		pass();

		respondWith(Answer.YES);

		assertEquals("Cosi's Trickster", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(0, this.game.actualState.stack().objects.size());
	}

	@Test
	public void shuffleCardThatsNotThereAnymore()
	{
		// 701.15c If an effect would cause a player to shuffle one or more
		// specific objects into a library, but none of those objects are in the
		// zone they're expected to be in, that library is not shuffled.

		this.addDeck(CosisTrickster.class, MnemonicNexus.class, Plains.class, Shadowfeed.class, TomeScour.class, TomeScour.class, ScaldingTarn.class);
		this.addDeck(Vigor.class, Vigor.class, Vigor.class, Vigor.class, Vigor.class, Vigor.class, Vigor.class, Vigor.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(CosisTrickster.class, "U");

		// dump vigor:
		castAndResolveSpell(TomeScour.class, "U", player(1));
		assertEquals("Vigor", getGraveyard(1).objects.get(0).getName());

		// vigor triggers:
		assertEquals(1, this.game.actualState.stack().objects.size());

		// remove vigor:
		castAndResolveSpell(Shadowfeed.class, "B", Vigor.class);

		// resolve vigor trigger:
		pass();
		pass();

		// no trickster trigger:
		assertEquals(0, this.game.actualState.stack().objects.size());
	}

	@Test
	public void shuffleEmptySet()
	{
		// This is the same as the previous test, except that we don't put any
		// cards in anyone's graveyard before causing players to shuffle their
		// graveyards into their libraries. This means that players are
		// instructed to shuffle empty sets into their libraries.
		//
		// 701.15d: If an effect would cause a player to shuffle a set of
		// objects into a library, that library is shuffled even if there are no
		// objects in that set.

		this.addDeck(CosisTrickster.class, MnemonicNexus.class, BlackLotus.class, BlackLotus.class, TomeScour.class, TomeScour.class, ScaldingTarn.class, ScaldingTarn.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(CosisTrickster.class, "U");
		castAndResolveSpell(MnemonicNexus.class, "3U");

		assertEquals(PlayerInterface.ChoiceType.TRIGGERS, this.choiceType);
		assertEquals(2, this.choices.size());
		respondArbitrarily();

		assertEquals("Cosi's Trickster", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.stack().objects.size());

		pass();
		pass();
		pass();

		respondWith(Answer.YES);

		assertEquals("Cosi's Trickster", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();
		pass();

		respondWith(Answer.YES);

		assertEquals("Cosi's Trickster", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(0, this.game.actualState.stack().objects.size());
	}
}
