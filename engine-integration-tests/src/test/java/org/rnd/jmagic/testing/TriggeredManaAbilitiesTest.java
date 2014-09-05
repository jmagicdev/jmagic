package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

public class TriggeredManaAbilitiesTest extends JUnitTest
{
	@Test
	public void highTide()
	{
		this.addDeck(HighTide.class, HighTide.class, Island.class, Island.class, Island.class, Island.class, Island.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getLandAction(Island.class));

		castAndResolveSpell(HighTide.class, "U");

		respondWith(getSpellAction(HighTide.class));
		respondWith(getAbilityAction(Game.IntrinsicManaAbility.class));
		assertEquals(2, player(0).pool.converted());
	}

	@Test
	public void overgrowth()
	{
		this.addDeck(BirdsofParadise.class, BirdsofParadise.class, BirdsofParadise.class, Forest.class, Overgrowth.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(Forest.class));

		respondWith(getSpellAction(Overgrowth.class));
		addMana("2G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getIntrinsicAbilityAction(SubType.FOREST));

		assertEquals(3, player(0).pool.converted());

		respondWith(getSpellAction(BirdsofParadise.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		respondWith(getSpellAction(BirdsofParadise.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		respondWith(getSpellAction(BirdsofParadise.class));
		donePlayingManaAbilities();
		pass();
		pass();
	}
}
