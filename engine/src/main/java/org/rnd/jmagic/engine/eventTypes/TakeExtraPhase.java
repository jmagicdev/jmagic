package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class TakeExtraPhase extends EventType
{	public static final EventType INSTANCE = new TakeExtraPhase();

	 private TakeExtraPhase()
	{
		super("TAKE_EXTRA_PHASE");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PHASE;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Phase addAfter = parameters.get(Parameter.TARGET).getOne(Phase.class);
		if(addAfter == null)
		{
			event.setResult(Empty.set);
			return false;
		}

		int index = 1 + game.physicalState.currentTurn().phases.indexOf(addAfter);
		// If indexOf returns -1, then either the target phase is the
		// current phase, or the target phase has already passed, in
		// which case we do nothing and fail.
		if(index == 0 && !game.physicalState.currentPhase().equals(addAfter))
		{
			event.setResult(Empty.set);
			return false;
		}

		@SuppressWarnings("unchecked") java.util.List<Phase.PhaseType> types = parameters.get(Parameter.PHASE).getOne(java.util.List.class);
		Set result = new Set();
		java.util.ListIterator<Phase.PhaseType> i = types.listIterator(types.size());
		while(i.hasPrevious())
		{
			Phase.PhaseType type = i.previous();
			Phase phase = new Phase(game.physicalState.currentTurn().getOwner(game.physicalState), type);

			// 500.8. Some effects can add phases to a turn. They do this by
			// adding the phases directly after the specified phase. If
			// multiple extra phases are created after the same phase, the
			// most recently created phase will occur first.
			game.physicalState.currentTurn().phases.add(index, phase);
			result.add(phase);
		}
		event.setResult(result);
		return true;
	}
}