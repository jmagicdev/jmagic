package org.rnd.jmagic.cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.rnd.jmagic.engine.GameTypes;
import org.rnd.jmagic.engine.Phase;
import org.rnd.jmagic.testing.JUnitTest;

@RunWith(JUnit4.class)
public class BlazingTorchTest extends JUnitTest
{
	@Test
	public void blazingTorch()
	{
		this.addDeck(BlazingTorch.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(BlazingTorch.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);

		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		this.castAndResolveSpell(RagingGoblin.class, "R");
		this.castAndResolveSpell(BlazingTorch.class, "1");

		this.respondWith(this.getAbilityAction(org.rnd.jmagic.abilities.keywords.Equip.EquipAbility.class));
		// auto-target goblin
		this.addMana("1");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.respondWith(this.getAbilityAction(BlazingTorch.ThrowTorch.class));
		this.respondWith(this.getTarget(RagingGoblin.class));
		assertEquals(1, this.getGraveyard(0).objects.size());
		assertEquals("Blazing Torch", this.getGraveyard(0).objects.get(0).getName());
		assertTrue(this.game.actualState.battlefield().objects.get(0).isTapped());

		this.pass();
		this.pass();
		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}
}
