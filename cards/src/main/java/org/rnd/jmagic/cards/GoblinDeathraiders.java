package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Goblin Deathraiders")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("BR")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class GoblinDeathraiders extends Card
{
	public GoblinDeathraiders(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
