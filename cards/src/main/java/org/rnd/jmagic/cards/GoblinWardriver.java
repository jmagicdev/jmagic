package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Goblin Wardriver")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("RR")
@ColorIdentity({Color.RED})
public final class GoblinWardriver extends Card
{
	public GoblinWardriver(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Battle cry (Whenever this creature attacks, each other attacking
		// creature gets +1/+0 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.BattleCry(state));
	}
}
