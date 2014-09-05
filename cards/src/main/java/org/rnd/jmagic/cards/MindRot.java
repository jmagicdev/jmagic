package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mind Rot")
@Types({Type.SORCERY})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = ReturnToRavnica.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.COMMON), @Printings.Printed(ex = PortalSecondAge.class, r = Rarity.COMMON), @Printings.Printed(ex = Portal.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MindRot extends Card
{
	public MindRot(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");
		this.addEffect(discardCards(targetedBy(target), 2, "Target player discards two cards."));
	}
}
