package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Seraph Sanctuary")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({})
public final class SeraphSanctuary extends Card
{
	public static final class SeraphSanctuaryAbility0 extends EventTriggeredAbility
	{
		public SeraphSanctuaryAbility0(GameState state)
		{
			super(state, "When Seraph Sanctuary enters the battlefield, you gain 1 life.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public static final class SeraphSanctuaryAbility1 extends EventTriggeredAbility
	{
		public SeraphSanctuaryAbility1(GameState state)
		{
			super(state, "Whenever an Angel enters the battlefield under your control, you gain 1 life.");
			SetGenerator angels = HasSubType.instance(SubType.ANGEL);
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), angels, You.instance(), false));
			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public SeraphSanctuary(GameState state)
	{
		super(state);

		// When Seraph Sanctuary enters the battlefield, you gain 1 life.
		this.addAbility(new SeraphSanctuaryAbility0(state));

		// Whenever an Angel enters the battlefield under your control, you gain
		// 1 life.
		this.addAbility(new SeraphSanctuaryAbility1(state));

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));
	}
}
