package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class EndTheTurn extends EventType
{
	public static final EventType INSTANCE = new EndTheTurn();

	private EndTheTurn()
	{
		super("END_THE_TURN");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.CAUSE;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// 1) Exile every object on the stack.
		// This includes Time Stop, though it will continue to resolve. It
		// also includes spells and abilities that can't be countered.
		Event exile = new Event(game.physicalState, "All spells and abilities on the stack are exiled.", EventType.MOVE_OBJECTS);
		exile.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		exile.parameters.put(EventType.Parameter.TO, ExileZone.instance());
		exile.parameters.put(EventType.Parameter.OBJECT, InZone.instance(Stack.instance()));
		exile.perform(null, true);

		// 2) All attacking and blocking creatures are removed from combat.
		Event removeFromCombat = new Event(game.physicalState, "All attacking and blocking creatures are removed from combat.", EventType.REMOVE_FROM_COMBAT);
		removeFromCombat.parameters.put(EventType.Parameter.OBJECT, Union.instance(Attacking.instance(), Blocking.instance()));
		removeFromCombat.perform(null, true);

		// 3) State-based actions are checked. No player gets priority, and
		// no triggered abilities are put onto the stack.
		while(game.checkStateBasedActions())
		{
			// intentionally left blank
		}

		// 4) The current phase and/or step ends. The game skips straight to
		// the cleanup step. The cleanup step happens in its entirety.
		Turn currentTurn = game.physicalState.currentTurn();
		currentTurn.phases.clear();

		Player owner = currentTurn.getOwner(game.physicalState);
		Phase newPhase = new Phase(owner, Phase.PhaseType.ENDING);
		newPhase.steps.clear();
		newPhase.steps.add(new Step(owner, Step.StepType.CLEANUP));
		currentTurn.phases.add(newPhase);

		throw new Game.StopPriorityException();
	}
}