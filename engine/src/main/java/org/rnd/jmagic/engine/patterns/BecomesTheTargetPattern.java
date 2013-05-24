package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

public class BecomesTheTargetPattern implements EventPattern
{
	private SetGenerator triggerObject;
	private SetGenerator targetOfWhat;

	/**
	 * Creates an EventPattern that matches {@link EventType#BECOMES_TARGET}
	 * events where <code>triggerObject</code> is becoming the target of any
	 * spell or ability.
	 * 
	 * @param triggerObject A generator evaluating to a single
	 * {@link GameObject}.
	 */
	public BecomesTheTargetPattern(SetGenerator triggerObject)
	{
		this(triggerObject, null);
	}

	public BecomesTheTargetPattern(SetGenerator triggerObject, SetGenerator targetOfWhat)
	{
		this.triggerObject = triggerObject;
		this.targetOfWhat = targetOfWhat;
	}

	@Override
	public boolean match(Event event, Identified object, GameState state)
	{
		if(event.type != EventType.BECOMES_TARGET)
			return false;

		GameObject source = event.getSource();

		if(this.targetOfWhat != null)
		{
			GameObject spellOrAbility = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state.game, source).getOne(GameObject.class);
			GameObject targetOfWhat = this.targetOfWhat.evaluate(state.game, object).getOne(GameObject.class);
			if(!spellOrAbility.equals(targetOfWhat))
				return false;
		}

		GameObject target = event.parameters.get(EventType.Parameter.TARGET).evaluate(state.game, source).getOne(GameObject.class);
		GameObject triggerObject = this.triggerObject.evaluate(state.game, object).getOne(GameObject.class);
		return triggerObject.equals(target);
	}

	@Override
	public boolean looksBackInTime()
	{
		return false;
	}

	@Override
	public boolean matchesManaAbilities()
	{
		return false;
	}
}
