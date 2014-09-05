package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;
import org.rnd.jmagic.gameTypes.*;

public class GraftedExoskeletonTest extends JUnitTest
{
	@Test
	public void graftedExoskeleton()
	{
		this.addDeck(GraftedExoskeleton.class, GrizzlyBears.class, Shatter.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(GraftedExoskeleton.class);
		castAndResolveSpell(GrizzlyBears.class);

		respondWith(getAbilityAction(org.rnd.jmagic.abilities.keywords.Equip.EquipAbility.class));
		addMana("2");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Grafted Exoskeleton", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(this.game.actualState.battlefield().objects.get(0).ID, this.game.actualState.battlefield().objects.get(1).getAttachedTo());
		assertEquals(0, this.game.actualState.stack().objects.size());

		castAndResolveSpell(Shatter.class);

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.game.actualState.stack().objects.size());

		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
	}
}