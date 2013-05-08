package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Silverpelt Werewolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class SilverpeltWerewolf extends AlternateCard
{
	public static final class SilverpeltWerewolfAbility0 extends EventTriggeredAbility
	{
		public SilverpeltWerewolfAbility0(GameState state)
		{
			super(state, "Whenever Silverpelt Werewolf deals combat damage to a player, draw a card.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			this.addEffect(drawACard());
		}
	}

	public SilverpeltWerewolf(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		this.setColorIndicator(Color.GREEN);

		// Whenever Silverpelt Werewolf deals combat damage to a player, draw a
		// card.
		this.addAbility(new SilverpeltWerewolfAbility0(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Silverpelt Werewolf.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
