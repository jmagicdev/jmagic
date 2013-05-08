package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Manabarbs")
@Types({Type.ENCHANTMENT})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Manabarbs extends Card
{
	// Whenever a player taps a land for mana, Manabarbs deals 1 damage to that
	// player.
	public static final class ManaEqualsDamage extends EventTriggeredAbility
	{
		public ManaEqualsDamage(GameState state)
		{
			super(state, "Whenever a player taps a land for mana, Manabarbs deals 1 damage to that player.");

			this.addPattern(tappedForMana(Players.instance(), landPermanents()));

			SetGenerator activateAbilityEvent = TriggerEvent.instance(This.instance());
			SetGenerator player = EventParameter.instance(activateAbilityEvent, EventType.Parameter.PLAYER);

			this.addEffect(permanentDealDamage(1, player, "Manabarbs deals 1 damage to that player."));
		}
	}

	public Manabarbs(GameState state)
	{
		super(state);

		this.addAbility(new ManaEqualsDamage(state));
	}
}
