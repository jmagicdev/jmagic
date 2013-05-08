package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Pale Recluse")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("4GW")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class PaleRecluse extends Card
{
	public PaleRecluse(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.ForestCycling(state, "(2)"));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.PlainsCycling(state, "(2)"));
	}
}
