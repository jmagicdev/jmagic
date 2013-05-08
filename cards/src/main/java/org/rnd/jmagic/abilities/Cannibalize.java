package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

public final class Cannibalize extends ActivatedAbility
{
	private final String creatureName;

	public Cannibalize(GameState state, String creatureName)
	{
		super(state, "Sacrifice a creature: " + creatureName + " gets +2/+2 until end of turn.");
		this.creatureName = creatureName;
		this.addCost(sacrificeACreature("Sacrifice a creature"));
		this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+2), (+2), (creatureName + " gets +2/+2 until end of turn.")));
	}

	@Override
	public Cannibalize create(Game game)
	{
		return new Cannibalize(game.physicalState, this.creatureName);
	}
}
