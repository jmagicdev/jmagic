package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Thornweald Archer")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.ARCHER})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class ThornwealdArcher extends Card
{
	public ThornwealdArcher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));
	}
}
