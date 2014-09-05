package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Plague Wind")
@Types({Type.SORCERY})
@ManaCost("7BB")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Prophecy.class, r = Rarity.RARE)})
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
