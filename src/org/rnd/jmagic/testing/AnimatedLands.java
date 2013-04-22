package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class AnimatedLands extends JUnitTest
{
	@Test
	public void mutavault()
	{
		this.addDeck(BlackLotus.class, AzusaLostbutSeeking.class, Mountain.class, Mountain.class, Mutavault.class, Mutavault.class, ChaosCharm.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		// pass upkeep
		pass();
		pass();

		respondWith(getSpellAction(BlackLotus.class));
		pass();
		pass();

		respondWith(getSpellAction(AzusaLostbutSeeking.class));
		respondWith(getAbilityAction(BlackLotus.BlackLotusMana.class));
		respondWith(Color.GREEN);
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getLandAction(Mountain.class));
		GameObject mountain1 = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getLandAction(Mountain.class));
		GameObject mountain2 = this.game.physicalState.battlefield().objects.get(0);

		respondWith(getLandAction(Mutavault.class));

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mutavault"));
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).getTypes().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getSubTypes().size());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getAbilityAction(Mutavault.AnimateMutavault.class));
		respondWith(getAbilityAction((ActivatedAbility)this.game.actualState.<GameObject>get(mountain1.ID).getNonStaticAbilities().iterator().next()));
		donePlayingManaAbilities();
		pass();
		pass();

		assertTrue(this.game.actualState.currentPhase().type == Phase.PhaseType.PRECOMBAT_MAIN);

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mutavault"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getSubTypes().size() == org.rnd.jmagic.engine.SubType.getAllCreatureTypes().size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		respondWith(getTarget(Mutavault.class));
		respondWith(getAbilityAction((ActivatedAbility)this.game.actualState.<GameObject>get(mountain2.ID).getNonStaticAbilities().iterator().next()));
		donePlayingManaAbilities();
		assertTrue(this.game.actualState.currentPhase().type == Phase.PhaseType.PRECOMBAT_MAIN);
		pass();
		pass();

		// pass main
		assertTrue(this.game.actualState.currentPhase().type == Phase.PhaseType.PRECOMBAT_MAIN);
		pass();
		pass();

		// pass beginning of combat
		assertTrue(this.game.actualState.currentStep().type == Step.StepType.BEGINNING_OF_COMBAT);
		pass();
		pass();

		// declare attackers
		respondWith(pullChoice(Mutavault.class));
		pass();
		pass();

		// declare blockers
		pass();
		pass();

		// resolve combat damage
		pass();
		pass();

		// pass combat damage
		assertEquals(18, player(1).lifeTotal);
		pass();
		pass();

		// pass end of combat
		pass();
		pass();

		// main #2

		assertTrue(this.game.actualState.battlefield().objects.get(0).getName().equals("Mutavault"));
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getTypes().size());
		assertTrue(this.game.actualState.battlefield().objects.get(0).getSubTypes().size() == org.rnd.jmagic.engine.SubType.getAllCreatureTypes().size());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getPower());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getToughness());

	}
}
