package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

import static org.rnd.jmagic.Convenience.*;

@Name("Twiddle")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = EighthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.COMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.COMMON)})
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
