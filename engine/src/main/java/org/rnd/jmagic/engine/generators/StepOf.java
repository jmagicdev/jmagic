package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to each given players step of the given StepType, or the steps of
 * the given turns with the given StepType.
 */
public class StepOf extends SetGenerator
{
	public static StepOf instance(SetGenerator players, Phase.PhaseType phaseType, Step.StepType stepType)
	{
		return new StepOf(players, phaseType, stepType);
	}

	private final Phase.PhaseType phaseType;
	private final SetGenerator playersAndTurns;
	private final Step.StepType stepType;

	protected StepOf(SetGenerator playersAndTurns, Phase.PhaseType phaseType, Step.StepType stepType)
	{
		this.phaseType = phaseType;
		this.playersAndTurns = playersAndTurns;
		this.stepType = stepType;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		Set returnValue = new Set();
		for(Player player: this.playersAndTurns.evaluate(state, thisObject).getAll(Player.class))
		{
			if((state.currentStep() != null) && (state.currentStep().ownerID == player.ID) && (state.currentStep().type == this.stepType))
				returnValue.add(state.currentStep());

			if((state.currentPhase() != null) && (state.currentPhase().ownerID == player.ID) && (state.currentPhase().type == this.phaseType))
				handleSteps(state.currentPhase(), returnValue);

			if((state.currentTurn() != null) && (state.currentTurn().ownerID == player.ID))
				handlePhases(state.currentTurn(), returnValue);

			for(Turn turn: state.futureTurns)
				if(turn.ownerID == player.ID)
					handlePhases(turn, returnValue);
		}

		for(Turn turn: this.playersAndTurns.evaluate(state, thisObject).getAll(Turn.class))
		{
			// If this turn is the current turn, handle the current phase
			// specifically since it won't be in any collections of the current
			// turn.
			if((state.currentTurn() != null) && (state.currentTurn() == turn))
				if((state.currentPhase() != null) && (state.currentPhase().type == this.phaseType))
					handleSteps(state.currentPhase(), returnValue);

			handleTurn(turn, returnValue);
		}

		return returnValue;
	}

	private void handleTurn(Turn turn, Set returnValue)
	{
		handlePhases(turn, returnValue);
		handlePhases(turn.phasesRan, returnValue);
	}

	private void handlePhases(java.lang.Iterable<Phase> phases, Set returnValue)
	{
		for(Phase phase: phases)
			if(phase.type == this.phaseType)
			{
				handleSteps(phase, returnValue);
				handleSteps(phase.stepsRan, returnValue);
			}
	}

	private void handleSteps(java.lang.Iterable<Step> steps, Set returnValue)
	{
		for(Step step: steps)
			if(step.type == this.stepType)
				returnValue.add(step);
	}
}
