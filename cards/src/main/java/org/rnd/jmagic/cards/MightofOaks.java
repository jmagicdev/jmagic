package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Might of Oaks")
@Types({Type.INSTANT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.RARE), @Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UrzasLegacy.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class MightofOaks extends Card
{
	public MightofOaks(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (+7), (+7), "Target creature gets +7/+7 until end of turn."));
	}
}
