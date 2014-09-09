package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Angel's Feather")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class AngelsFeather extends Card
{
	public static final class WhiteLife extends org.rnd.jmagic.abilityTemplates.ColorTriggeredLife
	{
		public WhiteLife(GameState state)
		{
			super(state, Color.WHITE);
		}
	}

	public AngelsFeather(GameState state)
	{
		super(state);

		this.addAbility(new WhiteLife(state));
	}
}
