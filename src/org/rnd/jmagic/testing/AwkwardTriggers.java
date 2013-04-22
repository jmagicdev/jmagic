package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

// hawkward
public class AwkwardTriggers extends JUnitTest
{
	@Test
	public void guerrillaTactics()
	{
		this.addDeck(MindRot.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(GuerrillaTactics.class, GuerrillaTactics.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(MindRot.class));
		respondWith(getTarget(player(1)));
		addMana("2B");
		donePlayingManaAbilities();
		pass();
		pass();

		// discard two guerilla tactics:
		respondWith(pullChoice(GuerrillaTactics.class), pullChoice(GuerrillaTactics.class));
		// order the triggers:
		respondWith(pullChoice(GuerrillaTactics.DiscardTrigger.class), pullChoice(GuerrillaTactics.DiscardTrigger.class));
		// target player 0 with both triggers:
		respondWith(getTarget(player(0)));
		respondWith(getTarget(player(0)));
	}

	@Test
	public void zendikon()
	{
		this.addDeck(VastwoodZendikon.class, Terminate.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(Plains.class));
		castAndResolveSpell(VastwoodZendikon.class, "4G");
		castAndResolveSpell(Terminate.class, "BR");
		// zendikon trigger:
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();
		// terminate, zendikon:
		assertEquals(2, getGraveyard(0).objects.size());
		// five plains:
		assertEquals(5, getHand(0).objects.size());
	}
}
