package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Azusa, Lost but Seeking")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.MONK})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = ChampionsOfKamigawa.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class AzusaLostbutSeeking extends Card
{
	public AzusaLostbutSeeking(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		this.addAbility(new PlayExtraLands.Final(state, 2, "You may play two additional lands on each of your turns."));
	}
}
