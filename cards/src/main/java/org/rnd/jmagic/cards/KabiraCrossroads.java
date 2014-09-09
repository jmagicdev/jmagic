package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Kabira Crossroads")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE})
public final class KabiraCrossroads extends Card
{
	public static final class KabiraLife extends EventTriggeredAbility
	{
		public KabiraLife(GameState state)
		{
			super(state, "When Kabira Crossroads enters the battlefield, you gain 2 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(org.rnd.jmagic.engine.generators.You.instance(), 2, "You gain 2 life"));
		}
	}

	public KabiraCrossroads(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, "Kabira Crossroads"));

		this.addAbility(new KabiraLife(state));

		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));
	}
}
