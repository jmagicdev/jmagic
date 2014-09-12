package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Siege Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("5GG")
@ColorIdentity({Color.GREEN})
public final class SiegeWurm extends Card
{
	public SiegeWurm(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Trample (If this creature would assign enough damage to its blockers
		// to destroy them, you may have it assign the rest of its damage to
		// defending player or planeswalker.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
