package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Stealer of Secrets")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.HUMAN})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class StealerofSecrets extends Card
{
	public static final class StealerofSecretsAbility0 extends EventTriggeredAbility
	{
		public StealerofSecretsAbility0(GameState state)
		{
			super(state, "Whenever Stealer of Secrets deals combat damage to a player, draw a card.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.addEffect(drawACard());
		}
	}

	public StealerofSecrets(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever Stealer of Secrets deals combat damage to a player, draw a
		// card.
		this.addAbility(new StealerofSecretsAbility0(state));
	}
}
