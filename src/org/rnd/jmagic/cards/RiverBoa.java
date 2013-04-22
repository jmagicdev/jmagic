package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("River Boa")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.VISIONS, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class RiverBoa extends Card
{
	public RiverBoa(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));

		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(G)", "River Boa"));
	}
}
