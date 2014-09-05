package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Counterspell")
@Types({Type.INSTANT})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter2000.class, r = Rarity.COMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.COMMON), @Printings.Printed(ex = Starter1999.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Tempest.class, r = Rarity.COMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = IceAge.class, r = Rarity.COMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Counterspell extends Card
{
	public Counterspell(GameState state)
	{
		super(state);

		Target target = this.addTarget(Spells.instance(), "target spell");

		this.addEffect(counter(targetedBy(target), "Counter target spell."));
	}
}
