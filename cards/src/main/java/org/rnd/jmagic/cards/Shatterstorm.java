package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Shatterstorm")
@Types({Type.SORCERY})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ANTIQUITIES, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Shatterstorm extends Card
{
	public Shatterstorm(GameState state)
	{
		super(state);

		this.addEffects(bury(this, org.rnd.jmagic.engine.generators.ArtifactPermanents.instance(), "Destroy all artifacts. They can't be regenerated."));
	}
}
