package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class DjinnofWishesTest extends JUnitTest
{
	@Test
	public void djinnOfWishes()
	{
		this.addDeck(Forest.class, Fireball.class, Forest.class, Sprout.class, DjinnofWishes.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.STACKED);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(DjinnofWishes.class, "3UU");

		// This will cast a Sprout
		this.respondWith(this.getAbilityAction(DjinnofWishes.Wish.class));
		this.addMana("2UU");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals("Sprout", this.game.actualState.stack().objects.get(0).getName());
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Djinn of Wishes", this.game.actualState.battlefield().objects.get(0).getName());

		// This will play a Forest
		this.respondWith(this.getAbilityAction(DjinnofWishes.Wish.class));
		this.addMana("2UU");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals("Sprout", this.game.actualState.stack().objects.get(0).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Forest", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Djinn of Wishes", this.game.actualState.battlefield().objects.get(1).getName());

		// This will play a Fireball
		this.respondWith(this.getAbilityAction(DjinnofWishes.Wish.class));
		this.addMana("2UU");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		// Declare the target
		this.respondWith(this.getTarget(this.player(1)));

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals("Fireball", this.game.actualState.stack().objects.get(0).getName());
		assertEquals("Sprout", this.game.actualState.stack().objects.get(1).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Forest", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Djinn of Wishes", this.game.actualState.battlefield().objects.get(1).getName());

		this.pass();
		this.pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals("Sprout", this.game.actualState.stack().objects.get(0).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertEquals("Forest", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Djinn of Wishes", this.game.actualState.battlefield().objects.get(1).getName());

		this.pass();
		this.pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(3, this.game.actualState.battlefield().objects.size());
		assertEquals("Saproling", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals("Forest", this.game.actualState.battlefield().objects.get(1).getName());
		assertEquals("Djinn of Wishes", this.game.actualState.battlefield().objects.get(2).getName());

		// This will attempt to play a Forest and fail
		this.respondWith(this.getAbilityAction(DjinnofWishes.Wish.class));
		this.addMana("2UU");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// The player can't play the forest, so they will not be given the
		// option to.
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);
	}
}