package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

public final class ShadePump extends ActivatedAbility
{
	private String creatureName;

	public ShadePump(GameState state, String creatureName)
	{
		super(state, "(B): " + creatureName + " gets +1/+1 until end of turn.");

		this.creatureName = creatureName;

		this.setManaCost(new ManaPool("B"));

		this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+1), (+1), creatureName + " gets +1/+1 until end of turn."));
	}

	@Override
	public ShadePump create(Game game)
	{
		return new ShadePump(game.physicalState, this.creatureName);
	}
}