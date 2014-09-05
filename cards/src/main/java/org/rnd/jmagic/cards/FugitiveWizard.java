package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Fugitive Wizard")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("U")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Legions.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class FugitiveWizard extends Card
{
	public FugitiveWizard(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);
	}
}
