package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class TextChangeColorOrBasicLandType extends EventType
{	public static final EventType INSTANCE = new TextChangeColorOrBasicLandType();

	 private TextChangeColorOrBasicLandType()
	{
		super("TEXT_CHANGE_COLOR_OR_BASIC_LAND_TYPE");
	}

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
}