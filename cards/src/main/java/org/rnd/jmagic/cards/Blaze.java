package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blaze")
@Types({Type.SORCERY})
@ManaCost("XR")
@ColorIdentity({Color.RED})
public final class Blaze extends Card
{
	public Blaze(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), targetedBy(target), "Blaze deals X damage to target creature or player."));
	}
}
