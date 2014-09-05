package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Aladdin's Ring")
@Types({Type.ARTIFACT})
@ManaCost("8")
@Printings({@Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = ArabianNights.class, r = Rarity.RARE)})
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
