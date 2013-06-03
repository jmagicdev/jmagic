package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class PrimalBeyondTest extends JUnitTest
{
	@Test
	public void primalBeyondElementalAbilities()
	{
		this.addDeck(PrimalBeyond.class, CharRumbler.class, CharRumbler.class, BlackLotus.class, BlackLotus.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(PrimalBeyond.class));
		this.respondWith(Answer.YES);
		// Choose which char rumbler to reveal
		this.respondWith(this.pullChoice(CharRumbler.class));

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(CharRumbler.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED, Color.RED, Color.RED, Color.RED));
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Char-Rumbler"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getPower() == -1);

		this.respondWith(this.getAbilityAction(org.rnd.jmagic.abilities.Firebreathing.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Char-Rumbler"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getPower());

		this.respondWith(this.getAbilityAction(org.rnd.jmagic.abilities.Firebreathing.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Char-Rumbler"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());

		this.respondWith(this.getAbilityAction(org.rnd.jmagic.abilities.Firebreathing.class));
		this.respondWith(this.getAbilityAction(PrimalBeyond.MakeElementalMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Char-Rumbler"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());

	}

	@Test
	public void primalBeyondElementalSpells()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, PrimalBeyond.class, SparkElemental.class, Shock.class, Twitch.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		// Play a primal beyond, tapped
		this.respondWith(this.getLandAction(PrimalBeyond.class));
		this.respondWith(Answer.NO);

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Primal Beyond"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Twitch.class));
		this.respondWith(this.getTarget(PrimalBeyond.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Primal Beyond"));
		assertTrue(!this.game.actualState.battlefield().objects.get(0).isTapped());

		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.respondWith(this.getAbilityAction(PrimalBeyond.MakeElementalMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.player(0).pool.converted());

		this.respondWith(this.getSpellAction(SparkElemental.class));
		this.respondWith(this.getAbilityAction(PrimalBeyond.MakeElementalMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.player(0).pool.converted());

		this.pass();
		this.pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.player(0).pool.converted());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Spark Elemental"));
	}
}