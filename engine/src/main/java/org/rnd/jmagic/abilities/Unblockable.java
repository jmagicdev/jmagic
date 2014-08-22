package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Unblockable extends StaticAbility
{
	private String thisName;

	public Unblockable(GameState state, String thisName)
	{
		super(state, thisName + " can't be blocked.");

		this.thisName = thisName;

		this.addEffectPart(unblockable(This.instance()));
	}

	@Override
	public Unblockable create(Game game)
	{
		return new Unblockable(game.physicalState, this.thisName);
	}
}
