package org.rnd.jmagic.engine.eventTypes;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class DeclareBlockers extends EventType
{	public static final EventType INSTANCE = new DeclareBlockers();

	 private DeclareBlockers()
	{
		super("DECLARE_BLOCKERS");
	}

	@Override
	public Parameter affects()
	{
		return null;
	}

	@Override
	public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
	{
		Player activePlayer = game.actualState.currentTurn().getOwner(game.actualState);

		// 509.1. First, the defending player declares blockers. This
		// turn-based action doesn't use the stack.
		java.util.Set<Player> isAttacked = IsAttacked.get(game.actualState).getAll(Player.class);
		for(Player player: game.physicalState.getPlayerCycle(activePlayer))
		{
			if(!isAttacked.contains(player))
				continue;

			// Keep the instance around because it calculates information
			// needed to determine legality in each perform
			DeclareBlockersAction declareBlockersAction = new DeclareBlockersAction(game, player, event);
			while(!declareBlockersAction.saveStateAndPerform())
				// Fix the actual state in case declaring blockers failed
				game.refreshActualState();
		}

		activePlayer = activePlayer.getActual();

		// 509.2. Second, for each attacking creature that's become
		// blocked by multiple creatures, the active player announces
		// its damage assignment order among the blocking creatures.
		// This turn-based action doesn't use the stack.
		for(GameObject attacker: Attacking.get(game.actualState).getAll(GameObject.class))
		{
			if(attacker.getBlockedByIDs() != null && attacker.getBlockedByIDs().size() > 1)
			{
				java.util.List<GameObject> blockers = new java.util.LinkedList<GameObject>();
				for(int blockerID: attacker.getBlockedByIDs())
					blockers.add(game.actualState.<GameObject>get(blockerID));
				int numBlockers = blockers.size();
				PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(numBlockers, numBlockers, PlayerInterface.ChoiceType.OBJECTS_ORDERED, PlayerInterface.ChooseReason.DAMAGE_ASSIGNMENT_ORDER);
				chooseParameters.thisID = attacker.ID;
				blockers = activePlayer.sanitizeAndChoose(game.actualState, blockers, chooseParameters);
				attacker = attacker.getPhysical();
				for(int i = 0; i < numBlockers; i++)
					attacker.getBlockedByIDs().set(i, blockers.get(i).ID);
			}
		}

		// 509.3. Third, for each creature that's blocking multiple
		// creatures (because some effect allows it to), the defending
		// player announces its damage assignment order among the
		// attacking creatures. This turn-based action doesn't use the
		// stack.
		for(Player player: IsAttacked.get(game.actualState).getAll(Player.class))
		{
			for(GameObject blocker: Blocking.get(game.actualState).getAll(GameObject.class))
			{
				if(blocker.controllerID == player.ID && blocker.getBlockingIDs().size() > 1)
				{
					java.util.List<GameObject> attackers = new java.util.LinkedList<GameObject>();
					for(int attackerID: blocker.getBlockingIDs())
						attackers.add(game.actualState.<GameObject>get(attackerID));
					int numAttackers = attackers.size();
					PlayerInterface.ChooseParameters<java.io.Serializable> chooseParameters = new PlayerInterface.ChooseParameters<java.io.Serializable>(numAttackers, numAttackers, PlayerInterface.ChoiceType.OBJECTS_ORDERED, PlayerInterface.ChooseReason.DAMAGE_ASSIGNMENT_ORDER);
					chooseParameters.thisID = blocker.ID;
					attackers = player.sanitizeAndChoose(game.actualState, attackers, chooseParameters);
					blocker = blocker.getPhysical();
					for(int i = 0; i < numAttackers; i++)
						blocker.getBlockingIDs().set(i, attackers.get(i).ID);
				}
			}
		}

		event.setResult(Empty.set);
		return true;
	}
}