package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class MoveBatch extends EventType
{
	public static final EventType INSTANCE = new MoveBatch();

	private MoveBatch()
	{
		super("MOVE_BATCH");
	}

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
			players = game.actualState.apnapOrder(Set.fromCollection(players));
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
				if(toBattlefield)
				{
					game.physicalState.voidedObjects.remove(moveIn);
					if((original.getTypes().contains(Type.INSTANT) || original.getTypes().contains(Type.SORCERY)))
					{
						event.setResult(Empty.set);
						continue;
					}
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

				java.util.Set<Integer> sides = movement.characteristicsIndices;
				if(sides != null && sides.size() == 1)
					moveIn.selectCharacteristics(sides.iterator().next());

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
						Set newAbilities = Set.fromCollection(moveIn.getNonStaticAbilities());
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

						newAbilities = Set.fromCollection(moveIn.getStaticAbilities());
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

		event.setResult(Identity.fromCollection(newObjects));
		return true;
	}
}