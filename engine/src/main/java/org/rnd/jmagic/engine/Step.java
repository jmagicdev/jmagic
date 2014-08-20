package org.rnd.jmagic.engine;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

/** Represents a step (e.g. untap) within a phase (e.g. beginning). */
public class Step implements Ownable
{
	/**
	 * Represents the different kinds of steps.
	 * 
	 * Several of the rules quoted in the documentation here say "any abilities
	 * that trigger at the beginning of that upkeep step and any abilities that
	 * triggered during the turn's untap step go on the stack." This is
	 * automatically handled by the engine (see Game.givePriority and
	 * Game.beforePriority), so you won't see code for that here, and those
	 * sections of those rules have been omitted from this documentation.
	 */
	public enum StepType
	{
		/**
		 * TODO : 502.1. First, all phased-in permanents with phasing that the
		 * active player controls phase out, and all phased-out permanents that
		 * the active player controlled when they phased out phase in. This all
		 * happens simultaneously.
		 * 
		 * Next we determine what events, if any, need to be "additionally"
		 * performed during the untap step. Cards that do this sort of thing are
		 * Seedborn Muse and Undiscovered Paradise. (Also, cards that do this
		 * sort of thing SUCK MAJORLY AND CAN GO TO HELL AND DIE.)
		 * 
		 * 502.2. Second, the active player determines which permanents he or
		 * she controls will untap. Then he or she untaps them all
		 * simultaneously.
		 * 
		 * TODO : "Determine which permanents" will untap. This changes
		 * implementations of cards that don't untap during untap step (either
		 * optionally or all the time).
		 */
		UNTAP(Phase.PhaseType.BEGINNING)
		{
			@Override
			public void run(Game game, Player owner, Step step)
			{
				final Set untapEvents = new Set();

				Event normalUntapEvent = new Event(game.physicalState, game.actualState.currentTurn().getOwner(game.actualState) + " untaps their permanents.", EventType.UNTAP_PERMANENTS);
				normalUntapEvent.parameters.put(EventType.Parameter.CAUSE, Identity.instance(game));
				normalUntapEvent.parameters.put(EventType.Parameter.OBJECT, ControlledBy.instance(Identity.instance(owner)));
				untapEvents.add(normalUntapEvent);

				// So far, this is the only step where extra events can be
				// added
				untapEvents.addAll(game.actualState.extraEvents);

				EventType untapEventType = new EventType("EXTRA_UNTAP_EVENT")
				{
					@Override
					public Parameter affects()
					{
						return null;
					}

					@Override
					public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
					{
						boolean ret = true;
						for(Event untapEvent: untapEvents.getAll(Event.class))
						{
							Event newUntapEvent = untapEvent.create(game);
							newUntapEvent.setSource(untapEvent.getSource());
							ret = newUntapEvent.perform(event, false) && ret;
						}
						event.setResult(Empty.set);
						return ret;
					}
				};

				new Event(game.physicalState, "Untap step events.", untapEventType).perform(null, true);
			}

			@Override
			public String toString()
			{
				return "Untap";
			}
		},
		/**
		 * 503.2. The active player gets priority. Players may cast spells and
		 * activate abilities.
		 */
		UPKEEP(Phase.PhaseType.BEGINNING)
		{
			@Override
			public void run(Game game, Player owner, Step step)
			{
				game.givePriority();
			}

			@Override
			public String toString()
			{
				return "Upkeep";
			}
		},
		/**
		 * 504.1. First, the active player draws a card. This turn-based action
		 * doesn't use the stack.
		 * 
		 * 504.2. Second, any abilities that trigger at the beginning of the
		 * draw step and any other abilities that have triggered go on the
		 * stack. (automatic)
		 * 
		 * 504.3. Third, the active player gets priority. Players may cast
		 * spells and activate abilities.
		 */
		DRAW(Phase.PhaseType.BEGINNING)
		{
			@Override
			public void run(Game game, Player owner, Step step)
			{
				Event drawEvent = new Event(game.physicalState, owner + " draws a card.", EventType.DRAW_CARDS);
				drawEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
				drawEvent.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
				drawEvent.parameters.put(EventType.Parameter.PLAYER, owner.thisPlayer());

				drawEvent.perform(null, true);

				game.givePriority();
			}

			@Override
			public String toString()
			{
				return "Draw";
			}
		},
		/**
		 * Not actually a step, but this step type keeps us from making a
		 * special exception when running the turn.
		 * 
		 * 505.4. The active player gets priority. Players may cast spells and
		 * activate abilities.
		 */
		PRECOMBAT_MAIN(Phase.PhaseType.PRECOMBAT_MAIN)
		{
			@Override
			public void run(Game game, Player owner, Step step)
			{
				game.givePriority();
			}

			@Override
			public String toString()
			{
				return "Pre-combat main";
			}
		},
		/**
		 * 507.3. The active player gets priority. Players may cast spells and
		 * activate abilities.
		 */
		BEGINNING_OF_COMBAT(Phase.PhaseType.COMBAT)
		{
			@Override
			public void run(Game game, Player owner, Step step)
			{
				game.givePriority();
			}

			@Override
			public String toString()
			{
				return "Beginning of combat";
			}
		},
		/**
		 * 508.1. First, the active player declares attackers.
		 * 
		 * 508.3. The active player gets priority. Players may cast spells and
		 * activate abilities.
		 */
		DECLARE_ATTACKERS(Phase.PhaseType.COMBAT)
		{
			@Override
			public void run(Game game, Player owner, Step step)
			{
				new Event(game.physicalState, owner + " declares attackers.", EventType.DECLARE_ATTACKERS).perform(null, true);

				// 508.6. If no creatures are declared as attackers or put onto
				// the battlefield attacking, skip the declare blockers and
				// combat damage steps.
				if(Attacking.instance().evaluate(game, null).isEmpty())
				{
					SetGenerator player = Identity.instance(owner);

					SimpleEventPattern declareBlockersStep = new SimpleEventPattern(EventType.BEGIN_STEP);
					declareBlockersStep.put(EventType.Parameter.STEP, DeclareBlockersStepOf.instance(player));
					EventReplacementEffect blockReplacement = new EventReplacementEffect(game, "Skip the declare blockers step", declareBlockersStep);

					ContinuousEffect.Part blockPart = replacementEffectPart(blockReplacement);

					SimpleEventPattern combatDamageStep = new SimpleEventPattern(EventType.BEGIN_STEP);
					combatDamageStep.put(EventType.Parameter.STEP, CombatDamageStepOf.instance(player));
					EventReplacementEffect combatDamageReplacement = new EventReplacementEffect(game, "Skip the combat damage step", combatDamageStep);

					ContinuousEffect.Part combatDamagePart = replacementEffectPart(combatDamageReplacement);

					Event skipBlockers = new Event(game.physicalState, "Skip the declare blockers and combat damage steps of this combat phase.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT);
					skipBlockers.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
					skipBlockers.parameters.put(EventType.Parameter.EFFECT, Identity.instance(blockPart));
					skipBlockers.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
					skipBlockers.parameters.put(EventType.Parameter.USES, numberGenerator(1));
					skipBlockers.perform(null, true);

					Event skipDamage = new Event(game.physicalState, "Skip the declare blockers and combat damage steps of this combat phase.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT);
					skipDamage.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
					skipDamage.parameters.put(EventType.Parameter.EFFECT, Identity.instance(combatDamagePart));
					skipDamage.parameters.put(EventType.Parameter.EXPIRES, Identity.instance(Empty.instance()));
					skipDamage.parameters.put(EventType.Parameter.USES, numberGenerator(1));
					skipDamage.perform(null, true);
				}

				game.givePriority();
			}

			@Override
			public String toString()
			{
				return "Declare attackers";
			}
		},
		/**
		 * 509. Lots of stuff will happen here.
		 */
		DECLARE_BLOCKERS(Phase.PhaseType.COMBAT)
		{
			@Override
			public void run(Game game, Player owner, Step step)
			{
				new Event(game.physicalState, "Defending players declares blockers.", EventType.DECLARE_BLOCKERS).perform(null, true);

				game.givePriority();

				// Create a new combat damage step for first- and
				// double-strikers if applicable
				SetGenerator firstStrike = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.FirstStrike.class);
				SetGenerator doubleStrike = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.DoubleStrike.class);
				SetGenerator attackers = Attacking.instance();
				SetGenerator blockers = Blocking.instance();
				SetGenerator toCreate = Intersect.instance(Union.instance(firstStrike, doubleStrike), Union.instance(attackers, blockers));

				// if any of the attackers or blockers have first- or
				// double-strike, create their damage step
				if(!toCreate.evaluate(game, null).isEmpty())
					game.physicalState.currentPhase().steps.add(0, new Step(owner, COMBAT_DAMAGE));
			}

			@Override
			public String toString()
			{
				return "Declare blockers";
			}
		},
		/**
		 * 510. Lots of stuff will happen here.
		 */
		COMBAT_DAMAGE(Phase.PhaseType.COMBAT)
		{
			public java.util.Map<GameObject, java.util.List<Target>> getLegalAttackAssignment(Game game, Set attackers)
			{
				java.util.Map<GameObject, java.util.List<Target>> assignment = new java.util.HashMap<GameObject, java.util.List<Target>>();

				Set hasTrample = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Trample.class).evaluate(game, null);

				// Each entry in this map needs to be verified for rules
				// regarding assignment ordering and trample damage
				java.util.Map<Target, java.util.Set<Target>> makeSureTheseWereAssignedLethal = new java.util.HashMap<Target, java.util.Set<Target>>();

				// Anything assigned damage from a source with deathtouch is
				// considered to have been assigned lethal damage
				java.util.List<GameObject> assignedFromDeathtouch = new java.util.LinkedList<GameObject>();

				// Each entry in this map needs to be verified for rules
				// regarding objects which 'may assign combat damage as though
				// unblocked'
				java.util.Map<Target, java.util.Set<Target>> makeSureTheseWerentAssignedDamage = new java.util.HashMap<Target, java.util.Set<Target>>();

				// This is a map from the blocker's ID to the damage thats been
				// assigned to it
				java.util.Map<Integer, Integer> damageAssignedToBlocker = new java.util.HashMap<Integer, Integer>();

				for(GameObject attacker: attackers.getAll(GameObject.class))
				{
					attacker = attacker.getActual();

					// 510.1a Each attacking creature and each blocking creature
					// assigns combat damage equal to its power. Creatures that
					// would assign 0 or less damage this way don't assign
					// combat damage at all.
					if((game.actualState.assignCombatDamageUsingToughness ? attacker.getToughness() : attacker.getPower()) > 0)
					{
						Identified beingAttacked = game.actualState.get(attacker.getAttackingID());
						if(!beingAttacked.attackable())
							throw new IllegalStateException("A creature is attacking a non-Planeswalker GameObject");

						if(attacker.getBlockedByIDs() == null)
						{
							// make sure we have Set(List(items)), not
							// Set(items)
							Set targetParameter = new Set();
							java.util.List<Target> assignTo = new java.util.LinkedList<Target>();
							assignTo.add(new Target(beingAttacked));
							targetParameter.add(assignTo);

							Event assignDamage = new Event(game.physicalState, attacker + " assigns combat damage", EventType.ASSIGN_COMBAT_DAMAGE);
							assignDamage.parameters.put(EventType.Parameter.OBJECT, Identity.instance(attacker));
							assignDamage.parameters.put(EventType.Parameter.TARGET, Identity.fromCollection(targetParameter));
							assignDamage.setSource(null);
							assignDamage.perform(null, true);
							assignment.put(attacker.getActual(), assignTo);
						}
						else
						{
							java.util.List<Target> assignTo = new java.util.LinkedList<Target>();
							for(Integer i: attacker.getBlockedByIDs())
							{
								GameObject blocker = game.actualState.getByIDObject(i);
								if(blocker != null && blocker.getBlockingIDs().contains(attacker.ID))
									assignTo.add(new Target(blocker));
							}

							Target defender = null;
							boolean thisHasTrample = hasTrample.contains(attacker);

							// If the creature has trample, or can deal
							// damage as though it were unblocked, add the
							// defending player or planeswalkers to the
							// targets
							if(thisHasTrample || attacker.isDealDamageAsUnblocked())
							{
								defender = new Target(beingAttacked);

								if(attacker.isDealDamageAsUnblocked())
									makeSureTheseWerentAssignedDamage.put(defender, new java.util.HashSet<Target>(assignTo));

								assignTo.add(defender);
							}

							if(!assignTo.isEmpty())
							{
								Event assignDamage = new Event(game.physicalState, attacker + " assigns combat damage", EventType.ASSIGN_COMBAT_DAMAGE);
								assignDamage.parameters.put(EventType.Parameter.OBJECT, Identity.instance(attacker));

								// Use the Object method instead of the
								// Collection method to store the List and
								// preserve ordering.
								assignDamage.parameters.put(EventType.Parameter.TARGET, Identity.instance((Object)assignTo));
								assignDamage.setSource(null);
								assignDamage.perform(null, true);

								attacker = attacker.getActual();
								boolean hasDeathtouch = attacker.hasAbility(org.rnd.jmagic.abilities.keywords.Deathtouch.class);
								java.util.Set<Target> previousAssignments = new java.util.HashSet<Target>();

								// Add these assignments to the total damage
								// assigned to each blocker. This is used to
								// determine valid assignment of trample damage.
								for(Target damageAssignment: assignTo)
								{
									if(damageAssignedToBlocker.containsKey(damageAssignment.targetID))
										damageAssignedToBlocker.put(damageAssignment.targetID, damageAssignedToBlocker.get(damageAssignment.targetID) + damageAssignment.division);
									else
										damageAssignedToBlocker.put(damageAssignment.targetID, damageAssignment.division);

									if(!previousAssignments.isEmpty())
										makeSureTheseWereAssignedLethal.put(damageAssignment, new java.util.HashSet<Target>(previousAssignments));
									previousAssignments.add(damageAssignment);

									if(hasDeathtouch && defender != damageAssignment)
										assignedFromDeathtouch.add(game.actualState.<GameObject>get(damageAssignment.targetID));
								}
								assignment.put(attacker, assignTo);
							}
						}
					}
				}

				// The following loops do checking for legality of the various
				// rules modifying ways to assign damage

				// This loops checks for creatures that 'may assign combat
				// damage as though they were unblocked'. If they dealt any
				// damage to the defending player AND to a blocker, they
				// don't pass this loop (but let the other loops check them
				// if they exist there).
				dealDamageAsThoughUnblockedChecking: for(java.util.Map.Entry<Target, java.util.Set<Target>> entry: makeSureTheseWerentAssignedDamage.entrySet())
				{
					Target defender = entry.getKey();
					if(defender.division > 0)
					{
						for(Target blockerTarget: entry.getValue())
						{
							// The damage to the defender is also in this
							// list, so ignore that.
							if(blockerTarget == defender)
								continue;

							if(blockerTarget.division > 0)
							{
								// If its also being checked for trample,
								// give that check the chance to determine
								// if its legal. Otherwise, fail this set of
								// assignments.
								if(makeSureTheseWereAssignedLethal.containsKey(defender))
									continue dealDamageAsThoughUnblockedChecking;

								// Break the legal assignment so it can start
								// again from scratch
								return null;
							}
						}
						// If it passes this damage checking, make sure
						// trample checking doesn't accidentally fail it
						makeSureTheseWereAssignedLethal.remove(defender);
					}
				}

				// This loop ensures that damage assignment order is honored for
				// blocking creatures.
				for(java.util.Map.Entry<Target, java.util.Set<Target>> entry: makeSureTheseWereAssignedLethal.entrySet())
				{
					// if the target was assigned any damage, make sure all
					// the blockers were assigned fatal
					Target defender = entry.getKey();
					if(defender.division > 0)
					{
						for(Target blockerTarget: entry.getValue())
						{
							// The damage to the defender is also in this
							// list, so ignore that.
							if(blockerTarget == defender)
								continue;

							// if all the damage that was assigned to the
							// creature plus its existing damage is less
							// than its toughness, an illegal division was
							// made, so start from the beginning
							GameObject blocker = game.actualState.getByIDObject(blockerTarget.targetID);
							if(blocker.getDamage() + damageAssignedToBlocker.get(blocker.ID) < blocker.getToughness() && !assignedFromDeathtouch.contains(blocker))
							{
								// Break the legal assignment so it can start
								// again from scratch
								return null;
							}
						}
					}
				}
				return assignment;
			}

