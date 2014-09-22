package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Ethercaste Knight")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class EthercasteKnight extends Card
{
	public EthercasteKnight(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));
	}
}
