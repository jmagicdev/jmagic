package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Protean Hydra")
@Types({Type.CREATURE})
@SubTypes({SubType.HYDRA})
@ManaCost("XG")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2010.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class ProteanHydra extends Card
{
	public static final class ManyHeads extends StaticAbility
	{
		public ManyHeads(GameState state)
		{
			super(state, "Protean Hydra enters the battlefield with X +1/+1 counters on it.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, this.getName());
			replacement.addPattern(asThisEntersTheBattlefield());
			replacement.addEffect(putCounters(ValueOfX.instance(This.instance()), Counter.CounterType.PLUS_ONE_PLUS_ONE, NewObjectOf.instance(replacement.replacedByThis()), "Protean Hydra enters the battlefield with X +1/+1 counters on it."));

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class DamageCausesLossOfHead extends StaticAbility
	{
		public static final class PreventDamageEffect extends DamageReplacementEffect
		{
			public PreventDamageEffect(Game game)
			{
				super(game, "If damage would be dealt to Protean Hydra, prevent that damage and remove that many +1/+1 counters from it.");
				this.makePreventionEffect();
			}

			@Override
			public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
			{
				DamageAssignment.Batch ret = new DamageAssignment.Batch();

				int hydra = this.getStaticSourceObject(context.game.actualState).ID;
				for(DamageAssignment assignment: damageAssignments)
					if(assignment.takerID == hydra)
						ret.add(assignment);

				return ret;
			}

			@Override
			public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
			{
				damageAssignments.clear();
				return new java.util.LinkedList<EventFactory>();
			}

			@Override
			public java.util.List<EventFactory> replace(DamageAssignment.Batch damageAssignments)
			{
				if(damageAssignments.isEmpty())
					return new java.util.LinkedList<EventFactory>();

				SetGenerator thisThing = Identity.instance(this.getStaticSourceObject(this.game.physicalState));
				EventFactory effect = new EventFactory(EventType.REMOVE_COUNTERS, "Remove that many +1/+1 counters from it");
				effect.parameters.put(EventType.Parameter.CAUSE, IdentifiedWithID.instance(damageAssignments.iterator().next().sourceID));
				effect.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
				effect.parameters.put(EventType.Parameter.NUMBER, numberGenerator(damageAssignments.size()));
				effect.parameters.put(EventType.Parameter.OBJECT, thisThing);
				return java.util.Collections.singletonList(effect);
			}
		}

		public DamageCausesLossOfHead(GameState state)
		{
			super(state, "If damage would be dealt to Protean Hydra, prevent that damage and remove that many +1/+1 counters from it.");

			this.addEffectPart(replacementEffectPart(new PreventDamageEffect(state.game)));
		}
	}

	public static final class ApplyDirectlyToTheForehead extends EventTriggeredAbility
	{
		public ApplyDirectlyToTheForehead(GameState state)
		{
			super(state, "Whenever a +1/+1 counter is removed from Protean Hydra, put two +1/+1 counters on it at the beginning of the next end step.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.REMOVE_ONE_COUNTER);
			pattern.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			pattern.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			this.addPattern(pattern);

			SetGenerator addEvent = Identity.instance(putCounters(2, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put two +1/+1 counters on Protean Hydra."));

			EventType.ParameterMap triggerParameters = new EventType.ParameterMap();
			triggerParameters.put(EventType.Parameter.CAUSE, This.instance());
			triggerParameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
			triggerParameters.put(EventType.Parameter.EFFECT, addEvent);
			this.addEffect(new EventFactory(EventType.CREATE_DELAYED_TRIGGER, triggerParameters, "Put two +1/+1 counters on Protean Hydra at the beginning of the next end step."));
		}
	}

	public ProteanHydra(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		this.addAbility(new ManyHeads(state));
		this.addAbility(new DamageCausesLossOfHead(state));
		this.addAbility(new ApplyDirectlyToTheForehead(state));
	}
}
