package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Soaring Seacliff")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SoaringSeacliff extends Card
{
	public static final class ETBFly extends EventTriggeredAbility
	{
		public ETBFly(GameState state)
		{
			super(state, "When Soaring Seacliff enters the battlefield, target creature gains flying until end of turn.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Flying.class, "Target creature gains flying until end of turn"));
		}
	}

	public SoaringSeacliff(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, "Soaring Seacliff"));

		this.addAbility(new ETBFly(state));

		this.addAbility(new org.rnd.jmagic.abilities.TapForU(state));
	}
}
