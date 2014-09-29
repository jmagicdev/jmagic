package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Scion of Glaciers")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class ScionofGlaciers extends Card
{
	public static final class ScionofGlaciersAbility0 extends ActivatedAbility
	{
		public ScionofGlaciersAbility0(GameState state)
		{
			super(state, "(U): Scion of Glaciers gets +1/-1 until end of turn.");
			this.setManaCost(new ManaPool("(U)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, -1, "Scion of Glaciers gets +1/-1 until end of turn."));
		}
	}

	public ScionofGlaciers(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// (U): Scion of Glaciers gets +1/-1 until end of turn.
		this.addAbility(new ScionofGlaciersAbility0(state));
	}
}
