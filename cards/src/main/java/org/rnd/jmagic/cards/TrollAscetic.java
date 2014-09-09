package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Troll Ascetic")
@Types({Type.CREATURE})
@SubTypes({SubType.TROLL, SubType.SHAMAN})
@ManaCost("1GG")
@ColorIdentity({Color.GREEN})
public final class TrollAscetic extends Card
{
	public TrollAscetic(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(1)(G)", this.getName()));
	}
}
