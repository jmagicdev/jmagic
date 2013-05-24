/**
 * 
 */
package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

/**
 * (T): This creature deals 1 damage to target creature or player.
 */
public final class Ping extends ActivatedAbility
{
	private final String cardName;

	/**
	 * Constructs a Ping where the creature name is 'This creature'.
	 */
	public Ping(GameState state)
	{
		this(state, "This creature");
	}

	public Ping(GameState state, String cardName)
	{
		super(state, "(T): " + cardName + " deals 1 damage to target creature or player.");
		this.cardName = cardName;

		this.costsTap = true;

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(permanentDealDamage(1, targetedBy(target), cardName + " deals 1 damage to target creature or player."));
	}

	@Override
	public Ping create(Game game)
	{
		return new Ping(game.physicalState, this.cardName);
	}
}