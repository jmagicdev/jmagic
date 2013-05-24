package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Lightning Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("1RWU")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.SPECIAL), @Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.RED})
public final class LightningAngel extends Card
{
	public LightningAngel(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying, vigilance, haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
