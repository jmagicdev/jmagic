package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.testing.*;

public class PreyUponTest extends JUnitTest
{
	@Test
	public void preyUpon()
	{
		this.addDeck(PreyUpon.class, PreyUpon.class, SleeperAgent.class, WallofAir.class, Terminate.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.startGame(GameTypes.OPEN);

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(SleeperAgent.class);

		// resolve trigger
		pass();
		pass();

		castAndResolveSpell(WallofAir.class);

		assertEquals(2, this.game.actualState.battlefield().objects.size());

		GameObject battleRampart = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Wall of Air", battleRampart.getName());
		assertEquals(1, battleRampart.getPower());
		assertEquals(5, battleRampart.getToughness());
		assertEquals(0, battleRampart.getDamage());

		GameObject sleeperAgent = this.game.actualState.battlefield().objects.get(1);
		assertEquals("Sleeper Agent", sleeperAgent.getName());
		assertEquals(3, sleeperAgent.getPower());
		assertEquals(3, sleeperAgent.getToughness());
		assertEquals(0, sleeperAgent.getDamage());

		// targets chosen automatically
		castAndResolveSpell(PreyUpon.class);

		assertEquals(2, this.game.actualState.battlefield().objects.size());

		battleRampart = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Wall of Air", battleRampart.getName());
		assertEquals(1, battleRampart.getPower());
		assertEquals(5, battleRampart.getToughness());
		assertEquals(3, battleRampart.getDamage());

		sleeperAgent = this.game.actualState.battlefield().objects.get(1);
		assertEquals("Sleeper Agent", sleeperAgent.getName());
		assertEquals(3, sleeperAgent.getPower());
		assertEquals(3, sleeperAgent.getToughness());
		assertEquals(1, sleeperAgent.getDamage());

		respondWith(getSpellAction(PreyUpon.class));
		// targets chosen automatically
		addMana("G");
		donePlayingManaAbilities();

		assertEquals(2, this.game.actualState.battlefield().objects.size());

		battleRampart = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Wall of Air", battleRampart.getName());
		assertEquals(1, battleRampart.getPower());
		assertEquals(5, battleRampart.getToughness());
		assertEquals(3, battleRampart.getDamage());

		sleeperAgent = this.game.actualState.battlefield().objects.get(1);
		assertEquals("Sleeper Agent", sleeperAgent.getName());
		assertEquals(3, sleeperAgent.getPower());
		assertEquals(3, sleeperAgent.getToughness());
		assertEquals(1, sleeperAgent.getDamage());

		castAndResolveSpell(Terminate.class, WallofAir.class);

		assertEquals(1, this.game.actualState.battlefield().objects.size());

		sleeperAgent = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Sleeper Agent", sleeperAgent.getName());
		assertEquals(3, sleeperAgent.getPower());
		assertEquals(3, sleeperAgent.getToughness());
		assertEquals(1, sleeperAgent.getDamage());

		// resolve prey upon (should do nothing, but not be countered)
		pass();
		pass();

		assertEquals(1, this.game.actualState.battlefield().objects.size());

		sleeperAgent = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Sleeper Agent", sleeperAgent.getName());
		assertEquals(3, sleeperAgent.getPower());
		assertEquals(3, sleeperAgent.getToughness());
		assertEquals(1, sleeperAgent.getDamage());
	}
}