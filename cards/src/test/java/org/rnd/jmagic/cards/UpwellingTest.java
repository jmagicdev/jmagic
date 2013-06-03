package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class UpwellingTest extends JUnitTest
{
	@Test
	public void upwelling()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Upwelling.class, Upwelling.class, Upwelling.class);
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

		this.respondWith(this.getSpellAction(Upwelling.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED, Color.RED, Color.GREEN, Color.GREEN));
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertTrue(this.player(0).pool.toArray(new ManaSymbol[0])[0].colors.contains(org.rnd.jmagic.engine.Color.RED));
		assertTrue(this.player(0).pool.toArray(new ManaSymbol[0])[1].colors.contains(org.rnd.jmagic.engine.Color.GREEN));

		this.goToPhase(Phase.PhaseType.COMBAT);
		assertEquals(20, this.player(0).lifeTotal);
		assertTrue(this.player(0).pool.toArray(new ManaSymbol[0])[0].colors.contains(org.rnd.jmagic.engine.Color.RED));
		assertTrue(this.player(0).pool.toArray(new ManaSymbol[0])[1].colors.contains(org.rnd.jmagic.engine.Color.GREEN));
	}
}