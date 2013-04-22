package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Aladdin's Ring")
@Types({Type.ARTIFACT})
@ManaCost("8")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ARABIAN_NIGHTS, r = Rarity.RARE)})
@ColorIdentity({})
public final class AladdinsRing extends Card
{
	public static final class AladdinsRingAbility0 extends ActivatedAbility
	{
		public AladdinsRingAbility0(GameState state)
		{
			super(state, "(8), (T): Aladdin's Ring deals 4 damage to target creature or player.");
			this.setManaCost(new ManaPool("(8)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(4, target, "Aladdin's Ring deals 4 damage to target creature or player."));
		}
	}

	public AladdinsRing(GameState state)
	{
		super(state);

		// (8), (T): Aladdin's Ring deals 4 damage to target creature or player.
		this.addAbility(new AladdinsRingAbility0(state));
	}
}
