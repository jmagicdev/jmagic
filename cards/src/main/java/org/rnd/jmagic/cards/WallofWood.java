package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Wall of Wood")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class WallofWood extends Card
{
	public WallofWood(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
	}
}
