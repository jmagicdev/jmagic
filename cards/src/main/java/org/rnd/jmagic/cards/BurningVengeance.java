package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Burning Vengeance")
@Types({Type.ENCHANTMENT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class BurningVengeance extends Card
{
	public static final class BurningVengeanceAbility0 extends EventTriggeredAbility
	{
		public BurningVengeanceAbility0(GameState state)
		{
			super(state, "Whenever you cast a spell from your graveyard, Burning Vengeance deals 2 damage to target creature or player.");

			this.addPattern(whenYouCastASpellFromYourGraveyard());

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(2, target, "Burning Vengeance deals 2 damage to target creature or player."));
		}
	}

	public BurningVengeance(GameState state)
	{
		super(state);

		// Whenever you cast a spell from your graveyard, Burning Vengeance
		// deals 2 damage to target creature or player.
		this.addAbility(new BurningVengeanceAbility0(state));
	}
}
