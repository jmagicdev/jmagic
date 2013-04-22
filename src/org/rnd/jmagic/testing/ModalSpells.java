package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;

public class ModalSpells extends JUnitTest
{
	@Test
	public void modalTrigger()
	{
		this.addDeck(ShivanSandMage.class, ShivanSandMage.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		{
			SanitizedPlayerAction response = null;
			for(SanitizedPlayerAction action: this.choices.getAll(SanitizedPlayerAction.class))
				if(action.name.equals("Suspend Shivan Sand-Mage"))
				{
					response = action;
					break;
				}

			if(response == null)
				fail("Suspend action not found.");

			respondWith(response);
		}

		addMana("R");
		donePlayingManaAbilities();

		castAndResolveSpell(ShivanSandMage.class);

		respondWith(getMode(EventType.REMOVE_COUNTERS));

		respondWith(getTarget(this.game.actualState.exileZone().objects.get(0)));
		pass();
		pass();

		assertEquals(2, this.game.actualState.exileZone().objects.get(0).counters.size());
	}

	@Test
	public void branchingBolt()
	{
		this.addDeck(Ornithopter.class, Ornithopter.class, Ornithopter.class, RagingGoblin.class, RagingGoblin.class, RagingGoblin.class, Plains.class);
		this.addDeck(BranchingBolt.class, BranchingBolt.class, BranchingBolt.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameType.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		for(int i = 0; i < 3; ++i)
		{
			respondWith(getSpellAction(Ornithopter.class));
			pass();
			pass();
		}

		for(int i = 0; i < 3; ++i)
			castAndResolveSpell(RagingGoblin.class, "R");

		java.util.List<GameObject> battlefield = this.game.actualState.battlefield().objects;
		assertEquals(6, battlefield.size());
		assertEquals("Ornithopter", battlefield.get(5).getName());
		assertEquals("Ornithopter", battlefield.get(4).getName());
		assertEquals("Ornithopter", battlefield.get(3).getName());
		assertEquals("Raging Goblin", battlefield.get(2).getName());
		assertEquals("Raging Goblin", battlefield.get(1).getName());
		assertEquals("Raging Goblin", battlefield.get(0).getName());

		pass();

		respondWith(getSpellAction(BranchingBolt.class));
		respondWith(getModeByName("Branching Bolt deals 3 damage to target creature with flying"));
		respondWith(getTarget(Ornithopter.class));
		addMana("1RG");
		donePlayingManaAbilities();
		pass();
		pass();

		battlefield = this.game.actualState.battlefield().objects;
		assertEquals(5, battlefield.size());
		assertEquals("Ornithopter", battlefield.get(4).getName());
		assertEquals("Ornithopter", battlefield.get(3).getName());
		assertEquals("Raging Goblin", battlefield.get(2).getName());
		assertEquals("Raging Goblin", battlefield.get(1).getName());
		assertEquals("Raging Goblin", battlefield.get(0).getName());

		pass();

		respondWith(getSpellAction(BranchingBolt.class));
		respondWith(getModeByName("Branching Bolt deals 3 damage to target creature without flying."));
		respondWith(getTarget(RagingGoblin.class));
		addMana("1RG");
		donePlayingManaAbilities();
		pass();
		pass();

		battlefield = this.game.actualState.battlefield().objects;
		assertEquals(4, battlefield.size());
		assertEquals("Ornithopter", battlefield.get(3).getName());
		assertEquals("Ornithopter", battlefield.get(2).getName());
		assertEquals("Raging Goblin", battlefield.get(1).getName());
		assertEquals("Raging Goblin", battlefield.get(0).getName());

		pass();

		respondWith(getSpellAction(BranchingBolt.class));
		respondWith(getModeByName("Branching Bolt deals 3 damage to target creature with flying"), getModeByName("Branching Bolt deals 3 damage to target creature without flying."));
		respondWith(getTarget(Ornithopter.class));
		respondWith(getTarget(RagingGoblin.class));
		addMana("1RG");
		donePlayingManaAbilities();
		pass();
		pass();

		battlefield = this.game.actualState.battlefield().objects;
		assertEquals(2, battlefield.size());
		assertEquals("Ornithopter", battlefield.get(1).getName());
		assertEquals("Raging Goblin", battlefield.get(0).getName());
	}
}