			public java.util.Map<GameObject, java.util.List<Target>> getLegalBlockAssignment(Game game, Set blockers)
			{
				java.util.Map<GameObject, java.util.List<Target>> assignment = new java.util.HashMap<GameObject, java.util.List<Target>>();

				// Each entry in this map needs to be verified for rules
				// regarding damage assignment order
				java.util.Map<Target, java.util.Set<Target>> makeSureTheseWereAssignedLethal = new java.util.HashMap<Target, java.util.Set<Target>>();

				// Anything assigned damage from a source with deathtouch is
				// considered to have been assigned lethal damage
				java.util.List<GameObject> assignedFromDeathtouch = new java.util.LinkedList<GameObject>();

				// This is a map from the blocker's ID to the damage thats been
				// assigned to it
				java.util.Map<Integer, Integer> damageAssignedToBlocker = new java.util.HashMap<Integer, Integer>();

				for(GameObject blocker: blockers.getAll(GameObject.class))
				{
					blocker = blocker.getActual();
					int damageAmount = (game.actualState.assignCombatDamageUsingToughness ? blocker.getToughness() : blocker.getPower());

					// 510.1a Each attacking creature and each blocking creature
					// assigns combat damage equal to its power. Creatures that
					// would assign 0 or less damage this way don't assign
					// combat damage at all.
					if(blocker.getTypes().contains(Type.CREATURE) && damageAmount > 0)
					{

						java.util.List<Target> assignTo = new java.util.LinkedList<Target>();
						for(Integer i: blocker.getBlockingIDs())
						{
							GameObject attacker = game.actualState.getByIDObject(i);
							if(attacker != null && attacker.getBlockedByIDs() != null && attacker.getBlockedByIDs().contains(blocker.ID))
								assignTo.add(new Target(attacker));
						}

						if(!assignTo.isEmpty())
						{
							// make sure we have Set(List(items)), not
							// Set(items)
							Set targetParameter = new Set();
							targetParameter.add(assignTo);

							Event assignDamage = new Event(game.physicalState, blocker + " assigns combat damage", EventType.ASSIGN_COMBAT_DAMAGE);
							assignDamage.parameters.put(EventType.Parameter.OBJECT, Identity.instance(blocker));
							assignDamage.parameters.put(EventType.Parameter.TARGET, Identity.fromCollection(targetParameter));
							assignDamage.setSource(null);
							assignDamage.perform(null, true);

							blocker = blocker.getActual();
							boolean hasDeathtouch = blocker.hasAbility(org.rnd.jmagic.abilities.keywords.Deathtouch.class);
							java.util.Set<Target> previousAssignments = new java.util.HashSet<Target>();

							// Add these assignments to the total damage
							// assigned to each blocker. This is used to
							// determine valid assignment of trample damage.
							for(Target damageAssignment: assignTo)
							{
								if(damageAssignedToBlocker.containsKey(damageAssignment.targetID))
									damageAssignedToBlocker.put(damageAssignment.targetID, damageAssignedToBlocker.get(damageAssignment.targetID) + damageAssignment.division);
								else
									damageAssignedToBlocker.put(damageAssignment.targetID, damageAssignment.division);

								if(!previousAssignments.isEmpty())
									makeSureTheseWereAssignedLethal.put(damageAssignment, new java.util.HashSet<Target>(previousAssignments));
								previousAssignments.add(damageAssignment);

								if(hasDeathtouch)
									assignedFromDeathtouch.add(game.actualState.<GameObject>get(damageAssignment.targetID));
							}
							assignment.put(blocker, assignTo);
						}
					}
				}

				// The following loop does checking for legality of the various
				// rules modifying ways to assign damage
				for(java.util.Map.Entry<Target, java.util.Set<Target>> entry: makeSureTheseWereAssignedLethal.entrySet())
				{
					// if the target was assigned any damage, make sure all
					// other targets were assigned fatal
					Target defender = entry.getKey();
					if(defender.division > 0)
					{
						for(Target blockerTarget: entry.getValue())
						{
							// The damage to the defender is also in this
							// list, so ignore that.
							if(blockerTarget == defender)
								continue;

							// if all the damage that was assigned to the
							// creature plus its existing damage is less
							// than its toughness, an illegal division was
							// made, so start from the beginning
							GameObject attacker = game.actualState.getByIDObject(blockerTarget.targetID);
							if(attacker.getDamage() + damageAssignedToBlocker.get(attacker.ID) < attacker.getToughness() && !assignedFromDeathtouch.contains(attacker))
							{
								// Break the legal assignment so it can start
								// again from scratch
								return null;
							}
						}
					}
				}
				return assignment;
			}

