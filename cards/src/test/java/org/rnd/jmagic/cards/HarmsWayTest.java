package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class HarmsWayTest extends JUnitTest
{
	@Test
	public void harmsWay()
	{
		this.addDeck(MoggFanatic.class, ChaosCharm.class, LightningBolt.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(HarmsWay.class, Sprout.class, HarmsWay.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(MoggFanatic.class, "R");

		this.respondWith(this.getSpellAction(ChaosCharm.class));
		this.respondWith(this.getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// auto-target Mogg Fanatic
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToStep(Step.StepType.DECLARE_ATTACKERS);
		this.respondWith(this.pullChoice(MoggFanatic.class));
		this.pass();

		this.respondWith(this.getSpellAction(HarmsWay.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("W");
		this.donePlayingManaAbilities();

		this.castAndResolveSpell(Sprout.class, "G");

		this.pass();
		this.pass();

		this.respondWith(this.pullChoice(MoggFanatic.class));

		this.goToStep(Step.StepType.END_OF_COMBAT);

		assertEquals(19, this.player(0).lifeTotal);

		this.respondWith(this.getAbilityAction(MoggFanatic.SacPing.class));
		this.respondWith(this.getTarget(Token.class));
		this.pass();
		this.pass();

		assertEquals(18, this.player(0).lifeTotal);
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(0).getName());

		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(Token.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();

		this.respondWith(this.getSpellAction(HarmsWay.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("W");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(this.pullChoice(LightningBolt.class));

		// resolve the lightning bolt
		this.pass();
		this.pass();

		assertEquals(16, this.player(0).lifeTotal);
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}
}