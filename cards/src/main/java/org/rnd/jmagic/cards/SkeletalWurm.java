package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Skeletal Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM, SubType.SKELETON})
@ManaCost("7B")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class SkeletalWurm extends Card
{
	public SkeletalWurm(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(6);

		// (B): Regenerate Skeletal Wurm.
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(B)", this.getName()));
	}
}
