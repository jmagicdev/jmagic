package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.sanitized.*;
import org.rnd.jmagic.testing.*;

public class HighTideTest extends JUnitTest
{
	@Test
	public void highTide()
	{
		this.addDeck(AzusaLostbutSeeking.class, HighTide.class, HighTide.class, HighTide.class, Island.class, Island.class, Island.class);
		this.addDeck(Plains.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(AzusaLostbutSeeking.class, "2G");

		this.respondWith(this.getLandAction(Island.class));
		this.respondWith(this.getLandAction(Island.class));
		this.respondWith(this.getLandAction(Island.class));

		int[] islands = new int[2];

		// Tap for U
		SanitizedCastSpellOrActivateAbilityAction ability = this.getAbilityAction(Game.IntrinsicManaAbility.class);
		islands[0] = ability.actOnID;
		this.respondWith(ability);

		assertEquals(1, this.player(0).pool.converted());

		this.respondWith(this.getSpellAction(HighTide.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(0, this.player(0).pool.converted());

		ability = null;
		do
		{
			ability = this.getAbilityAction(Game.IntrinsicManaAbility.class);
			this.pullChoice(ability);
			if(islands[0] != ability.actOnID)
				break;
		}
		while(true);
		islands[1] = ability.actOnID;
		this.respondWith(ability);

		assertEquals(2, this.player(0).pool.converted());

		this.respondWith(this.getSpellAction(HighTide.class));
		this.donePlayingManaAbilities();
		this.respondWith(this.getMana(Color.BLUE));
		this.pass();
		this.pass();

		assertEquals(1, this.player(0).pool.converted());

		this.respondWith(this.getSpellAction(HighTide.class));
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		assertEquals(0, this.player(0).pool.converted());

		ability = null;
		do
		{
			ability = this.getAbilityAction(Game.IntrinsicManaAbility.class);
			this.pullChoice(ability);
			if(islands[0] != ability.actOnID && islands[1] != ability.actOnID)
				break;
		}
		while(true);
		this.respondWith(ability);

		// Stack the three triggers
		this.respondArbitrarily();

		assertEquals(4, this.player(0).pool.converted());
	}
}