package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.sanitized.*;

public class SplitCardsTest extends JUnitTest
{
	@Test
	public void assaultBattery()
	{
		this.addDeck(AssaultBattery.class, AssaultBattery.class, AssaultBattery.class, AssaultBattery.class, AssaultBattery.class, AssaultBattery.class, GrizzlyBears.class);
		this.addDeck(GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(GrizzlyBears.class);

		SanitizedPlayerAction action = null;
		for(SanitizedCastSpellOrActivateAbilityAction choice: this.choices.getAll(SanitizedCastSpellOrActivateAbilityAction.class))
			if(choice.name.equals("Cast Assault"))
			{
				action = choice;
				break;
			}
		if(action == null)
			fail("Couldn't cast Assault.");

		respondWith(action);
		respondWith(getTarget(GrizzlyBears.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());

		action = null;
		for(SanitizedCastSpellOrActivateAbilityAction choice: this.choices.getAll(SanitizedCastSpellOrActivateAbilityAction.class))
			if(choice.name.equals("Cast Battery"))
			{
				action = choice;
				break;
			}
		if(action == null)
			fail("Couldn't cast Battery.");

		respondWith(action);
		addMana("3G");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());
		GameObject elephant = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Elephant", elephant.getName());
	}

	@Test
	public void deadGone()
	{
		this.addDeck(DeadGone.class, DeadGone.class, DeadGone.class, DeadGone.class, DeadGone.class, SleeperAgent.class, GrizzlyBears.class);
		this.addDeck(GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);

		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(GrizzlyBears.class);

		SanitizedPlayerAction action = null;
		for(SanitizedCastSpellOrActivateAbilityAction choice: this.choices.getAll(SanitizedCastSpellOrActivateAbilityAction.class))
			if(choice.name.equals("Cast Dead"))
			{
				action = choice;
				break;
			}
		if(action == null)
			fail("Couldn't cast Dead.");
		respondWith(action);
		// auto-pick grizzly bears
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());

		castAndResolveSpell(SleeperAgent.class);
		// resolve Sleeper Agent trigger:
		pass();
		pass();

		action = null;
		for(SanitizedCastSpellOrActivateAbilityAction choice: this.choices.getAll(SanitizedCastSpellOrActivateAbilityAction.class))
			if(choice.name.equals("Cast Gone"))
			{
				action = choice;
				break;
			}
		if(action == null)
			fail("Couldn't cast Gone.");
		respondWith(action);
		// auto-pick sleeper agent
		addMana("2R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
		// cast two creatures, and dead, and gone; sleeper agent returned
		assertEquals(7 - 4 + 1, player(0).getHand(this.game.actualState).objects.size());
	}

	@Test
	public void meddlingMageNamingFire()
	{
		this.addDeck(MeddlingMage.class, FireIce.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		this.addDeck(GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);

		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(MeddlingMage.class);
		respondWith("Fire");

		SanitizedPlayerAction action = null;
		for(SanitizedCastSpellOrActivateAbilityAction choice: this.choices.getAll(SanitizedCastSpellOrActivateAbilityAction.class))
			if(choice.name.equals("Cast Ice"))
				action = choice;
			else if(choice.name.equals("Cast Fire"))
				fail("Could cast Fire (which was named for Meddling Mage)");
		if(action == null)
			fail("Couldn't cast Ice.");
	}
}
