package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Verdant Force")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("5GGG")
@ColorIdentity({Color.GREEN})
public final class VerdantForce extends Card
{
	public static final class SaprolingEveryTurn extends EventTriggeredAbility
	{
		public SaprolingEveryTurn(GameState state)
		{
			super(state, "At the beginning of each upkeep, put a 1/1 green Saproling creature token onto the battlefield under your control.");
			this.addPattern(atTheBeginningOfEachUpkeep());

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 green Saproling creature token onto the battlefield.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.SAPROLING);
			this.addEffect(token.getEventFactory());
		}
	}

	public VerdantForce(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		this.addAbility(new SaprolingEveryTurn(state));
	}
}
