package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Foundry Street Denizen")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class FoundryStreetDenizen extends Card
{
	public static final class FoundryStreetDenizenAbility0 extends EventTriggeredAbility
	{
		public FoundryStreetDenizenAbility0(GameState state)
		{
			super(state, "Whenever another red creature enters the battlefield under your control, Foundry Street Denizen gets +1/+0 until end of turn.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(Intersect.instance(HasColor.instance(Color.RED), CreaturePermanents.instance()), ABILITY_SOURCE_OF_THIS), You.instance(), false));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +0, "Foundry Street Denizen gets +1/+0 until end of turn."));
		}
	}

	public FoundryStreetDenizen(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Whenever another red creature enters the battlefield under your
		// control, Foundry Street Denizen gets +1/+0 until end of turn.
		this.addAbility(new FoundryStreetDenizenAbility0(state));
	}
}
