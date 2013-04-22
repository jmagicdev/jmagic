package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class LinkedAbilities extends JUnitTest
{
	@Test
	public void chromeMox()
	{
		this.addDeck(ChromeMox.class, GrizzlyBears.class, PullfromEternity.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(ChromeMox.class));
		pass();
		pass();

		// Resolve Chrome Mox's CIP ability
		pass();
		pass();

		respondWith(Answer.YES);
		respondWith(pullChoice(GrizzlyBears.class));

		castAndResolveSpell(PullfromEternity.class, "W");

		{
			assertEquals(0, player(0).pool.converted());
			assertEquals(5, this.choices.size());
			java.util.Set<org.rnd.jmagic.sanitized.SanitizedPlayerAction> actions = this.choices.getAll(org.rnd.jmagic.sanitized.SanitizedPlayerAction.class);
			assertEquals(5, actions.size());
			boolean failedOnce = false;
			for(org.rnd.jmagic.sanitized.SanitizedPlayerAction action: actions)
				if(org.rnd.jmagic.sanitized.SanitizedPlayLandAction.class != action.getClass())
					if(failedOnce)
						fail("Too many actions found");
					else
						failedOnce = true;
			if(!failedOnce)
				fail("Chrome Mox action not found");
		}

		respondWith(getAbilityAction(ChromeMox.ImprintMana.class));

		{
			assertEquals(0, player(0).pool.converted());
			assertEquals(5, this.choices.size());
			java.util.Set<org.rnd.jmagic.sanitized.SanitizedPlayerAction> actions = this.choices.getAll(org.rnd.jmagic.sanitized.SanitizedPlayerAction.class);
			assertEquals(5, actions.size());
			boolean failedOnce = false;
			for(org.rnd.jmagic.sanitized.SanitizedPlayerAction action: actions)
				if(org.rnd.jmagic.sanitized.SanitizedPlayLandAction.class != action.getClass())
					if(failedOnce)
						fail("Too many actions found");
					else
						failedOnce = true;
			if(!failedOnce)
				fail("Chrome Mox action not found");
		}

	}

	@Test
	public void hoardingDragon()
	{
		this.addDeck(BlackLotus.class, HoardingDragon.class, Terminate.class, GrizzlyBears.class, Shock.class, Shock.class, Shock.class, Shock.class);
		this.addDeck(Shock.class, Shock.class, Shock.class, Shock.class, Shock.class, Shock.class, Shock.class);
		startGame(GameType.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(HoardingDragon.class);

		// dragon's trigger:
		pass();
		pass();
		respondWith(Answer.YES);
		respondWith(pullChoice(BlackLotus.class));

		assertEquals(1, this.game.actualState.exileZone().objects.size());

		castAndResolveSpell(Terminate.class);

		// dragon's other trigger:
		pass();
		pass();
		respondWith(Answer.YES);
		assertEquals("Black Lotus", getHand(0).objects.iterator().next().getName());
	}

	@Test
	public void multiplyLinkedAbilities()
	{
		this.addDeck(GauntletofPower.class, Plains.class, Forest.class, Overgrowth.class, LlanowarElves.class, MoggFanatic.class, AzusaLostbutSeeking.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(AzusaLostbutSeeking.class);
		respondWith(getLandAction(Plains.class));
		respondWith(getLandAction(Forest.class));
		castAndResolveSpell(Overgrowth.class, Plains.class);
		castAndResolveSpell(LlanowarElves.class);
		castAndResolveSpell(MoggFanatic.class);

		GameObject moggFanatic = this.game.actualState.battlefield().objects.get(0);
		assertEquals(1, moggFanatic.getPower());
		assertEquals(1, moggFanatic.getToughness());

		GameObject llanowarElves = this.game.actualState.battlefield().objects.get(1);
		assertEquals(1, llanowarElves.getPower());
		assertEquals(1, llanowarElves.getToughness());

		castAndResolveSpell(GauntletofPower.class);
		respondWith(Color.GREEN);

		moggFanatic = this.game.actualState.battlefield().objects.get(1);
		assertEquals(1, moggFanatic.getPower());
		assertEquals(1, moggFanatic.getToughness());

		llanowarElves = this.game.actualState.battlefield().objects.get(2);
		assertEquals(2, llanowarElves.getPower());
		assertEquals(2, llanowarElves.getToughness());

		// Make sure mana from Overgrowth doesn't trigger Gauntlet of Power
		respondWith(getIntrinsicAbilityAction(SubType.PLAINS));
		// W, G, G
		assertEquals(3, player(0).pool.converted());

		// Make sure mana from Forest does trigger Gauntlet of Power
		respondWith(getIntrinsicAbilityAction(SubType.FOREST));
		// W, G, G, G, G
		assertEquals(5, player(0).pool.converted());
	}

	@Test
	public void voiceOfAll()
	{
		this.addDeck(VoiceofAll.class, GrizzlyBears.class, GrizzlyBears.class, Shock.class, Shock.class, Shock.class, Shock.class);
		this.addDeck(Shock.class, Shock.class, Shock.class, Shock.class, Shock.class, Shock.class, Shock.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(VoiceofAll.class));
		addMana("2WW");
		donePlayingManaAbilities();
		pass();
		pass();

		// Make choice for Voice of All
		respondWith(Color.RED);

		// This makes sure the choice is on the permanent in play, even though
		// it was set for the spell on the stack
		assertTrue(this.game.actualState.battlefield().objects.get(0).getStaticAbilities().get(1).getLinkManager().getLinkInformation(this.game.actualState).iterator().next().equals(org.rnd.jmagic.engine.Color.RED));

		respondWith(getSpellAction(GrizzlyBears.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(GrizzlyBears.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Shock.class));

		// Make sure the Voice of All is not a valid target
		assertEquals(4, this.choices.size());
		assertTrue(!this.choices.contains(this.game.actualState.battlefield().objects.get(2)));
		assertTrue(this.game.actualState.battlefield().objects.get(2).getName().equals("Voice of All"));

	}
}
