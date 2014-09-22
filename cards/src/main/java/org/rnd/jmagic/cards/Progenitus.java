package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Progenitus")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR, SubType.HYDRA})
@ManaCost("WWUUBBRRGG")
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK, Color.RED, Color.GREEN})
public final class Progenitus extends Card
{
	public Progenitus(GameState state)
	{
		super(state);

		this.setPower(10);
		this.setToughness(10);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, SetPattern.EVERYTHING, "everything"));

		this.addAbility(new org.rnd.jmagic.abilities.ColossusShuffle(state, this.getName()));
	}
}
