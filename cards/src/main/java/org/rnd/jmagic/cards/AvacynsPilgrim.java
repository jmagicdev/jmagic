package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Avacyn's Pilgrim")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.HUMAN})
@ManaCost("G")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class AvacynsPilgrim extends Card
{
	public AvacynsPilgrim(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): Add (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));
	}
}
