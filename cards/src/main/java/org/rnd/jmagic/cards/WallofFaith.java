package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Wall of Faith")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class WallofFaith extends Card
{
	public static final class Faithbreathing extends ActivatedAbility
	{
		public Faithbreathing(GameState state)
		{
			super(state, "(W): Wall of Faith gets +0/+1 until end of turn.");

			this.setManaCost(new ManaPool("W"));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+0), (+1), "Wall of Faith gets +0/+1 until end of turn."));
		}
	}

	public WallofFaith(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));
		this.addAbility(new Faithbreathing(state));
	}
}
