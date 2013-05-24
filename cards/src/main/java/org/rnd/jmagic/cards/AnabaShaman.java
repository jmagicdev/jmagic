package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Anaba Shaman")
@Types({Type.CREATURE})
@SubTypes({SubType.MINOTAUR, SubType.SHAMAN})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.HOMELANDS, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class AnabaShaman extends Card
{
	public static final class AnabaShamanAbility0 extends ActivatedAbility
	{
		public AnabaShamanAbility0(GameState state)
		{
			super(state, "(R), (T): Anaba Shaman deals 1 damage to target creature or player.");
			this.setManaCost(new ManaPool("(R)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(1, target, "Anaba Shaman deals 1 damage to target creature or player."));
		}
	}

	public AnabaShaman(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (R), (T): Anaba Shaman deals 1 damage to target creature or player.
		this.addAbility(new AnabaShamanAbility0(state));
	}
}
