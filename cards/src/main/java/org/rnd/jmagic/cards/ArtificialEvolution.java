package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Artificial Evolution")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ArtificialEvolution extends Card
{
	public static final PlayerInterface.ChooseReason FIRST_REASON = new PlayerInterface.ChooseReason("ArtificialEvolution", "Choose a creature type.", true);
	public static final PlayerInterface.ChooseReason SECOND_REASON = new PlayerInterface.ChooseReason("ArtificialEvolution", "Choose another creature type that isn't Wall.", true);

	/**
	 * @eparam CAUSE: what is causing the text change
	 * @eparam TARGET: what to change the text of
	 * @eparam RESULT: Empty.
	 */
	public static final EventType TEXT_CHANGE_CREATURE_TYPE = new EventType("TEXT_CHANGE_CREATURE_TYPE")
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
			choices.addAll(SubType.getAllCreatureTypes());
			java.util.List<Enum<?>> chosen = you.choose(1, choices, PlayerInterface.ChoiceType.CREATURE_TYPE, FIRST_REASON);
			Enum<?> from = chosen.get(0);

			choices.remove(from);
			choices.remove(Identity.instance(SubType.WALL).evaluate(game, thisObject).getOne(SubType.class));
			Enum<?> to = you.choose(1, choices, PlayerInterface.ChoiceType.CREATURE_TYPE, SECOND_REASON).get(0);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_TEXT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(parameters.get(Parameter.TARGET)));
			part.parameters.put(ContinuousEffectType.Parameter.FROM, Identity.instance(from));
			part.parameters.put(ContinuousEffectType.Parameter.TO, Identity.instance(to));

			java.util.Map<Parameter, Set> textChangeParameters = new java.util.HashMap<Parameter, Set>();
			textChangeParameters.put(Parameter.CAUSE, parameters.get(Parameter.CAUSE));
			textChangeParameters.put(Parameter.EFFECT, new Set(part));
			Event textChange = createEvent(game, "Change the text of target spell or permanent by replacing all instances of one creature type with another. The new creature type can't be Wall.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, textChangeParameters);
			if(parameters.containsKey(Parameter.EFFECT))
				textChange.parameters.put(Parameter.EXPIRES, Identity.instance(parameters.get(Parameter.EFFECT).getOne(SetGenerator.class)));

			textChange.perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	public ArtificialEvolution(GameState state)
	{
		super(state);

		Target target = this.addTarget(Union.instance(Spells.instance(), Permanents.instance()), "target spell or permanent");

		// Change the text of target spell or permanent by replacing all
		// instances of one creature type with another. The new creature type
		// can't be Wall. (This effect lasts indefinitely.)
		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.TARGET, targetedBy(target));
		this.addEffect(new EventFactory(TEXT_CHANGE_CREATURE_TYPE, parameters, "Change the text of target spell or permanent by replacing all instances of one creature type with another. The new creature type can't be Wall."));
	}
}
