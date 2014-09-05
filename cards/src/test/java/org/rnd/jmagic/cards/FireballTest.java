package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;
import org.rnd.jmagic.gameTypes.*;

public class FireballTest extends JUnitTest
{
	@Test
	public void fireball()
	{
		this.addDeck(Fireball.class, GrizzlyBears.class, MoggFanatic.class, MoggFanatic.class, Fireball.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(MoggFanatic.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(GrizzlyBears.class));
		this.addMana("1G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Fireball.class));
		// choose X:
		this.respondWith(3);
		// choose targets:
		this.respondWith(this.getTarget(GrizzlyBears.class), this.getTarget(MoggFanatic.class));
		// 3R + (2 - 1) = 4R
		this.addMana("4R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// 3 / 2 rounded down = 1 damage -- fanatic dies; bears lives
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getDamage());

		this.respondWith(this.getSpellAction(MoggFanatic.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Fireball.class));
		// choose X:
		this.respondWith(1);
		// targets:
		this.respondWith(this.getTarget(GrizzlyBears.class), this.getTarget(MoggFanatic.class));
		// 1R + (2 - 1) = 2R
		this.addMana("2R");
		this.donePlayingManaAbilities();

		// fireball on the stack, kill the fanatic:
		this.respondWith(this.getAbilityAction(MoggFanatic.SacPing.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.pass();
		this.pass();

		// resolve fireball
		this.pass();
		this.pass();

		// fireball has one legal target -- the bears, so its 1 damage
		// divided
		// evenly rounded down should kill the bears:
		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}
}