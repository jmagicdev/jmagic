package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class HumbleTest extends JUnitTest
{
	@Test
	public void humble()
	{
		this.addDeck(BlackLotus.class, SoulWarden.class, SoulWarden.class, Humble.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(SoulWarden.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Humble.class));
		this.donePlayingManaAbilities();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Soul Warden", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());

		// Resolve Humble
		this.pass();
		this.pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Soul Warden", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());

		this.respondWith(this.getLandAction(Plains.class));

		this.respondWith(this.getSpellAction(SoulWarden.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Soul Warden (normal), Plains, Soul Warden (humbled)
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getNonStaticAbilities().size());

	}
}