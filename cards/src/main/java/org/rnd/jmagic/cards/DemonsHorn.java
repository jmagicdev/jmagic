package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Demon's Horn")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class DemonsHorn extends Card
{
	public static final class BlackLife extends org.rnd.jmagic.abilityTemplates.ColorTriggeredLife
	{
		public BlackLife(GameState state)
		{
			super(state, Color.BLACK);
		}
	}

	public DemonsHorn(GameState state)
	{
		super(state);

		this.addAbility(new BlackLife(state));
	}
}
