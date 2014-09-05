package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class ProteanHydraTest extends JUnitTest
{
	@Test
	public void proteanHydra()
	{
		this.addDeck(ProteanHydra.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Excruciator.class, ChaosCharm.class, Shock.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(ProteanHydra.class));
		this.respondWith(17);
		this.addMana("(17)G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(17, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());

		this.goToPhase(Phase.PhaseType.BEGINNING);
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(Excruciator.class, "6RR");

		this.respondWith(this.getSpellAction(ChaosCharm.class));
		this.respondWith(this.getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		this.respondWith(this.getTarget(Excruciator.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToStep(Step.StepType.DECLARE_ATTACKERS);
		this.respondWith(this.pullChoice(Excruciator.class));

		this.goToStep(Step.StepType.DECLARE_BLOCKERS);
		this.respondWith(this.pullChoice(ProteanHydra.class));

		this.goToStep(Step.StepType.COMBAT_DAMAGE);

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(10, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(7, this.game.actualState.battlefield().objects.get(0).getDamage());

		// Stack all the triggers
		this.respondArbitrarily();

		this.castAndResolveSpell(Shock.class, "R", ProteanHydra.class);

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(8, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(7, this.game.actualState.battlefield().objects.get(0).getDamage());

		// Stack the triggers the shock caused too
		this.respondArbitrarily();

		this.goToStep(Step.StepType.END);

		// Stack the 9 triggers that happened this turn
		assertEquals(9, this.choices.size());
		this.respondArbitrarily();

		for(int i = 0; i < 9; i++)
		{
			assertEquals(9 - i, this.game.actualState.stack().objects.size());
			assertEquals(1, this.game.actualState.battlefield().objects.size());
			assertEquals(8 + (2 * i), this.game.actualState.battlefield().objects.get(0).counters.size());
			assertEquals(7, this.game.actualState.battlefield().objects.get(0).getDamage());

			this.pass();
			this.pass();
		}

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(26, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(7, this.game.actualState.battlefield().objects.get(0).getDamage());
	}
}