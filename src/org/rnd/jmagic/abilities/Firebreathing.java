package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@ManaCost("R")
public final class Firebreathing extends ActivatedAbility
{
	private String creatureName;

	public Firebreathing(GameState state)
	{
		this(state, "This creature");
	}

	public Firebreathing(GameState state, String creatureName)
	{
		super(state, "(R): " + creatureName + " gets +1/+0 until end of turn.");
		this.creatureName = creatureName;

		this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+1), (+0), (creatureName + " gets +1/+0 until end of turn.")));
	}

	@Override
	public Firebreathing create(Game game)
	{
		return new Firebreathing(game.physicalState, this.creatureName);
	}
}
