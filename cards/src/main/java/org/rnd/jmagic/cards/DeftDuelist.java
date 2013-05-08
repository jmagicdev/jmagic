package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Deft Duelist")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.HUMAN})
@ManaCost("WU")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class DeftDuelist extends Card
{
	public DeftDuelist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));
	}
}