			@Override
			public void run(Game game, Player owner, Step step)
			{
				Set damage = new Set();

				Phase currentCombatPhase = game.physicalState.currentPhase();
				Step thisStep = game.physicalState.currentStep();
				Step previousCombatStep = null;

				boolean thisIsFirstStrike = false;
				boolean thereWasAFirstStrike = false;

				// Look for a first strike step that already ran
				for(Step pastStep: currentCombatPhase.stepsRan)
					if(pastStep.type == COMBAT_DAMAGE && pastStep != thisStep)
					{
						previousCombatStep = pastStep;
						thereWasAFirstStrike = true;
					}

				// If you can't find one, look for a normal combat damage step
				// to be run after this
				if(!thereWasAFirstStrike)
					for(Step futureStep: currentCombatPhase)
						if(futureStep.type == COMBAT_DAMAGE && futureStep != thisStep)
						{
							thisIsFirstStrike = true;
							break;
						}

				Set attackers = Attacking.get(game.actualState);
				Set blockers = Blocking.get(game.actualState);

				// If this is a first-strike step, only creatures with first- or
				// double-strike assign and deal damage
				if(thisIsFirstStrike)
				{
					Set firstStrike = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.FirstStrike.class).evaluate(game, null);
					SetGenerator doubleStrike = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.DoubleStrike.class);
					Set hasStrikeAbility = Union.instance(Identity.fromCollection(firstStrike), doubleStrike).evaluate(game, null);

