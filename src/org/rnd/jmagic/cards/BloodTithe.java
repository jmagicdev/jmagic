package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blood Tithe")
@Types({Type.SORCERY})
@ManaCost("3B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BloodTithe extends Card
{
	public BloodTithe(GameState state)
	{
		super(state);

		// Each opponent loses 3 life.
		EventFactory loseLife = loseLife(OpponentsOf.instance(You.instance()), 3, "Each opponent loses 3 life.");
		this.addEffect(loseLife);

		// You gain life equal to the life lost this way.
		SetGenerator amount = EffectResult.instance(loseLife);
		this.addEffect(gainLife(You.instance(), amount, "You gain life equal to the life lost this way."));
	}
}
