package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;
import org.rnd.jmagic.testing.*;

public class HuntedWumpusTest extends JUnitTest
{
	@Test
	public void huntedWumpus()
	{
		this.addDeck(HuntedWumpus.class, HuntedWumpus.class, GrizzlyBears.class, GrizzlyBears.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(HuntedWumpus.class, HuntedWumpus.class, MoggFanatic.class, MoggFanatic.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();
		this.keep();

		int playerZeroID = this.player(0).ID;
		int playerOneID = this.player(1).ID;
		int playerOneHandID = this.getHand(1).ID;
		int playerZeroHandID = this.getHand(0).ID;

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getSpellAction(HuntedWumpus.class));
		this.addMana("3G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.pass();

		// Resolve Wumpus ability
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();
		this.pass();

		// Make sure player 1 has 4 choices, and they're all from their hand
		this.respondWith(Answer.YES);
		java.util.Iterator<SanitizedGameObject> iter = this.choices.getAll(SanitizedGameObject.class).iterator();
		assertEquals(4, this.choices.size());
		for(int i = 0; i < 4; i++)
			assertEquals(playerOneHandID, iter.next().zoneID);
		assertFalse(iter.hasNext());

		// Player 1 chooses Mogg Fanatic
		this.respondWith(this.pullChoice(MoggFanatic.class));

		this.respondWith(this.getSpellAction(HuntedWumpus.class));
		this.addMana("3G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.pass();

		// Resolve Wumpus ability
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();
		this.pass();

		// Player 1 puts a Wumpus onto the battlefield
		this.respondWith(Answer.YES);
		this.respondWith(this.pullChoice(HuntedWumpus.class));

		// Resolve Wumpus ability
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();
		this.pass();

		// Player 0 should have 2 choices
		this.respondWith(Answer.YES);
		iter = this.choices.getAll(SanitizedGameObject.class).iterator();
		assertEquals(2, this.choices.size());
		for(int i = 0; i < 2; i++)
			assertEquals(playerZeroHandID, iter.next().zoneID);
		assertFalse(iter.hasNext());

		// Player 0 chooses Grizzly Bears
		this.respondWith(this.pullChoice(GrizzlyBears.class));

		java.util.List<GameObject> stuff = this.game.actualState.battlefield().objects;

		assertEquals(5, stuff.size());

		assertEquals("Grizzly Bears", stuff.get(0).getName());
		assertEquals(playerZeroID, stuff.get(0).controllerID);

		assertEquals("Hunted Wumpus", stuff.get(1).getName());
		assertEquals(playerOneID, stuff.get(1).controllerID);

		assertEquals("Hunted Wumpus", stuff.get(2).getName());
		assertEquals(playerZeroID, stuff.get(2).controllerID);

		assertEquals("Mogg Fanatic", stuff.get(3).getName());
		assertEquals(playerOneID, stuff.get(3).controllerID);

		assertEquals("Hunted Wumpus", stuff.get(4).getName());
		assertEquals(playerZeroID, stuff.get(4).controllerID);
	}
}