package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wall of Mulch")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class WallofMulch extends Card
{
	public static final class WallofMulchAbility1 extends ActivatedAbility
	{
		public WallofMulchAbility1(GameState state)
		{
			super(state, "(G), Sacrifice a Wall: Draw a card.");
			this.setManaCost(new ManaPool("(G)"));
			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.WALL), "sacrifice a Wall"));
			this.addEffect(drawACard());
		}
	}

	public WallofMulch(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (G), Sacrifice a Wall: Draw a card.
		this.addAbility(new WallofMulchAbility1(state));
	}
}
