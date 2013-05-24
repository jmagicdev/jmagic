package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Snapping Creeper")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SnappingCreeper extends Card
{
	public static final class SnappingCreeperAbility0 extends EventTriggeredAbility
	{
		public SnappingCreeperAbility0(GameState state)
		{
			super(state, "Whenever a land enters the battlefield under your control, Snapping Creeper gains vigilance until end of turn.");
			this.addPattern(landfall());
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Vigilance.class, "Snapping Creeper gains vigilance until end of turn."));
		}
	}

	public SnappingCreeper(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Landfall \u2014 Whenever a land enters the battlefield under your
		// control, Snapping Creeper gains vigilance until end of turn.
		this.addAbility(new SnappingCreeperAbility0(state));
	}
}
