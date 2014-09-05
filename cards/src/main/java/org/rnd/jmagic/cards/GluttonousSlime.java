package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Gluttonous Slime")
@Types({Type.CREATURE})
@SubTypes({SubType.OOZE})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class GluttonousSlime extends Card
{
	public GluttonousSlime(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Devour 1
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Devour(state, 1));
	}
}
