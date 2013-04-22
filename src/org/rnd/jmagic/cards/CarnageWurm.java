package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Carnage Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("6G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class CarnageWurm extends Card
{
	public CarnageWurm(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Bloodthirst 3 (If an opponent was dealt damage this turn, this
		// creature enters the battlefield with three +1/+1 counters on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bloodthirst.Final(state, 3));

		// Trample (If this creature would assign enough damage to its blockers
		// to destroy them, you may have it assign the rest of its damage to
		// defending player or planeswalker.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));
	}
}
