package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class RhoxTest extends JUnitTest
{
	@Test
	public void rhox()
	{
		// AKA Super Tramp(le)
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, IndomitableAncients.class, IndomitableAncients.class, IndomitableAncients.class);
		this.addDeck(ChaosCharm.class, Rhox.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, GiantGrowth.class, GiantGrowth.class, Plains.class);
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

		this.respondWith(this.getSpellAction(IndomitableAncients.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		this.pass();
		this.pass();

		Identified IndomitableAncientsA = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(IndomitableAncients.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		this.pass();
		this.pass();

		Identified IndomitableAncientsB = this.game.physicalState.battlefield().objects.get(0);

		this.respondWith(this.getSpellAction(IndomitableAncients.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		// auto-select remaining WWWW
		this.pass();
		this.pass();

		Identified IndomitableAncientsC = this.game.physicalState.battlefield().objects.get(0);

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

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Rhox.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED, Color.RED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(ChaosCharm.class));
		this.respondWith(this.getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		this.respondWith(this.getTarget(Rhox.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(GiantGrowth.class));
		this.respondWith(this.getTarget(Rhox.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.GREEN));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(GiantGrowth.class));
		this.respondWith(this.getTarget(Rhox.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Rhox"));
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getToughness());

		assertEquals(IndomitableAncientsC, this.game.actualState.battlefield().objects.get(1));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(1).getToughness());

		assertEquals(IndomitableAncientsB, this.game.actualState.battlefield().objects.get(2));
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(2).getToughness());

		assertEquals(IndomitableAncientsA, this.game.actualState.battlefield().objects.get(3));
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(3).getToughness());

		// pass main
		this.pass();
		this.pass();

		// pass beginning of combat
		this.pass();
		this.pass();

		// declare attackers
		this.respondWith(this.pullChoice(Rhox.class));
		this.pass();
		this.pass();

		// declare blockers
		this.respondWith(this.pullChoice(IndomitableAncients.class), this.pullChoice(IndomitableAncients.class), this.pullChoice(IndomitableAncients.class));
		this.respondArbitrarily();
		this.pass();
		this.pass();

		assertEquals("Rhox", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getDamage());

		assertEquals(IndomitableAncientsC, this.game.actualState.battlefield().objects.get(1));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getDamage());

		assertEquals(IndomitableAncientsB, this.game.actualState.battlefield().objects.get(2));
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());

		assertEquals(IndomitableAncientsA, this.game.actualState.battlefield().objects.get(3));
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(3).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).getDamage());

		assertEquals(20, this.player(0).lifeTotal);

		// combat damage
		// assign 1 to the player and 5 to two ancients (illegal assignment)
		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		divisions.put(IndomitableAncientsA.ID, 5);
		divisions.put(IndomitableAncientsB.ID, 5);
		divisions.put(IndomitableAncientsC.ID, 0);
		divisions.put(this.player(0).ID, 1);
		this.divide(divisions);

		// go ahead and assign all 11 to player 0 (as though Rhox were
		// unblocked)
		divisions.put(IndomitableAncientsA.ID, 0);
		divisions.put(IndomitableAncientsB.ID, 0);
		divisions.put(IndomitableAncientsC.ID, 0);
		divisions.put(this.player(0).ID, 11);
		this.divide(divisions);

		assertEquals("Rhox", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(11, this.game.actualState.battlefield().objects.get(0).getToughness());
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getDamage());

		assertEquals(IndomitableAncientsC, this.game.actualState.battlefield().objects.get(1));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(1).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getDamage());

		assertEquals(IndomitableAncientsB, this.game.actualState.battlefield().objects.get(2));
		assertEquals(2, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(2).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getDamage());

		assertEquals(IndomitableAncientsA, this.game.actualState.battlefield().objects.get(3));
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(10, this.game.actualState.battlefield().objects.get(3).getToughness());
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).getDamage());

		assertEquals(9, this.player(0).lifeTotal);
	}
}