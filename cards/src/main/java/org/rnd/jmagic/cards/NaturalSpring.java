package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Natural Spring")
@Types({Type.SORCERY})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = PortalSecondAge.class, r = Rarity.COMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.COMMON), @Printings.Printed(ex = Portal.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class NaturalSpring extends Card
{
	public NaturalSpring(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(gainLife(targetedBy(target), 8, "Target player gains 8 life."));
	}
}
