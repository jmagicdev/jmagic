package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hellkite Charger")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class HellkiteCharger extends Card
{
	/**
	 * @eparam CAUSE: hellkite charger's trigger
	 * @eparam OBJECT: what to untap (all attacking creatures)
	 */
	public static final EventType HELLKITE_CHARGER_EVENT = new EventType("HELLKITE_CHARGER_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set untap = parameters.get(Parameter.OBJECT);

			java.util.Map<Parameter, Set> untapParameters = new java.util.HashMap<Parameter, Set>();
			untapParameters.put(Parameter.CAUSE, cause);
			untapParameters.put(Parameter.OBJECT, untap);
			createEvent(game, "Untap all attacking creatures", EventType.UNTAP_PERMANENTS, untapParameters).perform(event, true);

			java.util.List<Phase.PhaseType> combatPhaseList = new java.util.LinkedList<Phase.PhaseType>();
			combatPhaseList.add(Phase.PhaseType.COMBAT);
			Set combatPhaseSet = new Set();
			combatPhaseSet.add(combatPhaseList);

			java.util.Map<Parameter, Set> combatParameters = new java.util.HashMap<Parameter, Set>();
			combatParameters.put(Parameter.CAUSE, cause);
			combatParameters.put(Parameter.TARGET, new Set(game.actualState.currentPhase()));
			combatParameters.put(Parameter.PHASE, combatPhaseSet);
			createEvent(game, "There is an additional combat phase", EventType.TAKE_EXTRA_PHASE, combatParameters).perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class MayPayForAnotherCombatPhase extends EventTriggeredAbility
	{
		public MayPayForAnotherCombatPhase(GameState state)
		{
			super(state, "Whenever Hellkite Charger attacks, you may pay (5)(R)(R). If you do, untap all attacking creatures and after this phase, there is an additional combat phase.");
			this.addPattern(whenThisAttacks());

			EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (5)(R)(R).");
			mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay.parameters.put(EventType.Parameter.PLAYER, You.instance());
			mayPay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("5RR")));

			EventFactory moreFighting = new EventFactory(HELLKITE_CHARGER_EVENT, "Untap all attacking creatures and after this phase, there is an additional combat phase");
			moreFighting.parameters.put(EventType.Parameter.CAUSE, This.instance());
			moreFighting.parameters.put(EventType.Parameter.OBJECT, Attacking.instance());

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (5)(R)(R). If you do, untap all attacking creatures and after this phase, there is an additional combat phase.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(moreFighting));
			this.addEffect(effect);
		}
	}

	public HellkiteCharger(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Whenever Hellkite Charger attacks, you may pay (5)(R)(R). If you do,
		// untap all attacking creatures and after this phase, there is an
		// additional combat phase.
		this.addAbility(new MayPayForAnotherCombatPhase(state));
	}
}
