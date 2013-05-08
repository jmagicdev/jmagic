package org.rnd.jmagic.engine;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

/**
 * Before adding a new EventType to this file, please see the note near the top
 * about the attempt() method.
 */

public abstract class EventType
{
	public enum Parameter
	{
		ABILITY, ACTION, ALTERNATE_COST, ATTACKER, CARD, CAUSE, CHOICE, COLOR, CONTROLLER, COST, @SuppressWarnings("hiding")
		COUNTER, DAMAGE, DEFENDER, EFFECT, ELSE, EVENT, EXPIRES, FACE_DOWN, FROM, HIDDEN, IF, INDEX, LAND, MANA, MULTIPLY, NAME, NUMBER, OBJECT, ORDERED, PERMANENT, PHASE, PLAYER, POWER, PREVENT, @SuppressWarnings("hiding")
		RANDOM, REASON, RESOLVING, SOURCE, STEP, SUBTYPE, SUPERTYPE, TAKER, TAPPED, TARGET, THEN, TO, TOUGHNESS, TURN, TYPE, USES, ZONE, ZONE_CHANGE
	}

	public static class ParameterMap extends java.util.HashMap<Parameter, SetGenerator>
	{
		private static final long serialVersionUID = 1L;
	}

	/**
	 * Any EventType calling this should read the documentation on
	 * {@link EventType#addsMana()}.
	 * 
	 * @eparam SOURCE: what is producing the mana
	 * @eparam MANA: mana being added, or colors of mana to add
	 * @eparam MULTIPLY: the number of times to duplicate the mana in the pool
	 * before exploding [optional; default is 1]
	 * @eparam NUMBER: the number of times to add the exploded mana into the
	 * players pool [optional; default is 1]
	 * @eparam PLAYER: who is getting the mana
	 * @eparam RESULT: the mana objects as they exist in the player(s) manapool
	 */
	public static final EventType ADD_MANA = new EventType("ADD_MANA")
	{
		@Override
		public boolean addsMana()
		{
			return true;
		}

		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();
			Identified producer = parameters.get(Parameter.SOURCE).getOne(Identified.class);
			java.util.Set<Color> colors = parameters.get(Parameter.MANA).getAll(Color.class);
			java.util.Set<ManaSymbol.ManaType> types = parameters.get(Parameter.MANA).getAll(ManaSymbol.ManaType.class);
			ManaPool pool = new ManaPool();

			ManaSymbol addition = new ManaSymbol("");
			addition.colors.addAll(colors);
			for(ManaSymbol.ManaType type: types)
			{
				if(ManaSymbol.ManaType.COLORLESS == type)
				{
					addition.colorless = 1;
					addition.name += "1";
				}
				else
				{
					Color color = type.getColor();
					addition.colors.add(color);
					addition.name += color.getLetter();
				}
			}
			if(!addition.isZero())
				pool.add(addition);

			pool.addAll(parameters.get(Parameter.MANA).getAll(ManaSymbol.class));

			int multiply = 1;
			if(parameters.containsKey(Parameter.MULTIPLY))
				multiply = Sum.get(parameters.get(Parameter.MULTIPLY));

			if(multiply != 1)
			{
				ManaPool newPool = new ManaPool();

				if(multiply > 1)
				{
					newPool.addAll(pool);
					for(ManaSymbol symbol: pool)
						for(int i = 1; i < multiply; ++i)
							newPool.add(symbol.create());
				}

				pool = newPool;
			}

			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));

			boolean snow = false;
			if(producer.isGameObject())
				snow = ((GameObject)producer).getSuperTypes().contains(SuperType.SNOW);
			for(Player actualPlayer: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				Player physicalPlayer = actualPlayer.getPhysical();
				java.util.Set<CostCollection> choices = pool.explode("Add");
				// TODO : Ticket 430

				ManaPool chosen = null;
				if(choices.isEmpty()) // empty pool to start with
					chosen = pool;
				else if(choices.size() == 1)
					chosen = choices.iterator().next().manaCost;
				else
				{
					// if all of the choices are mana of a single color, present
					// it as a color choice
					boolean allSingleColorSymbols = true;
					for(CostCollection choiceCollection: choices)
					{
						ManaPool choice = choiceCollection.manaCost;
						if(choice.converted() != 1)
						{
							allSingleColorSymbols = false;
							break;
						}
						ManaSymbol symbol = choice.first();
						if(symbol.colorless != 0)
						{
							allSingleColorSymbols = false;
							break;
						}
						if(symbol.colors.size() != 1)
						{
							allSingleColorSymbols = false;
							break;
						}
					}

					if(allSingleColorSymbols)
					{
						java.util.Set<Color> colorChoices = java.util.EnumSet.noneOf(Color.class);
						for(CostCollection choice: choices)
							colorChoices.add(choice.manaCost.first().colors.iterator().next());
						Color chosenColor = physicalPlayer.chooseColor(colorChoices, producer.ID);

						ManaSymbol s = pool.first().create();
						s.colors = java.util.EnumSet.of(chosenColor);
						s.colorless = 0;
						chosen = new ManaPool(java.util.Collections.singleton(s));
					}
					else
						chosen = physicalPlayer.sanitizeAndChoose(game.actualState, 1, choices, PlayerInterface.ChoiceType.MANA_EXPLOSION, PlayerInterface.ChooseReason.HYBRID_MANA).iterator().next().manaCost;
				}

				for(int i = 0; i < number; ++i)
				{
					// Use the reference mana to create a new mana to
					// add to the pool
					for(ManaSymbol newMana: chosen)
					{
						newMana = newMana.create();
						newMana.sourceID = producer.ID;
						newMana.isSnow = snow;
						physicalPlayer.pool.add(newMana);
						result.add(newMana);
						if(game.currentAction != null)
							game.currentAction.manaProduced.add(newMana);
					}
				}
			}

			GameObject source = event.getSource();
			if(source.isActivatedAbility())
				((ActivatedAbility)source.getPhysical()).addedMana(result.getAll(ManaSymbol.class));

			event.setResult(Identity.instance(result));
			return true;
		}
	};

	/**
	 * @eparam PLAYER: the player getting the counters
	 * @eparam NUMBER: the number to add
	 * @eparam RESULT: the counters as they exist on the player
	 */
	public static final EventType ADD_POISON_COUNTERS = new EventType("ADD_POISON_COUNTERS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<Counter> counters = new java.util.HashSet<Counter>();
			int number = Sum.get(parameters.get(Parameter.NUMBER));
			java.util.Set<Player> players = parameters.get(Parameter.PLAYER).getAll(Player.class);
			for(Player player: players)
			{
				Player physical = player.getPhysical();
				for(int i = 0; i < number; ++i)
				{
					Counter counter = new Counter(Counter.CounterType.POISON, player.ID);
					if(physical.counters.add(counter))
						counters.add(counter);
				}
			}
			event.setResult(Identity.instance(counters));
			return true;
		}
	};

	/**
	 * Causes a single creature's combat damage to be assigned. This event type
	 * determines who will assign the damage, as well as how much damage to
	 * assign (thanks, Illusionary Mask).
	 * 
	 * @eparam OBJECT: the creature assigning damage
	 * @eparam TARGET: a {@link java.util.List} of {@link Target}s, where each
	 * target points at a damage-receival candidate. Upon completion of this
	 * event, this list's targets will have their {@link Target#division} fields
	 * set to the player's assignments.
	 * @eparam NUMBER: the amount of damage to assign, if that amount would be
	 * different from the amount the creature would normally assign (for
	 * example, if there is an effect telling the creature to assign no combat
	 * damage this turn). don't set this parameter at all if the creture is to
	 * assign its normal amount of combat damage.
	 * @eparam RESULT: empty
	 */
	public static final EventType ASSIGN_COMBAT_DAMAGE = new EventType("ASSIGN_COMBAT_DAMAGE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			GameObject assigning = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			int amount = assigning.getPower();
			if(parameters.containsKey(Parameter.NUMBER))
				amount = Sum.get(parameters.get(Parameter.NUMBER));
			else if(game.actualState.assignCombatDamageUsingToughness)
				amount = assigning.getToughness();
			amount = Math.max(amount, 0);

			java.util.List<Target> assignTo = parameters.get(Parameter.TARGET).getOne(java.util.List.class);

			if(amount == 0)
				for(Target t: assignTo)
					t.division = 0;
			else
				assigning.getController(game.actualState).divide(amount, 0, assigning.ID, "damage", assignTo);

			event.setResult(Empty.set);
			return true;
		}
	};

	/**
	 * @eparam OBJECT: the GameObject instances attaching by
	 * @eparam TARGET: the AttachableTo which will become attached to
	 * @eparam RESULT: the AttachableBy instances which are now attached to
	 */
	public static final EventType ATTACH = new EventType("ATTACH")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			AttachableTo targetObject = parameters.get(Parameter.TARGET).getOne(AttachableTo.class);

			if(targetObject == null)
				return false;

			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(!object.canAttachTo(game, targetObject))
					return false;

			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean attachedAll = true;
			java.util.Set<GameObject> attachments = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			AttachableTo target = parameters.get(Parameter.TARGET).getOne(AttachableTo.class);
			if(target == null || (target.isGameObject() && ((GameObject)target).isGhost()))
			{
				event.setResult(Empty.set);
				return true;
			}
			int attachmentID = ((Identified)target).ID;

			// a collection of things that are already attached to the specified
			// object or player
			java.util.Collection<Integer> reAttachments = new java.util.LinkedList<Integer>();
			Set detachables = new Set();
			for(GameObject o: attachments)
				if(o.getAttachedTo() != -1)
				{
					if(o.getAttachedTo() == attachmentID)
						reAttachments.add(o.ID);
					else
						detachables.add(o);
				}

			java.util.Iterator<GameObject> iter = attachments.iterator();
			while(iter.hasNext())
			{
				GameObject o = iter.next();
				if(!o.canAttachTo(game, target))
				{
					detachables.remove(o);
					iter.remove();
					attachedAll = false;
				}
			}

			if(!detachables.isEmpty())
			{
				java.util.Map<Parameter, Set> detachParameters = new java.util.HashMap<Parameter, Set>();
				detachParameters.put(Parameter.OBJECT, detachables);
				createEvent(game, "Unattach before attaching", EventType.UNATTACH, detachParameters).perform(event, false);
			}

			for(GameObject o: attachments)
			{
				target.getPhysical().addAttachment(o.ID);
				o.getPhysical().setAttachedTo(attachmentID);

				// 613.6d If an Aura, Equipment, or Fortification becomes
				// attached to an object or player, the Aura, Equipment, or
				// Fortification receives a new timestamp at that time.
				if(!reAttachments.contains(o.ID))
					event.addToNeedsNewTimestamps(o);
			}
			event.setResult(Identity.instance(target));
			return attachedAll;
		}
	};

	/**
	 * @eparam OBJECT: the {@link GameObject} which will be attached (must be a
	 * single {@link GameObject})
	 * @eparam PLAYER: the player that is attaching (and thus choosing)
	 * @eparam CHOICE: the AttachableTo which will become chosen from to attach
	 * to
	 * @eparam RESULT: the GameObject instances which are now attached to
	 */
	public static final EventType ATTACH_TO_CHOICE = new EventType("ATTACH_TO_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CHOICE;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set object = parameters.get(Parameter.OBJECT);
			Set choices = parameters.get(Parameter.CHOICE);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			// reset this players successes to zero
			Set failedCards = new Set();

			while(true)
			{
				if(choices.isEmpty())
					return false;

				GameObject thisCard = choices.getOne(GameObject.class);
				choices.remove(thisCard);
				java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
				parameters.put(Parameter.OBJECT, object);
				parameters.put(Parameter.TARGET, new Set(thisCard));

				// if the player can attach the card, go to next player.
				// otherwise, give other players the chance to attach the
				// card.
				if(createEvent(game, player + " attaches " + thisCard + " to " + object + ".", ATTACH, newParameters).attempt(event))
					break;
				failedCards.add(thisCard);
			}

			// next player is given the chance to choose all cards this
			// player failed on
			choices.addAll(failedCards);

			return true;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<AttachableTo> validChoices = parameters.get(Parameter.CHOICE).getAll(AttachableTo.class);
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			java.util.Iterator<AttachableTo> i = validChoices.iterator();
			while(i.hasNext())
			{
				AttachableTo to = i.next();
				java.util.Map<Parameter, Set> attachParameters = new java.util.HashMap<Parameter, Set>();
				attachParameters.put(Parameter.OBJECT, new Set(object));
				attachParameters.put(Parameter.TARGET, new Set(to));
				Event attachEvent = createEvent(game, player + " attaches " + object + " to " + to + ".", ATTACH, attachParameters);
				if(!attachEvent.attempt(event))
					i.remove();
			}

			PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(1, 1, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.ATTACH);
			chooseParameters.thisID = object.ID;
			java.util.Collection<AttachableTo> choices = player.sanitizeAndChoose(game.actualState, validChoices, chooseParameters);
			if(choices.isEmpty())
				event.allChoicesMade = false;
			event.putChoices(player, choices);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean ret = event.allChoicesMade;
			Set result = new Set();
			Set object = parameters.get(Parameter.OBJECT);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			Set choices = event.getChoices(player);
			if(!choices.isEmpty())
			{
				// perform the attach event
				java.util.Map<Parameter, Set> attachParameters = new java.util.HashMap<Parameter, Set>();
				attachParameters.put(Parameter.OBJECT, object);
				attachParameters.put(Parameter.TARGET, choices);
				Event attachEvent = createEvent(game, player + " attaches " + object + " to " + choices + ".", ATTACH, attachParameters);
				if(!attachEvent.perform(event, false))
					ret = false;
				result.addAll(attachEvent.getResult());
			}

			event.setResult(Identity.instance(result));
			return ret;
		}
	};

	/**
	 * @eparam ATTACKER: the blocked attacker
	 * @eparam DEFENDER: the blockers
	 * @eparam RESULT: empty
	 */
	public static final EventType BECOMES_BLOCKED = new EventType("BECOMES_BLOCKED")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.ATTACKER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set attackers = parameters.get(Parameter.ATTACKER);

			for(GameObject attacker: attackers.getAll(GameObject.class))
			{
				GameObject physicalAttacker = attacker.getPhysical();
				if(physicalAttacker.getBlockedByIDs() == null)
				{
					attacker.setBlockedByIDs(new java.util.LinkedList<Integer>());
					physicalAttacker.setBlockedByIDs(new java.util.LinkedList<Integer>());
				}
			}

			for(GameObject blocker: parameters.get(Parameter.DEFENDER).getAll(GameObject.class))
			{
				Set blockerSet = new Set(blocker);
				java.util.Map<Parameter, Set> blockedParameters = new java.util.HashMap<Parameter, Set>();
				blockedParameters.put(Parameter.ATTACKER, attackers);
				blockedParameters.put(Parameter.DEFENDER, blockerSet);
				createEvent(game, blockerSet + " blocks " + attackers, EventType.BECOMES_BLOCKED_BY_ONE, blockedParameters).perform(event, false);
			}

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam ATTACKER: the blocked attacker
	 * @eparam DEFENDER: a single object blocking the attacker
	 * @eparam RESULT: empty
	 */
	public static final EventType BECOMES_BLOCKED_BY_ONE = new EventType("BECOMES_BLOCKED_BY_ONE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.ATTACKER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int blockerID = parameters.get(Parameter.DEFENDER).getOne(GameObject.class).ID;
			for(GameObject attacker: parameters.get(Parameter.ATTACKER).getAll(GameObject.class))
			{
				GameObject physicalAttacker = attacker.getPhysical();
				if(!physicalAttacker.getBlockedByIDs().contains(blockerID))
				{
					attacker.getBlockedByIDs().add(blockerID);
					physicalAttacker.getBlockedByIDs().add(blockerID);
				}
			}
			event.setResult(Empty.set);
			return true;
		}

	};
	/**
	 * This is a marker event for the moment at which a spell or ability becomes
	 * played. Mana abilities resolve here.
	 * 
	 * 601.2h Once the steps described in 601.2a-g are completed, the spell
	 * becomes cast. Any abilities that trigger when a spell is cast or put onto
	 * the stack trigger at this time.
	 * 
	 * @eparam PLAYER: for spells, the player who cast the spell; for activated
	 * abilities, the player who activated the ability; for triggered abilities,
	 * not present; for lands, the player playing the land
	 * @eparam OBJECT: the spell/ability/land
	 * @eparam RESULT: the first GameObject in OBJECT
	 */
	public static final EventType BECOMES_PLAYED = new EventType("BECOMES_PLAYED")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject played = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			if(played.isManaAbility())
			{
				// Refresh the game state here to make sure that, among other
				// things, the actual player has the correct amount of mana in
				// their pool (important for DOUBLE_MANA)
				game.refreshActualState();

				played = played.getActual();
				played.resolve();
			}
			event.setResult(Identity.instance(played));
			return true;
		}
	};
	/**
	 * @eparam OBJECT: the targetter
	 * @eparam TARGET: the targettee
	 * @eparam RESULT: empty
	 */
	public static final EventType BECOMES_TARGET = new EventType("BECOMES_TARGET")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.TARGET;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam ATTACKER: the unblocked attacker
	 * @eparam RESULT: empty
	 */
	public static final EventType BECOMES_UNBLOCKED = new EventType("BECOMES_UNBLOCKED")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.ATTACKER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam PHASE: the phase
	 * @eparam RESULT: the phase
	 */
	public static final EventType BEGIN_PHASE = new EventType("BEGIN_PHASE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PHASE;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Phase phase = parameters.get(Parameter.PHASE).getOne(Phase.class);
			game.physicalState.setCurrentPhase(phase);
			event.setResult(Identity.instance(phase));
			return true;
		}
	};
	/**
	 * @eparam STEP: the step
	 * @eparam RESULT: the step
	 */
	public static final EventType BEGIN_STEP = new EventType("BEGIN_STEP")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.STEP;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Step step = parameters.get(Parameter.STEP).getOne(Step.class);
			game.physicalState.setCurrentStep(step);
			event.setResult(Identity.instance(step));
			return true;
		}
	};

	/**
	 * @eparam TURN: the turn
	 * @eparam RESULT: the turn
	 */
	public static final EventType BEGIN_TURN = new EventType("BEGIN_TURN")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Turn turn = parameters.get(Parameter.TURN).getOne(Turn.class);
			game.physicalState.setCurrentTurn(turn);
			event.setResult(Identity.instance(turn));
			return true;
		}
	};

	/**
	 * Don't use this EventType directly. It should always be wrapped within an
	 * action so that any failure in casting the spell or ability will
	 * automatically be reverted.
	 * 
	 * @eparam PLAYER: the player playing the spell/ability
	 * @eparam ALTERNATE_COST: a set of forced alternate costs represented as
	 * events and/or mana pools [optional; default = no forced alternate cost]
	 * @eparam OBJECT: the spell/ability
	 * @eparam ACTION: if the object being played is a spell being cast using an
	 * action, that action; otherwise leave it out
	 * @eparam FACE_DOWN: if OBJECT is a spell and is to be cast face down, a
	 * class extending CopiableValues defining the characteristics the face-down
	 * spell is to have (see {@link ZoneChange#faceDownCharacteristics})
	 * @eparam RESULT: the object as it exists on the stack, empty otherwise
	 */
	public static final EventType CAST_SPELL_OR_ACTIVATE_ABILITY = new EventType("CAST_SPELL_OR_ACTIVATE_ABILITY")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

			if(!object.getModes().isEmpty())
			{
				// TODO : Is this code duplication? Does this functionality
				// differ from mode.canBeChosen()? If so, how is the code
				// different, and what is the reason for the difference?
				// -RulesGuru
				int legalModes = 0;
				for(Mode mode: object.getModes())
				{
					java.util.LinkedList<Target> targets = new java.util.LinkedList<Target>();
					for(Target target: mode.targets)
						targets.add(target);
					if(this.checkTargets(game, object, targets))
						legalModes++;
				}

				Integer minimum = Minimum.get(object.getNumModes());
				if(minimum == null || legalModes < minimum)
					return false;
			}

			if(parameters.containsKey(Parameter.ALTERNATE_COST))
			{
				// if it hasn't been set, GameObject.getDefinedX returns -1,
				// which still works for this.
				// From Gatherer Rulings: If another spell or ability instructs
				// you to cast Mind Grind "without paying its mana cost," you
				// won't be able to. You must pick 0 as the value of X in the
				// mana cost of a spell being cast "without paying its mana
				// cost," but the X in Mind Grind's mana cost can't be 0.
				if(object.getMinimumX() > 0 && object.getDefinedX() < object.getMinimumX())
					return false;

				java.util.Set<EventFactory> factories = new java.util.HashSet<EventFactory>();
				factories.addAll(parameters.get(Parameter.ALTERNATE_COST).getAll(EventFactory.class));

				CostCollection costs = parameters.get(Parameter.ALTERNATE_COST).getOne(CostCollection.class);
				if(costs != null)
					factories.addAll(costs.events);

				for(EventFactory cost: factories)
					if(!cost.createEvent(event.game, null).attempt(event))
						return false;
			}

			return true;
		}

		public boolean checkTargets(Game game, GameObject object, java.util.List<Target> targets)
		{
			return this.checkTargets(game, object, targets, new java.util.HashSet<Integer>());
		}

		public boolean checkTargets(Game game, GameObject object, java.util.List<Target> targets, java.util.Set<Integer> ignoreThese)
		{
			if(targets.size() == 0)
				return true;

			Target target = targets.remove(0);
			Set choices = target.legalChoicesNow(game, object);

			for(Identified potentialTarget: choices.getAll(Identified.class))
			{
				if(ignoreThese.contains(potentialTarget.ID))
					continue;
				if(target.restrictFromLaterTargets)
					ignoreThese.add(potentialTarget.ID);
				if(this.checkTargets(game, object, targets, ignoreThese))
					return true;
				ignoreThese.remove(potentialTarget.ID);
			}

			targets.add(0, target);

			return false;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			// Rule 409!
			GameObject beingPlayed = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			// 601.2a The player announces that he or she is casting the spell.
			// That card (or that copy of a card) physically moves from the zone
			// it's in to the stack. It becomes the topmost object on the stack.
			// 602.2a The player announces that he or she is activating the
			// ability. If an activated ability is being played from a hidden
			// zone, the card that has that ability is revealed. That ability is
			// created on the stack as an object that's not a card. It becomes
			// the topmost object on the stack.
			// TODO : If the ability is being played from a hidden zone,
			// reveal the source of the ability.
			Player playerActing = parameters.get(Parameter.PLAYER).getOne(Player.class).getActual();
			GameObject onStack = null;
			if(parameters.containsKey(Parameter.FACE_DOWN))
				onStack = beingPlayed.putOnStack(playerActing, parameters.get(Parameter.FACE_DOWN).getOne(Class.class));
			else
				onStack = beingPlayed.putOnStack(playerActing, null);
			GameObject physicalOnStack = onStack.getPhysical();

			if(parameters.containsKey(Parameter.ACTION))
				physicalOnStack.castAction = parameters.get(Parameter.ACTION).getOne(CastSpellAction.class);

			playerActing = playerActing.getActual();

			if(onStack.isManaAbility())
				game.physicalState.currentlyResolvingManaAbilities.add((NonStaticAbility)onStack);
			else if(onStack.isSpell())
			{
				onStack.zoneCastFrom = beingPlayed.zoneID;
				onStack.playerCasting = playerActing.ID;
				physicalOnStack.zoneCastFrom = beingPlayed.zoneID;
				physicalOnStack.playerCasting = playerActing.ID;
			}
			// physicalOnStack.setController(playerActing);
			// onStack.setController(playerActing);
			// [a spell/ability's controller is now required to be set by the
			// time GameObject.putOnStack finishes. -RulesGuru]

			// 602.2b The remainder of the process for activating an ability is
			// identical to the process for casting a spell listed in rules
			// 601.2b-h.

			// 601.2b -- Modal spells, X in cost, alternate costs, choosing
			// which color to pay for a hybrid cost, splice
			java.util.Set<EventFactory> factories = new java.util.LinkedHashSet<EventFactory>();
			java.util.Set<Event> costs = new java.util.LinkedHashSet<Event>();
			ManaPool totalManaCost = new ManaPool();

			Set forcedAltCost = parameters.get(Parameter.ALTERNATE_COST);

			if(forcedAltCost != null)
			{
				// alternate mana costs are handled below
				factories.addAll(forcedAltCost.getAll(EventFactory.class));

				java.util.Set<ManaSymbol> altMana = forcedAltCost.getAll(ManaSymbol.class);
				totalManaCost = new ManaPool(altMana);

				CostCollection chosenCost = forcedAltCost.getOne(CostCollection.class);
				if(chosenCost != null)
				{
					onStack.setAlternateCost(chosenCost);
					physicalOnStack.setAlternateCost(chosenCost);

					factories.addAll(chosenCost.events);
					totalManaCost.addAll(chosenCost.manaCost);
				}
			}
			else
			{
				CostCollection chosenCost = (onStack.getManaCost() == null ? null : new CostCollection(CostCollection.TYPE_MANA, onStack.getManaCost()));
				if(onStack.alternateCosts != null && !onStack.alternateCosts.isEmpty())
				{
					java.util.Set<CostCollection> choices = new java.util.HashSet<CostCollection>();
					for(AlternateCost alt: onStack.alternateCosts)
						if(alt.playersMayPay.contains(playerActing))
							choices.add(alt.cost);
					if(chosenCost != null)
						choices.add(chosenCost);
					chosenCost = playerActing.sanitizeAndChoose(game.actualState, 1, choices, PlayerInterface.ChoiceType.ALTERNATE_COST, PlayerInterface.ChooseReason.OPTIONAL_ALTERNATE_COST).get(0);
					if(!chosenCost.type.equals(CostCollection.TYPE_MANA))
					{
						onStack.setAlternateCost(chosenCost);
						physicalOnStack.setAlternateCost(chosenCost);
					}
				}

				if(chosenCost == null)
					return false;

				if(!chosenCost.manaCost.isEmpty())
					totalManaCost.addAll(chosenCost.manaCost);

				factories.addAll(chosenCost.events);
			}

			if(!onStack.optionalAdditionalCosts.isEmpty())
			{
				boolean valid = false;
				java.util.Collection<CostCollection> extras = null;
				java.util.Map<CostCollection, Integer> frequencies = null;
				while(!valid)
				{
					PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(0, null, PlayerInterface.ChoiceType.ALTERNATE_COST, PlayerInterface.ChooseReason.OPTIONAL_ADDITIONAL_COST);
					chooseParameters.allowMultiples = true;
					extras = playerActing.sanitizeAndChoose(game.actualState, onStack.optionalAdditionalCosts, chooseParameters);
					frequencies = new java.util.HashMap<CostCollection, Integer>();

					// validate that the player only multiply chose costs if
					// it's allowed
					valid = true;
					for(CostCollection extra: extras)
					{
						if(frequencies.containsKey(extra))
						{
							if(!extra.allowMultiples)
							{
								valid = false;
								break;
							}
							frequencies.put(extra, frequencies.get(extra) + 1);
						}
						else
							frequencies.put(extra, 1);
					}
				}

				for(java.util.Map.Entry<CostCollection, Integer> entry: frequencies.entrySet())
				{
					totalManaCost.addAll(entry.getKey().manaCost.duplicate(entry.getValue()));
					factories.addAll(entry.getKey().events);
				}
				for(GameObject o: onStack.andPhysical())
					o.getOptionalAdditionalCostsChosen().addAll(extras);
			}

			// "As an additional cost to cast..." costs apply even when a forced
			// alternate cost is specified.
			factories.addAll(onStack.getCosts());

			// Splice
			ManaPool spliceMana = new ManaPool();
			boolean spliced = false;
			if(onStack.isSpell())
			{
				java.util.Map<GameObject, CostCollection> splicables = new java.util.HashMap<GameObject, CostCollection>();
				cards: for(GameObject card: playerActing.getHand(game.actualState).objects)
					for(Keyword k: card.getKeywordAbilities())
						if(k.isSplice())
						{
							org.rnd.jmagic.abilities.keywords.Splice splice = (org.rnd.jmagic.abilities.keywords.Splice)k;
							if(onStack.getSubTypes().contains(splice.getSubType()))
							{
								if(card.getModes().iterator().next().canBeChosen(game, onStack))
									splicables.put(card, splice.getCost());
								continue cards;
							}
						}

				PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(0, splicables.size(), PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.SPLICE_OBJECTS);
				chooseParameters.thisID = onStack.ID;
				java.util.List<GameObject> splice = playerActing.sanitizeAndChoose(game.actualState, splicables.keySet(), chooseParameters);

				if(!splice.isEmpty())
				{
					java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
					revealParameters.put(Parameter.CAUSE, new Set(game));
					revealParameters.put(Parameter.OBJECT, new Set(splice));
					Event revealSplices = createEvent(game, "Reveal " + splice, REVEAL, revealParameters);
					revealSplices.perform(event, true);

					playerActing = playerActing.getActual();
					onStack = onStack.getActual();

					// 702.44b ... If you're splicing more than one card onto a
					// spell, reveal them all at once and choose the order in
					// which their instructions will be followed.
					if(splice.size() > 1)
						splice = playerActing.sanitizeAndChoose(game.actualState, splice.size(), splice, PlayerInterface.ChoiceType.OBJECTS_ORDERED, PlayerInterface.ChooseReason.SPLICE_ORDER);

					for(GameObject object: splice)
					{
						spliced = true;
						object.getActual().spliceOnto(physicalOnStack);

						CostCollection spliceCost = splicables.get(object);
						spliceMana.addAll(spliceCost.manaCost);
						for(EventFactory cost: spliceCost.events)
							costs.add(cost.createEvent(game, onStack));
					}
				}
			}

			// A value of X must be chosen if the mana component of the cost
			// includes X ...
			boolean usesX = totalManaCost.usesX();
			// ... or if some other cost includes X.
			if(!usesX)
				for(EventFactory factory: factories)
					if(factory.willUseX())
					{
						usesX = true;
						break;
					}
			// If a value of X must be chosen, choose it now. This must be done
			// before mode selection so that modes whose selectability depends
			// on the value of X (i.e., Repeal, Profane Command) can determine
			// their selectability correctly. Same with other variable costs.
			if(usesX)
			{
				int newX = onStack.getDefinedX();
				if(newX == -1)
					newX = playerActing.chooseNumber(new org.rnd.util.NumberRange(onStack.getMinimumX(), null), "Choose a value for X.");
				else if(newX < onStack.getMinimumX())
					return false;
				onStack.setValueOfX(newX);
				onStack.getPhysical().setValueOfX(newX);
				totalManaCost = totalManaCost.expandX(onStack.getValueOfX(), onStack.xRestriction);
			}

			CostCollection chosenManaCost = null;
			{
				java.util.Set<CostCollection> manaCostExplosions = totalManaCost.explode(CostCollection.TYPE_MANA);
				chosenManaCost = manaCostExplosions.iterator().next();
				if(manaCostExplosions.size() > 1)
					chosenManaCost = playerActing.sanitizeAndChoose(game.actualState, 1, manaCostExplosions, PlayerInterface.ChoiceType.MANA_EXPLOSION, PlayerInterface.ChooseReason.CHOOSE_MANA_COST).get(0);
			}

			totalManaCost.clear();
			if(!chosenManaCost.manaCost.isEmpty())
				totalManaCost.addAll(chosenManaCost.manaCost);
			if(!spliceMana.isEmpty())
				totalManaCost.addAll(spliceMana);
			factories.addAll(chosenManaCost.events);

			java.util.Map<Event, EventFactory> generatedCosts = new java.util.HashMap<Event, EventFactory>();

			for(EventFactory costFactory: factories)
			{
				Event cost = costFactory.createEvent(game, onStack);
				costs.add(cost);
				generatedCosts.put(cost, costFactory);
				physicalOnStack.paidCost(costFactory, cost);
			}
			for(Event cost: costs)
				if(!cost.makeChoices(event))
					return false;

			game.refreshActualState();
			onStack = onStack.getActual();
			playerActing = playerActing.getActual();

			// If there are more modes than the spell is allowed to perform and
			// no splice is involved (PLEASE NO MODAL SPLICE SPELLS OH GOD), ask
			// the player to choose the modes
			Integer minimum = Minimum.get(onStack.getNumModes());
			if(!spliced && (minimum == null || onStack.getModes().size() > minimum))
			{
				physicalOnStack.selectModes();

				// It's possible that not enough modes were chosen, for example,
				// if Branching Bolt is cast with no creatures in play
				if(Intersect.get(onStack.getNumModes(), new Set(physicalOnStack.getSelectedModes().size())).isEmpty())
					return false;
			}
			// Otherwise, choose all the modes
			else
				for(Mode mode: onStack.getModes())
				{
					// If we have to choose all the modes, and even one can't be
					// chosen, fail.
					if(!mode.canBeChosen(game, onStack))
						return false;
					physicalOnStack.getSelectedModes().add(mode);
				}

			// Copy the selected modes to the actual version
			onStack.setSelectedModes(physicalOnStack.getSelectedModes());

			// 601.2c -- Choose targets
			game.refreshActualState();
			onStack = game.actualState.getByIDObject(onStack.ID);

			if(!onStack.selectTargets())
				return false;

			for(Mode mode: onStack.getModes())
			{
				if(!onStack.getSelectedModes().contains(mode))
					continue;
				if(null == mode.targets)
					return false;
				for(Target target: mode.targets)
				{
					if(null == target)
						return false;
					java.util.List<Target> chosenTargets = new java.util.LinkedList<Target>(onStack.getChosenTargets().get(target));
					onStack.getPhysical().getChosenTargets().put(target, chosenTargets);
				}
			}

			game.refreshActualState();
			onStack = onStack.getActual();
			playerActing = playerActing.getActual();

			// 601.2d -- Divisions (like Violent Eruption)
			for(int i = 1; i <= onStack.getModes().size(); i++)
			{
				Set division = onStack.getMode(i).division.evaluate(game, onStack);
				int divisionAmount = division.getOne(Integer.class);
				if(divisionAmount != 0)
				{
					java.util.LinkedList<Target> targets = new java.util.LinkedList<Target>();
					for(Target possibleTarget: onStack.getPhysical().getMode(i).targets)
						for(Target chosenTarget: onStack.getPhysical().getChosenTargets().get(possibleTarget))
							targets.add(chosenTarget);
					playerActing.divide(divisionAmount, 1, onStack.ID, division.getOne(String.class), targets);
				}
			}

			// 601.2e -- Determine total cost

			if(onStack.isActivatedAbility())
			{
				ActivatedAbility abilityOnStack = (ActivatedAbility)onStack;
				if(abilityOnStack.costsTap || abilityOnStack.costsUntap)
				{
					// get the physical version of the source in case it changes
					// (IE; BECOMES A GHOST)
					Identified sourceIdentified = ((ActivatedAbility)onStack).getSource(game.physicalState);
					if(!sourceIdentified.isGameObject())
						throw new IllegalStateException(onStack + ": Attempt to " + (abilityOnStack.costsTap ? "tap " : "untap ") + sourceIdentified + " which isn't an object");

					GameObject source = ((GameObject)sourceIdentified);
					if(source.zoneID != game.actualState.battlefield().ID)
						throw new IllegalStateException(onStack + ": Attempt to " + (abilityOnStack.costsTap ? "tap " : "untap ") + source + " which isn't a permanent");

					java.util.Map<Parameter, Set> costParameters = new java.util.HashMap<Parameter, Set>();
					costParameters.put(Parameter.CAUSE, new Set(playerActing));
					costParameters.put(Parameter.OBJECT, new Set(source));

					if(abilityOnStack.costsTap)
					{
						Event cost = createEvent(game, "(T)", EventType.TAP_PERMANENTS, costParameters);
						costs.add(cost);
						physicalOnStack.paidCost(GameObject.TAP_COST_FACTORY, cost);
					}
					if(abilityOnStack.costsUntap)
					{
						Event cost = createEvent(game, "(Q)", EventType.UNTAP_PERMANENTS, costParameters);
						costs.add(cost);
						physicalOnStack.paidCost(GameObject.UNTAP_COST_FACTORY, cost);
					}
				}
			}

			// These two things can be affected by choices made for additional
			// costs.
			// (damn you convoke)
			game.refreshActualState();
			playerActing = playerActing.getActual();

			// additional costs
			for(java.util.Map.Entry<Set, ManaPool> costAddition: game.actualState.manaCostAdditions.entrySet())
				if(costAddition.getKey().contains(onStack))
					totalManaCost.addAll(costAddition.getValue());

			// cost reductions that can't reduce to less than one mana
			for(java.util.Map.Entry<Set, ManaPool> costReduction: game.actualState.manaCostRestrictedReductions.entrySet())
				if(costReduction.getKey().contains(onStack))
				{
					ManaPool reduction = costReduction.getValue();
					reduction = playerActing.sanitizeAndChoose(game.actualState, 1, reduction.explode(CostCollection.TYPE_REDUCE_COST), PlayerInterface.ChoiceType.MANA_EXPLOSION, PlayerInterface.ChooseReason.COST_REDUCTION).get(0).manaCost;
					totalManaCost.reduce(reduction);
					if(totalManaCost.converted() < 1)
						totalManaCost.add(new ManaSymbol(ManaSymbol.ManaType.COLORLESS));
				}

			// cost reductions
			for(java.util.Map.Entry<Set, ManaPool> costReduction: game.actualState.manaCostReductions.entrySet())
				if(costReduction.getKey().contains(onStack))
				{
					ManaPool reduction = costReduction.getValue();
					reduction = playerActing.sanitizeAndChoose(game.actualState, 1, reduction.explode(CostCollection.TYPE_REDUCE_COST), PlayerInterface.ChoiceType.MANA_EXPLOSION, PlayerInterface.ChooseReason.COST_REDUCTION).get(0).manaCost;
					totalManaCost.reduce(reduction);
				}

			// trinisphere's effect
			for(java.util.Map.Entry<Set, Integer> costMinimum: game.actualState.manaCostMinimums.entrySet())
				if(costMinimum.getKey().contains(onStack))
					totalManaCost.minimum(costMinimum.getValue());

			// set up the mana cost payment event
			for(ManaSymbol m: totalManaCost)
				m.sourceID = onStack.ID;

			if(!totalManaCost.isEmpty())
			{
				java.util.Map<Parameter, Set> payManaParameters = new java.util.HashMap<Parameter, Set>();
				payManaParameters.put(Parameter.CAUSE, new Set(game));
				payManaParameters.put(Parameter.OBJECT, new Set(onStack));
				payManaParameters.put(Parameter.COST, new Set(totalManaCost));
				payManaParameters.put(Parameter.PLAYER, new Set(playerActing));

				Event payMana = createEvent(game, "Pay " + totalManaCost, PAY_MANA, payManaParameters);
				costs.add(payMana);
				physicalOnStack.paidCost(GameObject.MANA_COST_FACTORY, payMana);

				// 601.2f If the total cost includes a mana payment, the player
				// then has a chance to activate mana abilities
				playerActing.mayActivateManaAbilities();
			}

			// 601.2g -- The player pays the costs in the order of his or
			// her choosing
			if(0 < costs.size())
			{
				int numCosts = costs.size();
				PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(numCosts, numCosts, PlayerInterface.ChoiceType.COSTS, PlayerInterface.ChooseReason.ORDER_COSTS);
				chooseParameters.thisID = onStack.ID;
				for(Event cost: playerActing.getActual().sanitizeAndChoose(game.actualState, costs, chooseParameters))
				{
					cost.isCost = true;
					cost.isEffect = false;
					if(!cost.perform(event, true))
						return false;
				}
			}

			// 601.2h Once the steps described in 601.2a-g are completed, the
			// spell becomes cast. Any abilities that trigger when a spell is
			// cast or put onto the stack trigger at this time.
			java.util.Map<Parameter, Set> becomesPlayedParameters = new java.util.HashMap<Parameter, Set>();
			becomesPlayedParameters.put(Parameter.OBJECT, new Set(onStack));
			becomesPlayedParameters.put(Parameter.PLAYER, new Set(playerActing));
			createEvent(game, onStack + " has been played.", BECOMES_PLAYED, becomesPlayedParameters).perform(event, false);

			event.setResult(Identity.instance(onStack));
			return true;
		}
	};
	/**
	 * This is a marker event. Don't call it.
	 * 
	 * @eparam OBJECT: the object
	 * @eparam PLAYER: the old controller (optional; default = no one)
	 * @eparam TARGET: the new controller (optional; default = no one)
	 * @eparam ATTACKER: optional parameter; if present, the objects will not be
	 * removed from combat (intended only for use by
	 * {@link EventType#MOVE_BATCH})
	 * @eparam RESULT: empty
	 */
	public static final EventType CHANGE_CONTROL = new EventType("CHANGE_CONTROL")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Map<Parameter, Set> rfcParameters = new java.util.HashMap<Parameter, Set>();
			rfcParameters.put(Parameter.OBJECT, parameters.get(Parameter.OBJECT));

			java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);

			if(!parameters.containsKey(Parameter.ATTACKER) && game.actualState.currentPhase() != null && game.actualState.currentPhase().type == Phase.PhaseType.COMBAT)
				createEvent(game, "Remove " + objects + " from combat.", REMOVE_FROM_COMBAT, rfcParameters).perform(event, false);

			// When something changes control, it "gains summoning sickness". It
			// will "lose summoning sickness" when its controller's next turn
			// starts.
			java.util.Collection<Integer> changedControlIDs = new java.util.LinkedList<Integer>();
			for(GameObject object: objects)
				changedControlIDs.add(object.ID);
			for(java.util.Collection<Integer> objectIDs: game.physicalState.summoningSick.values())
				objectIDs.removeAll(changedControlIDs);

			if(parameters.containsKey(Parameter.TARGET))
			{
				Player controller = parameters.get(Parameter.TARGET).getOne(Player.class);
				game.physicalState.summoningSick.get(controller.ID).addAll(changedControlIDs);
			}

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam OBJECT: the spell or ability to change a target of
	 * @eparam TARGET: the new assignment for the target
	 * @eparam PLAYER: the player choosing which target to reassign
	 * @eparam RESULT: the objects whose targets were changed
	 */
	public static final EventType CHANGE_SINGLE_TARGET_TO = new EventType("CHANGE_SINGLE_TARGET_TO")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			Identified newTarget = parameters.get(Parameter.TARGET).getOne(Identified.class);
			java.util.Set<Target> canBeChanged = new java.util.HashSet<Target>();

			java.util.Map<Target, Boolean> wasLegal = new java.util.HashMap<Target, Boolean>();

			{
				java.util.Set<Integer> restricted = new java.util.HashSet<Integer>();
				for(Mode checkMode: object.getSelectedModes())
				{
					for(Target checkBaseTarget: checkMode.targets)
					{
						java.util.Set<Integer> thisBaseTarget = new java.util.HashSet<Integer>();
						for(Target checkTarget: object.getChosenTargets().get(checkBaseTarget))
						{
							Set legalCheck = checkTarget.legalChoicesNow(game, object);
							boolean legal = !(restricted.contains(checkTarget.targetID) || !legalCheck.contains(game.actualState.get(checkTarget.targetID)));
							wasLegal.put(checkTarget, legal);
							if(checkTarget.restrictFromLaterTargets)
								restricted.add(checkTarget.targetID);
							thisBaseTarget.add(checkTarget.targetID);
						}
					}
				}
			}

			for(Mode mode: object.getSelectedModes())
			{
				for(Target baseTarget: mode.targets)
				{
					for(Target target: object.getChosenTargets().get(baseTarget))
					{
						Set targets = target.legalChoicesNow(game, object);
						if(targets.contains(newTarget))
						{
							// The target won't "change" if its already set to
							// this target.
							if(target.targetID == newTarget.ID)
								continue;

							int oldValue = target.targetID;
							target.targetID = newTarget.ID;

							boolean illegal = false;
							java.util.Set<Integer> restricted = new java.util.HashSet<Integer>();
							legalityCheck: for(Mode checkMode: object.getSelectedModes())
							{
								for(Target checkBaseTarget: checkMode.targets)
								{
									java.util.Set<Integer> thisBaseTarget = new java.util.HashSet<Integer>();
									for(Target checkTarget: object.getChosenTargets().get(checkBaseTarget))
									{
										Set legalCheck = checkTarget.legalChoicesNow(game, object);
										boolean targetWasLegal = wasLegal.get(checkTarget);
										if(targetWasLegal && (thisBaseTarget.contains(checkTarget.targetID) || restricted.contains(checkTarget.targetID) || !legalCheck.contains(game.actualState.get(checkTarget.targetID))))
										{
											illegal = true;
											break legalityCheck;
										}
										if(checkTarget.restrictFromLaterTargets)
											restricted.add(checkTarget.targetID);
										thisBaseTarget.add(checkTarget.targetID);
									}
								}
							}

							target.targetID = oldValue;

							if(!illegal)
							{
								canBeChanged.add(target);
							}
						}
					}
				}
			}

			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			java.util.List<Target> choice = player.sanitizeAndChoose(game.actualState, 1, canBeChanged, PlayerInterface.ChoiceType.SINGLE_TARGET, PlayerInterface.ChooseReason.TARGET);

			if(!choice.isEmpty())
			{
				event.setResult(Identity.instance(object));
				Target chosenTarget = choice.get(0);
				chosenTarget.targetID = newTarget.ID;

				// Also set the target on the physical object
				setPhysicalTarget: for(java.util.Map.Entry<Target, java.util.List<Target>> entry: object.getChosenTargets().entrySet())
				{
					for(int i = 0; i < entry.getValue().size(); ++i)
					{
						if(entry.getValue().get(i) == chosenTarget)
						{
							GameObject physical = object.getPhysical();
							physical.getChosenTargets().get(entry.getKey()).get(i).targetID = newTarget.ID;
							break setPhysicalTarget;
						}
					}
				}
			}
			else
			{
				event.setResult(Identity.instance());
			}

			return choice.size() == 1;
		}

	};
	/**
	 * @eparam OBJECT: the spell or ability to choose new targets for
	 * @eparam PLAYER: the player choosing the targets
	 * @eparam RESULT: the objects whose targets were changed
	 */
	public static final EventType CHANGE_TARGETS = new EventType("CHANGE_TARGETS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			if(parameters.get(Parameter.PLAYER).getOne(Player.class) == null)
				return false;

			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(!this.canBeChanged(game, object))
					return false;
			return true;
		}

		private boolean canBeChanged(Game game, GameObject originalObject)
		{
			for(Mode mode: originalObject.getSelectedModes())
			{
				java.util.List<Integer> ignoreThese = new java.util.LinkedList<Integer>();
				ignoreThese.add(originalObject.ID);

				for(Target possibleTarget: mode.targets)
				{
					if(!originalObject.getChosenTargets().containsKey(possibleTarget))
						continue;

					for(Target chosenTarget: originalObject.getChosenTargets().get(possibleTarget))
					{
						int previousTarget = chosenTarget.targetID;

						Set legalTargetsNow = chosenTarget.legalChoicesNow(game, originalObject);
						java.util.Set<Target> targetSet = new java.util.HashSet<Target>();

						for(Identified targetObject: legalTargetsNow.getAll(Identified.class))
							if(!ignoreThese.contains(targetObject.ID) && previousTarget != targetObject.ID)
								targetSet.add(new Target(targetObject, chosenTarget));

						if(targetSet.size() == 0)
							return false;

						if(chosenTarget.restrictFromLaterTargets)
							ignoreThese.add(targetSet.iterator().next().targetID);
					}
				}
			}
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);

			Set result = new Set();
			boolean ret = true;

			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				// if the object can be changed and is changed successfully, add
				// it to results. Otherwise, we're returning false.
				if(this.canBeChanged(game, object) && object.reselectTargets(chooser))
				{
					// Update the physical object, unless this is the physical
					// object.
					for(GameObject objectToUpdate: object.andPhysical())
					{
						if(objectToUpdate == object)
							continue;
						objectToUpdate.getChosenTargets().clear();
						objectToUpdate.getChosenTargets().putAll(object.getChosenTargets());
					}
					result.add(object);
				}
				else
					ret = false;
			}

			event.setResult(Identity.instance(result));

			return ret;
		}
	};
	/**
	 * @eparam PLAYER: who is choosing
	 * @eparam EVENT: event factories for the events to choose from
	 * @eparam RESULT: the chosen event
	 */
	public static final EventType CHOOSE_AND_PERFORM = new EventType("CHOOSE_AND_PERFORM")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.EVENT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(EventFactory factory: parameters.get(Parameter.EVENT).getAll(EventFactory.class))
			{
				Event choice = factory.createEvent(game, event.getSource());
				if(choice.attempt(event))
					return true;
			}

			return false;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Collection<Event> validChoices = new java.util.LinkedList<Event>();

			for(EventFactory factory: parameters.get(Parameter.EVENT).getAll(EventFactory.class))
			{
				Event choice = factory.createEvent(game, event.getSource());
				if(choice.attempt(event))
					validChoices.add(choice);
			}

			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			java.util.List<Event> choice = player.getPhysical().sanitizeAndChoose(game.physicalState, 1, validChoices, PlayerInterface.ChoiceType.EVENT, PlayerInterface.ChooseReason.CHOOSE_EVENT);

			if(choice.size() == 0)
			{
				event.setResult(Empty.set);
				return false;
			}

			Event chosenEvent = choice.get(0);
			boolean status = chosenEvent.perform(event, false);
			event.setResult(Identity.instance(chosenEvent));
			return status;
		}
	};
	/**
	 * @eparam CAUSE: the cause
	 * @eparam PLAYER: the players involved in the clash
	 * @eparam RESULT: the winner of the clash
	 */
	public static final EventType CLASH = new EventType("CLASH")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// 701.20. Clash
			//
			// 701.20a To clash, a player reveals the top card of his or her
			// library. That player may then put that card on the bottom of his
			// or her library.
			//
			// ...
			//
			// 701.20c A player wins a clash if that player revealed a card with
			// a higher converted mana cost than all other cards revealed in
			// that clash.

			Set players = parameters.get(Parameter.PLAYER);
			Set revealed = TopCards.instance(1, LibraryOf.instance(Identity.instance(players))).evaluate(game, null);

			java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
			revealParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			revealParameters.put(EventType.Parameter.OBJECT, revealed);
			Event revealEvent = createEvent(game, "Each player clashing reveals the top card of his or her library.", EventType.REVEAL, revealParameters);
			revealEvent.perform(event, false);

			GameObject highestCMC = null;
			int cmc = -1;
			for(GameObject object: revealEvent.getResult().getAll(GameObject.class))
			{
				int newCmc = object.getConvertedManaCost();
				if(newCmc == cmc)
					highestCMC = null;
				else if(newCmc > cmc)
				{
					cmc = newCmc;
					highestCMC = object;
				}
			}

			Player winner = (highestCMC == null ? null : highestCMC.getZone().getOwner(game.actualState));

			for(Player participant: game.actualState.apnapOrder(players))
			{
				EventFactory bottom = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put that card on the bottom of his or her library.");
				bottom.parameters.put(Parameter.CAUSE, Identity.instance(parameters.get(Parameter.CAUSE)));
				bottom.parameters.put(Parameter.INDEX, numberGenerator(-1));
				bottom.parameters.put(Parameter.OBJECT, TopCards.instance(1, LibraryOf.instance(Identity.instance(participant))));
				playerMay(Identity.instance(participant), bottom, "That player may then put that card on the bottom of his or her library.").createEvent(game, event.getSource()).perform(event, false);
			}

			if(winner == null)
				event.setResult(Empty.set);
			else
				event.setResult(Identity.instance(winner));
			return true;
		}
	};
	/**
	 * @eparam CAUSE: the cause
	 * @eparam PLAYER: the player initiating the clash
	 * @eparam RESULT: the winner of the clash
	 */
	public static final EventType CLASH_WITH_AN_OPPONENT = new EventType("CLASH_WITH_AN_OPPONENT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// 701.20b "Clash with an opponent" means
			// "Choose an opponent. You and that opponent each clash."

			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			java.util.Set<Player> choices = OpponentsOf.instance(Identity.instance(player)).evaluate(game, null).getAll(Player.class);
			java.util.List<Player> choice = player.sanitizeAndChoose(game.actualState, 1, choices, PlayerInterface.ChoiceType.PLAYER, PlayerInterface.ChooseReason.CLASH);

			Set players = new Set(choice);
			players.add(player);

			java.util.Map<Parameter, Set> clashParameters = new java.util.HashMap<Parameter, Set>();
			clashParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			clashParameters.put(EventType.Parameter.PLAYER, players);
			Event clashEvent = createEvent(game, "You and an opponent each clash.", EventType.CLASH, clashParameters);
			clashEvent.perform(event, false);

			event.setResult(clashEvent.getResult());
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is copying the spell
	 * @eparam OBJECT: the spell to copy for each possible target
	 * @eparam TARGET: a subset of the targets to limit it to
	 * @eparam RESULT: empty
	 */
	public static final EventType COPY_SPELL_FOR_EACH_TARGET = new EventType("COPY_SPELL_FOR_EACH_TARGET")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean ret = true;

			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

			event.setResult(Empty.set);

			Target toChange = null;

			for(java.util.Map.Entry<Target, java.util.List<Target>> entry: object.getChosenTargets().entrySet())
			{
				// This eventtype doesn't work on objects with multiple
				// targets
				if(entry.getValue().size() > 1)
					return false;

				if(toChange == null)
					toChange = entry.getValue().get(0);
				else
					// This eventtype doesn't work on objects with multiple
					// targets
					return false;
			}

			Set targets = toChange.legalChoicesNow(game, object);
			targets.remove(game.actualState.get(toChange.targetID));

			if(parameters.containsKey(Parameter.TARGET))
				targets = Intersect.get(targets, parameters.get(Parameter.TARGET));

			java.util.Map<Parameter, Set> copyParameters = new java.util.HashMap<Parameter, Set>();
			copyParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			copyParameters.put(Parameter.PLAYER, new Set(object.getController(game.actualState)));
			copyParameters.put(Parameter.OBJECT, new Set(object));
			copyParameters.put(Parameter.TARGET, Empty.set);
			copyParameters.put(Parameter.NUMBER, new Set(targets.size()));
			Event copyEvent = createEvent(game, "Copy the spell for each other possible target.", EventType.COPY_SPELL_OR_ABILITY, copyParameters);
			copyEvent.perform(event, false);

			java.util.Iterator<Identified> targetsIter = targets.getAll(Identified.class).iterator();

			java.util.Set<GameObject> copies = copyEvent.getResult().getAll(GameObject.class);
			for(GameObject copy: copies)
			{
				Identified nextTarget = targetsIter.next();
				for(java.util.List<Target> chosenTargets: copy.getChosenTargets().values())
					chosenTargets.get(0).targetID = nextTarget.ID;
			}

			if(targets.size() > 1)
			{
				Zone stack = game.physicalState.stack();
				java.util.List<GameObject> orderedObjects = object.getController(game.actualState).sanitizeAndChoose(game.actualState, copies.size(), copies, PlayerInterface.ChoiceType.MOVEMENT_STACK, PlayerInterface.ChooseReason.ORDER_STACK);
				for(GameObject copy: orderedObjects)
					if(stack.objects.remove(copy))
						stack.addToTop(copy);
			}

			return ret;
		}
	};
	/**
	 * @eparam CAUSE: what is copying the spell
	 * @eparam OBJECT: the spell to copy
	 * @eparam PLAYER: the player who will control the spell (and choose the new
	 * targets if applicable) (don't specify this if the spell is in the exile
	 * zone)
	 * @eparam TARGET: if this parameter is present, the choice to choose new
	 * targets will not be given
	 * @eparam NUMBER: the number of times to copy the spell [optional; default
	 * is 1]
	 * @eparam RESULT: the copy
	 */
	public static final EventType COPY_SPELL_OR_ABILITY = new EventType("COPY_SPELL_OR_ABILITY")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player controller = null;
			if(parameters.containsKey(Parameter.PLAYER))
				controller = parameters.get(Parameter.PLAYER).getOne(Player.class);

			Set controllerSet = null;
			if(controller != null)
				controllerSet = new Set(controller);

			Set result = new Set();

			int number = (parameters.containsKey(Parameter.NUMBER) ? Sum.get(parameters.get(Parameter.NUMBER)) : 1);

			java.util.Map<GameObject, GameObject> copies = new java.util.HashMap<GameObject, GameObject>();

			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				for(int i = 0; i < number; i++)
				{
					GameObject copy = null;
					if(object.isSpell() || object.isCard())
						copy = new SpellCopy(game.physicalState, object.getName());
					else if(object.isActivatedAbility() || object.isTriggeredAbility())
					{
						// 706.9b A copy of an ability has the same source as
						// the original ability. If the ability refers to its
						// source by name, the copy refers to that same object
						// and not to any other object with the same name. The
						// copy is considered to be the same ability by effects
						// that count how many times that ability has resolved
						// during the turn.
						NonStaticAbility ability = (NonStaticAbility)object;
						NonStaticAbility create = (NonStaticAbility)ability.create(game);
						create.sourceID = ability.sourceID;
						copy = create;
					}
					else
						throw new UnsupportedOperationException("Trying to cast something that isn't a spell, activated ability, or triggered ability");

					if(controller == null)
						copy.ownerID = object.ownerID;
					else
						copy.ownerID = controller.ID;
					game.physicalState.exileZone().addToTop(copy);
					copies.put(copy, object);

					// keep Identity happy
					game.actualState.put(copy);
				}
			}

			if(!copies.isEmpty())
			{
				for(java.util.Map.Entry<GameObject, GameObject> entry: copies.entrySet())
				{
					Zone toZone = entry.getValue().getZone();

					java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
					moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
					if(controllerSet != null)
						moveParameters.put(Parameter.CONTROLLER, controllerSet);
					moveParameters.put(Parameter.OBJECT, new Set(entry.getKey()));
					moveParameters.put(Parameter.SOURCE, new Set(entry.getValue()));
					moveParameters.put(Parameter.TO, new Set(toZone));

					Event movedCopies = createEvent(game, "Put " + entry.getKey() + " onto " + toZone + ".", EventType.PUT_INTO_ZONE_AS_A_COPY_OF, moveParameters);
					movedCopies.perform(event, true);

					if(result == null)
						result = movedCopies.getResult();
					else
						result.addAll(movedCopies.getResult());
				}

				result = NewObjectOf.instance(Identity.instance(result)).evaluate(game, null);

				if(!parameters.containsKey(Parameter.TARGET))
				{
					// Refresh the state to apply all the copy effects
					game.refreshActualState();

					for(GameObject copy: result.getAll(GameObject.class))
					{
						java.util.Set<Integer> targets = new java.util.HashSet<Integer>();
						for(Mode m: copy.getSelectedModes())
							for(Target possibleTarget: m.targets)
								for(Target chosenTarget: copy.getChosenTargets().get(possibleTarget))
									targets.add(chosenTarget.targetID);

						if(!targets.isEmpty())
						{
							EventFactory changeTargetFactory = new EventFactory(EventType.CHANGE_TARGETS, ("Choose new targets for " + copy));
							changeTargetFactory.parameters.put(Parameter.OBJECT, Identity.instance(copy));
							changeTargetFactory.parameters.put(Parameter.PLAYER, Identity.instance(controller));

							EventFactory mayFactory = new EventFactory(EventType.PLAYER_MAY, "You may choose new targets for " + copy);
							mayFactory.parameters.put(Parameter.PLAYER, Identity.instance(controller));
							mayFactory.parameters.put(Parameter.EVENT, Identity.instance(changeTargetFactory));

							EventFactory becomesTargetFactory = new EventFactory(EventType.BECOMES_TARGET, "Targets remain the same.");
							becomesTargetFactory.parameters.put(Parameter.OBJECT, Identity.instance(copy));
							becomesTargetFactory.parameters.put(Parameter.TARGET, IdentifiedWithID.instance(targets));

							java.util.Map<Parameter, Set> targetParameters = new java.util.HashMap<Parameter, Set>();
							targetParameters.put(Parameter.IF, new Set(mayFactory));
							targetParameters.put(Parameter.ELSE, new Set(becomesTargetFactory));
							Event mayEvent = createEvent(game, "You may choose new targets for " + copy, EventType.IF_EVENT_THEN_ELSE, targetParameters);

							mayEvent.perform(event, false);
						}
					}
				}
			}

			event.setResult(result);

			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is countering
	 * @eparam OBJECT: objects being countered
	 * @eparam TO: zone to put the countered object [optional; default is
	 * owner's graveyard]
	 * @eparam RESULT: the objects as they exist after the counter
	 */
	public static final EventType COUNTER = new EventType("COUNTER")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();
			boolean allCountered = true;
			Set counterer = parameters.get(Parameter.CAUSE);
			Zone zone = (parameters.containsKey(Parameter.TO) ? parameters.get(Parameter.TO).getOne(Zone.class) : null);
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
				counterParameters.put(Parameter.CAUSE, counterer);
				counterParameters.put(Parameter.OBJECT, new Set(object));
				if(zone != null)
					counterParameters.put(Parameter.TO, new Set(zone));
				Event counterOne = createEvent(game, "Counter " + object, EventType.COUNTER_ONE, counterParameters);
				counterOne.perform(event, false);
				result.addAll(counterOne.getResult());
			}

			event.setResult(Identity.instance(result));

			return allCountered;
		}
	};
	/**
	 * @eparam CAUSE: what is countering
	 * @eparam OBJECT: object being countered
	 * @eparam TO: zone to put the countered object [optional; default is
	 * owner's graveyard]
	 * @eparam RESULT: the object as it exists after the counter
	 */

	public static final EventType COUNTER_ONE = new EventType("COUNTER_ONE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();
			boolean status = true;

			Set counterer = parameters.get(Parameter.CAUSE);
			Zone zone = (parameters.containsKey(Parameter.TO) ? parameters.get(Parameter.TO).getOne(Zone.class) : null);
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			if(object.zoneID == game.actualState.stack().ID)
			{
				GameObject countered = (zone == null ? object.counterThisObject(counterer) : object.counterThisObject(counterer, zone));
				result.add(countered);
			}
			else
				status = false;
			event.setResult(Identity.instance(result));

			return status;
		}
	};

	/**
	 * <p>
	 * 603.7d If a spell creates a delayed triggered ability, the source of that
	 * delayed triggered ability is that spell. The controller of that delayed
	 * triggered ability is the player who controlled that spell as it resolved.
	 * </p>
	 * <p>
	 * 603.7e If an activated or triggered ability creates a delayed triggered
	 * ability, the source of that delayed triggered ability is the same as the
	 * source of that other ability. The controller of that delayed triggered
	 * ability is the player who controlled that other ability as it resolved.
	 * </p>
	 * <p>
	 * 603.7f If a static ability generates a replacement effect which causes a
	 * delayed triggered ability to be created, the source of that delayed
	 * triggered ability is the object with that static ability. The controller
	 * of that delayed triggered ability is the same as the controller of that
	 * object at the time the replacement effect was applied.
	 * </p>
	 * 
	 * @eparam CAUSE: the object creating the trigger, as defined above, EXCEPT
	 * that if an activated or triggered ability is creating this delayed
	 * trigger, this parameter should be that ability rather than that ability's
	 * source (so that this event can properly set the controller of the delayed
	 * trigger). Long Story Short: You're usually passing "This.instance()"
	 * here, but if you're a replacement effect it's possible you'll need to be
	 * more careful.
	 * @eparam EVENT: event patterns that describe what the new trigger will
	 * trigger on [can't be specified with ZONE_CHANGE or DAMAGE]
	 * @eparam DAMAGE: damage patterns that describe what the new trigger will
	 * trigger on [can't be specified with EVENT or ZONE_CHANGE]
	 * @eparam ZONE_CHANGE: ZoneChangePattern instances that describe what the
	 * new trigger will trigger on [can't be specified with EVENT or DAMAGE]
	 * @eparam EFFECT: an EventFactory to construct the trigger's effect
	 * (remember that any use of This will refer to the created ability, not to
	 * the creating {@link GameObject})
	 * @eparam EXPIRES: when the delayed trigger should expire [optional;
	 * default is until the end of the game or when it triggers once, whichever
	 * is shorter; requires double-generator idiom]
	 * @eparam COST: a CostCollection that PLAYER can pay to stop the delayed
	 * trigger from triggering [optional; requires PLAYER]
	 * @eparam PLAYER: who can stop the delayed trigger from triggering by
	 * paying COST [optional; requires COST]
	 * @eparam RESULT: the trigger
	 */
	public static final EventType CREATE_DELAYED_TRIGGER = new EventType("CREATE_DELAYED_TRIGGER")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.EFFECT; // awkward...
		}

		@Override
		public boolean perform(final Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			final GameObject causingObject = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
			Set events = parameters.get(Parameter.EVENT);
			Set zoneChanges = parameters.get(Parameter.ZONE_CHANGE);
			Set damagePatterns = parameters.get(Parameter.DAMAGE);
			EventFactory effect = parameters.get(Parameter.EFFECT).getOne(EventFactory.class);

			SetGenerator duration = (parameters.containsKey(Parameter.EXPIRES) ? parameters.get(Parameter.EXPIRES).getOne(SetGenerator.class) : null);
			final DelayedTrigger trigger;
			if(parameters.containsKey(Parameter.EVENT))
			{
				trigger = new DelayedTrigger(game.physicalState, effect.name, causingObject, events.getAll(EventPattern.class), java.util.Collections.<DamagePattern>emptySet(), java.util.Collections.<ZoneChangePattern>emptySet(), effect, duration);
				game.physicalState.delayedTriggers.add(trigger);
			}
			else if(parameters.containsKey(Parameter.ZONE_CHANGE))
			{
				trigger = new DelayedTrigger(game.physicalState, effect.name, causingObject, java.util.Collections.<EventPattern>emptySet(), java.util.Collections.<DamagePattern>emptySet(), zoneChanges.getAll(ZoneChangePattern.class), effect, duration);
				game.physicalState.delayedTriggers.add(trigger);
			}
			else if(parameters.containsKey(Parameter.DAMAGE))
			{
				trigger = new DelayedTrigger(game.physicalState, effect.name, causingObject, java.util.Collections.<EventPattern>emptySet(), damagePatterns.getAll(DamagePattern.class), java.util.Collections.<ZoneChangePattern>emptySet(), effect, duration);
				game.physicalState.delayedTriggers.add(trigger);
			}
			else
				throw new UnsupportedOperationException("CREATE_DELAYED_TRIGGER must specify either EVENT or ZONE_CHANGE parameter");

			if(parameters.containsKey(Parameter.COST))
			{
				// In order to keep an Identified reference from being held
				// through future game states, get the ID of this trigger for
				// use in the non-static anonymous classes that follow. Same
				// with the name of the causing object.
				final int triggerID = trigger.ID;
				final String objectName = causingObject.getName();

				final CostCollection cost = parameters.get(Parameter.COST).getOne(CostCollection.class);
				SpecialActionFactory stopFactory = new SpecialActionFactory()
				{
					@Override
					public java.util.Set<PlayerAction> getActions(GameState state, GameObject source, Player actor)
					{
						return java.util.Collections.<PlayerAction>singleton(new StopDelayedTriggerAction(game, "Stop " + objectName + "'s delayed trigger from triggering", cost, triggerID, actor));
					}
				};

				Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

				ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SPECIAL_ACTION);
				part.parameters.put(ContinuousEffectType.Parameter.ACTION, Identity.instance(stopFactory));
				part.parameters.put(ContinuousEffectType.Parameter.PLAYER, Identity.instance(player));

				// This effect expires (that is, PLAYER can't stop this trigger
				// from triggering) when the trigger either triggers or is
				// stopped. That is, the effect expires when the trigger isn't
				// in the current state's delayed trigger list.
				SetGenerator expires = new SetGenerator()
				{
					@Override
					public Set evaluate(GameState state, Identified thisObject)
					{
						for(DelayedTrigger t: state.delayedTriggers)
							// if the trigger is in the list, the effect doesn't
							// expire yet
							if(t.ID == triggerID)
								return Empty.set;
						return NonEmpty.set;
					}
				};

				java.util.Map<Parameter, Set> stopParameters = new java.util.HashMap<Parameter, Set>();
				stopParameters.put(Parameter.CAUSE, new Set(trigger));
				stopParameters.put(Parameter.EFFECT, new Set(part));
				stopParameters.put(Parameter.EXPIRES, new Set(expires));
				Event allowStoppage = createEvent(game, "Allow " + player + " to stop '" + trigger + "'", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, stopParameters);
				allowStoppage.perform(event, false);
			}

			event.setResult(Identity.instance(trigger));
			return true;
		}
	};

	/**
	 * @eparam CAUSE: what is creating the emblem
	 * @eparam ABILITY: any abilities the emblem should have [optional; default
	 * is none]
	 * @eparam CONTROLLER: who will control the emblem
	 * @eparam RESULT: the emblem as it exists in the battlefield
	 */
	public static final EventType CREATE_EMBLEM = new EventType("CREATE_EMBLEM")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// 113.2. An effect that creates an emblem is written
			// "[Player] gets an emblem with [ability]." This means that
			// [player] puts an emblem with [ability] into the command zone. The
			// emblem is both owned and controlled by that player.
			int owner = parameters.get(Parameter.CONTROLLER).getOne(Player.class).ID;

			java.util.Set<Class<? extends Keyword>> keywords = null;
			java.util.Set<Class<? extends NonStaticAbility>> nonStaticAbilities = null;
			java.util.Set<Class<? extends StaticAbility>> staticAbilities = null;

			if(parameters.containsKey(Parameter.ABILITY))
			{
				Set abilities = parameters.get(Parameter.ABILITY);
				keywords = abilities.getAllClasses(Keyword.class);
				nonStaticAbilities = abilities.getAllClasses(NonStaticAbility.class);
				staticAbilities = abilities.getAllClasses(StaticAbility.class);
			}

			Emblem e = new Emblem(game.physicalState);

			Class<?>[] constructorTypes = new Class<?>[] {GameState.class};
			Object[] constructorArguments = new Object[] {game.physicalState};
			if(null != keywords)
				for(Class<? extends Keyword> c: keywords)
					e.addAbility(org.rnd.util.Constructor.construct(c, constructorTypes, constructorArguments));
			if(null != nonStaticAbilities)
				for(Class<? extends NonStaticAbility> c: nonStaticAbilities)
					e.addAbility(org.rnd.util.Constructor.construct(c, constructorTypes, constructorArguments));
			if(null != staticAbilities)
				for(Class<? extends StaticAbility> c: staticAbilities)
					e.addAbility(org.rnd.util.Constructor.construct(c, constructorTypes, constructorArguments));

			e.ownerID = e.controllerID = owner;
			game.physicalState.commandZone().addToTop(e);

			event.setResult(Identity.instance(e));
			return true;
		}
	};

	/**
	 * @eparam CAUSE: what is creating the continuous effect(s)
	 * @eparam EFFECT: optionally, any parts of a continuous effect to use to
	 * create a new floating continuous effect (note that using the PREVENT
	 * parameter will automatically add a part)
	 * @eparam EXPIRES: optionally, when the floating continuous effect will
	 * expire; requires double-generator idiom (default is until end of turn)
	 * @eparam PREVENT: optionally, an amount of damage and a
	 * {@link SetGenerator} of {@link GameObject} and/or {@link Player} to
	 * prevent damage to; requires double-generator idiom (default is none)
	 * @eparam USES: optionally, the number of uses the floating continuous
	 * effect is limited to (default is unlimited)
	 * @eparam DAMAGE: optionally, if EFFECT is specified and the effect
	 * prevents or redirects damage, how much damage this effect can prevent or
	 * redirect
	 * @eparam RESULT: all floating continuous effects in EFFECT
	 */
	public static final EventType CREATE_FLOATING_CONTINUOUS_EFFECT = new EventType("CREATE_FLOATING_CONTINUOUS_EFFECT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.EFFECT; // awkward...
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject cause = parameters.get(Parameter.CAUSE).getOne(GameObject.class);

			java.util.Set<FloatingContinuousEffect> physicalEffects = new java.util.HashSet<FloatingContinuousEffect>();

			FloatingContinuousEffect newEffect = new FloatingContinuousEffect(game, event.getName());

			// keep Identity happy
			game.actualState.put(newEffect);

			if(parameters.containsKey(Parameter.EFFECT))
				for(ContinuousEffect.Part p: parameters.get(Parameter.EFFECT).getAll(ContinuousEffect.Part.class))
					newEffect.parts.add(p.clone());
			if(parameters.containsKey(Parameter.EXPIRES))
			{
				newEffect.expires = parameters.get(Parameter.EXPIRES).getOne(SetGenerator.class);
				if(newEffect.expires == null)
					throw new IllegalStateException("EXPIRES parameter of '" + event + "' didn't contain a SetGenerator.");
			}
			if(parameters.containsKey(Parameter.PREVENT))
			{
				Set prevent = parameters.get(Parameter.PREVENT);
				SetGenerator whoTo = prevent.getOne(SetGenerator.class);
				if(whoTo == null)
					throw new IllegalStateException("PREVENT parameter of '" + event + "' didn't contain a SetGenerator.");
				newEffect.addDamagePreventionShield(whoTo, prevent.getOne(Integer.class));
			}
			if(parameters.containsKey(Parameter.USES))
			{
				Integer number = Sum.get(parameters.get(Parameter.USES));
				newEffect.uses = number;
			}
			if(parameters.containsKey(Parameter.DAMAGE))
			{
				Integer number = Sum.get(parameters.get(Parameter.DAMAGE));
				newEffect.damage = number;
			}
			if(0 < newEffect.parts.size())
				physicalEffects.add(newEffect);

			for(FloatingContinuousEffect effect: physicalEffects)
			{
				for(ContinuousEffect.Part part: effect.parts)
				{
					// 611.2c ... A continuous effect generated by the
					// resolution of a spell or ability that doesn't modify the
					// characteristics or change the controller of any objects
					// modifies the rules of the game, so it can affect objects
					// that weren't affected when that continuous effect began.
					if(part.type.layer() == ContinuousEffectType.Layer.RULE_CHANGE)
						continue;

					for(java.util.Map.Entry<ContinuousEffectType.Parameter, SetGenerator> parameter: part.parameters.entrySet())
					{
						ContinuousEffectType.Parameter parameterName = parameter.getKey();
						Set evaluation = parameter.getValue().evaluate(game, cause);

						SetGenerator newParameter = Identity.instance(evaluation);
						part.parameters.put(parameterName, newParameter);
					}
				}
				effect.sourceEvent = event;

				// 613.6b A continuous effect generated by the resolution of a
				// spell or ability receives a timestamp at the time it's
				// created.
				effect.timestamp = game.physicalState.getNextAvailableTimestamp();
				effect.turnCreated = game.physicalState.currentTurn();
				game.physicalState.floatingEffects.add(effect);
			}
			event.setResult(Identity.instance(physicalEffects));
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what's creating the shield
	 * @eparam OBJECT: the objects being shielded
	 * @eparam RESULT: the regeneration shields
	 */
	public static final EventType CREATE_REGENERATION_SHIELD = new EventType("CREATE_REGENERATION_SHIELD")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				SetGenerator thisObject = IdentifiedWithID.instance(object.ID);

				SimpleEventPattern destroyThis = new SimpleEventPattern(EventType.DESTROY_ONE_PERMANENT);
				destroyThis.put(Parameter.PERMANENT, thisObject);

				EventFactory factory = new EventFactory(EventType.REGENERATE, ("Regenerate " + object.getName()));
				factory.parameters.put(Parameter.CAUSE, Identity.instance(parameters.get(Parameter.CAUSE)));
				factory.parameters.put(Parameter.OBJECT, thisObject);

				EventReplacementEffect regenerate = new EventReplacementEffect(game, "Regenerate " + object.getName(), destroyThis);
				regenerate.addEffect(factory);

				ContinuousEffect.Part part = replacementEffectPart(regenerate);

				java.util.Map<Parameter, Set> FCEparameters = new java.util.HashMap<Parameter, Set>();
				FCEparameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				FCEparameters.put(Parameter.EFFECT, new Set(part));
				FCEparameters.put(Parameter.USES, ONE);
				Event createShield = createEvent(game, "Create regeneration shields", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, FCEparameters);
				createShield.perform(event, false);
				result.addAll(createShield.getResult());
			}

			event.setResult(result);
			return true;
		}
	};
	/**
	 * @eparam NUMBER: the number of tokens to instantiate
	 * @eparam NAME: the name of the tokens
	 * @eparam ABILITY: the abilities
	 * @eparam CONTROLLER: the controller of the tokens (not used, but important
	 * to be set for effects like Doubling Season to see it)
	 * @eparam RESULT: the tokens
	 */
	public static final EventType CREATE_TOKEN = new EventType("CREATE_TOKEN")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set ret = new Set();
			Set abilities = parameters.get(Parameter.ABILITY);
			String name = parameters.get(Parameter.NAME).getOne(String.class);
			int number = Sum.get(parameters.get(Parameter.NUMBER));
			for(int i = 0; i < number; ++i)
			{
				Token token = new Token(game.physicalState, abilities, name);
				ret.add(token);
			}
			event.setResult(ret);
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is creating the tokens
	 * @eparam ABILITY: any abilities the token should have [optional; default
	 * is none]
	 * @eparam COLOR: any colors the token should have [optional; default is
	 * none]
	 * @eparam CONTROLLER: who will control the tokens
	 * @eparam NAME: the name of the token [optional; default is to construct
	 * the name according to rule 110.5c]
	 * @eparam NUMBER: the number of times to create these tokens [optional;
	 * default is once]
	 * @eparam POWER: the power of the token, if it's a creature [required if
	 * CREATURE is part of TYPE; forbidden otherwise]
	 * @eparam SUBTYPE: the sub-types of the token (this must evaluate to a
	 * java.util.List<SubType> rather than the SubType instances themselves so
	 * order is maintained) [required if NAME is not specified; otherwise,
	 * optional; default is none]
	 * @eparam SUPERTYPE: the super-types of the token [optional; default is
	 * none]
	 * @eparam TOUGHNESS: the toughness of the token, if it's a creature
	 * [required if CREATURE is part of TYPE; forbidden otherwise]
	 * @eparam TYPE: the types of the token (if CREATURE is included, POWER and
	 * TOUGHNESS are required)
	 * @eparam EVENT: the EventType by which to put the token on the battlefield
	 * [optional; default is PUT_ONTO_BATTLEFIELD. PUT_ONTO_BATTIEFIELD_TAPPED
	 * and PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING are also acceptable]
	 * @eparam ATTACKER: if EVENT is PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING,
	 * the attackingID to pass to that event [optional, default is none;
	 * prohibited when EVENT is something else]
	 * @eparam RESULT: the tokens as they exist on the battlefield
	 */
	public static final EventType CREATE_TOKEN_ON_BATTLEFIELD = new EventType("CREATE_TOKEN_ON_BATTLEFIELD")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set newObjects = new Set();

			// 110.5a A token is both owned and controlled by the player under
			// whose control it entered the battlefield.
			Player owner = parameters.get(Parameter.CONTROLLER).getOne(Player.class);

			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));

			java.util.Set<SuperType> superTypes = null;
			Integer power = null;
			Integer toughness = null;
			java.util.Set<Color> colors = null;
			java.util.List<SubType> subTypes = null;
			java.util.Set<Type> types = parameters.get(Parameter.TYPE).getAll(Type.class);
			Set abilities = null;
			String name = null;

			if(parameters.containsKey(Parameter.SUPERTYPE))
				superTypes = parameters.get(Parameter.SUPERTYPE).getAll(SuperType.class);

			if(parameters.containsKey(Parameter.COLOR))
				colors = parameters.get(Parameter.COLOR).getAll(Color.class);

			if(parameters.containsKey(Parameter.SUBTYPE))
			{
				subTypes = new java.util.LinkedList<SubType>();
				// Run all the sub-types through an Identity to make sure
				// text-change effects apply correctly
				// TODO: find a better way to make this work
				for(Object o: parameters.get(Parameter.SUBTYPE).getOne(java.util.List.class))
					subTypes.add(Identity.instance(o).evaluate(game, event.getSource()).getOne(SubType.class));
			}

			if(types.contains(Type.CREATURE))
			{
				power = Sum.get(parameters.get(Parameter.POWER));
				toughness = Sum.get(parameters.get(Parameter.TOUGHNESS));
			}
			else if(parameters.containsKey(Parameter.POWER))
				throw new UnsupportedOperationException("CREATE_TOKEN can only take POWER if CREATURE is part of TYPE");
			else if(parameters.containsKey(Parameter.TOUGHNESS))
				throw new UnsupportedOperationException("CREATE_TOKEN can only take TOUGHNESS if CREATURE is part of TYPE");

			if(parameters.containsKey(Parameter.ABILITY))
				abilities = parameters.get(Parameter.ABILITY);
			else
				abilities = new Set();

			if(parameters.containsKey(Parameter.NAME))
				name = parameters.get(Parameter.NAME).getOne(String.class);
			else
			{
				// 110.5c A spell or ability that creates a creature token sets
				// both its name and its creature type. If the spell or ability
				// doesn't specify the name of the creature token, its name is
				// the same as its creature type(s). A
				// "Goblin Scout creature token," for example, is named
				// "Goblin Scout" and has the creature subtypes Goblin and
				// Scout. Once a token is on the battlefield, changing its name
				// doesn't change its creature type, and vice versa.
				if(null == subTypes)
					throw new UnsupportedOperationException("CREATE_TOKEN must take SUBTYPE if NAME is not specified");

				StringBuilder nameBuilder = new StringBuilder();
				boolean firstSubType = true;
				for(SubType subType: subTypes)
				{
					if(!firstSubType)
						nameBuilder.append(" ");

					// Assembly_Worker should be Assembly-Worker
					boolean firstPart = true;
					for(String subTypePart: subType.name().split("_"))
					{
						if(!firstPart)
							nameBuilder.append("-");
						nameBuilder.append(subTypePart.substring(0, 1).toUpperCase());
						nameBuilder.append(subTypePart.substring(1).toLowerCase());
						firstPart = false;
					}

					firstSubType = false;
				}
				name = nameBuilder.toString();
			}

			java.util.Map<Parameter, Set> tokenParameters = new java.util.HashMap<Parameter, Set>();
			tokenParameters.put(EventType.Parameter.ABILITY, abilities);
			tokenParameters.put(EventType.Parameter.NAME, new Set(name));
			tokenParameters.put(EventType.Parameter.NUMBER, new Set(number));
			tokenParameters.put(EventType.Parameter.CONTROLLER, new Set(owner));
			Event createTokens = createEvent(game, "", CREATE_TOKEN, tokenParameters);

			if(createTokens.perform(event, false))
				for(Token t: createTokens.getResultGenerator().evaluate(game.physicalState, null).getAll(Token.class))
				{
					if(null != superTypes)
						t.addSuperTypes(superTypes);
					if((null != power) && (null != toughness))
					{
						t.setPower(power);
						t.setToughness(toughness);
					}
					if(null != subTypes)
						t.addSubTypes(subTypes);
					t.addTypes(types);
					if(null != colors)
						t.getColors().addAll(colors);

					t.ownerID = owner.ID;
					game.physicalState.exileZone().addToTop(t);
					newObjects.add(t);
				}

			// Put all tokens and their abilities into the actual state for
			// triggers/effects
			game.refreshActualState();

			java.util.Map<Parameter, Set> moveParameters = tokenParameters;
			moveParameters.put(Parameter.OBJECT, newObjects);
			moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			moveParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));

			EventType moveType = PUT_ONTO_BATTLEFIELD;
			if(parameters.containsKey(Parameter.EVENT))
				moveType = parameters.get(Parameter.EVENT).getOne(EventType.class);
			if(moveType == PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING && parameters.containsKey(Parameter.ATTACKER))
				moveParameters.put(Parameter.ATTACKER, parameters.get(Parameter.ATTACKER));

			Event moveTokens = createEvent(game, "Put " + newObjects + " onto the battlefield.", moveType, moveParameters);
			// 110.5d If a spell or ability would create a token, but an effect
			// states that a permanent with one or more of that token's
			// characteristics can't enter the battlefield, the token is not
			// created.
			// Handled by design; if we create a token and it doesn't enter the
			// battlefield due to a prohibition, it'll remain in the exile zone;
			// state-based actions will clean it up.

			boolean ret = moveTokens.perform(event, true);
			event.setResult(NewObjectOf.instance(moveTokens.getResultGenerator()).evaluate(game, null));
			return ret;
		}
	};
	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam ATTACKER: the attacker to block
	 * @eparam ABILITY: any abilities the token should have [optional; default
	 * is none]
	 * @eparam COLOR: any colors the token should have [optional; default is
	 * none]
	 * @eparam CONTROLLER: who will control the tokens
	 * @eparam NAME: the name of the token [optional; default is to construct
	 * the name according to rule 110.5c]
	 * @eparam NUMBER: the number of times to create these tokens [optional;
	 * default is once]
	 * @eparam POWER: the power of the token, if it's a creature [required if
	 * CREATURE is part of TYPE; forbidden otherwise]
	 * @eparam SUBTYPE: the sub-types of the token (this must evaluate to a
	 * java.util.List<SubType> rather than the SubType instances themselves so
	 * order is maintained) [required if NAME is not specified; otherwise,
	 * optional; default is none]
	 * @eparam SUPERTYPE: the super-types of the token [optional; default is
	 * none]
	 * @eparam TOUGHNESS: the toughness of the token, if it's a creature
	 * [required if CREATURE is part of TYPE; forbidden otherwise]
	 * @eparam TYPE: the types of the token (if CREATURE is included, POWER and
	 * TOUGHNESS are required)
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType CREATE_TOKEN_BLOCKING = new EventType("CREATE_TOKEN_BLOCKING")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Event putOntoBattlefield = createEvent(game, "Put a token onto the battlefield.", CREATE_TOKEN_ON_BATTLEFIELD, parameters);
			boolean status = putOntoBattlefield.perform(event, true);

			java.util.Set<GameObject> attackers = parameters.get(Parameter.ATTACKER).getAll(GameObject.class);

			for(GameObject blocker: putOntoBattlefield.getResult().getAll(GameObject.class))
				if(blocker.zoneID == game.actualState.battlefield().ID)
				{
					GameObject physicalBlocker = blocker.getPhysical();
					for(GameObject attacker: attackers)
					{
						GameObject physicalAttacker = attacker.getPhysical();
						if(physicalAttacker.getBlockedByIDs() == null)
							physicalAttacker.setBlockedByIDs(new java.util.LinkedList<Integer>());
						physicalAttacker.getBlockedByIDs().add(blocker.ID);
						physicalBlocker.getBlockingIDs().add(attacker.ID);
					}
				}

			event.setResult(putOntoBattlefield.getResultGenerator());
			return status;
		}
	};
	/**
	 * @eparam CAUSE: what is creating the tokens
	 * @eparam CONTROLLER: who will control the tokens
	 * @eparam NUMBER: the number of times to create these tokens [optional;
	 * default is once]
	 * @eparam OBJECT: the object to make token copies of (singular, if this
	 * parameter is empty then no tokens will be made and the event will fail)
	 * @eparam TYPE: Any {@link Type}s, {@link SuperType}s, or {@link SubType}s
	 * to add as part of the copying process
	 * @eparam RESULT: the zone changes
	 */
	public static final EventType CREATE_TOKEN_COPY = new EventType("CREATE_TOKEN_COPY")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject original = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			if(original == null)
			{
				event.setResult(Empty.set);
				return false;
			}

			Set tokenCopies = new Set();

			// 110.5a A token is both owned and controlled by the player
			// under whose control it entered the battlefield.
			Player owner = parameters.get(Parameter.CONTROLLER).getOne(Player.class);

			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));

			java.util.Map<Parameter, Set> tokenParameters = new java.util.HashMap<Parameter, Set>();
			tokenParameters.put(EventType.Parameter.ABILITY, new Set());
			tokenParameters.put(EventType.Parameter.NAME, new Set(""));
			tokenParameters.put(EventType.Parameter.NUMBER, new Set(number));
			tokenParameters.put(EventType.Parameter.CONTROLLER, new Set(owner));
			Event createTokens = createEvent(game, "", CREATE_TOKEN, tokenParameters);

			if(createTokens.perform(event, false))
				for(Token copy: createTokens.getResultGenerator().evaluate(game.physicalState, null).getAll(Token.class))
				{
					copy.ownerID = owner.ID;
					game.physicalState.exileZone().addToTop(copy);
					tokenCopies.add(copy);

					// to keep Identity happy
					game.actualState.put(copy);
				}

			Set result = new Set();
			boolean status = true;

			for(GameObject tokenCopy: tokenCopies.getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> putAsCopyParameters = new java.util.HashMap<Parameter, Set>();
				putAsCopyParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				putAsCopyParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));
				putAsCopyParameters.put(Parameter.OBJECT, new Set(tokenCopy));
				putAsCopyParameters.put(Parameter.SOURCE, parameters.get(Parameter.OBJECT));
				if(parameters.containsKey(Parameter.TYPE))
					putAsCopyParameters.put(Parameter.TYPE, parameters.get(Parameter.TYPE));
				putAsCopyParameters.put(Parameter.TO, new Set(game.actualState.battlefield()));
				Event putAsCopy = createEvent(game, "Put " + (number == 1 ? "a token" : number + " tokens") + " onto the battlefield copying " + original + ".", EventType.PUT_INTO_ZONE_AS_A_COPY_OF, putAsCopyParameters);
				if(!putAsCopy.perform(event, false))
					status = false;

				result.addAll(putAsCopy.getResult());
			}

			event.setResult(result);
			return status;
		}
	};
	/**
	 * @eparam TARGET: DamageAssignment objects
	 * @eparam RESULT: empty
	 */
	public static final EventType DEAL_COMBAT_DAMAGE = new EventType("DEAL_COMBAT_DAMAGE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.TARGET;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.addDamage(parameters.get(Parameter.TARGET).getAll(DamageAssignment.class));

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * This event ACTUALLY DEALS DAMAGE. This event should only be created or
	 * performed in Event.perform().
	 * 
	 * @eparam TARGET: DamageAssignments
	 * @eparam RESULT: the damage assignments of the damage actually dealt
	 */
	public static final EventType DEAL_DAMAGE_BATCHES = new EventType("DEAL_DAMAGE_BATCHES")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.TARGET;
		}

		private boolean fakeAbilityForDamage(DamageAssignment.Batch damage, Class<? extends Keyword> ability, GameState state)
		{
			for(java.util.Map.Entry<Integer, ContinuousEffectType.DamageAbility> entry: state.dealDamageAsThoughHasAbility.entrySet())
				if(!entry.getValue().dp.match(damage, state.get(entry.getKey()), state).isEmpty())
					if(entry.getValue().k.isAssignableFrom(ability))
						return true;
			return false;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// the structure of these maps is:
			// map<player, map<source, amount>>
			java.util.Map<Player, java.util.Map<GameObject, Integer>> lifeLosses = new java.util.HashMap<Player, java.util.Map<GameObject, Integer>>();
			java.util.Map<Player, java.util.Map<GameObject, Integer>> lifeGains = new java.util.HashMap<Player, java.util.Map<GameObject, Integer>>();
			// the maps are split up in this manner because of this rule:
			// 118.9. Some triggered abilities are written, "Whenever [a player]
			// gains life, . . . ." Such abilities are treated as though they
			// are written, "Whenever a source causes [a player] to gain life, .
			// . . ."

			java.util.Map<GameObject, Integer> creaturesGettingCounters = new java.util.HashMap<GameObject, Integer>();
			java.util.Map<Player, Integer> playersGettingPoisonCounters = new java.util.HashMap<Player, Integer>();
			java.util.Map<GameObject, Integer> planeswalkersLosingCounters = new java.util.HashMap<GameObject, Integer>();

			java.util.Set<DamageAssignment> assignments = parameters.get(Parameter.TARGET).getAll(DamageAssignment.class);
			for(DamageAssignment assignment: assignments)
			{
				// for checking as-though effects:
				DamageAssignment.Batch batch = new DamageAssignment.Batch();
				batch.add(assignment);

				GameObject source = game.actualState.get(assignment.sourceID);
				Identified taker = game.actualState.get(assignment.takerID);

				boolean lifelink = source.hasAbility(org.rnd.jmagic.abilities.keywords.Lifelink.class);
				if(!lifelink)
					lifelink = fakeAbilityForDamage(batch, org.rnd.jmagic.abilities.keywords.Lifelink.class, game.actualState);
				if(lifelink)
				{
					Player controller = source.getController(source.state);
					if(lifeGains.containsKey(controller))
					{
						java.util.Map<GameObject, Integer> lifeGain = lifeGains.get(controller);
						if(lifeGain.containsKey(source))
							lifeGain.put(source, lifeGain.get(source) + 1);
						else
							lifeGain.put(source, 1);
					}
					else
					{
						java.util.Map<GameObject, Integer> lifeGain = new java.util.HashMap<GameObject, Integer>();
						lifeGain.put(source, 1);
						lifeGains.put(controller, lifeGain);
					}
				}

				boolean infect = source.hasAbility(org.rnd.jmagic.abilities.keywords.Infect.class);
				if(!infect)
					infect = fakeAbilityForDamage(batch, org.rnd.jmagic.abilities.keywords.Infect.class, game.actualState);
				if(taker.isPlayer())
				{
					Player losingLife = (Player)taker;
					if(infect)
					{
						if(!playersGettingPoisonCounters.containsKey(losingLife))
							playersGettingPoisonCounters.put(losingLife, 1);
						else
							// can't ++ an Integer
							playersGettingPoisonCounters.put(losingLife, playersGettingPoisonCounters.get(losingLife) + 1);
					}
					else
					{
						if(lifeLosses.containsKey(losingLife))
						{
							java.util.Map<GameObject, Integer> lifeLoss = lifeLosses.get(losingLife);
							if(lifeLoss.containsKey(source))
								lifeLoss.put(source, lifeLoss.get(source) + 1);
							else
								lifeLoss.put(source, 1);
						}
						else
						{
							java.util.Map<GameObject, Integer> lifeLoss = new java.util.HashMap<GameObject, Integer>();
							lifeLoss.put(source, 1);
							lifeLosses.put(losingLife, lifeLoss);
						}
					}

					continue;
				}

				// if it's not an object, we'll get a class-cast exception here
				GameObject takerObject = (GameObject)taker;
				if(takerObject.getTypes().contains(Type.CREATURE))
				{
					// If the source has wither/infect add -1/-1 counters,
					// otherwise, increment damage
					boolean wither = source.hasAbility(org.rnd.jmagic.abilities.keywords.Wither.class);
					if(!wither)
						wither = fakeAbilityForDamage(batch, org.rnd.jmagic.abilities.keywords.Wither.class, game.actualState);
					if(wither || infect)
					{
						if(!creaturesGettingCounters.containsKey(takerObject))
							creaturesGettingCounters.put(takerObject, 1);
						else
							// can't ++ an Integer
							creaturesGettingCounters.put(takerObject, creaturesGettingCounters.get(takerObject) + 1);
					}
					else
					{
						// Mark any creature damaged by deathtouch so SBAs can
						// destroy them
						GameObject physical = takerObject.getPhysical();
						if(source.hasAbility(org.rnd.jmagic.abilities.keywords.Deathtouch.class))
							physical.setDamagedByDeathtouchSinceLastSBA(true);
						physical.setDamage(physical.getDamage() + 1);
					}
				}
				if(takerObject.getTypes().contains(Type.PLANESWALKER))
				{
					if(!planeswalkersLosingCounters.containsKey(takerObject))
						planeswalkersLosingCounters.put(takerObject, 1);
					else
						// can't ++ an Integer
						planeswalkersLosingCounters.put(takerObject, planeswalkersLosingCounters.get(takerObject) + 1);
				}
			}

			for(java.util.Map.Entry<Player, Integer> playerPoisonCounter: playersGettingPoisonCounters.entrySet())
			{
				Player player = playerPoisonCounter.getKey();
				int number = playerPoisonCounter.getValue();

				java.util.Map<Parameter, Set> witherParameters = new java.util.HashMap<Parameter, Set>();
				witherParameters.put(Parameter.PLAYER, new Set(player));
				witherParameters.put(Parameter.NUMBER, new Set(number));
				createEvent(game, "Put " + number + " poison counter" + (number == 1 ? "" : "s") + " on " + player + ".", ADD_POISON_COUNTERS, witherParameters).perform(event, false);
			}

			for(java.util.Map.Entry<Player, java.util.Map<GameObject, Integer>> playerLifeGain: lifeGains.entrySet())
			{
				Player player = playerLifeGain.getKey();
				for(java.util.Map.Entry<GameObject, Integer> lifeGain: playerLifeGain.getValue().entrySet())
				{
					java.util.Map<Parameter, Set> gainLifeParameters = new java.util.HashMap<Parameter, Set>();
					gainLifeParameters.put(Parameter.CAUSE, new Set(lifeGain.getKey()));
					gainLifeParameters.put(Parameter.PLAYER, new Set(player));
					gainLifeParameters.put(Parameter.NUMBER, new Set(lifeGain.getValue()));
					createEvent(game, player + " gains " + lifeGain.getValue() + " life.", GAIN_LIFE, gainLifeParameters).perform(event, false);
				}
			}

			for(java.util.Map.Entry<Player, java.util.Map<GameObject, Integer>> playerLifeLoss: lifeLosses.entrySet())
			{
				Player player = playerLifeLoss.getKey();
				for(java.util.Map.Entry<GameObject, Integer> lifeLoss: playerLifeLoss.getValue().entrySet())
				{
					java.util.Map<Parameter, Set> loseLifeParameters = new java.util.HashMap<Parameter, Set>();
					loseLifeParameters.put(Parameter.CAUSE, new Set(lifeLoss.getKey()));
					loseLifeParameters.put(Parameter.PLAYER, new Set(player));
					loseLifeParameters.put(Parameter.NUMBER, new Set(lifeLoss.getValue()));
					loseLifeParameters.put(Parameter.DAMAGE, Empty.set);
					createEvent(game, player + " loses " + lifeLoss.getValue() + " life.", LOSE_LIFE, loseLifeParameters).perform(event, false);
				}
			}

			// same for creatures and -1/-1 counters
			for(java.util.Map.Entry<GameObject, Integer> witherCounter: creaturesGettingCounters.entrySet())
			{
				GameObject taker = witherCounter.getKey();
				int number = witherCounter.getValue();

				java.util.Map<Parameter, Set> witherParameters = new java.util.HashMap<Parameter, Set>();
				witherParameters.put(Parameter.CAUSE, new Set(game));
				witherParameters.put(Parameter.COUNTER, new Set(Counter.CounterType.MINUS_ONE_MINUS_ONE));
				witherParameters.put(Parameter.NUMBER, new Set(number));
				witherParameters.put(Parameter.OBJECT, new Set(taker));
				createEvent(game, "Put " + number + " -1/-1 counter" + (number == 1 ? "" : "s") + " on " + taker + ".", PUT_COUNTERS, witherParameters).perform(event, false);
			}

			for(java.util.Map.Entry<GameObject, Integer> loyaltyCounter: planeswalkersLosingCounters.entrySet())
			{
				GameObject taker = loyaltyCounter.getKey();
				int number = planeswalkersLosingCounters.get(taker);

				java.util.Map<Parameter, Set> removeCounterParameters = new java.util.HashMap<Parameter, Set>();
				removeCounterParameters.put(Parameter.CAUSE, new Set(game));
				removeCounterParameters.put(Parameter.COUNTER, new Set(Counter.CounterType.LOYALTY));
				removeCounterParameters.put(Parameter.NUMBER, new Set(number));
				removeCounterParameters.put(Parameter.OBJECT, new Set(taker));

				createEvent(game, "Remove " + number + " loyalty counter" + (number == 1 ? "" : "s") + " from " + taker + ".", REMOVE_COUNTERS, removeCounterParameters).perform(event, false);
			}

			// If we get as far as this event type, all the damage here will be
			// dealt. No need to check for damage not being dealt, so we just
			// add the assignments directly to the result. -RulesGuru
			event.setResult(Identity.instance(assignments));

			return true;
		}
	};
	/**
	 * @eparam SOURCE: what is dealing damage
	 * @eparam NUMBER: how much damage
	 * @eparam TAKER: what is being damaged
	 * @eparam PREVENT: [optional] if present, the damage is unpreventable.
	 * @eparam RESULT: empty
	 */
	public static final EventType DEAL_DAMAGE_EVENLY = new EventType("DEAL_DAMAGE_EVENLY")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.TAKER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject source = parameters.get(Parameter.SOURCE).getOne(GameObject.class);
			int damageAmount = Sum.get(parameters.get(Parameter.NUMBER));
			boolean unpreventable = parameters.containsKey(Parameter.PREVENT);
			java.util.Collection<Identified> takers = new java.util.LinkedList<Identified>();
			takers.addAll(parameters.get(Parameter.TAKER).getAll(Player.class));
			takers.addAll(parameters.get(Parameter.TAKER).getAll(GameObject.class));

			for(Identified taker: takers)
				for(int i = 0; i < damageAmount; i++)
					event.addDamage(source, taker, unpreventable);

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam SOURCE: what is dealing damage
	 * @eparam NUMBER: how much damage
	 * @eparam TAKER: what is being damaged
	 * @eparam PREVENT: [optional] if present, the damage is unpreventable
	 * @eparam RESULT: empty
	 */
	public static final EventType DEAL_DAMAGE_EVENLY_CANT_BE_REGENERATED = new EventType("DEAL_DAMAGE_EVENLY_CANT_BE_REGENERATED")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.TAKER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Event damageEvent = createEvent(game, event.getName(), EventType.DEAL_DAMAGE_EVENLY, parameters);
			boolean ret = damageEvent.perform(event, false);

			SetPattern affectedCreatures = new SimpleSetPattern(Intersect.instance(TakerOfDamage.instance(EventDamage.instance(Identity.instance(event))), CreaturePermanents.instance()));

			SimpleEventPattern regenerate = new SimpleEventPattern(EventType.REGENERATE);
			regenerate.put(EventType.Parameter.OBJECT, affectedCreatures);

			EventReplacementEffectStopper stopRegen = new EventReplacementEffectStopper(parameters.get(Parameter.SOURCE).getOne(GameObject.class), null, regenerate);
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.STOP_REPLACEMENT_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(stopRegen));

			java.util.Map<EventType.Parameter, Set> stopRegenParameters = new java.util.HashMap<EventType.Parameter, Set>();
			stopRegenParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.SOURCE));
			stopRegenParameters.put(EventType.Parameter.EFFECT, new Set(part));
			Event regenStopper = createEvent(game, "A creature dealt damage this way can't be regenerated this turn.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, stopRegenParameters);
			ret = regenStopper.perform(event, false) && ret;

			event.setResult(Empty.set);

			return ret;
		}
	};

	/**
	 * @eparam RESULT: the creatures declared as attacking
	 */
	public static final EventType DECLARE_ATTACKERS = new EventType("DECLARE_ATTACKERS")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player attacker = game.actualState.currentTurn().getOwner(game.actualState);

			// Keep the instance around because it calculates information needed
			// to determine legality in each perform
			DeclareAttackersAction declareAttackersAction = new DeclareAttackersAction(game, attacker, event);
			while(!declareAttackersAction.saveStateAndPerform())
				// Fix the actual state in case declaring attackers failed
				game.refreshActualState();

			Set attackers = new Set();
			for(int attackerID: declareAttackersAction.attackerIDs)
				attackers.add(game.actualState.getByIDObject(attackerID));
			event.setResult(Identity.instance(attackers));

			return true;
		}
	};
	/**
	 * @eparam RESULT: empty
	 */
	public static final EventType DECLARE_BLOCKERS = new EventType("DECLARE_BLOCKERS")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player activePlayer = game.actualState.currentTurn().getOwner(game.actualState);

			// 509.1. First, the defending player declares blockers. This
			// turn-based action doesn't use the stack.
			java.util.Set<Player> isAttacked = IsAttacked.get(game.actualState).getAll(Player.class);
			for(Player player: game.physicalState.getPlayerCycle(activePlayer))
			{
				if(!isAttacked.contains(player))
					continue;

				// Keep the instance around because it calculates information
				// needed to determine legality in each perform
				DeclareBlockersAction declareBlockersAction = new DeclareBlockersAction(game, player, event);
				while(!declareBlockersAction.saveStateAndPerform())
					// Fix the actual state in case declaring blockers failed
					game.refreshActualState();
			}

			activePlayer = activePlayer.getActual();

			// 509.2. Second, for each attacking creature that's become
			// blocked by multiple creatures, the active player announces
			// its damage assignment order among the blocking creatures.
			// This turn-based action doesn't use the stack.
			for(GameObject attacker: Attacking.get(game.actualState).getAll(GameObject.class))
			{
				if(attacker.getBlockedByIDs() != null && attacker.getBlockedByIDs().size() > 1)
				{
					java.util.List<GameObject> blockers = new java.util.LinkedList<GameObject>();
					for(int blockerID: attacker.getBlockedByIDs())
						blockers.add(game.actualState.<GameObject>get(blockerID));
					int numBlockers = blockers.size();
					PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(numBlockers, numBlockers, PlayerInterface.ChoiceType.OBJECTS_ORDERED, PlayerInterface.ChooseReason.DAMAGE_ASSIGNMENT_ORDER);
					chooseParameters.thisID = attacker.ID;
					blockers = activePlayer.sanitizeAndChoose(game.actualState, blockers, chooseParameters);
					attacker = attacker.getPhysical();
					for(int i = 0; i < numBlockers; i++)
						attacker.getBlockedByIDs().set(i, blockers.get(i).ID);
				}
			}

			// 509.3. Third, for each creature that's blocking multiple
			// creatures (because some effect allows it to), the defending
			// player announces its damage assignment order among the
			// attacking creatures. This turn-based action doesn't use the
			// stack.
			for(Player player: IsAttacked.get(game.actualState).getAll(Player.class))
			{
				for(GameObject blocker: Blocking.get(game.actualState).getAll(GameObject.class))
				{
					if(blocker.controllerID == player.ID && blocker.getBlockingIDs().size() > 1)
					{
						java.util.List<GameObject> attackers = new java.util.LinkedList<GameObject>();
						for(int attackerID: blocker.getBlockingIDs())
							attackers.add(game.actualState.<GameObject>get(attackerID));
						int numAttackers = attackers.size();
						PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(numAttackers, numAttackers, PlayerInterface.ChoiceType.OBJECTS_ORDERED, PlayerInterface.ChooseReason.DAMAGE_ASSIGNMENT_ORDER);
						chooseParameters.thisID = blocker.ID;
						attackers = player.sanitizeAndChoose(game.actualState, attackers, chooseParameters);
						blocker = blocker.getPhysical();
						for(int i = 0; i < numAttackers; i++)
							blocker.getBlockingIDs().set(i, attackers.get(i).ID);
					}
				}
			}

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam OBJECT: the attacking creature
	 * @eparam DEFENDER: the thing it's attacking
	 * @eparam RESULT: empty
	 */
	public static final EventType DECLARE_ONE_ATTACKER = new EventType("DECLARE_ONE_ATTACKER")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam OBJECT: the blocking creature
	 * @eparam ATTACKER: the things it's blocking
	 * @eparam RESULT: empty
	 */
	public static final EventType DECLARE_ONE_BLOCKER = new EventType("DECLARE_ONE_BLOCKER")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * @eparam CAUSE: what is destroying something
	 * @eparam PERMANENT: the permanent being destroyed
	 * @eparam RESULT: the result of the {@link EventType#MOVE_OBJECTS} event
	 */
	public static final EventType DESTROY_ONE_PERMANENT = new EventType("DESTROY_ONE_PERMANENT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PERMANENT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject permanent = parameters.get(Parameter.PERMANENT).getOne(GameObject.class);
			Player owner = permanent.getOwner(game.actualState);
			Zone graveyard = owner.getGraveyard(game.actualState);

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			moveParameters.put(Parameter.TO, new Set(graveyard));
			moveParameters.put(Parameter.OBJECT, new Set(permanent));

			// move the permanent to the graveyard
			Event move = createEvent(game, "Put " + permanent + " in " + owner + "'s graveyard.", MOVE_OBJECTS, moveParameters);
			boolean status = move.perform(event, false);

			event.setResult(move.getResultGenerator());
			return status;
		}
	};
	/**
	 * @eparam CAUSE: what is destroying things
	 * @eparam PERMANENT: what is being destroyed
	 * @eparam RESULT: the zone changes
	 */
	public static final EventType DESTROY_PERMANENTS = new EventType("DESTROY_PERMANENTS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PERMANENT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean allDestroyed = true;
			Set cause = parameters.get(Parameter.CAUSE);
			Set result = new Set();
			for(GameObject object: parameters.get(Parameter.PERMANENT).getAll(GameObject.class))
			{
				if(object.isPermanent())
				{
					java.util.Map<Parameter, Set> destroyParameters = new java.util.HashMap<Parameter, Set>();
					destroyParameters.put(Parameter.CAUSE, cause);
					destroyParameters.put(Parameter.PERMANENT, new Set(object));
					Event destroy = createEvent(game, "Destroy " + object + ".", EventType.DESTROY_ONE_PERMANENT, destroyParameters);
					if(!destroy.perform(event, false))
						allDestroyed = false;
					result.addAll(destroy.getResult());
				}
				else
					allDestroyed = false;
			}

			event.setResult(Identity.instance(result));
			return allDestroyed;
		}
	};
	/**
	 * 701.26. Detain
	 * 
	 * 701.26a Certain spells and abilities can detain a permanent. Until the
	 * next turn of the controller of that spell or ability, that permanent
	 * can't attack or block and its activated abilities can't be activated.
	 * 
	 * NOTE: Make sure to call
	 * GameState.ensureTracker(DetainGenerator.Tracker.class).
	 * 
	 * @eparam CAUSE: the spell or ability causing the detainment
	 * @eparam PERMANENT: the permanents to detain
	 * @eparam RESULT: empty
	 */
	public static final EventType DETAIN = new EventType("DETAIN")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CARD;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject cause = parameters.get(EventType.Parameter.CAUSE).getOne(GameObject.class);
			SetGenerator expires = Not.instance(Intersect.instance(Identity.instance(cause), DetainGenerator.instance()));

			for(GameObject object: parameters.get(EventType.Parameter.PERMANENT).getAll(GameObject.class))
			{
				Identity thatObject = Identity.instance(object);

				// can't attack
				ContinuousEffect.Part attack = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
				attack.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(thatObject, Attacking.instance())));

				// or block,
				ContinuousEffect.Part block = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
				block.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(thatObject, Blocking.instance())));

				// and its activated abilities can't be activated.
				SimpleEventPattern prohibitPattern = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
				prohibitPattern.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(thatObject));

				ContinuousEffect.Part prohibition = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
				prohibition.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern));

				java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
				lookParameters.put(Parameter.CAUSE, new Set(cause));
				lookParameters.put(Parameter.EFFECT, new Set(attack, block, prohibition));
				lookParameters.put(Parameter.EXPIRES, new Set(expires));
				createEvent(game, event.getName(), CREATE_FLOATING_CONTINUOUS_EFFECT, lookParameters).perform(event, false);
			}

			event.setResult(Identity.instance());
			return true;
		}
	};
	/**
	 * Causes a player to discard a specific set of cards from their hand. To
	 * cause a player to discard cards of their choice, use DISCARD_CHOICE. To
	 * have another player choose which cards the player discards, use
	 * DISCARD_FORCE.
	 * 
	 * @eparam CAUSE: what is causing the discard
	 * @eparam CARD: what is being discarded
	 * @eparam RESULT: the {@link ZoneChange}s that were generated
	 */
	public static final EventType DISCARD_CARDS = new EventType("DISCARD_CARDS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CARD;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.CARD).getAll(Card.class))
			{
				if(object == null)
					return false;

				Zone zone = object.getZone();
				if(zone == null || object.isGhost() || !zone.isHand())
					return false;
			}
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean allDiscarded = true;
			Set result = new Set();
			for(GameObject card: parameters.get(Parameter.CARD).getAll(Card.class))
			{
				java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
				discardParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				discardParameters.put(Parameter.CARD, new Set(card));
				Event discard = createEvent(game, card.getActual().getOwner(game.actualState) + " discards " + card + ".", DISCARD_ONE_CARD, discardParameters);
				if(!discard.perform(event, false))
					allDiscarded = false;
				result.addAll(discard.getResult());
			}

			event.setResult(Identity.instance(result));
			return allDiscarded;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the discard
	 * @eparam PLAYER: the players that are discarding (and thus choosing)
	 * @eparam NUMBER: number of cards to discard [optional; default is 1]
	 * @eparam CHOICE: set of cards to choose from [optional; default is all the
	 * cards in the players' hands] NOTE: If Parameter.CHOICE is not left as
	 * default, it's a Bad Thing(tm) to have multiple players specified here.
	 * @eparam RESULT: the zone change(s)
	 */
	public static final EventType DISCARD_CHOICE = new EventType("DISCARD_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CHOICE;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int successes = 0;
			Set cause = parameters.get(Parameter.CAUSE);
			int required = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				required = Sum.get(parameters.get(Parameter.NUMBER));

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				java.util.Set<Card> cards = null;
				if(parameters.containsKey(Parameter.CHOICE))
					cards = parameters.get(Parameter.CHOICE).getAll(Card.class);
				else
					cards = new Set(player.getHand(game.actualState).objects).getAll(Card.class);

				successes = 0;
				for(Card thisCard: cards)
				{
					java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
					newParameters.put(Parameter.CAUSE, cause);
					newParameters.put(Parameter.CARD, new Set(thisCard));
					if(createEvent(game, thisCard.getOwner(game.actualState) + " discards " + thisCard + ".", DISCARD_ONE_CARD, newParameters).attempt(event))
						successes++;

					if(successes == required)
						break;
				}
				if(successes != required)
					return false;
			}
			return true;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int numberOfCards = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				numberOfCards = Sum.get(parameters.get(Parameter.NUMBER));

			if(numberOfCards < 0)
				numberOfCards = 0;

			java.util.Set<Card> cardsInHand = null;
			boolean specificChoices = parameters.containsKey(Parameter.CHOICE);

			if(specificChoices)
				cardsInHand = parameters.get(Parameter.CHOICE).getAll(Card.class);

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				if(!specificChoices)
					cardsInHand = new Set(player.getHand(game.actualState).objects).getAll(Card.class);

				java.util.Collection<Card> choices = player.sanitizeAndChoose(game.actualState, numberOfCards, cardsInHand, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.DISCARD);
				if(choices.size() != numberOfCards)
					event.allChoicesMade = false;
				event.putChoices(player, choices);
			}
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean allDiscarded = event.allChoicesMade;
			Set cause = parameters.get(Parameter.CAUSE);
			Set result = new Set();

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				Set discardThese = event.getChoices(player);

				java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
				discardParameters.put(Parameter.CAUSE, cause);
				discardParameters.put(Parameter.CARD, discardThese);
				Event discard = createEvent(game, player + " discards " + discardThese + ".", DISCARD_CARDS, discardParameters);
				if(!discard.perform(event, false))
					allDiscarded = false;
				result.addAll(discard.getResult());
			}

			event.setResult(Identity.instance(result));
			return allDiscarded;
		}
	};
	/**
	 * This event causes PLAYER to look at all of the cards in TARGET's hand
	 * since it invokes a {@link EventType#SEARCH}.
	 * 
	 * @eparam CAUSE: what is causing the discard
	 * @eparam PLAYER: the player that is choosing (just one)
	 * @eparam TARGET: the player that is discarding (just one)
	 * @eparam NUMBER: number of cards to discard [optional; default is 1]
	 * @eparam CHOICE: kind of cards to choose from [optional; default is all
	 * the cards in the player's hand] (uses double-generator idiom)
	 * @eparam RESULT: the results of the DISCARD_CARDS events
	 */
	public static final EventType DISCARD_FORCE = new EventType("DISCARD_FORCE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.TARGET;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int successes = 0;
			Set cause = parameters.get(Parameter.CAUSE);
			int required = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				required = Sum.get(parameters.get(Parameter.NUMBER));

			Player player = parameters.get(Parameter.TARGET).getOne(Player.class);
			Set cards = null;
			if(parameters.containsKey(Parameter.CHOICE))
				cards = parameters.get(Parameter.CHOICE);
			else
				cards = new Set(player.getHand(game.actualState).objects);

			successes = 0;
			while(!cards.isEmpty())
			{
				GameObject thisCard = cards.getOne(GameObject.class);
				cards.remove(thisCard);
				java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
				parameters.put(Parameter.CAUSE, cause);
				parameters.put(Parameter.CARD, new Set(thisCard));
				if(createEvent(game, thisCard.getOwner(game.actualState) + " discards " + thisCard + ".", DISCARD_ONE_CARD, newParameters).attempt(event))
					successes++;

				if(successes == required)
					return true;
			}
			return false;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// get the number of cards out of the parameters
			int numberOfCards = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				numberOfCards = Sum.get(parameters.get(Parameter.NUMBER));

			if(numberOfCards < 0)
				numberOfCards = 0;

			boolean allDiscarded = true;
			Set cause = parameters.get(Parameter.CAUSE);

			Player target = parameters.get(Parameter.TARGET).getOne(Player.class);

			// offer the choices to the player
			// TODO : "Search" is a keyword action. This should not be a search.
			java.util.Map<Parameter, Set> chooseParameters = new java.util.HashMap<Parameter, Set>();
			chooseParameters.put(Parameter.CAUSE, cause);
			chooseParameters.put(Parameter.PLAYER, parameters.get(Parameter.PLAYER));
			chooseParameters.put(Parameter.NUMBER, new Set(numberOfCards));
			chooseParameters.put(Parameter.CARD, new Set(target.getHand(game.actualState)));
			if(parameters.containsKey(Parameter.CHOICE))
				chooseParameters.put(Parameter.TYPE, parameters.get(Parameter.CHOICE));
			Event chooseEvent = createEvent(game, "Choose cards to discard", EventType.SEARCH, chooseParameters);
			chooseEvent.perform(event, false);

			java.util.Set<GameObject> choices = chooseEvent.getResult().getAll(GameObject.class);
			if(choices.size() != numberOfCards)
				allDiscarded = false;
			if(choices.size() != 0)
			{
				// build the Set of objects to discard
				Set discardThese = new Set(choices);

				// perform the discard event
				java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
				discardParameters.put(Parameter.CAUSE, cause);
				discardParameters.put(Parameter.CARD, discardThese);
				Event discard = createEvent(game, target + " discards " + discardThese + ".", DISCARD_CARDS, discardParameters);
				if(!discard.perform(event, false))
					allDiscarded = false;
				event.setResult(discard.getResultGenerator());
				return allDiscarded;
			}

			event.setResult(Empty.set);
			return allDiscarded;
		}
	};
	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * @eparam CAUSE: what is causing the discard
	 * @eparam CARD: what is being discarded
	 * @eparam TO: where the card will go [optional; default = owner's
	 * graveyard]
	 * @eparam CONTROLLER: who will control the card after it's discarded
	 * [required if TO is the battlefield or the stack; prohibited otherwise]
	 * @eparam RESULT: the zone change
	 */
	public static final EventType DISCARD_ONE_CARD = new EventType("DISCARD_ONE_CARD")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CARD;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject card = parameters.get(Parameter.CARD).getOne(Card.class);

			if(card == null)
				return false;

			Zone zone = card.getZone();

			return (zone != null && !card.isGhost() && zone.isHand());
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set card = parameters.get(Parameter.CARD);
			Player player = card.getOne(Card.class).getOwner(game.actualState);

			Zone to;
			Player controller = null;
			if(parameters.containsKey(Parameter.TO))
			{
				to = parameters.get(Parameter.TO).getOne(Zone.class);
				if(parameters.containsKey(Parameter.CONTROLLER))
					controller = parameters.get(Parameter.CONTROLLER).getOne(Player.class);
			}
			else
				to = player.getGraveyard(game.actualState);

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			moveParameters.put(Parameter.TO, new Set(to));
			moveParameters.put(Parameter.OBJECT, card);
			if(controller != null)
				moveParameters.put(Parameter.CONTROLLER, new Set(controller));

			Event move = createEvent(game, "Move " + card + " to " + to + ".", MOVE_OBJECTS, moveParameters);
			move.perform(event, false);

			ZoneChange change = move.getResult().getOne(ZoneChange.class);
			change.isDiscard = true;

			event.setResult(move.getResultGenerator());
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the discard
	 * @eparam PLAYER: the players that are discarding
	 * @eparam NUMBER: number of cards to discard
	 * @eparam CARD: set of cards to choose from [optional - default is the
	 * players' hands]
	 * @eparam RESULT: the results of the {@link #DISCARD_CARDS} event(s)
	 */
	public static final EventType DISCARD_RANDOM = new EventType("DISCARD_RANDOM")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CARD;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int numDiscard = Sum.get(parameters.get(Parameter.NUMBER));

			if(parameters.containsKey(Parameter.CARD))
			{
				// if a set of cards has specifically been given, make sure its
				// big enough to support all the discards
				int numChoices = parameters.get(Parameter.CARD).size();
				int numPlayers = parameters.get(Parameter.PLAYER).size();

				return (numChoices >= (numPlayers * numDiscard));
			}

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				if(player.getHand(game.actualState).objects.size() < numDiscard)
					return false;
			}
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// get the number of cards out of the parameter
			int numberOfCards = Sum.get(parameters.get(Parameter.NUMBER));

			Set cause = parameters.get(Parameter.CAUSE);

			boolean allDiscarded = true;
			Set result = new Set();

			java.util.List<GameObject> cardsInHand = null;
			if(parameters.containsKey(Parameter.CARD))
			{
				cardsInHand = new java.util.LinkedList<GameObject>();
				for(GameObject card: parameters.get(Parameter.CARD).getAll(GameObject.class))
					cardsInHand.add(card);
			}

			// get the cards in the player's hand
			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				player = player.getActual();

				// build the list of cards to choose from
				if(!parameters.containsKey(Parameter.CARD))
				{
					cardsInHand = new java.util.LinkedList<GameObject>();
					for(GameObject card: player.getHand(game.actualState))
						cardsInHand.add(card);
				}

				// choose n cards randomly
				java.util.Collections.shuffle(cardsInHand);
				Set discardThese = new Set();
				while(discardThese.size() < numberOfCards && 0 < cardsInHand.size())
					discardThese.add(cardsInHand.remove(0));
				if(discardThese.size() < numberOfCards)
					allDiscarded = false;

				// perform the discard event
				java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
				discardParameters.put(Parameter.CAUSE, cause);
				discardParameters.put(Parameter.CARD, discardThese);
				Event discard = createEvent(game, player + " discards " + discardThese + ".", DISCARD_CARDS, discardParameters);
				if(!discard.perform(event, false))
					allDiscarded = false;
				result.addAll(discard.getResult());
			}

			event.setResult(Identity.instance(result));
			return allDiscarded;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the discard
	 * @eparam PLAYER: the players discarding
	 * @eparam NUMBER: number of cards for each player to keep
	 * @eparam RESULT: the results of the DISCARD_CARDS event(s)
	 */
	public static final EventType DISCARD_TO = new EventType("DISCARD_TO")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// get the number of cards out of the parameter
			int numberOfCards = Sum.get(parameters.get(Parameter.NUMBER));

			if(numberOfCards < 0)
				numberOfCards = 0;

			Set cause = parameters.get(Parameter.CAUSE);

			boolean allDiscarded = true;
			Set result = new Set();

			// get the player out of the actee and get the ID's of the cards in
			// their hand
			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				int numberToDiscard = player.getHand(game.actualState).objects.size() - numberOfCards;
				if(numberToDiscard <= 0)
					continue;

				java.util.Map<Parameter, Set> discardParameters = new java.util.HashMap<Parameter, Set>();
				discardParameters.put(Parameter.CAUSE, cause);
				discardParameters.put(Parameter.PLAYER, new Set(player));
				discardParameters.put(Parameter.NUMBER, new Set(numberToDiscard));

				Event discard = createEvent(game, player + " discards " + numberToDiscard + " card" + (numberToDiscard == 1 ? "" : "s."), DISCARD_CHOICE, discardParameters);
				if(!discard.perform(event, false))
					result.addAll(discard.getResult());
				allDiscarded = false;
			}

			event.setResult(Identity.instance(result));
			return allDiscarded;
		}
	};
	/**
	 * @eparam CAUSE: the cause of the counters
	 * @eparam PLAYER: the player distributing the counters
	 * @eparam OBJECT: targets describing who is getting counters and how many
	 * @eparam COUNTER: the Counter.CounterType of counters to distribute
	 * @eparam RESULT: empty
	 */
	public static final EventType DISTRIBUTE_COUNTERS = new EventType("DISTRIBUTE_COUNTERS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject source = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
			Counter.CounterType counter = parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class);
			Set receivers = parameters.get(Parameter.OBJECT);

			EventFactory[] putCounters = new EventFactory[receivers.size()];
			int i = 0;
			for(Target receiver: receivers.getAll(Target.class))
				putCounters[i++] = putCounters(receiver.division, counter, IdentifiedWithID.instance(receiver.targetID), "");

			simultaneous(putCounters).createEvent(game, source).perform(event, false);
			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam SOURCE: the source of damage
	 * @eparam TAKER: targets describing who is taking damage and how much
	 * @eparam PREVENT: [optional] if present, the damage is unpreventable
	 * @eparam RESULT: empty
	 */
	public static final EventType DISTRIBUTE_DAMAGE = new EventType("DISTRIBUTE_DAMAGE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.TAKER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject source = parameters.get(Parameter.SOURCE).getOne(GameObject.class);
			Set takers = parameters.get(Parameter.TAKER);
			boolean unpreventable = parameters.containsKey(Parameter.PREVENT);

			for(Target takerTarget: takers.getAll(Target.class))
			{
				Identified taker = game.actualState.get(takerTarget.targetID);
				for(int i = 0; i < takerTarget.division; i++)
					event.addDamage(source, taker, unpreventable);
			}

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam SOURCE: the source of the new mana
	 * @eparam PLAYER: the player whose mana to double
	 * @eparam MANA: the mana to double
	 * @eparam RESULT: the mana that was created this way
	 */
	public static final EventType DOUBLE_MANA = new EventType("DOUBLE_MANA")
	{
		@Override
		public boolean addsMana()
		{
			return true;
		}

		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();
			boolean success = true;

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				ManaPool oldMana = new ManaPool(parameters.get(Parameter.MANA).getAll(ManaSymbol.class));
				ManaPool newMana = new ManaPool();

				for(ManaSymbol mana: oldMana)
				{
					ManaSymbol.ManaType manaType = mana.getType();
					if(null != manaType)
					{
						switch(manaType)
						{
						case COLORLESS:
							ManaSymbol newSymbol = new ManaSymbol(Integer.toString(mana.colorless));
							newSymbol.colorless = mana.colorless;
							newMana.add(newSymbol);
							break;
						default:
							newMana.add(new ManaSymbol(manaType.getColor()));
							break;
						}
					}
				}

				java.util.Map<Parameter, Set> manaParameters = new java.util.HashMap<Parameter, Set>();
				manaParameters.put(Parameter.SOURCE, parameters.get(Parameter.SOURCE));
				manaParameters.put(Parameter.MANA, new Set(newMana));
				manaParameters.put(Parameter.PLAYER, new Set(player));
				Event manaEvent = createEvent(game, "Add " + newMana + " to " + player.getName() + "'s mana pool.", EventType.ADD_MANA, manaParameters);
				success = (manaEvent.perform(event, false) && success);
				result.addAll(manaEvent.getResult());
			}

			event.setResult(Identity.instance(result));
			return success;
		}
	};

	/**
	 * @eparam EVENT: An EventFactory that will construct the appropriate draw
	 * event. If this draw-and-reveal effect is part of a replacement effect,
	 * this should be an EventParts of the replacement effect's
	 * "replacedByThis()" generator. Otherwise, construct a normal draw event
	 * factory and pass it to this parameter.
	 * @eparam RESULT: The result of the draw event.
	 */
	public static final EventType DRAW_AND_REVEAL = new EventType("DRAW_AND_REVEAL")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			EventFactory drawFactory = parameters.get(Parameter.EVENT).getOne(EventFactory.class);
			Event draw = drawFactory.createEvent(game, event.getSource());
			boolean ret = draw.perform(event, true);
			SetGenerator result = NewObjectOf.instance(draw.getResultGenerator());
			event.setResult(result.evaluate(game, null));
			if(!ret)
				return false;

			java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
			revealParameters.put(Parameter.CAUSE, draw.parameters.get(Parameter.CAUSE).evaluate(game, draw.getSource()));
			revealParameters.put(Parameter.OBJECT, result.evaluate(game, null));
			Event reveal = createEvent(game, "Reveal the drawn cards", REVEAL, revealParameters);
			reveal.perform(event, true);

			return true;
		}
	};

	/**
	 * @eparam CAUSE: what is causing the draw
	 * @eparam PLAYER: the players that are drawing
	 * @eparam NUMBER: number of cards to draw
	 * @eparam RESULT: the results of the {@link #DRAW_ONE_CARD} event(s)
	 */
	public static final EventType DRAW_CARDS = new EventType("DRAW_CARDS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean allDrawn = true;
			Set cause = parameters.get(Parameter.CAUSE);
			int numberOfCards = Sum.get(parameters.get(Parameter.NUMBER));
			Set result = new Set();

			// 120.2a If an effect instructs more than one player to draw cards,
			// the active player performs all of his or her draws first, then
			// each other player in turn order does the same.
			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				java.util.Map<Parameter, Set> drawParameters = new java.util.HashMap<Parameter, Set>();
				drawParameters.put(Parameter.CAUSE, cause);

				// this player draws one card numberOfCards times
				for(int i = 0; i < numberOfCards; i++)
				{
					// get the 'new' actual player each time since his library
					// changed after the last draw
					drawParameters.put(Parameter.PLAYER, new Set(game.actualState.get(player.ID)));

					Event drawOne = createEvent(game, player + " draws a card.", DRAW_ONE_CARD, drawParameters);
					if(!drawOne.perform(event, true))
						allDrawn = false;
					result.addAll(drawOne.getResult());
				}
			}

			event.setResult(Identity.instance(result));
			return allDrawn;
		}
	};
	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * @eparam CAUSE: what is causing the draw
	 * @eparam PLAYER: the player that is drawing
	 * @eparam RESULT: the result of the {@link #MOVE_OBJECTS} event
	 */
	public static final EventType DRAW_ONE_CARD = new EventType("DRAW_ONE_CARD")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Zone library = player.getLibrary(game.actualState);

			if(library.objects.size() == 0)
			{
				player.getPhysical().unableToDraw = true;
				event.setResult(Empty.set);
				return false;
			}

			Zone hand = player.getHand(game.actualState);
			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			moveParameters.put(Parameter.TO, new Set(hand));
			moveParameters.put(Parameter.OBJECT, new Set(library.objects.get(0)));
			Event move = createEvent(game, "Put " + library.objects.get(0) + " into " + hand + ".", MOVE_OBJECTS, moveParameters);
			move.perform(event, false);

			ZoneChange change = move.getResult().getOne(ZoneChange.class);
			change.isDraw = true;

			event.setResult(move.getResultGenerator());
			return true;
		}
	};
	/** @eparam RESULT: Empty */
	public static final EventType EMPTY_ALL_MANA_POOLS = new EventType("EMPTY_ALL_MANA_POOLS")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Map<Parameter, Set> emptyPoolParameters = new java.util.HashMap<Parameter, Set>();
			emptyPoolParameters.put(Parameter.CAUSE, new Set(game));
			emptyPoolParameters.put(Parameter.PLAYER, new Set(game.actualState.players));
			Event emptyPools = createEvent(game, event.getName(), EMPTY_MANA_POOL, emptyPoolParameters);
			emptyPools.perform(event, false);

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is emptying the mana pools
	 * @eparam PLAYER: whose mana pools to empty
	 * @eparam RESULT: the mana emptied
	 */
	public static final EventType EMPTY_MANA_POOL = new EventType("EMPTY_MANA_POOL")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject cause = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
			Set result = new Set();
			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				// If the cause is the game, then mana pools are being emptied
				// as a step or phase ends, and we may have some work to do.
				if(cause == null)
				{
					SetPattern doesntEmpty = game.actualState.manaThatDoesntEmpty.get(player.ID);
					java.util.Iterator<ManaSymbol> m = player.getPhysical().pool.iterator();
					while(m.hasNext())
					{
						ManaSymbol mana = m.next();
						if(!doesntEmpty.match(game.actualState, null, new Set(mana)))
						{
							result.add(mana);
							m.remove();
						}
					}
				}
				// Otherwise an effect is doing it and continuous effects will
				// do nothing to stop it.
				else
				{
					result.addAll(player.pool);
					player.getPhysical().pool.clear();
				}
			}

			event.setResult(Identity.instance(result));
			return true;
		}
	};

	/**
	 * @eparam STEP: the step that is ending
	 * @eparam RESULT: empty
	 */
	public static final EventType END_STEP = new EventType("END_STEP")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CAUSE;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			game.physicalState.setPreviousStep(parameters.get(Parameter.STEP).getOne(Step.class));
			createEvent(game, "Empty all mana pools.", EventType.EMPTY_ALL_MANA_POOLS, null).perform(event, false);
			event.setResult(Empty.set);
			return true;
		}
	};

	/**
	 * @eparam CAUSE: why is the turn ending?
	 * @eparam RESULT: nothing. thats all thats left. nothing.
	 */
	public static final EventType END_THE_TURN = new EventType("END_THE_TURN")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CAUSE;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// 1) Exile every object on the stack.
			// This includes Time Stop, though it will continue to resolve. It
			// also includes spells and abilities that can't be countered.
			Event exile = new Event(game.physicalState, "All spells and abilities on the stack are exiled.", EventType.MOVE_OBJECTS);
			exile.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
			exile.parameters.put(EventType.Parameter.TO, ExileZone.instance());
			exile.parameters.put(EventType.Parameter.OBJECT, InZone.instance(Stack.instance()));
			exile.perform(null, true);

			// 2) All attacking and blocking creatures are removed from combat.
			Event removeFromCombat = new Event(game.physicalState, "All attacking and blocking creatures are removed from combat.", EventType.REMOVE_FROM_COMBAT);
			removeFromCombat.parameters.put(EventType.Parameter.OBJECT, Union.instance(Attacking.instance(), Blocking.instance()));
			removeFromCombat.perform(null, true);

			// 3) State-based actions are checked. No player gets priority, and
			// no triggered abilities are put onto the stack.
			while(game.checkStateBasedActions())
			{
				// intentionally left blank
			}

			// 4) The current phase and/or step ends. The game skips straight to
			// the cleanup step. The cleanup step happens in its entirety.
			Turn currentTurn = game.physicalState.currentTurn();
			currentTurn.phases.clear();

			Player owner = currentTurn.getOwner(game.physicalState);
			Phase newPhase = new Phase(owner, Phase.PhaseType.ENDING);
			newPhase.steps.clear();
			newPhase.steps.add(new Step(owner, Step.StepType.CLEANUP));
			currentTurn.phases.add(newPhase);

			throw new Game.StopPriorityException();
		}
	};
	/**
	 * @eparam CAUSE: what is causing the swap
	 * @eparam OBJECT: the two objects whose control is beings swapped (no more
	 * than two, no less than two, the number of the counting shall be two, not
	 * one, not three, four is right out)
	 * @eparam RESULT: empty
	 */
	public static final EventType EXCHANGE_CONTROL = new EventType("EXCHANGE_CONTROL")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			return this.attemptEvents(event, this.getEvents(game, parameters));
		}

		private boolean attemptEvents(Event parentEvent, java.util.Set<Event> events)
		{
			if(events == null)
				return false;
			for(Event event: events)
				if(!event.attempt(parentEvent))
					return false;
			return true;
		}

		private java.util.Set<Event> getEvents(Game game, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);

			if(objects.size() != 2)
				return null;

			java.util.Set<Event> ret = new java.util.HashSet<Event>();
			java.util.Iterator<GameObject> iter = objects.iterator();
			GameObject objectOne = iter.next();
			GameObject objectTwo = iter.next();
			Player controllerOne = objectOne.getController(game.actualState);
			Player controllerTwo = objectTwo.getController(game.actualState);

			{
				ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
				controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(objectTwo));
				controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, Identity.instance(controllerOne));

				java.util.Map<Parameter, Set> controlParameters = new java.util.HashMap<Parameter, Set>();
				controlParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				controlParameters.put(Parameter.EFFECT, new Set(controlPart));
				controlParameters.put(Parameter.EXPIRES, new Set(Empty.instance()));
				ret.add(createEvent(game, controllerOne + " gains control of " + objectTwo + ".", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, controlParameters));
			}

			{
				ContinuousEffect.Part controlPart = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
				controlPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(objectOne));
				controlPart.parameters.put(ContinuousEffectType.Parameter.PLAYER, Identity.instance(controllerTwo));

				java.util.Map<Parameter, Set> controlParameters = new java.util.HashMap<Parameter, Set>();
				controlParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				controlParameters.put(Parameter.EFFECT, new Set(controlPart));
				controlParameters.put(Parameter.EXPIRES, new Set(Empty.instance()));
				ret.add(createEvent(game, controllerTwo + " gains control of " + objectOne + ".", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, controlParameters));
			}

			return ret;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			java.util.Set<Event> events = this.getEvents(game, parameters);
			if(!this.attemptEvents(event, events))
				return false;

			boolean ret = true;

			for(Event childEvent: events)
				if(!childEvent.perform(event, false))
					ret = false;

			return ret;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the swap
	 * @eparam PLAYER: the two players swapping lifetotals (no more than two, no
	 * less than two)
	 * @eparam RESULT: empty
	 */
	public static final EventType EXCHANGE_LIFE_TOTALS = new EventType("EXCHANGE_LIFE_TOTALS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			return this.attemptEvents(event, this.getEvents(game, parameters));
		}

		private boolean attemptEvents(Event parentEvent, java.util.Set<Event> events)
		{
			if(events == null)
				return false;
			for(Event event: events)
				if(!event.attempt(parentEvent))
					return false;
			return true;
		}

		private java.util.Set<Event> getEvents(Game game, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<Player> players = parameters.get(Parameter.PLAYER).getAll(Player.class);

			if(players.size() != 2)
				return null;

			java.util.Iterator<Player> iter = players.iterator();
			Player playerOne = iter.next();
			Player playerTwo = iter.next();
			int lifeOne = playerOne.lifeTotal;
			int lifeTwo = playerTwo.lifeTotal;

			java.util.Map<Parameter, Set> parametersOne = new java.util.HashMap<Parameter, Set>();
			parametersOne.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			parametersOne.put(Parameter.NUMBER, new Set(lifeTwo));
			parametersOne.put(Parameter.PLAYER, new Set(playerOne));
			Event eventOne = createEvent(game, playerOne + "'s life total becomes " + lifeTwo, EventType.SET_LIFE, parametersOne);

			java.util.Map<Parameter, Set> parametersTwo = new java.util.HashMap<Parameter, Set>();
			parametersTwo.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			parametersTwo.put(Parameter.NUMBER, new Set(lifeOne));
			parametersTwo.put(Parameter.PLAYER, new Set(playerTwo));
			Event eventTwo = createEvent(game, playerTwo + "'s life total becomes " + lifeOne, EventType.SET_LIFE, parametersTwo);

			java.util.Set<Event> ret = new java.util.HashSet<Event>();
			ret.add(eventOne);
			ret.add(eventTwo);
			return ret;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			java.util.Set<Event> events = this.getEvents(game, parameters);
			if(!this.attemptEvents(event, events))
				return false;

			boolean ret = true;

			for(Event childEvent: events)
				if(!childEvent.perform(event, false))
					ret = false;

			return ret;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the choice
	 * @eparam NUMBER: how many to choose (integer or range) [optional; default
	 * is 1]
	 * @eparam OBJECT: what is being chosen from
	 * @eparam PLAYER: who is choosing (singular)
	 * @eparam HIDDEN: if present, the cards should be exiled "face down"
	 * (hidden).
	 * @eparam RESULT: results of the MOVE_OBJECTS event
	 */
	public static final EventType EXILE_CHOICE = new EventType("EXILE_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int required = getRange(parameters.get(Parameter.NUMBER)).getLower(0);

			java.util.Set<GameObject> chosen = new java.util.HashSet<GameObject>();
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
				newParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				newParameters.put(Parameter.TO, new Set(game.actualState.exileZone()));
				newParameters.put(Parameter.OBJECT, new Set(object));
				if(createEvent(game, "Exile " + object + ".", MOVE_OBJECTS, newParameters).attempt(event))
				{
					chosen.add(object);
					if(chosen.size() >= required)
						return true;
				}
			}

			return false;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			org.rnd.util.NumberRange number = getRange(parameters.get(Parameter.NUMBER));

			// offer the choices to the player
			java.util.Set<GameObject> choices = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			java.util.Collection<GameObject> chosen = player.sanitizeAndChoose(game.actualState, number.getLower(0), number.getUpper(choices.size()), choices, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.EXILE);
			if(number.contains(chosen.size()))
				event.allChoicesMade = true;

			event.putChoices(player, chosen);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set chosen = event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class));

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			moveParameters.put(Parameter.TO, new Set(game.actualState.exileZone()));
			moveParameters.put(Parameter.OBJECT, chosen);
			if(parameters.containsKey(Parameter.HIDDEN))
				moveParameters.put(Parameter.HIDDEN, Empty.set);

			Event exileEvent = createEvent(game, "Exile " + chosen + ".", MOVE_OBJECTS, moveParameters);
			boolean status = exileEvent.perform(event, false);

			event.setResult(exileEvent.getResultGenerator());

			return event.allChoicesMade && status;
		}
	};
	/**
	 * IMPORTANT NOTE: The OBJECT parameter takes two objects that can either be
	 * 2 Targets, 1 Target and 1 GameObject, or 2 GameObjects. If there is a
	 * possibility that both objects could represent the same creature, they are
	 * not allowed to both be represented by GameObjects. Otherwise, Set
	 * uniqueness will remove one, and performing the event will fail.
	 * 
	 * 701.10. Fight
	 * 
	 * 701.10a A spell or ability may instruct a creature to fight another
	 * creature. To fight, each creature deals damage equal to its power to the
	 * other creature. When such a spell or ability resolves, if either creature
	 * is no longer on the battlefield, is no longer a creature, or is otherwise
	 * an illegal target (if the spell or ability targeted that creature), no
	 * damage is dealt.
	 * 
	 * 701.10b If a creature fights itself, it deals damage equal to its power
	 * to itself twice.
	 * 
	 * 701.10c The damage dealt when a creature fights isn't combat damage.
	 * 
	 * @eparam CAUSE: the cause of the fighting
	 * @eparam OBJECT: two objects representing the creatures that are fighting
	 * @eparam RESULT: the creatures that fought
	 */
	public static final EventType FIGHT = new EventType("FIGHT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<GameObject> fighters = new java.util.HashSet<GameObject>();

			fighters.addAll(parameters.get(Parameter.OBJECT).getAll(GameObject.class));

			for(Target target: parameters.get(Parameter.OBJECT).getAll(Target.class))
			{
				GameObject object = game.actualState.getByIDObject(target.targetID);
				if(object != null)
					fighters.add(object);
			}

			if(fighters.size() != 2)
			{
				event.setResult(Empty.set);
				return false;
			}

			java.util.Iterator<GameObject> iterator = fighters.iterator();

			GameObject one = iterator.next();
			Set oneSet = new Set(one);

			GameObject two = iterator.next();
			Set twoSet = new Set(two);

			java.util.Map<Parameter, Set> oneParameters = new java.util.HashMap<Parameter, Set>();
			oneParameters.put(Parameter.SOURCE, oneSet);
			oneParameters.put(Parameter.NUMBER, new Set(one.getPower()));
			oneParameters.put(Parameter.TAKER, twoSet);
			Event oneDamage = createEvent(game, one + " deals damage equal to its power to " + two, EventType.DEAL_DAMAGE_EVENLY, oneParameters);

			java.util.Map<Parameter, Set> twoParameters = new java.util.HashMap<Parameter, Set>();
			twoParameters.put(Parameter.SOURCE, twoSet);
			twoParameters.put(Parameter.NUMBER, new Set(two.getPower()));
			twoParameters.put(Parameter.TAKER, oneSet);
			Event twoDamage = createEvent(game, two + " deals damage equal to its power to " + one, EventType.DEAL_DAMAGE_EVENLY, twoParameters);

			boolean ret = true;
			if(!oneDamage.perform(event, false))
				ret = false;
			if(!twoDamage.perform(event, false))
				ret = false;

			if(ret)
				event.setResult(Identity.instance(one, two));
			return ret;
		}
	};
	/**
	 * @eparam OBJECT: the card to flip
	 * @eparam RESULT: empty
	 */
	public static final EventType FLIP_CARD = new EventType("FLIP_CARD")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				object.getPhysical().setFlipped(true);

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam PLAYER: who is flipping
	 * @eparam TYPE: a set containing exactly two Answer objects representing
	 * the possible results of this coin flip (optional; default is WIN/LOSE; if
	 * you use this parameter you'll probably use HEADS/TAILS (TODO : implement
	 * HEADS/TAILS flips))
	 * @eparam RESULT: an Answer that is the result of this flip
	 */
	public static final EventType FLIP_COIN = new EventType("FLIP_COIN")
	{
		private final java.util.Random generator = new java.util.Random();

		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Answer[] possibleResults = {Answer.WIN, Answer.LOSE};
			if(parameters.containsKey(Parameter.TYPE))
			{
				java.util.Set<Answer> typeParameter = parameters.get(Parameter.TYPE).getAll(Answer.class);
				if(typeParameter.size() != 2)
					throw new UnsupportedOperationException("Coin flip type " + typeParameter + " does not contain exactly two Answer objects!");
				possibleResults = typeParameter.toArray(possibleResults);
			}

			Answer flipResult = possibleResults[this.generator.nextBoolean() ? 1 : 0];
			if(game.noRandom)
			{
				Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
				flipResult = player.choose(1, java.util.Arrays.asList(possibleResults), PlayerInterface.ChoiceType.COIN_FLIP, PlayerInterface.ChooseReason.MANIPULATE_COIN_FLIP).get(0);
			}

			event.setResult(Identity.instance(flipResult));

			// This returns false if the flip resulted in Answer.LOSE, but true
			// for everything else (including Answer.WIN, and any specified
			// Answers)
			return (flipResult != Answer.LOSE);
		}
	};
	/**
	 * @eparam CAUSE: what is causing the life gain
	 * @eparam PLAYER: the players gaining the life
	 * @eparam NUMBER: how much life is being gained
	 * @eparam RESULT: the players that gained life and each amount of life
	 * gained by a player
	 */
	public static final EventType GAIN_LIFE = new EventType("GAIN_LIFE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int lifeGain = Sum.get(parameters.get(Parameter.NUMBER));
			if(lifeGain <= 0)
				return true;

			Set players = parameters.get(Parameter.PLAYER);
			for(Player player: players.getAll(Player.class))
			{
				java.util.HashMap<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>(parameters);
				newParameters.put(Parameter.PLAYER, new Set(player));
				Event gainLifeOnePlayer = createEvent(game, player + " loses " + lifeGain + " life", GAIN_LIFE_ONE_PLAYER, newParameters);
				if(!gainLifeOnePlayer.attempt(event))
					return false;
			}

			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int lifeGain = Sum.get(parameters.get(Parameter.NUMBER));
			if(lifeGain <= 0)
			{
				event.setResult(Empty.set);
				return true;
			}

			Set result = new Set();
			Set players = parameters.get(Parameter.PLAYER);
			for(Player player: players.getAll(Player.class))
			{
				java.util.HashMap<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>(parameters);
				newParameters.put(Parameter.PLAYER, new Set(player));
				Event gain = createEvent(game, player + " gains " + lifeGain + " life", GAIN_LIFE_ONE_PLAYER, newParameters);
				gain.perform(event, false);
				result.addAll(gain.getResult());
			}

			event.setResult(result);
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the life gain
	 * @eparam PLAYER: the player gaining the life
	 * @eparam NUMBER: how much life is being gained
	 * @eparam RESULT: the player that gained life and the amount of life gained
	 */
	public static final EventType GAIN_LIFE_ONE_PLAYER = new EventType("GAIN_LIFE_ONE_PLAYER")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int lifeGain = Sum.get(parameters.get(Parameter.NUMBER));

			if(lifeGain < 0)
				lifeGain = 0;

			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			player.getPhysical().lifeTotal += lifeGain;

			event.setResult(Identity.instance(player, lifeGain));
			return true;
		}
	};
	public static final EventType GAME_OVER = new EventType("GAME_OVER")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent(event);
			for(Player player: game.actualState.players)
				player.alert(sanitized);

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * Marker event for the decks having been established. This is a hack to
	 * support X-10 modifying the decks after Pack Wars. The correct solution is
	 * to prioritize GameTypeRules' modifyGameState functions. See ticket #381.
	 * 
	 * @eparam RESULT: empty
	 */
	public static final EventType GAME_START = new EventType("GAME_START")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);
			return true;
		}

	};
	/**
	 * @eparam IF: if the set's size is 0, do else, otherwise, do then
	 * @eparam THEN: EventFactory to perform for nonempty
	 * @eparam ELSE: EventFactory to perform for empty
	 * @eparam RESULT: result of THEN or ELSE, depending on how IF evaluated
	 */
	public static final EventType IF_CONDITION_THEN_ELSE = new EventType("IF_CONDITION_THEN_ELSE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.IF;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set ifCondition = parameters.get(Parameter.IF);

			// if condition is true, do Then
			if(!ifCondition.isEmpty())
			{
				// if there isn't a then event, return FULL
				if(!parameters.containsKey(Parameter.THEN))
				{
					event.setResult(Empty.set);
					return true;
				}

				Event thenEvent = parameters.get(Parameter.THEN).getOne(EventFactory.class).createEvent(game, event.getSource());
				boolean status = thenEvent.perform(event, false);
				event.setResult(thenEvent.getResultGenerator());
				return status;
			}

			// otherwise condition is false, so do Else

			// if there isn't an else event, return NONE
			if(!parameters.containsKey(Parameter.ELSE))
			{
				event.setResult(Empty.set);
				return false;
			}

			Event elseEvent = parameters.get(Parameter.ELSE).getOne(EventFactory.class).createEvent(game, event.getSource());
			boolean status = elseEvent.perform(event, false);
			event.setResult(elseEvent.getResultGenerator());
			return status;
		}
	};
	/**
	 * @eparam IF: EventFactory to perform and test
	 * @eparam THEN: EventFactory to perform on success
	 * @eparam ELSE: EventFactory to perform on failure
	 * @eparam RESULT: results of either THEN or ELSE, depending on whether IF
	 * successfully performed
	 */
	public static final EventType IF_EVENT_THEN_ELSE = new EventType("IF_EVENT_THEN_ELSE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.IF;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Event ifEvent = parameters.get(Parameter.IF).getOne(EventFactory.class).createEvent(game, event.getSource());
			ifEvent.isCost = true;
			boolean ifStatus = ifEvent.perform(event, true);

			if(ifStatus)
			{
				// if there isn't a then event, return FULL
				if(!parameters.containsKey(Parameter.THEN))
				{
					event.setResult(Empty.set);
					return true;
				}

				Event thenEvent = parameters.get(Parameter.THEN).getOne(EventFactory.class).createEvent(game, event.getSource());
				boolean status = thenEvent.perform(event, true);
				Set result = thenEvent.getResult();
				event.setResult(Identity.instance(result));
				return status;
			}
			// if there isn't an else event, return NONE
			if(!parameters.containsKey(Parameter.ELSE))
			{
				event.setResult(Empty.set);
				return false;
			}

			Event elseEvent = parameters.get(Parameter.ELSE).getOne(EventFactory.class).createEvent(game, event.getSource());
			boolean status = elseEvent.perform(event, true);
			Set result = elseEvent.getResult();
			event.setResult(Identity.instance(result));
			return status;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the look
	 * @eparam OBJECT: the objects to be looked at or zones from which to look
	 * at all objects
	 * @eparam PLAYER: who's looking (can be multiple players)
	 * @eparam EFFECT: a set generator saying when the look expires [optional;
	 * if set, this will create a reveal fce with that duration]
	 * @eparam RESULT: the revealed object
	 */
	public static final EventType LOOK = new EventType("LOOK")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set objects = parameters.get(Parameter.OBJECT);
			java.util.Set<GameObject> gameObjects = objects.getAll(GameObject.class);
			for(Zone z: objects.getAll(Zone.class))
				gameObjects.addAll(z.objects);
			java.util.Set<Player> players = parameters.get(Parameter.PLAYER).getAll(Player.class);
			Set ret = new Set();

			for(Player player: players)
			{
				org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent.Look(event, player, gameObjects);
				player.alert(sanitized);
			}

			GameObject cause = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
			for(GameObject object: gameObjects)
			{
				// Change the visibleTo property of the actual object. If there
				// is a duration, the FCE will take over. Otherwise it will
				// revert next time the game state refreshes.
				object = game.actualState.copyForEditing(object);
				for(Player player: players)
					object.setActualVisibility(player, true);
				ret.add(object);
			}

			SetGenerator expiration;
			if(parameters.containsKey(Parameter.EFFECT))
			{
				expiration = parameters.get(Parameter.EFFECT).getOne(SetGenerator.class);
				if(expiration == null)
					throw new UnsupportedOperationException(cause + ": LOOK.EFFECT didn't contain a SetGenerator");
			}
			else
				expiration = Not.instance(Exists.instance(Identity.instance(cause)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.LOOK);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(objects));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, Identity.instance(players));

			java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
			lookParameters.put(Parameter.CAUSE, new Set(cause));
			lookParameters.put(Parameter.EFFECT, new Set(part));
			lookParameters.put(Parameter.EXPIRES, new Set(expiration));
			createEvent(game, event.getName(), CREATE_FLOATING_CONTINUOUS_EFFECT, lookParameters).perform(event, false);

			event.setResult(Identity.instance(ret));
			return true;
		}
	};
	/**
	 * Encapsulates
	 * "look at the top [n] cards of your library, then put them back in any order."
	 * 
	 * @eparam CAUSE: what is causing the look
	 * @eparam PLAYER: who is looking
	 * @eparam TARGET: whose library to look at [optional; default = PLAYER]
	 * @eparam NUMBER: number of cards to look at
	 * @eparam RESULT: the zone changes
	 */
	public static final EventType LOOK_AND_PUT_BACK = new EventType("LOOK_AND_PUT_BACK")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{

			Set cause = parameters.get(Parameter.CAUSE);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Player target = player;
			if(parameters.containsKey(Parameter.TARGET))
				target = parameters.get(Parameter.TARGET).getOne(Player.class);
			Zone library = target.getLibrary(game.actualState);
			int number = Sum.get(parameters.get(Parameter.NUMBER));

			boolean ret = true;
			Set cards = new Set();
			for(int i = 0; i < number; i++)
			{
				if(i == library.objects.size())
				{
					ret = false;
					break;
				}
				cards.add(library.objects.get(i));
			}

			java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
			lookParameters.put(Parameter.CAUSE, cause);
			lookParameters.put(EventType.Parameter.OBJECT, cards);
			lookParameters.put(EventType.Parameter.PLAYER, new Set(player));
			createEvent(game, "Look at the top " + org.rnd.util.NumberNames.get(number) + " cards of your library.", EventType.LOOK, lookParameters).perform(event, false);

			// then put them back in any order.
			if(target.equals(player))
			{
				// If the player looking owns the library being looked at, we
				// can do this the easy way ...
				java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
				moveParameters.put(EventType.Parameter.CAUSE, cause);
				moveParameters.put(EventType.Parameter.INDEX, ONE);
				moveParameters.put(EventType.Parameter.OBJECT, cards);
				Event move = createEvent(game, "Put them back in any order.", EventType.PUT_INTO_LIBRARY, moveParameters);
				move.perform(event, false);
				event.setResult(move.getResultGenerator());
			}
			else
			{
				// ... otherwise we'll have to manually ask the player to order
				// the cards.
				java.util.Set<GameObject> choices = cards.getAll(GameObject.class);
				java.util.List<GameObject> ordered = player.sanitizeAndChoose(game.actualState, choices.size(), choices, PlayerInterface.ChoiceType.MOVEMENT_LIBRARY, PlayerInterface.ChooseReason.ORDER_LIBRARY_TARGET);

				Set result = new Set();
				for(GameObject o: ordered)
				{
					java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
					moveParameters.put(EventType.Parameter.CAUSE, cause);
					moveParameters.put(EventType.Parameter.INDEX, ONE);
					moveParameters.put(EventType.Parameter.OBJECT, new Set(o));
					Event move = createEvent(game, "Put a card back.", EventType.PUT_INTO_LIBRARY, moveParameters);
					move.perform(event, true);
					result.addAll(move.getResult());
				}
				event.setResult(Identity.instance(result));
			}

			return ret;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the game loss (must contain one and
	 * exactly one Game.LoseReason)
	 * @eparam PLAYER: the player losing the game (one and exactly one)
	 * @eparam RESULT: the player who lost
	 */
	public static final EventType LOSE_GAME = new EventType("LOSE_GAME")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Game.LoseReason reason = parameters.get(Parameter.CAUSE).getOne(Game.LoseReason.class);
			if(reason == null)
				throw new RuntimeException("LOSE_GAME called without a Game.LoseReason");

			Set players = parameters.get(Parameter.PLAYER);

			org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent(event, players.toString() + " lost the game");
			for(Player player: game.actualState.players)
				player.alert(sanitized);

			// 104.5. If a player loses the game, he or she leaves the game.
			game.physicalState.removePlayers(players.getAll(Player.class));

			event.setResult(Identity.instance(players));
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the life loss
	 * @eparam PLAYER: the players losing the life
	 * @eparam NUMBER: how much life is being lost
	 * @eparam DAMAGE: required if you are DEAL_DAMAGE_BATCHES (value is
	 * unimportant); prohibited if you aren't.
	 * @eparam RESULT: the players that lost life and each amount of life lost
	 * by a player
	 */
	public static final EventType LOSE_LIFE = new EventType("LOSE_LIFE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int lifeloss = Sum.get(parameters.get(Parameter.NUMBER));
			if(lifeloss <= 0)
				return true;

			Set players = parameters.get(Parameter.PLAYER);
			for(Player player: players.getAll(Player.class))
			{
				java.util.HashMap<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>(parameters);
				newParameters.put(Parameter.PLAYER, new Set(player));
				Event loseLifeOnePlayer = createEvent(game, player + " loses " + lifeloss + " life", LOSE_LIFE_ONE_PLAYER, newParameters);
				if(!loseLifeOnePlayer.attempt(event))
					return false;
			}

			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int lifeloss = Sum.get(parameters.get(Parameter.NUMBER));
			if(lifeloss <= 0)
			{
				event.setResult(Empty.set);
				return true;
			}

			Set players = parameters.get(Parameter.PLAYER);
			Set result = new Set();
			for(Player player: players.getAll(Player.class))
			{
				java.util.HashMap<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>(parameters);
				newParameters.put(Parameter.PLAYER, new Set(player));
				Event loseLifeOnePlayer = createEvent(game, player + " loses " + lifeloss + " life", LOSE_LIFE_ONE_PLAYER, newParameters);
				loseLifeOnePlayer.perform(event, false);
				result.addAll(loseLifeOnePlayer.getResult());
			}

			event.setResult(result);
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the life loss
	 * @eparam PLAYER: the player losing the life
	 * @eparam NUMBER: how much life is being lost
	 * @eparam DAMAGE: required if the parent LOSE_LIFE event had this
	 * parameter; prohibited otherwise
	 * @eparam RESULT: the player that lost life and how much life was lost
	 */
	public static final EventType LOSE_LIFE_ONE_PLAYER = new EventType("LOSE_LIFE_ONE_PLAYER")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int lifeLoss = Sum.get(parameters.get(Parameter.NUMBER));

			if(lifeLoss < 0)
				lifeLoss = 0;

			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Player physicalPlayer = player.getPhysical();
			physicalPlayer.lifeTotal -= lifeLoss;
			if(parameters.containsKey(Parameter.DAMAGE))
				if(null != player.minimumLifeTotalFromDamage)
					if(physicalPlayer.lifeTotal < player.minimumLifeTotalFromDamage)
						physicalPlayer.lifeTotal = player.minimumLifeTotalFromDamage;

			event.setResult(Identity.instance(player, lifeLoss));
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the mill
	 * @eparam NUMBER: how many cards to mill
	 * @eparam PLAYER: who is milling
	 * @eparam RESULT: results of the MOVE_OBJECTS event(s)
	 */
	public static final EventType MILL_CARDS = new EventType("MILL_CARDS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int number = Sum.get(parameters.get(Parameter.NUMBER));

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
				if(player.getPhysical().getLibrary(game.physicalState).objects.size() < number)
					return false;

			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean allMilled = true;
			Set cause = parameters.get(Parameter.CAUSE);
			int num = Sum.get(parameters.get(Parameter.NUMBER));

			if(num < 0)
				num = 0;

			Set result = new Set();

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				Zone graveyard = player.getGraveyard(game.actualState);
				Zone library = player.getLibrary(game.actualState);
				Set topCards = TopCards.get(num, library);
				java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
				moveParameters.put(Parameter.CAUSE, cause);
				moveParameters.put(Parameter.TO, new Set(graveyard));
				moveParameters.put(Parameter.OBJECT, topCards);

				Event move = createEvent(game, "Put " + topCards + " into " + graveyard + ".", MOVE_OBJECTS, moveParameters);
				if(!move.perform(event, false))
					allMilled = false;
				result.addAll(move.getResult());
			}

			event.setResult(Identity.instance(result));
			return allMilled;
		}
	};
	/**
	 * This event ACTUALLY MOVES OBJECTS. This event should only be created or
	 * performed in Event.perform().
	 * 
	 * @eparam TARGET: Movements
	 * @eparam RESULT: the new GameObject instances
	 */
	public static final EventType MOVE_BATCH = new EventType("MOVE_BATCH")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(ZoneChange change: parameters.get(Parameter.TARGET).getAll(ZoneChange.class))
			{
				Zone from = game.actualState.get(change.sourceZoneID);

				if(from == null)
					return false;

				GameObject object = game.actualState.get(change.oldObjectID);

				if(object == null || object.isGhost())
					return false;

				if(!from.objects.contains(object))
					return false;
			}

			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<ZoneChange> successfulZoneChanges = new java.util.HashSet<ZoneChange>();

			java.util.Map<Player, java.util.List<ZoneChange>> controlledChanges = new java.util.HashMap<Player, java.util.List<ZoneChange>>();
			for(ZoneChange movement: parameters.get(Parameter.TARGET).getAll(ZoneChange.class))
			{
				Player controller = null;
				if(-1 != movement.controllerID)
					controller = game.actualState.get(movement.controllerID);
				java.util.List<ZoneChange> changes;
				if(controlledChanges.containsKey(controller))
					changes = controlledChanges.get(controller);
				else
				{
					changes = new java.util.LinkedList<ZoneChange>();
					controlledChanges.put(controller, changes);
				}
				changes.add(movement);
			}

			java.util.Set<GameObject> newObjects = new java.util.HashSet<GameObject>();
			java.util.Collection<Player> players = new java.util.HashSet<Player>(controlledChanges.keySet());
			if(game.hasStarted())
			{
				players.remove(null);
				players = game.actualState.apnapOrder(new Set(players));
				if(controlledChanges.containsKey(null))
					players.add(null);
			}
			for(Player player: players)
			{
				for(ZoneChange movement: controlledChanges.get(player))
				{
					GameObject original = game.actualState.get(movement.oldObjectID);

					// If the object isn't there anymore, don't revive it.
					if(original.isGhost())
						continue;

					GameObject moveOut = original.getPhysical();

					// We need to make sure moveOut has a copy in the actual
					// state so that effects like UNATTACH don't also affect the
					// object that will become the ghost.
					game.actualState.copyForEditing(moveOut);

					// Because of Event.validate, this will come out to the
					// actual version unless we find the physical version again
					GameObject moveIn = game.physicalState.get(movement.newObjectID);

					Zone from = game.physicalState.get(movement.sourceZoneID);
					Zone to = game.physicalState.get(movement.destinationZoneID);

					// 304.4. Instants can't enter the battlefield. If an
					// instant would enter the battlefield, it remains in its
					// previous zone instead.
					// 307.4. Sorceries can't enter the battlefield. If a
					// sorcery would enter the battlefield, it remains in its
					// previous zone instead.
					// These are worded like replacement effects. We really
					// don't care.
					boolean toBattlefield = to.equals(game.physicalState.battlefield());
					if(toBattlefield && (original.getTypes().contains(Type.INSTANT) || original.getTypes().contains(Type.SORCERY)))
					{
						event.setResult(Empty.set);
						continue;
					}

					boolean toStack = to.equals(game.physicalState.stack());
					boolean fromBattlefield = from.equals(game.physicalState.battlefield());
					boolean fromStack = from.equals(game.physicalState.stack());

					Set attachments = new Set();
					// If this is on the battlefield and was attached to
					// something, detach it.
					if(fromBattlefield && moveOut.getAttachedTo() != -1)
						attachments.add(moveOut);
					// Detach any objects attached to this.
					for(int attachmentID: moveOut.getAttachments())
						attachments.add(game.actualState.getByIDObject(attachmentID));
					if(!attachments.isEmpty())
					{
						java.util.Map<Parameter, Set> detachParameters = new java.util.HashMap<Parameter, Set>();
						detachParameters.put(EventType.Parameter.OBJECT, attachments);
						createEvent(game, "Unattach " + attachments + ".", EventType.UNATTACH, detachParameters).perform(event, false);
					}

					if(movement.hidden)
						for(Player p: game.actualState.players)
						{
							moveIn.setPhysicalVisibility(p, false);
							moveIn.setActualVisibility(p, false);
						}

					// 303.4f If an Aura is entering the battlefield under a
					// player's control by any means other than by resolving as
					// an Aura spell, and the effect putting it onto the
					// battlefield doesn't specify the object or player the
					// Aura will enchant, that player chooses what it will
					// enchant as the Aura enters the battlefield. The player
					// must choose a legal object or player according to the
					// Aura's enchant ability and any other applicable effects.
					if(moveIn.getSubTypes().contains(SubType.AURA) && toBattlefield && (-1 == moveOut.zoneCastFrom))
					{
						boolean attachEffect = false;
						for(EventFactory factory: movement.events)
							if((ATTACH == factory.type) || (ATTACH_TO_CHOICE == factory.type))
								if(factory.parameters.get(Parameter.OBJECT).evaluate(game, event.getSource()).contains(moveIn))
									attachEffect = true;

						if(!attachEffect)
						{
							org.rnd.jmagic.abilities.keywords.Enchant enchantKeyword = null;
							for(Keyword k: moveIn.getKeywordAbilities())
							{
								if(k.isEnchant())
								{
									enchantKeyword = (org.rnd.jmagic.abilities.keywords.Enchant)k;
									break;
								}
							}

							boolean couldAttach = true;
							if(enchantKeyword == null)
								couldAttach = false;
							else
							{
								Set choices = enchantKeyword.filter.evaluate(game, moveIn);
								// Can't attach to something moving at the same
								// time as the Aura
								choices.removeAll(newObjects);
								if(choices.isEmpty())
									couldAttach = false;
								else
								{
									java.util.Map<Parameter, Set> attachParameters = new java.util.HashMap<Parameter, Set>();
									attachParameters.put(Parameter.OBJECT, new Set(moveIn));
									attachParameters.put(Parameter.PLAYER, new Set(moveIn.getController(moveIn.state)));
									attachParameters.put(Parameter.CHOICE, choices);
									createEvent(game, "Attach " + moveIn + " to an object or player.", EventType.ATTACH_TO_CHOICE, attachParameters).perform(event, false);
								}
							}

							// 303.4g If an Aura is entering the battlefield and
							// there is no legal object or player for it to
							// enchant, the Aura remains in its current zone,
							// unless that zone is the stack. In that case, the
							// Aura is put into its owner's graveyard instead of
							// entering the battlefield.
							if(!couldAttach)
							{
								// I'm ignoring the
								// "unless that zone is the stack"
								// because HOW THE HELL DOES AN AURA ENTER THE
								// BATTLEFIELD FROM THE STACK WITHOUT RESOLVING.
								// -RulesGuru
								event.setResult(Empty.set);
								continue;
							}
						}
					}

					// Remove before adding, just in case from == to
					from.remove(moveOut);
					// get the ghost
					moveOut = moveOut.getPhysical();

					if(0 != movement.index)
						to.addAtPosition(moveIn, movement.index);
					else
						to.addToTop(moveIn);

					Characteristics faceDownValues = null;
					if(movement.faceDownCharacteristics != null)
						faceDownValues = org.rnd.util.Constructor.construct(movement.faceDownCharacteristics, new Class<?>[] {}, new Object[] {});
					moveIn.faceDownValues = faceDownValues;
					if(toBattlefield || toStack)
					{
						Player controller = game.actualState.get(movement.controllerID);
						moveIn.setController(controller);

						java.util.Map<EventType.Parameter, Set> controlParameters = new java.util.HashMap<EventType.Parameter, Set>();
						controlParameters.put(Parameter.OBJECT, new Set(moveIn));
						controlParameters.put(Parameter.TARGET, new Set(controller));
						controlParameters.put(Parameter.ATTACKER, Empty.set);
						createEvent(game, controller + " controls " + moveIn + ".", CHANGE_CONTROL, controlParameters).perform(event, false);

						if(moveIn.isActivatedAbility() || moveIn.isTriggeredAbility())
							((NonStaticAbility)moveIn).sourceID = ((NonStaticAbility)moveOut).sourceID;
					}
					if(fromBattlefield || fromStack)
					{
						Player oldController = game.actualState.get(moveOut.controllerID);

						java.util.Map<EventType.Parameter, Set> controlParameters = new java.util.HashMap<EventType.Parameter, Set>();
						controlParameters.put(Parameter.OBJECT, new Set(moveOut));
						controlParameters.put(Parameter.PLAYER, new Set(oldController));
						createEvent(game, "No one controls " + moveOut + ".", CHANGE_CONTROL, controlParameters).perform(event, false);

						if(!toBattlefield)
							for(Player p: game.actualState.players)
								moveOut.setPhysicalVisibility(p, true);
					}

					if(fromStack && toBattlefield)
					{
						// 400.7a Effects from spells, activated abilities, and
						// triggered abilities that change the characteristics
						// of a permanent spell on the stack continue to apply
						// to the permanent that spell becomes.
						java.util.Collection<ContinuousEffect.Part> partsToModify = new java.util.LinkedList<ContinuousEffect.Part>();
						for(FloatingContinuousEffect effect: game.physicalState.floatingEffects)
							for(ContinuousEffect.Part part: effect.parts)
							{
								if(part.type.layer() == ContinuousEffectType.Layer.RULE_CHANGE || part.type.affects() == null)
									continue;

								Set affectedObjects = part.parameters.get(part.type.affects()).evaluate(game, null);
								if(affectedObjects.contains(original))
									partsToModify.add(part);
							}

						for(ContinuousEffect.Part part: partsToModify)
						{
							SetGenerator affectedObjects = part.parameters.get(part.type.affects());
							SetGenerator newAffectedObjects = Union.instance(IdentifiedWithID.instance(moveIn.ID), affectedObjects);
							part.parameters.put(part.type.affects(), newAffectedObjects);
						}

						// 707.4. ... The permanent the spell becomes will be a
						// face-down permanent.
						if(moveOut.faceDownValues != null)
							moveIn.faceDownValues = org.rnd.util.Constructor.construct(moveOut.faceDownValues.getClass(), new Class<?>[] {}, new Object[] {});
					}

					// Now that the object is actually there, refresh the state
					// so later events see it there
					game.refreshActualState();
					event.addToNeedsNewTimestamps(moveIn);
					if(movement.random)
						event.addToObjectsToOrderRandomly(moveIn, movement.index, to);
					else
						event.addToObjectsToOrderByChoice(moveIn, movement.index, to);
					successfulZoneChanges.add(movement);
					newObjects.add(moveIn);

					// 400.7. An object that moves from one zone to another
					// becomes a new object with no memory of, or relation to,
					// its previous existence. There are six exceptions to this
					// rule:

					// TODO : 400.7b Prevention effects that apply to damage
					// from a permanent spell on the stack continue to apply to
					// damage from the permanent that spell becomes.

					// 400.7d and 400.7e handled by card writers, using
					// NewObjectOf and/or FutureSelf generators

					// 400.7f: See ContinuousEffect.part.apply.

					if(fromStack && toBattlefield)
					{
						// 400.7c Abilities of a permanent that require
						// information about choices made when that permanent
						// was cast use information about the spell that became
						// that permanent.
						{
							Set newAbilities = new Set(moveIn.getNonStaticAbilities());
							for(NonStaticAbility ability: moveOut.getNonStaticAbilities())
							{
								Linkable.Manager originalManager = ability.getLinkManager();
								if(originalManager.getLinkInformation(game.actualState) != null)
								{
									NonStaticAbility newAbility = newAbilities.getOne(ability.getClass());
									if(newAbility == null)
										continue;

									// Make sure nothing else tries to add link
									// information to this ability later
									newAbilities.remove(newAbility);

									Linkable.Manager newManager = newAbility.getLinkManager();
									for(Object o: originalManager.getLinkInformation(game.actualState))
										newManager.addLinkInformation(o);
								}
							}

							newAbilities = new Set(moveIn.getStaticAbilities());
							for(StaticAbility ability: moveOut.getStaticAbilities())
							{
								Linkable.Manager originalManager = ability.getLinkManager();
								if(originalManager.getLinkInformation(game.actualState) != null)
								{
									StaticAbility newAbility = newAbilities.getOne(ability.getClass());
									if(newAbility == null)
										continue;

									// Make sure nothing else tries to add link
									// information to this ability later
									newAbilities.remove(newAbility);

									Linkable.Manager newManager = newAbility.getLinkManager();
									for(Object o: originalManager.getLinkInformation(game.actualState))
										newManager.addLinkInformation(o);
								}
							}
						} // 400.7c
					} // if from stack and to battlefield
				}
			}

			for(Player player: game.actualState.players)
			{
				player.alert(game.actualState);
				org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent.Move(event, successfulZoneChanges, player);
				player.alert(sanitized);
			}

			event.setResult(Identity.instance(newObjects));
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the choice
	 * @eparam NUMBER: how many to choose (integer or range) [optional; default
	 * is 1]
	 * @eparam OBJECT: what is being chosen from
	 * @eparam CHOICE: a ChooseReason for this choice
	 * @eparam TO: where the objects are going
	 * @eparam PLAYER: who is choosing (singular)
	 * @eparam HIDDEN: if present, the new object will be visible to no one.
	 * used for exiling things "face down"
	 * @eparam INDEX: where to insert the object if the zone is ordered (See
	 * {@link ZoneChange#index})
	 * @eparam RESULT: results of the {@link #MOVE_OBJECTS} event
	 */
	public static final EventType MOVE_CHOICE = new EventType("MOVE_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean hidden = parameters.containsKey(Parameter.HIDDEN);
			int required = getRange(parameters.get(Parameter.NUMBER)).getLower(0);

			java.util.Set<GameObject> chosen = new java.util.HashSet<GameObject>();
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>(parameters);
				newParameters.put(Parameter.OBJECT, new Set(object));
				if(hidden)
					newParameters.put(Parameter.HIDDEN, NonEmpty.set);
				if(createEvent(game, "Move " + object + ".", MOVE_OBJECTS, newParameters).attempt(event))
				{
					chosen.add(object);
					if(chosen.size() >= required)
						return true;
				}
			}

			return false;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			org.rnd.util.NumberRange number = getRange(parameters.get(Parameter.NUMBER));

			// offer the choices to the player
			java.util.Set<GameObject> choices = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			PlayerInterface.ChooseReason reason = parameters.get(Parameter.CHOICE).getOne(PlayerInterface.ChooseReason.class);
			if(parameters.containsKey(Parameter.HIDDEN) && reason.isPublic)
				reason = new PlayerInterface.ChooseReason(reason.source, reason.query, false);
			java.util.Collection<GameObject> chosen = player.sanitizeAndChoose(game.actualState, number.getLower(0), number.getUpper(choices.size()), choices, PlayerInterface.ChoiceType.OBJECTS, reason);
			if(!number.contains(chosen.size()))
				event.allChoicesMade = false;

			event.putChoices(player, chosen);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set chosen = event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class));
			boolean status = true;

			if(!chosen.isEmpty())
			{
				java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>(parameters);
				moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				moveParameters.put(Parameter.TO, parameters.get(Parameter.TO));
				moveParameters.put(Parameter.OBJECT, chosen);
				if(parameters.containsKey(Parameter.INDEX))
					moveParameters.put(Parameter.INDEX, parameters.get(Parameter.INDEX));

				Event moveEvent = createEvent(game, "Move " + chosen + ".", MOVE_OBJECTS, moveParameters);
				status = moveEvent.perform(event, false);

				event.setResult(moveEvent.getResultGenerator());
			}
			else
				event.setResult(Empty.set);

			return event.allChoicesMade && status;
		}
	};
	/**
	 * @eparam CAUSE: what is doing the moving
	 * @eparam FROM: where it's coming from
	 * @eparam TO: where it's going
	 * @eparam NUMBER: how many to move [optional; default is 1]
	 * @eparam COUNTER: the type of counter to move
	 * @eparam RESULT: the counters that were moved
	 */
	public static final EventType MOVE_COUNTERS = new EventType("MOVE_COUNTERS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.COUNTER;
		}

		// there was a commented-out attempt() method here. if you want it, go
		// back through the repository and find it.

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject from = parameters.get(Parameter.FROM).getOne(GameObject.class);
			GameObject to = parameters.get(Parameter.TO).getOne(GameObject.class);

			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));

			Counter.CounterType type = null;
			if(parameters.containsKey(Parameter.COUNTER))
				type = parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class);

			boolean success = true;
			Set result = new Set();

			if(number < 1)
			{
				event.setResult(Identity.instance(result));
				return true;
			}

			java.util.Map<Parameter, Set> removeParameters = new java.util.HashMap<Parameter, Set>();
			removeParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			removeParameters.put(Parameter.COUNTER, new Set(type));
			removeParameters.put(Parameter.NUMBER, new Set(number));
			removeParameters.put(Parameter.OBJECT, new Set(from));
			Event removeEvent = createEvent(game, "Remove counters from " + from.getName(), EventType.REMOVE_COUNTERS, removeParameters);
			success = removeEvent.perform(event, false) && success;

			java.util.Map<Parameter, Set> addParameters = new java.util.HashMap<Parameter, Set>();
			addParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			addParameters.put(Parameter.COUNTER, new Set(type));
			addParameters.put(Parameter.NUMBER, new Set(removeEvent.getResult().size()));
			addParameters.put(Parameter.OBJECT, new Set(to));
			Event addEvent = createEvent(game, "Put counters on " + to.getName(), EventType.PUT_COUNTERS, addParameters);
			success = addEvent.perform(event, false) && success;

			result.addAll(removeEvent.getResult());
			result.addAll(addEvent.getResult());

			event.setResult(Identity.instance(result));

			return success;
		}
	};
	/**
	 * @eparam CAUSE: what is doing the moving
	 * @eparam TO: where it's going
	 * @eparam CONTROLLER: who controls the object after it moves [required when
	 * TO is the stack or the battlefield; prohibited when TO is anything else]
	 * @eparam INDEX: where to insert the object if the zone is ordered (See
	 * {@link ZoneChange#index})
	 * @eparam OBJECT: what is being moved
	 * @eparam HIDDEN: if present, the new object will be visible to no one.
	 * used for exiling things "face down"
	 * @eparam FACE_DOWN: if present, put the object into the new zone face down
	 * with the characteristics defined by this Class<? extends
	 * {@link Characteristics}> object (see
	 * {@link ZoneChange#faceDownCharacteristics})
	 * @eparam RANDOM: if present, and the objects move to an ordered zone,
	 * those objects will be ordered randomly rather than being ordered by a
	 * player's choice
	 * @eparam RESOLVING: if this parameter is present, it signifies that the
	 * movement being performed is the last step of a spell's resolution
	 * (usually, putting a permanent spell onto the battlefield or a
	 * nonpermanent spell into its owner's graveyard). cards should not ever set
	 * this parameter.
	 * @eparam RESULT: {@link ZoneChange}s that will eventually be carried out
	 * by {@link EventType#MOVE_BATCH}
	 */
	public static final EventType MOVE_OBJECTS = new EventType("MOVE_OBJECTS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			if(objects.isEmpty())
			{
				event.setResult(Empty.set);
				return true;
			}

			Set cause = parameters.get(Parameter.CAUSE);
			Zone to = parameters.get(Parameter.TO).getOne(Zone.class);
			int controllerID = -1;
			if(parameters.containsKey(Parameter.CONTROLLER))
				controllerID = parameters.get(Parameter.CONTROLLER).getOne(Player.class).ID;
			int index = 0;
			if(parameters.containsKey(Parameter.INDEX))
				index = parameters.get(Parameter.INDEX).getOne(Integer.class);
			Class<? extends Characteristics> faceDownCharacteristics = null;
			if(parameters.containsKey(Parameter.FACE_DOWN))
				faceDownCharacteristics = parameters.get(Parameter.FACE_DOWN).getOne(Class.class);
			boolean hidden = parameters.containsKey(Parameter.HIDDEN);
			boolean random = parameters.containsKey(Parameter.RANDOM);
			boolean isSpellResolution = parameters.containsKey(Parameter.RESOLVING);

			GameObject causeObject = cause.getOne(GameObject.class);
			if(null == causeObject && null == cause.getOne(Game.class))
				throw new UnsupportedOperationException("MOVE_OBJECTS has non-GameObject, non-Game cause: " + cause);
			int causeID = causeObject == null ? 0 : causeObject.ID;

			boolean fromLibrary = false;
			boolean allMoved = true;
			Set result = new Set();

			for(GameObject moveMe: objects)
			{
				Zone from = moveMe.getZone().getActual();
				to = to.getActual();

				if(!fromLibrary)
					fromLibrary = from.isLibrary();

				// 110.5f A token that has left the battlefield can't change
				// zones.
				if(moveMe.isToken() && ((Token)moveMe).wasOnBattlefield())
				{
					allMoved = false;
					continue;
				}

				ZoneChange change = new ZoneChange();
				change.causeID = causeID;
				change.controllerID = controllerID;
				change.destinationZoneID = to.ID;
				change.faceDownCharacteristics = faceDownCharacteristics;
				change.hidden = hidden;
				change.index = index;
				change.isCost = event.isCost;
				change.isEffect = event.isEffect;
				change.isSpellResolution = isSpellResolution;
				change.oldObjectID = moveMe.ID;
				change.random = random;
				change.sourceZoneID = from.ID;
				event.addZoneChange(change);

				result.add(change);
			}

			event.setResult(Identity.instance(result));

			to = to.getActual();
			if((game.currentAction != null) && !parameters.get(Parameter.OBJECT).isEmpty())
				if(to.isLibrary() || (fromLibrary && game.actualState.stack().equals(to)))
					game.currentAction.irreversible = true;

			return allMoved;
		}
	};

	/**
	 * @eparam PLAYER: the player mulliganning
	 * @eparam RESULT: Empty
	 */
	public static final EventType MULLIGAN = new EventType("MULLIGAN")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Zone library = player.getLibrary(game.actualState);
			Zone hand = player.getHand(game.actualState);

			Set cardsInHand = new Set();
			for(GameObject card: hand)
				cardsInHand.add(card);

			Set shuffleObjects = new Set(cardsInHand);
			shuffleObjects.add(player);

			java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
			shuffleParameters.put(Parameter.CAUSE, new Set(game));
			shuffleParameters.put(Parameter.OBJECT, shuffleObjects);
			shuffleParameters.put(Parameter.ZONE, new Set(library));

			boolean hasMulliganed = game.actualState.getTracker(NormalMulliganTracker.class).getValue(game.actualState).contains(player.ID);

			int numberToDraw = hand.objects.size() - ((hasMulliganed || game.actualState.numPlayers() == 2) ? 1 : 0);

			java.util.Map<Parameter, Set> drawParameters = new java.util.HashMap<Parameter, Set>();
			drawParameters.put(Parameter.PLAYER, new Set(player));
			drawParameters.put(Parameter.CAUSE, new Set(game));
			drawParameters.put(Parameter.NUMBER, new Set(numberToDraw));

			createEvent(game, "Shuffle " + cardsInHand + " into " + library + ".", SHUFFLE_INTO_LIBRARY, shuffleParameters).perform(event, true);
			createEvent(game, player + " draws " + numberToDraw + " cards.", DRAW_CARDS, drawParameters).perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam PLAYER: the players mulliganning
	 * @eparam RESULT: The players who chose not to keep
	 */
	public static final EventType MULLIGAN_SIMULTANEOUS = new EventType("MULLIGAN_SIMULTANEOUS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean ret = true;

			java.util.List<Player> orderedPlayers = new java.util.LinkedList<Player>(game.physicalState.players);
			orderedPlayers.retainAll(parameters.get(Parameter.PLAYER).getAll(Player.class));

			java.util.Iterator<Player> iter = orderedPlayers.iterator();
			// keys are player ids, values are event ids (no entry in the map
			// means that the player chose to keep)
			java.util.Map<Integer, Integer> mulligans = new java.util.HashMap<Integer, Integer>();
			while(iter.hasNext())
			{
				Player player = iter.next();
				boolean keep = (player.getHand(game.actualState).objects.size() == 0);

				if(!keep)
				{
					java.util.Map<Parameter, Set> mulliganParameters = new java.util.HashMap<Parameter, Set>();
					mulliganParameters.put(EventType.Parameter.PLAYER, new Set(player));
					Event mulligan = createEvent(game, "Mulligan", EventType.MULLIGAN, mulliganParameters);

					// 103.4a If an effect allows a player to perform an action
					// any time [that player] could mulligan, the player may
					// perform that action at a time he or she would declare
					// whether or not he or she will take a mulligan. ... If the
					// player performs the action, he or she then declares
					// whether or not he or she will take a mulligan.

					// [aka the Serum Powder rule]
					// We loop until the player has made a "real" keep/mulligan
					// decision, since according to 103.4a (see above) a Serum
					// Powder mulligan causes the player to then choose whether
					// to take a mulligan.
					while(!(keep || mulligans.containsKey(player.ID)))
					{
						java.util.Collection<Event> choices = new java.util.HashSet<Event>();
						choices.add(mulligan);
						for(java.util.Map.Entry<Integer, EventFactory> e: player.getActual().mulliganOptions.entrySet())
							choices.add(e.getValue().createEvent(game, game.actualState.<GameObject>get(e.getKey())));

						java.util.List<Event> chosen = player.sanitizeAndChoose(game.physicalState, 0, 1, choices, PlayerInterface.ChoiceType.EVENT, PlayerInterface.ChooseReason.MULLIGAN);

						if(chosen.isEmpty())
							keep = true;
						else
						{
							Event mulliganEvent = chosen.iterator().next();
							if(mulliganEvent.equals(mulligan))
								mulligans.put(player.ID, mulliganEvent.ID);
							else
								mulliganEvent.perform(null, true);
						}
					}
				}

				if(keep)
				{
					iter.remove();
					ret = false;
				}
			}

			// Players that chose to keep are not in the list anymore.
			for(Player player: orderedPlayers)
				game.physicalState.<Event>get(mulligans.get(player.ID)).perform(event, false);

			event.setResult(Identity.instance(orderedPlayers));
			return ret;
		}
	};

	/**
	 * @eparam CAUSE: the cause of the event
	 * @eparam EVENT: an EventFactory for the cost to be payed
	 * @eparam NUMBER: the number of times to pay it
	 * @eparam RESULT: the results of all the cost events
	 */
	public static final EventType PAY_CUMULATIVE_UPKEEP = new EventType("PAY_CUMULATIVE_UPKEEP")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.EVENT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Event costEvent = parameters.get(Parameter.EVENT).getOne(EventFactory.class).createEvent(game, event.getSource());
			int number = Sum.get(parameters.get(Parameter.NUMBER));

			if(0 >= number)
			{
				event.setResult(Empty.set);
				return true;
			}

			/*
			 * Kamikaze: But then we're forcing all cumulative upkeep payment events 
			 *       to use the NUMBER parameter
			 * RulesGuru: So?
			 * Kamikaze: Ok, I'm putting this conversation in a comment right above
			 *       that change so that when you yell at me, I can yell back.
			 */
			if(costEvent.parameters.containsKey(Parameter.NUMBER))
				costEvent.parameters.put(Parameter.NUMBER, Multiply.instance(costEvent.parameters.get(Parameter.NUMBER), numberGenerator(number)));
			else
				costEvent.parameters.put(Parameter.NUMBER, Identity.instance(number));

			boolean status = costEvent.perform(event, false);

			event.setResult(costEvent.getResultGenerator());
			return status;
		}
	};
	/**
	 * @eparam CAUSE: what is asking for life payment
	 * @eparam PLAYER: what player is paying the life (singular!)
	 * @eparam NUMBER: how much life is being paid
	 * @eparam RESULT: the player who paid life and the amount of life paid
	 */
	public static final EventType PAY_LIFE = new EventType("PAY_LIFE")
	{
		@Override
		public Parameter affects()
		{
			return LOSE_LIFE.affects();
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int number = Sum.get(parameters.get(Parameter.NUMBER));

			// 117.3b. ... (Players can always pay 0 life.)
			if(number == 0)
				return true;

			// 118.4. If a cost or effect allows a player to pay an amount of
			// life greater than 0, the player may do so only if his or her life
			// total is greater than or equal to the amount of the payment. ...
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			if(player.lifeTotal < number)
				return false;

			// 118.4. If a player pays life, the payment is subtracted from his
			// or her life total; in other words, the player loses that much
			// life.
			// They can't pay life if they can't lose it.
			java.util.HashMap<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>(parameters);
			newParameters.put(Parameter.PLAYER, new Set(player));
			Event loseLifeOnePlayer = createEvent(game, player + " loses " + number + " life", LOSE_LIFE_ONE_PLAYER, newParameters);
			return loseLifeOnePlayer.attempt(event);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean status = this.attempt(game, event, parameters);

			Event loseLife = createEvent(game, event.getName(), LOSE_LIFE, parameters);
			status = loseLife.perform(event, false) && status;

			event.setResult(loseLife.getResultGenerator());
			return status;
		}
	};
	/**
	 * @eparam CAUSE: what is asking the player to pay mana
	 * @eparam OBJECT: if this payment is the cost to play an object, that
	 * object. [required for {@link EventType#CAST_SPELL_OR_ACTIVATE_ABILITY}
	 * and {@link EventType#PAY_MANA_COST}; prohibited otherwise]
	 * @eparam COST: what mana cost is being paid (either mana symbols or a
	 * single mana pool) [optional; default is "any amount of mana"]
	 * @eparam PLAYER: who is being asked to pay
	 * @eparam NUMBER: the number of times to pay that cost [optional; default
	 * is 1]
	 * @eparam RESULT: the mana used to pay the cost
	 */
	public static final EventType PAY_MANA = new EventType("PAY_MANA")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// if this is the cost for a spell or ability, we really don't know
			// whether they'll be able to pay, since they have the chance to
			// play mana abilities
			if(parameters.containsKey(Parameter.OBJECT))
				return true;

			ManaPool cost = parameters.get(Parameter.COST).getOne(ManaPool.class);
			if(null == cost)
				cost = new ManaPool(parameters.get(Parameter.COST).getAll(ManaSymbol.class));
			else
			{
				// shallow copy the mana pool so we don't have to worry about
				// accidentally changing someones mana cost or something
				cost = new ManaPool(cost);
			}
			GameObject cause = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
			if(cause != null)
				for(ManaSymbol m: cost)
					if(m.sourceID == -1)
						m.sourceID = cause.ID;
			Player paying = parameters.get(Parameter.PLAYER).getOne(Player.class).getPhysical();
			return paying.pool.pays(game.actualState, cost);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject object = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
			ManaPool cost = new ManaPool(parameters.get(Parameter.COST).getAll(ManaSymbol.class));
			if(cost.usesX())
			{
				boolean expand = false;
				for(ManaSymbol symbol: cost)
					if(symbol.isX && !symbol.isSingular())
					{
						expand = true;
						break;
					}
				if(expand)
					cost = cost.expandX(object.getValueOfX(), object.xRestriction);
			}

			event.setResult(Empty.set);

			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
			{
				number = Sum.get(parameters.get(Parameter.NUMBER));
				if(number < 0)
					number = 1;
			}

			cost = cost.duplicate(number);
			if(object != null)
				for(ManaSymbol m: cost)
					if(m.sourceID == -1)
						m.sourceID = object.ID;

			int convertedCost = cost.converted();
			if(0 == convertedCost)
				return true;

			Player paying = parameters.get(Parameter.PLAYER).getOne(Player.class).getPhysical();

			// 608.2f If an effect gives a player the option to pay mana, he or
			// she may activate mana abilities before taking that action.
			// If this request to pay mana came from a game object, it fits the
			// description in 608.2f (above). However, if the parent of this
			// event is a PLAYER_MAY, then they've already been given the
			// opportunity to activate mana abilities.
			if(null != object && (event.parent == null || (event.parent.type != PLAYER_MAY && event.parent.type != PLAYER_MAY_PAY_X)))
				paying.mayActivateManaAbilities();

			if((paying.pool.size() < convertedCost) || !paying.pool.pays(game.actualState, cost))
				return false;

			// The player can pay the mana, so keep asking them to choose
			// until they choose a set that can pay for it
			while(true)
			{
				PlayerInterface.ChooseParameters<ManaSymbol> chooseParameters = new PlayerInterface.ChooseParameters<ManaSymbol>(convertedCost, convertedCost, new java.util.LinkedList<ManaSymbol>(paying.pool), PlayerInterface.ChoiceType.MANA_PAYMENT, PlayerInterface.ChooseReason.PAY_MANA);
				chooseParameters.replacement = cost.toString();
				ManaPool choice = new ManaPool(paying.choose(chooseParameters));
				if(choice.pays(game.actualState, cost))
				{
					paying.pool.removeAll(choice);

					// resolving spells and abilities abilities that ask you
					// to
					// pay mana aren't part of a PlayerAction being
					// performed
					if(null != game.currentAction)
						game.currentAction.manaPaid.addAll(choice);

					event.setResult(Identity.instance(choice));
					return true;
				}
				// TODO: Else, let the player know that the choice was
				// invalid and they should choose again?
			}
		}
	};
	/**
	 * NOTE -- THIS EVENT DOES NOT ACCEPT A COST! If you're looking for that,
	 * you want {@link EventType#PAY_MANA}!!
	 * 
	 * @eparam CAUSE: what is asking for the payment
	 * @eparam PLAYER: who is paying
	 * @eparam OBJECT: the object for which to pay the mana cost
	 * @eparam RESULT: empty (should this be something meaningful?)
	 */
	public static final EventType PAY_MANA_COST = new EventType("PAY_MANA_COST")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			if(object == null || object.getManaCost() != null)
				return true;

			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			if(null != object.alternateCosts)
				for(AlternateCost c: object.alternateCosts)
					if(c.playersMayPay.contains(player))
						return true;

			return false;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject o = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			if(o == null)
				return;
			Player p = parameters.get(Parameter.PLAYER).getOne(Player.class);

			java.util.Collection<CostCollection> availableChoices = new java.util.LinkedList<CostCollection>();
			if(o.getManaCost() != null)
				availableChoices.add(new CostCollection(CostCollection.TYPE_MANA, o.getManaCost()));
			if(null != o.alternateCosts)
				for(AlternateCost c: o.alternateCosts)
					if(c.playersMayPay.contains(p))
						availableChoices.add(c.cost);

			CostCollection choice = p.sanitizeAndChoose(game.actualState, 1, availableChoices, PlayerInterface.ChoiceType.ALTERNATE_COST, PlayerInterface.ChooseReason.OPTIONAL_ALTERNATE_COST).get(0);
			event.putChoices(p, java.util.Collections.singleton(choice));
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			GameObject o = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			if(o == null)
				return true;
			Player p = parameters.get(Parameter.PLAYER).getOne(Player.class);
			CostCollection cost = event.getChoices(p).getOne(CostCollection.class);

			for(EventFactory f: cost.events)
				if(!f.createEvent(game, o).perform(event, true))
					return false;
			if(!cost.manaCost.isEmpty())
			{
				java.util.Map<Parameter, Set> manaParameters = new java.util.HashMap<Parameter, Set>();
				manaParameters.put(Parameter.CAUSE, new Set(o));
				manaParameters.put(Parameter.OBJECT, new Set(o));
				manaParameters.put(Parameter.PLAYER, new Set(p));
				manaParameters.put(Parameter.COST, new Set(cost.manaCost));
				Event payMana = createEvent(game, p + " pays " + cost.manaCost, EventType.PAY_MANA, manaParameters);
				if(!payMana.perform(event, true))
					return false;
			}

			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is letting the player play the card
	 * @eparam PLAYER: the player playing the card
	 * @eparam OBJECT: the object being played
	 * @eparam ALTERNATE_COST: the alternate cost for playing the object
	 * [optional]
	 * @eparam RESULT: the object after it's played
	 */
	public static final EventType PLAY_CARD = new EventType("PLAY_CARD")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

			if(object.getTypes().contains(Type.LAND))
			{
				for(PlayLandAction action: game.createPlayLandActions(player, object))
				{
					java.util.Map<Parameter, Set> landParameters = new java.util.HashMap<Parameter, Set>();
					landParameters.put(Parameter.ACTION, new Set(action));
					landParameters.put(Parameter.PLAYER, new Set(player));
					landParameters.put(Parameter.LAND, new Set(object));
					Event playLand = createEvent(game, player + " plays " + object + ".", PLAY_LAND, landParameters);
					if(playLand.attempt(event))
						return true;
				}
				return false;
			}

			java.util.Map<Parameter, Set> castParameters = new java.util.HashMap<Parameter, Set>();
			castParameters.put(Parameter.PLAYER, new Set(player));
			castParameters.put(Parameter.OBJECT, new Set(object));
			if(parameters.containsKey(Parameter.ALTERNATE_COST))
				castParameters.put(Parameter.ALTERNATE_COST, parameters.get(Parameter.ALTERNATE_COST));
			Event castEvent = createEvent(game, player + " casts " + object + ".", CAST_SPELL_OR_ACTIVATE_ABILITY, castParameters);
			return castEvent.attempt(event);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Identified cause = parameters.get(Parameter.CAUSE).getOne(Identified.class);
			int sourceID = (cause == null ? 0 : cause.ID);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);

			if(object.getTypes().contains(Type.LAND))
			{
				// If a PlayLandAction wasn't specified, have the player choose
				// one.
				java.util.Set<PlayLandAction> actions = game.createPlayLandActions(player, object);
				PlayLandAction action = player.sanitizeAndChoose(game.actualState, 1, actions, PlayerInterface.ChoiceType.ACTION, PlayerInterface.ChooseReason.ACTION).get(0);
				boolean ret = action.saveStateAndPerform();

				event.setResult((ret ? Identity.instance(action.played()) : Identity.instance()));
				return ret;
			}

			CastSpellAction cast = new CastSpellAction(game, object, player, sourceID);
			if(parameters.containsKey(Parameter.ALTERNATE_COST))
				cast.forceAlternateCost(Identity.instance(parameters.get(Parameter.ALTERNATE_COST)));

			boolean ret = cast.saveStateAndPerform();
			event.setResult((ret ? Identity.instance(cast.played()) : Identity.instance()));
			return ret;
		}
	};
	/**
	 * @eparam ACTION: what action is being used to play the land
	 * @eparam PLAYER: the player playing the land
	 * @eparam LAND: the land being played
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PLAY_LAND = new EventType("PLAY_LAND")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.LAND;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Turn currentTurn = game.actualState.currentTurn();

			if(!(player.ID == currentTurn.ownerID))
				return false;

			return parameters.containsKey(Parameter.ACTION) && parameters.get(Parameter.ACTION).getOne(PlayLandAction.class) != null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject land = parameters.get(Parameter.LAND).getOne(GameObject.class);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			if(player.ID != game.actualState.currentTurn().ownerID)
			{
				event.setResult(Empty.set);
				return false;
			}

			PlayLandAction action = parameters.containsKey(EventType.Parameter.ACTION) ? parameters.get(EventType.Parameter.ACTION).getOne(PlayLandAction.class) : null;

			if(action == null)
			{
				event.setResult(Empty.set);
				return false;
			}

			java.util.Map<Parameter, Set> playParameters = new java.util.HashMap<Parameter, Set>();
			playParameters.put(Parameter.CAUSE, new Set(game));
			playParameters.put(Parameter.CONTROLLER, new Set(player));
			playParameters.put(Parameter.OBJECT, new Set(land));
			Event putOntoBattlefield = createEvent(game, player + " puts " + land + " onto the battlefield.", PUT_ONTO_BATTLEFIELD, playParameters);
			putOntoBattlefield.perform(event, true);

			GameObject playedLand = game.actualState.get(putOntoBattlefield.getResult().getOne(ZoneChange.class).newObjectID);

			java.util.Map<Parameter, Set> playFlagParameters = new java.util.HashMap<Parameter, Set>();
			playFlagParameters.put(Parameter.PLAYER, new Set(player));
			playFlagParameters.put(Parameter.OBJECT, new Set(playedLand));
			createEvent(game, player + " plays " + land + ".", BECOMES_PLAYED, playFlagParameters).perform(event, false);

			event.setResult(putOntoBattlefield.getResultGenerator());
			return true;
		}
	};
	/**
	 * Don't feel the need to use this unless it's the "only way". Performing
	 * choices directly via {@link Player}'s choose methods is fine. This event
	 * type is for when you can avoid a custom event type entirely using this
	 * type and {@link EffectResult}.
	 * 
	 * @eparam PLAYER: who is choosing
	 * @eparam NUMBER: how many choices are required [optional; default is 1]
	 * @eparam CHOICE: what to choose from
	 * @eparam TYPE: a {@link PlayerInterface#ChoiceType} and a
	 * {@link PlayerInterface#ChooseReason}
	 * @eparam OBJECT: what object is causing the choice [required when the
	 * {@link PlayerInterface#ChooseReason} in TYPE contains a "~", prohibited
	 * otherwise]
	 * @eparam RESULT: what was chosen
	 */
	public static final EventType PLAYER_CHOOSE = new EventType("PLAYER_CHOOSE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Set choices = parameters.get(Parameter.CHOICE);
			org.rnd.util.NumberRange number = getRange(parameters.get(Parameter.NUMBER));
			PlayerInterface.ChoiceType type = parameters.get(Parameter.TYPE).getOne(PlayerInterface.ChoiceType.class);
			PlayerInterface.ChooseReason reason = parameters.get(Parameter.TYPE).getOne(PlayerInterface.ChooseReason.class);

			PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters;
			chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(number.getLower(), number.getUpper(), type, reason);
			if(parameters.containsKey(Parameter.OBJECT))
				chooseParameters.thisID = parameters.get(Parameter.OBJECT).getOne(GameObject.class).ID;

			Set result = new Set();
			java.util.List<Object> choice = player.sanitizeAndChoose(game.actualState, choices, chooseParameters);

			if(parameters.containsKey(Parameter.ORDERED))
				result.add(choice);
			else
				result.addAll(choice);

			event.setResult(Identity.instance(result));
			return true;
		}
	};
	/**
	 * Don't feel the need to use this unless it's the "only way". Performing
	 * choices directly via {@link Player}'s choose methods is fine. This event
	 * type is for when you can avoid a custom event type entirely using this
	 * type and {@link EffectResult}.
	 * 
	 * @eparam PLAYER: who is choosing
	 * @eparam CHOICE: {@link org.rnd.util.NumberRange} (use a {@link Between})
	 * @eparam TYPE: a {@link String} describing the choice
	 * @eparam RESULT: what was chosen
	 */
	public static final EventType PLAYER_CHOOSE_NUMBER = new EventType("PLAYER_CHOOSE_NUMBER")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			org.rnd.util.NumberRange range = getRange(parameters.get(Parameter.CHOICE));
			String description = parameters.get(Parameter.TYPE).getOne(String.class);

			int chosen = player.chooseNumber(range, description);
			event.setResult(Identity.instance(chosen));
			return true;
		}
	};
	/**
	 * @eparam PLAYER: the player being given the choice
	 * @eparam EVENT: an EventFactory describing the event to perform
	 * @eparam TARGET: what the choice is for (must be an {@link Identified};
	 * optional)
	 * @eparam RESULT: the players YES/NO response, empty if the event is
	 * impossible
	 */
	public static final EventType PLAYER_MAY = new EventType("PLAYER_MAY")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set eventParameter = parameters.get(Parameter.EVENT);
			Event mayEvent = eventParameter.getOne(EventFactory.class).createEvent(game, event.getSource());

			Linkable link = eventParameter.getOne(Linkable.class);
			if(link != null)
				mayEvent.setStoreInObject(link);

			if(!mayEvent.attempt(event))
			{
				event.setResult(Empty.set);
				return false;
			}

			Player chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);

			PlayerInterface.ChooseParameters<Answer> chooseParameters = new PlayerInterface.ChooseParameters<Answer>(1, 1, new java.util.LinkedList<Answer>(Answer.mayChoices()), PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.MAY_EVENT);
			chooseParameters.thisID = mayEvent.ID;
			if(parameters.containsKey(Parameter.TARGET))
				chooseParameters.whatForID = parameters.get(Parameter.TARGET).getOne(Identified.class).ID;

			java.util.List<Answer> choice = chooser.choose(chooseParameters);

			Set result = new Set();
			for(Answer response: choice)
				result.add(response);
			event.setResult(Identity.instance(result));

			if(choice.size() == 0 || choice.get(0).equals(Answer.NO))
				return false;
			return mayEvent.perform(event, false);
		}
	};
	/**
	 * @eparam PLAYER: the player being given the choice
	 * @eparam OBJECT: the card to cast
	 * @eparam ALTERNATE_COST: a forced alternate cost as per
	 * CAST_SPELL_OR_ACTIVATE_ABILITY
	 * @eparam RESULT: the spell on the stack, if it is cast this way; empty
	 * otherwise
	 */
	public static final EventType PLAYER_MAY_CAST = new EventType("PLAYER_MAY_CAST")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject spell = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			CastSpellAction action = new CastSpellAction(game, spell, player, spell.ID);

			if(parameters.containsKey(Parameter.ALTERNATE_COST))
				action.forceAlternateCost(Identity.instance(parameters.get(Parameter.ALTERNATE_COST)));

			PlayerInterface.ChooseParameters<Answer> chooseParameters = new PlayerInterface.ChooseParameters<Answer>(1, 1, new java.util.LinkedList<Answer>(Answer.mayChoices()), PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.MAY_CAST);
			chooseParameters.thisID = spell.ID;
			Answer answer = player.choose(chooseParameters).get(0);

			if(answer == Answer.YES)
				if(action.saveStateAndPerform())
				{
					event.setResult(new Set(game.actualState.get(spell.getActual().futureSelf)));
					return true;
				}

			event.setResult(Empty.set);
			return false;
		}
	};
	/**
	 * @eparam CAUSE: what is asking the player to pay mana
	 * @eparam COST: what mana cost is being paid (use a ManaPool)
	 * @eparam NUMBER: how many times to pay the cost (integer only) [optional;
	 * default is 1]
	 * @eparam PLAYER: who is being asked to pay
	 * @eparam RESULT: the result of the PLAYER_MAY (TODO : this is bad since
	 * they might choose to pay and fail; this doesn't affect the outcome of any
	 * cards currently but it might in the future.)
	 */
	public static final EventType PLAYER_MAY_PAY_MANA = new EventType("PLAYER_MAY_PAY_MANA")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set playerParameter = parameters.get(Parameter.PLAYER);
			Player player = playerParameter.getOne(Player.class);
			player.mayActivateManaAbilities();

			Set costParameter = parameters.get(Parameter.COST);
			ManaPool cost = new ManaPool(costParameter.getAll(ManaSymbol.class));
			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));
			cost = cost.duplicate(number);

			EventFactory factory = new EventFactory(PAY_MANA, "Pay " + cost);
			factory.parameters.put(Parameter.CAUSE, Identity.instance(parameters.get(Parameter.CAUSE)));
			factory.parameters.put(Parameter.COST, Identity.instance(cost));
			factory.parameters.put(Parameter.PLAYER, Identity.instance(playerParameter));

			java.util.Map<Parameter, Set> mayPayParameters = new java.util.HashMap<Parameter, Set>();
			mayPayParameters.put(Parameter.PLAYER, playerParameter);
			mayPayParameters.put(Parameter.EVENT, new Set(factory));
			Event mayPay = createEvent(game, player + " may pay " + cost + ".", PLAYER_MAY, mayPayParameters);
			boolean ret = mayPay.perform(event, false);

			event.setResult(mayPay.getResultGenerator());
			return ret;
		}
	};
	/**
	 * @eparam CAUSE: what is asking the player to pay mana
	 * @eparam PLAYER: who is being asked to pay
	 * @eparam MANA: mana in addition to X that must be paid (for example, if
	 * the player may pay (X)(R), pass (R) here) [optional; default is nothing]
	 * @eparam RESULT: value of X if they paid, empty if they didn't
	 */
	public static final EventType PLAYER_MAY_PAY_X = new EventType("PLAYER_MAY_PAY_X")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set causeParameter = parameters.get(Parameter.CAUSE);
			GameObject cause = causeParameter.getOne(GameObject.class);

			Set playerParameter = parameters.get(Parameter.PLAYER);
			Player player = playerParameter.getOne(Player.class);
			player.mayActivateManaAbilities();
			player = player.getActual();

			Set manaParameter = parameters.get(Parameter.MANA);
			ManaPool additional = manaParameter == null ? new ManaPool() : new ManaPool(manaParameter.getAll(ManaSymbol.class));
			if(!player.pool.pays(game.actualState, additional))
			{
				event.setResult(Empty.set);
				return false;
			}

			int max = player.pool.size() - additional.size();
			boolean valid = false;
			while(!valid)
			{
				org.rnd.util.NumberRange range = new org.rnd.util.NumberRange(0, max);
				int X = player.chooseNumber(range, "Choose a value for X.");
				if(X == 0)
				{
					event.setResult(Empty.set);
					return true;
				}

				// Performing the PAY_MANA event will expand X, so we need to
				// make a separate ManaPool to attempt the payment with.
				ManaPool attemptCost = new ManaPool("X");
				if(parameters.containsKey(Parameter.MANA))
					attemptCost.addAll(additional);
				attemptCost.expandX(X, ManaSymbol.ManaType.COLORLESS);
				if(player.pool.pays(game.actualState, attemptCost))
				{
					// Performing the PAY_MANA event expands X using the value
					// of X of the cause.
					cause.getActual().setValueOfX(X);
					cause.getPhysical().setValueOfX(X);
					valid = true;
					event.setResult(new Set(X));
				}
			}

			ManaPool cost = new ManaPool("X");
			cost.addAll(additional);

			java.util.Map<Parameter, Set> payParameters = new java.util.HashMap<Parameter, Set>();
			payParameters.put(EventType.Parameter.CAUSE, causeParameter);
			payParameters.put(EventType.Parameter.PLAYER, playerParameter);
			payParameters.put(EventType.Parameter.COST, new Set(cost));
			Event pay = createEvent(game, "Pay (X)", PAY_MANA, payParameters);
			pay.perform(event, false);

			return true;
		}
	};
	/**
	 * 
	 * 701.27. Populate
	 * 
	 * 701.27a To populate means to choose a creature token you control and put
	 * a token onto the battlefield that's a copy of that creature token.
	 * 
	 * 701.27b If you control no creature tokens when instructed to populate,
	 * you won't put a token onto the battlefield.
	 * 
	 * @eparam CAUSE: what is causing the populate
	 * @eparam PLAYER: the player choosing
	 * @eparam RESULT: empty
	 */
	public static final EventType POPULATE = new EventType("POPULATE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			boolean ret = true;
			java.util.Set<Identified> all = Intersect.instance(Tokens.instance(), CreaturePermanents.instance(), ControlledBy.instance(Identity.instance(player))).evaluate(game, null).getAll(Identified.class);

			if(!all.isEmpty())
			{
				java.util.List<Identified> chosen = player.sanitizeAndChoose(game.actualState, 1, 1, all, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.POPULATE);

				for(Identified choice: chosen)
				{
					java.util.Map<Parameter, Set> copyParameters = new java.util.HashMap<Parameter, Set>();
					copyParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
					copyParameters.put(Parameter.CONTROLLER, new Set(player));
					copyParameters.put(Parameter.OBJECT, new Set(choice));
					if(!createEvent(game, "Create a token copy of " + choice + ".", EventType.CREATE_TOKEN_COPY, copyParameters).perform(event, false))
						ret = false;
				}
			}

			event.setResult(Identity.instance());
			return ret;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the proliferation
	 * @eparam PLAYER: the player choosing
	 * @eparam RESULT: empty
	 */
	public static final EventType PROLIFERATE = new EventType("PROLIFERATE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// 701.23. Proliferate
			//
			// 701.23a To proliferate means to choose any number of permanents
			// and/or players that have a counter, then give each exactly one
			// additional counter of a kind that permanent or player already
			// has.
			//
			// 701.23b If a permanent or player chosen this way has more than
			// one kind of counter, the player who is proliferating chooses
			// which kind of counter to add.

			Set hasCounters = Intersect.instance(Union.instance(Permanents.instance(), Players.instance()), HasCounters.instance()).evaluate(game, null);

			boolean ret = true;

			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			java.util.List<Identified> chosen = player.sanitizeAndChoose(game.actualState, 0, null, hasCounters.getAll(Identified.class), PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.PROLIFERATE);

			if(!chosen.isEmpty())
			{
				for(Identified i: chosen)
				{
					java.util.Set<Counter.CounterType> counters = new java.util.HashSet<Counter.CounterType>();
					if(i instanceof GameObject)
						for(Counter c: ((GameObject)i).counters)
							counters.add(c.getType());
					else if(i instanceof Player)
						for(Counter c: ((Player)i).counters)
							counters.add(c.getType());

					Counter.CounterType newCounter = null;
					if(counters.size() == 1)
						newCounter = counters.iterator().next();
					else
					{
						PlayerInterface.ChooseParameters<Counter.CounterType> chooseParameters = new PlayerInterface.ChooseParameters<Counter.CounterType>(1, 1, new java.util.LinkedList<Counter.CounterType>(counters), PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.PROLIFERATE_CHOOSECOUNTER);
						chooseParameters.whatForID = i.ID;
						java.util.List<Counter.CounterType> chosenType = player.choose(chooseParameters);
						if(!chosenType.isEmpty())
							newCounter = chosenType.iterator().next();
					}

					if(newCounter == null)
						ret = false;
					else
					{
						java.util.HashMap<Parameter, Set> putCounterParameters = new java.util.HashMap<Parameter, Set>();
						putCounterParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
						putCounterParameters.put(Parameter.COUNTER, new Set(newCounter));
						putCounterParameters.put(Parameter.OBJECT, new Set(i));
						Event putCounter = createEvent(game, "Put a " + newCounter + " counter on " + i + ".", PUT_COUNTERS, putCounterParameters);
						if(!putCounter.perform(event, false))
							ret = false;
					}
				}
			}

			event.setResult(Empty.set);
			return ret;
		}
	};
	/**
	 * @eparam CAUSE: what is putting the counter
	 * @eparam CHOICE: set of objects to choose one from
	 * @eparam COUNTER: the counterType being added
	 * @eparam PLAYER: who is choosing
	 * @eparam RESULT: results of the {@link EventType#PUT_COUNTERS} event(s)
	 */
	public static final EventType PUT_COUNTER_ON_CHOICE = new EventType("PUT_COUNTER_ON_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CHOICE;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int numberOfCards = 1;

			java.util.Set<GameObject> objects = parameters.get(Parameter.CHOICE).getAll(GameObject.class);

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				java.util.Collection<GameObject> choices = player.sanitizeAndChoose(game.actualState, numberOfCards, objects, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.PUT_COUNTER);
				if(choices.size() != numberOfCards)
					event.allChoicesMade = false;
				event.putChoices(player, choices);
			}
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean allReceivedCounters = event.allChoicesMade;
			Set cause = parameters.get(Parameter.CAUSE);
			Set counter = parameters.get(Parameter.COUNTER);
			Set one = new Set(1);
			Set result = new Set();

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				Set putOnThese = event.getChoices(player);

				java.util.Map<Parameter, Set> putCountersParameters = new java.util.HashMap<Parameter, Set>();
				putCountersParameters.put(Parameter.CAUSE, cause);
				putCountersParameters.put(Parameter.COUNTER, counter);
				putCountersParameters.put(Parameter.NUMBER, one);
				putCountersParameters.put(Parameter.OBJECT, putOnThese);
				Event putCounters = createEvent(game, player + " puts a " + counter + " counter on " + putOnThese + ".", PUT_COUNTERS, putCountersParameters);
				if(!putCounters.perform(event, false))
					allReceivedCounters = false;
				result.addAll(putCounters.getResult());
			}

			event.setResult(result);
			return allReceivedCounters;
		}
	};
	/**
	 * Do not use this event in {@link SimpleEventPattern}s. Use the
	 * {@link CounterPlacedPattern} instead.
	 * 
	 * @eparam CAUSE: what is putting the counter
	 * @eparam COUNTER: the counterTypes being added
	 * @eparam NUMBER: the number of the counter to put [optional; default is 1]
	 * @eparam OBJECT: the object to put the counters on
	 * @eparam RESULT: results of the {@link EventType#PUT_ONE_COUNTER} event(s)
	 */
	public static final EventType PUT_COUNTERS = new EventType("PUT_COUNTERS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<Counter.CounterType> counterTypes = parameters.get(Parameter.COUNTER).getAll(Counter.CounterType.class);
			java.util.Set<Identified> objects = parameters.get(Parameter.OBJECT).getAll(Identified.class);
			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));

			boolean allPlaced = true;
			Set result = new Set();

			for(Counter.CounterType counterType: counterTypes)
				for(Identified object: objects)
				{
					for(int i = 0; i < number; i++)
					{
						java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
						counterParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
						counterParameters.put(Parameter.COUNTER, new Set(counterType));
						counterParameters.put(Parameter.OBJECT, new Set(object));
						Event putCounter = createEvent(game, "Put a " + counterType + " on " + object + ".", EventType.PUT_ONE_COUNTER, counterParameters);
						boolean status = putCounter.perform(event, false);
						if(!status)
							allPlaced = false;
						result.addAll(putCounter.getResult());
					}
				}

			event.setResult(Identity.instance(result));

			return allPlaced;
		}
	};
	/**
	 * @eparam CAUSE: what is moving the objects
	 * @eparam CONTROLLER: who will control the objects
	 * @eparam ZONE: which zone to put the objects into
	 * @eparam OBJECT: the objects to move
	 * @eparam FACE_DOWN: if the object is to be put into the new zone with the
	 * face down status, a CopiableValues class defining the characteristics for
	 * that object to assume while face down (see
	 * {@link ZoneChange#faceDownCharacteristics})
	 * @eparam INDEX: where to insert the object if the zone is ordered
	 * [required if zone is ordered; not permitted otherwise] (See
	 * {@link ZoneChange#index})
	 * @eparam RESOLVING: if this parameter is present, it signifies that the
	 * movement being performed is the last step of a spell's resolution
	 * (usually, putting a permanent spell onto the battlefield or a
	 * nonpermanent spell into its owner's graveyard). cards should not ever set
	 * this parameter.
	 * @eparam RESULT: result of the {@link #MOVE_OBJECTS} event(s)
	 */
	public static final EventType PUT_IN_CONTROLLED_ZONE = new EventType("PUT_IN_CONTROLLED_ZONE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean allMoved = true;

			Set objects = parameters.get(Parameter.OBJECT);
			if(objects.isEmpty())
			{
				event.setResult(Empty.set);
				return true;
			}

			boolean resolvingParameterPresent = parameters.containsKey(Parameter.RESOLVING);

			boolean faceDownParameterPresent = parameters.containsKey(Parameter.FACE_DOWN);
			Set faceDownParameter = null;
			if(faceDownParameterPresent)
				faceDownParameter = parameters.get(Parameter.FACE_DOWN);

			Set cause = parameters.get(Parameter.CAUSE);
			Set controller = parameters.get(Parameter.CONTROLLER);
			Zone controlledZone = parameters.get(Parameter.ZONE).getOne(Zone.class);
			Set result = new Set();

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, cause);
			moveParameters.put(Parameter.TO, new Set(controlledZone));
			moveParameters.put(Parameter.OBJECT, objects);
			moveParameters.put(Parameter.CONTROLLER, controller);
			if(parameters.containsKey(Parameter.INDEX))
				moveParameters.put(Parameter.INDEX, parameters.get(Parameter.INDEX));
			if(resolvingParameterPresent)
				moveParameters.put(Parameter.RESOLVING, Empty.set);
			if(faceDownParameterPresent)
				moveParameters.put(Parameter.FACE_DOWN, faceDownParameter);

			Event moveEvent = createEvent(game, "Put " + objects + " onto " + controlledZone + ".", EventType.MOVE_OBJECTS, moveParameters);
			if(!moveEvent.perform(event, false))
				allMoved = false;

			result.addAll(moveEvent.getResult());

			event.setResult(result);
			return allMoved;
		}
	};
	/**
	 * @eparam CAUSE: what is moving the cards
	 * @eparam INDEX: where to put the cards 1 = top, 2 = second from top, 3 =
	 * third from top... -1 = bottom, -2 = second from bottom... [optional;
	 * default = top]
	 * @eparam OBJECT: cards being moved
	 * @eparam RESULT: results of the MOVE_OBJECTS event(s)
	 */
	public static final EventType PUT_INTO_GRAVEYARD = new EventType("PUT_INTO_GRAVEYARD")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean allMoved = true;

			java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			if(objects.isEmpty())
			{
				event.setResult(Empty.set);
				return true;
			}

			java.util.Map<Zone, Set> objectMap = new java.util.HashMap<Zone, Set>();
			for(GameObject object: objects)
			{
				Set mappedSet = null;
				Zone targetGraveyard = object.getOwner(game.actualState).getGraveyard(game.actualState);
				if(objectMap.containsKey(targetGraveyard))
					mappedSet = objectMap.get(targetGraveyard);
				else
					mappedSet = new Set();

				mappedSet.add(object);
				objectMap.put(targetGraveyard, mappedSet);
			}

			Set result = new Set();
			for(java.util.Map.Entry<Zone, Set> toEntry: objectMap.entrySet())
			{
				Zone to = toEntry.getKey();
				Set theseObjects = toEntry.getValue();

				java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
				moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				moveParameters.put(Parameter.TO, new Set(to));
				moveParameters.put(Parameter.OBJECT, theseObjects);

				Event moveEvent = createEvent(game, "Put " + theseObjects + " into " + to + ".", EventType.MOVE_OBJECTS, moveParameters);
				if(!moveEvent.perform(event, false))
					allMoved = false;

				result.addAll(moveEvent.getResult());
			}

			event.setResult(result);
			return allMoved;
		}
	};

	/**
	 * @eparam CAUSE: what is causing the bounce
	 * @eparam PERMANENT: what is being bounced
	 * @eparam RESULT: the zone changes
	 */
	public static final EventType PUT_INTO_HAND = new EventType("PUT_INTO_HAND")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PERMANENT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set permanents = parameters.get(Parameter.PERMANENT);
			java.util.Map<Player, Set> whoOwnsWhat = this.whoOwnsWhat(game.actualState, permanents);

			Set result = new Set();
			boolean allBounced = true;
			Set cause = parameters.get(Parameter.CAUSE);
			for(java.util.Map.Entry<Player, Set> ownedThings: whoOwnsWhat.entrySet())
			{
				Player owner = ownedThings.getKey();
				Set thesePermanents = ownedThings.getValue();

				java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
				moveParameters.put(Parameter.CAUSE, cause);
				moveParameters.put(Parameter.TO, new Set(owner.getHand(game.actualState)));
				moveParameters.put(Parameter.OBJECT, thesePermanents);
				Event move = createEvent(game, "Put " + thesePermanents + " into " + owner + "'s hand.", MOVE_OBJECTS, moveParameters);

				if(!move.perform(event, false))
					allBounced = false;
				result.addAll(move.getResult());
			}

			event.setResult(Identity.instance(result));

			return allBounced;
		}

		private java.util.Map<Player, Set> whoOwnsWhat(GameState state, Set permanents)
		{
			java.util.Map<Player, Set> whoOwnsWhat = new java.util.HashMap<Player, Set>();
			for(GameObject object: permanents.getAll(GameObject.class))
			{
				Player owner = object.getOwner(state);
				if(!whoOwnsWhat.containsKey(owner))
					whoOwnsWhat.put(owner, new Set());
				whoOwnsWhat.get(owner).add(object);
			}
			return whoOwnsWhat;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the bounce
	 * @eparam PLAYER: the players that are bouncing (and thus choosing)
	 * @eparam NUMBER: number of permanents to bounce
	 * @eparam CHOICE: set of permanents to choose from
	 * @eparam RESULT: the results of the BOUNCE events
	 */
	public static final EventType PUT_INTO_HAND_CHOICE = new EventType("PUT_INTO_HAND_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CHOICE;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			org.rnd.util.NumberRange requiredRange = getRange(parameters.get(Parameter.NUMBER));
			int required = requiredRange.getLower();

			if(required == 0)
				return true;

			int successes = required;
			Set cause = parameters.get(Parameter.CAUSE);
			Set cards = parameters.get(Parameter.CHOICE);

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				// reset this players successes to zero
				successes = 0;
				Set failedCards = new Set();

				while(!cards.isEmpty())
				{
					GameObject thisCard = cards.getOne(GameObject.class);
					cards.remove(thisCard);
					java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
					parameters.put(Parameter.CAUSE, cause);
					parameters.put(Parameter.CARD, new Set(thisCard));

					// if the player can bounce the card, increment successes.
					// otherwise, give other players the chance to bounce the
					// card.
					if(createEvent(game, player + " returns " + thisCard + " to owners hand.", PUT_INTO_HAND, newParameters).attempt(event))
						successes++;
					else
						failedCards.add(thisCard);

					if(successes == required)
						break;
				}

				// if the player couldn't bounce enough cards, then this is
				// impossible
				if(successes != required)
					return false;

				// next player is given the chance to bounce all cards this
				// player failed on
				cards.addAll(failedCards);
			}

			// return whether the last player was able to bounce enough
			return successes == required;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			org.rnd.util.NumberRange number = getRange(parameters.get(Parameter.NUMBER));
			java.util.Set<GameObject> choiceParameter = parameters.get(Parameter.CHOICE).getAll(GameObject.class);

			// get the player out of the parameter
			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				// offer the choices to the player
				java.util.Collection<GameObject> choices = player.sanitizeAndChoose(game.actualState, number.getLower(), number.getUpper(), choiceParameter, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.BOUNCE);
				if(!number.contains(choices.size()))
					event.allChoicesMade = false;
				event.putChoices(player, choices);
			}
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean allBounced = event.allChoicesMade;
			Set cause = parameters.get(Parameter.CAUSE);
			Set result = new Set();

			// get the player out of the parameter
			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				Set bounceThese = event.getChoices(player);

				// perform the bounce event
				java.util.Map<Parameter, Set> bounceParameters = new java.util.HashMap<Parameter, Set>();
				bounceParameters.put(Parameter.CAUSE, cause);
				bounceParameters.put(Parameter.PERMANENT, bounceThese);
				Event bounceEvent = createEvent(game, player + " returns " + bounceThese + " to owners hand.", PUT_INTO_HAND, bounceParameters);
				if(!bounceEvent.perform(event, false))
					allBounced = false;
				result.addAll(bounceEvent.getResult());
			}

			event.setResult(Identity.instance(result));
			return allBounced;
		}
	};
	/**
	 * @eparam CAUSE: what is moving the cards
	 * @eparam INDEX: where to insert the object (See {@link ZoneChange#index})
	 * @eparam OBJECT: cards being moved; can be cards owned by multiple players
	 * @eparam RANDOM: if present, cards will be put into libraries in a random
	 * order (and thus players won't be asked to order their cards)
	 * @eparam RESULT: results of the MOVE_OBJECT event(s)
	 */
	public static final EventType PUT_INTO_LIBRARY = new EventType("PUT_INTO_LIBRARY")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean allMoved = true;

			java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			if(objects.isEmpty())
			{
				event.setResult(Empty.set);
				return true;
			}

			java.util.Map<Zone, Set> objectMap = new java.util.HashMap<Zone, Set>();
			for(GameObject object: objects)
			{
				Zone targetLibrary = object.getOwner(game.actualState).getLibrary(game.actualState);
				if(!objectMap.containsKey(targetLibrary))
					objectMap.put(targetLibrary, new Set());
				objectMap.get(targetLibrary).add(object);
			}

			Set result = new Set();
			for(java.util.Map.Entry<Zone, Set> toEntry: objectMap.entrySet())
			{
				Zone to = toEntry.getKey();
				Set theseObjects = toEntry.getValue();

				java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
				moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				moveParameters.put(Parameter.TO, new Set(to));
				moveParameters.put(Parameter.OBJECT, theseObjects);
				if(parameters.containsKey(Parameter.INDEX))
					moveParameters.put(Parameter.INDEX, parameters.get(Parameter.INDEX));
				if(parameters.containsKey(Parameter.RANDOM))
					moveParameters.put(Parameter.RANDOM, Empty.set);

				Event moveEvent = createEvent(game, "Put " + theseObjects + " into " + to + ".", EventType.MOVE_OBJECTS, moveParameters);
				if(!moveEvent.perform(event, false))
					allMoved = false;

				result.addAll(moveEvent.getResult());
			}

			event.setResult(result);
			return allMoved;
		}
	};
	/**
	 * @eparam CAUSE: what is moving the object
	 * @eparam CONTROLLER: player who controls it on the battlefield [required
	 * when TO is the battlefield or the stack, prohibited any other time]
	 * @eparam OBJECT: the object to move (singular)
	 * @eparam SOURCE: the object it will be copying
	 * @eparam TYPE: Any {@link Type}s, {@link SuperType}s, or {@link SubType}s
	 * to add as part of the copying process
	 * @eparam TO: the zone to put it into/on top of
	 * @eparam RESULT: the result of the put onto the battlefield event
	 */
	public static final EventType PUT_INTO_ZONE_AS_A_COPY_OF = new EventType("PUT_INTO_ZONE_AS_A_COPY_OF")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			Set to = parameters.get(Parameter.TO);

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, cause);
			moveParameters.put(Parameter.TO, to);
			if(parameters.containsKey(Parameter.CONTROLLER))
				moveParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));
			moveParameters.put(Parameter.OBJECT, new Set(object));
			Event move = createEvent(game, "Move " + object + " to " + to, EventType.MOVE_OBJECTS, moveParameters);
			boolean ret = move.perform(event, false);
			if(!ret)
				return false;

			ZoneChange movement = move.getResult().getOne(ZoneChange.class);
			event.setResult(Identity.instance(movement));

			Set source = parameters.get(Parameter.SOURCE);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, NewObjectOf.instance(Identity.instance(movement)));
			part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, Identity.instance(source));
			if(parameters.containsKey(Parameter.TYPE))
				part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(parameters.get(Parameter.TYPE)));

			EventFactory copy = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, (object + " copies " + source));
			copy.parameters.put(EventType.Parameter.CAUSE, Identity.instance(cause));
			copy.parameters.put(EventType.Parameter.EFFECT, Identity.instance(part));
			copy.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
			movement.events.add(copy);

			return true;
		}
	};

	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * Do not use this event in {@link SimpleEventPattern}s. Use the
	 * {@link CounterPlacedPattern} instead.
	 * 
	 * @eparam CAUSE: what is putting the counter
	 * @eparam COUNTER: the counterType to add
	 * @eparam OBJECT: the object to put the counter on
	 * @eparam RESULT: the counter as it exists on the object
	 */
	public static final EventType PUT_ONE_COUNTER = new EventType("PUT_ONE_COUNTER")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Identified physicalObject = parameters.get(Parameter.OBJECT).getOne(Identified.class).getPhysical();
			Counter newCounter = new Counter(parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class), physicalObject.ID);
			if(physicalObject instanceof GameObject)
				((GameObject)physicalObject).counters.add(newCounter);
			else
				((Player)physicalObject).counters.add(newCounter);

			event.setResult(Identity.instance(newCounter));

			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam RESOLVING: if this parameter is present, it signifies that the
	 * movement being performed is the last step of a spell's resolution
	 * (usually, putting a permanent spell onto the battlefield or a
	 * nonpermanent spell into its owner's graveyard). cards should not ever set
	 * this parameter.
	 * @eparam RESULT: result of the {@link #PUT_IN_CONTROLLED_ZONE} event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD = new EventType("PUT_ONTO_BATTLEFIELD")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
			newParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			newParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));
			newParameters.put(Parameter.ZONE, new Set(game.physicalState.battlefield()));
			newParameters.put(Parameter.OBJECT, parameters.get(Parameter.OBJECT));
			if(parameters.containsKey(Parameter.RESOLVING))
				newParameters.put(Parameter.RESOLVING, Empty.set);

			Event move = createEvent(game, event.getName(), PUT_IN_CONTROLLED_ZONE, newParameters);
			boolean status = move.perform(event, false);
			event.setResult(move.getResultGenerator());
			return status;
		}
	};
	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam CHOICE: the things to choose from
	 * @eparam NUMBER: the number of things to choose (an integer)
	 * @eparam SOURCE: the object to store the choice on
	 * @eparam TYPE: the ChoiceType of the choice to be made, and a ChooseReason
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_AND_CHOOSE = new EventType("PUT_ONTO_BATTLEFIELD_AND_CHOOSE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.CONTROLLER).getOne(Player.class);
			int number = parameters.get(Parameter.NUMBER).getOne(Integer.class);
			Set choices = parameters.get(Parameter.CHOICE);
			PlayerInterface.ChoiceType type = parameters.get(Parameter.TYPE).getOne(PlayerInterface.ChoiceType.class);
			PlayerInterface.ChooseReason description = parameters.get(Parameter.TYPE).getOne(PlayerInterface.ChooseReason.class);
			java.util.List<Object> result = player.sanitizeAndChoose(game.actualState, number, choices, type, description);

			for(Linkable link: parameters.get(Parameter.SOURCE).getAll(Linkable.class))
			{
				Linkable physicalLink = link.getPhysical();
				for(Object o: result)
				{
					physicalLink.getLinkManager().addLinkInformation(o);
					link.getLinkManager().addLinkInformation(o);
				}
			}

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			moveParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));
			moveParameters.put(Parameter.OBJECT, parameters.get(Parameter.OBJECT));
			Event moveEvent = createEvent(game, "Put " + parameters.get(Parameter.OBJECT) + " onto the battlefield", EventType.PUT_ONTO_BATTLEFIELD, moveParameters);
			boolean ret = moveEvent.perform(event, false);

			event.setResult(moveEvent.getResultGenerator());

			return ret;
		}
	};
	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing(s) being put onto the battlefield
	 * @eparam RESOLVING: if this parameter is present, it signifies that the
	 * movement being performed is the last step of a spell's resolution
	 * (usually, putting a permanent spell onto the battlefield or a
	 * nonpermanent spell into its owner's graveyard). cards should not ever set
	 * this parameter.
	 * @eparam TARGET: the object to attach to
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_ATTACHED_TO = new EventType("PUT_ONTO_BATTLEFIELD_ATTACHED_TO")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject target = parameters.get(Parameter.TARGET).getOne(GameObject.class);

			if(target == null || target.isGhost())
				return false;

			Zone targetZone = target.getZone();

			if(targetZone == null || !targetZone.objects.contains(target) || targetZone.getPhysical() != game.physicalState.battlefield())
				return false;

			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject attachment: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				AttachableTo attachTo = parameters.get(Parameter.TARGET).getOne(AttachableTo.class);
				if(attachment.canAttachTo(game, attachTo))
				{
					Event putOntoBattlefield = createEvent(game, "Put " + parameters.get(Parameter.OBJECT) + " onto the battlefield.", PUT_ONTO_BATTLEFIELD, parameters);
					boolean status = putOntoBattlefield.perform(event, false);

					ZoneChange zoneChange = putOntoBattlefield.getResult().getOne(ZoneChange.class);

					EventFactory attach = new EventFactory(ATTACH, "Attach to target");
					attach.parameters.put(Parameter.OBJECT, NewObjectOf.instance(Identity.instance(zoneChange)));
					attach.parameters.put(Parameter.TARGET, Identity.instance(parameters.get(Parameter.TARGET)));

					zoneChange.events.add(attach);
					event.setResult(putOntoBattlefield.getResultGenerator());
					return status;
				}
			}

			event.setResult(Empty.set);
			return false;
		}
	};
	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam PLAYER: the player choosing the objects [optional; default is
	 * CONTROLLER]
	 * @eparam OBJECT: the thing being put onto the battlefield (one object
	 * only)
	 * @eparam CHOICE: the choices of things to attach to
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_ATTACHED_TO_CHOICE = new EventType("PUT_ONTO_BATTLEFIELD_ATTACHED_TO_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			Set controller = parameters.get(Parameter.CONTROLLER);

			for(GameObject choice: parameters.get(Parameter.CHOICE).getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
				putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
				putOntoBattlefieldParameters.put(Parameter.OBJECT, new Set(object));
				putOntoBattlefieldParameters.put(Parameter.CONTROLLER, controller);
				putOntoBattlefieldParameters.put(Parameter.TARGET, new Set(choice));
				Event putOntoBattlefield = createEvent(game, "Put " + object + " onto the battlefield attached to " + choice, PUT_ONTO_BATTLEFIELD_ATTACHED_TO, putOntoBattlefieldParameters);
				if(putOntoBattlefield.attempt(event))
					return true;
			}
			return false;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			Set controller = parameters.get(Parameter.CONTROLLER);
			Set chooser = controller;
			if(parameters.containsKey(Parameter.PLAYER))
				chooser = parameters.get(Parameter.PLAYER);
			Set choices = parameters.get(Parameter.CHOICE);

			java.util.List<?> chosen = chooser.getOne(Player.class).sanitizeAndChoose(game.actualState, 1, choices, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.ATTACH);

			java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
			putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
			putOntoBattlefieldParameters.put(Parameter.OBJECT, new Set(object));
			putOntoBattlefieldParameters.put(Parameter.CONTROLLER, controller);
			putOntoBattlefieldParameters.put(Parameter.TARGET, new Set(chosen));
			Event putOntoBattlefield = createEvent(game, "Put " + object + " onto the battlefield attached to " + chosen, PUT_ONTO_BATTLEFIELD_ATTACHED_TO, putOntoBattlefieldParameters);
			boolean status = putOntoBattlefield.perform(event, false);

			event.setResult(putOntoBattlefield.getResultGenerator());
			return status;
		}
	};
	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the choices of things to put onto the battlefield
	 * @eparam NUMBER: the number of choices to make (can be an integer or a
	 * {@link org.rnd.util.NumberRange} [optional; default is 1]
	 * @eparam PLAYER: the player choosing the objects [optional; default is
	 * CONTROLLER]
	 * @eparam RESULT: result of the {@link #PUT_ONTO_BATTLEFIELD} event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_CHOICE = new EventType("PUT_ONTO_BATTLEFIELD_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CONTROLLER;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set controller = parameters.get(Parameter.CONTROLLER);
			Set objects = parameters.get(Parameter.OBJECT);
			int number = getRange(parameters.get(Parameter.NUMBER)).getLower(0);

			int successes = 0;
			for(GameObject object: objects.getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
				putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
				putOntoBattlefieldParameters.put(Parameter.OBJECT, new Set(object));
				putOntoBattlefieldParameters.put(Parameter.CONTROLLER, controller);
				Event putOntoBattlefield = createEvent(game, "Put " + object + " onto the battlefield", PUT_ONTO_BATTLEFIELD, putOntoBattlefieldParameters);
				if(putOntoBattlefield.attempt(event))
				{
					successes++;
					if(successes == number)
						return true;
				}
			}

			return false;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<GameObject> choices = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			Player chooser;
			if(parameters.containsKey(Parameter.PLAYER))
				chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);
			else
				chooser = parameters.get(Parameter.CONTROLLER).getOne(Player.class);

			org.rnd.util.NumberRange number = getRange(parameters.get(Parameter.NUMBER));
			int lower = number.getLower(0);
			int upper = number.getUpper(choices.size());
			String choiceName;
			if(lower == upper)
			{
				if(1 == lower)
					choiceName = "Put a card onto the battlefield.";
				else
					choiceName = "Put " + org.rnd.util.NumberNames.get(lower) + " cards onto the battlefield.";
			}
			else if(lower == 0)
			{
				if(upper == 1)
					choiceName = "You may put a card onto the battlefield.";
				else
					choiceName = "Put up to " + org.rnd.util.NumberNames.get(upper) + " cards onto the battlefield.";
			}
			else
				choiceName = "Put between " + org.rnd.util.NumberNames.get(lower) + " and " + org.rnd.util.NumberNames.get(upper) + " cards onto the battlefield.";

			// offer the choices to the player
			PlayerInterface.ChooseReason reason = new PlayerInterface.ChooseReason(PlayerInterface.ChooseReason.GAME, choiceName, true);
			java.util.Collection<GameObject> chosen = chooser.sanitizeAndChoose(game.actualState, lower, upper, choices, PlayerInterface.ChoiceType.OBJECTS, reason);
			if(number.contains(chosen.size()))
				event.allChoicesMade = true;

			event.putChoices(chooser, chosen);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Player controller = parameters.get(Parameter.CONTROLLER).getOne(Player.class);

			Player chooser;
			if(parameters.containsKey(Parameter.PLAYER))
				chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);
			else
				chooser = controller;

			Set stuffToPutOntoBattlefield = event.getChoices(chooser);

			java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
			putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
			putOntoBattlefieldParameters.put(Parameter.CONTROLLER, new Set(controller));
			putOntoBattlefieldParameters.put(Parameter.OBJECT, stuffToPutOntoBattlefield);
			Event putOntoBattlefield = createEvent(game, "Put " + stuffToPutOntoBattlefield + " onto the battlefield", PUT_ONTO_BATTLEFIELD, putOntoBattlefieldParameters);

			boolean moveSuccess = putOntoBattlefield.perform(event, false);
			event.setResult(putOntoBattlefield.getResultGenerator());
			return event.allChoicesMade && moveSuccess;
		}

	};
	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the choices of things to put onto the battlefield
	 * @eparam NUMBER: the number of choices to make [optional; default is 1]
	 * @eparam PLAYER: the player choosing the objects [optional; default is
	 * CONTROLLER]
	 * @eparam TARGET: the object to attach to
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD_ATTACHED_TO event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_CHOICE_ATTACHED_TO = new EventType("PUT_ONTO_BATTLEFIELD_CHOICE_ATTACHED_TO")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CONTROLLER;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set controller = parameters.get(Parameter.CONTROLLER);
			Set objects = parameters.get(Parameter.OBJECT);
			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));
			Set target = parameters.get(Parameter.TARGET);

			int successes = 0;
			for(GameObject object: objects.getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
				putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
				putOntoBattlefieldParameters.put(Parameter.OBJECT, new Set(object));
				putOntoBattlefieldParameters.put(Parameter.CONTROLLER, controller);
				putOntoBattlefieldParameters.put(Parameter.TARGET, target);
				Event putOntoBattlefield = createEvent(game, "Put " + object + " onto the battlefield attached to " + target, PUT_ONTO_BATTLEFIELD_ATTACHED_TO, putOntoBattlefieldParameters);
				if(putOntoBattlefield.attempt(event))
				{
					successes++;
					if(successes == number)
						return true;
				}
			}

			return false;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Player controller = parameters.get(Parameter.CONTROLLER).getOne(Player.class);
			Set objects = parameters.get(Parameter.OBJECT);
			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));
			Player chooser = controller;
			if(parameters.containsKey(Parameter.PLAYER))
				chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);

			// offer the choices to the player
			java.util.List<GameObject> choices = chooser.sanitizeAndChoose(game.actualState, number, objects.getAll(GameObject.class), PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.PUT_ONTO_BATTLEFIELD);
			if(choices.size() == 0)
				return false;

			boolean allChosen = true;
			if(choices.size() != number)
				allChosen = false;

			Set stuffToPutOntoBattlefield = new Set(choices);
			Set target = parameters.get(Parameter.TARGET);

			java.util.Map<Parameter, Set> putOntoBattlefieldParameters = new java.util.HashMap<Parameter, Set>();
			putOntoBattlefieldParameters.put(Parameter.CAUSE, cause);
			putOntoBattlefieldParameters.put(Parameter.CONTROLLER, new Set(controller));
			putOntoBattlefieldParameters.put(Parameter.OBJECT, stuffToPutOntoBattlefield);
			putOntoBattlefieldParameters.put(Parameter.TARGET, target);
			Event putOntoBattlefield = createEvent(game, "Put " + stuffToPutOntoBattlefield + " onto the battlefield attached to " + target, PUT_ONTO_BATTLEFIELD_ATTACHED_TO, putOntoBattlefieldParameters);

			boolean moveSuccess = putOntoBattlefield.perform(event, false);
			event.setResult(putOntoBattlefield.getResultGenerator());
			return allChosen && moveSuccess;
		}
	};
	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_TAPPED = new EventType("PUT_ONTO_BATTLEFIELD_TAPPED")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Event putOntoBattlefield = createEvent(game, "Put " + parameters.get(Parameter.OBJECT) + " onto the battlefield.", PUT_ONTO_BATTLEFIELD, parameters);
			boolean status = putOntoBattlefield.perform(event, false);

			for(ZoneChange change: putOntoBattlefield.getResult().getAll(ZoneChange.class))
			{
				EventFactory tap = new EventFactory(TAP_PERMANENTS, event.getName());
				tap.parameters.put(EventType.Parameter.CAUSE, Identity.instance(parameters.get(Parameter.CAUSE)));
				tap.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(Identity.instance(change)));
				change.events.add(tap);
			}

			event.setResult(putOntoBattlefield.getResultGenerator());
			return status;
		}
	};
	/**
	 * @eparam ATTACKER: the attackingID to assign it [optional; default = the
	 * player chooses when this event performs]
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING = new EventType("PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Event putOntoBattlefield = createEvent(game, "Put " + parameters.get(Parameter.OBJECT) + " onto the battlefield.", PUT_ONTO_BATTLEFIELD, parameters);
			boolean status = putOntoBattlefield.perform(event, false);

			Player controller = parameters.get(Parameter.CONTROLLER).getOne(Player.class);

			int attackingID;
			java.util.Set<Identified> defenders;
			if(parameters.containsKey(Parameter.ATTACKER))
			{
				attackingID = parameters.get(Parameter.ATTACKER).getOne(Integer.class);
				defenders = null;
			}
			else
			{
				attackingID = 0;
				SetGenerator opponents = OpponentsOf.instance(Identity.instance(controller));
				SetGenerator controlledByOpponents = ControlledBy.instance(opponents);
				SetGenerator planeswalkers = HasType.instance(Type.PLANESWALKER);
				defenders = Union.instance(Intersect.instance(planeswalkers, controlledByOpponents), opponents).evaluate(game, null).getAll(Identified.class);
			}

			for(ZoneChange change: putOntoBattlefield.getResult().getAll(ZoneChange.class))
			{
				// old object since the object hasn't shown up in the new zone
				// yet
				GameObject attacker = game.actualState.get(change.oldObjectID);

				EventFactory tapped = new EventFactory(TAP_PERMANENTS, "Tapped");
				tapped.parameters.put(Parameter.CAUSE, CauseOf.instance(Identity.instance(change)));
				tapped.parameters.put(Parameter.OBJECT, NewObjectOf.instance(Identity.instance(change)));
				change.events.add(tapped);

				if(null != defenders)
				{
					PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(1, 1, PlayerInterface.ChoiceType.ATTACK_WHAT, PlayerInterface.ChooseReason.DECLARE_ATTACK_DEFENDER);
					chooseParameters.thisID = attacker.ID;
					attackingID = controller.sanitizeAndChoose(game.actualState, defenders, chooseParameters).get(0).ID;
				}

				EventFactory attacking = new EventFactory(SET_ATTACKING_ID, "Attacking");
				attacking.parameters.put(Parameter.OBJECT, NewObjectOf.instance(Identity.instance(change)));
				attacking.parameters.put(Parameter.ATTACKER, Identity.instance(attackingID));
				change.events.add(attacking);
			}

			event.setResult(putOntoBattlefield.getResultGenerator());
			return status;
		}
	};
	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam NUMBER: how many counters (default is 1)
	 * @eparam COUNTER: the counterTypes to add
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_TAPPED_WITH_COUNTERS = new EventType("PUT_ONTO_BATTLEFIELD_TAPPED_WITH_COUNTERS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Event putOntoBattlefield = createEvent(game, "Put " + parameters.get(Parameter.OBJECT) + " onto the battlefield.", PUT_ONTO_BATTLEFIELD, parameters);
			boolean status = putOntoBattlefield.perform(event, false);

			for(GameObject object: NewObjectOf.instance(putOntoBattlefield.getResultGenerator()).evaluate(game.actualState, null).getAll(GameObject.class))
				if(object.zoneID == game.actualState.battlefield().ID)
					object.getPhysical().setTapped(true);

			java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
			counterParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
			{
				Set numberParameter = parameters.get(Parameter.NUMBER);
				number = Sum.get(numberParameter);
				counterParameters.put(Parameter.NUMBER, numberParameter);
			}
			Set counterType = parameters.get(Parameter.COUNTER);
			counterParameters.put(Parameter.COUNTER, counterType);
			Set object = NewObjectOf.instance(putOntoBattlefield.getResultGenerator()).evaluate(game.actualState, null);
			counterParameters.put(Parameter.OBJECT, object);
			Event putCounters = createEvent(game, "Put " + number + " " + counterType.getOne(Counter.CounterType.class) + (number == 1 ? "" : "s") + " on " + object + ".", PUT_COUNTERS, counterParameters);
			boolean counterStatus = putCounters.perform(event, false);

			event.setResult(putOntoBattlefield.getResultGenerator());
			return status && counterStatus;
		}
	};
	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_TRANSFORMED = new EventType("PUT_ONTO_BATTLEFIELD_TRANSFORMED")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Event putOntoBattlefield = createEvent(game, "Put " + parameters.get(Parameter.OBJECT) + " onto the battlefield.", PUT_ONTO_BATTLEFIELD, parameters);
			boolean status = putOntoBattlefield.perform(event, false);

			for(ZoneChange change: putOntoBattlefield.getResult().getAll(ZoneChange.class))
			{
				EventFactory transform = new EventFactory(TRANSFORM_PERMANENT, event.getName());
				transform.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(Identity.instance(change)));
				change.events.add(transform);
			}

			event.setResult(putOntoBattlefield.getResultGenerator());
			return status;
		}
	};
	/**
	 * @eparam CAUSE: what is putting things into play
	 * @eparam OBJECT: what is being put into play
	 * @eparam RESULT: the zone changes
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL = new EventType("PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Map<Player, Set> ownerMap = new java.util.HashMap<Player, Set>();
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				Player owner = object.getOwner(game.actualState);
				if(ownerMap.containsKey(owner))
					ownerMap.get(owner).add(object);
				else
					ownerMap.put(owner, new Set(object));
			}

			boolean allMoved = true;
			Set cause = parameters.get(Parameter.CAUSE);
			Set result = new Set();
			for(java.util.Map.Entry<Player, Set> entry: ownerMap.entrySet())
			{
				Player owner = entry.getKey();
				Set move = entry.getValue();

				java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
				moveParameters.put(Parameter.CAUSE, cause);
				moveParameters.put(Parameter.CONTROLLER, new Set(owner));
				moveParameters.put(Parameter.OBJECT, move);
				Event putOntoBattlefield = createEvent(game, "Put " + move + " onto the battlefield.", PUT_ONTO_BATTLEFIELD, moveParameters);

				if(!putOntoBattlefield.perform(event, false))
					allMoved = false;
				result.addAll(putOntoBattlefield.getResult());
			}

			event.setResult(Identity.instance(result));
			return allMoved;
		}
	};
	/**
	 * @eparam CAUSE: what is putting it onto the battlefield
	 * @eparam CONTROLLER: player who controls it after it's put onto the
	 * battlefield
	 * @eparam OBJECT: the thing being put onto the battlefield
	 * @eparam NUMBER: how many counters (default is 1)
	 * @eparam COUNTER: the counterTypes to add
	 * @eparam RESULT: result of the PUT_ONTO_BATTLEFIELD event
	 */
	public static final EventType PUT_ONTO_BATTLEFIELD_WITH_COUNTERS = new EventType("PUT_ONTO_BATTLEFIELD_WITH_COUNTERS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Event putOntoBattlefield = createEvent(game, "Put " + parameters.get(Parameter.OBJECT) + " onto the battlefield.", PUT_ONTO_BATTLEFIELD, parameters);
			boolean moveStatus = putOntoBattlefield.perform(event, false);

			for(ZoneChange change: putOntoBattlefield.getResult().getAll(ZoneChange.class))
			{
				Set counterType = parameters.get(Parameter.COUNTER);
				int number = 1;
				if(parameters.containsKey(Parameter.NUMBER))
					number = Sum.get(parameters.get(Parameter.NUMBER));
				EventFactory putCounters = new EventFactory(PUT_COUNTERS, "Put " + number + " " + counterType.getOne(Counter.CounterType.class) + (number == 1 ? "" : "s") + " on " + parameters.get(Parameter.OBJECT).getAll(GameObject.class));
				putCounters.parameters.put(Parameter.CAUSE, Identity.instance(parameters.get(Parameter.CAUSE)));
				putCounters.parameters.put(Parameter.COUNTER, Identity.instance(counterType));
				putCounters.parameters.put(Parameter.NUMBER, Identity.instance(number));
				putCounters.parameters.put(Parameter.OBJECT, NewObjectOf.instance(Identity.instance(change)));
				change.events.add(putCounters);
			}

			event.setResult(putOntoBattlefield.getResultGenerator());
			return moveStatus;
		}
	};
	/**
	 * @eparam CAUSE: the reason its being regenerated
	 * @eparam OBJECT: the object being regenerated
	 * @eparam RESULT: the objects that were regenerated
	 */
	public static final EventType REGENERATE = new EventType("REGENERATE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// 701.11. Regenerate
			// 701.11a If the effect of a resolving spell or ability regenerates
			// a permanent, it creates a replacement effect that protects the
			// permanent the next time it would be destroyed this turn. In this
			// case, "Regenerate [permanent]" means "The next time [permanent]
			// would be destroyed this turn, instead remove all damage marked on
			// it and tap it. If it's an attacking or blocking creature, remove
			// it from combat."
			// 701.11b If the effect of a static ability regenerates a
			// permanent, it replaces destruction with an alternate effect each
			// time that permanent would be destroyed. In this case,
			// "Regenerate [permanent]" means "Instead remove all damage marked
			// on [permanent] and tap it. If it's an attacking or blocking
			// creature, remove it from combat."
			Set ret = new Set();
			Set removeFromCombat = new Set();

			Set objects = parameters.get(Parameter.OBJECT);
			for(GameObject object: objects.getAll(GameObject.class))
			{
				GameObject physicalObject = object.getPhysical();
				physicalObject.setDamage(0);

				if(object.getAttackingID() != -1 || !object.getBlockingIDs().isEmpty())
					removeFromCombat.add(object);

				ret.add(object);
			}

			java.util.Map<Parameter, Set> tapParameters = new java.util.HashMap<Parameter, Set>();
			tapParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			tapParameters.put(Parameter.OBJECT, objects);
			createEvent(game, "Tap " + objects, EventType.TAP_PERMANENTS, tapParameters).perform(event, false);

			if(!removeFromCombat.isEmpty())
			{
				java.util.Map<Parameter, Set> removeParameters = new java.util.HashMap<Parameter, Set>();
				removeParameters.put(Parameter.OBJECT, removeFromCombat);
				createEvent(game, "Remove " + removeFromCombat + " from combat", EventType.REMOVE_FROM_COMBAT, removeParameters).perform(event, false);
			}

			event.setResult(Identity.instance(ret));
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is removing the counter
	 * @eparam COUNTER: the counter types to remove [optional; default = all
	 * counter types]
	 * @eparam OBJECT: the object to remove the counters from
	 * @eparam RESULT: results of the REMOVE_ONE_COUNTER event(s)
	 */
	public static final EventType REMOVE_ALL_COUNTERS = new EventType("REMOVE_ALL_COUNTERS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();
			Set types = parameters.get(Parameter.COUNTER);

			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				java.util.Collection<Event> removeEvents = new java.util.LinkedList<Event>();
				for(Counter counter: object.counters)
				{
					if(types == null || types.contains(counter.getType()))
					{
						java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
						counterParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
						counterParameters.put(Parameter.COUNTER, new Set(counter.getType()));
						counterParameters.put(Parameter.OBJECT, new Set(object));
						Event removeCounter = createEvent(game, "Remove a " + counter.getType() + " from " + object + ".", EventType.REMOVE_ONE_COUNTER, counterParameters);
						removeEvents.add(removeCounter);
					}
				}

				for(Event e: removeEvents)
				{
					e.perform(event, false);
					result.addAll(e.getResult());
				}
			}

			event.setResult(Identity.instance(result));
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is removing the counter
	 * @eparam COUNTER: the counterTypes being removed
	 * @eparam NUMBER: the number of the counters to remove [optional; default
	 * is all]
	 * @eparam OBJECT: the object or player to remove the counters from
	 * @eparam RESULT: results of the {@link #REMOVE_ONE_COUNTER} event(s)
	 */
	public static final EventType REMOVE_COUNTERS = new EventType("REMOVE_COUNTERS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<Counter.CounterType> counterTypes = parameters.get(Parameter.COUNTER).getAll(Counter.CounterType.class);
			Set objects = parameters.get(Parameter.OBJECT);
			if(!parameters.containsKey(Parameter.NUMBER))
				return true;

			int number = Sum.get(parameters.get(Parameter.NUMBER));

			for(GameObject object: objects.getAll(GameObject.class))
				for(Counter.CounterType type: counterTypes)
					if(CountersOn.get(object, type).size() < number)
						return false;

			for(Player player: objects.getAll(Player.class))
				for(Counter.CounterType type: counterTypes)
					if(CountersOn.get(player, type).size() < number)
						return false;

			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();
			java.util.Set<Counter.CounterType> counterTypes = parameters.get(Parameter.COUNTER).getAll(Counter.CounterType.class);
			java.util.Set<Identified> objects = parameters.get(Parameter.OBJECT).getAll(Identified.class);
			Integer number = null;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));
			boolean allRemoved = true;

			for(Counter.CounterType counterType: counterTypes)
			{
				for(Identified object: objects)
				{
					int numCounters = (object.isPlayer() ? CountersOn.get((Player)object, counterType) : CountersOn.get((GameObject)object, counterType)).size();
					int toRemove = number == null ? numCounters : number;
					for(int numRemoved = 0; numRemoved < toRemove; numRemoved++)
					{
						java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
						counterParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
						counterParameters.put(Parameter.COUNTER, new Set(counterType));
						counterParameters.put(Parameter.OBJECT, new Set(object));
						Event removeCounter = createEvent(game, "Remove a " + counterType + " from " + object + ".", EventType.REMOVE_ONE_COUNTER, counterParameters);
						boolean status = removeCounter.perform(event, false);
						if(!status)
							allRemoved = false;
						result.addAll(removeCounter.getResult());
					}
				}
			}

			event.setResult(Identity.instance(result));
			return allRemoved;
		}
	};

	/**
	 * @eparam CAUSE: the cause
	 * @eparam COUNTER: the counters to choose from [optional when only one
	 * object is specified in OBJECT; defaults to all counters on OBJECT]
	 * @eparam NUMBER: the number of counters to choose (integer or number
	 * range) [optional; defaults to 1]
	 * @eparam PLAYER: the player choosing
	 * @eparam RESULT: the removed counters
	 */
	public static final EventType REMOVE_COUNTERS_CHOICE = new EventType("REMOVE_COUNTERS_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<Counter> counters = parameters.get(Parameter.COUNTER).getAll(Counter.class);

			org.rnd.util.NumberRange number = null;
			if(parameters.containsKey(Parameter.NUMBER))
				number = getRange(parameters.get(Parameter.NUMBER));
			else
				number = new org.rnd.util.NumberRange(1, 1);

			if(counters.size() < number.getLower(0))
				return false;
			return true;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			java.util.Set<Counter> counters = parameters.get(Parameter.COUNTER).getAll(Counter.class);
			org.rnd.util.NumberRange number = null;
			if(parameters.containsKey(Parameter.NUMBER))
				number = getRange(parameters.get(Parameter.NUMBER));
			else
				number = new org.rnd.util.NumberRange(1, 1);

			java.util.List<Counter> choice = new java.util.LinkedList<Counter>();
			if(counters.size() <= number.getLower(0))
			{
				choice.addAll(counters);
				if(counters.size() < number.getLower(0))
					event.allChoicesMade = false;
			}
			else
			{
				choice.addAll(player.choose(new PlayerInterface.ChooseParameters<Counter>(new Set(number), new java.util.LinkedList<Counter>(counters), PlayerInterface.ChoiceType.STRING, PlayerInterface.ChooseReason.CHOOSE_COUNTERS)));
				event.allChoicesMade = true;
				event.putChoices(player, choice);
			}
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Set choice = event.getChoices(player);

			boolean allRemoved = true;

			Set object = parameters.get(Parameter.OBJECT);
			Set result = new Set();
			for(Counter counter: choice.getAll(Counter.class))
			{
				java.util.Map<Parameter, Set> counterParameters = new java.util.HashMap<Parameter, Set>();
				counterParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				counterParameters.put(Parameter.COUNTER, new Set(counter.getType()));
				counterParameters.put(Parameter.OBJECT, new Set(game.actualState.get(counter.sourceID)));
				Event removeCounter = createEvent(game, "Remove a " + counter + " from " + object + ".", EventType.REMOVE_ONE_COUNTER, counterParameters);
				boolean status = removeCounter.perform(event, false);
				if(!status)
					allRemoved = false;
				result.addAll(removeCounter.getResult());
			}
			event.setResult(result);

			return allRemoved;
		}
	};
	/**
	 * @eparam CAUSE: the cause
	 * @eparam COUNTER: the counter type to remove
	 * @eparam OBJECT: the objects to select from (no need to make sure they
	 * have the counters of the right type on them, this event type will do
	 * that)
	 * @eparam NUMBER: the number of counters to remove (integer) [optional;
	 * defaults to 1]
	 * @eparam PLAYER: the player choosing
	 * @eparam RESULT: the removed counters
	 */
	public static final EventType REMOVE_COUNTERS_FROM_CHOICE = new EventType("REMOVE_COUNTERS_FROM_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Counter.CounterType type = parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class);
			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = parameters.get(Parameter.NUMBER).getOne(Integer.class);

			for(GameObject o: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				int counters = 0;
				for(Counter c: o.counters)
					if(c.getType() == type && ++counters >= number)
						return true;
			}
			return false;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Counter.CounterType type = parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class);
			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = parameters.get(Parameter.NUMBER).getOne(Integer.class);

			java.util.Set<GameObject> objects = new java.util.HashSet<GameObject>();
			for(GameObject o: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				int counters = 0;
				for(Counter c: o.counters)
					if(c.getType() == type && ++counters >= number)
					{
						objects.add(o);
						break;
					}
			}
			if(objects.isEmpty())
			{
				event.setResult(Empty.set);
				return false;
			}

			GameObject choice = player.sanitizeAndChoose(game.actualState, 1, objects, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.REMOVE_COUNTER_FROM).get(0);

			Event removeCounters = removeCounters(number, type, Identity.instance(choice), "Remove " + number + " " + type + "(s) from " + choice).createEvent(game, event.getSource());
			boolean ret = removeCounters.perform(event, false);
			event.setResult(removeCounters.getResult());
			return ret;
		}
	};

	/**
	 * @eparam OBJECT: GameObjects and Players to be removed from combat
	 * @eparam RESULT: The GameObjects/Players removed from combat
	 */
	public static final EventType REMOVE_FROM_COMBAT = new EventType("REMOVE_FROM_COMBAT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set results = new Set();

			Set objectsAndPlayers = parameters.get(Parameter.OBJECT);
			for(GameObject o: objectsAndPlayers.getAll(GameObject.class))
			{
				if(o.isGhost())
					continue;

				GameObject physical = o.getPhysical();
				physical.setAttackingID(-1);
				physical.setBlockedByIDs(null);
				physical.setBlockingIDs(new java.util.LinkedList<Integer>());
				physical.setDefendingIDs(new java.util.HashSet<Integer>());
				results.add(physical);
			}

			for(Player p: objectsAndPlayers.getAll(Player.class))
			{
				Player physical = p.getPhysical();
				physical.defendingIDs.clear();
				results.add(physical);
			}
			event.setResult(Identity.instance(results));
			return true;
		}
	};
	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * @eparam CAUSE: what is removing the counter
	 * @eparam COUNTER: the counterType being removed
	 * @eparam OBJECT: the object or player to remove the counter from
	 * @eparam RESULT: the removed counter
	 */
	public static final EventType REMOVE_ONE_COUNTER = new EventType("REMOVE_ONE_COUNTER")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Counter.CounterType counterType = parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class);
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			if(object == null)
				return CountersOn.get(parameters.get(Parameter.OBJECT).getOne(Player.class), counterType).size() > 0;
			return CountersOn.get(object, counterType).size() > 0;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Counter.CounterType counterType = parameters.get(Parameter.COUNTER).getOne(Counter.CounterType.class);

			Identified physicalObject = parameters.get(Parameter.OBJECT).getOne(Identified.class).getPhysical();

			java.util.Collection<Counter> counters;
			if(physicalObject.isGameObject())
				counters = ((GameObject)physicalObject).counters;
			else
				counters = ((Player)physicalObject).counters;
			Set result = new Set();

			boolean removed = false;
			boolean last = false;

			for(Counter counter: counters)
			{
				if(counter.getType().equals(counterType))
				{
					if(!removed)
					{
						last = true;
						removed = true;
						result.add(counter);
					}
					else if(last)
					{
						last = false;
						break;
					}
				}
			}

			counters.removeAll(result.getAll(Counter.class));

			event.setResult(Identity.instance(result));

			if(last)
			{
				java.util.Map<Parameter, Set> lastCounterParameters = new java.util.HashMap<Parameter, Set>();
				lastCounterParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				lastCounterParameters.put(Parameter.COUNTER, new Set(counterType));
				lastCounterParameters.put(Parameter.OBJECT, new Set(physicalObject));
				Event removeLastCounter = createEvent(game, "Removed last " + counterType + " from " + physicalObject + ".", EventType.REMOVED_LAST_COUNTER, lastCounterParameters);
				removeLastCounter.perform(event, false);
			}

			return removed;
		}
	};
	/**
	 * @eparam PLAYER: the player to remove counters from
	 * @eparam NUMBER: the number of counters to remove
	 * @eparam RESULT: the counters removed from the players
	 */
	public static final EventType REMOVE_POISON_COUNTERS = new EventType("REMOVE_POISON_COUNTERS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<Counter> counters = new java.util.HashSet<Counter>();
			int number = Sum.get(parameters.get(Parameter.NUMBER));
			java.util.Set<Player> players = parameters.get(Parameter.PLAYER).getAll(Player.class);
			for(Player player: players)
			{
				Player physical = player.getPhysical();
				java.util.Iterator<Counter> i = physical.counters.iterator();
				int removed = 0;
				while(i.hasNext() && (removed < number))
				{
					Counter c = i.next();
					if(Counter.CounterType.POISON == c.getType())
					{
						i.remove();
						counters.add(c);
						++removed;
					}
				}
			}
			event.setResult(Identity.instance(counters));
			if(counters.size() == players.size() * number)
				return true;

			return false;
		}
	};
	/**
	 * @eparam CAUSE: what removed the last counter
	 * @eparam COUNTER: the type of counter that was removed, and of which there
	 * are no more on the object
	 * @eparam OBJECT: the object the counter was removed from
	 * @eparam RESULT: empty
	 */
	public static final EventType REMOVED_LAST_COUNTER = new EventType("REMOVED_LAST_COUNTER")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);
			return true;
		}
	};

	/**
	 * @eparam OBJECT: any objects exempt from the procedure that restarts the
	 * game
	 */
	public static final EventType RESTART_THE_GAME = new EventType("RESTART_THE_GAME")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent(event, "Restart the game");
			for(Player player: game.actualState.players)
				player.alert(sanitized);

			GameObject oldCause = event.getSource();
			PlayerInterface firstPlayerComm = oldCause.getController(game.actualState).comm;
			GameState oldPhysical = game.physicalState;
			oldPhysical.currentPhase().steps.clear();
			oldPhysical.currentTurn().phases.clear();

			game.physicalState = new GameState(game);
			game.started = false;

			// Maps zone IDs in the old physical state to zone IDs in the new
			// physical state; objects that are exempt from the restart
			// procedure need to "stay" in their zone, so put the new copies of
			// those objects into the same zone they used to be in. Player IDs
			// are also represented here, in order to facilitate updating the
			// controller/owner of the ability that is currently resolving.
			java.util.Map<Integer, Integer> idMap = new java.util.HashMap<Integer, Integer>();
			idMap.put(-1, -1);
			idMap.put(oldPhysical.battlefield().ID, game.physicalState.battlefield().ID);
			idMap.put(oldPhysical.commandZone().ID, game.physicalState.commandZone().ID);
			idMap.put(oldPhysical.exileZone().ID, game.physicalState.exileZone().ID);
			idMap.put(oldPhysical.stack().ID, game.physicalState.stack().ID);

			Player firstPlayer = null;
			java.util.Map<PlayerInterface, Integer> playerMap = new java.util.HashMap<PlayerInterface, Integer>();
			for(Player p: oldPhysical.players)
			{
				Player newPlayer = game.addInterface(p.comm);
				playerMap.put(p.comm, newPlayer.ID);
				idMap.put(p.getGraveyardID(), newPlayer.getGraveyardID());
				idMap.put(p.getHandID(), newPlayer.getHandID());
				idMap.put(p.getLibraryID(), newPlayer.getLibraryID());
				idMap.put(p.getSideboardID(), newPlayer.getSideboardID());
				idMap.put(p.ID, newPlayer.ID);

				if(p.comm == firstPlayerComm)
					firstPlayer = newPlayer;
			}

			Set exemptFromRestart = parameters.get(Parameter.OBJECT);

			// 713.2. All _Magic_ cards involved in the game that was restarted
			// when it ended, including phased-out permanents and nontraditional
			// _Magic_ cards, are involved in the new game, even if those cards
			// were not originally involved in the restarted game. Ownership of
			// cards in the new game doesn't change, regardless of their
			// location when the new game begins.
			// Example: A player casts Living Wish, bringing a creature card
			// into the game from outside the game. Then that game is restarted.
			// The creature card will be part of that player's library when the
			// new game begins.
			// The above rule basically says we should leave the sideboard where
			// it is, but don't bother tracking where cards that left the
			// sideboard ended up since they should be shuffled into their
			// owner's library
			for(Player p: game.actualState.players)
				exemptFromRestart.addAll(p.getSideboard(game.actualState).objects);

			for(GameObject o: oldPhysical.getAllObjects())
			{
				if(o.isCard())
				{
					Player oldOwner = o.getOwner(oldPhysical);
					GameObject newObject = o.create(game);

					Player newOwner = game.physicalState.get(playerMap.get(oldOwner.comm));
					newObject.setOwner(newOwner);
					if(exemptFromRestart.contains(o))
						game.physicalState.<Zone>get(idMap.get(o.zoneID)).addToTop(newObject);
					else
						newOwner.getLibrary(game.physicalState).addToTop(newObject);
				}
			}

			// This event and its source need to exist in the new state so it
			// can finish resolving
			game.physicalState.put(event.clone(game.physicalState));
			GameObject newCause = oldCause.clone(game.physicalState);

			// Update some vital ID-based properties so generators in the new
			// state work properly
			newCause.controllerID = idMap.get(oldCause.controllerID);
			newCause.ownerID = idMap.get(oldCause.ownerID);

			game.startGame(firstPlayer);
			game.restarted = true;

			oldPhysical.clear();
			event.setResult(Identity.instance(game));
			return true;
		}
	};

	/**
	 * @eparam CAUSE: what is causing the reveal
	 * @eparam OBJECT: the objects to reveal, or zones to reveal all objects
	 * from
	 * @eparam EFFECT: a set generator saying when the reveal expires [optional;
	 * if unset, the fce will expire when the cause no longer exists (good
	 * enough for spells and abilities on the stack)]
	 * @eparam RESULT: the revealed object
	 */
	public static final EventType REVEAL = new EventType("REVEAL")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set objectParameter = parameters.get(Parameter.OBJECT);
			java.util.Set<GameObject> objects = objectParameter.getAll(GameObject.class);
			for(Zone z: objectParameter.getAll(Zone.class))
				objects.addAll(z.objects);
			Set ret = new Set();

			Set cause = parameters.get(Parameter.CAUSE);
			for(GameObject object: objects)
			{
				// Change the visibleTo property of the actual object. The FCE
				// will take over afterward.
				object = game.actualState.copyForEditing(object);
				for(Player player: game.actualState.players)
					object.setActualVisibility(player, true);
				ret.add(object);
			}

			for(Player player: game.actualState.players)
			{
				org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent.Reveal(event, objects, player);
				player.alert(sanitized);
			}

			SetGenerator expiration;
			if(parameters.containsKey(Parameter.EFFECT))
			{
				expiration = parameters.get(Parameter.EFFECT).getOne(SetGenerator.class);
				if(expiration == null)
					throw new UnsupportedOperationException(cause + ": REVEAL.EFFECT didn't contain a SetGenerator!");
			}
			else
				expiration = Not.instance(Exists.instance(Identity.instance(cause)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REVEAL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(ret));

			java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
			revealParameters.put(Parameter.CAUSE, cause);
			revealParameters.put(Parameter.EFFECT, new Set(part));
			revealParameters.put(Parameter.EXPIRES, new Set(expiration));
			createEvent(game, event.getName(), CREATE_FLOATING_CONTINUOUS_EFFECT, revealParameters).perform(event, false);

			event.setResult(Identity.instance(ret));
			return true;
		}
	};
	/**
	 * @eparam CAUSE: the cause of the reveal
	 * @eparam OBJECT: the objects to choose from
	 * @eparam EFFECT: a set generator saying when the reveal expires [optional;
	 * if set, this will create a reveal fce with that duration]
	 * @eparam NUMBER: the number of cards to choose (single number or a
	 * {@link org.rnd.util.NumberRange}) [optional; default is one]
	 * @eparam PLAYER: the player who chooses what to reveal
	 * @eparam RESULT: the revealed object
	 */
	public static final EventType REVEAL_CHOICE = new EventType("REVEAL_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));
			java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			return (objects.size() >= number);
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			org.rnd.util.NumberRange number;
			if(parameters.containsKey(Parameter.NUMBER))
				number = getRange(parameters.get(Parameter.NUMBER));
			else
				number = new org.rnd.util.NumberRange(1, 1);

			Player chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);
			java.util.List<GameObject> chosen = chooser.sanitizeAndChoose(game.actualState, number.getLower(0), number.getUpper(objects.size()), objects, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.CHOOSE_CARD_TO_REVEAL);
			if(!number.contains(chosen.size()))
				event.allChoicesMade = false;
			event.putChoices(chooser, chosen);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set choices = event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class));

			java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
			revealParameters.put(Parameter.CAUSE, cause);
			revealParameters.put(Parameter.OBJECT, choices);
			if(parameters.containsKey(Parameter.EFFECT))
				revealParameters.put(Parameter.EFFECT, parameters.get(Parameter.EFFECT));
			Event revealEvent = createEvent(game, "Reveal " + choices, EventType.REVEAL, revealParameters);

			boolean ret = revealEvent.perform(event, false);

			event.setResult(revealEvent.getResultGenerator());

			return ret;
		}
	};
	/**
	 * @eparam CAUSE: the cause of the reveal
	 * @eparam PLAYER: the players to randomly reveal cards
	 * @eparam NUMBER: the number of cards to randomly reveal
	 * @eparam RESULT: the combined results of REVEAL events
	 */
	public static final EventType REVEAL_RANDOM_FROM_HAND = new EventType("REVEAL_RANDOM_FROM_HAND")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();
			int number = Sum.get(parameters.get(Parameter.NUMBER));
			boolean ret = true;

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				java.util.List<GameObject> reveal = new java.util.LinkedList<GameObject>();
				java.util.List<GameObject> hand = new java.util.LinkedList<GameObject>(player.getHand(game.actualState).objects);
				java.util.Collections.shuffle(hand);

				if(hand.size() <= number)
					reveal.addAll(hand);
				else
					for(int i = 0; i < number; i++)
						reveal.add(hand.remove(0));

				if(reveal.size() < number)
					ret = false;

				java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
				revealParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				revealParameters.put(EventType.Parameter.OBJECT, new Set(reveal));
				Event revealEvent = createEvent(game, player + " reveals " + reveal, EventType.REVEAL, revealParameters);

				if(!revealEvent.perform(event, false))
					ret = false;

				result.addAll(revealEvent.getResult());
			}

			event.setResult(result);

			return ret;
		}
	};

	/**
	 * @eparam CAUSE: what is causing the sacrifice
	 * @eparam NUMBER: number of permanents to be sacrificed (integer or range)
	 * @eparam CHOICE: set of permanents to choose from; it is sufficient to use
	 * this as a filter -- e.g., sacrifice a land can be represented as simply
	 * HasType(LAND). This does NOT use the double-generator idiom.
	 * @eparam PLAYER: who is choosing and sacrificing
	 * @eparam RESULT: results of the {@link #SACRIFICE_PERMANENTS} event(s)
	 */
	public static final EventType SACRIFICE_CHOICE = new EventType("SACRIFICE_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CHOICE;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int required = getRange(parameters.get(Parameter.NUMBER)).getLower(0);
			if(required == 0)
				return true;

			java.util.Map<Player, Set> validChoices = this.validChoices(game, event, parameters);
			for(Player p: parameters.get(Parameter.PLAYER).getAll(Player.class))
				if(!validChoices.containsKey(p) || (validChoices.get(p).size() < required))
					return false;

			return true;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			org.rnd.util.NumberRange numberToSac = getRange(parameters.get(Parameter.NUMBER));

			java.util.Map<Player, Set> validChoices = this.validChoices(game, event, parameters);
			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				if(!validChoices.containsKey(player))
				{
					event.allChoicesMade = false;
					continue;
				}
				java.util.Set<GameObject> thisPlayersStuff = validChoices.get(player).getAll(GameObject.class);
				if(thisPlayersStuff.size() < numberToSac.getLower(0))
					event.allChoicesMade = false;

				int size = thisPlayersStuff.size();
				java.util.Collection<GameObject> choices = player.sanitizeAndChoose(game.actualState, numberToSac.getLower(0), numberToSac.getUpper(size), thisPlayersStuff, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.SACRIFICE);
				event.putChoices(player, choices);
			}
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set result = new Set();
			boolean allSacrificed = event.allChoicesMade;

			// get the player out of the parameters and figure out what they
			// control
			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				Set choices = event.getChoices(player);

				java.util.Map<Parameter, Set> sacParameters = new java.util.HashMap<Parameter, Set>();
				sacParameters.put(Parameter.CAUSE, cause);
				sacParameters.put(Parameter.PLAYER, new Set(player));
				sacParameters.put(Parameter.PERMANENT, choices);

				Event sacrifice = createEvent(game, player + " sacrifices " + choices + ".", SACRIFICE_PERMANENTS, sacParameters);
				if(!sacrifice.perform(event, false))
					allSacrificed = false;
				result.addAll(sacrifice.getResult());
			}
			event.setResult(Identity.instance(result));
			return allSacrificed;
		}

		private java.util.Map<Player, Set> validChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set players = parameters.get(Parameter.PLAYER);
			java.util.Map<Player, Set> ret = new java.util.HashMap<Player, Set>();
			for(GameObject o: parameters.get(Parameter.CHOICE).getAll(GameObject.class))
			{
				Player controller = o.getController(game.actualState);
				if(!players.contains(controller))
					continue;

				java.util.Map<Parameter, Set> attemptParameters = new java.util.HashMap<Parameter, Set>();
				attemptParameters.put(Parameter.CAUSE, cause);
				attemptParameters.put(Parameter.PLAYER, new Set(controller));
				attemptParameters.put(Parameter.PERMANENT, new Set(o));
				Event toAttempt = createEvent(game, "Sacrifice " + o, EventType.SACRIFICE_ONE_PERMANENT, attemptParameters);
				if(toAttempt.attempt(event))
				{
					if(ret.containsKey(controller))
						ret.get(controller).add(o);
					else
						ret.put(controller, new Set(o));
				}
			}
			return ret;
		}
	};
	/**
	 * NEVER, NEVER, NEVER INVOKE *_ONE_* EVENTS DIRECTLY! These event types are
	 * here solely for the purpose of being invoked by other events! DON'T
	 * INVOKE THESE!!!!
	 * 
	 * @eparam CAUSE: what is causing the sacrifice
	 * @eparam PLAYER: who is sacrificing
	 * @eparam PERMANENT: what is being sacrificed
	 * @eparam RESULT: the zone change
	 */
	public static final EventType SACRIFICE_ONE_PERMANENT = new EventType("SACRIFICE_ONE_PERMANENT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PERMANENT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject permanent = parameters.get(Parameter.PERMANENT).getOne(GameObject.class);
			Player controller = parameters.get(Parameter.PLAYER).getOne(Player.class);
			return permanent.isPermanent() && permanent.getController(game.actualState).equals(controller);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			if(!this.attempt(game, event, parameters))
			{
				event.setResult(Empty.set);
				return false;
			}

			GameObject permanent = parameters.get(Parameter.PERMANENT).getOne(GameObject.class);
			Set permanentSet = new Set(permanent);
			Zone graveyard = permanent.getOwner(game.actualState).getGraveyard(game.actualState);

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			moveParameters.put(Parameter.TO, new Set(graveyard));
			moveParameters.put(Parameter.OBJECT, permanentSet);

			Event move = createEvent(game, "Put " + permanent + " into " + graveyard + ".", MOVE_OBJECTS, moveParameters);
			boolean status = move.perform(event, false);

			event.setResult(move.getResultGenerator());
			return status;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the sacrifice
	 * @eparam PLAYER: who is sacrificing (singular) (not redundant, as an
	 * attempt to sacrifice a permanent you don't control should fail)
	 * @eparam PERMANENT: the permanents being sacrificed
	 * @eparam RESULT: results of the {@link #SACRIFICE_ONE_PERMANENT} event(s)
	 */
	public static final EventType SACRIFICE_PERMANENTS = new EventType("SACRIFICE_PERMANENTS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PERMANENT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int battlefield = game.actualState.battlefield().ID;
			int controller = parameters.get(Parameter.PLAYER).getOne(Player.class).ID;

			for(GameObject object: parameters.get(Parameter.PERMANENT).getAll(GameObject.class))
			{
				if(object.isGhost() || object.controllerID != controller)
					return false;

				Zone zone = object.getZone();

				if(zone == null || zone.ID != battlefield)
					return false;
			}
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set result = new Set();
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			int playerID = player.ID;

			boolean allSacrificed = true;
			for(GameObject permanent: parameters.get(Parameter.PERMANENT).getAll(GameObject.class))
			{
				if(permanent.controllerID != playerID)
					continue;

				java.util.Map<Parameter, Set> sacParameters = new java.util.HashMap<Parameter, Set>();
				sacParameters.put(Parameter.CAUSE, cause);
				sacParameters.put(Parameter.PLAYER, new Set(player));
				sacParameters.put(Parameter.PERMANENT, new Set(permanent));
				Event sacrifice = createEvent(game, permanent.getActual().getOwner(game.actualState) + " sacrifices " + permanent + ".", EventType.SACRIFICE_ONE_PERMANENT, sacParameters);
				if(!sacrifice.perform(event, false))
					allSacrificed = false;
				result.addAll(sacrifice.getResult());
			}
			event.setResult(Identity.instance(result));
			return allSacrificed;
		}
	};
	/**
	 * 701.17. Scry
	 * 
	 * 701.17a To "scry N" means to look at the top N cards of your library, put
	 * any number of them on the bottom of your library in any order, and put
	 * the rest on top of your library in any order.
	 * 
	 * @eparam CAUSE: what is letting the player scry
	 * @eparam PLAYER: the player scrying
	 * @eparam NUMBER: the number of objects to look at
	 * @eparam RESULT: empty
	 */
	public static final EventType SCRY = new EventType("SCRY")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int num = Sum.get(parameters.get(Parameter.NUMBER));
			if(num < 0)
				num = 0;

			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Zone library = player.getLibrary(game.actualState);

			Set topCards = TopCards.instance(num, Identity.instance(library)).evaluate(game, null);

			java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
			lookParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			lookParameters.put(Parameter.OBJECT, topCards);
			lookParameters.put(Parameter.PLAYER, new Set(player));
			Event lookEvent = createEvent(game, "Look at the top " + num + " cards of your library.", EventType.LOOK, lookParameters);
			lookEvent.perform(event, true);

			player = player.getActual();
			library = library.getActual();

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			moveParameters.put(Parameter.NUMBER, new Set(new org.rnd.util.NumberRange(0, num)));
			moveParameters.put(Parameter.FROM, new Set(library));
			moveParameters.put(Parameter.TO, new Set(library));
			moveParameters.put(Parameter.OBJECT, topCards);
			moveParameters.put(Parameter.CHOICE, new Set(PlayerInterface.ChooseReason.SCRY_TO_BOTTOM));
			moveParameters.put(Parameter.INDEX, new Set(-1));
			moveParameters.put(Parameter.PLAYER, new Set(player));
			Event moveEvent = createEvent(game, "Put any number of them on the bottom of your library.", EventType.MOVE_CHOICE, moveParameters);
			moveEvent.perform(event, true);

			player = player.getActual();
			library = library.getActual();

			java.util.Map<Parameter, Set> reorderParameters = new java.util.HashMap<Parameter, Set>();
			reorderParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			reorderParameters.put(Parameter.OBJECT, topCards);
			reorderParameters.put(Parameter.TO, new Set(library));
			reorderParameters.put(Parameter.INDEX, new Set(1));
			reorderParameters.put(Parameter.PLAYER, new Set(player));
			Event reorderEvent = createEvent(game, "Put the rest on top of your library in any order.", EventType.MOVE_OBJECTS, reorderParameters);
			reorderEvent.perform(event, true);

			event.setResult(Empty.set);

			return true;
		}
	};
	/**
	 * 701.14. Search<br>
	 * <br>
	 * 701.14a To search for a card in a zone, look at all cards in that zone
	 * (even if it's a hidden zone) and find a card that matches the given
	 * description.<br>
	 * <br>
	 * 701.14b If a player is searching a hidden zone for cards with a stated
	 * quality, such as a card with a certain card type or color, that player
	 * isn't required to find some or all of those cards even if they're present
	 * in that zone.<br>
	 * <br>
	 * 701.14c If a player is searching a hidden zone simply for a quantity of
	 * cards, such as "a card" or "three cards," that player must find that many
	 * cards (or as many as possible, if the zone doesn't contain enough cards).<br>
	 * <br>
	 * 701.14d If the effect that contains the search instruction doesn't also
	 * contain instructions to reveal the found card(s), then they're not
	 * revealed.<br>
	 * <br>
	 * Despite 701.14d, this event type will reveal the chosen cards if the TYPE
	 * parameter is present. This is for two reasons: 1. To prevent card writers
	 * from needing to explicitly reveal those cards (since every effect that
	 * specifies a restriction also says to reveal the searched-for cards), and
	 * 2. because we're lazy and there used to be a rule saying to reveal the
	 * chosen cards if there was a restriction on the search.<br>
	 * <br>
	 * If you need to do a {@link EventType#SHUFFLE} after searching, you need
	 * to do it manually.<br>
	 * <br>
	 * To trigger from a search, reference this event type. To prohibit or
	 * replace a search, do not reference this event type; instead reference
	 * {@link EventType#SEARCH_MARKER}.
	 * 
	 * @eparam CAUSE: the reason for the search
	 * @eparam PLAYER: the player searching
	 * @eparam NUMBER: the number of objects to find (can be a range)
	 * @eparam CARD: the cards or zones to search through (any number of zones
	 * (and cards from different zones) is supported)
	 * @eparam TYPE: the restriction on cards to find [optional; default is all]
	 * (requires double generator idiom)
	 * @eparam RESULT: the objects found and the {@link Zone}s searched
	 */
	public static final EventType SEARCH = new EventType("SEARCH")
	{
		// SEARCH_FOR_ALL_AND_PUT_INTO breaks the restriction on CARD -- it
		// specifies cards from multiple zones. Card writers are not allowed to
		// break this restriction.
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		private boolean zoneIsHidden(java.util.Set<Zone> publicZones, java.util.Set<Zone> hiddenZones, Zone zone, Player player)
		{
			if(publicZones.contains(zone))
				return false;
			if(hiddenZones.contains(zone))
				return true;
			for(Integer playerID: zone.visibleTo)
				if(playerID != player.ID)
				{
					publicZones.add(zone);
					return false;
				}
			hiddenZones.add(zone);
			return true;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cards = parameters.get(Parameter.CARD);
			if(cards.isEmpty())
				return true;

			Set player = parameters.get(Parameter.PLAYER);
			java.util.Set<Zone> zones = cards.getAll(Zone.class);
			for(Zone zone: zones)
			{
				java.util.Map<Parameter, Set> testParameters = new java.util.HashMap<Parameter, Set>();
				testParameters.put(Parameter.PLAYER, player);
				testParameters.put(Parameter.CARD, new Set(zone));
				Event test = createEvent(game, "Search " + zone + "?", SEARCH_MARKER, testParameters);
				cards.remove(zone);
				if(!test.perform(event, false))
					return false;
			}
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			Set cards = parameters.get(Parameter.CARD);

			if(cards.isEmpty())
			{
				event.setResult(Empty.set);
				return true;
			}

			java.util.Set<Zone> zones = cards.getAll(Zone.class);
			for(Zone zone: zones)
			{
				java.util.Map<Parameter, Set> testParameters = new java.util.HashMap<Parameter, Set>();
				testParameters.put(Parameter.PLAYER, new Set(player));
				testParameters.put(Parameter.CARD, new Set(zone));
				Event test = createEvent(game, "Search " + zone + "?", SEARCH_MARKER, testParameters);
				cards.remove(zone);
				if(test.perform(event, false))
					cards.addAll(test.getResult());
			}
			zones = cards.getAll(Zone.class);

			java.util.Set<Zone> publicZones = new java.util.HashSet<Zone>();
			java.util.Set<Zone> hiddenZones = new java.util.HashSet<Zone>();
			java.util.Set<GameObject> publicObjects = new java.util.HashSet<GameObject>();
			for(Zone zone: zones)
			{
				cards.addAll(zone.objects);

				if(!zoneIsHidden(publicZones, hiddenZones, zone, player))
					publicObjects.addAll(zone.objects);
			}

			for(GameObject o: cards.getAll(GameObject.class))
			{
				Zone zone = o.getZone();
				zones.add(zone);
				if(!zoneIsHidden(publicZones, hiddenZones, zone, player))
					publicObjects.add(o);
			}
			Set result = new Set(zones);

			boolean multipleZones = (zones.size() > 1);

			java.util.Map<Parameter, Set> lookParameters = new java.util.HashMap<Parameter, Set>();
			lookParameters.put(Parameter.CAUSE, cause);
			lookParameters.put(Parameter.OBJECT, cards);
			lookParameters.put(Parameter.PLAYER, new Set(player));
			Event lookEvent = createEvent(game, player + " looks at " + cards, EventType.LOOK, lookParameters);
			lookEvent.perform(event, false);

			boolean searchRestricted = false;
			if(parameters.containsKey(Parameter.TYPE))
			{
				searchRestricted = true;
				cards = Intersect.get(parameters.get(Parameter.TYPE).getOne(SetGenerator.class).evaluate(game, cause.getOne(Identified.class)), cards);
				publicObjects.retainAll(cards);
			}

			org.rnd.util.NumberRange num = getRange(parameters.get(Parameter.NUMBER));
			// if we have multiple zones, all of them are hidden (this event
			// will have been called from SEARCH_FOR_ALL_AND_PUT_INTO which only
			// passes hidden zones)
			Integer min = null;
			if(searchRestricted || multipleZones)
			{
				// If every zone is hidden then the minimum is zero.
				if(publicZones.isEmpty())
					num = new org.rnd.util.NumberRange(0, num.getUpper());
				else
				{
					min = num.getLower();
					if(min != null && min.intValue() > publicObjects.size())
						num = new org.rnd.util.NumberRange(publicObjects.size(), num.getUpper());
				}
			}

			java.util.List<GameObject> choices = null;
			PlayerInterface.ChooseReason reason = new PlayerInterface.ChooseReason(PlayerInterface.ChooseReason.GAME, event.getName(), false);

			int lower = num.getLower(0);
			int upper = num.getUpper(cards.size());

			boolean valid = false;
			do
			{
				choices = player.sanitizeAndChoose(game.actualState, lower, upper, cards.getAll(GameObject.class), PlayerInterface.ChoiceType.OBJECTS, reason);

				valid = true;

				// In the situation where you're searching both hidden and
				// public zones (min is not null), and you choose less than the
				// originally requested minimum (choices.size() < min), then
				// force the player to rechoose if they did not pick all
				// available public objects.
				if(min != null && choices.size() < min.intValue())
				{
					if(!choices.containsAll(publicObjects))
						valid = false;
				}
			}
			while(!valid);

			result.addAll(choices);
			if(searchRestricted)
			{
				java.util.Map<EventType.Parameter, Set> revealParameters = new java.util.HashMap<EventType.Parameter, Set>();
				revealParameters.put(Parameter.CAUSE, new Set(cause));
				revealParameters.put(Parameter.OBJECT, new Set(choices));
				createEvent(game, "Reveal " + choices, EventType.REVEAL, revealParameters).perform(event, false);
			}

			event.setResult(Identity.instance(result));

			return true;
		}
	};
	/**
	 * Search [zones] for all cards matching [condition] and put them in [zone].
	 * E.g. {@link org.rnd.jmagic.cards.CranialExtraction}.
	 * 
	 * @eparam CAUSE: the reason for the search
	 * @eparam PLAYER: the player searching
	 * @eparam ZONE: the zones to search through
	 * @eparam TYPE: the restriction on cards to find (requires double generator
	 * idiom)
	 * @eparam TO: where to put the cards after they're found
	 * @eparam RESULT: the results of the PLAYER_CHOOSE event
	 */
	public static final EventType SEARCH_FOR_ALL_AND_PUT_INTO = new EventType("SEARCH_FOR_ALL_AND_PUT_INTO")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject cause = parameters.get(Parameter.CAUSE).getOne(GameObject.class);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			java.util.Set<Zone> zones = parameters.get(Parameter.ZONE).getAll(Zone.class);
			Set cards = new Set();
			for(Zone zone: zones)
				cards.addAll(zone.objects);

			SetGenerator restriction = parameters.get(Parameter.TYPE).getOne(SetGenerator.class);
			Set canBeFound = restriction.evaluate(game, event.getSource());

			// All cards in public zones must be chosen.
			// 400.2. ... Graveyard, battlefield, stack, exile, ante, and
			// command are public zones. ...
			Set toPut = new Set();
			for(GameObject card: cards.getAll(GameObject.class))
			{
				Zone zone = card.getZone();
				if(zone.isGraveyard() //
						|| game.actualState.stack().equals(zone) //
						|| game.actualState.battlefield().equals(zone) //
						|| game.actualState.exileZone().equals(zone) //
						|| game.actualState.commandZone().equals(zone)) //
				{
					if(canBeFound.contains(card))
						toPut.add(card);
					zones.remove(zone);
				}
			}

			// Cards in other zones don't need to be found, even if the entire
			// zone is revealed:
			// 400.2. ... Library and hand are hidden zones, even if all the
			// cards in one such zone happen to be revealed.
			java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
			searchParameters.put(Parameter.CAUSE, new Set(cause));
			searchParameters.put(Parameter.PLAYER, new Set(player));
			searchParameters.put(Parameter.NUMBER, new Set(new org.rnd.util.NumberRange(0, null)));
			searchParameters.put(Parameter.CARD, new Set(zones));
			searchParameters.put(Parameter.TYPE, new Set(restriction));
			Event search = createEvent(game, player + " searches " + zones, EventType.SEARCH, searchParameters);
			search.perform(event, false);

			toPut.addAll(search.getResult().getAll(GameObject.class));

			Set result = new Set();
			Zone to = parameters.get(Parameter.TO).getOne(Zone.class);
			if(!toPut.isEmpty())
			{
				java.util.Map<EventType.Parameter, Set> moveParameters = new java.util.HashMap<EventType.Parameter, Set>();
				moveParameters.put(Parameter.CAUSE, new Set(cause));
				moveParameters.put(Parameter.OBJECT, toPut);
				moveParameters.put(Parameter.TO, new Set(to));
				Event move = createEvent(game, "Put " + toPut + " into " + to, EventType.MOVE_OBJECTS, moveParameters);
				move.perform(event, false);
				result.addAll(move.getResult());
			}

			event.setResult(Identity.instance(result));
			return true;
		}
	};
	/**
	 * @eparam CAUSE: the reason for the search
	 * @eparam CONTROLLER: who controls the object after it moves [required when
	 * TO is the stack or the battlefield; prohibited when TO is anything else]
	 * @eparam INDEX: where to insert the object if the zone is ordered
	 * [required when the zone is an ordered zone; not permitted otherwise] (See
	 * {@link ZoneChange#index})
	 * @eparam PLAYER: the player searching
	 * @eparam TARGET: the player whose library will be searched [optional;
	 * default is PLAYER]
	 * @eparam NUMBER: the number of objects to find (can be a NumberRange)
	 * @eparam TO: the zone to put the cards found
	 * @eparam TAPPED: if present, and TO is the battlefield, the object is put
	 * onto the battlefield tapped
	 * @eparam TYPE: the restriction on cards to find [optional; default is all]
	 * (requires double generator idiom)
	 * @eparam HIDDEN: if present, is passed on to MOVE_OBJECTS [optional]
	 * @eparam RESULT: the new object
	 */
	public static final EventType SEARCH_LIBRARY_AND_PUT_INTO = new EventType("SEARCH_LIBRARY_AND_PUT_INTO")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Player target = player;
			if(parameters.containsKey(Parameter.TARGET))
				target = parameters.get(Parameter.TARGET).getOne(Player.class);

			org.rnd.util.NumberRange num = getRange(parameters.get(Parameter.NUMBER));
			Zone library = target.getLibrary(game.actualState);

			java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
			searchParameters.put(Parameter.CAUSE, cause);
			searchParameters.put(Parameter.PLAYER, new Set(player));
			searchParameters.put(Parameter.NUMBER, new Set(num));
			searchParameters.put(Parameter.CARD, new Set(library));
			if(parameters.containsKey(Parameter.TYPE))
				searchParameters.put(Parameter.TYPE, parameters.get(Parameter.TYPE));

			int lower = num.getLower(0);
			int upper = num.getUpper(library.objects.size());
			String searchName;
			if(lower == upper)
				searchName = "Search your library for " + lower + " cards";
			else
				searchName = "Search your library for " + lower + " to " + upper + " cards";
			Event searchEvent = createEvent(game, searchName, EventType.SEARCH, searchParameters);
			searchEvent.perform(event, true);

			java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
			shuffleParameters.put(Parameter.CAUSE, cause);
			shuffleParameters.put(Parameter.PLAYER, new Set(target));

			target = target.getActual();

			Set object = searchEvent.getResult();
			Set to = parameters.get(Parameter.TO);
			boolean ret;
			if(to.getOne(Zone.class).equals(game.actualState.battlefield()) && parameters.containsKey(Parameter.TAPPED))
			{
				java.util.Map<Parameter, Set> ontoBattlefieldTappedParameters = new java.util.HashMap<Parameter, Set>();
				ontoBattlefieldTappedParameters.put(Parameter.CAUSE, cause);
				ontoBattlefieldTappedParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));
				ontoBattlefieldTappedParameters.put(Parameter.OBJECT, object);
				Event putOntoBattlefieldTapped = createEvent(game, "Put " + object + " onto the battlefield tapped", EventType.PUT_ONTO_BATTLEFIELD_TAPPED, ontoBattlefieldTappedParameters);

				ret = putOntoBattlefieldTapped.perform(event, true);
				event.setResult(NewObjectOf.instance(putOntoBattlefieldTapped.getResultGenerator()).evaluate(game, null));
			}
			else
			{
				ret = true;
				Set result = new Set();
				java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
				moveParameters.put(Parameter.CAUSE, cause);
				moveParameters.put(Parameter.TO, to);
				if(parameters.containsKey(Parameter.CONTROLLER))
					moveParameters.put(Parameter.CONTROLLER, parameters.get(Parameter.CONTROLLER));
				if(parameters.containsKey(Parameter.INDEX))
					moveParameters.put(Parameter.INDEX, parameters.get(Parameter.INDEX));
				if(parameters.containsKey(Parameter.HIDDEN))
					moveParameters.put(Parameter.HIDDEN, parameters.get(Parameter.HIDDEN));
				moveParameters.put(Parameter.OBJECT, object);

				Event moveEvent = createEvent(game, "Put the object into", EventType.MOVE_OBJECTS, moveParameters);
				ret = moveEvent.perform(event, true) && ret;
				result.addAll(NewObjectOf.instance(moveEvent.getResultGenerator()).evaluate(game, null));

				event.setResult(result);
			}

			Event shuffleEvent = createEvent(game, target + " shuffles their library", EventType.SHUFFLE_LIBRARY, shuffleParameters);
			shuffleEvent.perform(event, true);

			return ret;
		}
	};
	/**
	 * @eparam CAUSE: what is causing the search
	 * @eparam PLAYER: who is searching
	 * @eparam TYPE: setgenerator restricting what cards can be chosen
	 * [optional; default is all] (requires double generator idiom)
	 * @eparam RESULT: empty
	 */
	public static final EventType SEARCH_LIBRARY_AND_PUT_ON_TOP = new EventType("SEARCH_LIBRARY_AND_PUT_ON_TOP")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set player = parameters.get(Parameter.PLAYER);

			java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
			searchParameters.put(Parameter.CAUSE, cause);
			searchParameters.put(Parameter.PLAYER, player);
			searchParameters.put(Parameter.NUMBER, ONE);
			searchParameters.put(Parameter.CARD, new Set(player.getOne(Player.class).getLibrary(game.actualState)));
			if(parameters.containsKey(Parameter.TYPE))
				searchParameters.put(Parameter.TYPE, parameters.get(Parameter.TYPE));
			Event search = createEvent(game, "Search your library for a card.", SEARCH, searchParameters);
			search.perform(event, false);
			Set searchedFor = search.getResult();

			java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
			shuffleParameters.put(Parameter.CAUSE, cause);
			shuffleParameters.put(Parameter.PLAYER, player);
			createEvent(game, "Shuffle your library.", SHUFFLE_LIBRARY, shuffleParameters).perform(event, true);

			GameObject found = searchedFor.getOne(GameObject.class);

			if(found != null)
			{
				Set putOnTop = new Set(game.actualState.<GameObject>get(found.getPhysical().futureSelf));

				java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
				moveParameters.put(Parameter.CAUSE, cause);
				moveParameters.put(Parameter.INDEX, ONE);
				moveParameters.put(Parameter.OBJECT, putOnTop);
				createEvent(game, "Put that card on top of your library.", PUT_INTO_LIBRARY, moveParameters).perform(event, true);
			}

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam PLAYER: the player searching
	 * @eparam CARD: the cards/zones being searched
	 * @eparam RESULT: empty
	 */
	public final static EventType SEARCH_MARKER = new EventType("SEARCH_MARKER")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(parameters.get(Parameter.CARD));
			return true;
		}
	};
	/**
	 * @eparam OBJECT: the objects to set the attacking id on
	 * @eparam ATTACKER: an integer to set the id to
	 * @eparam RESULT: empty
	 */
	private final static EventType SET_ATTACKING_ID = new EventType("SET_ATTACKING_ID")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			int attackingID = parameters.get(Parameter.ATTACKER).getOne(Integer.class);
			if(!game.actualState.containsIdentified(attackingID))
				throw new UnsupportedOperationException("Tried to attack ID " + attackingID + " which doesn't exist in the game state.");

			// 506.3c If an effect would put a creature onto the battlefield
			// attacking either a player not in the game or a planeswalker no
			// longer on the battlefield or no longer a planeswalker, that
			// creature does enter the battlefield, but it's never considered to
			// be an attacking creature.
			Identified attackingWho = game.actualState.get(attackingID);
			if(attackingWho.isGameObject())
			{
				GameObject planeswalker = (GameObject)attackingWho;
				if(!planeswalker.getTypes().contains(Type.PLANESWALKER) || planeswalker.isGhost())
					return false;
			}
			else if(((Player)attackingWho).outOfGame)
				return false;

			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				GameObject physicalObject = object.getPhysical();
				physicalObject.setAttackingID(attackingID);
				physicalObject.setBlockedByIDs(null);
			}

			return true;
		}
	};
	/**
	 * @eparam CAUSE: what is setting the life
	 * @eparam NUMBER: the new life total
	 * @eparam PLAYER: whose life is being set
	 * @eparam RESULT: each player whose life was changed
	 */
	public static final EventType SET_LIFE = new EventType("SET_LIFE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			int life = Sum.get(parameters.get(Parameter.NUMBER));
			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
				newParameters.put(Parameter.PLAYER, new Set(player));
				newParameters.put(Parameter.CAUSE, cause);

				// 118.5. If an effect sets a player's life total to a specific
				// number, the player gains or loses the necessary amount of
				// life to end up with the new total.
				Event lifeSet = null;
				if(life < player.lifeTotal)
				{
					int change = player.lifeTotal - life;
					newParameters.put(Parameter.NUMBER, new Set(change));
					lifeSet = createEvent(game, player + " loses " + change + " life.", EventType.LOSE_LIFE, newParameters);
				}
				else if(player.lifeTotal < life)
				{
					int change = life - player.lifeTotal;
					newParameters.put(Parameter.NUMBER, new Set(change));
					lifeSet = createEvent(game, player + " gains " + change + " life.", EventType.GAIN_LIFE, newParameters);
				}

				if(null != lifeSet)
					if(!lifeSet.attempt(event))
						return false;
			}

			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set result = new Set();
			int life = Sum.get(parameters.get(Parameter.NUMBER));
			boolean allSet = true;
			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
				newParameters.put(Parameter.PLAYER, new Set(player));
				newParameters.put(Parameter.CAUSE, cause);

				// 118.5. If an effect sets a player's life total to a specific
				// number, the player gains or loses the necessary amount of
				// life to end up with the new total.
				Event lifeSet = null;
				if(life < player.lifeTotal)
				{
					int change = player.lifeTotal - life;
					newParameters.put(Parameter.NUMBER, new Set(change));
					lifeSet = createEvent(game, player + " loses " + change + " life.", EventType.LOSE_LIFE, newParameters);
				}
				else if(player.lifeTotal < life)
				{
					int change = life - player.lifeTotal;
					newParameters.put(Parameter.NUMBER, new Set(change));
					lifeSet = createEvent(game, player + " gains " + change + " life.", EventType.GAIN_LIFE, newParameters);
				}

				if(null != lifeSet)
				{
					if(!lifeSet.perform(event, false))
						allSet = false;
					result.addAll(lifeSet.getResult());
				}
			}

			event.setResult(Identity.instance(result));
			return allSet;
		}
	};
	/**
	 * TODO: this event performs a top level move because performing a shuffle
	 * after it is harder otherwise
	 * 
	 * @eparam CAUSE: what's causing the shuffle
	 * @eparam OBJECT: the cards to shuffle in and the players whose libraries
	 * are being shuffled (not redundant, since
	 * "shuffle your graveyard into your library" should shuffle your library
	 * even with no cards in your graveyard)
	 * @eparam RESULT: the cards as they exist in the library, and the shuffled
	 * library
	 */
	public static final EventType SHUFFLE_INTO_LIBRARY = new EventType("SHUFFLE_INTO_LIBRARY")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isGhost())
					return false;
			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			java.util.Set<Player> player = parameters.get(Parameter.OBJECT).getAll(Player.class);

			java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
			java.util.Set<GameObject> cards = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			moveParameters.put(Parameter.CAUSE, cause);
			moveParameters.put(Parameter.INDEX, new Set(-1));
			// if this is a stacked game, the cards will stay on the bottom and
			// be player-ordered
			if(!game.noRandom)
				moveParameters.put(Parameter.RANDOM, Empty.set);
			moveParameters.put(Parameter.OBJECT, new Set(cards));
			Event move = createEvent(game, "Put " + cards + " into their owners' libraries.", PUT_INTO_LIBRARY, moveParameters);
			boolean moveStatus = move.perform(event, true);

			// 701.15d If an effect would cause a player to shuffle one or more
			// specific objects into a library, and a replacement or prevention
			// effect causes all such objects to be moved to another zone
			// instead, that library isn't shuffled.
			out: while(true)
			{
				// 701.15e If an effect would cause a player to shuffle a set of
				// objects into a library, that library is shuffled even if
				// there are no objects in that set.
				if(cards.isEmpty())
					break out;

				// keys are cards, values are the libraries those cards should
				// go to
				java.util.Map<Integer, Integer> expectedDestinations = new java.util.HashMap<Integer, Integer>();
				for(GameObject card: cards)
					expectedDestinations.put(card.ID, card.getOwner(game.actualState).getLibrary(game.actualState).ID);

				for(ZoneChange zc: move.getResult().getAll(ZoneChange.class))
					if(expectedDestinations.get(zc.oldObjectID) == zc.destinationZoneID)
						break out;
				event.setResult(new Set());
				return false;
			}

			java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
			shuffleParameters.put(Parameter.CAUSE, cause);
			shuffleParameters.put(Parameter.PLAYER, new Set(player));
			Event shuffle = createEvent(game, player + " shuffles their library.", SHUFFLE_LIBRARY, shuffleParameters);
			boolean shuffleStatus = shuffle.perform(event, false);

			Set result = new Set();
			result.addAll(move.getResult());
			result.addAll(shuffle.getResult());
			event.setResult(result);
			return moveStatus && shuffleStatus;
		}
	};

	/**
	 * @eparam CAUSE: what's causing the shuffle
	 * @eparam PLAYER: the player choosing cards (singular)
	 * @eparam NUMBER: the number of cards to shuffle in (integer or range)
	 * @eparam CHOICE: the choice of cards to shuffle in
	 * @eparam RESULT: the result of SHUFFLE_INTO_LIBRARY
	 */
	public static final EventType SHUFFLE_INTO_LIBRARY_CHOICE = new EventType("SHUFFLE_INTO_LIBRARY_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			int required = getRange(parameters.get(Parameter.NUMBER)).getLower(0);
			Set choices = parameters.get(Parameter.CHOICE);
			return choices.size() >= required;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			org.rnd.util.NumberRange number = getRange(parameters.get(Parameter.NUMBER));
			Player choosing = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Set choices = parameters.get(Parameter.CHOICE);

			java.util.List<Object> chosen = choosing.sanitizeAndChoose(game.actualState, number.getLower(0), number.getUpper(choices.size()), choices, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.SHUFFLE_OBJECTS);
			event.allChoicesMade = (chosen.size() >= number.getLower(0));
			event.putChoices(choosing, chosen);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set objectsToShuffle = event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class));
			String name = "Shuffle " + objectsToShuffle + " into " + (objectsToShuffle.size() <= 1 ? "its owner's library." : "their owners' libraries.");

			for(GameObject o: objectsToShuffle.getAll(GameObject.class))
				objectsToShuffle.add(o.getOwner(game.actualState));

			boolean ret = event.allChoicesMade;
			java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
			shuffleParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			shuffleParameters.put(Parameter.OBJECT, objectsToShuffle);
			Event shuffle = createEvent(game, name, SHUFFLE_INTO_LIBRARY, shuffleParameters);
			ret = shuffle.perform(event, false) && ret;

			event.setResult(shuffle.getResultGenerator());
			return ret;
		}
	};

	/**
	 * @eparam CAUSE: what's causing the shuffle
	 * @eparam PLAYER: players shuffling their libraries
	 * @eparam RESULT: the shuffled libraries
	 */
	public static final EventType SHUFFLE_LIBRARY = new EventType("SHUFFLE_LIBRARY")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();
			Set cause = parameters.get(Parameter.CAUSE);

			for(Player player: parameters.get(Parameter.PLAYER).getAll(Player.class))
			{
				java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
				shuffleParameters.put(Parameter.CAUSE, cause);
				shuffleParameters.put(Parameter.PLAYER, new Set(player));
				Event shuffleOne = createEvent(game, player + " shuffles their library.", EventType.SHUFFLE_ONE_LIBRARY, shuffleParameters);
				if(shuffleOne.perform(event, false))
					result.addAll(shuffleOne.getResult());
			}

			// Objects were created... :(
			game.refreshActualState();

			event.setResult(Identity.instance(result));
			return true;
		}
	};
	/**
	 * @eparam CAUSE: what's causing the shuffle
	 * @eparam PLAYER: player (only one) shuffling their library
	 * @eparam RESULT: the shuffled library
	 */
	public static final EventType SHUFFLE_ONE_LIBRARY = new EventType("SHUFFLE_ONE_LIBRARY")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();

			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Zone actualLibrary = player.getLibrary(game.actualState);
			event.addShuffle(actualLibrary.ID);
			if(!game.noRandom)
				event.removeIndexedZone(actualLibrary);

			result.add(actualLibrary);
			event.setResult(Identity.instance(result));
			return true;
		}
	};
	/**
	 * <p>
	 * Permanently reveals an object that is hidden. Cards using this will have
	 * language on them that instructs a player to turn the card face up. We've
	 * chosen not to use the same language to keep it from being confused with
	 * the face-down status of permanents caused by the morph keyword.
	 * </p>
	 * <p>
	 * Contrast with {@link EventType#REVEAL}, which reveals a card for a
	 * limited duration.
	 * </p>
	 * 
	 * @eparam OBJECT: the object(s) to "turn face up".
	 * @eparam RESULT: empty
	 */
	public static final EventType SHOW = new EventType("SHOW")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<GameObject> objects = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			for(GameObject object: objects)
			{
				object = object.getPhysical();
				for(Player p: game.actualState.players)
				{
					object.setPhysicalVisibility(p, true);
					object.setActualVisibility(p, true);
					org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent.Reveal(event, objects, p);
					p.alert(sanitized);
				}
			}

			event.setResult(Empty.set);
			return true;
		}
	};
	/**
	 * @eparam CAUSE: The reason a phase is being added
	 * @eparam PHASE: A {@link java.util.List} of
	 * {@link org.rnd.jmagic.engine.Phase.PhaseType} specifying which kind of
	 * phase to create; the phases created will be taken in the order given
	 * @eparam TARGET: Which {@link Phase} to add the phase after. If this
	 * parameter is empty or the target phase has already occurred, this event
	 * won't create extra phases, and will fail (return false).
	 * @eparam RESULT: The created phase(s)
	 */
	public static final EventType TAKE_EXTRA_PHASE = new EventType("TAKE_EXTRA_PHASE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PHASE;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Phase addAfter = parameters.get(Parameter.TARGET).getOne(Phase.class);
			if(addAfter == null)
			{
				event.setResult(Empty.set);
				return false;
			}

			int index = 1 + game.physicalState.currentTurn().phases.indexOf(addAfter);
			// If indexOf returns -1, then either the target phase is the
			// current phase, or the target phase has already passed, in
			// which case we do nothing and fail.
			if(index == 0 && !game.physicalState.currentPhase().equals(addAfter))
			{
				event.setResult(Empty.set);
				return false;
			}

			@SuppressWarnings("unchecked") java.util.List<Phase.PhaseType> types = parameters.get(Parameter.PHASE).getOne(java.util.List.class);
			Set result = new Set();
			java.util.ListIterator<Phase.PhaseType> i = types.listIterator(types.size());
			while(i.hasPrevious())
			{
				Phase.PhaseType type = i.previous();
				Phase phase = new Phase(game.physicalState.currentTurn().getOwner(game.physicalState), type);

				// 500.8. Some effects can add phases to a turn. They do this by
				// adding the phases directly after the specified phase. If
				// multiple extra phases are created after the same phase, the
				// most recently created phase will occur first.
				game.physicalState.currentTurn().phases.add(index, phase);
				result.add(phase);
			}
			event.setResult(result);
			return true;
		}
	};
	/**
	 * @eparam CAUSE: the reason turns are being added
	 * @eparam PLAYER: the player to take the turn
	 * @eparam NUMBER: the number of extra turns to add [optional; default is 1]
	 * @eparam STEP: the StepType of any steps that should be skipped
	 * @eparam RESULT: the turns that were created
	 */
	public static final EventType TAKE_EXTRA_TURN = new EventType("TAKE_EXTRA_TURN")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			int number = 1;
			if(parameters.containsKey(Parameter.NUMBER))
				number = Sum.get(parameters.get(Parameter.NUMBER));

			Set result = new Set();
			for(int i = 0; i < number; i++)
				result.add(game.physicalState.addExtraTurn(player));

			if(parameters.containsKey(Parameter.STEP))
				for(Step.StepType step: parameters.get(Parameter.STEP).getAll(Step.StepType.class))
					for(Turn turn: result.getAll(Turn.class))
					{
						SimpleEventPattern extraTurnsStep = new SimpleEventPattern(EventType.BEGIN_STEP);
						extraTurnsStep.put(EventType.Parameter.STEP, StepOf.instance(Identity.instance(turn), step.phase, step));

						EventReplacementEffect skipReplacement = new EventReplacementEffect(game, "Skip " + step.name() + " step of extra turn.", extraTurnsStep);
						ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
						part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(skipReplacement));

						java.util.Map<Parameter, Set> skipParameters = new java.util.HashMap<Parameter, Set>();
						skipParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
						skipParameters.put(EventType.Parameter.EFFECT, new Set(part));
						skipParameters.put(EventType.Parameter.EXPIRES, new Set(Empty.instance()));
						skipParameters.put(EventType.Parameter.USES, ONE);
						Event skipStep = createEvent(game, "Skip " + step.name() + " step of extra turn.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, skipParameters);

						skipStep.perform(event, false);
					}

			event.setResult(Identity.instance(result));
			return true;
		}
	};

	/**
	 * @eparam CAUSE: what is doing the tapping
	 * @eparam PLAYER: who is choosing
	 * @eparam CHOICE: what to choose from
	 * @eparam NUMBER: how many to tap (integer or range)
	 * @eparam RESULT: the tapped object
	 */
	public static final EventType TAP_CHOICE = new EventType("TAP_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CHOICE;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set choices = parameters.get(Parameter.CHOICE);
			int required = getRange(parameters.get(Parameter.NUMBER)).getLower(0);

			int tappable = 0;
			for(GameObject choice: choices.getAll(GameObject.class))
				if(!choice.isTapped())
				{
					tappable++;
					if(tappable >= required)
						return true;
				}

			return false;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			java.util.Set<GameObject> tappable = parameters.get(Parameter.CHOICE).getAll(GameObject.class);
			java.util.Set<GameObject> toRemove = new java.util.HashSet<GameObject>();
			for(GameObject o: tappable)
				if(o.isTapped())
					toRemove.add(o);
			tappable.removeAll(toRemove);

			org.rnd.util.NumberRange range = getRange(parameters.get(Parameter.NUMBER));

			if(range.getLower(0) > tappable.size())
				event.allChoicesMade = false;
			java.util.List<GameObject> chosen = player.sanitizeAndChoose(game.actualState, range.getLower(0), range.getUpper(tappable.size()), tappable, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.TAP);

			event.putChoices(player, chosen);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean ret = event.allChoicesMade;
			Set objects = event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class));

			java.util.Map<Parameter, Set> tapParameters = new java.util.HashMap<Parameter, Set>();
			tapParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			tapParameters.put(Parameter.OBJECT, objects);
			Event tap = createEvent(game, "Tap " + objects, EventType.TAP_PERMANENTS, tapParameters);
			ret = tap.perform(event, false) && ret;

			event.setResult(tap.getResultGenerator());
			return ret;
		}

	};

	/**
	 * Represents "Tap [x]. Those objects don't untap during their controllers'
	 * next untap steps."
	 * 
	 * @eparam CAUSE: what is doing the tapping
	 * @eparam OBJECT: what is being tapped
	 * @eparam RESULT: the tapped objects
	 */
	public static final EventType TAP_HARD = new EventType("TAP_HARD")
	{
		@Override
		public Parameter affects()
		{
			return TAP_PERMANENTS.affects();
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			return TAP_PERMANENTS.attempt(game, event, parameters);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set objects = parameters.get(Parameter.OBJECT);

			Event tap = createEvent(game, "Tap " + objects, EventType.TAP_PERMANENTS, parameters);
			boolean status = tap.perform(event, false);
			event.setResult(tap.getResultGenerator());

			// Gotta do this one by one. See comments inside the loop for why
			// this is true.
			for(GameObject object: objects.getAll(GameObject.class))
			{
				// This event pattern matches the untap step of an arbitrary
				// controller of any of the given objects.
				EventPattern untapping = new UntapDuringControllersUntapStep(Identity.instance(object));

				ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
				part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(untapping));

				// These generators evaluate differently for different players,
				// and if objects change controllers, they'll even evaluate
				// differently at different times.
				SetGenerator thatPlayersUntap = UntapStepOf.instance(ControllerOf.instance(Identity.instance(object)));
				SetGenerator untapStepOver = Intersect.instance(PreviousStep.instance(), thatPlayersUntap);

				java.util.Map<Parameter, Set> ctsEffectParameters = new java.util.HashMap<Parameter, Set>();
				ctsEffectParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				ctsEffectParameters.put(Parameter.EFFECT, new Set(part));
				ctsEffectParameters.put(Parameter.EXPIRES, new Set(untapStepOver));
				Event noUntap = createEvent(game, object + " doesn't untap during its controller's next untap step.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, ctsEffectParameters);
				noUntap.perform(event, false);
			}

			return status;
		}

	};
	/**
	 * @eparam CAUSE: what is doing the tapping
	 * @eparam OBJECT: what is being tapped
	 * @eparam RESULT: the tapped object
	 */
	public static final EventType TAP_ONE_PERMANENT = new EventType("TAP_ONE_PERMANENT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// 701.16a. ... Only untapped permanents can be tapped.
			if(parameters.get(Parameter.OBJECT).getOne(GameObject.class).isTapped())
				return false;

			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean status = true;

			Set result = new Set();
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			if(!object.isTapped() && ((-1 == object.zoneID) || object.isPermanent()))
			{
				object.getPhysical().setTapped(true);
				result.add(object);
			}
			else
				status = false;

			event.setResult(Identity.instance(result));
			return status;
		}
	};

	/**
	 * @eparam CAUSE: what is doing the tapping
	 * @eparam OBJECT: what is being tapped
	 * @eparam RESULT: the tapped objects
	 */
	public static final EventType TAP_PERMANENTS = new EventType("TAP_PERMANENTS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(object.isTapped())
					return false;

			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();

			boolean allTapped = true;
			for(GameObject actualObject: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> tapOneParameters = new java.util.HashMap<Parameter, Set>();
				tapOneParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				tapOneParameters.put(Parameter.OBJECT, new Set(actualObject));
				Event tapOne = createEvent(game, "Tap " + actualObject + ".", TAP_ONE_PERMANENT, tapOneParameters);

				if(!tapOne.perform(event, false))
					allTapped = false;
				result.addAll(tapOne.getResult());
			}
			event.setResult(Identity.instance(result));
			return allTapped;
		}
	};

	/**
	 * @eparam CAUSE: what is causing the text change
	 * @eparam TARGET: what to change the text of
	 * @eparam EFFECT: SetGenerator for the expires field of the effect created
	 * (optional; default is "until cleanup")
	 * @eparam RESULT: Empty.
	 */
	public static final EventType TEXT_CHANGE_COLOR_OR_BASIC_LAND_TYPE = new EventType("TEXT_CHANGE_COLOR_OR_BASIC_LAND_TYPE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.TARGET;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject thisObject = event.getSource();
			Player you = thisObject.getController(thisObject.state);
			java.util.Collection<Enum<?>> choices = new java.util.LinkedList<Enum<?>>();
			choices.addAll(Color.allColors());
			choices.addAll(SubType.getBasicLandTypes());
			java.util.List<Enum<?>> chosen = you.choose(1, choices, PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.CHOOSE_COLOR_OR_BASIC_LAND_TYPE);
			Enum<?> from = chosen.get(0);

			Enum<?> to = null;

			// "another"
			choices.remove(from);

			// if they chose a color, they must change to another color
			if(from.getDeclaringClass().equals(Color.class))
			{
				choices.removeAll(SubType.getBasicLandTypes());
				to = you.choose(1, choices, PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.CHOOSE_ANOTHER_COLOR).get(0);
			}
			// otherwise they must change to a land type
			else
			{
				choices.removeAll(Color.allColors());
				to = you.choose(1, choices, PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.CHOOSE_ANOTHER_BASIC_LAND_TYPE).get(0);
			}

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_TEXT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(parameters.get(Parameter.TARGET)));
			part.parameters.put(ContinuousEffectType.Parameter.FROM, Identity.instance(from));
			part.parameters.put(ContinuousEffectType.Parameter.TO, Identity.instance(to));

			java.util.Map<Parameter, Set> newParameters = new java.util.HashMap<Parameter, Set>();
			newParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			newParameters.put(Parameter.EFFECT, new Set(part));
			if(parameters.containsKey(Parameter.EFFECT))
				newParameters.put(Parameter.EXPIRES, new Set(parameters.get(Parameter.EFFECT).getOne(SetGenerator.class)));
			Event textChange = createEvent(game, "Change the text of target permanent by replacing all instances of one color word with another or one basic land type with another.", CREATE_FLOATING_CONTINUOUS_EFFECT, newParameters);

			textChange.perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	/**
	 * @eparam CAUSE: what is doing the turning face down
	 * @eparam OBJECT: what permanents to turn face down
	 * @eparam RESULT: the permanents that were turned face down
	 */
	public static final EventType TURN_PERMANENTS_FACE_DOWN = new EventType("TURN_PERMANENTS_FACE_DOWN")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<GameObject> result = new java.util.HashSet<GameObject>();
			for(GameObject o: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				if(o.isPermanent())
				{
					// 711.6 ... If a spell or ability tries to turn a
					// double-faced permanent face down, nothing happens.
					if(o.getBackFace() != null)
						continue;

					// 707.2a If a face-up permanent is turned face down by a
					// spell or ability, it becomes a 2/2 face-down creature
					// with no text, no name, no subtypes, no expansion symbol,
					// and no mana cost. These values are the copiable values of
					// that object's characteristics.
					GameObject physical = o.getPhysical();

					// 707.2a If a face-up permanent is turned face down by a
					// spell or ability, it becomes a 2/2 face-down creature
					// with no text, no name, no subtypes, no expansion symbol,
					// and no mana cost. These values are the copiable values of
					// that object's characteristics.
					physical.faceDownValues = new FaceDownCard();

					result.add(o);
				}
			}

			event.setResult(Identity.instance(result));
			return true;
		}
	};

	/**
	 * @eparam OBJECT: the card to transform
	 * @eparam RESULT: empty
	 */
	public static final EventType TRANSFORM_ONE_PERMANENT = new EventType("TRANSFORM_ONE_PERMANENT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			GameObject physical = object.getPhysical();
			if(null != physical.getBackFace())
				physical.setTransformed(!physical.isTransformed());
			event.setResult(Empty.set);
			return true;
		}
	};

	/**
	 * @eparam OBJECT: the cards to transform
	 * @eparam RESULT: empty
	 */
	public static final EventType TRANSFORM_PERMANENT = new EventType("TRANSFORM_PERMANENT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> transformOneParameters = new java.util.HashMap<EventType.Parameter, Set>();
				transformOneParameters.put(Parameter.OBJECT, new Set(object));
				createEvent(game, "Transform " + object, TRANSFORM_ONE_PERMANENT, transformOneParameters).perform(event, false);
			}

			event.setResult(Empty.set);
			return true;
		}
	};

	/**
	 * Note that this only operates on a single GameObject. This is allowed to
	 * be invoked directly, unlike the other *_ONE_* EventType.
	 * 
	 * @eparam CAUSE: what is doing the turning face up
	 * @eparam OBJECT: what permanent to turn face up
	 * @eparam RESULT: the permanents that were turned face up
	 */
	public static final EventType TURN_PERMANENT_FACE_UP = new EventType("TURN_PERMANENT_FACE_UP")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			java.util.Set<GameObject> result = new java.util.HashSet<GameObject>();
			GameObject actual = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			if(actual.isPermanent())
			{
				GameObject physical = actual.getPhysical();
				// 707.8. As a face-down permanent is turned face up, its
				// copiable values revert to its normal copiable values. Any
				// effects that have been applied to the face-down permanent
				// still apply to the face-up permanent. Any abilities relating
				// to the permanent entering the battlefield don't trigger and
				// don't have any effect, because the permanent has already
				// entered the battlefield.
				physical.faceDownValues = null;
				result.add(actual);
			}

			event.setResult(Identity.instance(result));
			return true;
		}
	};

	private static java.util.Map<String, Class<? extends EventType>> typeNames;

	/**
	 * @eparam OBJECT: the GameObject instances already attached
	 * @eparam RESULT: the AttachedBy instances which were previously attached
	 * to
	 */
	public static final EventType UNATTACH = new EventType("DETACH")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set ret = new Set();
			for(GameObject o: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				AttachableTo previouslyAttachedTo = (AttachableTo)game.actualState.get(o.getAttachedTo());
				previouslyAttachedTo.getPhysical().removeAttachment(o.ID);

				ret.add(previouslyAttachedTo);
				o.getPhysical().setAttachedTo(-1);
			}
			event.setResult(Identity.instance(ret));
			return true;
		}
	};

	/**
	 * @eparam CAUSE: what is doing the untapping
	 * @eparam PLAYER: who is choosing
	 * @eparam CHOICE: what to choose from
	 * @eparam NUMBER: how many to untap (integer or range)
	 * @eparam RESULT: the tapped object
	 */
	public static final EventType UNTAP_CHOICE = new EventType("UNTAP_CHOICE")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.CHOICE;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set choices = parameters.get(Parameter.CHOICE);

			org.rnd.util.NumberRange range = getRange(parameters.get(Parameter.NUMBER));
			int required = range.getLower(0);

			int untappable = 0;
			for(GameObject choice: choices.getAll(GameObject.class))
				if(choice.isTapped())
				{
					untappable++;
					if(untappable >= required)
						return true;
				}

			return false;
		}

		@Override
		public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			java.util.Set<GameObject> untappable = parameters.get(Parameter.CHOICE).getAll(GameObject.class);
			java.util.Set<GameObject> toRemove = new java.util.HashSet<GameObject>();
			for(GameObject o: untappable)
				if(!o.isTapped())
					toRemove.add(o);
			untappable.removeAll(toRemove);

			org.rnd.util.NumberRange range = getRange(parameters.get(Parameter.NUMBER));

			if(range.getLower(0) > untappable.size())
				event.allChoicesMade = false;
			java.util.List<GameObject> chosen = player.sanitizeAndChoose(game.actualState, range.getLower(0), range.getUpper(untappable.size()), untappable, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.UNTAP);

			event.putChoices(player, chosen);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean ret = event.allChoicesMade;
			Set objects = event.getChoices(parameters.get(Parameter.PLAYER).getOne(Player.class));

			java.util.Map<Parameter, Set> untapParameters = new java.util.HashMap<Parameter, Set>();
			untapParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			untapParameters.put(Parameter.OBJECT, objects);
			Event untap = createEvent(game, "Untap " + objects, EventType.UNTAP_PERMANENTS, untapParameters);
			ret = untap.perform(event, false) && ret;

			event.setResult(untap.getResultGenerator());
			return ret;
		}
	};

	/**
	 * @eparam CAUSE: what is doing the untapping
	 * @eparam OBJECT: what is being untapped
	 * @eparam RESULT: the untapped object
	 */
	public static final EventType UNTAP_ONE_PERMANENT = new EventType("UNTAP_ONE_PERMANENT")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			// 701.16b. ... Only tapped permanents can be untapped.
			if(!parameters.get(Parameter.OBJECT).getOne(GameObject.class).isTapped())
				return false;

			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			boolean status = true;

			Set result = new Set();
			GameObject object = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
			if(object.isTapped() && object.isPermanent())
			{
				object.getPhysical().setTapped(false);
				result.add(object);
			}
			else
				status = false;

			event.setResult(Identity.instance(result));
			return status;
		}
	};

	/**
	 * @eparam CAUSE: what is doing the untapping
	 * @eparam OBJECT: what is being untapped
	 * @eparam RESULT: the untapped objects
	 */
	public static final EventType UNTAP_PERMANENTS = new EventType("UNTAP_PERMANENTS")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.OBJECT;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			for(GameObject object: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
				if(!object.isTapped())
					return false;

			return true;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set result = new Set();

			boolean allUntapped = true;
			for(GameObject actualObject: parameters.get(Parameter.OBJECT).getAll(GameObject.class))
			{
				java.util.Map<Parameter, Set> tapOneParameters = new java.util.HashMap<Parameter, Set>();
				tapOneParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
				tapOneParameters.put(Parameter.OBJECT, new Set(actualObject));
				Event untapOne = createEvent(game, "Untap " + actualObject + ".", UNTAP_ONE_PERMANENT, tapOneParameters);

				if(!untapOne.perform(event, false))
					allUntapped = false;
				result.addAll(untapOne.getResult());
			}
			event.setResult(Identity.instance(result));
			return allUntapped;
		}
	};

	/**
	 * @eparam CAUSE: who told the player to win
	 * @eparam PLAYER: the player that should win (TODO: allow multiple players
	 * to win)
	 * @eparam RESULT: nothing. an exception gets thrown, so we don't even set
	 * it up.
	 */
	public static final EventType WIN_GAME = new EventType("WIN_GAME")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			org.rnd.jmagic.sanitized.SanitizedEvent sanitized = new org.rnd.jmagic.sanitized.SanitizedEvent(event);
			for(Player player: game.actualState.players)
				player.alert(sanitized);

			parameters.get(Parameter.PLAYER).getOne(Player.class).wonGame = true;
			throw new Game.GameOverException();
		}
	};

	/**
	 * @eparam CAUSE: the wish
	 * @eparam CHOICE: the available choices
	 * @eparam PLAYER: the chooser
	 * @eparam RESULT: empty
	 * 
	 * TODO : This probably isn't possible, if multiple players win the game
	 * simultaneously via a replacement effect (LOL), and it ends up being
	 * executed as multiple simultaneous WIN_GAMEs, only the "first" player will
	 * actually win here. We need to have the flag set by this event but only
	 * throw the exception after all possible losses and wins have been set up
	 * (possibly in Event.perform, if the event is top level).
	 * 
	 * In general, we need to make sure that an event type's perform method is
	 * simultaneous-friendly.
	 */
	public static final EventType WISH = new EventType("WISH")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			return !parameters.get(Parameter.CHOICE).getAll(GameObject.class).isEmpty();
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			Set cause = parameters.get(Parameter.CAUSE);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Set choices = new Set(player.sanitizeAndChoose(game.actualState, 0, 1, parameters.get(Parameter.CHOICE).getAll(GameObject.class), PlayerInterface.ChoiceType.OBJECTS, new PlayerInterface.ChooseReason(PlayerInterface.ChooseReason.GAME, event.getName(), false)));

			java.util.Map<EventType.Parameter, Set> revealParameters = new java.util.HashMap<EventType.Parameter, Set>();
			revealParameters.put(Parameter.CAUSE, cause);
			revealParameters.put(Parameter.OBJECT, choices);
			createEvent(game, "Reveal " + choices, EventType.REVEAL, revealParameters).perform(event, false);

			java.util.Map<EventType.Parameter, Set> moveParameters = new java.util.HashMap<EventType.Parameter, Set>();
			moveParameters.put(Parameter.CAUSE, cause);
			moveParameters.put(Parameter.TO, new Set(player.getHand(game.actualState)));
			moveParameters.put(Parameter.OBJECT, choices);
			createEvent(game, "Put " + choices + " into your hand", EventType.MOVE_OBJECTS, moveParameters).perform(event, false);

			return true;
		}
	};

	/**
	 * Creates an event with the intent of immediately performing it. This event
	 * is protected to prevent its use outside the context of an EventType.
	 * 
	 * @param game The game whose physical state will be passed to the event
	 * constructor.
	 * @param name The name to pass to the event constructor.
	 * @param type The type to pass to the event constructor.
	 * @param parameters The parameter map to pass to the event constructor.
	 * @return The new event.
	 */
	protected static final Event createEvent(Game game, String name, EventType type, java.util.Map<Parameter, Set> parameters)
	{
		Event newEvent = new Event(game.physicalState, name, type);

		if(parameters != null)
			for(java.util.Map.Entry<Parameter, Set> parameter: parameters.entrySet())
				newEvent.parameters.put(parameter.getKey(), Identity.instance(parameter.getValue()));

		return newEvent;
	}

	/**
	 * Gets a number range from a parameter when that parameter is allowed to
	 * contain a number or a NumberRange.
	 * 
	 * @param parameter A set from a parameter map to an EventType.
	 * @return If <code>parameter</code> contains a number range, that
	 * NumberRange; otherwise, a NumberRange whose lower and upper bounds are
	 * each the sum of the integers in <code>parameter</code>. (1, 1) if
	 * <code>parameter</code> is null.
	 */
	public static org.rnd.util.NumberRange getRange(Set parameter)
	{
		if(parameter == null)
			return new org.rnd.util.NumberRange(1, 1);

		org.rnd.util.NumberRange num = parameter.getOne(org.rnd.util.NumberRange.class);
		if(num == null)
		{
			int sum = Sum.get(parameter);
			if(sum < 0)
				sum = 0;
			num = new org.rnd.util.NumberRange(sum, sum);
		}
		return num;
	}

	private final String toString;

	/**
	 * @param name Be nice and make this the name of the constant that stores
	 * the singleton instance of this event type.
	 */
	public EventType(String name)
	{
		if(null == typeNames)
			typeNames = new java.util.HashMap<String, Class<? extends EventType>>();
		if(typeNames.containsKey(name) && !typeNames.get(name).equals(this.getClass()))
			throw new UnsupportedOperationException("EventType names must be unique");
		typeNames.put(name, this.getClass());

		this.toString = name;
	}

	/**
	 * Override this function for any event type that adds mana to a mana pool.
	 * If it does add mana, that mana must be specified in the MANA parameter.
	 * Also, the symbols created and adding to a mana pool must be part of the
	 * event's RESULT.
	 * 
	 * @return Whether this event type adds mana to a mana pool.
	 */
	public boolean addsMana()
	{
		return false;
	}

	public abstract Parameter affects();

	/**
	 * attempt() returns true if, given all the assumptions you make when
	 * overriding perform() are true, the event is able to be performed.
	 * Example: TAP's attempt method returns false if any of the objects to be
	 * tapped are already tapped. If, given all the assumptions you make when
	 * overriding perform() are true, the event is always able to be performed,
	 * don't override attempt().
	 * 
	 * @param game The game in which the event is being attempted.
	 * @param event The event being attempted.
	 * @param parameters The parameters of the event to attempt.
	 * @return Whether the event is able to be performed.
	 */
	public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		return true;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(this.getClass() != obj.getClass())
			return false;
		EventType other = (EventType)obj;
		if(this.toString == null)
		{
			if(other.toString != null)
				return false;
		}
		else if(!this.toString.equals(other.toString))
			return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.toString == null) ? 0 : this.toString.hashCode());
		return result;
	}

	/**
	 * Causes choices for an event to be made, such as which creature to
	 * sacrifice to Innocent Blood or put onto the battlefield from Hunted
	 * Wumpus.
	 * 
	 * Event types where choices are required should override this method to
	 * make those choices and store them in <code>event</code>. Theoretically,
	 * you only need to override this method if your event type can be used as a
	 * cost or if multiple players are making choices for a simultaneous action;
	 * however it's good practice to override it anyway. Ignore the man behind
	 * the PUT_ONTO_BATTLEFIELD*_CHOICE* curtain. He's a bad programmer and he's
	 * lazy.
	 * 
	 * Properly overriding this method involves calling
	 * <code>event.putChoices</code> for each player that makes a choice, and
	 * setting <code>event.allChoicesMade</code> to false if a player is asked
	 * to make more choices than there are available (or for some other reason
	 * is unable to make a required choice).
	 * 
	 * @param game What game the choice is made in.
	 * @param event What even the choice is made for.
	 * @param parameters Parameters for the event.
	 */
	public void makeChoices(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// By default no choices need to be made.
	}

	/**
	 * When overriding this method, you may assume:
	 * <ul>
	 * <li>all the objects in the affected parameter exist</li>
	 * <li>the event wasn't replaced</li>
	 * <li>the event wasn't prohibited</li>
	 * </ul>
	 * 
	 * @param game The game the event is being performed in
	 * @param event The event being performed
	 * @param parameters The parameters to the event
	 * @return Whether the event can be used to pay a cost
	 */
	public abstract boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters);

	@Override
	public String toString()
	{
		return this.toString;
	}
}
