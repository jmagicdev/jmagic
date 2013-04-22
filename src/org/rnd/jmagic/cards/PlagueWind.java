package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Plague Wind")
@Types({Type.SORCERY})
@ManaCost("7BB")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PROPHECY, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class PlagueWind extends Card
{
	public PlagueWind(GameState state)
	{
		super(state);

		SetGenerator affectedCreatures = RelativeComplement.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance()));

		this.addEffects(bury(this, affectedCreatures, "Destroy all creatures you don't control. They can't be regenerated."));
	}
}
