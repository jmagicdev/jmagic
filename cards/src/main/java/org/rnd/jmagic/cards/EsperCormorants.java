package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Esper Cormorants")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("2WU")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class EsperCormorants extends Card
{
	public EsperCormorants(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
