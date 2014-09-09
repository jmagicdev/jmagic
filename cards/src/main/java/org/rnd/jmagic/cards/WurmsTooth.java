package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Wurm's Tooth")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class WurmsTooth extends Card
{
	public static final class GreenLife extends org.rnd.jmagic.abilityTemplates.ColorTriggeredLife
	{
		public GreenLife(GameState state)
		{
			super(state, Color.GREEN);
		}
	}

	public WurmsTooth(GameState state)
	{
		super(state);

		this.addAbility(new GreenLife(state));
	}
}
