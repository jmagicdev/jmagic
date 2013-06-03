package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class EmpyrialArchangelTest extends JUnitTest
{
	@Test
	public void empyrialArchangelCombatAndNoncombatDamage()
	{
		// This test makes sure both combat and noncombat damage are
		// redirected
		// to the angel
		this.addDeck(EmpyrialArchangel.class, Shock.class, Mountain.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Mountain.class));

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(EmpyrialArchangel.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.GREEN, Color.GREEN, Color.GREEN, Color.WHITE, Color.WHITE, Color.BLUE, Color.BLUE, Color.BLUE));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.respondWith(this.getIntrinsicAbilityAction(SubType.MOUNTAIN));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		assertEquals(20, this.player(0).lifeTotal);
		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Empyrial Archangel"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getDamage());

		// End the turn
		// Pass Main
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
		// Pass 2nd Main
		this.pass();
		this.pass();
		// Pass EoT
		this.pass();
		this.pass();

		// Pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Pass Main
		this.pass();
		this.pass();

		// Pass Beginning of Combat
		this.pass();
		this.pass();

		// Declare Attackers (all 6 goblins attack)
		this.respondWith(this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class));
		this.pass();
		this.pass();

		// Declare Blockers (none)
		this.declareNoBlockers();
		this.pass();
		this.pass();

		// Resolve Combat Damage
		this.pass();
		this.pass();

		// no combat damage was dealt to player
		assertEquals(20, this.player(0).lifeTotal);
		assertEquals("Empyrial Archangel", this.game.actualState.battlefield().objects.get(6).getName());
		assertEquals(6, this.game.actualState.battlefield().objects.get(6).getDamage());
	}

	@Test
	public void empyrialArchangelTwoAngelsReplacingOneCombatDamage()
	{
		// Mana needed for Player 0 - 2U (Meditate) + 8GGWWWWUU (2 Angels)
		// The lotuses will produce UUU, UUU, GGG, WWW, WWW, WWW
		// The plains will produce the final mana needed for the angels
		// The swamp is filler for the deck
		this.addDeck(Swamp.class, EmpyrialArchangel.class, EmpyrialArchangel.class, Plains.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, BlackLotus.class, Meditate.class);
		this.addDeck(BlackLotus.class, BlackLotus.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class);
		this.startGame(GameTypes.STACKED);

		// This test makes sure that all of combat damage is dealt to a
		// single
		// angel, even if there are two in play
		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		// pass upkeep
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.BLUE);

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.GREEN);

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.WHITE);

		this.respondWith(this.getSpellAction(Meditate.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLUE, Color.BLUE, Color.BLUE));
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Plains.class));

		this.respondWith(this.getIntrinsicAbilityAction(SubType.PLAINS));

		this.respondWith(this.getSpellAction(EmpyrialArchangel.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.GREEN, Color.GREEN, Color.BLUE));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(EmpyrialArchangel.class));
		this.donePlayingManaAbilities();
		// Auto-choose the following: #W#W#W#W#W#G#U#U
		this.pass();
		this.pass();

		// End the turn
		// Pass Main
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
		// Pass 2nd Main
		this.pass();
		this.pass();
		// Pass EoT
		this.pass();
		this.pass();

		// Pass upkeep & draw
		this.pass();
		this.pass();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(BlackLotus.class));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.respondWith(this.getAbilityAction(BlackLotus.BlackLotusMana.class));
		this.respondWith(Color.RED);
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.RED));
		this.pass();
		this.pass();

		this.respondWith(this.getSpellAction(RagingGoblin.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// Pass Main
		this.pass();
		this.pass();

		// Pass Beginning of Combat
		this.pass();
		this.pass();

		// Declare Attackers (all 6 goblins attack)
		this.respondWith(this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class), this.pullChoice(RagingGoblin.class));
		this.pass();
		this.pass();

		// Declare Blockers (none)
		this.declareNoBlockers();
		this.pass();
		this.pass();

		// Choose either replacement effect to apply (there should be two)
		assertEquals(2, this.choices.size());
		this.respondWith(this.getDamageAssignmentReplacement("All damage that would be dealt to you is dealt to Empyrial Archangel instead"));

		// 6x Raging Goblin, 2x Empyrial Archangel, 1x Plains
		assertEquals(9, this.game.actualState.battlefield().objects.size());
		assertTrue(this.game.actualState.battlefield().objects.get(6).getName().equals("Empyrial Archangel"));
		assertTrue(this.game.actualState.battlefield().objects.get(7).getName().equals("Empyrial Archangel"));

		// Assert that one of the angels took all the damage, and the other
		// took
		// none

		assertTrue((this.game.actualState.battlefield().objects.get(6).getDamage() == 6 && this.game.actualState.battlefield().objects.get(7).getDamage() == 0) || (this.game.actualState.battlefield().objects.get(6).getDamage() == 0 && this.game.actualState.battlefield().objects.get(7).getDamage() == 6));
	}
}