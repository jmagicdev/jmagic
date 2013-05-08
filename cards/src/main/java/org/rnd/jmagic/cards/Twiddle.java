package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Twiddle")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.BETA, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Twiddle extends Card
{
	public Twiddle(GameState state)
	{
		super(state);

		Target target = this.addTarget(Union.instance(CreaturePermanents.instance(), Union.instance(LandPermanents.instance(), ArtifactPermanents.instance())), "target artifact, creature, or land");

		this.addEffect(youMay(tapOrUntap(targetedBy(target), "target artifact, creature, or land"), "You may tap or untap target artifact, creature, or land."));
	}
}
