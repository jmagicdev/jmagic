package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Kira, Great Glass-Spinner")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = BetrayersOfKamigawa.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class KiraGreatGlassSpinner extends Card
{
	public static final class TargetedThisTurn extends SetGenerator
	{
		public static final class Tracker extends org.rnd.jmagic.engine.Tracker<java.util.Collection<Integer>>
		{
			private java.util.Set<Integer> values = new java.util.HashSet<Integer>();
			private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.values);

			@Override
			protected Tracker clone()
			{
				Tracker ret = (Tracker)super.clone();
				ret.values = new java.util.HashSet<Integer>(this.values);
				ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.values);
				return ret;
			}

			@Override
			protected java.util.Collection<Integer> getValueInternal()
			{
				return this.unmodifiable;
			}

			@Override
			protected void reset()
			{
				this.values.clear();
			}

			@Override
			protected boolean match(GameState state, Event event)
			{
				return event.type == EventType.BECOMES_TARGET;
			}

			@Override
			protected void update(GameState state, Event event)
			{
				Identified target = event.parameters.get(EventType.Parameter.TARGET).evaluate(state, event.getSource()).getOne(Identified.class);
				this.values.add(target.ID);
			}
		}

		private static SetGenerator _instance;

		private TargetedThisTurn()
		{
			// singleton generator
		}

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new TargetedThisTurn();
			return _instance;
		}

		@Override
		public org.rnd.jmagic.engine.Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();
			for(int ID: state.getTracker(Tracker.class).getValue(state))
				ret.add(state.get(ID));
			return ret;
		}
	}

	public static final class Glass extends EventTriggeredAbility
	{
		public Glass(GameState state)
		{
			super(state, "Whenever this creature becomes the target of a spell or ability for the first time in a turn, counter that spell or ability.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_TARGET);
			pattern.put(EventType.Parameter.TARGET, RelativeComplement.instance(ABILITY_SOURCE_OF_THIS, TargetedThisTurn.instance()));
			state.ensureTracker(new TargetedThisTurn.Tracker());

			SetGenerator thatSpell = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			this.addEffect(counter(thatSpell, "Counter that spell or ability."));
		}
	}

	public static final class KiraGreatGlassSpinnerAbility1 extends StaticAbility
	{
		public KiraGreatGlassSpinnerAbility1(GameState state)
		{
			super(state, "Creatures you control have \"Whenever this creature becomes the target of a spell or ability for the first time in a turn, counter that spell or ability.\"");

			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, Glass.class));
		}
	}

	public KiraGreatGlassSpinner(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Creatures you control have
		// "Whenever this creature becomes the target of a spell or ability for the first time in a turn, counter that spell or ability."
		this.addAbility(new KiraGreatGlassSpinnerAbility1(state));
	}
}
