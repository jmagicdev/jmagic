package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sorin Markov")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.SORIN})
@ManaCost("3BBB")
@ColorIdentity({Color.BLACK})
public final class SorinMarkov extends Card
{
	public static final class DrainForTwo extends LoyaltyAbility
	{
		public DrainForTwo(GameState state)
		{
			super(state, +2, "Sorin Markov deals 2 damage to target creature or player and you gain 2 life.");
			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Sorin Markov deals 2 damage to target creature or player"));
			this.addEffect(gainLife(You.instance(), 2, "and you gain 2 life."));
		}
	}

	public static final class TenLife extends LoyaltyAbility
	{
		public TenLife(GameState state)
		{
			super(state, -3, "Target opponent's life total becomes 10.");

			Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");
			EventFactory setLife = new EventFactory(EventType.SET_LIFE, "Target opponent's life total becomes 10.");
			setLife.parameters.put(EventType.Parameter.CAUSE, This.instance());
			setLife.parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
			setLife.parameters.put(EventType.Parameter.NUMBER, numberGenerator(10));
			this.addEffect(setLife);
		}
	}

	public static final class Mindslaver extends LoyaltyAbility
	{
		public Mindslaver(GameState state)
		{
			super(state, -7, "You control target player during their next turn.");
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

	public SorinMarkov(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +2: Sorin Markov deals 2 damage to target creature or player and you
		// gain 2 life.
		this.addAbility(new DrainForTwo(state));

		// -3: Target opponent's life total becomes 10.
		this.addAbility(new TenLife(state));

		// -7: You control target player's next turn.
		this.addAbility(new Mindslaver(state));
	}
}
