package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Seismic Shudder")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SeismicShudder extends Card
{
	public SeismicShudder(GameState state)
	{
		super(state);

		SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
		SetGenerator takers = RelativeComplement.instance(CreaturePermanents.instance(), hasFlying);

		this.addEffect(spellDealDamage(1, takers, "Seismic Shudder deals 1 damage to each creature without flying."));
	}
}
