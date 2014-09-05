package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Azure Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Legends.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class AzureDrake extends Card
{
	public AzureDrake(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
