package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mindslaver")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.MYTHIC), @Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class Mindslaver extends Card
{
	public static final class TurnControl extends ActivatedAbility
	{
		public TurnControl(GameState state)
		{
			super(state, "(4), (T), Sacrifice Mindslaver: You control target player's next turn.");
			this.setManaCost(new ManaPool("4"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Mindslaver"));

			Target target = this.addTarget(Players.instance(), "target player");

			ContinuousEffect.Part turnControlEffect = new ContinuousEffect.Part(ContinuousEffectType.CONTROL_PLAYER);
			turnControlEffect.parameters.put(ContinuousEffectType.Parameter.CONTROLLER, You.instance());
			turnControlEffect.parameters.put(ContinuousEffectType.Parameter.PLAYER, targetedBy(target));

			EventFactory createFloatingEffect = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "You control target player during their next turn.");
			createFloatingEffect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			createFloatingEffect.parameters.put(EventType.Parameter.EFFECT, Identity.instance(turnControlEffect));

			SetGenerator theEffect = EffectResult.instance(createFloatingEffect);
			SetGenerator thisTurn = TurnFloatingEffectWasCreated.instance(theEffect);
			SetGenerator thatPlayersTurnIsOver = Intersect.instance(PreviousStep.instance(), CleanupStepOf.instance(targetedBy(target)));
			SetGenerator thatTurnWasThisTurn = Intersect.instance(PreviousStep.instance(), CleanupStepOf.instance(thisTurn));
			SetGenerator expires = Both.instance(Not.instance(thatTurnWasThisTurn), thatPlayersTurnIsOver);
			createFloatingEffect.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(expires));

			SetGenerator itsThatPlayersTurn = Intersect.instance(CurrentTurn.instance(), TurnOf.instance(targetedBy(target)));
			SetGenerator duration = RelativeComplement.instance(itsThatPlayersTurn, thisTurn);
			turnControlEffect.parameters.put(ContinuousEffectType.Parameter.RANGE, Identity.instance(duration));

			this.addEffect(createFloatingEffect);
		}
	}

	public Mindslaver(GameState state)
	{
		super(state);

		// (4), (T), Sacrifice Mindslaver: You control target player's next
		// turn. (You see all cards that player could see and make all decisions
		// for the player.)
		this.addAbility(new TurnControl(state));
	}
}
