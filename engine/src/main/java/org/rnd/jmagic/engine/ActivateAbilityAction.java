package org.rnd.jmagic.engine;

/** Represents the action a player takes in order to activate an ability. */
public class ActivateAbilityAction extends CastSpellOrActivateAbilityAction
{
	/**
	 * @param game The game in which this action is to be performed.
	 * @param ability The ability this action will cause a player to activate
	 * when it is performed.
	 */
	public ActivateAbilityAction(Game game, ActivatedAbility ability, Player activator, int source)
	{
		super(game, ability.toString(), java.util.Collections.singleton(0), activator, source);
		this.toBePlayedID = ability.ID;
	}

	@Override
	public PlayerInterface.ReversionParameters getReversionReason()
	{
		Player player = this.game.physicalState.get(this.actorID);
		ActivatedAbility ability = this.game.physicalState.get(this.toBePlayedID);
		return new PlayerInterface.ReversionParameters("ActivateAbilityAction", player.getName() + " failed to activate \"" + ability.getName() + "\".");
	}
}
