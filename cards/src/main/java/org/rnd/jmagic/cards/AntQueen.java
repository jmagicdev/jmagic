package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Ant Queen")
@Types({Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class AntQueen extends Card
{
	public static final class MakeAnt extends ActivatedAbility
	{
		public MakeAnt(GameState state)
		{
			super(state, "(1)(G): Put a 1/1 green Insect creature token onto the battlefield.");

			// (1)(G):
			this.setManaCost(new ManaPool("1G"));

			// Put a 1/1 green Insect creature token onto the battlefield.
			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 green Insect creature token onto the battlefield.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.INSECT);
			this.addEffect(token.getEventFactory());
		}
	}

	public AntQueen(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new MakeAnt(state));
	}
}
