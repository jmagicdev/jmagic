package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class TapForManaPain extends org.rnd.jmagic.abilities.TapForMana
{
	private String permanentName;
	private String painMana;

	/**
	 * T: Add [mana] to your mana pool. This permanent deals 1 damage to you.
	 * 
	 * @param state The game state in which this ability should exist.
	 * @param permanentName The name of the permanent this ability is on.
	 * @param painMana The letters representing the mana choices. Do not use
	 * parentheses.
	 */
	public TapForManaPain(GameState state, String permanentName, String painMana)
	{
		super(state, "(" + painMana + ")");

		this.permanentName = permanentName;
		this.setName(this.getName() + " " + permanentName + " deals 1 damage to you.");
		this.painMana = painMana;

		this.addEffect(permanentDealDamage(1, You.instance(), (permanentName + " deals 1 damage to you.")));
	}

	@Override
	public TapForManaPain create(Game game)
	{
		return new TapForManaPain(game.physicalState, this.permanentName, this.painMana);
	}
}