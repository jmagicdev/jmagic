package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Boggart Harbinger")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.GOBLIN})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class BoggartHarbinger extends Card
{
	public BoggartHarbinger(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.HarbingerAbility(state, this.getName(), SubType.GOBLIN));
	}
}
