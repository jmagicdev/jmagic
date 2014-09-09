package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Dragon's Claw")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class DragonsClaw extends Card
{
	public static final class RedLife extends org.rnd.jmagic.abilityTemplates.ColorTriggeredLife
	{
		public RedLife(GameState state)
		{
			super(state, Color.RED);
		}
	}

	public DragonsClaw(GameState state)
	{
		super(state);

		this.addAbility(new RedLife(state));
	}
}
