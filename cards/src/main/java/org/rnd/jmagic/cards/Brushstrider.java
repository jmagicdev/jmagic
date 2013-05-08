package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Brushstrider")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Brushstrider extends Card
{
	public Brushstrider(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
