package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class DrawDuringManaAbility extends JUnitTest
{
	@Test
	public void chromaticSphere()
	{
		this.addDeck(ChromaticSphere.class, ChromaticSphere.class, ChromaticSphere.class, ChromaticSphere.class, ChromaticSphere.class, ChromaticSphere.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(ChromaticSphere.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE));
		pass();
		pass();

		respondWith(getAbilityAction(ChromaticSphere.Filter.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE));
		respondWith(Color.BLUE);

		respondWith(getSpellAction(ChromaticSphere.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE));
		pass();
		pass();

		respondWith(getAbilityAction(ChromaticSphere.Filter.class));
		donePlayingManaAbilities();
		respondWith(Color.RED);

		respondWith(getSpellAction(ChromaticSphere.class));
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(0, player(0).pool.converted());
		assertEquals(5, getHand(0).objects.size());
		assertEquals(3, getGraveyard(0).objects.size());
		assertEquals(3, getLibrary(0).objects.size());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
	}
}
