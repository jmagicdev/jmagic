package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class SorinMarkovTest extends JUnitTest
{
	@Test
	public void sorinMarkov()
	{
		this.addDeck(SorinMarkov.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(LightningBolt.class, MoggFanatic.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(SorinMarkov.class));
		this.addMana("3BBB");
		this.donePlayingManaAbilities();
		this.game.physicalState.stack().objects.get(0).setPrintedLoyalty(8);
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(SorinMarkov.Mindslaver.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.pass();
		this.pass();

		this.goToPhase(Phase.PhaseType.BEGINNING);
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		assertEquals(this.player(0).ID, this.choosingPlayerID);

		this.respondWith(this.getLandAction(Plains.class));
		assertEquals("Plains", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(this.player(1).ID, this.game.actualState.battlefield().objects.get(0).controllerID);

		this.respondWith(this.getSpellAction(MoggFanatic.class));
		this.player(1).pool.addAll(new ManaPool("R"));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals("Mogg Fanatic", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(this.player(1).ID, this.game.actualState.battlefield().objects.get(0).controllerID);

		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(MoggFanatic.class));
		this.player(1).pool.addAll(new ManaPool("R"));
		this.donePlayingManaAbilities();
		assertEquals(this.player(1).ID, this.game.actualState.stack().objects.get(0).controllerID);
		this.pass();
		this.pass();
	}
}