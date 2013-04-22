package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * Represents an ability that makes the permanent it's on indestructible. You
 * should only use this ability when {@link Indestructible} (the version that
 * takes a permanent name) is not appropriate.
 */
public final class ThisCreatureIsIndestructible extends StaticAbility
{
	public ThisCreatureIsIndestructible(GameState state)
	{
		super(state, "This creature is indestructible.");

		this.addEffectPart(indestructible(This.instance()));
	}
}
