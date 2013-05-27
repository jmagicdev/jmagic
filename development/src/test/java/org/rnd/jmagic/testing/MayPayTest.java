package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class MayPayTest extends JUnitTest
{
	@Test
	public void llanowarSentinel()
	{
		this.addDeck(LlanowarSentinel.class, LlanowarSentinel.class, LlanowarSentinel.class, LlanowarSentinel.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(LlanowarSentinel.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.GREEN, Color.GREEN));
		pass();
		pass();

		// Sentinel, Lotus in play
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Llanowar Sentinel"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Black Lotus"));

		// Sentinel's ability triggers
		pass();
		pass();

		// Play mana abilities
		donePlayingManaAbilities();
		// Do you want to pay 1G?
		respondWith(Answer.YES);
		respondWith(getMana(Color.BLUE, Color.BLUE));
		// The previous choice should be invalid, so it will ask again
		respondWith(getMana(Color.GREEN, Color.BLUE));
		// Choose the Llanowar Sentinel in the library
		respondWith(pullChoice(LlanowarSentinel.class));

		// Sentinel, Sentinel, Lotus in play
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Llanowar Sentinel"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Llanowar Sentinel"));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Black Lotus"));
	}

	@Test
	public void spiketailHatchlingCantPay()
	{
		this.addDeck(SpiketailHatchling.class, SpiketailHatchling.class, BlackLotus.class, BlackLotus.class, VolcanicHammer.class, VolcanicHammer.class, Mountain.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

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

		respondWith(getLandAction(Mountain.class));

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);

		respondWith(getSpellAction(SpiketailHatchling.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));
		pass();
		pass();

		// Spiketail, Mountain in play
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Spiketail Hatchling"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Mountain"));

		respondWith(getSpellAction(VolcanicHammer.class));
		respondWith(getTarget(player(1)));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		// auto-choose UR
		// no passes - it doesn't resolve

		respondWith(getAbilityAction(SpiketailHatchling.SpiketailHatchlingAbility.class));
		// auto-target hammer
		pass();
		pass();

		donePlayingManaAbilities();
		// The Spiketail ability will counter the spell since the player has no
		// way to pay for it

		// Volcanic Hammer is countered
		assertEquals(0, this.game.actualState.stack().objects.size());

	}

	@Test
	public void spiketailHatchlingChooseNotToPay()
	{
		this.addDeck(SpiketailHatchling.class, SpiketailHatchling.class, BlackLotus.class, BlackLotus.class, LightningBolt.class, LightningBolt.class, Mountain.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

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

		respondWith(getLandAction(Mountain.class));

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);

		respondWith(getSpellAction(SpiketailHatchling.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));
		pass();
		pass();

		// Spiketail, Mountain in play
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Spiketail Hatchling"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Mountain"));

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(player(1)));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		// no passes - it doesn't resolve

		respondWith(getAbilityAction(SpiketailHatchling.SpiketailHatchlingAbility.class));
		// auto-target bolt
		pass();
		pass();

		donePlayingManaAbilities();
		// do you want to pay 1?
		respondWith(Answer.NO);

		// lightning bolt is countered
		assertEquals(0, this.game.actualState.stack().objects.size());
	}

	@Test
	public void spiketailHatchlingChooseToPayAndPay()
	{
		// player can pay, chooses to, pays
		this.addDeck(SpiketailHatchling.class, SpiketailHatchling.class, BlackLotus.class, BlackLotus.class, LightningBolt.class, LightningBolt.class, Mountain.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

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

		respondWith(getLandAction(Mountain.class));

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);

		respondWith(getSpellAction(SpiketailHatchling.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));
		pass();
		pass();

		// Spiketail, Mountain
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Spiketail Hatchling"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Mountain"));

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(player(1)));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		// no passes - it doesn't resolve yet

		respondWith(getAbilityAction(SpiketailHatchling.SpiketailHatchlingAbility.class));
		// auto-target bolt
		pass();
		pass();

		donePlayingManaAbilities();
		// do you want to pay 1?
		respondWith(Answer.YES);
		// auto-choose U

		// lightning bolt is NOT countered
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertTrue(this.game.actualState.stack().objects.get(0).getName().equals("Lightning Bolt"));

	}
}
