package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

public class ObjectVisibilityTest extends JUnitTest
{
	@Test
	public void crownOfConvergence()
	{
		this.addDeck(Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, Mountain.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class, MoggFanatic.class);
		this.addDeck(PlagueBoiler.class, LightningBolt.class, BoggartRamGang.class, HearthfireHobgoblin.class, HearthfireHobgoblin.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, CrownofConvergence.class);
		startGame(new Stacked());

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getIntrinsicAbilityAction(SubType.MOUNTAIN));
		donePlayingManaAbilities();
		pass();
		pass();

		// End the turn
		// Pass 1st Main Phase
		pass();
		pass();
		// Pass Beginning of Combat
		pass();
		pass();
		// Pass Declare Attackers
		pass();
		pass();
		// Pass End of Combat
		pass();
		pass();
		// Pass 2nd Main Phase
		pass();
		pass();
		// Pass End of Turn
		pass();
		pass();

		// pass upkeep & draw
		pass();
		pass();
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		assertEquals(0, getLibrary(1).objects.get(0).getVisibleTo().size());

		respondWith(getSpellAction(CrownofConvergence.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.GREEN));

		assertEquals(0, getLibrary(1).objects.get(0).getVisibleTo().size());

		pass();
		pass();

		GameObject hh = this.game.actualState.get(getLibrary(1).objects.get(0).ID);

		assertEquals(2, hh.getVisibleTo().size());
		assertEquals(2, hh.getColors().size());
		assertTrue(hh.getName().equals("Hearthfire Hobgoblin"));

		respondWith(getSpellAction(HearthfireHobgoblin.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "(R)(R)(R)"));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		assertTrue(getLibrary(1).objects.get(0).getName().equals("Hearthfire Hobgoblin"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Hearthfire Hobgoblin"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertTrue(this.game.actualState.battlefield().objects.get(5).getName().equals("Mogg Fanatic"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(5).getPower());

		respondWith(getAbilityAction(CrownofConvergence.Dig.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.WHITE));
		pass();
		pass();

		assertTrue(getLibrary(1).objects.get(0).getName().equals("Boggart Ram-Gang"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Hearthfire Hobgoblin"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertTrue(this.game.actualState.battlefield().objects.get(5).getName().equals("Mogg Fanatic"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(5).getPower());

		respondWith(getAbilityAction(CrownofConvergence.Dig.class));
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(getLibrary(1).objects.get(0).getName().equals("Lightning Bolt"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Hearthfire Hobgoblin"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertTrue(this.game.actualState.battlefield().objects.get(5).getName().equals("Mogg Fanatic"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(5).getPower());

		respondWith(getAbilityAction(CrownofConvergence.Dig.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.WHITE));
		pass();
		pass();

		assertTrue(getLibrary(1).objects.get(0).getName().equals("Plague Boiler"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Hearthfire Hobgoblin"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Mogg Fanatic"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(3).getPower());

		respondWith(getAbilityAction(CrownofConvergence.Dig.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.GREEN, Color.WHITE));
		pass();
		pass();

		assertTrue(getLibrary(1).objects.get(0).getName().equals("Hearthfire Hobgoblin"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Hearthfire Hobgoblin"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Mogg Fanatic"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(3).getPower());

		respondWith(getAbilityAction(CrownofConvergence.Dig.class));
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(getLibrary(1).objects.get(0).getName().equals("Boggart Ram-Gang"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Hearthfire Hobgoblin"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getPower());
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Mogg Fanatic"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(3).getPower());

	}
}
