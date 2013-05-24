package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Refraction Trap")
@Types({Type.INSTANT})
@SubTypes({SubType.TRAP})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class RefractionTrap extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("RefractionTrap", "Choose a source of damage.", true);

	public static final class RefractionTrapEffect extends DamageReplacementEffect
	{
		private final int target;
		private final int chosenSource;

		public RefractionTrapEffect(Game game, String name, Identified target, GameObject chosenSource)
		{
			super(game, name);
			this.target = target.ID;
			this.chosenSource = chosenSource.ID;
			this.makePreventionEffect();
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			Player you = ((GameObject)this.getSourceObject(context.game.actualState)).getController(context.game.actualState);
			Set yourPermanents = ControlledBy.instance(Identity.instance(you)).evaluate(context.game, this.getSourceObject(context.game.actualState));

			DamageAssignment.Batch ret = new DamageAssignment.Batch();
			for(DamageAssignment damage: damageAssignments)
			{
				if(this.chosenSource != damage.sourceID)
					continue;

				if(you.ID == damage.takerID)
					ret.add(damage);
				else if(yourPermanents.contains(context.state.get(damage.takerID)))
					ret.add(damage);
			}
			return ret;
		}

		@Override
		public java.util.List<EventFactory> prevent(DamageAssignment.Batch damageAssignments)
		{
			if(damageAssignments.isEmpty())
				return java.util.Collections.emptyList();

			FloatingContinuousEffect fce = this.getFloatingContinuousEffect(this.game.physicalState);
			if(fce.damage == 0)
				return java.util.Collections.emptyList();

			// This will never get more damage than it can prevent
			int prevented = damageAssignments.size();
			fce.damage -= prevented;
			damageAssignments.clear();
			return java.util.Collections.singletonList(spellDealDamage(prevented, Identity.instance(this.game.actualState.get(this.target)), "Refraction Trap deals that much damage to target creature or player."));
		}

		@Override
		public java.util.Collection<GameObject> refersTo(GameState state)
		{
			return java.util.Collections.singleton((GameObject)this.getSourceObject(state));
		}
	}

	public static final class OpponentCastRedInstantOrSorceryThisTurn extends SetGenerator
	{
		/**
		 * Tracks IDs of players that have cast a red instant or sorcery spell
		 * this turn
		 */
		public static final class Tracker extends org.rnd.jmagic.engine.Tracker<java.util.Collection<Integer>>
		{
			private java.util.HashSet<Integer> IDs = new java.util.HashSet<Integer>();
			private java.util.Set<Integer> unmodifiable = java.util.Collections.unmodifiableSet(this.IDs);

			@SuppressWarnings("unchecked")
			@Override
			public Tracker clone()
			{
				Tracker ret = (Tracker)super.clone();
				ret.IDs = (java.util.HashSet<Integer>)this.IDs.clone();
				ret.unmodifiable = java.util.Collections.unmodifiableSet(ret.IDs);
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

				GameObject object = event.parameters.get(EventType.Parameter.OBJECT).evaluate(state, null).getOne(GameObject.class);
				if(!object.isSpell())
					return false;
				if(!object.getColors().contains(Color.RED))
					return false;
				if(!object.getTypes().contains(Type.INSTANT) && !object.getTypes().contains(Type.SORCERY))
					return false;

				return true;
			}

			@Override
			protected void reset()
			{
				this.IDs.clear();
			}

			@Override
			protected void update(GameState state, Event event)
			{
				this.IDs.add(event.parameters.get(EventType.Parameter.PLAYER).evaluate(state, null).getOne(Player.class).ID);
			}

		}

		private static OpponentCastRedInstantOrSorceryThisTurn _instance = null;

		public static OpponentCastRedInstantOrSorceryThisTurn instance()
		{
			if(_instance == null)
				_instance = new OpponentCastRedInstantOrSorceryThisTurn();
			return _instance;
		}

		private OpponentCastRedInstantOrSorceryThisTurn()
		{
			// singleton
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Player you = ((GameObject)thisObject).getController(state);
			Set opponents = OpponentsOf.get(state, you);

			java.util.Collection<Integer> flagValue = state.getTracker(Tracker.class).getValue(state);

			for(Player p: opponents.getAll(Player.class))
				if(flagValue.contains(p.ID))
					return NonEmpty.set;

			return Empty.set;
		}
	}

	/**
	 * @eparam CAUSE: refraction trap
	 * @eparam PLAYER: controller of CAUSE
	 * @eparam OBJECT: permanents controlled by PLAYER
	 * @eparam TARGET: creature or player targeted by CAUSE
	 */
	public static final EventType REFRACTION_TRAP_EVENT = new EventType("REFRACTION_TRAP_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			final Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);
			final Identified target = parameters.get(Parameter.TARGET).getOne(Identified.class);

			Set damageSources = AllSourcesOfDamage.instance().evaluate(game.actualState, null);
			final GameObject chosenSource = you.sanitizeAndChoose(game.actualState, 1, damageSources.getAll(GameObject.class), PlayerInterface.ChoiceType.DAMAGE_SOURCE, REASON).iterator().next();

			DamageReplacementEffect replacement = new RefractionTrapEffect(game, "Prevent the next 3 damage that a source of your choice would deal to you and/or permanents you control this turn. If damage is prevented this way, Refraction Trap deals that much damage to target creature or player.", target, chosenSource);

			ContinuousEffect.Part part = replacementEffectPart(replacement);

			java.util.Map<Parameter, Set> fceParameters = new java.util.HashMap<Parameter, Set>();
			fceParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			fceParameters.put(Parameter.EFFECT, new Set(part));
			fceParameters.put(Parameter.DAMAGE, new Set(3));
			Event createFce = createEvent(game, "Prevent the next 3 damage that a source of your choice would deal to you and/or permanents you control this turn. If damage is prevented this way, Refraction Trap deals that much damage to target creature or player.", CREATE_FLOATING_CONTINUOUS_EFFECT, fceParameters);
			createFce.perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	public RefractionTrap(GameState state)
	{
		super(state);

		// If an opponent cast a red instant or sorcery spell this turn, you may
		// pay (W) rather than pay Refraction Trap's mana cost.
		state.ensureTracker(new OpponentCastRedInstantOrSorceryThisTurn.Tracker());
		this.addAbility(new org.rnd.jmagic.abilities.Trap(state, this.getName(), OpponentCastRedInstantOrSorceryThisTurn.instance(), "If an opponent cast a red instant or sorcery spell this turn", "(W)"));

		// Prevent the next 3 damage that a source of your choice would deal to
		// you and/or permanents you control this turn. If damage is prevented
		// this way, Refraction Trap deals that much damage to target creature
		// or player.
		Target t = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		EventFactory effect = new EventFactory(REFRACTION_TRAP_EVENT, "Prevent the next 3 damage that a source of your choice would deal to you and/or permanents you control this turn. If damage is prevented this way, Refraction Trap deals that much damage to target creature or player.");
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
		effect.parameters.put(EventType.Parameter.OBJECT, ControlledBy.instance(You.instance()));
		effect.parameters.put(EventType.Parameter.TARGET, targetedBy(t));
		this.addEffect(effect);
	}
}
