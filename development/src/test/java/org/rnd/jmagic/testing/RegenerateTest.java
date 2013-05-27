package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class RegenerateTest extends JUnitTest
{
	@Test
	public void regenerateCantBeRegenerated()
	{
		this.addDeck(Rhox.class, WrathofGod.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);

		respondWith(getSpellAction(Rhox.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		pass();
		pass();

		// regenerate rhox:
		respondWith(getAbilityAction(org.rnd.jmagic.abilities.Regenerate.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.GREEN, Color.GREEN));
		pass();
		pass();

		respondWith(getSpellAction(WrathofGod.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertTrue(getGraveyard(0).objects.get(0).getName().equals("Wrath of God"));
		assertTrue(getGraveyard(0).objects.get(1).getName().equals("Rhox"));

	}

	@Test
	public void regenerateRegeneratePreventionIsTemporary()
	{
		// this test makes sure a creature that got wrath'd can still be
		// regenerated (assuming it survives the wrath)
		this.addDeck(DarksteelGargoyle.class, WrathofGod.class, Regeneration.class, Humble.class, CreepingMold.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		addMana("(13)WWWGGGG");

		respondWith(getSpellAction(DarksteelGargoyle.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS));
		pass();
		pass();

		respondWith(getSpellAction(Regeneration.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.GREEN));
		pass();
		pass();

		respondWith(getSpellAction(WrathofGod.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.WHITE, ManaSymbol.ManaType.WHITE));
		pass();
		pass();

		respondWith(getSpellAction(Humble.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.WHITE));
		pass();
		pass();

		respondWith(getAbilityAction(Regeneration.RegenerateCreature.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN));
		pass();
		pass();

		respondWith(getSpellAction(CreepingMold.class));
		// target the gargoyle (regeneration is also a legal target)
		respondWith(getTarget(DarksteelGargoyle.class));
		donePlayingManaAbilities();
		// auto-choose remaining 2GG
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Regeneration"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Darksteel Gargoyle"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(1).isTapped());

	}

	@Test
	public void regenerateShieldOnlyAppliesOnce()
	{
		this.addDeck(Rhox.class, Rhox.class, Incinerate.class, Incinerate.class, LightningBolt.class, LightningBolt.class, LightningBolt.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		addMana("(14)RRRRGGGGGG");

		respondWith(getSpellAction(Rhox.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.GREEN, ManaSymbol.ManaType.GREEN));
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.Regenerate.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.GREEN));
		pass();
		pass();

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(Rhox.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(Incinerate.class));
		respondWith(getTarget(Rhox.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.RED));
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());

		respondWith(getSpellAction(Rhox.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.GREEN, ManaSymbol.ManaType.GREEN));
		pass();
		pass();

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.Regenerate.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.GREEN));
		pass();
		pass();

		respondWith(getSpellAction(Incinerate.class));
		respondWith(getTarget(Rhox.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.RED));
		pass();
		pass();

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(Rhox.class));
		donePlayingManaAbilities();
		// auto-choose remaining R
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void regenerateTwoShields()
	{
		this.addDeck(Rhox.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class);
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(Rhox.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		pass();
		pass();

		// End the turn
		// Pass 1st Main Phase
		pass();
		pass();
		// Pass Beginning of Combat
		pass();
		pass();
		// Pass Declare Attackers
		pass();
		pass();
		// Pass End of Combat
		pass();
		pass();
		// Pass 2nd Main Phase
		pass();
		pass();
		// Pass End of Turn
		pass();
		pass();

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Rhox"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(2).isTapped() == false);

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(Rhox.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Rhox"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(1).isTapped() == false);

		pass();
		respondWith(getAbilityAction(org.rnd.jmagic.abilities.Regenerate.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getAbilityAction(org.rnd.jmagic.abilities.Regenerate.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		pass();
		pass();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Rhox"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(1).isTapped() == false);

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(Rhox.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Choose which "Regenerate this" to apply, since there two floating
		// shields
		respondWith(getChoiceByToString("Regenerate Rhox"));

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Rhox"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(1).isTapped() == true);

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(Rhox.class));
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Rhox"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(1).isTapped() == true);

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(Rhox.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Rhox"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped() == true);

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(Rhox.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Rhox"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped() == true);

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(Rhox.class));
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Black Lotus"));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Black Lotus"));
		assertTrue(getGraveyard(0).objects.get(0).getName().equals("Rhox"));

	}
}
