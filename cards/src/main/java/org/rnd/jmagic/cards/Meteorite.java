package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Meteorite")
@Types({Type.ARTIFACT})
@ManaCost("5")
@ColorIdentity({})
public final class Meteorite extends Card
{
	public static final class MeteoriteAbility0 extends EventTriggeredAbility
	{
		public MeteoriteAbility0(GameState state)
		{
			super(state, "When Meteorite enters the battlefield, it deals 2 damage to target creature or player.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(2, target, "Meteorite deals 2 damage to target creature or player."));
		}
	}

	public Meteorite(GameState state)
	{
		super(state);

		// When Meteorite enters the battlefield, it deals 2 damage to target
		// creature or player.
		this.addAbility(new MeteoriteAbility0(state));

		// (T): Add one mana of any color to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForAnyColor(state));
	}
}
