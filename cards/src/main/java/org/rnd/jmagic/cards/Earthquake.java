package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Earthquake")
@Types({Type.SORCERY})
@ManaCost("XR")
@ColorIdentity({Color.RED})
public final class Earthquake extends Card
{
	public Earthquake(GameState state)
	{
		super(state);

		SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
		SetGenerator takers = RelativeComplement.instance(CREATURES_AND_PLAYERS, hasFlying);
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), takers, "Earthquake deals X damage to each creature without flying and each player."));
	}
}
