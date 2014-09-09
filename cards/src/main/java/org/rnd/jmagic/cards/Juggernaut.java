package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Juggernaut")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.JUGGERNAUT})
@ManaCost("4")
@ColorIdentity({})
public final class Juggernaut extends Card
{
	public static final class CantBeBlockedByWalls extends StaticAbility
	{
		public CantBeBlockedByWalls(GameState state)
		{
			super(state, "Juggernaut can't be blocked by Walls.");

			SetGenerator blockedByWalls = BlockedBy.instance(HasSubType.instance(SubType.WALL));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(blockedByWalls, This.instance())));
			this.addEffectPart(part);
		}
	}

	public Juggernaut(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(3);

		// Juggernaut attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));

		// Juggernaut can't be blocked by Walls.
		this.addAbility(new CantBeBlockedByWalls(state));
	}
}
