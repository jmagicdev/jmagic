package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.trackers.*;

@Name("Angelic Arbiter")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("5WW")
@ColorIdentity({Color.WHITE})
public final class AngelicArbiter extends Card
{
	public static final class DontAttack extends StaticAbility
	{
		public static final class CastTracker extends Tracker<java.util.Set<Integer>>
		{
			public static final class Generator extends SetGenerator
			{
				private static final Generator _instance = new Generator();

				public static Generator instance()
				{
					return _instance;
				}

				private Generator()
				{
					// Singleton constructor
				}

				@Override
				public Set evaluate(GameState state, Identified thisObject)
				{
					CastTracker tracker = state.getTracker(CastTracker.class);
					Set ret = new Set();
					for(int i: tracker.getValue(state))
						ret.add(state.get(i));
					return ret;
				}
			}

			private java.util.Set<Integer> values = new java.util.HashSet<Integer>();
			private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.values);

			@Override
			protected CastTracker clone()
			{
				CastTracker ret = (CastTracker)super.clone();
				ret.values = new java.util.HashSet<Integer>(this.values);
				ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.values);
				return ret;
			}

			@Override
			protected java.util.Set<Integer> getValueInternal()
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
				if(event.type != EventType.BECOMES_PLAYED)
					return false;

				return SetPattern.CASTABLE.match(state, null, event.parametersNow.get(EventType.Parameter.OBJECT).evaluate(state, null));
			}

			@Override
			protected void update(GameState state, Event event)
			{
				Player player = event.parametersNow.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class);
				this.values.add(player.ID);
			}
		}

		public DontAttack(GameState state)
		{
			super(state, "Each opponent who cast a spell this turn can't attack with creatures.");

			state.ensureTracker(new CastTracker());

			SetGenerator restriction = Intersect.instance(Attacking.instance(), ControlledBy.instance(CastTracker.Generator.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public static final class DontCast extends StaticAbility
	{
		public DontCast(GameState state)
		{
			super(state, "Each opponent who attacked with a creature this turn can't cast spells.");

			state.ensureTracker(new AttackTracker());
			PlayProhibition castSomething = new PlayProhibition(AttackedWithACreatureThisTurn.instance(), (c -> true));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSomething));
			this.addEffectPart(part);
		}
	}

	public AngelicArbiter(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Each opponent who cast a spell this turn can't attack with creatures.
		this.addAbility(new DontAttack(state));

		// Each opponent who attacked with a creature this turn can't cast
		// spells.
		this.addAbility(new DontCast(state));
	}
}
