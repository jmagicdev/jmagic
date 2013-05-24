package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Memnite")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("0")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Memnite extends Card
{
	public Memnite(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);
	}
}
