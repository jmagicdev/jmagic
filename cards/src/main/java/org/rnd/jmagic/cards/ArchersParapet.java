package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Archers' Parapet")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class ArchersParapet extends Card
{
	public static final class ArchersParapetAbility1 extends ActivatedAbility
	{
		public ArchersParapetAbility1(GameState state)
		{
			super(state, "(1)(B), (T): Each opponent loses 1 life.");
			this.setManaCost(new ManaPool("(1)(B)"));
			this.costsTap = true;
			this.addEffect(loseLife(OpponentsOf.instance(You.instance()), 3, "Each opponent loses 3 life."));
		}
	}

	public ArchersParapet(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(5);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (1)(B), (T): Each opponent loses 1 life.
		this.addAbility(new ArchersParapetAbility1(state));
	}
}
