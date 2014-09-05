package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;
import org.rnd.jmagic.gameTypes.*;

public class FlashofInsightTest extends JUnitTest
{
	@Test
	public void flashOfInsight()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Mountain.class, Swamp.class, Island.class, OnewithNothing.class, Opt.class, Opt.class, Opt.class, Opt.class, Opt.class, FlashofInsight.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Stacked());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(FlashofInsight.class));
		this.respondWith(3);
		this.addMana("4U");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.pullChoice(Island.class));
		this.respondWith(this.pullChoice(Mountain.class), this.pullChoice(Swamp.class));

		assertEquals(7, this.getHand(0).objects.size());
		assertEquals(5, this.getLibrary(0).objects.size());
		assertEquals(1, this.getGraveyard(0).objects.size());
		assertEquals(0, this.game.actualState.exileZone().objects.size());

		this.castAndResolveSpell(OnewithNothing.class, "B");

		this.respondWith(this.getSpellAction(FlashofInsight.class));
		this.respondWith(4);
		this.respondWith(this.pullChoice(Opt.class), this.pullChoice(Opt.class), this.pullChoice(Opt.class), this.pullChoice(Opt.class));
		this.addMana("1U");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.pullChoice(Mountain.class));
		this.respondWith(this.pullChoice(Plains.class), this.pullChoice(Plains.class), this.pullChoice(Plains.class));

		assertEquals(1, this.getHand(0).objects.size());
		assertEquals(4, this.getLibrary(0).objects.size());
		assertEquals(3, this.getGraveyard(0).objects.size());
		assertEquals(5, this.game.actualState.exileZone().objects.size());
	}
}