package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Plumeveil")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("(W/U)(W/U)(W/U)")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Shadowmoor.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class Plumeveil extends Card
{
	public Plumeveil(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Defender, flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
