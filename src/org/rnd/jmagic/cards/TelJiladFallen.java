package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Tel-Jilad Fallen")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ELF})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class TelJiladFallen extends Card
{
	public TelJiladFallen(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Protection from artifacts
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.FromArtifacts(state));

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}
