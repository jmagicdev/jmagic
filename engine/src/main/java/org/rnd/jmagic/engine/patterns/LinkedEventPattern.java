package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

public class LinkedEventPattern implements EventPattern
{
	private EventPattern pattern;
	private SetGenerator linkedObject;

	public LinkedEventPattern(EventPattern pattern, SetGenerator linkedObject)
	{
		this.pattern = pattern;
		this.linkedObject = linkedObject;
	}

	@Override
	public boolean match(Event event, Identified object, GameState state)
	{
		Identified linked = this.linkedObject.evaluate(state, object).getOne(Identified.class);
		if(linked == null || linked.ID != event.getStoreInID())
			return false;

		return this.pattern.match(event, object, state);
	}

	@Override
	public boolean looksBackInTime()
	{
		return this.pattern.looksBackInTime();
	}

	@Override
	public boolean matchesManaAbilities()
	{
		return this.pattern.matchesManaAbilities();
	}

}