package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Desert Twister")
@Types({Type.SORCERY})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = MercadianMasques.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ArabianNights.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class DesertTwister extends Card
{
	public DesertTwister(GameState state)
	{
		super(state);

		// Destroy target permanent.
		SetGenerator target = targetedBy(this.addTarget(Permanents.instance(), "target permanent"));
		this.addEffect(destroy(target, "Destroy target permanent."));
	}
}
