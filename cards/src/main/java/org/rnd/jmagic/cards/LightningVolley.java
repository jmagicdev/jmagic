package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Lightning Volley")
@Types({Type.INSTANT})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class LightningVolley extends Card
{
	public LightningVolley(GameState state)
	{
		super(state);

		// Until end of turn, creatures you control gain
		// "(T): This creature deals 1 damage to target creature or player."
		this.addEffect(addAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.Ping.class,//
				"Until end of turn, creatures you control gain \"(T): This creature deals 1 damage to target creature or player.\""));
	}
}
