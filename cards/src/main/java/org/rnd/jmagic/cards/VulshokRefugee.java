package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Vulshok Refugee")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class VulshokRefugee extends Card
{
	public VulshokRefugee(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Protection from red
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromRed(state));
	}
}
