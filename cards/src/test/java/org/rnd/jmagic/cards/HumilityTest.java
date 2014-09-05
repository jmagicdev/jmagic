package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class HumilityTest extends JUnitTest
{
	@Test
	public void humility()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, SoulWarden.class, SoulWarden.class, Humility.class);
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

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Humility.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));

		// Soul Warden (normal)
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Soul Warden", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getKeywordAbilities().size());

		// Resolve Humility
		this.pass();
		this.pass();

		// Humility, Soul Warden (humiliated)
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Soul Warden", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getNonStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getStaticAbilities().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getKeywordAbilities().size());

		this.respondWith(this.getSpellAction(SoulWarden.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Soul Warden (humiliated), Humility, Soul Warden (humiliated)
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(0).lifeTotal);
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().size());
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getNonStaticAbilities().size());

	}
}