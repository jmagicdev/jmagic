package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Divine Offering")
@Types({Type.INSTANT})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.LEGENDS, r = Rarity.COMMON)})
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
