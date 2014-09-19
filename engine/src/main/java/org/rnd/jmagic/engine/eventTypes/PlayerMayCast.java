package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class PlayerMayCast extends EventType
{
	public static final EventType INSTANCE = new PlayerMayCast();

	private PlayerMayCast()
	{
		super("PLAYER_MAY_CAST");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.OBJECT;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		GameObject spell = parameters.get(Parameter.OBJECT).getOne(GameObject.class);
		Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

		event.setResult(Empty.set);
		java.util.List<Integer> canCast = PlayProhibition.getAllowed(game.actualState, player, spell);
		if(canCast.isEmpty())
			return false;

		CastSpellAction action = new CastSpellAction(game, spell, canCast, player, spell.ID);

		if(parameters.containsKey(Parameter.ALTERNATE_COST))
			action.forceAlternateCost(Identity.fromCollection(parameters.get(Parameter.ALTERNATE_COST)));

		PlayerInterface.ChooseParameters<Answer> chooseParameters = new PlayerInterface.ChooseParameters<Answer>(1, 1, new java.util.LinkedList<Answer>(Answer.mayChoices()), PlayerInterface.ChoiceType.ENUM, PlayerInterface.ChooseReason.MAY_CAST);
		chooseParameters.thisID = spell.ID;
		Answer answer = player.choose(chooseParameters).get(0);

		if(answer == Answer.YES)
			if(action.saveStateAndPerform())
			{
				event.setResult(new Set(game.actualState.get(spell.getActual().futureSelf)));
				return true;
			}

		return false;
	}
}