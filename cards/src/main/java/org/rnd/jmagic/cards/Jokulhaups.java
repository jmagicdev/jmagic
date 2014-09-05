package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Jokulhaups")
@Types({Type.SORCERY})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = IceAge.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Jokulhaups extends Card
{
	public Jokulhaups(GameState state)
	{
		super(state);

		this.addEffects(bury(this, Union.instance(Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), LandPermanents.instance()), "Destroy all artifacts, creatures, and lands. They can't be regenerated."));
	}
}
