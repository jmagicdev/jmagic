package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Territorial Baloth")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class TerritorialBaloth extends Card
{
	public static final class TerritoryPump extends EventTriggeredAbility
	{
		public TerritoryPump(GameState state)
		{
			super(state, "Landfall \u2014 Whenever a land enters the battlefield under your control, Territorial Baloth gets +2/+2 until end of turn.");

			this.addPattern(landfall());

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, 2, 2, "Territorial Baloth gets +2/+2 until end of turn"));
		}
	}

	public TerritorialBaloth(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new TerritoryPump(state));
	}
}
