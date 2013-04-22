package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Indomitable Ancients")
@Types({Type.CREATURE})
@SubTypes({SubType.TREEFOLK, SubType.WARRIOR})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class IndomitableAncients extends Card
{
	public IndomitableAncients(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(10);
	}
}
