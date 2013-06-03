package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class GaddockTeegTest extends JUnitTest
{
	@Test
	public void gaddockTeeg()
	{
		this.addDeck(GaddockTeeg.class, BlackLotus.class, WrathofGod.class, Fireball.class, AntQueen.class, ProteanHydra.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(GaddockTeeg.class, "GW");

		// noncreature that costs < 4 and has no X
		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		// creature that costs >= 4
		this.castAndResolveSpell(AntQueen.class, "3GG");

		// creature that costs X
		this.respondWith(this.getSpellAction(ProteanHydra.class));
		this.respondWith(4);
		this.addMana("4G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// noncreature that costs >= 4
		this.confirmCantBePlayed(WrathofGod.class);

		// noncreature that costs X
		this.confirmCantBePlayed(Fireball.class);

		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);
	}
}