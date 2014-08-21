package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ChangeControl extends EventType
{
	public static final EventType INSTANCE = new ChangeControl();

	private ChangeControl()
	{
		super("CHANGE_CONTROL");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		java.util.Map<Parameter, Set> rfcParameters = new java.util.HashMap<Parameter, Set>();
		rfcParameters.put(Parameter.OBJECT, parameters.get(Parameter.OBJECT));

		java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);

		if(!parameters.containsKey(Parameter.ATTACKER) && game.actualState.currentPhase() != null && game.actualState.currentPhase().type == Phase.PhaseType.COMBAT)
			createEvent(game, "Remove " + objects + " from combat.", REMOVE_FROM_COMBAT, rfcParameters).perform(event, false);

		// When something changes control, it "gains summoning sickness". It
		// will "lose summoning sickness" when its controller's next turn
		// starts.
		java.util.Collection<Integer> changedControlIDs = new java.util.LinkedList<Integer>();
		for(GameObject object: objects)
			changedControlIDs.add(object.ID);
		for(java.util.Collection<Integer> objectIDs: game.physicalState.summoningSick.values())
			objectIDs.removeAll(changedControlIDs);

		if(parameters.containsKey(Parameter.TARGET))
		{
			Player controller = parameters.get(Parameter.TARGET).getOne(Player.class);
			game.physicalState.summoningSick.get(controller.ID).addAll(changedControlIDs);
		}

		event.setResult(Empty.set);
		return true;
	}
}