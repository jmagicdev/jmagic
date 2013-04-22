package org.rnd.jmagic.interfaceAdapters;

import org.rnd.jmagic.sanitized.SanitizedIdentified;

/**
 * An adapter interface for when the game asks a player to choose counters that
 * are on multiple creatures.
 */
public class CountersAcrossCreaturesInterface extends SimplePlayerInterface
{
	public static final ChooseReason REASON = new ChooseReason("CountersAcrossCreaturesInterface", "Choose a permanent to remove counters from.", true);
	private org.rnd.jmagic.sanitized.SanitizedGameState lastState = null;

	public CountersAcrossCreaturesInterface(org.rnd.jmagic.engine.PlayerInterface adapt)
	{
		super(adapt);
	}

	@Override
	public void alertState(org.rnd.jmagic.sanitized.SanitizedGameState sanitizedGameState)
	{
		this.lastState = sanitizedGameState;
		super.alertState(sanitizedGameState);
	}

	@Override
	public <T extends java.io.Serializable> java.util.List<Integer> choose(ChooseParameters<T> parameterObject)
	{
		if(parameterObject.reason != ChooseReason.CHOOSE_COUNTERS)
			return super.choose(parameterObject);

		java.util.Map<Integer, java.util.List<org.rnd.jmagic.engine.Counter>> counterMap = new java.util.HashMap<Integer, java.util.List<org.rnd.jmagic.engine.Counter>>();
		java.util.List<SanitizedIdentified> availablePermanents = new java.util.LinkedList<SanitizedIdentified>();
		for(T c: parameterObject.choices)
		{
			org.rnd.jmagic.engine.Counter counter = (org.rnd.jmagic.engine.Counter)c;
			if(counterMap.containsKey(counter.sourceID))
				counterMap.get(counter.sourceID).add(counter);
			else
			{
				java.util.List<org.rnd.jmagic.engine.Counter> counters = new java.util.ArrayList<org.rnd.jmagic.engine.Counter>();
				counters.add(counter);
				counterMap.put(counter.sourceID, counters);
				availablePermanents.add(this.lastState.get(counter.sourceID));
			}
		}

		if(counterMap.size() <= 1)
			return super.choose(parameterObject);

		java.util.List<org.rnd.jmagic.engine.Counter> chosenCounters = new java.util.LinkedList<org.rnd.jmagic.engine.Counter>();
		while(availablePermanents.size() > 0)
		{
			ChooseParameters<SanitizedIdentified> choosePermanent = new ChooseParameters<SanitizedIdentified>(0, 1, ChoiceType.OBJECTS, REASON);
			choosePermanent.choices = availablePermanents;
			java.util.List<Integer> permanentChoice = this.choose(choosePermanent);
			if(permanentChoice.isEmpty())
				break;

			int index = permanentChoice.get(0);
			SanitizedIdentified permanent = availablePermanents.remove(index);
			ChooseParameters<org.rnd.jmagic.engine.Counter> chooseCounters = new ChooseParameters<org.rnd.jmagic.engine.Counter>(0, null, ChoiceType.STRING, ChooseReason.CHOOSE_COUNTERS);
			java.util.List<org.rnd.jmagic.engine.Counter> availableCounters = counterMap.get(permanent.ID);
			chooseCounters.choices = availableCounters;
			java.util.List<Integer> counterChoice = this.choose(chooseCounters);
			for(int choice: counterChoice)
				chosenCounters.add(availableCounters.get(choice));
		}

		java.util.List<Integer> ret = new java.util.LinkedList<Integer>();
		for(int i = 0; i < parameterObject.choices.size(); i++)
		{
			T c = parameterObject.choices.get(i);
			org.rnd.jmagic.engine.Counter counter = (org.rnd.jmagic.engine.Counter)c;
			for(org.rnd.jmagic.engine.Counter chosen: chosenCounters)
			{
				if(counter.compareTo(chosen) == 0)
				{
					chosenCounters.remove(chosen);
					ret.add(i);
					break;
				}
			}
		}

		return ret;
	}
}
