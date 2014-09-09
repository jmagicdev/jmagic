package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Manabarbs")
@Types({Type.ENCHANTMENT})
@ManaCost("3R")
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
