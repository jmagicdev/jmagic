package org.rnd.jmagic.cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rnd.jmagic.engine.Answer;
import org.rnd.jmagic.engine.Color;
import org.rnd.jmagic.engine.GameTypes;
import org.rnd.jmagic.engine.SubType;
import org.rnd.jmagic.testing.JUnitTest;

@RunWith(JUnit4.class)
public class AcademyResearchersTest extends JUnitTest {
	@Test
	public void academyResearchers() {
		this.addDeck(BlackLotus.class, BlackLotus.class, SeasClaim.class,
				UnholyStrength.class, AcademyResearchers.class,
				AcademyResearchers.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class,
				Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Plains.class));

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(AcademyResearchers.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Make sure the trigger is on the stack, then resolve it
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();

		this.respondWith(Answer.YES);
		this.respondWith(this.pullChoice(SeasClaim.class));

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName()
				.equals("Academy Researchers"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(0)
				.getAttachments().size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0)
				.getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0)
				.getToughness());
		assertEquals(3, this.getHand(0).objects.size());

		this.respondWith(this.getSpellAction(AcademyResearchers.class));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.BLUE, Color.BLUE));
		this.pass();
		this.pass();

		// Make sure the trigger is on the stack, then resolve it
		assertEquals(1, this.game.actualState.stack().objects.size());
		this.pass();
		this.pass();

		this.respondWith(Answer.YES);
		this.respondWith(this.pullChoice(UnholyStrength.class));

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName()
				.equals("Unholy Strength"));

		assertTrue(this.game.actualState.battlefield().objects.get(1).getName()
				.equals("Academy Researchers"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(1)
				.getAttachments().size());
		assertEquals(4, this.game.actualState.battlefield().objects.get(1)
				.getPower());
		assertEquals(3, this.game.actualState.battlefield().objects.get(1)
				.getToughness());

		assertTrue(this.game.actualState.battlefield().objects.get(2).getName()
				.equals("Academy Researchers"));
		assertEquals(0, this.game.actualState.battlefield().objects.get(2)
				.getAttachments().size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2)
				.getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(2)
				.getToughness());

		assertEquals(1, this.getHand(0).objects.size());

		this.respondWith(this.getSpellAction(SeasClaim.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertTrue(this.game.actualState.battlefield().objects.get(4).getName()
				.equals("Plains"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(4)
				.getSubTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(4)
				.getSubTypes().contains(org.rnd.jmagic.engine.SubType.ISLAND));
	}
}
