package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class RelentlessRatsTest extends JUnitTest
{
	@Test
	public void relentlessRats()
	{
		this.addDeck(RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, RelentlessRats.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLACK);

		this.respondWith(this.getSpellAction(RelentlessRats.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLACK, Color.BLACK, Color.BLACK));
		this.pass();
		this.pass();

		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		this.respondWith(this.getSpellAction(RelentlessRats.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLACK, Color.BLACK, Color.BLACK));
		this.pass();
		this.pass();

		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getToughness());

		this.respondWith(this.getSpellAction(RelentlessRats.class));
		this.donePlayingManaAbilities();
		// auto-choose BBB
		this.pass();
		this.pass();

		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(4, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(2).getToughness());

	}
}