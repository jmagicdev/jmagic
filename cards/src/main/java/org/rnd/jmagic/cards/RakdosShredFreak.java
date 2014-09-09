package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Rakdos Shred-Freak")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.HUMAN})
@ManaCost("(B/R)(B/R)")
@ColorIdentity({Color.BLACK, Color.RED})
public final class RakdosShredFreak extends Card
{
	public RakdosShredFreak(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
