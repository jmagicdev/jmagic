package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Soul Feast")
@Types({Type.SORCERY})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasDestiny.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class SoulFeast extends Card
{
	public SoulFeast(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		this.addEffect(loseLife(targetedBy(target), 4, "Target player loses 4 life"));
		this.addEffect(gainLife(You.instance(), 4, "and you gain 4 life."));
	}
}
