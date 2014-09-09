package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Moltensteel Dragon")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4(R/P)(R/P)")
@ColorIdentity({Color.RED})
public final class MoltensteelDragon extends Card
{
	public static final class MoltensteelDragonAbility1 extends ActivatedAbility
	{
		public MoltensteelDragonAbility1(GameState state)
		{
			super(state, "(r/p): Moltensteel Dragon gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("(r/p)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +0, "Moltensteel Dragon gets +1/+0 until end of turn."));
		}
	}

	public MoltensteelDragon(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (r/p): Moltensteel Dragon gets +1/+0 until end of turn.
		this.addAbility(new MoltensteelDragonAbility1(state));
	}
}
