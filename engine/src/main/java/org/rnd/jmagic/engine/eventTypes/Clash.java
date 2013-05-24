package org.rnd.jmagic.engine.eventTypes;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Clash extends EventType
{	public static final EventType INSTANCE = new Clash();

	 private Clash()
	{
		super("CLASH");
	}

	@Override
	public Parameter affects()
	{
		return Parameter.PLAYER;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		// 701.20. Clash
		//
		// 701.20a To clash, a player reveals the top card of his or her
		// library. That player may then put that card on the bottom of his
		// or her library.
		//
		// ...
		//
		// 701.20c A player wins a clash if that player revealed a card with
		// a higher converted mana cost than all other cards revealed in
		// that clash.

		Set players = parameters.get(Parameter.PLAYER);
		Set revealed = TopCards.instance(1, LibraryOf.instance(Identity.instance(players))).evaluate(game, null);

		java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
		revealParameters.put(EventType.Parameter.CAUSE, parameters.get(Parameter.CAUSE));
		revealParameters.put(EventType.Parameter.OBJECT, revealed);
		Event revealEvent = createEvent(game, "Each player clashing reveals the top card of his or her library.", EventType.REVEAL, revealParameters);
		revealEvent.perform(event, false);

		GameObject highestCMC = null;
		int cmc = -1;
		for(GameObject object: revealEvent.getResult().getAll(GameObject.class))
		{
			int newCmc = object.getConvertedManaCost();
			if(newCmc == cmc)
				highestCMC = null;
			else if(newCmc > cmc)
			{
				cmc = newCmc;
				highestCMC = object;
			}
		}

		Player winner = (highestCMC == null ? null : highestCMC.getZone().getOwner(game.actualState));

		for(Player participant: game.actualState.apnapOrder(players))
		{
			EventFactory bottom = new EventFactory(EventType.PUT_INTO_LIBRARY, "Put that card on the bottom of his or her library.");
			bottom.parameters.put(Parameter.CAUSE, Identity.instance(parameters.get(Parameter.CAUSE)));
			bottom.parameters.put(Parameter.INDEX, numberGenerator(-1));
			bottom.parameters.put(Parameter.OBJECT, TopCards.instance(1, LibraryOf.instance(Identity.instance(participant))));
			playerMay(Identity.instance(participant), bottom, "That player may then put that card on the bottom of his or her library.").createEvent(game, event.getSource()).perform(event, false);
		}

		if(winner == null)
			event.setResult(Empty.set);
		else
			event.setResult(Identity.instance(winner));
		return true;
	}
}