package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class CostModificationsTest extends JUnitTest
{
	@Test
	public void avatarOfMightNoReduction()
	{
		// Play Avatar of Might with no cost reduction
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, AvatarofMight.class, AvatarofMight.class, AvatarofMight.class, AvatarofMight.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep:
		pass();
		pass();

		// main phase:
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);

		respondWith(getSpellAction(AvatarofMight.class));
		donePlayingManaAbilities();
		// Choose GGGGGGGG:
		respondWith(getMana(Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Avatar of Might"));

		// Play it while my opponent controls 4 creatures and I control 0
	}

	@Test
	public void avatarOfMightWithReduction()
	{
		this.addDeck(PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class, PhyrexianWalker.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, AvatarofMight.class, AvatarofMight.class, AvatarofMight.class, AvatarofMight.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep:
		pass();
		pass();

		respondWith(getSpellAction(PhyrexianWalker.class));
		pass();
		pass();
		respondWith(getSpellAction(PhyrexianWalker.class));
		pass();
		pass();
		respondWith(getSpellAction(PhyrexianWalker.class));
		pass();
		pass();
		respondWith(getSpellAction(PhyrexianWalker.class));
		pass();
		pass();

		assertEquals(4, this.game.actualState.battlefield().objects.size());

		// finish main phase:
		pass();
		pass();

		// beg. combat:
		pass();
		pass();

		// attackers
		pass();
		pass();

		// end combat:
		pass();
		pass();

		// second main:
		pass();
		pass();

		// eot:
		pass();
		pass();

		// player 1's upkeep:
		pass();
		pass();

		// draw:
		pass();
		pass();

		// main:
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);

		respondWith(getSpellAction(AvatarofMight.class));
		donePlayingManaAbilities();
		// Choose GG:
		respondWith(getMana(Color.GREEN, Color.GREEN));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Avatar of Might"));
	}

	@Test
	public void costIncreases()
	{
		// sac: naturalize
		// 2 auras = lotus costs 4
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(AuraofSilence.class, AuraofSilence.class, AuraofSilence.class, AuraofSilence.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, PhyrexianWalker.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// upkeep
		pass();
		pass();

		// main phase
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		// finish main
		pass();
		pass();

		// beg. combat
		pass();
		pass();

		// attackers
		pass();
		pass();

		// end combat
		pass();
		pass();

		// main
		pass();
		pass();

		// eot
		pass();
		pass();

		// player 1: upkeep
		pass();
		pass();

		// draw
		pass();
		pass();

		// main
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(PhyrexianWalker.class));
		pass();
		pass();

		respondWith(getSpellAction(AuraofSilence.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		// auto-choose WWW
		pass();
		pass();

		respondWith(getSpellAction(AuraofSilence.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		// auto-choose WWW
		pass();
		pass();

		respondWith(getSpellAction(AuraofSilence.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		donePlayingManaAbilities();
		// auto-choose WWW
		pass();
		pass();

		// player 0's 2 lotuses, player 1's walker and 3 auras
		assertEquals(6, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Phyrexian Walker"));

		respondWith(getAbilityAction(AuraofSilence.Disenchant.class));
		respondWith(getTarget(PhyrexianWalker.class));
		pass();
		pass();

		assertTrue(getGraveyard(1).objects.get(0).getName().equals("Phyrexian Walker"));

		// finish main
		pass();
		pass();

		// beg. combat
		pass();
		pass();

		// attackers
		pass();
		pass();

		// end combat
		pass();
		pass();

		// main
		pass();
		pass();

		// eot
		pass();
		pass();

		// player 0: second upkeep
		pass();
		pass();

		// draw
		pass();
		pass();

		// main
		respondWith(getSpellAction(BlackLotus.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
	}
}
