package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Oran-Rief Survivalist")
@Types({Type.CREATURE})
@SubTypes({SubType.ALLY, SubType.WARRIOR, SubType.HUMAN})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class OranRiefSurvivalist extends Card
{
	public OranRiefSurvivalist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.ZendikarAllyCounter(state, this.getName()));
	}
}
