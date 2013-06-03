package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class DragonskullSummitTest extends JUnitTest
{
	@Test
	public void m10Lands()
	{
		this.addDeck(DragonskullSummit.class, Swamp.class, DragonskullSummit.class, AzusaLostbutSeeking.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.respondWith(this.getLandAction(DragonskullSummit.class));
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());

		this.respondWith(this.getSpellAction(AzusaLostbutSeeking.class));
		this.addMana("2G");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getLandAction(Swamp.class));
		this.respondWith(this.getLandAction(DragonskullSummit.class));
		assertFalse(this.game.actualState.battlefield().objects.get(0).isTapped());

		ActivatedAbility ability = (ActivatedAbility)this.game.actualState.battlefield().objects.get(0).getNonStaticAbilities().get(0);

		this.respondWith(this.getAbilityAction(ability));

		assertTrue(this.choices.contains(Color.BLACK));
		assertTrue(this.choices.contains(Color.RED));
	}
}