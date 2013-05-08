package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Shriek Raptor")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class ShriekRaptor extends Card
{
	public ShriekRaptor(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}
