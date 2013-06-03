package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;
import org.rnd.jmagic.testing.*;

public class HailofArrowsTest extends JUnitTest
{
	@Test
	public void hailOfArrows()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, HailofArrows.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		// End the turn
		// Pass 1st Main Phase
		this.pass();
		this.pass();
		// Pass Beginning of Combat
		this.pass();
		this.pass();
		// Pass Declare Attackers
		this.pass();
		this.pass();
		// Pass End of Combat
		this.pass();
		this.pass();
		// Pass 2nd Main Phase
		this.pass();
		this.pass();
		// Pass End of Turn
		this.pass();
		this.pass();

		// pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		GameObject ragingGoblinA = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		GameObject ragingGoblinB = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		GameObject ragingGoblinC = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		GameObject ragingGoblinD = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		GameObject ragingGoblinE = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		GameObject ragingGoblinF = this.game.physicalState.battlefield().objects.get(0);

		// Pass Main
		this.pass();
		this.pass();

		// Pass Beginning of Combat
		this.pass();
		this.pass();

		// Declare Attackers
		this.respondWith(this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class));
		this.pass();

		this.respondWith(this.getSpellAction(HailofArrows.class));

		// Choose X
		this.respondWith(17);

		// Choose the targets
		assertEquals(6, this.choices.size());
		assertEquals(ragingGoblinF, this.game.actualState.battlefield().objects.get(0));
		assertEquals(ragingGoblinE, this.game.actualState.battlefield().objects.get(1));
		assertEquals(ragingGoblinD, this.game.actualState.battlefield().objects.get(2));
		assertEquals(ragingGoblinC, this.game.actualState.battlefield().objects.get(3));
		assertEquals(ragingGoblinB, this.game.actualState.battlefield().objects.get(4));
		assertEquals(ragingGoblinA, this.game.actualState.battlefield().objects.get(5));
		boolean targetingRagingGoblins[] = {false, false, false, false, false, false};
		for(SanitizedTarget t: this.choices.getAll(SanitizedTarget.class))
			for(int i = 0; i < 6; ++i)
				if(t.targetID == this.game.actualState.battlefield().objects.get(0).ID)
					targetingRagingGoblins[i] = true;
		for(int i = 0; i < 6; ++i)
			assertTrue("Raging Goblin " + i + " not found in choice of targets", targetingRagingGoblins[i]);

		// Target #1
		this.respondWith(this.getTarget(ragingGoblinA), this.getTarget(ragingGoblinB), this.getTarget(ragingGoblinC), this.getTarget(ragingGoblinD), this.getTarget(ragingGoblinE));

		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		divisions.put(ragingGoblinA.ID, 4);
		divisions.put(ragingGoblinB.ID, 3);
		divisions.put(ragingGoblinC.ID, 3);
		divisions.put(ragingGoblinD.ID, 3);
		divisions.put(ragingGoblinE.ID, 4);
		this.divide(divisions);

		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		// Order the Raging Goblins in the graveyard

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals(ragingGoblinF, this.game.actualState.battlefield().objects.get(0));

	}
}