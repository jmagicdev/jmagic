package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Manabarbs")
@Types({Type.ENCHANTMENT})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2010.class, r = Rarity.RARE), @Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
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
