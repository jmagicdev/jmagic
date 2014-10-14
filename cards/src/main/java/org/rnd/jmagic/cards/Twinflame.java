package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Twinflame")
@Types({Type.SORCERY})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class Twinflame extends Card
{
	public Twinflame(GameState state)
	{
		super(state);

		// Strive \u2014 Twinflame costs (2)(R) more to cast for each target
		// beyond the first.
		this.addAbility(new org.rnd.jmagic.abilities.Strive(state, this.getName(), "(2)(R)"));

		// Choose any number of target creatures you control.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "any number of target creatures").setNumber(0, null));

		// For each of them, put a token that's a copy of that creature onto the
		// battlefield.
		DynamicEvaluation eachCreature = DynamicEvaluation.instance();

		EventFactory eachToken = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token that's a copy of target creature onto the battlefield");
		eachToken.parameters.put(EventType.Parameter.CAUSE, This.instance());
		eachToken.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		eachToken.parameters.put(EventType.Parameter.OBJECT, eachCreature);

		EventFactory allTokens = new EventFactory(FOR_EACH, "Choose any number of target creatures you control. For each of them, put a token that's a copy of that creature onto the battlefield.");
		allTokens.parameters.put(EventType.Parameter.OBJECT, target);
		allTokens.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachCreature));
		allTokens.parameters.put(EventType.Parameter.EFFECT, Identity.instance(eachToken));
		this.addEffect(allTokens);

		// Those tokens have haste.
		SetGenerator thoseTokens = NewObjectOf.instance(ForEachResult.instance(allTokens, target));
		EventFactory giveHaste = addAbilityUntilEndOfTurn(thoseTokens, org.rnd.jmagic.abilities.keywords.Haste.class, "Those tokens have haste.");
		giveHaste.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
		this.addEffect(giveHaste);

		// Exile them at the beginning of the next end step.
		EventFactory exile = exile(delayedTriggerContext(thoseTokens), "Exile those tokens.");

		EventFactory exileLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Exile them at the beginning of the next end step.");
		exileLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		exileLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfEachEndStep()));
		exileLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile));
		this.addEffect(exileLater);
	}
}
