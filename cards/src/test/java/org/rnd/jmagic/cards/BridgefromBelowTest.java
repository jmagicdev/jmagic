package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;
import org.rnd.jmagic.gameTypes.*;

public class BridgefromBelowTest extends JUnitTest
{
	@Test
	public void bridgeFromBelow()
	{
		this.addDeck(
		// mill off:
		BridgefromBelow.class, Plains.class, DreadReturn.class, Narcomoeba.class, Narcomoeba.class, Narcomoeba.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class,
		// opening seven:
		GlimpsetheUnthinkable.class, MoggFanatic.class, GlimpsetheUnthinkable.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.addDeck(GlimpsetheUnthinkable.class, GlimpsetheUnthinkable.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(new Stacked());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// glimpse myself:
		this.respondWith(this.getSpellAction(GlimpsetheUnthinkable.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("UB");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// order narcomoeba triggers:
		this.respondArbitrarily();

		// put narcomoebas into play:
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		// flashback dread return:
		this.respondWith(this.getSpellAction(DreadReturn.class));
		// auto-target raging goblin

		// order triggers:
		this.respondArbitrarily();

		// zombie tokens (three triggers total):
		this.pass();
		this.pass();

		this.pass();
		this.pass();

		this.pass();
		this.pass();

		// dread return hasn't resolved yet, so it's just the tokens in
		// play:
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("Zombie", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Zombie", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Zombie", this.game.actualState.battlefield().objects.get(2).getName());
	}
}