package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Winged Coatl")
@Types({Type.CREATURE})
@SubTypes({SubType.SNAKE})
@ManaCost("1GU")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class WingedCoatl extends Card
{
	public WingedCoatl(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
