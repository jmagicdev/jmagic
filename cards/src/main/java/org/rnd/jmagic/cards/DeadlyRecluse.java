package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Deadly Recluse")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class DeadlyRecluse extends Card
{
	public DeadlyRecluse(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
