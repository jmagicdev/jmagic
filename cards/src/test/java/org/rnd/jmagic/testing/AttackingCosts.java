package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class AttackingCosts extends JUnitTest
{
	@Test
	public void twoWindbornMuse()
	{
		// with two muses:
		// attack with 2 guys, then try:
		// making no mana (fail, redeclare)
		// making 3 mana (fail, redeclare)
		// making 9 mana (success -- should have to choose the order to pay the
		// two 4's, and choose the mana)
		// block with wb muse
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, Plains.class, WindbornMuse.class, WindbornMuse.class, WindbornMuse.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
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
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);

		respondWith(getSpellAction(WindbornMuse.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		pass();
		pass();
		respondWith(getSpellAction(WindbornMuse.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		pass();
		pass();

		// finish phase:
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
		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		Identified RagingGoblinA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		Identified RagingGoblinB = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		// auto-choose R
		pass();
		pass();

		// finish main phase:
		pass();
		pass();

		// beg. combat:
		pass();
		pass();

		// attackers
		respondWith(getChoice(RagingGoblinA), getChoice(RagingGoblinB));
		// auto-choose player 0 for both attackers
		// play mana abilities -- don't play any
		donePlayingManaAbilities();
		// can't pay, action is reversed, back to the beginning of declaring
		// attackers:

		respondWith(getChoice(RagingGoblinA), getChoice(RagingGoblinB));
		// auto-choose player 0 for both attackers
		// play mana abilities -- make 3 mana
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		// can't pay, action is reversed, back to the beginning of declaring
		// attackers:

		respondWith(getChoice(RagingGoblinA), getChoice(RagingGoblinB));
		// auto-choose player 0 for both attackers
		// play mana abilities -- make 9 mana
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		// pay 4:
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED));
		// pay 4:
		respondWith(getMana(Color.RED, Color.RED, Color.RED, Color.RED));

		// finish decl. attackers step:
		pass();
		pass();

		// declare blockers
		// windborn muse will block:
		respondWith(pullChoice(WindbornMuse.class));
		// the muse will block goblin b:
		respondWith(getChoice(RagingGoblinB));

		// finish decl. blockers step:
		pass();
		pass();

		// 1 damage from a goblin:
		assertEquals(19, player(0).lifeTotal);
		assertTrue(getGraveyard(1).objects.get(0).getName().equals("Raging Goblin"));
	}

	@Test
	public void windbornMuseFinallyAttack()
	{
		// with one muse:
		// attack with 3 guys, then try:
		// making no mana (fail, redeclare)
		// making 3 mana (fail, redeclare)
		// making 6 mana (success)
		// block with wb muse
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, Plains.class, WindbornMuse.class, WindbornMuse.class, WindbornMuse.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// upkeep
		pass();
		pass();

		// main phase
		respondWith(getLandAction(Plains.class));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);

		respondWith(getSpellAction(WindbornMuse.class));
		respondWith(getIntrinsicAbilityAction(SubType.PLAINS));
		donePlayingManaAbilities();
		// auto-choose WWWW
		pass();
		pass();

		// finish main:
		pass();
		pass();
		// beg. combat
		pass();
		pass();
		// declare attackers
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

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		// auto-choose R
		pass();
		pass();

		// p0: plains, muse
		// p1: lotus, lotus, goblin, goblin, goblin
		assertEquals(7, this.game.actualState.battlefield().objects.size());

		// finish main phase:
		pass();
		pass();

		// beg. combat
		pass();
		pass();

		// declare attackers:
		respondWith(pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class));
		// auto-choose player 0 for all three attackers
		// play mana abilities -- don't play any:
		donePlayingManaAbilities();
		// order costs -- auto-choose Pay 6
		// can't pay, action is reversed, back to the beginning of declaring
		// attackers:

		respondWith(pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class));
		// auto-choose player 0 for all three attackers
		// play mana abilities -- make three mana:
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		// order costs -- auto-choose Pay 6
		// can't pay, action is reversed, back to the beginning of declaring
		// attackers:

		respondWith(pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class), pullChoice(RagingGoblin.class));
		// auto-choose player 0 for all three attackers
		// play mana abilities -- make six mana:
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);
		donePlayingManaAbilities();
		// order costs -- auto-choose Pay 6
		// pay costs -- auto-choose RRRRRR

		// finish declare attackers:
		pass();
		pass();

		// declare blockers
		// windborn muse will block:
		respondWith(pullChoice(WindbornMuse.class));
		// the muse will block goblin b:
		respondWith(pullChoice(RagingGoblin.class));
		pass();
		pass();

		assertEquals(18, player(0).lifeTotal);
		assertTrue(getGraveyard(1).objects.get(0).getName().equals("Raging Goblin"));
	}

	@Test
	public void windbornMuseFinallyNoAttack()
	{
		// with one muse:
		// attack with 3 guys, then try:
		// making no mana (fail, redeclare)
		// decide i'd rather not attack
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, Plains.class, WindbornMuse.class, WindbornMuse.class, WindbornMuse.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// upkeep
		pass();
		pass();

		// main phase
		respondWith(getLandAction(Plains.class));

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.WHITE);

		respondWith(getSpellAction(WindbornMuse.class));
		respondWith(getIntrinsicAbilityAction(SubType.PLAINS));
		donePlayingManaAbilities();
		// auto-choose WWWW
		pass();
		pass();

		// finish main:
		pass();
		pass();
		// beg. combat
		pass();
		pass();
		// declare attackers
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

		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.RED);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();
		Identified RagingGoblinA = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		respondWith(getMana(Color.RED));
		pass();
		pass();
		Identified RagingGoblinB = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getSpellAction(RagingGoblin.class));
		donePlayingManaAbilities();
		// auto-choose R
		pass();
		pass();
		Identified RagingGoblinC = this.game.physicalState.battlefield().objects.get(0);

		// p0: plains, muse
		// p1: lotus, lotus, goblin, goblin, goblin
		assertEquals(7, this.game.actualState.battlefield().objects.size());

		// finish main phase:
		pass();
		pass();

		// beg. combat
		pass();
		pass();

		// declare attackers:
		respondWith(getChoice(RagingGoblinA), getChoice(RagingGoblinB), getChoice(RagingGoblinC));
		// auto-choose player 0 for all three attackers
		// play mana abilities -- don't play any:
		donePlayingManaAbilities();
		// order costs -- auto-choose Pay 6
		// can't pay, action is reversed, back to the beginning of declaring
		// attackers

		// decide i'd rather not attack:
		declareNoAttackers();

		// finish the step:
		pass();
		pass();

		// since there are no attackers, we should end up in eoc:
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.END_OF_COMBAT);

	}
}
