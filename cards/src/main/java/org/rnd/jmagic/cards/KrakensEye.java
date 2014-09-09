package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Kraken's Eye")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class KrakensEye extends Card
{
	public static final class BlueLife extends org.rnd.jmagic.abilityTemplates.ColorTriggeredLife
	{
		public BlueLife(GameState state)
		{
			super(state, Color.BLUE);
		}
	}

	public KrakensEye(GameState state)
	{
		super(state);

		this.addAbility(new BlueLife(state));
	}
}
