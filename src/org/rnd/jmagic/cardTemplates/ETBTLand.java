package org.rnd.jmagic.cardTemplates;

import org.rnd.jmagic.engine.*;

public abstract class ETBTLand extends Card
{
	/**
	 * Creates a land that enters the battlefield tapped and produces the
	 * specified colors of mana.
	 * 
	 * @param state The game state in which to create the land.
	 * @param tripleMana The mana for which this land will tap, without
	 * parentheses (e.g., "BRG");
	 */
	public ETBTLand(GameState state, String tripleMana)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, tripleMana));
	}
}
