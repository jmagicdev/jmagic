package org.rnd.jmagic.abilityTemplates;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public abstract class ExaltedBase extends EventTriggeredAbility
{
	private static class ExaltedSetPattern implements SetPattern
	{
		private SetGenerator controller;

		public ExaltedSetPattern(SetGenerator controller)
		{
			this.controller = controller;
		}

		@Override
		public boolean match(GameState state, Identified object, Set set)
		{
			Set attackers = Attacking.instance().evaluate(state, object);
			if(attackers.size() != 1)
				return false;

			Player controller = (Player)(this.controller.evaluate(state, object).iterator().next());
			return ((GameObject)attackers.iterator().next()).controllerID == controller.ID;
		}

		@Override
		public void freeze(GameState state, Identified thisObject)
		{
			// No text changes to protect from
		}
	}

	protected final SetGenerator thatCreature;

	protected ExaltedBase(GameState state, String abilityText)
	{
		super(state, abilityText);

		SetGenerator thisAbility = This.instance();
		SetGenerator thisCard = AbilitySource.instance(thisAbility);
		SetGenerator controller = ControllerOf.instance(thisCard);

		SimpleEventPattern triggerPattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
		triggerPattern.put(EventType.Parameter.OBJECT, new ExaltedSetPattern(controller));
		this.addPattern(triggerPattern);

		this.thatCreature = EventParameter.instance(TriggerEvent.instance(thisAbility), EventType.Parameter.OBJECT);
	}
}
