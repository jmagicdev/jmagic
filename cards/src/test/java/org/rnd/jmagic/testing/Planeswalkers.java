package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class Planeswalkers extends JUnitTest
{
	@Test
	public void abilitiesOnlyFromInPlay()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class);
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

		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(ChandraNalaar.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Chandra Nalaar"));
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getAbilityAction(ChandraNalaar.Ping.class));
		respondWith(getTarget(player(1)));
		assertEquals(7, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(0, this.choices.size());
		pass();
		pass();

		assertEquals(19, player(1).lifeTotal);

		// main
		// 4 spells in hand, no abilities on chandra
		assertEquals(4, this.choices.size());
		pass();
		pass();
		// bgn combat
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

		// player 1's turn
		// upkeep
		pass();
		pass();
		// draw
		pass();
		pass();
		// main
		pass();
		assertEquals(0, this.choices.size());
		pass();
		// bgn combat
		pass();
		pass();
		// attackers
		pass();
		pass();
		// end combat
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(ArcanistheOmnipotent.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		pass();
		pass();

		// main
		pass();
		pass();
		// eot
		pass();
		pass();

		// player 0's turn
		// upkeep
		pass();
		pass();
		// draw
		pass();
		pass();

		respondWith(getAbilityAction(ChandraNalaar.Smack.class));
		respondWith(3);
		// only one choice of target -- Arcanis
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Chandra Nalaar"));
		assertEquals(4, this.game.actualState.battlefield().objects.get(1).counters.size());
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Arcanis the Omnipotent"));
		assertEquals(3, this.game.actualState.battlefield().objects.get(0).getDamage());

	}

	@Test
	public void cantHitOwnPlaneswalker()
	{
		this.addDeck(LightningBolt.class, ChandraNalaar.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(ChandraNalaar.class, "3RR");

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		// at this point I should be at 17, and therefore NOT being given the
		// option to hit my own pw:
		assertEquals(17, player(0).lifeTotal);
	}

	@Test
	public void loyaltyCountersForPlaneswalkersOnly()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class, ArcanistheOmnipotent.class);
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

		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(ChandraNalaar.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Chandra Nalaar"));
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getAbilityAction(ChandraNalaar.Ping.class));
		respondWith(getTarget(player(1)));
		assertEquals(7, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(0, this.choices.size());
		pass();
		pass();

		assertEquals(19, player(1).lifeTotal);

		// main
		pass();
		pass();
		// bgn combat
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

		// player 1's turn
		// upkeep
		pass();
		pass();
		// draw
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getSpellAction(ArcanistheOmnipotent.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.BLUE);
		donePlayingManaAbilities();
		pass();
		pass();

		// main
		pass();
		assertEquals(0, this.choices.size());
		pass();
		// bgn combat
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

		// player 0's turn
		// upkeep
		pass();
		pass();
		// draw
		pass();
		pass();

		respondWith(getAbilityAction(ChandraNalaar.Ping.class));
		respondWith(getTarget(player(1)));
		assertTrue(this.game.actualState.battlefield().objects.get(1).getName().equals("Chandra Nalaar"));
		assertEquals(8, this.game.actualState.battlefield().objects.get(1).counters.size());
		assertEquals(0, this.choices.size());
		pass();
		pass();

		assertEquals(18, player(1).lifeTotal);

		// main
		pass();
		pass();
		// bgn combat
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

		// player 1's turn
		// upkeep
		pass();
		pass();
		// draw
		pass();
		pass();
		// main
		pass();
		pass();
		// bgn combat
		pass();
		pass();
		// attackers
		declareNoAttackers();
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

		// player 0's turn
		// upkeep
		pass();
		pass();
		// draw
		pass();
		pass();

		respondWith(getAbilityAction(ChandraNalaar.Boom.class));
		respondWith(getTarget(player(1)));
		assertEquals(1, this.game.actualState.battlefield().objects.size());
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		assertEquals(8, player(1).lifeTotal);
	}

	@Test
	public void planeswalkerDamageRedirection()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Shock.class, Shock.class, Shock.class, Shock.class, Shock.class);
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

		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(ChandraNalaar.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Chandra Nalaar"));
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).counters.size());

		respondWith(getAbilityAction(ChandraNalaar.Ping.class));
		respondWith(getTarget(player(1)));
		assertEquals(7, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(0, this.choices.size());
		pass();
		pass();

		assertEquals(19, player(1).lifeTotal);

		// main
		// 4 spells in hand, no abilities on chandra
		assertEquals(4, this.choices.size());
		pass();
		pass();
		// bgn combat
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

		// player 1's turn
		// upkeep
		pass();
		pass();
		// draw
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(player(0)));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Chandra Nalaar
		assertEquals(7, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(20, player(0).lifeTotal);

		// WHY YES, I WOULD LOVE TO REDIRECT THIS SHOCK TO YOU, MISS NALAAR.
		respondWith(Answer.YES);

		// Chandra Nalaar
		assertEquals(5, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(20, player(0).lifeTotal);

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(player(0)));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		// Chandra Nalaar
		assertEquals(5, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(20, player(0).lifeTotal);

		// This shock can go straight to face, thanks anyway Miss Nalaar
		respondWith(Answer.NO);

		// Chandra Nalaar
		assertEquals(5, this.game.actualState.battlefield().objects.get(0).counters.size());
		assertEquals(18, player(0).lifeTotal);
	}

	@Test
	public void planeswalkerUniquenessRule()
	{
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class, ChandraNalaar.class);
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

		respondWith(getSpellAction(ChandraNalaar.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Chandra Nalaar"));

		// main
		pass();
		pass();
		// bgn combat
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

		// player 1's turn
		// upkeep
		pass();
		pass();
		// draw
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());

		respondWith(getSpellAction(ChandraNalaar.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED, Color.RED));

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		assertEquals("Chandra Nalaar", this.game.actualState.battlefield().objects.get(0).getName());

		// Resolve Chandra
		pass();
		pass();

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}
}
