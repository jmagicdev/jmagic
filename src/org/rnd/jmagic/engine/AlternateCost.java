package org.rnd.jmagic.engine;

/**
 * Represents a cost that can be paid for a card instead of that card's mana
 * cost. Created by {@link ContinuousEffectType#ALTERNATE_COST}, and used
 * whenever the game, an effect, or a cost asks a player to pay a card's mana
 * cost (for example, when casting it, or exiling it to Back from the Brink).
 */
public class AlternateCost
{
	public CostCollection cost;
	public java.util.Set<Player> playersMayPay;

	public AlternateCost(CostCollection cost, java.util.Set<Player> playersMayPay)
	{
		this.cost = cost;
		this.playersMayPay = playersMayPay;
	}
}
