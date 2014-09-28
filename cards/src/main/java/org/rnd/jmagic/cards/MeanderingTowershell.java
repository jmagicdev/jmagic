package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Meandering Towershell")
@Types({Type.CREATURE})
@SubTypes({SubType.TURTLE})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class MeanderingTowershell extends Card
{
	public static final class MeanderingTowershellAbility1 extends EventTriggeredAbility
	{
		public MeanderingTowershellAbility1(GameState state)
		{
			super(state, "Whenever Meandering Towershell attacks, exile it. Return it to the battlefield under your control tapped and attacking at the beginning of the declare attackers step on your next turn.");
			this.addPattern(whenThisAttacks());

			EventFactory exile = exile(ABILITY_SOURCE_OF_THIS, "Exile Meandering Towershell.");
			this.addEffect(exile);
			SetGenerator it = NewObjectOf.instance(EffectResult.instance(exile));

			EventFactory thisTurn = new EventFactory(NOTE, "");
			thisTurn.parameters.put(EventType.Parameter.OBJECT, CurrentTurn.instance());
			this.addEffect(thisTurn);

			SetGenerator yourNextTurn = RelativeComplement.instance(TurnOf.instance(You.instance()), EffectResult.instance(thisTurn));
			SetGenerator attackStep = DeclareAttackersStepOf.instance(yourNextTurn);

			SimpleEventPattern nextAttack = new SimpleEventPattern(EventType.BEGIN_STEP);
			nextAttack.put(EventType.Parameter.STEP, attackStep);

			EventFactory swing = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING, "Return Meandering Towershell to the battlefield under your control tapped and attacking.");
			swing.parameters.put(EventType.Parameter.CAUSE, This.instance());
			swing.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			swing.parameters.put(EventType.Parameter.OBJECT, delayedTriggerContext(it));

			EventFactory swingLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Return Meandering Towershell to the battlefield under your control tapped and attacking at the beginning of the declare attackers step on your next turn.");
			swingLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
			swingLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(nextAttack));
			swingLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(swing));
			this.addEffect(swingLater);
		}
	}

	public MeanderingTowershell(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(9);

		// Islandwalk (This creature can't be blocked as long as defending
		// player controls an Island.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));

		// Whenever Meandering Towershell attacks, exile it. Return it to the
		// battlefield under your control tapped and attacking at the beginning
		// of the declare attackers step on your next turn.
		this.addAbility(new MeanderingTowershellAbility1(state));
	}
}
