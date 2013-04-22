package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Natural Spring")
@Types({Type.SORCERY})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.STARTER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.PORTAL_SECOND_AGE, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PORTAL, r = Rarity.UNCOMMON)})
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
