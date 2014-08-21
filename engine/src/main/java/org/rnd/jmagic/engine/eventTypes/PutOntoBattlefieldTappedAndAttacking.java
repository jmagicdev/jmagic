package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PutOntoBattlefieldTappedAndAttacking extends EventType
{
	public static final EventType INSTANCE = new PutOntoBattlefieldTappedAndAttacking();

	private PutOntoBattlefieldTappedAndAttacking()
	{
		super("PUT_ONTO_BATTLEFIELD_TAPPED_AND_ATTACKING");
	}

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

			EventFactory attacking = new EventFactory(EventType.SET_ATTACKING_ID, "Attacking");
			attacking.parameters.put(Parameter.OBJECT, NewObjectOf.instance(Identity.instance(change)));
			attacking.parameters.put(Parameter.ATTACKER, Identity.instance(attackingID));
			change.events.add(attacking);
		}

		event.setResult(putOntoBattlefield.getResultGenerator());
		return status;
	}
}