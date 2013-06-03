package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class SoulblastTest extends JUnitTest
{
	@Test
	public void soulblast()
	{

		this.addDeck(IronshellBeetle.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Soulblast.class, Soulblast.class, Soulblast.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
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

		this.respondWith(this.getSpellAction(IronshellBeetle.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.GREEN, Color.GREEN));
		this.pass();
		this.pass();

		// Resolve beetle cip ability
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Ironshell Beetle"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());

		this.respondWith(this.getSpellAction(Soulblast.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED, Color.RED, Color.RED, Color.BLUE, Color.BLUE, Color.BLUE));
		this.pass();
		this.pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(18, this.player(1).lifeTotal);
	}
}