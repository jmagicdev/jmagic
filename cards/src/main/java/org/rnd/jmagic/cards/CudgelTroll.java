package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Cudgel Troll")
@Types({Type.CREATURE})
@SubTypes({SubType.TROLL})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class CudgelTroll extends Card
{

	public CudgelTroll(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// {G}: Regenerate Cudgel Troll. (The next time this creature would be
		// destroyed this turn, it isn't. Instead tap it, remove all damage from
		// it, and remove it from combat.)
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(G)", this.getName()));
	}
}
