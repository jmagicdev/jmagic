package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Tombstalker")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("6BB")
@Printings({@Printings.Printed(ex = FutureSight.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class Tombstalker extends Card
{
	public Tombstalker(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Delve (You may exile any number of cards from your graveyard as you
		// cast this spell. It costs (1) less to cast for each card exiled this
		// way.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));
	}
}
