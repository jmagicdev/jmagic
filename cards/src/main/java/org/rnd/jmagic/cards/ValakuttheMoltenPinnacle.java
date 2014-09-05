package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Valakut, the Molten Pinnacle")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ValakuttheMoltenPinnacle extends Card
{
	public static final class MountainsHurt extends EventTriggeredAbility
	{
		public MountainsHurt(GameState state)
		{
			super(state, "Whenever a Mountain enters the battlefield under your control, if you control at least five other Mountains, you may have Valakut, the Molten Pinnacle deal 3 damage to target creature or player.");

			SetGenerator mountains = HasSubType.instance(SubType.MOUNTAIN);

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), mountains, You.instance(), false));

			SetGenerator thatMountain = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator otherMountains = RelativeComplement.instance(Intersect.instance(youControl, mountains), thatMountain);
			SetGenerator atLeastFive = Intersect.instance(Between.instance(5, null), Count.instance(otherMountains));
			this.interveningIf = atLeastFive;

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(youMay(permanentDealDamage(3, targetedBy(target), "Valukut, the Molten Pinnacle deals 3 damage to target creature or player"), "You may have Valakut, the Molten Pinnacle deal 3 damage to target creature or player."));
		}
	}

	public ValakuttheMoltenPinnacle(GameState state)
	{
		super(state);

		// Valakut, the Molten Pinnacle enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// Whenever a Mountain enters the battlefield under your control, if you
		// control at least five other Mountains, you may have Valakut, the
		// Molten Pinnacle deal 3 damage to target creature or player.
		this.addAbility(new MountainsHurt(state));

		// {T}: Add {R} to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForR(state));
	}
}
