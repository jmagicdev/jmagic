package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class GuileTest extends JUnitTest
{
	@Test
	public void guile()
	{
		this.addDeck(Guile.class, GrizzlyBears.class, Counterspell.class, Counterspell.class, Counterspell.class, Counterspell.class, Counterspell.class);
		this.addDeck(PactofNegation.class, PactofNegation.class, Sprout.class, Sprout.class, ElvishWarrior.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Play and resolve Guile
		this.castAndResolveSpell(Guile.class, "3UUU");

		// Play Grizzly Bears
		this.respondWith(this.getSpellAction(GrizzlyBears.class));
		this.addMana("1G");
		this.donePlayingManaAbilities();

		// Counter Grizzly Bears
		this.castAndResolveSpell(Counterspell.class, "UU");

		// Choose to cast Grizzly Bears
		this.respondWith(this.pullChoice(GrizzlyBears.class));

		// Resolve the Grizzly Bears
		this.pass();
		this.pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Guile", this.game.actualState.battlefield().objects.get(1).getName());

		this.pass();

		// Opp casts Sprout
		this.respondWith(this.getSpellAction(Sprout.class));
		this.addMana("G");
		this.donePlayingManaAbilities();
		this.pass();

		// Counterspell @ Sprout
		this.respondWith(this.getSpellAction(Counterspell.class));
		this.addMana("UU");
		this.donePlayingManaAbilities();
		this.pass();

		// Pact @ Counterspell
		this.respondWith(this.getSpellAction(PactofNegation.class));
		this.respondWith(this.getTarget(Counterspell.class));
		this.pass();

		// Counterspell @ Pact
		this.respondWith(this.getSpellAction(Counterspell.class));
		this.respondWith(this.getTarget(PactofNegation.class));
		this.addMana("UU");
		this.donePlayingManaAbilities();

		// Counter pact, recast @ counterspell
		this.pass();
		this.pass();
		this.respondWith(this.pullChoice(PactofNegation.class));

		// There are actually 2 counterspells on the stack right now, the
		// one
		// resolving at pact, and the one waiting to counter sprout
		GameObject counterspell = this.game.actualState.stack().objects.get(2);
		assertEquals("Counterspell", counterspell.getName());
		this.respondWith(this.getTarget(counterspell));

		// Counter counterspell, dont recast
		this.pass();
		this.pass();
		this.respondWith();

		// Original sprout resolves, uncountered
		this.pass();
		this.pass();

		// Go to player 1's turn
		this.goToPhase(Phase.PhaseType.BEGINNING);
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Cast Elvish Warrior
		this.respondWith(this.getSpellAction(ElvishWarrior.class));
		this.addMana("GG");
		this.donePlayingManaAbilities();
		this.pass();

		// Counterspell @ Elvish Warrior
		this.respondWith(this.getSpellAction(Counterspell.class));
		this.addMana("UU");
		this.donePlayingManaAbilities();
		this.pass();

		// Pact @ Counterspell
		this.respondWith(this.getSpellAction(PactofNegation.class));
		this.respondWith(this.getTarget(Counterspell.class));
		this.pass();
		this.pass();

		this.pass();

		// Counterspell @ Elvish Warrior
		this.castAndResolveSpell(Counterspell.class, "UU");

		// Choose to cast Elvish Warrior
		this.respondWith(this.pullChoice(ElvishWarrior.class));
		this.pass();
		this.pass();

		int player0 = this.player(0).ID;
		int player1 = this.player(1).ID;

		assertEquals(4, this.game.actualState.battlefield().objects.size());

		assertEquals("Guile", this.game.actualState.battlefield().objects.get(3).getName());
		assertEquals("Grizzly Bears", this.game.actualState.battlefield().objects.get(2).getName());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Elvish Warrior", this.game.actualState.battlefield().objects.get(0).getName());

		assertEquals(player0, this.game.actualState.battlefield().objects.get(3).controllerID);
		assertEquals(player0, this.game.actualState.battlefield().objects.get(2).controllerID);
		assertEquals(player1, this.game.actualState.battlefield().objects.get(1).controllerID);
		assertEquals(player0, this.game.actualState.battlefield().objects.get(0).controllerID);

		assertEquals(player0, this.game.actualState.battlefield().objects.get(3).ownerID);
		assertEquals(player0, this.game.actualState.battlefield().objects.get(2).ownerID);
		// Tokens are owned by the controller of the effect that created
		// them:
		assertEquals(player1, this.game.actualState.battlefield().objects.get(1).ownerID);
		assertEquals(player1, this.game.actualState.battlefield().objects.get(0).ownerID);

		// Go to player 0's upkeep to make sure his stolen pact triggers
		this.goToPhase(Phase.PhaseType.BEGINNING);
		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(org.rnd.jmagic.engine.DelayedTrigger.class, this.game.actualState.stack().objects.get(0).getClass());
	}
}