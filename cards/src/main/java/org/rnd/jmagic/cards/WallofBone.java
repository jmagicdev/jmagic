package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wall of Bone")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL, SubType.SKELETON})
@ManaCost("2B")
@ColorIdentity({Color.BLACK})
public final class WallofBone extends Card
{
	public WallofBone(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(B)", this.getName()));
	}
}
