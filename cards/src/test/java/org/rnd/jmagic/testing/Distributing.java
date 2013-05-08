package org.rnd.jmagic.testing;

import java.io.*;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;

public class Distributing extends JUnitTest
{
	@Test
	public void blessingsOfNature()
	{
		this.addDeck(BlessingsofNature.class, MartialCoup.class, Forest.class, Forest.class, Forest.class, Forest.class, Forest.class);
		this.addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));
		keep();
		keep();

		goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);

		respondWith(getSpellAction(MartialCoup.class));
		respondWith(4);
		addMana("4WW");
		donePlayingManaAbilities();
		pass();
		pass();

		java.util.List<Integer> ids = new java.util.LinkedList<Integer>();
		{
			Zone battlefield = this.game.physicalState.battlefield();
			assertEquals(4, battlefield.objects.size());

			ids.add(battlefield.objects.get(0).ID); // not targetted
			ids.add(battlefield.objects.get(1).ID); // first target
			ids.add(battlefield.objects.get(2).ID); // third target
			ids.add(battlefield.objects.get(3).ID); // second target
		}

		respondWith(getSpellAction(BlessingsofNature.class));

		{
			Serializable[] responses = new Serializable[3];

			for(SanitizedTarget target: this.choices.getAll(SanitizedTarget.class))
			{
				if(target.targetID == ids.get(1))
					responses[0] = target;
				else if(target.targetID == ids.get(3))
					responses[1] = target;
				else if(target.targetID == ids.get(2))
					responses[2] = target;
			}

			respondWith(responses);
		}

		java.util.Map<Integer, Integer> divisions = new java.util.HashMap<Integer, Integer>();
		divisions.put(ids.get(1), 1);
		divisions.put(ids.get(3), 1);
		divisions.put(ids.get(2), 2);
		this.divide(divisions);

		addMana("4G");
		donePlayingManaAbilities();
		pass();
		pass();

		// if the division was accepted, we will have moved to normal actions
		assertEquals(PlayerInterface.ChoiceType.NORMAL_ACTIONS, this.choiceType);

		{
			Zone battlefield = this.game.actualState.battlefield();

			assertEquals((int)ids.get(0), battlefield.objects.get(0).ID);
			assertEquals(1, battlefield.objects.get(0).getPower());

			assertEquals((int)ids.get(1), battlefield.objects.get(1).ID);
			assertEquals(2, battlefield.objects.get(1).getPower());

			assertEquals((int)ids.get(2), battlefield.objects.get(2).ID);
			assertEquals(3, battlefield.objects.get(2).getPower());

			assertEquals((int)ids.get(3), battlefield.objects.get(3).ID);
			assertEquals(2, battlefield.objects.get(3).getPower());
		}
	}
}
