package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Deadly Recluse")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class DeadlyRecluse extends Card
{
	public DeadlyRecluse(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
