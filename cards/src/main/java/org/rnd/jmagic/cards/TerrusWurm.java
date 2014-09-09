package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Terrus Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.WURM})
@ManaCost("6B")
@ColorIdentity({Color.BLACK})
public final class TerrusWurm extends Card
{
	public TerrusWurm(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Scavenge (6)(B) ((6)(B), Exile this card from your graveyard: Put a
		// number of +1/+1 counters equal to this card's power on target
		// creature. Scavenge only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Scavenge(state, "(6)(B)"));
	}
}
