package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;

public class PreventionEffects extends JUnitTest
{
	@Test
	public void holyDay()
	{
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, ChaosCharm.class, RagingGoblin.class, HolyDay.class, MoggFanatic.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.STACKED);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(HolyDay.class));
		addMana("W");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(MoggFanatic.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		// auto-choose mogg
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(MoggFanatic.class));
		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		assertEquals(20, player(1).lifeTotal);

		respondWith(getAbilityAction(MoggFanatic.SacPing.class));
		respondWith(getTarget(player(1)));
		pass();
		pass();

		assertEquals(19, player(1).lifeTotal);

		// go to player 1's turn, pass it, and discard a card:
		goToPhase(Phase.PhaseType.BEGINNING);
		goToStep(Step.StepType.CLEANUP);
		respondWith(pullChoice(Plains.class));

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(RagingGoblin.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(RagingGoblin.class));
		goToPhase(Phase.PhaseType.POSTCOMBAT_MAIN);

		assertEquals(18, player(1).lifeTotal);
	}

	@Test
	public void mendingHandsWithTwincast()
	{
		this.addDeck(MendingHands.class, Twincast.class, BloodfireColossus.class, RagingGoblin.class, GrizzlyBears.class, AmbassadorLaquatus.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(BloodfireColossus.class));
		addMana("6RR");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(RagingGoblin.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(GrizzlyBears.class));
		addMana("1G");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(AmbassadorLaquatus.class));
		addMana("1UU");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(MendingHands.class));
		respondWith(getTarget(AmbassadorLaquatus.class));
		addMana("W");
		donePlayingManaAbilities();

		respondWith(getSpellAction(Twincast.class));
		// respondWith(getTarget(MendingHands.class));
		addMana("UU");
		donePlayingManaAbilities();
		pass();
		pass();
		respondWith(Answer.YES);
		respondWith(getTarget(GrizzlyBears.class));

		// resolve copy:
		pass();
		pass();
		// resolve original:
		pass();
		pass();

		respondWith(getAbilityAction(BloodfireColossus.Explode.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();
		// order the mending hands' effects
		respondWith(getDamageAssignmentReplacement("Prevent the next 4 damage that would be dealt to target creature or player this turn"));

		// order cards in the graveyard:
		respondArbitrarily();

		assertEquals("Ambassador Laquatus", this.game.actualState.battlefield().objects.get(0).getName());
		assertEquals(2, this.game.actualState.battlefield().objects.get(0).getDamage());
	}

	@Test
	public void storyCircle()
	{
		this.addDeck(StoryCircle.class, Shock.class, Shock.class, MoggFanatic.class, MoggFanatic.class, Banefire.class, Shock.class);
		this.addDeck(RagingGoblin.class, RagingGoblin.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(StoryCircle.class);

		// Choose a color for Story Circle
		respondWith(Color.RED);

		// Check that damage from spells is prevented
		{
			castAndResolveSpell(MoggFanatic.class);

			respondWith(getSpellAction(Shock.class));
			respondWith(getTarget(player(0)));
			addMana("R");
			donePlayingManaAbilities();

			respondWith(getAbilityAction(StoryCircle.ColorPrevention.class));
			addMana("W");
			donePlayingManaAbilities();
			pass();
			pass();

			// Choose to prevent the shock (choices are shock & mogg fanatic)
			respondWith(pullChoice(Shock.class));

			// Resolve the shock
			pass();
			pass();

			assertEquals(20, player(0).lifeTotal);
		}

		// Check that damage from permanents is prevented
		{
			castAndResolveSpell(MoggFanatic.class);

			respondWith(getAbilityAction(MoggFanatic.SacPing.class));
			respondWith(getTarget(player(0)));

			respondWith(getAbilityAction(StoryCircle.ColorPrevention.class));
			addMana("W");
			donePlayingManaAbilities();
			pass();
			pass();

			// Choose to prevent the mogg fanatic thats been ghosted (aka the
			// one
			// whose ability is on the stack)
			{
				java.util.Iterator<SanitizedIdentified> iter = this.choices.getAll(SanitizedIdentified.class).iterator();
				GameObject MoggA = this.game.actualState.get(iter.next().ID);
				GameObject MoggB = this.game.actualState.get(iter.next().ID);

				assertTrue(MoggA instanceof MoggFanatic);
				assertTrue(MoggB instanceof MoggFanatic);
				assertFalse(iter.hasNext());

				if(MoggA.isGhost() == MoggB.isGhost())
					fail("Something weird happened");
				else if(MoggA.isGhost())
					respondWith(getChoice(MoggA));
				else if(MoggB.isGhost())
					respondWith(getChoice(MoggB));
			}

			// Resolve the mogg fanatic's sac ping ability
			pass();
			pass();

			assertEquals(20, player(0).lifeTotal);
		}
	}

	@Test
	public void thunderstaff()
	{
		this.addDeck(BazaarTrader.class, Thunderstaff.class, AkromaAngelofWrath.class, ChaosCharm.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(BazaarTrader.class, "1R");

		castAndResolveSpell(Thunderstaff.class, "3");

		castAndResolveSpell(AkromaAngelofWrath.class, "5WWW");

		respondWith(getSpellAction(ChaosCharm.class));
		respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getAbilityAction(BazaarTrader.BazaarTraderAbility0.class));
		respondWith(getTarget(player(1)));
		respondWith(getTarget(Thunderstaff.class));
		pass();
		pass();

		goToStep(Step.StepType.DECLARE_ATTACKERS);
		respondWith(pullChoice(AkromaAngelofWrath.class));

		assertEquals(20, player(1).lifeTotal);
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getPower());

		goToStep(Step.StepType.COMBAT_DAMAGE);

		assertEquals(15, player(1).lifeTotal);
		assertEquals(6, this.game.actualState.battlefield().objects.get(0).getPower());

	}

	@Test
	public void vengefulArchon()
	{
		this.addDeck(VengefulArchon.class, LightningBolt.class, Shock.class, Shock.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(VengefulArchon.class);

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(18, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);

		respondWith(getAbilityAction(VengefulArchon.VengefulArchonAbility1.class));
		respondWith(4);
		respondWith(getTarget(player(1)));
		addMana("4");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(18, player(0).lifeTotal);
		assertEquals(20, player(1).lifeTotal);

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(18, player(0).lifeTotal);
		assertEquals(17, player(1).lifeTotal);

		respondWith(getSpellAction(Shock.class));
		respondWith(getTarget(player(0)));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(17, player(0).lifeTotal);
		assertEquals(16, player(1).lifeTotal);
	}

	@Test
	public void unpreventable()
	{
		this.addDeck(Excruciator.class, ArcaneTeachings.class, ChaosCharm.class, Twiddle.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(AweStrike.class, Humble.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		// Check that unpreventable damage ignores prevention effects
		{
			castAndResolveSpell(Excruciator.class);
			castAndResolveSpell(ArcaneTeachings.class);
			// Automatically target Excruciator

			respondWith(getSpellAction(ChaosCharm.class));
			respondWith(getMode(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT));
			// Automatically target Excruciator
			addMana("R");
			donePlayingManaAbilities();
			pass();
			pass();

			pass();
			castAndResolveSpell(AweStrike.class);
			// Automatically target Excruciator

			respondWith(getAbilityAction(org.rnd.jmagic.abilities.Ping.class));
			respondWith(getTarget(player(1)));
			pass();
			pass();

			// The damage should not have been prevented
			assertEquals(19, player(1).lifeTotal);
		}

		// Check that unpreventable damage doesn't exhaust the prevention effect
		{
			castAndResolveSpell(Twiddle.class);
			// Automatically target Excruciator
			respondWith(Answer.YES);

			goToStep(Step.StepType.DECLARE_ATTACKERS);
			respondWith(pullChoice(Excruciator.class));
			pass();

			castAndResolveSpell(Humble.class);
			// Automatically target Excruciator

			goToStep(Step.StepType.COMBAT_DAMAGE);

			// Awe Strike's shield should have persisted until now to prevent
			// the damage and gain the player life
			assertEquals(21, player(1).lifeTotal);
		}
	}
}
