package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bad Moon")
@Types({Type.ENCHANTMENT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.SPECIAL), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class BadMoon extends Card
{
	public static final class BadMoonAbility0 extends StaticAbility
	{
		public BadMoonAbility0(GameState state)
		{
			super(state, "Black creatures get +1/+1.");

			SetGenerator black = HasColor.instance(Color.BLACK);
			SetGenerator blackCreatures = Intersect.instance(black, CreaturePermanents.instance());
			this.addEffectPart(modifyPowerAndToughness(blackCreatures, +1, +1));
		}
	}

	public BadMoon(GameState state)
	{
		super(state);

		// Black creatures get +1/+1.
		this.addAbility(new BadMoonAbility0(state));
	}
}
