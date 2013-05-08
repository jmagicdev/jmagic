package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Korozda Monitor")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class KorozdaMonitor extends Card
{
	public KorozdaMonitor(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Scavenge (5)(G)(G) ((5)(G)(G), Exile this card from your graveyard:
		// Put a number of +1/+1 counters equal to this card's power on target
		// creature. Scavenge only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Scavenge(state, "(5)(G)(G)"));
	}
}
