package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Precinct Captain")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class PrecinctCaptain extends Card
{
	public static final class PrecinctCaptainAbility1 extends EventTriggeredAbility
	{
		public PrecinctCaptainAbility1(GameState state)
		{
			super(state, "Whenever Precinct Captain deals combat damage to a player, put a 1/1 white Soldier creature token onto the battlefield.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Soldier creature token onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.SOLDIER);
			this.addEffect(token.getEventFactory());
		}
	}

	public PrecinctCaptain(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		// Whenever Precinct Captain deals combat damage to a player, put a 1/1
		// white Soldier creature token onto the battlefield.
		this.addAbility(new PrecinctCaptainAbility1(state));
	}
}
