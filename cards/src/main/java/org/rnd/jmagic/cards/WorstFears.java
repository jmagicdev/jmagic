package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Worst Fears")
@Types({Type.SORCERY})
@ManaCost("7B")
@ColorIdentity({Color.BLACK})
public final class WorstFears extends Card
{
	public WorstFears(GameState state)
	{
		super(state);

		// You control target player during that player's next turn.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		ContinuousEffect.Part turnControlEffect = new ContinuousEffect.Part(ContinuousEffectType.CONTROL_PLAYER);
		turnControlEffect.parameters.put(ContinuousEffectType.Parameter.CONTROLLER, You.instance());
		turnControlEffect.parameters.put(ContinuousEffectType.Parameter.PLAYER, target);

		EventFactory createFloatingEffect = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "You control target player during that player's next turn.");
		createFloatingEffect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		createFloatingEffect.parameters.put(EventType.Parameter.EFFECT, Identity.instance(turnControlEffect));

		SetGenerator theEffect = EffectResult.instance(createFloatingEffect);
		SetGenerator thisTurn = TurnFloatingEffectWasCreated.instance(theEffect);
		SetGenerator thatPlayersTurnIsOver = Intersect.instance(PreviousStep.instance(), CleanupStepOf.instance(target));
		SetGenerator thatTurnWasThisTurn = Intersect.instance(PreviousStep.instance(), CleanupStepOf.instance(thisTurn));
		SetGenerator expires = Both.instance(Not.instance(thatTurnWasThisTurn), thatPlayersTurnIsOver);
		createFloatingEffect.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(expires));

		SetGenerator itsThatPlayersTurn = Intersect.instance(CurrentTurn.instance(), TurnOf.instance(target));
		SetGenerator duration = RelativeComplement.instance(itsThatPlayersTurn, thisTurn);
		turnControlEffect.parameters.put(ContinuousEffectType.Parameter.RANGE, Identity.instance(duration));

		this.addEffect(createFloatingEffect);

		// Exile Worst Fears.
		this.addEffect(exile(This.instance(), "Exile Worst Fears."));
	}
}
