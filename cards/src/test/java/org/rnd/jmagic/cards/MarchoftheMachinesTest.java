package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class MarchoftheMachinesTest extends JUnitTest
{
	@Test
	public void marchOfTheMachines()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, ChromaticStar.class);
		this.addDeck(MarchoftheMachines.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, PhyrexianWalker.class, Island.class, ChromaticStar.class, AngelicWall.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Plains.class));

		this.respondWith(this.getSpellAction(ChromaticStar.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));
		this.donePlayingManaAbilities();
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

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(PhyrexianWalker.class));
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Island.class));

		this.respondWith(this.getSpellAction(AngelicWall.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(ChromaticStar.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(MarchoftheMachines.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.respondWith(this.getIntrinsicAbilityAction(SubType.ISLAND));
		this.donePlayingManaAbilities();

		// Leave the March on the stack while we make some assertions

		// Star, Wall, Island, Walker, Lotus, Star, Plains
		assertEquals(7, this.game.actualState.battlefield().objects.size());

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Chromatic Star"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Angelic Wall"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Island"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(2).getTypes().contains(org.rnd.jmagic.engine.Type.LAND));

		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Phyrexian Walker"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(3).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(3).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));
		assertTrue(this.game.actualState.battlefield().objects.get(3).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(3).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(4).getName().equals("Black Lotus"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(4).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(4).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.game.actualState.battlefield().objects.get(5).getName().equals("Chromatic Star"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(5).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(5).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.game.actualState.battlefield().objects.get(6).getName().equals("Plains"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(6).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(6).getTypes().contains(org.rnd.jmagic.engine.Type.LAND));

		// 2x Black Lotus
		assertEquals(2, this.getGraveyard(1).objects.size());

		assertTrue(this.getGraveyard(1).objects.get(0).getName().equals("Black Lotus"));
		assertEquals(1, this.getGraveyard(1).objects.get(0).getTypes().size());
		assertTrue(this.getGraveyard(1).objects.get(0).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.getGraveyard(1).objects.get(1).getName().equals("Black Lotus"));
		assertEquals(1, this.getGraveyard(1).objects.get(1).getTypes().size());
		assertTrue(this.getGraveyard(1).objects.get(1).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		// Resolve the March of the Machines
		this.pass();
		this.pass();

		// March, Star, Wall, Island, Walker, Star, Plains
		assertTrue(this.game.actualState.battlefield().objects.size() == 7.0);

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("March of the Machines"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getTypes().contains(org.rnd.jmagic.engine.Type.ENCHANTMENT));

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Chromatic Star"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(1).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(1).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(1).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Angelic Wall"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(2).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(2).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(0, this.game.actualState.battlefield().objects.get(2).getPower());
		assertEquals(4, this.game.actualState.battlefield().objects.get(2).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Island"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(3).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(3).getTypes().contains(org.rnd.jmagic.engine.Type.LAND));

		assertTrue(this.game.actualState.battlefield().objects.get(4).getName().equals("Phyrexian Walker"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(4).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(4).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));
		assertTrue(this.game.actualState.battlefield().objects.get(4).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(0, this.game.actualState.battlefield().objects.get(4).getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(4).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(5).getName().equals("Chromatic Star"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(5).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(5).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));
		assertTrue(this.game.actualState.battlefield().objects.get(5).getTypes().contains(org.rnd.jmagic.engine.Type.CREATURE));
		assertEquals(1, this.game.actualState.battlefield().objects.get(5).getPower());
		assertEquals(1, this.game.actualState.battlefield().objects.get(5).getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(6).getName().equals("Plains"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(6).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(6).getTypes().contains(org.rnd.jmagic.engine.Type.LAND));

		// 3x Black Lotus
		assertEquals(3, this.getGraveyard(1).objects.size());

		assertTrue(this.getGraveyard(1).objects.get(0).getName().equals("Black Lotus"));
		assertEquals(1, this.getGraveyard(1).objects.get(0).getTypes().size());
		assertTrue(this.getGraveyard(1).objects.get(0).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.getGraveyard(1).objects.get(1).getName().equals("Black Lotus"));
		assertEquals(1, this.getGraveyard(1).objects.get(1).getTypes().size());
		assertTrue(this.getGraveyard(1).objects.get(1).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));

		assertTrue(this.getGraveyard(1).objects.get(2).getName().equals("Black Lotus"));
		assertEquals(1, this.getGraveyard(1).objects.get(2).getTypes().size());
		assertTrue(this.getGraveyard(1).objects.get(2).getTypes().contains(org.rnd.jmagic.engine.Type.ARTIFACT));
	}
}