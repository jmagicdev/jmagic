package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class AttacksEachTurnIfAble extends StaticAbility
{
	private String creatureName;

	public AttacksEachTurnIfAble(GameState state, String creatureName)
	{
		super(state, creatureName + " attacks each turn if able.");
		this.creatureName = creatureName;

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
		part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, This.instance());
		this.addEffectPart(part);
	}

	@Override
	public AttacksEachTurnIfAble create(Game game)
	{
		return new AttacksEachTurnIfAble(game.physicalState, this.creatureName);
	}
}
