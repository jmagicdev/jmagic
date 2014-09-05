package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;
import org.rnd.jmagic.gameTypes.*;

public class DragonWhelpTest extends JUnitTest
{
	@Test
	public void dragonWhelp()
	{
		this.addDeck(DragonWhelp.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(DragonWhelp.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(DragonWhelp.class));
		this.addMana("2RR");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToStep(Step.StepType.END);
		assertEquals(1, this.game.actualState.stack().objects.size());

		// opponent's turn:
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.respondWith(this.getSpellAction(DragonWhelp.class));
		this.addMana("2RR");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(DragonWhelp.WhelpFirebreathing.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToStep(Step.StepType.END);
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(1, this.getGraveyard(0).objects.size());
		assertEquals(0, this.getGraveyard(1).objects.size());
		assertEquals("Dragon Whelp", this.getGraveyard(0).objects.get(0).getName());
	}
}