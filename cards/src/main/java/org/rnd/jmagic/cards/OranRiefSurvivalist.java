package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Oran-Rief Survivalist")
@Types({Type.CREATURE})
@SubTypes({SubType.ALLY, SubType.WARRIOR, SubType.HUMAN})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
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
