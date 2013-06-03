package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class ManabarbsTest extends JUnitTest
{
	@Test
	public void manabarbs()
	{
		this.addDeck(Manabarbs.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(Manabarbs.class));
		this.addMana("3R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Plains.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();
		assertEquals(19, this.player(0).lifeTotal);

		this.goToPhase(Phase.PhaseType.ENDING);
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getLandAction(Plains.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();
		assertEquals(19, this.player(1).lifeTotal);
	}
}