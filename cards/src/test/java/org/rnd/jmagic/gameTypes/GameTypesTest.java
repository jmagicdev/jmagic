package org.rnd.jmagic.gameTypes;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class GameTypesTest extends JUnitTest
{
	@Test
	public void anyNumberInADeck()
	{
		this.addDeck(RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Vintage());

		assertTrue(this.winner == null);
	}

	@Test(expected = AssertionError.class)
	public void tooManyNonBasic()
	{
		this.addDeck(Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Vintage());

		// assertTrue(this.winner.ID == player(1));
	}

	@Test
	public void x10()
	{
		this.addDeck(Plains.class);
		this.addDeck();

		org.rnd.jmagic.gameTypes.packWars.BoosterFactory expansion = new org.rnd.jmagic.gameTypes.packWars.SpecificCardsBoosterFactory("Memnite", "Memnite", "Memnite", "Memnite", "Memnite", "Memnite", "Memnite");

		GameType x10 = new GameType("X10");
		x10.addRule(new org.rnd.jmagic.engine.gameTypes.X10());
		x10.addRule(new PackWars.GameRules(expansion));
		startGame(x10);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
	}
}
