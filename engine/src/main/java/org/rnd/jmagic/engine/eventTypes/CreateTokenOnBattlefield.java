package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CreateTokenOnBattlefield extends EventType
{
	public static final EventType INSTANCE = new CreateTokenOnBattlefield();

	private CreateTokenOnBattlefield()
	{
		super("CREATE_TOKEN_ON_BATTLEFIELD");
	}

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
					t.addColors(colors);

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
}