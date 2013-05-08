package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class FreezingEffectParameters extends JUnitTest
{
	@Test
	public void gaeasMight()
	{
		this.addDeck(Plains.class, Island.class, Swamp.class, Mountain.class, TerramorphicExpanse.class, Forest.class, GaeasMight.class, AzusaLostbutSeeking.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		addMana("2GG");

		respondWith(getSpellAction(AzusaLostbutSeeking.class));
		donePlayingManaAbilities();
		respondWith(getMana(ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.COLORLESS, ManaSymbol.ManaType.GREEN));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Azusa, Lost but Seeking"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getLandAction(Island.class));

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Azusa, Lost but Seeking"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getToughness());

		respondWith(getLandAction(TerramorphicExpanse.class));

		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Azusa, Lost but Seeking"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getToughness());

		respondWith(getSpellAction(GaeasMight.class));
		// auto-target Azusa
		donePlayingManaAbilities();
		// auto-choose G

		// respond to gaea's might with terramorphic's ability
		respondWith(getAbilityAction(TerramorphicExpanse.Terraform.class));
		pass();
		pass();

		respondWith(pullChoice(Plains.class));

		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Azusa, Lost but Seeking"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getToughness());

		// Resolve Gaea's Might
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Azusa, Lost but Seeking"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(2).getToughness());

		respondWith(getLandAction(Swamp.class));

		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Azusa, Lost but Seeking"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(3).getToughness());

	}
}
