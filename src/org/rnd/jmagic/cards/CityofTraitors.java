package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("City of Traitors")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.EXODUS, r = Rarity.RARE)})
@ColorIdentity({})
public final class CityofTraitors extends Card
{
	public static final class CityofTraitorsAbility0 extends EventTriggeredAbility
	{
		public CityofTraitorsAbility0(GameState state)
		{
			super(state, "When you play another land, sacrifice City of Traitors.");

			SimpleEventPattern playLand = new SimpleEventPattern(EventType.PLAY_LAND);
			playLand.put(EventType.Parameter.PLAYER, You.instance());
			playLand.put(EventType.Parameter.LAND, RelativeComplement.instance(Cards.instance(), ABILITY_SOURCE_OF_THIS));
			this.addPattern(playLand);

			this.addEffect(sacrificeThis("City of Traitors"));
		}
	}

	public static final class CityofTraitorsAbility1 extends ActivatedAbility
	{
		public CityofTraitorsAbility1(GameState state)
		{
			super(state, "(T): Add (2) to your mana pool.");
			this.costsTap = true;
			this.addEffect(addManaToYourManaPoolFromAbility("(2)"));
		}
	}

	public CityofTraitors(GameState state)
	{
		super(state);

		// When you play another land, sacrifice City of Traitors.
		this.addAbility(new CityofTraitorsAbility0(state));

		// (T): Add (2) to your mana pool.
		this.addAbility(new CityofTraitorsAbility1(state));
	}
}
