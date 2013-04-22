package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Bull Cerodon")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4RW")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class BullCerodon extends Card
{
	public BullCerodon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
