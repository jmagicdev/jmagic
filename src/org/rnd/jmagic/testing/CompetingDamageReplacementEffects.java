package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class CompetingDamageReplacementEffects extends JUnitTest
{
	@Test
	public void aweStrikeAndVigor()
	{
		this.addDeck(Vigor.class, WildGriffin.class, WildGriffin.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Vigor.class, AirElemental.class, SilklashSpider.class, AweStrike.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Vigor.class));
		addMana("3GGG");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(WildGriffin.class));
		addMana("2W");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(WildGriffin.class));
		addMana("2W");
		donePlayingManaAbilities();
		pass();
		pass();

		goToPhase(Phase.PhaseType.BEGINNING);
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Vigor.class));
		addMana("3GGG");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(AirElemental.class));
		addMana("3UU");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(SilklashSpider.class));
		addMana("3GG");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(AweStrike.class));
		respondWith(getTarget(SilklashSpider.class));
		addMana("W");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(SilklashSpider.Hurricane.class));
		respondWith(3);
		addMana("3GG");
		donePlayingManaAbilities();
		pass();
		pass();

		// player 1 should be asked to choose whether to apply vigor to this
		// damage or to apply awe strike to this damage:
		assertEquals(2, this.choices.size());
		respondWith(getDamageAssignmentReplacement("If damage would be dealt to a creature you control other than Vigor, prevent that damage. Put a +1/+1 counter on that creature for each 1 damage prevented this way."));
		// player 0 gets the same choices for his creatures
		assertEquals(2, this.choices.size());
		respondWith(getDamageAssignmentReplacement("The next time target creature would deal damage this turn, prevent that damage.  You gain life equal to the damage prevented this way."));

		// player 1 gained life
		assertEquals(26, player(1).lifeTotal);
		assertEquals(20, player(0).lifeTotal);

		// player 1's air elemental should have some counters
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Air Elemental"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(1).counters.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(1).getDamage());
		assertTrue(this.game.actualState.battlefield().objects.get(3).getName().equals("Wild Griffin"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).counters.size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(3).getDamage());
	}
}
