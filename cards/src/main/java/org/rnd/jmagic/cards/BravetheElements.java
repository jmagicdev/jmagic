package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Brave the Elements")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class BravetheElements extends Card
{
	/**
	 * @eparam CAUSE: brave the elements
	 * @eparam CHOICE: color choices available
	 * @eparam PLAYER: the player choosing the color
	 * @eparam RESULT: empty
	 */
	public static final EventType BRAVE_THE_ELEMENTS_EVENT = new EventType("BRAVE_THE_ELEMENTS_EVENT")
	{
		@Override
		public Parameter affects()
		{
			return EventType.Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			event.setResult(Empty.set);

			java.util.Set<Color> choices = parameters.get(Parameter.CHOICE).getAll(Color.class);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			PlayerInterface.ChooseParameters<Color> chooseParameters = new PlayerInterface.ChooseParameters<Color>(1, 1, new java.util.LinkedList<Color>(choices), PlayerInterface.ChoiceType.COLOR, PlayerInterface.ChooseReason.CHOOSE_COLOR);
			chooseParameters.thisID = cause.getOne(GameObject.class).ID;
			java.util.List<Color> chosenList = player.choose(chooseParameters);
			if(chosenList.isEmpty())
				return false;

			Color color = chosenList.get(0);

			Class<? extends org.rnd.jmagic.abilities.keywords.Protection> ability = org.rnd.jmagic.abilities.keywords.Protection.from(color);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.WHITE), Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance()))));
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(ability)));

			java.util.Map<Parameter, Set> fceParameters = new java.util.HashMap<Parameter, Set>();
			fceParameters.put(Parameter.CAUSE, cause);
			fceParameters.put(Parameter.EFFECT, new Set(part));
			Event protection = createEvent(game, "White creatures you control gain protection from the chosen color until end of turn.", EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, fceParameters);
			protection.perform(event, false);

			return true;
		}
	};

	public BravetheElements(GameState state)
	{
		super(state);

		EventFactory factory = new EventFactory(BRAVE_THE_ELEMENTS_EVENT, "Choose a color. White creatures you control gain protection from the chosen color until end of turn.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CHOICE, Identity.fromCollection(Color.allColors()));
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(factory);
	}
}
