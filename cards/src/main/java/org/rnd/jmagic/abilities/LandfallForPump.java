package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

public final class LandfallForPump extends EventTriggeredAbility
{
	private final String cardName;

	private final int powerChange;
	private final int toughnessChange;

	public LandfallForPump(GameState state, String cardName, int power, int toughness)
	{
		super(state, "Whenever a land enters the battlefield under your control, " + cardName + " gets " + powerToughness(power, toughness) + " until end of turn.");
		this.cardName = cardName;
		this.powerChange = power;
		this.toughnessChange = toughness;

		// Landfall - Whenever a land enters the battlefield under your
		// control,
		this.addPattern(landfall());

		// Plated Geopede gets +2/+2 until end of turn.
		this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, power, toughness, (cardName + " gets +2/+2 until end of turn.")));
	}

	@Override
	public LandfallForPump create(Game game)
	{
		return new LandfallForPump(game.physicalState, this.cardName, this.powerChange, this.toughnessChange);
	}

	private static String powerToughness(int p, int t)
	{
		StringBuilder str = new StringBuilder();
		if(p >= 0)
			str.append('+');
		str.append(p);
		str.append('/');
		if(t >= 0)
			str.append('+');
		str.append(t);
		return str.toString();
	}
}
