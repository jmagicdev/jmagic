package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Swords to Plowshares")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = IceAge.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class SwordstoPlowshares extends Card
{
	public SwordstoPlowshares(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));

		// Exile target creature. Its controller gains life equal to its power.
		this.addEffect(exile(target, "Exile target creature."));
		this.addEffect(gainLife(ControllerOf.instance(target), PowerOf.instance(target), "Its controller gains life equal to its power."));
	}
}
