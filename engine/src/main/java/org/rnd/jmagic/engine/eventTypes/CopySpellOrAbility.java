package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CopySpellOrAbility extends EventType
{	public static final EventType INSTANCE = new CopySpellOrAbility();

	 private CopySpellOrAbility()
	{
		super("COPY_SPELL_OR_ABILITY");
	}

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

			result = NewObjectOf.instance(Identity.fromCollection(result)).evaluate(game, null);

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
}