					attackers = Intersect.get(attackers, hasStrikeAbility);
					blockers = Intersect.get(blockers, hasStrikeAbility);

					step.creaturesWithFirstStrike = new java.util.HashSet<Integer>();
					for(GameObject o: firstStrike.getAll(GameObject.class))
						step.creaturesWithFirstStrike.add(o.ID);
				}

				// The only creatures that assign combat damage in that step are
				// the remaining attackers and blockers that didn't have first
				// strike as the first combat damage step began, as well as the
				// remaining attackers and blockers that currently have double
				// strike.
				if(!thisIsFirstStrike && thereWasAFirstStrike)
				{
					Set firstStrike = new Set();
					for(Integer i: previousCombatStep.creaturesWithFirstStrike)
						firstStrike.add(game.actualState.get(i));
					Set creaturesToRemove = RelativeComplement.get(firstStrike, HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.DoubleStrike.class).evaluate(game, null));
					attackers = RelativeComplement.get(attackers, creaturesToRemove);
					blockers = RelativeComplement.get(blockers, creaturesToRemove);
				}

				java.util.Map<GameObject, java.util.List<Target>> attackingAssignment;
				while((attackingAssignment = getLegalAttackAssignment(game, attackers)) == null)
				{
					// TODO : notify the player that they messed up and the
					// division is restarting
				}

				for(java.util.Map.Entry<GameObject, java.util.List<Target>> entry: attackingAssignment.entrySet())
				{
					// Remove all zero-damage assignments
					java.util.Set<Target> toRemove = new java.util.HashSet<Target>();
					java.util.List<Target> assignments = entry.getValue();
					for(Target damageAssignment: assignments)
						if(damageAssignment.division == 0)
							toRemove.add(damageAssignment);
					assignments.removeAll(toRemove);

					// Add the rest of the assignments to the combat damage
					GameObject object = entry.getKey();
					for(Target taker: assignments)
					{
						if(!game.actualState.containsIdentified(taker.targetID))
							throw new UnsupportedOperationException("Taker doesn't exist in combat damage assignment: " + taker.targetID);
						Identified takerObject = game.actualState.get(taker.targetID);

						for(int i = 0; i < taker.division; i++)
						{
							DamageAssignment newAssignment = new DamageAssignment(object, takerObject);
							newAssignment.isCombatDamage = true;
							damage.add(newAssignment);
						}
					}
				}

				java.util.Map<Player, java.util.Set<GameObject>> blockersByController = new java.util.HashMap<Player, java.util.Set<GameObject>>();

				for(GameObject o: blockers.getAll(GameObject.class))
				{
					o = o.getActual();

					Player controller = o.getController(o.state);
					if(blockersByController.containsKey(controller))
						blockersByController.get(controller).add(o);
					else
					{
						java.util.HashSet<GameObject> controlled = new java.util.HashSet<GameObject>();
						controlled.add(o);
						blockersByController.put(controller, controlled);
					}
				}

				java.util.Map<GameObject, java.util.List<Target>> blockingAssignment = new java.util.HashMap<GameObject, java.util.List<Target>>();
				for(java.util.Set<GameObject> onePlayersBlockers: blockersByController.values())
					while((blockingAssignment = getLegalBlockAssignment(game, Set.fromCollection(onePlayersBlockers))) == null)
					{
						// continue until you get a legal assignment
					}

				for(java.util.Map.Entry<GameObject, java.util.List<Target>> block: blockingAssignment.entrySet())
				{
					GameObject object = block.getKey().getActual();

					// Remove all zero-damage assignments
					java.util.Set<Target> toRemove = new java.util.HashSet<Target>();
					java.util.List<Target> assignments = block.getValue();
					for(Target damageAssignment: assignments)
						if(damageAssignment.division == 0)
							toRemove.add(damageAssignment);
					assignments.removeAll(toRemove);

					// Add the rest of the assignments to the combat damage
					for(Target taker: assignments)
					{
						if(!game.actualState.containsIdentified(taker.targetID))
							throw new UnsupportedOperationException("Taker doesn't exist in combat damage assignment: " + taker.targetID);
						Identified takerObject = game.actualState.get(taker.targetID);

						for(int i = 0; i < taker.division; i++)
						{
							DamageAssignment newAssignment = new DamageAssignment(object, takerObject);
							newAssignment.isCombatDamage = true;
							damage.add(newAssignment);
						}
					}
				}

				Event damageEvent = new Event(game.physicalState, "Deal combat damage.", EventType.DEAL_COMBAT_DAMAGE);
				damageEvent.parameters.put(EventType.Parameter.TARGET, Identity.fromCollection(damage));
				damageEvent.perform(null, true);

				game.givePriority();
			}

			@Override
			public String toString()
			{
				return "Combat damage";
			}
		},
		/**
		 * 511.2. The active player gets priority. Players may cast spells and
		 * activate abilities.
		 * 
		 * 511.3. As soon as the end of combat step ends, all creatures and
		 * planeswalkers are removed from combat. After the end of combat step
		 * ends, the combat phase is over and the postcombat main phase begins
		 * (see rule 505).
		 */
		END_OF_COMBAT(Phase.PhaseType.COMBAT)
		{
			@Override
			public void run(Game game, Player owner, Step step)
			{
				game.givePriority();

				SetGenerator planeswalkers = Intersect.instance(HasType.instance(Type.PLANESWALKER), Permanents.instance());

				// Removing a player from combat has no rules meaning; however
				// for us, it means that we know nothing is attacking that
				// player anymore.
				Event removeEvent = new Event(game.physicalState, "Remove all creatures and planeswalkers from combat.", EventType.REMOVE_FROM_COMBAT);
				removeEvent.parameters.put(EventType.Parameter.OBJECT, Union.instance(Players.instance(), CreaturePermanents.instance(), planeswalkers));
				removeEvent.perform(null, true);
			}

			@Override
			public String toString()
			{
				return "End of combat";
			}
		},
		/**
		 * Not actually a step, but this step type keeps us from making a
		 * special exception when running the turn.
		 * 
		 * 505.4. The active player gets priority. Players may cast spells and
		 * activate abilities.
		 */
		POSTCOMBAT_MAIN(Phase.PhaseType.POSTCOMBAT_MAIN)
		{
			@Override
			public void run(Game game, Player owner, Step step)
			{
				game.givePriority();
			}

			@Override
			public String toString()
			{
				return "Post-combat main";
			}
		},
		/**
		 * 513.2. The active player gets priority. Players may cast spells and
		 * activate abilities.
		 */
		END(Phase.PhaseType.ENDING)
		{
			@Override
			public void run(Game game, Player owner, Step step)
			{
				game.givePriority();
			}

			@Override
			public String toString()
			{
				return "End";
			}
		},
		/**
		 * 514.1. First, if the active player's hand contains more cards than
		 * his or her maximum hand size (normally seven), he or she discards
		 * enough cards to reduce his or her hand size to that number. This
		 * turn-based action doesn't use the stack.
		 * 
		 * 514.2. Second, the following actions happen simultaneously: all
		 * damage marked on permanents (including phased-out permanents) is
		 * removed and all "until end of turn" and "this turn" effects end. This
		 * turn-based action doesn't use the stack.
		 * 
		 * 514.3. Normally, no player receives priority during the cleanup step,
		 * so no spells can be cast and no abilities can be activated. However,
		 * this rule is subject to the following exception:
		 * 
		 * 514.3a At this point, the game checks to see if any state-based
		 * actions would be performed and/or any triggered abilities are waiting
		 * to be put onto the stack (including those that trigger
		 * "at the beginning of the next cleanup step"). If so, those
		 * state-based actions are performed, then those triggered abilities are
		 * put on the stack, then the active player gets priority. Players may
		 * cast spells and activate abilities. Once the stack is empty and all
		 * players pass, another cleanup step begins.
		 */
		CLEANUP(Phase.PhaseType.ENDING)
		{
			@Override
			public void run(Game game, Player owner, Step step)
			{
				Integer maxHandSize = owner.getMaxHandSize();
				if(maxHandSize != null)
				{
					int cardsInHand = owner.getHand(game.actualState).objects.size();
					if(cardsInHand > maxHandSize)
					{
						Event discardEvent = new Event(game.physicalState, owner + " discards down to their maximum hand size.", EventType.DISCARD_TO);
						discardEvent.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
						discardEvent.parameters.put(EventType.Parameter.NUMBER, numberGenerator(maxHandSize));
						discardEvent.parameters.put(EventType.Parameter.PLAYER, owner.thisPlayer());
						discardEvent.perform(null, true);
					}
				}

				for(GameObject object: game.physicalState.battlefield().objects)
					object.setDamage(0);
				game.physicalState.currentTurn().endEffects = true;

				boolean stateBasedEffectsResolved = game.checkStateBasedActions();
				if(stateBasedEffectsResolved)
					while(game.checkStateBasedActions())
					{
						// intentionally left blank
					}
				boolean triggersStacked = game.stackTriggers();
				if(stateBasedEffectsResolved || triggersStacked)
				{
					game.givePriority();
					game.physicalState.currentPhase().steps.add(new Step(step.getOwner(game.physicalState), CLEANUP));
				}
			}

			@Override
			public String toString()
			{
				return "Cleanup";
			}
		};

		public final Phase.PhaseType phase;

		StepType(Phase.PhaseType phase)
		{
			this.phase = phase;
		}

		/** Runs a step of this type. */
		public abstract void run(Game game, Player owner, Step step);
	}

	private String name;

	/** Whose step this is (whose turn it is as well). */
	public int ownerID;

	/** What kind of step this is. */
	public final StepType type;

	/**
	 * Regular combat damage needs to know what creatures had first strike when
	 * the first strike combat damage step began, so it will store it in here
	 * for later reference. Apologies to all non-combat steps.
	 */
	public java.util.Set<Integer> creaturesWithFirstStrike;

	/**
	 * @param owner Whose step is being made.
	 * @param type What king of step to make.
	 */
	public Step(Player owner, StepType type)
	{
		this.type = type;
		this.setOwner(owner);
		this.creaturesWithFirstStrike = null;
	}

	/** @return Whose step this is. */
	@Override
	public Player getOwner(GameState state)
	{
		return state.get(this.ownerID);
	}

	/**
	 * Tells this step it has a new owner.
	 * 
	 * @param owner The new owner.
	 */
	public void setOwner(Player owner)
	{
		if(owner == null)
		{
			this.name = "Nobody's " + this.type + " step";
			this.ownerID = -1;
		}
		else
		{
			this.name = owner + "'s " + this.type + " step";
			this.ownerID = owner.ID;
		}
	}

	@Override
	public String toString()
	{
		return this.name;
	}
}
