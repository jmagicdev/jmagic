package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Shatterstorm")
@Types({Type.SORCERY})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Antiquities.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Shatterstorm extends Card
{
	public Shatterstorm(GameState state)
	{
		super(state);

		this.addEffects(bury(this, org.rnd.jmagic.engine.generators.ArtifactPermanents.instance(), "Destroy all artifacts. They can't be regenerated."));
	}
}
