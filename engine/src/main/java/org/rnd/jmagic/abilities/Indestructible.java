package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

/**
 * Represents an ability that makes the permanent it's on indestructible.
 */
public final class Indestructible extends StaticAbility
{
	private String permanentName;

	public Indestructible(GameState state, String permanentName)
	{
		super(state, permanentName + " is indestructible.");
		this.permanentName = permanentName;

		this.addEffectPart(indestructible(This.instance()));
	}

	@Override
	public Indestructible create(Game game)
	{
		return new Indestructible(game.physicalState, this.permanentName);
	}
}
