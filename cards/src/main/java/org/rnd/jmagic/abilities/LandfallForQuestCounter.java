package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

public final class LandfallForQuestCounter extends EventTriggeredAbility
{
	private String thisName;

	public LandfallForQuestCounter(GameState state, String thisName)
	{
		super(state, "Whenever a land enters the battlefield under your control, you may put a quest counter on " + thisName + ".");

		this.thisName = thisName;

		this.addPattern(landfall());

		this.addEffect(youMayPutAQuestCounterOnThis(thisName));
	}

	@Override
	public LandfallForQuestCounter create(Game game)
	{
		return new LandfallForQuestCounter(game.physicalState, this.thisName);
	}
}
