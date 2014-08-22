package org.rnd.jmagic.engine;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.CardLoader.*;
import org.rnd.jmagic.engine.PlayerInterface.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

/** THE GAME!!! */
public class Game
{
	public static final boolean debugging = false;

	public static LoseReason LESS_THAN_ZERO_LIFE = new LoseReason("LessThanZeroLife");
	public static LoseReason FAILED_TO_DRAW = new LoseReason("FailedToDraw");
	public static LoseReason TEN_OR_MORE_POISON_COUNTERS = new LoseReason("TenOrMorePoisonCounters");

	public static class LoseReason
	{
		private final Object _reason;

		public LoseReason(Object reason)
		{
			this._reason = reason;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this._reason == null) ? 0 : this._reason.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			LoseReason other = (LoseReason)obj;
			if(this._reason == null)
			{
				if(other._reason != null)
					return false;
			}
			else if(!this._reason.equals(other._reason))
				return false;
			return true;
		}
	}

	public static class CopyEffectSnapshotKey
	{
		private final int continuousEffectID;
		private final int targetID;

		public CopyEffectSnapshotKey(int continuousEffectID, int targetID)
		{
			this.continuousEffectID = continuousEffectID;
			this.targetID = targetID;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + this.continuousEffectID;
			result = prime * result + this.targetID;
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			CopyEffectSnapshotKey other = (CopyEffectSnapshotKey)obj;
			if(this.continuousEffectID != other.continuousEffectID)
				return false;
			if(this.targetID != other.targetID)
				return false;
			return true;
		}
	}

	/**
	 * Exception to be thrown when to end the game normally. {@link Game#run}
	 * catches this and reports the winner.
	 */
	public static class GameOverException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
	}

	public static class GrantedAbilityKey
	{
		/**
		 * This field is used when granting an ability explicitly. It will hold
		 * the class literal of the granted ability.
		 */
		private Class<?> abilityClass;

		/**
		 * This field is used when granting an ability that already exists. It
		 * will hold the id of the original ability.
		 */
		private int originalAbilityID;

		private int destinationID;

		private int grantedByID;

		/**
		 * This constructor is used when an object is being granted an ability
		 * by name. For instance, Assault Strobe grants a creature double
		 * strike.
		 */
		public GrantedAbilityKey(Identified grantedBy, Class<?> ability, int destID)
		{
			this.abilityClass = ability;
			if(null == grantedBy)
				this.grantedByID = -1;
			else
				this.grantedByID = grantedBy.ID;
			this.destinationID = destID;

			this.originalAbilityID = -1;
		}

		/**
		 * This constructor is used when an object is being granted an ability
		 * that already exists. For instance, Necrotic Ooze's ability grants it
		 * all activated abilities of creature cards in graveyards.
		 */
		public GrantedAbilityKey(Identified grantedBy, Identified originalAbility, int destID)
		{
			this(grantedBy, (Class<?>)null, destID);
			if(originalAbility != null)
				this.originalAbilityID = originalAbility.ID;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			GrantedAbilityKey other = (GrantedAbilityKey)obj;
			if(this.abilityClass == null)
			{
				if(other.abilityClass != null)
					return false;
			}
			else if(!this.abilityClass.equals(other.abilityClass))
				return false;
			if(this.destinationID != other.destinationID)
				return false;
			if(this.grantedByID != other.grantedByID)
				return false;
			if(this.originalAbilityID != other.originalAbilityID)
				return false;
			return true;
		}

		@Override
		public int hashCode()
		{
			int result = 31 + ((this.abilityClass == null) ? 0 : this.abilityClass.hashCode());
			result = 31 * result + this.destinationID;
			result = 31 * result + this.grantedByID;
			result = 31 * result + this.originalAbilityID;
			return result;
		}
	}

	public static class GrantedFactoryAbilityKey extends GrantedAbilityKey
	{
		private Object factory;

		/**
		 * @param factory The factory that created the ability
		 * @param grantedBy The thing granting the ability
		 * @param ability The ability class
		 * @param destID The thing receiving the ability
		 */
		public GrantedFactoryAbilityKey(Object factory, Identified grantedBy, Class<?> ability, int destID)
		{
			super(grantedBy, ability, destID);
			this.factory = factory;
		}

		@Override
		public int hashCode()
		{
			return 31 * super.hashCode() + ((this.factory == null) ? 0 : this.factory.hashCode());
		}

		@Override
		public boolean equals(Object obj)
		{
			if(this == obj)
				return true;
			if(!super.equals(obj))
				return false;
			if(!(obj instanceof GrantedFactoryAbilityKey))
				return false;
			GrantedFactoryAbilityKey other = (GrantedFactoryAbilityKey)obj;
			if(this.factory == null)
			{
				if(other.factory != null)
					return false;
			}
			else if(!this.factory.equals(other.factory))
				return false;
			return true;
		}
	}

	/**
	 * This is an exception for interfaces to throw when they are interrupted by
	 * the user. Game.run is guaranteed not to catch exceptions of this type.
	 */
	public static class InterruptedGameException extends RuntimeException
	{
		private static final long serialVersionUID = 1;
	}

	public static abstract class IntrinsicManaAbility extends org.rnd.jmagic.abilities.TapForMana
	{
		public static final class Forest extends IntrinsicManaAbility
		{
			public Forest(GameState state)
			{
				super(state, SubType.FOREST);
			}
		}

		public static final class Island extends IntrinsicManaAbility
		{
			public Island(GameState state)
			{
				super(state, SubType.ISLAND);
			}
		}

		public static final class Mountain extends IntrinsicManaAbility
		{
			public Mountain(GameState state)
			{
				super(state, SubType.MOUNTAIN);
			}
		}

		public static final class Plains extends IntrinsicManaAbility
		{
			public Plains(GameState state)
			{
				super(state, SubType.PLAINS);
			}
		}

		public static final class Swamp extends IntrinsicManaAbility
		{
			public Swamp(GameState state)
			{
				super(state, SubType.SWAMP);
			}
		}

		protected static final java.util.Map<SubType, Class<? extends IntrinsicManaAbility>> classesByType = new java.util.HashMap<SubType, Class<? extends IntrinsicManaAbility>>();

		static
		{
			classesByType.put(SubType.PLAINS, Plains.class);
			classesByType.put(SubType.ISLAND, Island.class);
			classesByType.put(SubType.SWAMP, Swamp.class);
			classesByType.put(SubType.MOUNTAIN, Mountain.class);
			classesByType.put(SubType.FOREST, Forest.class);
		}

		public final SubType type;

		protected IntrinsicManaAbility(GameState state, SubType type)
		{
			super(state, "(" + Color.getColorForType(type).getLetter() + ")");
			this.type = type;
		}

		@Override
		protected boolean addsMana()
		{
			return true;
		}
	}

	public static final class LoyaltyCountersAbility extends StaticAbility
	{
		public LoyaltyCountersAbility(GameState state)
		{
			super(state, "This permanent enters the battlefield with a number of loyalty counters on it equal to its printed loyalty number.");

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(state.game, "This permanent enters the battlefield with a number of loyalty counters on it equal to its printed loyalty number.");
			replacement.addPattern(asThisEntersTheBattlefield());

			SetGenerator entersTheBattlefield = replacement.replacedByThis();

			EventFactory putCounters = new EventFactory(EventType.PUT_COUNTERS, "Put a number of loyalty counters on this permanent equal to its printed loyalty number");
			putCounters.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putCounters.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.LOYALTY));
			putCounters.parameters.put(EventType.Parameter.NUMBER, LoyaltyOf.instance(OldObjectOf.instance(entersTheBattlefield)));
			putCounters.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(entersTheBattlefield));
			replacement.addEffect(putCounters);

			this.addEffectPart(replacementEffectPart(replacement));

			this.canApply = NonEmpty.instance();
		}
	}

	public static final class PlaneswalkerRedirectionEffect extends DamageReplacementEffect
	{
		public PlaneswalkerRedirectionEffect(Game game, String name)
		{
			super(game, name);
		}

		@Override
		public DamageAssignment.Batch match(Event context, DamageAssignment.Batch damageAssignments)
		{
			DamageAssignment.Batch ret = new DamageAssignment.Batch();

			// Only continue if its not combat damage
			if(EventType.DEAL_COMBAT_DAMAGE == context.type)
				return ret;

			SetGenerator planeswalkerPermanents = Intersect.instance(Permanents.instance(), HasType.instance(Type.PLANESWALKER));
			Set playersWithWalkers = ControllerOf.instance(planeswalkerPermanents).evaluate(this.game, null);

			for(DamageAssignment damage: damageAssignments)
			{
				// the taker of damage must be a player that controls a
				// planeswalker
				Identified taker = context.state.get(damage.takerID);
				if(!playersWithWalkers.contains(taker))
					continue;

				// the source of damage must be controlled by an
				// opponent of that player
				Set opponents = OpponentsOf.get(this.game.actualState, (Player)taker);
				Player controller = context.state.<GameObject>get(damage.sourceID).getController(this.game.actualState);
				if(!opponents.contains(controller))
					continue;

				ret.add(damage);
			}

			return ret;
		}

		@Override
		public java.util.List<EventFactory> redirect(java.util.Map<DamageAssignment, DamageAssignment> damageAssignments)
		{
			if(damageAssignments.size() == 0)
				return new java.util.LinkedList<EventFactory>();

			DamageAssignment singleAssignment = damageAssignments.keySet().iterator().next();
			GameObject source = this.game.actualState.get(singleAssignment.sourceID);
			Identified taker = this.game.actualState.get(singleAssignment.takerID);
			Player controllerOfSource = source.getController(source.state);
			SetGenerator takersPermanents = ControlledBy.instance(Identity.instance(taker));
			java.util.Set<GameObject> takersPlaneswalkers = Intersect.instance(takersPermanents, HasType.instance(Type.PLANESWALKER)).evaluate(this.game, null).getAll(GameObject.class);

			GameObject newTaker = controllerOfSource.sanitizeAndChoose(this.game.actualState, 1, takersPlaneswalkers, org.rnd.jmagic.engine.PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.PLANESWALKER_REDIRECT).get(0);

			for(DamageAssignment assignment: damageAssignments.keySet())
				damageAssignments.put(assignment, new DamageAssignment(source, newTaker));

			return new java.util.LinkedList<EventFactory>();
		}
	}

	/** Exception to be thrown when the priority should be stopped. */
	public static class StopPriorityException extends RuntimeException
	{
		private static final long serialVersionUID = 1L;
	}

	/**
	 * @eparam RESULT: empty
	 */
	public static final EventType STATE_BASED_ACTIONS = new EventType("STATE_BASED_ACTIONS")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		private void addLosingPlayer(java.util.Map<Player, java.util.Set<LoseReason>> playersLosing, Player player, LoseReason reason)
		{
			if(!playersLosing.containsKey(player))
				playersLosing.put(player, new java.util.HashSet<LoseReason>());
			playersLosing.get(player).add(reason);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> emptyParameters)
		{
			Set stateBasedActions = new Set();
			Set moveToGraveyard = new Set();
			Set detach = new Set();
			Set destroy = new Set();
			java.util.Map<Player, java.util.Set<LoseReason>> playersLosing = new java.util.HashMap<Player, java.util.Set<LoseReason>>();

			// 704.5a If a player has 0 or less life, he or she loses the game.
			// 704.5b If a player attempted to draw a card from an empty library
			// since the last time state-based actions were checked, he or she
			// loses the game.
			// 704.5c If a player has ten or more poison counters, he or she
			// loses the game.
			for(Player player: game.actualState.players)
				if(!(player.outOfGame))
				{
					if(player.lifeTotal <= 0)
					{
						addLosingPlayer(playersLosing, player, LESS_THAN_ZERO_LIFE);
					}
					if(player.unableToDraw)
					{
						addLosingPlayer(playersLosing, player, FAILED_TO_DRAW);
					}
					if(player.countPoisonCounters() >= 10)
					{
						addLosingPlayer(playersLosing, player, TEN_OR_MORE_POISON_COUNTERS);
					}
				}

			for(GameObject object: game.actualState.getAllObjects())
			{
				// 704.5d If a token is phased out, or is in a zone other than
				// the battlefield, it ceases to exist.
				// TODO : phased out tokens
				if(object.isToken() && !object.isPermanent())
					object.getZone().getPhysical().remove(object);

				// 704.5e If a copy of a spell is in a zone other than the
				// stack, it ceases to exist.
				if(object.isSpellCopy() && object.zoneID != game.actualState.stack().ID)
					object.getZone().getPhysical().remove(object);

				if(object.isPermanent())
				{
					if(object.getTypes().contains(Type.CREATURE))
					{
						// 704.5f If a creature has toughness 0 or less, it's
						// put into its owner's graveyard. Regeneration can't
						// replace this event.
						if(object.getToughness() <= 0)
							moveToGraveyard.add(object);
						// 704.5g If a creature has toughness greater than 0,
						// and the total damage marked on it is greater than or
						// equal to its toughness, that creature has been dealt
						// lethal damage and is destroyed. Regeneration can
						// replace this event.
						else if(object.getDamage() >= object.getToughness())
							destroy.add(object);
						// 704.5h If a creature has been dealt damage by a
						// source with deathtouch since the last time
						// state-based actions were checked, that creature is
						// destroyed. Regeneration can replace this event.
						else if(object.isDamagedByDeathtouchSinceLastSBA())
							destroy.add(object);
					}

					// 704.5i If a planeswalker has loyalty 0, it's put into its
					// owner's graveyard.
					if(object.getTypes().contains(Type.PLANESWALKER) && LoyaltyOf.get(object) == 0)
						moveToGraveyard.add(object);

					// 704.5n If an Aura is attached to an illegal object or
					// player, or is not attached to an object or player, that
					// Aura is put into its owner's graveyard.
					if(object.getSubTypes().contains(SubType.AURA))
					{
						if(object.getAttachedTo() == -1 || object.getTypes().contains(Type.CREATURE))
							moveToGraveyard.add(object);
						else
							for(Keyword k: object.getKeywordAbilities())
								if(k.isEnchant())
								{
									SetGenerator enchantRestriction = null;
									enchantRestriction = ((org.rnd.jmagic.abilities.keywords.Enchant)k).filter;

									if(game.actualState.containsIdentified(object.getAttachedTo()))
									{
										Identified attachedToObject = game.actualState.get(object.getAttachedTo());
										if(!enchantRestriction.evaluate(game, object).contains(attachedToObject))
											moveToGraveyard.add(object);
									}
									else
									{
										Player attachedToPlayer = game.actualState.get(object.getAttachedTo());
										if(!enchantRestriction.evaluate(game, object).contains(attachedToPlayer))
											moveToGraveyard.add(object);
									}
								}
					}

					// 704.5p If an Equipment or Fortification is attached to an
					// illegal permanent, it becomes unattached from that
					// permanent. It remains on the battlefield.
					// TODO : Ticket 132 (O-Naginata, Konda's Banner)
					if(object.getSubTypes().contains(SubType.EQUIPMENT))
					{
						if(object.getAttachedTo() != -1 && object.getTypes().contains(Type.CREATURE))
							detach.add(object);
						else
						{
							GameObject attachedToObject = game.actualState.getByIDObject(object.getAttachedTo());
							if(attachedToObject != null)
							{
								if(!attachedToObject.getTypes().contains(Type.CREATURE))
									detach.add(object);
							}
						}
					}

					// TODO : Ticket 132? (no cards with fortify restrictions
					// yet)
					if(object.getSubTypes().contains(SubType.FORTIFICATION))
					{
						if(object.getAttachedTo() != -1 && object.getTypes().contains(Type.CREATURE))
							detach.add(object);
						else
						{
							GameObject attachedToObject = game.actualState.getByIDObject(object.getAttachedTo());
							if(attachedToObject != null)
							{
								if(!attachedToObject.getTypes().contains(Type.LAND))
									detach.add(object);
							}
						}
					}
				}
			}

			java.util.Map<String, GameObject> legends = new java.util.HashMap<String, GameObject>();
			java.util.Map<SubType, GameObject> planeswalkers = new java.util.HashMap<SubType, GameObject>();
			GameObject newestWorld = null;
			int newestWorldTime = -1;

			for(GameObject object: game.actualState.battlefield().objects)
			{
				// Ignore things that are already going away.
				if(moveToGraveyard.contains(object) || destroy.contains(object))
					continue;

				// 704.5n If an Aura is attached to an illegal object or player,
				// or is not attached to an object or player, that Aura is put
				// into its owner's graveyard.
				if(object.getSubTypes().contains(SubType.AURA))
				{
					Identified bearer = game.actualState.get(object.getAttachedTo());

					if(!(bearer instanceof AttachableTo) || !object.canAttachTo(game, (AttachableTo)bearer))
						moveToGraveyard.add(object);
				}

				// 704.5k If two or more legendary permanents with the same name
				// are on the battlefield, all are put into their owners'
				// graveyards. This is called the "legend rule." If only one of
				// those permanents is legendary, this rule doesn't apply.
				if(object.getSuperTypes().contains(SuperType.LEGENDARY))
				{
					if(legends.containsKey(object.getName()))
					{
						moveToGraveyard.add(object);
						if(legends.get(object.getName()) != null)
						{
							moveToGraveyard.add(legends.get(object.getName()));
							legends.put(object.getName(), null);
						}
					}
					else
						legends.put(object.getName(), object);
				}
				// 704.5m If two or more permanents have the supertype world,
				// all except the one that has been a permanent with the world
				// supertype on the battlefield for the shortest amount of time
				// are put into their owners' graveyards. In the event of a tie
				// for the shortest amount of time, all are put into their
				// owners' graveyards. This is called the "world rule."
				if(object.getSuperTypes().contains(SuperType.WORLD))
				{
					if(object.ID > newestWorldTime)
					{
						if(null != newestWorld)
							moveToGraveyard.add(newestWorld);
						newestWorld = object;
						newestWorldTime = object.ID;
					}
					else if(object.ID == newestWorldTime)
					{
						if(null != newestWorld)
							moveToGraveyard.add(newestWorld);
						moveToGraveyard.add(object);
						newestWorld = null;
					}
					else
						moveToGraveyard.add(object);
				}

				// 704.5p If an Equipment or Fortification is attached to an
				// illegal permanent, it becomes unattached from that permanent.
				// It remains on the battlefield.
				if(object.getAttachedTo() != -1 && (object.getSubTypes().contains(SubType.EQUIPMENT) || object.getSubTypes().contains(SubType.FORTIFICATION)))
				{
					Identified bearer = game.actualState.get(object.getAttachedTo());

					if(!(bearer instanceof AttachableTo) || !object.canAttachTo(game, (AttachableTo)bearer))
						detach.add(object);
				}

				// 704.5q If a permanent that's neither an Aura, an Equipment,
				// nor a Fortification is attached to another permanent, it
				// becomes unattached from that permanent. It remains on the
				// battlefield.
				if(object.getAttachedTo() != -1 && !object.getSubTypes().contains(SubType.AURA) && !object.getSubTypes().contains(SubType.EQUIPMENT) && !object.getSubTypes().contains(SubType.FORTIFICATION))
					detach.add(object);

				// 704.5j If two or more planeswalkers that share a planeswalker
				// type are on the battlefield, all are put into their owners'
				// graveyards. This is called the "planeswalker uniqueness
				// rule."
				if(object.getTypes().contains(Type.PLANESWALKER))
				{
					for(SubType pwType: SubType.getAllSubTypesFor(Type.PLANESWALKER))
					{
						if(!object.getSubTypes().contains(pwType))
							continue;

						if(planeswalkers.containsKey(pwType))
						{
							moveToGraveyard.add(object);
							if(planeswalkers.get(pwType) != null)
							{
								moveToGraveyard.add(planeswalkers.get(pwType));
								planeswalkers.put(pwType, null);
							}
						}
						else
							planeswalkers.put(pwType, object);
					}
				}
			}

			for(java.util.Map.Entry<Player, java.util.Set<LoseReason>> entry: playersLosing.entrySet())
			{
				for(LoseReason reason: entry.getValue())
				{
					Event loseGame = new Event(game.physicalState, playersLosing + " lose" + (playersLosing.size() == 1 ? "s " : " ") + "the game due to state-based actions.", EventType.LOSE_GAME);
					loseGame.parameters.put(EventType.Parameter.CAUSE, Union.instance(CurrentGame.instance(), Identity.instance(reason)));
					loseGame.parameters.put(EventType.Parameter.PLAYER, Identity.instance(entry.getKey()));

					stateBasedActions.add(loseGame);
				}
			}

			if(moveToGraveyard.size() > 0)
			{
				Event moveToYard = new Event(game.physicalState, "Put " + moveToGraveyard + " into owner's graveyard(s) due to state-based actions.", EventType.PUT_INTO_GRAVEYARD);
				moveToYard.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
				moveToYard.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
				moveToYard.parameters.put(EventType.Parameter.OBJECT, Identity.fromCollection(moveToGraveyard));

				stateBasedActions.add(moveToYard);
			}

			destroy = RelativeComplement.get(destroy, moveToGraveyard);
			if(destroy.size() > 0)
			{
				Event destroyEvent = new Event(game.physicalState, "Destroy " + destroy + " due to state-based actions.", EventType.DESTROY_PERMANENTS);
				destroyEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
				destroyEvent.parameters.put(EventType.Parameter.PERMANENT, Identity.fromCollection(destroy));

				stateBasedActions.add(destroyEvent);
			}

			if(detach.size() > 0)
			{
				Event detachEvent = new Event(game.physicalState, "Unattach " + detach + " due to state-based actions.", EventType.UNATTACH);
				detachEvent.parameters.put(EventType.Parameter.OBJECT, Identity.fromCollection(detach));

				stateBasedActions.add(detachEvent);
			}

			// 704.5r If a permanent has both a +1/+1 counter and a -1/-1
			// counter on it, N +1/+1 and N -1/-1 counters are removed from it,
			// where N is the smaller of the number of +1/+1 and -1/-1 counters
			// on it.
			for(GameObject object: game.actualState.battlefield().objects)
			{
				// don't bother removing counters from dead things
				if(moveToGraveyard.contains(object) || destroy.contains(object))
					continue;

				int plus = CountersOn.get(object, Counter.CounterType.PLUS_ONE_PLUS_ONE).size();
				int minus = CountersOn.get(object, Counter.CounterType.MINUS_ONE_MINUS_ONE).size();
				if(plus > 0 && minus > 0)
				{
					int number = Math.min(plus, minus);
					Event removeCounterEvent = new Event(game.physicalState, "Remove " + number + " +1/+1 counters and " + number + " -1/-1 counters from " + object + ".", EventType.REMOVE_COUNTERS);
					removeCounterEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
					removeCounterEvent.parameters.put(EventType.Parameter.COUNTER, Union.instance(Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE), Identity.instance(Counter.CounterType.MINUS_ONE_MINUS_ONE)));
					removeCounterEvent.parameters.put(EventType.Parameter.NUMBER, numberGenerator(number));
					removeCounterEvent.parameters.put(EventType.Parameter.OBJECT, IdentifiedWithID.instance(object.ID));

					stateBasedActions.add(removeCounterEvent);
				}
			}

			// Clear any flags used by SBAs
			for(GameObject o: destroy.getAll(GameObject.class))
				o.getPhysical().setDamagedByDeathtouchSinceLastSBA(false);
			for(Player player: game.physicalState.players)
				player.unableToDraw = false;

			boolean onePerformed = false;
			// Set result = new Set();

			for(Event sbaEvent: stateBasedActions.getAll(Event.class))
			{
				// Make sure events return whether they did anything at all
				onePerformed = sbaEvent.perform(event, false) || onePerformed;

				// result.addAll(sbaEvent.parameters.get(EventType.Parameter.RESULT).evaluate(game,
				// null));
			}

			// setEventResult(event, Identity.instance(result));
			event.setResult(Empty.set);

			// If even one event performed, sbe's aren't done
			return onePerformed;
		}
	};

	/**
	 * The "actual" game state. In this game state, objects are represented as
	 * the versions that have been modified by effects.
	 */
	public GameState actualState;

	/** The states that have been backed up during this game. */
	public java.util.Stack<GameState> backupStates;

	/**
	 * A map from {@link ContinuousEffect#ID} and target {@link GameObject#ID}
	 * to the {@link CopiableValues} snapshot created the first time a
	 * {@link ContinuousEffectType#COPY_OBJECT} part of that
	 * {@link ContinuousEffect} applied.
	 */
	public java.util.Map<CopyEffectSnapshotKey, CopiableValues> copyEffectSnapshots;

	/** The action currently being performed. */
	public PlayerAction currentAction;

	/** The decks each player submits for deck-check, and start the game with. */
	private java.util.Map<Player, java.util.Map<String, java.util.List<Class<? extends Card>>>> decks;

	public final GameType gameType;

	/**
	 * Abilities that exist in the physical state to be granted by continuous
	 * effects or game rules (like intrinsic mana abilities or keywords).
	 * 
	 * Keys are {@link GrantedAbilityKey} objects representing the class of the
	 * ability, the object granting the ability, and the object the ability is
	 * being granted to; values are IDs of the abilities.
	 */
	public java.util.Map<GrantedAbilityKey, Integer> grantedAbilities;

	private int nextAvailableID;

	public boolean noRandom;

	/** The "physical" game state. Objects are represented "as printed." */
	public GameState physicalState;

	public boolean restarted;

	// To hold snapshots of objects that will be copied in the near future, but
	// will become ghosts before then. snapshotCache holds the actual snapshots,
	// which will be generated immediately after layer 1; snapshotSoon keeps a
	// list of the IDs to do this to.
	private java.util.Map<Integer, CopiableValues> snapshotCache;
	private java.util.Collection<Integer> snapshotSoon;

	public boolean started;

	private SetGenerator wishboard;

	/**
	 * Constructs a game.
	 * 
	 * @param gameType What deck construction rules to use.
	 */
	public Game(GameType gameType)
	{
		this.gameType = gameType;
		this.noRandom = false;

		this.nextAvailableID = 1;
		this.actualState = null;
		this.physicalState = new GameState(this);
		this.backupStates = new java.util.Stack<GameState>();
		this.currentAction = null;

		this.decks = new java.util.HashMap<Player, java.util.Map<String, java.util.List<Class<? extends Card>>>>();

		this.grantedAbilities = new java.util.HashMap<GrantedAbilityKey, Integer>();

		this.restarted = false;
		this.started = false;

		this.snapshotCache = new java.util.HashMap<Integer, CopiableValues>();
		this.snapshotSoon = new java.util.LinkedList<Integer>();
		this.copyEffectSnapshots = new java.util.HashMap<CopyEffectSnapshotKey, CopiableValues>();
		this.wishboard = Empty.instance();
	}

	/**
	 * Adds a player to this game.
	 * 
	 * @param comm The interface of the player to add.
	 * @return The player, or null if the deck was illegal.
	 */
	public Player addInterface(PlayerInterface comm)
	{
		Deck deck = comm.getDeck();

		java.util.Map<String, java.util.List<Class<? extends Card>>> cards = null;
		try
		{
			cards = deck.getCards();
		}
		catch(CardLoaderException e)
		{
			comm.alertError(e.getErrorParameters());
			return null;
		}

		// Reject any deck that has any alternate cards in it
		java.util.Set<String> illegalCards = new java.util.HashSet<String>();
		for(java.util.List<Class<? extends Card>> kind: cards.values())
			for(Class<? extends Card> card: kind)
				if(AlternateCard.class.isAssignableFrom(card))
					illegalCards.add(org.rnd.jmagic.Convenience.getName(card));

		if(!illegalCards.isEmpty())
		{
			comm.alertError(new PlayerInterface.ErrorParameters.IllegalCardsError(illegalCards));
			return null;
		}

		ErrorParameters deckCheckError = this.gameType.checkDeck(cards);
		if(null != deckCheckError)
		{
			comm.alertError(deckCheckError);
			return null;
		}

		Player player = new Player(this.physicalState, comm.getName(), comm);
		this.decks.put(player, cards);
		this.physicalState.addPlayer(player);
		comm.setPlayerID(player.ID);
		return player;
	}

	/**
	 * 704.3. Whenever a player would get priority (see rule 116, "Timing and
	 * Priority"), the game checks for any of the listed conditions for
	 * state-based actions, then performs all applicable state-based actions
	 * simultaneously as a single event. If any state-based actions are
	 * performed as a result of a check, the check is repeated; otherwise all
	 * triggered abilities that are waiting to be put on the stack are put on
	 * the stack, then the check is repeated. Once no more state-based actions
	 * have been performed as the result of a check and no triggered abilities
	 * are waiting to be put on the stack, the appropriate player gets priority
	 */
	public void beforePriority()
	{
		while(checkStateBasedActions())
		{
			// this space intentionally left blank
		}

		stackTriggers();
	}

	/** Refresh the game state, then check state-based actions once. */
	public boolean checkStateBasedActions()
	{
		this.refreshActualState();

		Event sbaEvent = new Event(this.physicalState, "State Based Actions", STATE_BASED_ACTIONS);
		return sbaEvent.perform(null, true);
	}

	/**
	 * Creates replacement effects "built in" to the game: The first player
	 * skipping their draw step in a two player game, and the planeswalker
	 * noncombat damage redirection effect.
	 */
	private void createBuiltInReplacements()
	{
		if(this.physicalState.players.size() == 2)
		{
			// create the "skip the first draw" replacement effect
			SimpleEventPattern drawStep = new SimpleEventPattern(EventType.BEGIN_STEP);
			drawStep.put(EventType.Parameter.STEP, DrawStepOf.instance(Players.instance()));
			EventReplacementEffect skipDrawReplacement = new EventReplacementEffect(this, "The player who plays first skips the draw step of their first turn", drawStep);

			ContinuousEffect.Part part = replacementEffectPart(skipDrawReplacement);

			Event skipDrawEvent = new Event(this.physicalState, "The player who plays first skips the draw step of their first turn", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT);
			skipDrawEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
			skipDrawEvent.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			skipDrawEvent.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			skipDrawEvent.parameters.put(EventType.Parameter.USES, org.rnd.jmagic.Convenience.numberGenerator(1));
			skipDrawEvent.perform(null, true);
		}

		{
			// redirect damage from a player to a planeswalker they control

			// the replacement effect has to be instantiated before the events,
			// because some of them need to reference it for the event its
			// replacing
			DamageReplacementEffect redirectDamage = new PlaneswalkerRedirectionEffect(this, "If noncombat damage would be dealt to a player by a source controlled by an opponent, that opponent may have that source deal that damage to a planeswalker the first player controls instead");
			redirectDamage.makeRedirectionEffect();

			SetGenerator originalDamage = ReplacedBy.instance(Identity.instance(redirectDamage));
			SetGenerator sourceOfDamage = SourceOfDamage.instance(originalDamage);
			SetGenerator controllerOfSource = ControllerOf.instance(sourceOfDamage);
			redirectDamage.makeOptional(controllerOfSource);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(redirectDamage));

			Event pwRedirectEvent = new Event(this.physicalState, "If noncombat damage would be dealt to a player by a source controlled by an opponent, that opponent may have that source deal that damage to a planeswalker the first player controls instead", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT);
			pwRedirectEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
			pwRedirectEvent.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			pwRedirectEvent.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			pwRedirectEvent.perform(null, true);
		}
	}

	public java.util.Set<PlayLandAction> createPlayLandActions(Player who, GameObject land)
	{
		java.util.Set<PlayLandAction> ret = new java.util.HashSet<PlayLandAction>();

		if(!who.outOfGame && (null != this.actualState.currentTurn()) && (who.totalLandActions == null || LandsPlayedThisTurn.get(who) < who.totalLandActions))
		{
			PlayLandAction action = new PlayLandAction(this, "Play land " + land + ".", land, who);
			if(action.attempt())
				ret.add(action);
		}

		return ret;
	}

	/**
	 * Generates actions for the player who has priority.
	 * 
	 * @param manaAbilitiesOnly Whether the player can only activate mana
	 * abilities. This is set to true when the player is in the middle of
	 * playing a spell or ability, or is being asked to pay a cost during the
	 * resolution of a spell or ability (such as Mana Leak or Lightning Rift).
	 * This is false when the player has priority.
	 */
	public void generatePlayerActions(Player whoFor, boolean manaAbilitiesOnly)
	{
		whoFor = whoFor.getActual();

		for(ActivatedAbility a: this.actualState.getAll(ActivatedAbility.class))
		{
			if(a.isActivatableBy(this.actualState, whoFor))
			{
				ActivateAbilityAction action = new ActivateAbilityAction(this, a, whoFor, a.sourceID);

				// 602.5. A player can't begin to activate an ability that's
				// prohibited from being activated.
				if(action.attempt())
					this.actualState.playerActions.add(action);
			}
		}

		if(!manaAbilitiesOnly)
		{
			// Generate cast actions for all castable spells
			for(Castable c: this.actualState.getAll(Castable.class))
				this.actualState.playerActions.addAll(c.getCastActions(this.actualState, whoFor));

			for(PlayableAsLand p: this.actualState.getAll(PlayableAsLand.class))
				if(p.isPlayableAsLandBy(this.actualState, whoFor))
					this.actualState.playerActions.addAll(createPlayLandActions(whoFor, (GameObject)p));

			// Generate all appropriate special actions that should be generated
			// from continuous effects
			for(SpecialActionFactory factory: this.actualState.specialActionFactories.keySet())
				this.actualState.playerActions.addAll(factory.getActions(this.actualState, this.actualState.specialActionFactories.get(factory), whoFor));

			// 702.34d If you have priority, you may turn a face-down permanent
			// you control face up. This is a special action; it doesn't use the
			// stack (see rule 115). To do this, show all players what the
			// permanent's morph cost would be if it were face up, pay that
			// cost, then turn the permanent face up. ...
			// Start by turning all face down permanents face up.
			java.util.Map<Integer, Characteristics> faceDownValues = new java.util.HashMap<Integer, Characteristics>();
			for(GameObject o: this.physicalState.battlefield().objects)
				if(o.faceDownValues != null && o.controllerID == this.actualState.playerWithPriorityID)
				{
					faceDownValues.put(o.ID, o.faceDownValues);
					o.faceDownValues = null;
				}
			if(!faceDownValues.isEmpty())
			{
				// If anything was turned face up, hold onto the actions already
				// generated, and refresh the state.
				java.util.Set<PlayerAction> actionsToRestore = new java.util.HashSet<PlayerAction>(this.actualState.playerActions);
				this.refreshActualState();
				// Now check all those objects' morph costs.
				for(int objectID: faceDownValues.keySet())
				{
					GameObject object = this.actualState.get(objectID);
					for(Keyword k: object.getKeywordAbilities())
					{
						if(k.isMorph())
						{
							org.rnd.jmagic.abilities.keywords.Morph ability = (org.rnd.jmagic.abilities.keywords.Morph)k;
							PlayerAction morphAction = new org.rnd.jmagic.abilities.keywords.Morph.TurnFaceUpAction(object, ability.morphCost);
							actionsToRestore.add(morphAction);
						}
						// 702.34d ... (If the permanent wouldn't have a morph
						// cost if it were face up, it can't be turned face up
						// this way.)
					}
				}
				// Now turn those objects back face down and restore the state.
				for(java.util.Map.Entry<Integer, Characteristics> faceDownRestore: faceDownValues.entrySet())
					this.physicalState.getByIDObject(faceDownRestore.getKey()).faceDownValues = faceDownRestore.getValue();
				this.refreshActualState();
				this.actualState.playerActions.addAll(actionsToRestore);
			} // if there were face down permanents
		} // if !mana abilities only
	}

	/**
	 * If {@link Game#snapshotSoon(GameObject)} was called for the specified
	 * object, returns that snapshot. Future calls to this method with that
	 * object will return null.
	 * 
	 * @param of The object to get the cached snapshot for.
	 * @return The cached snapshot, if it exists; otherwise null.
	 */
	public CopiableValues getCachedSnapshot(GameObject of)
	{
		return this.snapshotCache.remove(of.ID);
	}

	public IntrinsicManaAbility getIntrinsic(SubType type, int abilityHolderID)
	{
		GrantedAbilityKey key = new GrantedAbilityKey(null, IntrinsicManaAbility.classesByType.get(type), abilityHolderID);
		Integer existingAbility = this.grantedAbilities.get(key);

		if(existingAbility == null)
		{
			IntrinsicManaAbility ability = (IntrinsicManaAbility)org.rnd.util.Constructor.construct(key.abilityClass, new Class<?>[] {GameState.class}, new Object[] {this.physicalState});
			// ability.sourceID = abilityHolder.ID;
			this.grantedAbilities.put(key, ability.ID);
			existingAbility = ability.ID;
		}

		// This makes sure to handle instances where an object loses and regains
		// an intrinsic during the layers.
		if(!this.actualState.containsIdentified(existingAbility))
			this.physicalState.<Identified>get(existingAbility).clone(this.actualState);

		return this.actualState.get(existingAbility);
	}

	public StaticAbility getLoyaltyCountersAbility(int planeswalkerID)
	{
		GrantedAbilityKey key = new GrantedAbilityKey(null, LoyaltyCountersAbility.class, planeswalkerID);
		Integer existingAbility = this.grantedAbilities.get(key);

		if(existingAbility == null)
		{
			LoyaltyCountersAbility ability = org.rnd.util.Constructor.construct(LoyaltyCountersAbility.class, new Class<?>[] {GameState.class}, new Object[] {this.physicalState});
			this.grantedAbilities.put(key, ability.ID);
			existingAbility = ability.ID;
			ability.sourceID = planeswalkerID;

			// Because this ability is constructed *after* identifieds have been
			// cloned into the actual state, we need to add the ability's effect
			// to the actual state as well.
			ability.getEffect().clone(this.actualState);
		}

		if(!this.actualState.containsIdentified(existingAbility))
			this.physicalState.<Identified>get(existingAbility).clone(this.actualState);

		return this.actualState.get(existingAbility);
	}

	public int getNextAvailableID()
	{
		return this.nextAvailableID++;
	}

	public SetGenerator getWishboard()
	{
		return this.wishboard;
	}

	/** Rule 116 */
	public void givePriority()
	{
		// Start the priority loop with the active player
		Player firstPlayer = this.actualState.currentTurn().getOwner(this.actualState);
		while(true)
		{
			beforePriority();

			// This loops until a player takes a legal non-pass action or every
			// player has passed
			java.util.Iterator<Player> i = this.physicalState.getPlayerCycle(firstPlayer).iterator();
			Player hasPriority = i.next();
			while(true)
			{
				java.util.List<PlayerAction> actions = null;

				// if a player is no longer in the game, skip them
				if(!(hasPriority.outOfGame))
				{
					this.physicalState.setPlayerWithPriority(hasPriority);
					this.refreshActualState();
					this.generatePlayerActions(hasPriority, false);
					hasPriority = hasPriority.getActual();

					actions = hasPriority.sanitizeAndChoose(this.actualState, 0, 1, this.actualState.playerActions, PlayerInterface.ChoiceType.NORMAL_ACTIONS, PlayerInterface.ChooseReason.PRIORITY);

					this.physicalState.setPlayerWithPriority(null);
					this.actualState.setPlayerWithPriority(null);

					if((1 == actions.size()) && actions.get(0).saveStateAndPerform())
					{
						// They have taken an action, so allPassed is false
						firstPlayer = hasPriority;
						break;
					}
				}

				if(actions == null || 0 == actions.size())
				{
					// Give priority to the next player if there is a next
					// player
					if(i.hasNext())
						hasPriority = i.next();
					// If there isn't a next player, then everyone has passed
					else
					{
						this.refreshActualState();
						// If there's nothing on the stack, end priority
						if(this.actualState.stack().objects.size() == 0)
							return;

						// Resolve something
						try
						{
							this.actualState.stack().objects.get(0).resolve();
							if(this.restarted)
							{
								this.restarted = false;
								return;
							}

							firstPlayer = this.actualState.currentTurn().getOwner(this.actualState);
							break;
						}
						catch(Game.StopPriorityException ex)
						{
							return;
						}
					}
				}
			}
		}
	}

	public boolean hasStarted()
	{
		return this.started;
	}

	/**
	 * Instantiates a list of cards from their Class objects, assigns them
	 * expansions, owners, and puts them in the given zone.
	 * 
	 * @param cards The Class literals of the cards to create
	 * @param zone The zone to put the cards into
	 * @param owner The player to set as the owner of the cards
	 */
	private void instantiateCards(java.util.List<Class<? extends Card>> cards, Zone zone, Player owner)
	{
		for(Class<? extends Card> i: cards)
		{
			GameObject newCard = org.rnd.util.Constructor.construct(i, new Class<?>[] {GameState.class}, new Object[] {this.physicalState});

			if(org.rnd.jmagic.CardLoader.getPrintings(i).isEmpty())
				throw new IllegalStateException("No printings specified for card: " + i.getSimpleName());

			// The players' decks become their libraries.
			newCard.setOwner(owner);
			zone.addToTop(newCard);
		}
	}

	/**
	 * Refreshes the game state. Creates a new actual game state and applies
	 * continuous effects to it.
	 */
	public void refreshActualState()
	{
		GameState previous = this.actualState;
		this.actualState = this.physicalState.clone(false);

		java.util.Comparator<ContinuousEffect> compareOnTimestamp = new CompareOnTimestamp();

		// Find delayed triggered abilities that have expired.
		for(DelayedTrigger trigger: this.actualState.delayedTriggers)
			if(trigger.expired(this.actualState))
				this.physicalState.delayedTriggers.remove(trigger);

		{
			// handle the new copiable values of cards that are
			// flipped/transformed/face-down.
			java.util.List<GameObject> flipped = new java.util.LinkedList<GameObject>();
			java.util.List<GameObject> transformed = new java.util.LinkedList<GameObject>();
			java.util.List<GameObject> faceDown = new java.util.LinkedList<GameObject>();

			for(GameObject o: this.actualState.battlefield().objects)
			{
				// a permanent without a bottom half can flip; nothing happens
				if(o.isFlipped() && o.getBottomHalf() != null)
					flipped.add(this.actualState.copyForEditing(o));
				// a permanent without a back face can't transform; the
				// transform event handles this (but we check later anyway)
				if(o.isTransformed())
					transformed.add(this.actualState.copyForEditing(o));
				if(o.faceDownValues != null)
					faceDown.add(this.actualState.copyForEditing(o));
			}

			// spells can also be face-down.
			for(GameObject o: this.actualState.stack().objects)
				if(o.faceDownValues != null)
					faceDown.add(this.actualState.copyForEditing(o));

			// now that we've copied off and collected all the relevant objects,
			// apply the new values:

			for(GameObject o: flipped)
			{
				ManaPool newManaCost = new ManaPool(o.getManaCost());
				java.util.Set<Color> newColors = java.util.EnumSet.copyOf(o.getColors());

				o.setCharacteristics(o.getBottomHalf());
				o.setManaCost(newManaCost);
				o.setColors(newColors);
			}
			for(GameObject o: transformed)
			{
				if(o.getBackFace() == null)
					throw new IllegalStateException("A single-faced card has transformed: " + o);
				o.setCharacteristics(o.getBackFace());
			}
			for(GameObject o: faceDown)
				o.setCharacteristics(o.faceDownValues);
		}

		// Find all continuous effects which should be applying
		// 613.5. ... If an effect starts to apply in one layer and/or sublayer,
		// it will continue to be applied to the same set of objects in each
		// other applicable layer and/or sublayer, even if the ability
		// generating the effect is removed during this process.
		// Rule 613.5 paraphrased: the following collections aren't cleared
		// between layers
		java.util.SortedMap<ContinuousEffect, Identified> cdas = new java.util.TreeMap<ContinuousEffect, Identified>(compareOnTimestamp);
		java.util.SortedMap<ContinuousEffect, Identified> effects = new java.util.TreeMap<ContinuousEffect, Identified>(compareOnTimestamp);
		for(ContinuousEffectType.Layer layer: java.util.EnumSet.allOf(ContinuousEffectType.Layer.class))
		{
			// Add all effects from static abilities (NOTE: this loop _must_ be
			// repeated for every layer in case the previous layer(s) added any
			// static abilities)
			for(GameObject o: this.actualState.getAllObjects())
			{
				for(StaticAbility sa: o.getStaticAbilities())
				{
					ContinuousEffect effect = sa.getEffect();
					if(effect.appliesIn(layer))
					{
						if(!sa.definesCharacteristics().isEmpty())
							cdas.put(this.actualState.copyForEditing(effect), o);
						else
							effects.put(this.actualState.copyForEditing(effect), o);
					}
				}
			}

			for(Player p: this.actualState.players)
			{
				for(StaticAbility sa: p.getStaticAbilities())
				{
					ContinuousEffect effect = sa.getEffect();
					if(effect.appliesIn(layer))
						effects.put(effect, p);
				}
			}

			// Add all effects not attached to static abilities
			for(FloatingContinuousEffect effect: this.actualState.floatingEffects)
			{
				GameObject sourceObject = effect.sourceEvent.getSource();
				if(effect.appliesIn(layer))
					// Since floating effect parameters are frozen at creation,
					// it should be fine to use null as the source of the
					// effect.
					// TODO : ... except for rule-changing effects? -RulesGuru
					effects.put(effect, sourceObject);
			}

			// The POWER_AND_TOUGHNESS layer is handled by sub-layer
			if(ContinuousEffectType.Layer.POWER_AND_TOUGHNESS == layer)
			{
				for(ContinuousEffectType.SubLayer subLayer: java.util.EnumSet.allOf(ContinuousEffectType.SubLayer.class))
				{
					// COUNTERS are handled specially since you don't search for
					// actual continuous effects to apply
					if(ContinuousEffectType.SubLayer.COUNTERS == subLayer)
					{
						for(GameObject o: this.actualState.getAllObjects())
						{
							if(!o.counters.isEmpty())
							{
								GameObject actual = this.actualState.copyForEditing(o);
								for(Counter c: actual.counters)
									c.modifyObject(actual);
							}
						}
					}
					else
					{
						DependencyCalculator dependencies = new DependencyCalculator(compareOnTimestamp);
						dependencies.applyEffectsInOrder(cdas, this.actualState, subLayer);
						dependencies.applyEffectsInOrder(effects, this.actualState, subLayer);
					}
				}
			}
			// Otherwise just apply all the effects found
			else
			{
				if(layer.checkDependencies() && cdas.size() + effects.size() > 1)
				{
					DependencyCalculator dependencies = new DependencyCalculator(compareOnTimestamp);
					dependencies.applyEffectsInOrder(cdas, this.actualState, layer);
					dependencies.applyEffectsInOrder(effects, this.actualState, layer);
				}
				else
				{
					for(java.util.Map.Entry<ContinuousEffect, Identified> e: cdas.entrySet())
						this.actualState.copyForEditing(e.getKey()).apply(layer, e.getValue());

					for(java.util.Map.Entry<ContinuousEffect, Identified> e: effects.entrySet())
						this.actualState.copyForEditing(e.getKey()).apply(layer, e.getValue());
				}
			}

			if(ContinuousEffectType.Layer.COPY == layer)
			{
				// After the copy effects layer, all permanents which have the
				// land type get abilities for each basic land subtype they
				// have. Because granting abilities changes the collection of
				// Identified, collect the ones to check. Similarly to intrinsic
				// mana abilities for basic land types, add the loyalty counters
				// ability to planeswalkers.
				java.util.Collection<GameObject> landsToCheck = new java.util.LinkedList<GameObject>();
				java.util.Collection<GameObject> planeswalkers = new java.util.LinkedList<GameObject>();

				for(GameObject o: this.actualState.getAllObjects())
				{
					if(o.getTypes().contains(Type.LAND))
						landsToCheck.add(o);
					if(o.getTypes().contains(Type.PLANESWALKER))
						planeswalkers.add(o);
				}
				for(GameObject land: landsToCheck)
					for(SubType basic: SubType.getBasicLandTypes())
						if(land.getSubTypes().contains(basic))
							this.actualState.copyForEditing(land).addAbility(this.getIntrinsic(basic, land.ID));
				for(GameObject planeswalker: planeswalkers)
					this.actualState.copyForEditing(planeswalker).addAbility(this.getLoyaltyCountersAbility(planeswalker.ID));

				java.util.Iterator<Integer> i = this.snapshotSoon.iterator();
				while(i.hasNext())
				{
					int ID = i.next();
					GameObject object = this.actualState.get(ID);
					this.snapshotCache.put(ID, new CopiableValues(this, object, object));
					i.remove();
				}
			}
		}

		boolean redo = false;
		// Add all effects not attached to static abilities
		for(FloatingContinuousEffect effect: this.actualState.floatingEffects)
		{
			// Remove any floating continuous effect which no longer applies
			// or has been used up
			GameObject sourceObject = effect.sourceEvent.getSource();
			if(!effect.expires.evaluate(this, sourceObject).isEmpty() || (0 == effect.uses))
			{
				this.physicalState.floatingEffects.remove(effect);
				redo = true;
			}
		}

		if(redo)
		{
			this.refreshActualState();
			return;
		}

		// 710.3. If information about an object would be visible to the player
		// whose turn is controlled, it's visible to both that player and the
		// controller of the turn.
		if(!this.actualState.controlledPlayers.isEmpty())
		{
			for(java.util.Map.Entry<Integer, Integer> control: this.actualState.controlledPlayers.entrySet())
			{
				Player victim = this.actualState.get(control.getKey());
				int controllerID = control.getValue();

				victim.getHand(this.actualState).visibleTo.add(controllerID);
				victim.getSideboard(this.actualState).visibleTo.add(controllerID);

				Player controller = this.actualState.get(controllerID);
				for(GameObject o: this.actualState.exileZone().objects)
					if(o.isVisibleTo(victim))
						o.setActualVisibility(controller, true);
				// TODO : stack and battlefield (for physical morphs)

				for(GameObject o: victim.getLibrary(this.actualState).objects)
					if(o.isVisibleTo(victim))
						o.setActualVisibility(controller, true);
			}
		}

		if(previous != null)
		{
			java.util.List<EventFactory> controlChanges = new java.util.LinkedList<EventFactory>();
			for(GameObject newObject: this.actualState.getAllObjects())
			{
				GameObject oldObject = previous.getByIDObject(newObject.ID);
				// The new object might not have existed in the old state, might
				// have been in the void, or might have been controlled by the
				// same player as it is now, or might have been controlled by a
				// player that doesn't exist anymore
				if(null == oldObject || oldObject.zoneID == -1 || oldObject.controllerID == newObject.controllerID || !this.actualState.containsIdentified(oldObject.controllerID))
					continue;

				EventFactory controlChange = new EventFactory(EventType.CHANGE_CONTROL, (newObject.controllerID == -1 ? "No one" : newObject.getController(newObject.state)) + " controls " + newObject + ".");
				controlChange.parameters.put(EventType.Parameter.OBJECT, IdentifiedWithID.instance(newObject.ID));
				if(oldObject.controllerID != -1)
					controlChange.parameters.put(EventType.Parameter.PLAYER, PlayerByID.instance(oldObject.controllerID));
				if(newObject.controllerID != -1)
					controlChange.parameters.put(EventType.Parameter.TARGET, PlayerByID.instance(newObject.controllerID));
				controlChanges.add(controlChange);
			}
			if(!controlChanges.isEmpty())
				simultaneous(controlChanges.toArray(new EventFactory[0])).createEvent(this, null).perform(null, true);
		}

		if(previous != this.physicalState)
			previous.clear();
	}

	public void removeObject(GameObject voided)
	{
		this.physicalState.removeIdentified(voided.ID);
		for(int a: voided.getCharacteristics().nonStaticAbilities)
			this.physicalState.removeIdentified(a);
		for(int a: voided.getCharacteristics().staticAbilities)
			this.physicalState.removeIdentified(a);
		for(int a: voided.getCharacteristics().keywordAbilities)
			this.physicalState.removeIdentified(a);
	}

	/**
	 * Runs this game.
	 * <p>
	 * Since this is the main entry point to the engine, the general error
	 * theory will be described here. jMagic is buggy and is likely to stay that
	 * way for as long as it exists. This means NullPointerException and the
	 * like are facts of life for any caller of this function. Because of this,
	 * any internal error detection done by the engine will be reported using a
	 * generic RuntimeException with a description of the error as the message.
	 * That way, callers of this function need only handle a single exception
	 * type for any error from which jMagic can't recover.
	 * 
	 * @return Who won.
	 * @throws InterruptedGameException if an interface needs to end the game
	 * for some reason
	 */
	public Player run() throws InterruptedGameException
	{
		if(this.physicalState.numPlayers() == 0)
			return null;

		// 103. Starting the Game
		for(Player player: this.physicalState.players)
		{
			instantiateCards(this.decks.get(player).get(Deck.MAIN_DECK), player.getLibrary(this.physicalState), player);
			instantiateCards(this.decks.get(player).get(Deck.SIDEBOARD), player.getSideboard(this.physicalState), player);
		}

		// Bootstrap the actual game state so a new one can be created and
		// events can perform
		this.actualState = this.physicalState;

		try
		{
			this.startGame(null);
			while(true)
			{
				// effects that restart the game will cause this flag to clear
				// during the restart procedure.
				this.started = true;
				runTurn(this.physicalState.nextTurn());
			}
		}
		catch(GameOverException e)
		{
			new Event(this.physicalState, "Game Over", EventType.GAME_OVER).perform(null, true);
			for(Player player: this.actualState.players)
			{
				player.alert(this.actualState);
			}
			if(1 == this.actualState.numPlayers())
				for(Player player: this.actualState.players)
					if(player.wonGame || !player.outOfGame)
						return player;
		}

		return null;
	}

	private void runTurn(Turn turn)
	{
		// Things this player controls don't "have summoning sickness" anymore.
		this.physicalState.summoningSick.get(turn.ownerID).clear();

		Event beginTurnEvent = new Event(this.physicalState, "Begin " + turn + ".", EventType.BEGIN_TURN);
		beginTurnEvent.parameters.put(EventType.Parameter.TURN, Identity.instance(turn));
		beginTurnEvent.perform(null, true);

		if(this.physicalState.currentTurn() != turn)
		{
			// If the turn was skipped, we need to manually add in the next turn
			// for the player
			Player owner = turn.getOwner(this.physicalState);
			if(this.physicalState.players.contains(owner) && !turn.extra)
				this.physicalState.futureTurns.add(new Turn(owner));
			return;
		}

		while(!turn.phases.isEmpty())
		{
			// Can't remove the phase from the list before beginning the phase
			// because skipping a phase involves intersecting the parameters of
			// BEGIN_PHASE with only the current phase and all phases of the
			// current turn and all phases of all future turns, so remove it
			// after performing the event
			Phase phase = turn.phases.get(0);

			Event beginPhaseEvent = new Event(this.physicalState, "Begin " + phase + ".", EventType.BEGIN_PHASE);
			beginPhaseEvent.parameters.put(EventType.Parameter.PHASE, Identity.instance(phase));
			beginPhaseEvent.perform(null, true);

			turn.phases.remove(0);
			if(this.physicalState.currentPhase() != phase)
				continue;

			while(!phase.steps.isEmpty())
			{
				// Can't remove the step from the list before beginning the step
				// because skipping a step involves intersecting the parameters
				// of BEGIN_STEP with only the current step and all steps of the
				// current phase and all steps of all phases of the current turn
				// and all steps of all phases of all future turns, so remove it
				// after performing the event
				Step step = phase.steps.get(0);

				Event beginStepEvent = new Event(this.physicalState, "Begin " + step + ".", EventType.BEGIN_STEP);
				beginStepEvent.parameters.put(EventType.Parameter.STEP, Identity.instance(step));
				beginStepEvent.perform(null, true);

				phase.steps.remove(0);
				if(this.physicalState.currentStep() != step)
					continue;

				step.type.run(this, step.getOwner(this.actualState), step);

				Event endStepEvent = new Event(this.physicalState, "End of " + step + ".", EventType.END_STEP);
				endStepEvent.parameters.put(EventType.Parameter.STEP, Identity.instance(step));
				endStepEvent.perform(null, true);

				phase.stepsRan.add(step);
			}

			turn.phasesRan.add(phase);
		}

		// Remove any players which have left the game from the
		// game-state
		java.util.Iterator<Player> i = this.physicalState.players.iterator();
		while(i.hasNext())
		{
			Player p = i.next();
			if(p.outOfGame)
				i.remove();
		}

		// Try cleaning up events that don't need to be kept around
		java.util.List<Event> toRemove = new java.util.LinkedList<Event>();
		for(Event e: this.physicalState.getAll(Event.class))
			if(!e.preserve)
				toRemove.add(e);
		for(Event e: toRemove)
			this.physicalState.removeIdentified(e.ID);
	}

	/**
	 * Replaces the games value for "cards you own outside the game". (If the
	 * game has already started, this does nothing)
	 */
	public void setWishboard(SetGenerator generator)
	{
		if(!this.started)
			this.wishboard = generator;
	}

	/**
	 * Tells this game that an object will be copied in the near future.
	 * Normally these snapshots are created naturally during the process of
	 * copying the object. However, if the object to be copied changes zones
	 * before the copy effect is made, you must call this method before the copy
	 * changes zones.
	 * 
	 * @param object The object to be copied later.
	 */
	public void snapshotSoon(GameObject object)
	{
		this.snapshotSoon.add(object.ID);
	}

	/**
	 * 603.3b If multiple abilities have triggered since the last time a player
	 * received priority, each player, in APNAP order, puts triggered abilities
	 * he or she controls on the stack in any order he or she chooses. (See rule
	 * 101.4.)
	 * 
	 * @return True if at least one trigger is stacked; false otherwise.
	 */
	public boolean stackTriggers()
	{
		boolean triggerStacked = false;

		java.util.List<Player> players = this.physicalState.getPlayerCycle(this.actualState.currentTurn().getOwner(this.actualState));
		for(Player player: players)
		{
			if(player.outOfGame)
				continue;

			java.util.Collection<TriggeredAbility> thisPlayersTriggers = this.physicalState.waitingTriggers.get(player.ID);
			// if the triggered ability can't stack, don't try to stack it
			java.util.Iterator<TriggeredAbility> i = thisPlayersTriggers.iterator();
			while(i.hasNext())
			{
				TriggeredAbility trigger = i.next();
				if(!trigger.canStack())
				{
					this.physicalState.removeIdentified(trigger.ID);
					i.remove();
				}
			}

			int numTriggers = thisPlayersTriggers.size();
			if(numTriggers == 0)
				continue;

			// The controller of the ability is set at the time it is added to
			// the waitlist.
			java.util.List<TriggeredAbility> orderedTriggers = player.sanitizeAndChoose(this.physicalState, numTriggers, new java.util.HashSet<TriggeredAbility>(thisPlayersTriggers), PlayerInterface.ChoiceType.TRIGGERS, PlayerInterface.ChooseReason.STACK_TRIGGERS);
			for(TriggeredAbility t: orderedTriggers)
			{
				this.refreshActualState();
				this.actualState.<TriggeredAbility>get(t.ID).putOnStack(player, null);
				triggerStacked = true;
			}
		}
		return triggerStacked;
	}

	/**
	 * @param firstPlayer The player taking the first turn, or null to randomly
	 * determine the first player.
	 */
	public void startGame(Player firstPlayer)
	{
		this.refreshActualState();

		// Apply variant rules
		this.gameType.modifyGameState(this.physicalState);
		this.refreshActualState();

		if(firstPlayer == null)
		{
			// Rule 103.2: the players should choose a "mutually agreeable"
			// method to determine who will choose who plays first; however,
			// it's impossible to define an interface where players can agree on
			// such an algorithm, so instead we'll use the "generally agreeable"
			// algorithm of randomization.
			if(!this.noRandom)
				java.util.Collections.shuffle(this.physicalState.players);
			java.util.Set<Player> playerChoices = new java.util.LinkedHashSet<Player>();
			for(Player player: this.physicalState.players)
				playerChoices.add(player);

			for(Player p: this.physicalState.players)
				p.alert(this.actualState);

			// TODO : In a match of several games, the loser of the previous
			// game decides who will take the first turn. If the previous game
			// was a draw, the person who determined who would take the first
			// turn in the previous game decides.
			java.util.List<Player> choice = this.physicalState.players.get(0).sanitizeAndChoose(this.physicalState, 1, playerChoices, PlayerInterface.ChoiceType.PLAYER, PlayerInterface.ChooseReason.FIRST_PLAYER);
			firstPlayer = choice.get(0);
		}

		// This marker event is to support X-10 modifying the decks after
		// Pack Wars sets them. This is a temporary hack. The desired
		// solution is described in ticket 381.
		// The refresh is to allow X-10's GAME_START replacement into the
		// actual state. It shouldn't be needed either.
		this.refreshActualState();
		new Event(this.physicalState, "Start the game.", EventType.GAME_START).perform(null, true);

		// 103.1. At the start of a game, each player shuffles his or her
		// deck
		if(!this.noRandom)
			for(Player player: this.physicalState.players)
				// I'm directly shuffling the library's cards here because
				// the rules actually specify to shuffle the *decks*, not
				// the libraries. We would just do the shuffle at deck
				// construction time, except that it's possible for a
				// variant's modifyGameState function to specify that the
				// decks should not be shuffled.
				java.util.Collections.shuffle(player.getLibrary(this.physicalState).objects);

		// 103.2. After the decks have been shuffled, the players determine
		// which one of them will choose who takes the first turn. In the first
		// game of a match (including a single-game match), the players may use
		// any mutually agreeable method (flipping a coin, rolling dice, etc.)
		// to do so. In a match of several games, the loser of the previous game
		// chooses who takes the first turn. If the previous game was a draw,
		// the player who made the choice in that game makes the choice in this
		// game. The player chosen to take the first turn is the starting
		// player.
		this.physicalState.playingFirstID = firstPlayer.ID;

		// cycle the players around until the correct player is going first;
		// we assume the rest of the players are "seated" around the "table"
		// randomly
		this.physicalState.players = this.physicalState.getPlayerCycle(firstPlayer);

		// 103.3. Once the starting player has been determined, each player
		// sets his or her life total to 20 and draws a hand of seven cards.
		for(Player player: this.physicalState.players)
		{
			player.lifeTotal = 20;

			int number = player.getMaxHandSize();
			Event drawEvent = new Event(this.physicalState, player + " draws " + org.rnd.util.NumberNames.get(number) + " card(s).", EventType.DRAW_CARDS);
			drawEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
			drawEvent.parameters.put(EventType.Parameter.NUMBER, numberGenerator(number));
			drawEvent.parameters.put(EventType.Parameter.PLAYER, player.thisPlayer());
			drawEvent.perform(null, true);
		}

		// 103.4. A player who is dissatisfied with his or her initial hand
		// may take a mulligan. First, the starting player declares whether
		// or not he or she will take a mulligan. Then each other player in
		// turn order does the same. Once each player has made a
		// declaration, all players who decided to take mulligans do so at
		// the same time.
		Set canMulligan = Set.fromCollection(this.physicalState.players);
		while(!canMulligan.isEmpty())
		{
			Event mulliganEvent = new Event(this.physicalState, canMulligan + " choose whether to mulligan.", EventType.MULLIGAN_SIMULTANEOUS);
			mulliganEvent.parameters.put(EventType.Parameter.PLAYER, Identity.fromCollection(canMulligan));
			mulliganEvent.perform(null, true);

			canMulligan = mulliganEvent.getResult();
		}

		// 103.5. Some cards allow a player to take actions with them from his
		// or her opening hand. Once all players have kept their opening hands,
		// the starting player may take any such actions in any order. Then each
		// other player in turn order may do the same.
		for(Player player: this.physicalState.getPlayerCycle(firstPlayer))
		{
			java.util.Collection<GameObject> beginTheGameObjects = new java.util.HashSet<GameObject>();
			for(GameObject object: player.getHand(this.physicalState).objects)
				if(object.getActual().beginTheGameEffect != null)
					beginTheGameObjects.add(object);

			if(beginTheGameObjects.isEmpty())
				continue;

			java.util.List<GameObject> objectChoice = player.sanitizeAndChoose(player.game.actualState, 0, null, beginTheGameObjects, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.BEGIN_THE_GAME_EFFECTS);
			if(objectChoice.isEmpty())
				continue;

			java.util.Collection<GameObject> objectsWithConsequences = new java.util.LinkedList<GameObject>();
			for(GameObject object: objectChoice)
			{
				object = object.getActual();
				Event effect = object.beginTheGameEffect.createEvent(this, object);
				effect.setSource(object);
				effect.perform(null, true);
				object = object.getActual();

				if(object.beginTheGameConsequence != null)
					objectsWithConsequences.add(object);
			}

			for(GameObject object: objectsWithConsequences)
			{
				Event effect = object.getActual().beginTheGameConsequence.createEvent(this, object);
				effect.setSource(object);
				effect.perform(null, true);
			}
		}

		this.createBuiltInReplacements();

		for(Player player: this.physicalState.players)
			this.physicalState.futureTurns.add(new Turn(player));

	}
}
