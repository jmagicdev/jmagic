package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Burst of Speed")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BurstofSpeed extends Card
{
	public BurstofSpeed(GameState state)
	{
		super(state);

		SetGenerator youControl = ControlledBy.instance(You.instance());
		SetGenerator creaturesYouControl = Intersect.instance(CreaturePermanents.instance(), youControl);

		this.addEffect(addAbilityUntilEndOfTurn(creaturesYouControl, org.rnd.jmagic.abilities.keywords.Haste.class, "Creatures you control gain haste until end of turn."));
	}
}
