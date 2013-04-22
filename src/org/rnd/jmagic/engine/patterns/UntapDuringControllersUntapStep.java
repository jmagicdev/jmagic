package org.rnd.jmagic.engine.patterns;

import org.rnd.jmagic.engine.*;

public class UntapDuringControllersUntapStep extends SimpleEventPattern
{
	private SetGenerator permanents;

	public UntapDuringControllersUntapStep(SetGenerator permanents)
	{
		super(EventType.UNTAP_ONE_PERMANENT);
		this.put(EventType.Parameter.OBJECT, permanents);
		this.permanents = permanents;
	}

	@Override
	public boolean match(Event event, Identified object, GameState state)
	{
		// Add assertions that it is an untap step and that this
		// ability's source's controller is the owner of this step
		if(super.match(event, object, state) && (state.currentStep().type == Step.StepType.UNTAP) && (state.currentStep().ownerID == this.permanents.evaluate(state, object).getOne(GameObject.class).controllerID))
			return true;
		return false;
	}
}
