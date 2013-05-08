package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class Layers extends JUnitTest
{
	@Test
	public void characteristicDefiningAbilitiesFirst()
	{

		this.addDeck(BlackLotus.class, BlackLotus.class, Humility.class, Tarmogoyf.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(Humility.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.GREEN));
		pass();
		pass();

		respondWith(getSpellAction(Tarmogoyf.class));
		donePlayingManaAbilities();
		// Will automatically pick all the G in player0's mana-pool
		pass();
		pass();

		// Even though Tarmogoyf's ability came into play later, its CDA effect
		// will apply before Humility's effect, therefore Tarmogoyf will be a
		// 1/1
		// Tarmogoyf, Humility
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());

	}

	@Test
	public void customContinuousEffectType()
	{
		// Coat of arms
		this.addDeck(CoatofArms.class, GoblinRuinblaster.class, GoblinRuinblaster.class, MoggFanatic.class, MoggFanatic.class, TrollAscetic.class, Sprout.class);
		this.addDeck(Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class, Sprout.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(CoatofArms.class, "5");

		// 1/1 Saproling
		castAndResolveSpell(Sprout.class, "G");

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals("Coat of Arms", this.game.actualState.battlefield().objects.get(1).getName());

		// 2/1 Goblin Shaman
		respondWith(getSpellAction(GoblinRuinblaster.class));
		// Don't kick:
		respondWith();
		addMana("2R");
		donePlayingManaAbilities();
		pass();
		pass();

		// 1/1 Goblin
		castAndResolveSpell(MoggFanatic.class, "R");

		// 3/2 Troll Shaman
		castAndResolveSpell(TrollAscetic.class, "1GG");

		// 2/1 Goblin Shaman
		respondWith(getSpellAction(GoblinRuinblaster.class));
		// Don't kick:
		respondWith();
		addMana("2R");
		donePlayingManaAbilities();
		pass();
		pass();

		// 1/1 Goblin
		castAndResolveSpell(MoggFanatic.class, "R");

		assertEquals("Mogg Fanatic", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1 + 3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1 + 3, this.game.actualState.battlefield().objects.get(0).getToughness());

		assertEquals("Goblin Ruinblaster", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(2 + 4, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1 + 4, this.game.actualState.battlefield().objects.get(1).getToughness());

		assertEquals("Troll Ascetic", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals(3 + 2, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(2 + 2, this.game.actualState.battlefield().objects.get(2).getToughness());

		assertEquals("Mogg Fanatic", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals(1 + 3, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(1 + 3, this.game.actualState.battlefield().objects.get(3).getToughness());

		assertEquals("Goblin Ruinblaster", this.game.actualState.battlefield().objects.get(4).getName());
		assertEquals(2 + 4, this.game.actualState.battlefield().objects.get(4).getPower());
		assertEquals(1 + 4, this.game.actualState.battlefield().objects.get(4).getToughness());

		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(5).getName());
		assertEquals(1 + 0, this.game.actualState.battlefield().objects.get(5).getPower());
		assertEquals(1 + 0, this.game.actualState.battlefield().objects.get(5).getToughness());

		assertEquals("Coat of Arms", this.game.actualState.battlefield().objects.get(6).getName());
	}

	@Test
	public void expirationDependentOnEffect()
	{
		this.addDeck(ChildofNight.class, OliviaVoldaren.class, ChildofNight.class, OliviaVoldaren.class, ChildofNight.class, OliviaVoldaren.class, ChildofNight.class, OliviaVoldaren.class);
		this.addDeck(Threaten.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(ChildofNight.class);
		castAndResolveSpell(OliviaVoldaren.class);

		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(Threaten.class, OliviaVoldaren.class);

		respondWith(getAbilityAction(OliviaVoldaren.OliviaVoldarenAbility2.class));
		respondWith(getTarget(ChildofNight.class));
		addMana("3BB");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals("Child of Night", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(player(1).ID, this.game.actualState.battlefield().objects.get(1).controllerID);
	}

	@Test
	public void powerToughnessSubLayers()
	{
		// Stacked GameTypes read the deck starting at the bottom
		this.addDeck(// Need an extra card because Inside Out draws a card
		// After drawing, the only thing left in the library will be two Plains
		Plains.class, Plains.class, Plains.class, Plains.class, StreamofUnconsciousness.class, InsideOut.class, AmbassadorLaquatus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		// upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(AmbassadorLaquatus.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		// Will automatically pick all the U in player0's mana-pool
		pass();
		pass();

		// Ambassador
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(InsideOut.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(1)(U)"));
		// Will automatically target Ambassador Laquatus
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLUE, Color.BLUE));
		pass();
		pass();

		// Ambassador
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getSpellAction(StreamofUnconsciousness.class));
		// Will automatically target Ambassador Laquatus
		donePlayingManaAbilities();
		// Will automatically pick the remaining U in player0's mana-pool
		pass();
		pass();

		// Even though the Stream was played after Inside Out, Stream's effect
		// will apply first and cause Ambassador Laquatus to be a -3/3, after
		// which Inside Out will switch power/toughness, killing Laquatus

		// Ambassador, Stream, Inside Out, Lotus, Lotus
		assertEquals(5, getGraveyard(0).objects.size());

	}
}
