package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class ClashWithAnOpponent extends EventType
{	public static final EventType INSTANCE = new ClashWithAnOpponent();

	 private ClashWithAnOpponent()
	{
		super("CLASH_WITH_AN_OPPONENT");
	}

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

		Set players = Set.fromCollection(choice);
		players.add(player);

		java.util.Map<Parameter, Set> clashParameters = new java.util.HashMap<Parameter, Set>();
		clashParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		clashParameters.put(EventType.Parameter.PLAYER, players);
		Event clashEvent = createEvent(game, "You and an opponent each clash.", EventType.CLASH, clashParameters);
		clashEvent.perform(event, false);

		event.setResult(clashEvent.getResult());
		return true;
	}
}