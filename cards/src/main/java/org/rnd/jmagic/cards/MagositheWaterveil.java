package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Magosi, the Waterveil")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class MagositheWaterveil extends Card
{
	public static final class GainCounters extends ActivatedAbility
	{
		public GainCounters(GameState state)
		{
			super(state, "(U), (T): Put an eon counter on Magosi, the Waterveil. Skip your next turn.");
			this.setManaCost(new ManaPool("U"));
			this.costsTap = true;

			this.addEffect(putCounters(1, Counter.CounterType.EON, ABILITY_SOURCE_OF_THIS, "Put an eon counter on Magosi, the Waterveil."));

			SimpleEventPattern yourNextTurn = new SimpleEventPattern(EventType.BEGIN_TURN);
			yourNextTurn.put(EventType.Parameter.TURN, TurnOf.instance(ControllerOf.instance(This.instance())));

			EventReplacementEffect skipReplacement = new EventReplacementEffect(this.game, "You skip your next turn.", yourNextTurn);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(skipReplacement));

			EventType.ParameterMap skipParameters = new EventType.ParameterMap();
			skipParameters.put(EventType.Parameter.CAUSE, This.instance());
			skipParameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			skipParameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			skipParameters.put(EventType.Parameter.USES, numberGenerator(1));
			this.addEffect(new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, skipParameters, "Skip your next turn."));
		}
	}

	public static final class ExtraTurn extends ActivatedAbility
	{
		public ExtraTurn(GameState state)
		{
			super(state, "(T), Remove an eon counter from Magosi, the Waterveil and return it to its owner's hand: Take an extra turn after this one.");
			this.costsTap = true;

			this.addCost(removeCountersFromThis(1, Counter.CounterType.EON, "Magosi, the Waterveil"));
			this.addCost(bounce(ABILITY_SOURCE_OF_THIS, "Return Magosi, the Waterveil to its owner's hand"));

			this.addEffect(takeExtraTurns(You.instance(), 1, "Take an extra turn after this one."));
		}
	}

	public MagositheWaterveil(GameState state)
	{
		super(state);

		// Magosi, the Waterveil enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForU(state));

		// (U), (T): Put an eon counter on Magosi, the Waterveil. Skip your next
		// turn.
		this.addAbility(new GainCounters(state));

		// (T), Remove an eon counter from Magosi, the Waterveil and return it
		// to its owner's hand: Take an extra turn after this one.
		this.addAbility(new ExtraTurn(state));
	}
}
