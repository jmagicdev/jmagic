package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Regrowth")
@Types({Type.SORCERY})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Regrowth extends Card
{
	public Regrowth(GameState state)
	{
		super(state);

		// Return target card from your graveyard to your hand.
		SetGenerator yourGraveyard = GraveyardOf.instance(You.instance());
		SetGenerator inYard = InZone.instance(yourGraveyard);
		SetGenerator target = targetedBy(this.addTarget(inYard, "target card from your graveyard"));
		this.addEffect(putIntoHand(target, You.instance(), "Return target card from your graveyard to your hand."));
	}
}
