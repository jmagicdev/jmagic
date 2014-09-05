package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Turnabout")
@Types({Type.INSTANT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = UrzasSaga.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Turnabout extends Card
{
	public static final PlayerInterface.ChooseReason TURNABOUT_CHOICE = new PlayerInterface.ChooseReason("Turnabout", "Choose artifact, creature, or land.", true);

	/**
	 * @eparam CAUSE: turnabout
	 * @eparam PLAYER: the player choosing
	 * @eparam TARGET: the target player
	 * @eparam RESULT: empty
	 */
	public static final EventType TURNABOUT_EFFECT = new EventType("TURNABOUT_EFFECT")
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

			Player chooser = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Type choice = chooser.choose(1, java.util.EnumSet.of(Type.ARTIFACT, Type.CREATURE, Type.LAND), PlayerInterface.ChoiceType.ENUM, TURNABOUT_CHOICE).get(0);

			SetGenerator objects = Intersect.instance(ControlledBy.instance(Identity.fromCollection(parameters.get(Parameter.TARGET))), HasType.instance(choice));

			EventFactory tapEvent = new EventFactory(EventType.TAP_PERMANENTS, "Tap all untapped permanents of the chosen type target player controls.");
			tapEvent.parameters.put(Parameter.CAUSE, Identity.fromCollection(parameters.get(Parameter.CAUSE)));
			tapEvent.parameters.put(Parameter.OBJECT, Intersect.instance(objects, Untapped.instance()));

			EventFactory untapEvent = new EventFactory(EventType.UNTAP_PERMANENTS, "Untap all tapped permanents of the chosen type target player controls.");
			untapEvent.parameters.put(Parameter.CAUSE, Identity.fromCollection(parameters.get(Parameter.CAUSE)));
			untapEvent.parameters.put(Parameter.OBJECT, Intersect.instance(objects, Tapped.instance()));

			java.util.Map<Parameter, Set> mayParameters = new java.util.HashMap<Parameter, Set>();
			mayParameters.put(Parameter.PLAYER, new Set(chooser));
			mayParameters.put(Parameter.EVENT, new Set(tapEvent, untapEvent));
			return createEvent(game, "Tap all untapped permanents of the chosen type target player controls, or untap all tapped permanents of that type that player controls.", EventType.CHOOSE_AND_PERFORM, mayParameters).perform(event, false);
		}
	};

	public Turnabout(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.TARGET, targetedBy(target));
		this.addEffect(new EventFactory(TURNABOUT_EFFECT, parameters, "Choose artifact, creature, or land. Tap all untapped permanents of the chosen type target player controls, or untap all tapped permanents of that type that player controls."));
	}
}
