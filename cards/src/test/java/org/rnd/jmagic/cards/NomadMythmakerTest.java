package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class NomadMythmakerTest extends JUnitTest
{
	@Test
	public void nomadMythmaker()
	{
		this.addDeck(NomadMythmaker.class, MindRot.class, HolyStrength.class, UnholyStrength.class, GrizzlyBears.class, ChaosCharm.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(NomadMythmaker.class));
		this.addMana("2W");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(ChaosCharm.class));
		this.respondWith(this.getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// Auto-target Mythmaker
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(GrizzlyBears.class));
		this.addMana("1G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(MindRot.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("2B");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(this.pullChoice(UnholyStrength.class), this.pullChoice(HolyStrength.class));

		this.respondWith(this.getAbilityAction(NomadMythmaker.PutAuraOntoTheBattlefield.class));
		this.respondWith(this.getTarget(UnholyStrength.class));
		this.addMana("W");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(this.pullChoice(GrizzlyBears.class));

		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).getToughness());
	}
}