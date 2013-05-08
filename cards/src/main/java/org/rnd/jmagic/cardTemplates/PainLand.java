package org.rnd.jmagic.cardTemplates;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;

public abstract class PainLand extends Card
{
	public PainLand(GameState state, String painMana)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
		this.addAbility(new TapForManaPain(state, this.getName(), painMana));
	}
}
