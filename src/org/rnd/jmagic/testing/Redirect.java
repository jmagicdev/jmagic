package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;

public class Redirect extends JUnitTest
{
	@Test
	public void shuntAndChangeTarget()
	{
		this.addDeck(LightningBolt.class, LightningBolt.class, LightningBolt.class, Shunt.class, Shunt.class, Cancel.class, Cancel.class);
		this.addDeck(LightningBolt.class, LightningBolt.class, LightningBolt.class, Shunt.class, Shunt.class, Cancel.class, Cancel.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(player(1)));
		addMana("R");
		donePlayingManaAbilities();
		pass();

		respondWith(getSpellAction(Shunt.class));
		// auto-select lightning bolt
		addMana("1RR");
		donePlayingManaAbilities();
		pass();
		pass();

		// shunt is resolving
		// auto-choose player 0 as the target

		// resolve the lightning bolt
		pass();
		pass();

		assertEquals(20, player(1).lifeTotal);
		assertEquals(17, player(0).lifeTotal);

		respondWith(getSpellAction(LightningBolt.class));
		respondWith(getTarget(player(1)));
		addMana("R");
		donePlayingManaAbilities();
		pass();

		respondWith(getSpellAction(Cancel.class));
		// auto-target Lightning Bolt
		addMana("1UU");
		donePlayingManaAbilities();
		pass();

		respondWith(getSpellAction(Shunt.class));
		respondWith(getTarget(Cancel.class));
		addMana("1RR");
		donePlayingManaAbilities();
		pass();
		pass();

		// auto-choose to redirect the cancel to the shunt

		// resolve the cancel (countered due to illegal target)
		pass();
		pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertTrue(this.game.actualState.stack().objects.get(0).getName().equals("Lightning Bolt"));

		// resolve the lightning bolt
		pass();
		pass();

		assertEquals(17, player(0).lifeTotal);
		assertEquals(17, player(1).lifeTotal);

	}

	@Test
	public void shuntWithNoOtherLegalTargets()
	{
		this.addDeck(SoulWarden.class, Terror.class, Shunt.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(SoulWarden.class));
		addMana("W");
		donePlayingManaAbilities();
		pass();
		pass();

		respondWith(getSpellAction(Terror.class));
		// auto-target Soul Warden
		addMana("1B");
		donePlayingManaAbilities();

		respondWith(getSpellAction(Shunt.class));
		// auto-target Terror
		addMana("1RR");
		donePlayingManaAbilities();
		pass();
		pass();

		// resolve terror (its target should be unchanged)
		pass();
		pass();

		assertEquals(0, this.game.actualState.battlefield().objects.size());
	}

	@Test
	public void spellskite()
	{
		this.addDeck(Spellskite.class, RagingGoblin.class, Swelter.class, StaveOff.class, AgonyWarp.class, GiantGrowth.class, GiantGrowth.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		castAndResolveSpell(Spellskite.class);
		castAndResolveSpell(RagingGoblin.class);
		// Giant growth the spellskite so he survives this test
		castAndResolveSpell(GiantGrowth.class, Spellskite.class);
		// Giant growth the goblin so he survives this test
		castAndResolveSpell(GiantGrowth.class, RagingGoblin.class);

		respondWith(getSpellAction(Swelter.class));
		// auto target both critters
		addMana("3R");
		donePlayingManaAbilities();

		respondWith(getAbilityAction(Spellskite.SpellskiteAbility0.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "U"));
		addMana("U");
		donePlayingManaAbilities();

		// Resolve Spellskite Ability
		pass();
		pass();

		// Resolve Swelter
		pass();
		pass();

		respondWith(getSpellAction(AgonyWarp.class));
		respondWith(getTarget(RagingGoblin.class));
		respondWith(getTarget(RagingGoblin.class));
		addMana("UB");
		donePlayingManaAbilities();

		castAndResolveSpell(StaveOff.class, RagingGoblin.class);
		respondWith(Color.BLACK);

		respondWith(getAbilityAction(Spellskite.SpellskiteAbility0.class));
		respondWith(getCostCollection(CostCollection.TYPE_MANA, "U"));
		addMana("U");
		donePlayingManaAbilities();

		// Resolve Spellskite Ability
		pass();
		pass();

		SanitizedTarget toChoose = null;
		for(SanitizedTarget choice: this.choices.getAll(SanitizedTarget.class))
		{
			if(choice.name.equals("target creature to get -3/-0"))
			{
				toChoose = choice;
				break;
			}
		}

		assertNotNull(toChoose);
		respondWith(toChoose);

		// Resolve Agony Warp
		pass();
		pass();

		GameObject skite = this.game.actualState.battlefield().objects.get(1);
		assertEquals("Spellskite", skite.getName());
		assertEquals(0, skite.getPower());
		assertEquals(7, skite.getToughness());
		assertEquals(2, skite.getDamage());

		GameObject goblin = this.game.actualState.battlefield().objects.get(0);
		assertEquals("Raging Goblin", goblin.getName());
		assertEquals(4, goblin.getPower());
		assertEquals(4, goblin.getToughness());
		assertEquals(2, goblin.getDamage());
	}
}
