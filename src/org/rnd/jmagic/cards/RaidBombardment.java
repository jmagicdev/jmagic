package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Raid Bombardment")
@Types({Type.ENCHANTMENT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class RaidBombardment extends Card
{
	public static final class RaidBombardmentAbility0 extends EventTriggeredAbility
	{
		public RaidBombardmentAbility0(GameState state)
		{
			super(state, "Whenever a creature you control with power 2 or less attacks, Raid Bombardment deals 1 damage to defending player.");

			SetGenerator smallGuys = Intersect.instance(CREATURES_YOU_CONTROL, HasPower.instance(Between.instance(null, 2)));
			SimpleEventPattern smallAttacker = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			smallAttacker.put(EventType.Parameter.OBJECT, smallGuys);
			this.addPattern(smallAttacker);

			SetGenerator attacking = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.OBJECT);
			SetGenerator defendingPlayer = DefendingPlayer.instance(attacking);
			this.addEffect(permanentDealDamage(1, defendingPlayer, "Raid Bombardment deals 1 damage to defending player."));
		}
	}

	public RaidBombardment(GameState state)
	{
		super(state);

		// Whenever a creature you control with power 2 or less attacks, Raid
		// Bombardment deals 1 damage to defending player.
		this.addAbility(new RaidBombardmentAbility0(state));
	}
}
