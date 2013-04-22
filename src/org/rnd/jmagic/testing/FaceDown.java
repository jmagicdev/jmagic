package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class FaceDown extends JUnitTest
{
	@Test
	public void basicTestPlusClone()
	{
		this.addDeck(MoggFanatic.class, Ixidron.class, Clone.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(MoggFanatic.class, "R");

		castAndResolveSpell(Ixidron.class, "3UU");

		// Ixidron, face-down creature (Mogg Fanatic)
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		GameObject moggFanatic = this.game.actualState.battlefield().objects.get(1);
		assertEquals("", moggFanatic.getName());
		assertEquals(null, moggFanatic.getManaCost());
		assertTrue(moggFanatic.getSuperTypes().isEmpty());
		assertEquals(1, moggFanatic.getTypes().size());
		assertEquals(Type.CREATURE, moggFanatic.getTypes().iterator().next());
		assertTrue(moggFanatic.getSubTypes().isEmpty());
		assertNull(moggFanatic.getExpansionSymbol());
		assertTrue(moggFanatic.getAbilityIDsInOrder().isEmpty());
		assertEquals(2, moggFanatic.getPower());
		assertEquals(2, moggFanatic.getToughness());

		// Ixidron should only have 1 power
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());

		castAndResolveSpell(Clone.class, "3U");
		respondWith(Answer.YES);
		respondWith(pullChoice(MoggFanatic.class));

		// Face-down creature (Clone), Ixidron, face-down creature (Mogg
		// Fanatic)
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		GameObject clone = this.game.actualState.battlefield().objects.get(0);
		assertEquals("", clone.getName());
		assertEquals(null, clone.getManaCost());
		assertTrue(clone.getSuperTypes().isEmpty());
		assertEquals(1, clone.getTypes().size());
		assertEquals(Type.CREATURE, clone.getTypes().iterator().next());
		assertTrue(clone.getSubTypes().isEmpty());
		assertNull(clone.getExpansionSymbol());
		assertTrue(clone.getAbilityIDsInOrder().isEmpty());
		assertEquals(2, clone.getPower());
		assertEquals(2, clone.getToughness());

		// Ixidron should still only have 1 power (Clone isn't face-down)
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
	}

	@Test
	public void morph()
	{
		this.addDeck(OnewithNothing.class, BatteringCraghorn.class, BatteringCraghorn.class, BatteringCraghorn.class, BatteringCraghorn.class, BatteringCraghorn.class, BatteringCraghorn.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// the deck is entirely spells, so every available action
		// should either be a straight-up cast action or a cast face down
		// action:
		for(Object choice: this.choices)
		{
			if(!(choice instanceof org.rnd.jmagic.sanitized.SanitizedCastSpellOrActivateAbilityAction))
			{
				respondWith((java.io.Serializable)choice);
				break;
			}
		}
		addMana("3");
		donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(2, this.game.actualState.stack().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.stack().objects.get(0).getToughness());

		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		pass();

		// since the opponent has only lands in hand and shouldn't be able to
		// turn over the facedown, they have no available actions:
		assertEquals(0, this.choices.size());

		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);
		castAndResolveSpell(OnewithNothing.class, "B");

		assertFalse(this.choices.isEmpty());

		// now the only available action should be to turn the craghorn over:
		respondWith((java.io.Serializable)this.choices.iterator().next());
		addMana("1RR");
		donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
	}

	@Test
	public void morphWithMirrorweave()
	{
		this.addDeck(BatteringCraghorn.class, KrosanColossus.class, RuneclawBear.class, Mirrorweave.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RuneclawBear.class, "1G");

		castAndResolveSpell(BatteringCraghorn.class, "2RR");

		// Casting Krosan as a morph is the only un-derived
		// SanitizedPlayerAction, so go through the choices to find it.
		for(Object choice: this.choices)
			if(choice.getClass() == org.rnd.jmagic.sanitized.SanitizedPlayerAction.class)
			{
				respondWith((java.io.Serializable)choice);
				break;
			}
		addMana("3");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Battering Craghorn", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Runeclaw Bear", this.game.actualState.battlefield().objects.get(2).getName());

		respondWith(getSpellAction(Mirrorweave.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(2)(W)(W)"));
		respondWith(getTarget(BatteringCraghorn.class));
		addMana("2WW");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Battering Craghorn", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Battering Craghorn", this.game.actualState.battlefield().objects.get(2).getName());

		// Turning the "Craghorn" face up is the only un-derived
		// SanitizedPlayerAction, so go through the choices to find it.
		for(Object choice: this.choices)
			if(choice.getClass() == org.rnd.jmagic.sanitized.SanitizedPlayerAction.class)
			{
				respondWith((java.io.Serializable)choice);
				break;
			}
		addMana("1RR");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("Battering Craghorn", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Battering Craghorn", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Battering Craghorn", this.game.actualState.battlefield().objects.get(2).getName());

		goToPhase(Phase.PhaseType.BEGINNING);

		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("Krosan Colossus", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Battering Craghorn", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Runeclaw Bear", this.game.actualState.battlefield().objects.get(2).getName());
	}
}
