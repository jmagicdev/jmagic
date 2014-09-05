package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Divine Offering")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Mirage.class, r = Rarity.COMMON), @Printings.Printed(ex = Legends.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class DivineOffering extends Card
{
	public DivineOffering(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));

		// Destroy target artifact. You gain life equal to its converted mana
		// cost.
		this.addEffect(destroy(target, "Destroy target artifact."));
		this.addEffect(gainLife(You.instance(), ConvertedManaCostOf.instance(target), "You gain life equal to its converted mana cost."));
	}
}
