package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class TeferiMageofZhalfirTest extends JUnitTest
{
	@Test
	public void teferi()
	{
		this.addDeck(TeferiMageofZhalfir.class, GrizzlyBears.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, ElvishWarrior.class, BenalishKnight.class, LightningBolt.class, LightningBolt.class, Plains.class, Plains.class, Plains.class);
		this.startGame(new Stacked());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.pass();

		// Player 1 has 3 choices (knight, bolt, bolt)
		assertEquals(3, this.choices.size());
		this.respondWith(this.getSpellAction(LightningBolt.class));
		this.respondWith(this.getTarget(this.player(0)));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();

		// Respond with Teferi
		assertEquals(1, this.choices.size());
		this.castAndResolveSpell(TeferiMageofZhalfir.class, "2UUU");

		// Resolve the bolt
		this.pass();
		this.pass();

		this.pass();

		// Player 1 has no actions
		assertEquals(0, this.choices.size());

		// Player 1's turn
		this.goToPhase(Phase.PhaseType.BEGINNING);
		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Player 1 has 7 actions
		assertEquals(7, this.choices.size());

		this.respondWith(this.getSpellAction(ElvishWarrior.class));
		this.addMana("GG");
		this.donePlayingManaAbilities();

		// Player 1 has no actions
		assertEquals(0, this.choices.size());
		this.pass();

		// Player 0 has 1 action
		assertEquals(1, this.choices.size());
		this.castAndResolveSpell(GrizzlyBears.class, "1G");
	}
}