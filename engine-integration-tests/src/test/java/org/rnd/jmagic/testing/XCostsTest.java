package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class XCostsTest extends JUnitTest
{
	@Test
	public void belbesArmor()
	{
		this.addDeck(BelbesArmor.class, MoggFanatic.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
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

		respondWith(getSpellAction(BelbesArmor.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(MoggFanatic.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getAbilityAction(BelbesArmor.Reinforce.class));
		respondWith(2);
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(2, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mogg Fanatic"));
		assertTrue(this.game.actualState.battlefield().objects.get(0).getPower() == -1);
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getToughness());
	}

	@Test
	public void blazeReduced()
	{

		this.addDeck(RubyMedallion.class, RubyMedallion.class, RubyMedallion.class, Blaze.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(RubyMedallion.class, "2");
		castAndResolveSpell(RubyMedallion.class, "2");
		castAndResolveSpell(RubyMedallion.class, "2");

		respondWith(getSpellAction(Blaze.class));
		respondWith(7);
		respondWith(getTarget(player(1)));
		addMana("4R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(13, player(1).lifeTotal);

	}

	@Test
	public void consumeSpirit()
	{

		this.addDeck(ConsumeSpirit.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(ConsumeSpirit.class));
		respondWith(7);
		respondWith(getTarget(player(1)));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();

		// Cost is 1BBBBBBBB (that's eight black), which BBBBBBRRR doesn't pay
		// game state reverses:
		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(7, this.choices.size());

		respondWith(getSpellAction(ConsumeSpirit.class));
		respondWith(5);
		respondWith(getTarget(player(1)));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.BLACK, Color.RED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		// You shouldn't have any actions since all your spells are artifacts
		assertEquals(0, this.choices.size());
		assertEquals(7, this.game.actualState.stack().objects.get(0).getConvertedManaCost());
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(2, getGraveyard(0).objects.get(0).getConvertedManaCost());
		// Three black lotuses
		assertEquals(3, this.choices.size());

		assertEquals(25, player(0).lifeTotal);
		assertEquals(15, player(1).lifeTotal);

		assertEquals(2, player(0).pool.converted());

	}

	@Test
	public void consumeSpiritReduced()
	{
		this.addDeck(ConsumeSpirit.class, JetMedallion.class, JetMedallion.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(JetMedallion.class, "2");
		castAndResolveSpell(JetMedallion.class, "2");

		respondWith(getSpellAction(ConsumeSpirit.class));
		respondWith(7);
		respondWith(getTarget(player(1)));
		addMana("1BBBBBB");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(13, player(1).lifeTotal);
	}

	@Test
	public void playableXSpell()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Blaze.class);
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(Blaze.class));
		respondWith(7);
		respondWith(getTarget(player(1)));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.BLUE, Color.BLACK, Color.BLUE, Color.BLACK, Color.BLUE));
		assertEquals(8, this.game.actualState.stack().objects.get(0).getConvertedManaCost());
		pass();
		pass();

		assertEquals(20, player(0).lifeTotal);
		assertEquals(13, player(1).lifeTotal);

		assertEquals(1, player(0).pool.converted());

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(1, getGraveyard(0).objects.get(0).getConvertedManaCost());

	}

	@Test
	public void unplayableXSpell()
	{

		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Blaze.class);
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

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		// Attempt to pay more than you can
		respondWith(getSpellAction(Blaze.class));
		respondWith(10);
		respondWith(getTarget(player(1)));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);
		assertEquals(0, player(0).pool.converted());

		// Can't play this one either
		respondWith(getSpellAction(Blaze.class));
		respondWith(9);
		respondWith(getTarget(player(1)));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLACK);
		donePlayingManaAbilities();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(20, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);
		assertEquals(0, player(0).pool.converted());

		// There should be 3 Black Lotus sacs, 3 Play Black Lotuses, and 1 Play
		// Blaze
		assertEquals(7, this.choices.size());

	}

	@Test
	public void xEqualsDie()
	{
		xEqualsN(41);
	}

	/**
	 * Resolves a {@link Blaze} with X = <code>n</code> at player 1 and asserts
	 * that their life total is the correct value.
	 */
	public void xEqualsN(int n)
	{
		this.addDeck(Blaze.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Blaze.class));
		respondWith(n);
		respondWith(getTarget(player(1)));
		// haven't figured out why quite yet, but adding "(0)" to your pool
		// breaks things.
		addMana(n == 0 ? "R" : ("(" + n + ")R"));
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(20 - n, player(1).lifeTotal);
	}

	@Test
	public void xEqualsOne()
	{
		xEqualsN(1);
	}

	@Test
	public void xEqualsZero()
	{
		xEqualsN(0);
	}
}
