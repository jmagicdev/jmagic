package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Court Street Denizen")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class CourtStreetDenizen extends Card
{
	public static final class CourtStreetDenizenAbility0 extends EventTriggeredAbility
	{
		public CourtStreetDenizenAbility0(GameState state)
		{
			super(state, "Whenever another white creature enters the battlefield under your control, tap target creature an opponent controls.");

			SetGenerator anotherWhiteCreature = RelativeComplement.instance(Intersect.instance(HasColor.instance(Color.WHITE), CreaturePermanents.instance()), ABILITY_SOURCE_OF_THIS);
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), anotherWhiteCreature, You.instance(), false));

			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target creature an opponent controls"));
			this.addEffect(tap(target, "Tap target creature an opponent controls."));
		}
	}

	public CourtStreetDenizen(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Whenever another white creature enters the battlefield under your
		// control, tap target creature an opponent controls.
		this.addAbility(new CourtStreetDenizenAbility0(state));
	}
}
