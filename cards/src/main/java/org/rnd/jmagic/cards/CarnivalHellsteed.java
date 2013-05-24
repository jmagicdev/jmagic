package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Carnival Hellsteed")
@Types({Type.CREATURE})
@SubTypes({SubType.NIGHTMARE, SubType.HORSE})
@ManaCost("4BR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class CarnivalHellsteed extends Card
{
	public CarnivalHellsteed(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(4);

		// First strike, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Unleash (You may have this creature enter the battlefield with a
		// +1/+1 counter on it. It can't block as long as it has a +1/+1 counter
		// on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unleash(state));
	}
}
