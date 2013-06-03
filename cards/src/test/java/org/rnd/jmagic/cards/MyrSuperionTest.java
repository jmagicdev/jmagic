package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class MyrSuperionTest extends JUnitTest
{
	@Test
	public void myrSuperion()
	{
		this.addDeck(MyrSuperion.class, AzusaLostbutSeeking.class, Plains.class, Plains.class, GreenweaverDruid.class, ChaosCharm.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		respondWith(getLandAction(Plains.class));
		GameObject plains1 = this.game.actualState.battlefield().objects.get(0);
		castAndResolveSpell(AzusaLostbutSeeking.class);
		respondWith(getLandAction(Plains.class));
		GameObject plains2 = this.game.actualState.battlefield().objects.get(0);

		respondWith(getSpellAction(MyrSuperion.class));
		respondWith(getAbilityAction((ActivatedAbility)this.game.actualState.<GameObject>get(plains1.ID).getNonStaticAbilities().iterator().next()));
		respondWith(getAbilityAction((ActivatedAbility)this.game.actualState.<GameObject>get(plains2.ID).getNonStaticAbilities().iterator().next()));
		donePlayingManaAbilities();
		assertEquals(this.game.actualState.stack().objects.size(), 0);
		assertEquals(player(0).pool.size(), 0);

		castAndResolveSpell(GreenweaverDruid.class);
		GameObject druid = this.game.actualState.battlefield().objects.get(0);

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(GreenweaverDruid.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction((ActivatedAbility)this.game.actualState.<GameObject>get(druid.ID).getNonStaticAbilities().iterator().next()));
		assertEquals(player(0).pool.size(), 2);
		respondWith(getSpellAction(MyrSuperion.class));
		donePlayingManaAbilities();
		assertEquals(this.game.actualState.stack().objects.size(), 1);
		assertEquals(player(0).pool.size(), 0);
	}
}