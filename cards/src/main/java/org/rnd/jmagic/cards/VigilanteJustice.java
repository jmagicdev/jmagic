package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Vigilante Justice")
@Types({Type.ENCHANTMENT})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class VigilanteJustice extends Card
{
	public static final class VigilanteJusticeAbility0 extends EventTriggeredAbility
	{
		public VigilanteJusticeAbility0(GameState state)
		{
			super(state, "Whenever a Human enters the battlefield under your control, Vigilante Justice deals 1 damage to target creature or player.");
			SimpleZoneChangePattern newHuman = new SimpleZoneChangePattern(null, Battlefield.instance(), HasSubType.instance(SubType.HUMAN), You.instance(), false);
			this.addPattern(newHuman);

			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(1, target, "Vigilante Justice deals 1 damage to target creature or player."));
		}
	}

	public VigilanteJustice(GameState state)
	{
		super(state);

		// Whenever a Human enters the battlefield under your control, Vigilante
		// Justice deals 1 damage to target creature or player.
		this.addAbility(new VigilanteJusticeAbility0(state));
	}
}
