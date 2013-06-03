package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class LurkingPredatorsTest extends JUnitTest
{
	@Test
	public void lurkingPredators()
	{
		this.addDeck(
		// left in library
		Plains.class, Plains.class, RagingGoblin.class, Righteousness.class, MoggFanatic.class,
		// opening seven
		LurkingPredators.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.addDeck(LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class, LightningBolt.class);

		this.startGame(GameTypes.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(LurkingPredators.class, "4GG");

		this.pass();
		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("R");
		this.donePlayingManaAbilities();

		// lurking predators triggers with mogg fanatic on top
		this.pass();
		this.pass();
		assertEquals("Mogg Fanatic", this.game.actualState.battlefield().objects.get(0).getName());
		this.pass();
		this.pass();

		this.pass();
		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("R");
		this.donePlayingManaAbilities();

		// lurking predators triggers with righteousness on top
		this.pass();
		this.pass();
		this.respondWith(Answer.NO);
		this.pass();
		this.pass();

		this.pass();
		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("R");
		this.donePlayingManaAbilities();

		// lurking predators triggers with righteousness on top
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);
		this.pass();
		this.pass();

		this.pass();
		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("R");
		this.donePlayingManaAbilities();

		// lurking predators triggers with raging goblin on top
		this.pass();
		this.pass();
		assertEquals("Raging Goblin", this.game.actualState.battlefield().objects.get(0).getName());
		this.pass();
		this.pass();

	}
}