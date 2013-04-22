package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Umara Raptor")
@Types({Type.CREATURE})
@SubTypes({SubType.ALLY, SubType.BIRD})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class UmaraRaptor extends Card
{
	public UmaraRaptor(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.ZendikarAllyCounter(state, this.getName()));
	}
}
