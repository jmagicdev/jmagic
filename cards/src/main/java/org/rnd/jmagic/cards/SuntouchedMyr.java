package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Suntouched Myr")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.MYR})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class SuntouchedMyr extends Card
{
	public SuntouchedMyr(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Sunburst(state));
	}
}
