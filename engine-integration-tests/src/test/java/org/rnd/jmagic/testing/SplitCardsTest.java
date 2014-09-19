package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;

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

		respondWith(getSpellAction(AssaultBattery.class));
		respondWith(getChoiceByToString("Assault"));
		respondWith(getTarget(GrizzlyBears.class));
		addMana("R");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());

		respondWith(getSpellAction(AssaultBattery.class));
		respondWith(getChoiceByToString("Battery"));
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

		respondWith(getSpellAction(DeadGone.class));
		respondWith(getChoiceByToString("Dead"));
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

		respondWith(getSpellAction(DeadGone.class));
		respondWith(getChoiceByToString("Gone"));
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

		respondWith(getSpellAction(FireIce.class));
		respondWith(getChoiceByToString("Ice"));
	}

	@Test
	public void fuse()
	{
		this.addDeck(PyriteSpellbomb.class, IntangibleVirtue.class, WearTear.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);
		this.addDeck(GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class, GrizzlyBears.class);

		startGame(new Open());

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(PyriteSpellbomb.class);
		castAndResolveSpell(IntangibleVirtue.class);

		respondWith(getSpellAction(WearTear.class));
		respondWith(getChoiceByToString("Wear"), getChoiceByToString("Tear"));
		// auto-pick both targets
		addMana("1WR");
		donePlayingManaAbilities();
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}
}
