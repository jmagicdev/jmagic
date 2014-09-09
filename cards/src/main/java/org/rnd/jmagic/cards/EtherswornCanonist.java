package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ethersworn Canonist")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class EtherswornCanonist extends Card
{
	public static final class CastNonartifactSpellThisTurn extends SetGenerator
	{
		public static final class Tracker extends org.rnd.jmagic.engine.Tracker<java.util.Collection<Integer>>
		{
			private java.util.HashSet<Integer> values = new java.util.HashSet<Integer>();
			private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.values);

			@SuppressWarnings("unchecked")
			@Override
			public Tracker clone()
			{
				Tracker ret = (Tracker)super.clone();
				ret.values = (java.util.HashSet<Integer>)this.values.clone();
				ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.values);
				return ret;
			}

			@Override
			protected java.util.Collection<Integer> getValueInternal()
			{
				return this.unmodifiable;
			}

			@Override
			protected boolean match(GameState state, Event event)
			{
				if(event.type != EventType.BECOMES_PLAYED)
					return false;

				GameObject cast = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class);
				if(!cast.isSpell())
					return false;
				return !(cast.getTypes().contains(Type.ARTIFACT));
			}

			@Override
			protected void reset()
			{
				this.values.clear();
			}

			@Override
			protected void update(GameState state, Event event)
			{
				this.values.add(event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID);
			}
		}

		private static SetGenerator _instance = null;

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new CastNonartifactSpellThisTurn();
			return _instance;
		}

		private CastNonartifactSpellThisTurn()
		{
			// singleton
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Set ret = new Set();
			for(int playerID: state.getTracker(Tracker.class).getValue(state))
				ret.add(state.<Player>get(playerID));
			return ret;
		}
	}

	public static final class NonartifactRuleOfLaw extends StaticAbility
	{
		public NonartifactRuleOfLaw(GameState state)
		{
			super(state, "Each player who has cast a nonartifact spell this turn can't cast additional nonartifact spells.");

			MultipleSetPattern nonartifactSpells = new MultipleSetPattern(true);
			nonartifactSpells.addPattern(new SimpleSetPattern(DoesntHaveType.instance(Type.ARTIFACT)));
			nonartifactSpells.addPattern(SetPattern.CASTABLE);

			state.ensureTracker(new CastNonartifactSpellThisTurn.Tracker());
			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSpell.put(EventType.Parameter.PLAYER, CastNonartifactSpellThisTurn.instance());
			castSpell.put(EventType.Parameter.OBJECT, nonartifactSpells);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));
			this.addEffectPart(part);
		}
	}

	public EtherswornCanonist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Each player who has cast a nonartifact spell this turn can't cast
		// additional nonartifact spells.
		this.addAbility(new NonartifactRuleOfLaw(state));
	}
}
