package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Llanowar Reborn")
@Types({Type.LAND})
@ColorIdentity({Color.GREEN})
public final class LlanowarReborn extends Card
{
	public LlanowarReborn(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Graft(state, 1));
	}
}
