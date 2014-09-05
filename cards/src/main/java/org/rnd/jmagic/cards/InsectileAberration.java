package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Insectile Aberration")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.INSECT})
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class InsectileAberration extends AlternateCard
{
	public InsectileAberration(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.setColorIndicator(Color.BLUE);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
	}
}
