package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Caravan Hurda")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class CaravanHurda extends Card
{
	public CaravanHurda(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
	}
}
