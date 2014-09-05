package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;
import org.rnd.jmagic.gameTypes.*;

public class DoublingCubeTest extends JUnitTest
{
	@Test
	public void doublingCube()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(ColossusofSardia.class, SoulWarden.class, DoublingCube.class, CompositeGolem.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.startGame(new Stacked());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		// End the turn
		// Pass 1st Main Phase
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main Phase
		this.pass();
		this.pass();
		// Pass End of Turn
		this.pass();
		this.pass();

		// pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(CompositeGolem.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(DoublingCube.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE));
		this.pass();
		this.pass();

		// this is just to use up the extra white
		this.respondWith(this.getSpellAction(SoulWarden.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DoublingCube.DoubleMana.class));
		this.respondWith(this.getAbilityAction(CompositeGolem.SacForRainbow.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.donePlayingManaAbilities();
		assertEquals(8, this.player(1).pool.converted());
		this.respondWith(this.getMana(Color.BLACK, Color.BLACK, Color.BLACK));

		assertEquals(10, this.player(1).pool.converted());

		this.respondWith(this.getSpellAction(ColossusofSardia.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.BLUE, Color.BLUE, Color.BLACK, Color.BLACK, Color.RED, Color.RED, Color.GREEN));
		this.pass();
		this.pass();

		assertTrue(this.player(1).pool.toArray(new ManaSymbol[0])[0].isColor(org.rnd.jmagic.engine.Color.GREEN));

	}

	@Test
	public void doublingCubeDoesntCopyRestrictions()
	{
		this.addDeck(PrimalBeyond.class, DoublingCube.class, SparkElemental.class, SparkElemental.class, Shock.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(PrimalBeyond.class));
		this.respondWith(Answer.YES);
		this.respondWith(this.pullChoice(SparkElemental.class));

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(DoublingCube.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED, Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(SparkElemental.class));
		this.donePlayingManaAbilities();
		// Automatically choose R
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DoublingCube.DoubleMana.class));
		this.respondWith(this.getAbilityAction(PrimalBeyond.MakeElementalMana.class));
		this.respondWith(Color.RED);
		ManaSymbol elementalMana = this.player(0).getPhysical().pool.toArray(new ManaSymbol[0])[0];
		elementalMana.name = "P";
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLUE, Color.BLUE, Color.BLUE));

		// Attempt to play a shock with the elemental mana
		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.donePlayingManaAbilities();
		this.respondWith(elementalMana);
		// Can't pay for Shock with P, so have to pick R
		this.pullChoice(elementalMana);
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		// Make sure the elemental-mana is left
		assertTrue(this.player(0).pool.toArray(new ManaSymbol[0])[0].name.equals("P"));

		this.respondWith(this.getSpellAction(SparkElemental.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.pass();

		assertEquals(4, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Spark Elemental"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Spark Elemental"));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Doubling Cube"));
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Primal Beyond"));

		assertEquals(18, this.player(1).lifeTotal);

		assertEquals(0, this.player(0).pool.converted());
	}
}