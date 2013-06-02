package org.rnd.jmagic.testing;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class TriggeredAbilitiesTest extends JUnitTest
{
	@Ignore
	@Test
	public void tinStreetHooligan()
	{
		this.addDeck(Ornithopter.class, TinStreetHooligan.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(Ornithopter.class));
		pass();
		pass();

		respondWith(getSpellAction(TinStreetHooligan.class));
		addMana("GR");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
	}

	@Test
	public void triggeredAbilityStopping()
	{
		this.addDeck(TorporOrb.class, SoulWarden.class, FledglingGriffin.class, GeyserGlider.class, DryadArbor.class, DryadArbor.class, Explore.class, Explore.class);
		this.addDeck(Sprout.class, Sprout.class, Naturalize.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(Explore.class);
		castAndResolveSpell(SoulWarden.class);
		castAndResolveSpell(FledglingGriffin.class);

		// Soul Warden triggered
		assertEquals(1, this.game.actualState.stack().objects.size());
		pass();
		pass();

		castAndResolveSpell(TorporOrb.class);
		castAndResolveSpell(GeyserGlider.class);

		// Soul Warden didn't trigger
		assertEquals(0, this.game.actualState.stack().objects.size());

		respondWith(getLandAction(DryadArbor.class));

		// Noone triggered
		assertEquals(0, this.game.actualState.stack().objects.size());

		pass();
		castAndResolveSpell(Sprout.class);

		// Noone triggered
		assertEquals(0, this.game.actualState.stack().objects.size());

		pass();
		castAndResolveSpell(Naturalize.class); // destroy torpor orb
		respondWith(getLandAction(DryadArbor.class));

		// Everyone triggered
		respondArbitrarily();
		assertEquals(3, this.game.actualState.stack().objects.size());

		pass();
		castAndResolveSpell(Sprout.class);

		// Soul Warden triggered
		assertEquals(4, this.game.actualState.stack().objects.size());
	}

	@Test
	public void werewolves()
	{
		this.addDeck(TormentedPariah.class, TormentedPariah.class, TormentedPariah.class, TormentedPariah.class, TormentedPariah.class, TormentedPariah.class, TormentedPariah.class, TormentedPariah.class, TormentedPariah.class, TormentedPariah.class, TormentedPariah.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, TormentedPariah.class, TormentedPariah.class, Sprout.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(TormentedPariah.class);
		goToPhase(Phase.PhaseType.ENDING);

		// next turn: it should not transform, since a spell was cast last turn
		// (Tormented Pariah)
		goToStep(Step.StepType.UPKEEP);
		assertEquals(0, this.game.actualState.stack().objects.size());
		goToStep(Step.StepType.CLEANUP);
		respondWith(pullChoice(Plains.class));

		// next turn: it should transform, since no spells were cast last turn
		goToStep(Step.StepType.UPKEEP);
		assertEquals(1, this.game.actualState.stack().objects.size());
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		assertEquals("Rampaging Werewolf", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(Color.RED, this.game.actualState.battlefield().objects.get(0).getColors().iterator().next());
		castAndResolveSpell(TormentedPariah.class);
		pass();
		castAndResolveSpell(Sprout.class);

		// next turn: it should not transform back, since the two spells weren't
		// cast by the same player
		goToStep(Step.StepType.UPKEEP);
		assertEquals(0, this.game.actualState.stack().objects.size());
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		castAndResolveSpell(TormentedPariah.class);
		castAndResolveSpell(TormentedPariah.class);

		// next turn: it should transform back, since two spells were cast by
		// the same player
		goToStep(Step.StepType.UPKEEP);
		assertEquals(1, this.game.actualState.stack().objects.size());
		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		assertEquals("Tormented Pariah", this.game.actualState.battlefield().objects.get(4).getName());
	}
}
