package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Rain of Tears")
@Types({Type.SORCERY})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Portal.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class RainofTears extends Card
{
	public RainofTears(GameState state)
	{
		super(state);

		Target target = this.addTarget(LandPermanents.instance(), "target land");
		this.addEffect(destroy(targetedBy(target), "Destroy target land."));
	}
}
