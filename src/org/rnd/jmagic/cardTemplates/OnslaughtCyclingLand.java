package org.rnd.jmagic.cardTemplates;

import org.rnd.jmagic.engine.*;

public abstract class OnslaughtCyclingLand extends Card
{
	/**
	 * Constructs a new Onslaught cycling land.
	 * 
	 * @param state The state in which to construct this land.
	 * @param mana The mana the land produces, as well as the cycling cost of
	 * this land. Include parentheses around the mana symbol.
	 */
	public OnslaughtCyclingLand(GameState state, String mana)
	{
		super(state);

		// Tranquil Thicket enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, mana));

		// Cycling (G)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, mana));
	}
}