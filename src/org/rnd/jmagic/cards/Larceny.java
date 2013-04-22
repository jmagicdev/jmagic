package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Larceny")
@Types({Type.ENCHANTMENT})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Larceny extends Card
{
	public static final class MeleeDiscard extends EventTriggeredAbility
	{
		public MeleeDiscard(GameState state)
		{
			super(state, "Whenever a creature you control deals combat damage to a player, that player discards a card.");

			this.addPattern(whenDealsCombatDamageToAPlayer(CREATURES_YOU_CONTROL));

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(discardCards(thatPlayer, 1, "That player discards a card."));
		}
	}

	public Larceny(GameState state)
	{
		super(state);

		// Whenever a creature you control deals combat damage to a player, that
		// player discards a card.
		this.addAbility(new MeleeDiscard(state));
	}
}
