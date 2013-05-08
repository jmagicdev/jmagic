package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class CharacteristicDefiningAbilities extends JUnitTest
{
	@Test
	public void chimericMass()
	{
		this.addDeck(ChimericMass.class, ChimericMass.class, ChimericMass.class, ChimericMass.class, ChimericMass.class, ChimericMass.class, ChimericMass.class);
		this.addDeck(ChimericMass.class, ChimericMass.class, ChimericMass.class, ChimericMass.class, ChimericMass.class, ChimericMass.class, ChimericMass.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(ChimericMass.class));
		respondWith(18);
		addMana("(18)");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Chimeric Mass", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(18, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getAbilityIDsInOrder().size());
		assertFalse(this.game.actualState.battlefield().objects.get(0).getTypes().contains(Type.CREATURE));

		respondWith(getAbilityAction(ChimericMass.ChimericMassAbility1.class));
		addMana("1");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Chimeric Mass", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(18, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getAbilityIDsInOrder().size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getTypes().contains(Type.CREATURE));
		assertEquals(18, this.game.actualState.battlefield().objects.get(0).getPower());
	}

	@Test
	public void suturedGhoul()
	{
		this.addDeck(
		// Second Hand (6 cards)
		SuturedGhoul.class, Shock.class, Shock.class, Shock.class, Shock.class, Shock.class,
		// Initial Hand (7 cards)
		ShatteredPerception.class, RagingGoblin.class, Maro.class, GrizzlyBears.class, MoggFanatic.class, Forest.class, Plains.class);

		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(ShatteredPerception.class);

		castAndResolveSpell(SuturedGhoul.class);
		respondWith(pullChoice(MoggFanatic.class), pullChoice(Maro.class));

		assertEquals(5, player(0).getHand(this.game.actualState).objects.size());
		assertEquals("Sutured Ghoul", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getPower());

		pass();
	}

	@Test
	public void tarmogoyf()
	{
		this.addDeck(Tarmogoyf.class, Tarmogoyf.class, Tarmogoyf.class, BlackLotus.class, BlackLotus.class, Shock.class, Shock.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(Tarmogoyf.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.GREEN));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Tarmogoyf"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(Tarmogoyf.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Tarmogoyf"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getDamage());

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(Tarmogoyf.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// tarmogoyf should be 3/4 and dead -- it counts itself since it's in
		// the yard now!
		GameObject tarmy = this.game.actualState.get(getGraveyard(0).objects.get(0).ID);
		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertTrue(tarmy.getName().equals("Tarmogoyf"));
		assertEquals(3, tarmy.getPower());
		assertEquals(4, tarmy.getToughness());
	}
}
