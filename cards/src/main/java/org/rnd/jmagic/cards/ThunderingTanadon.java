package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Thundering Tanadon")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4(G/P)(G/P)")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class ThunderingTanadon extends Card
{
	public ThunderingTanadon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
