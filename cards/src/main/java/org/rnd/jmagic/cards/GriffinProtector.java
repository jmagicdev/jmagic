package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Griffin Protector")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GriffinProtector extends Card
{
	public static final class GriffinProtectorAbility1 extends EventTriggeredAbility
	{
		public GriffinProtectorAbility1(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield under your control, Griffin Protector gets +1/+1 until end of turn.");

			SetGenerator otherCreatures = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, Battlefield.instance(), otherCreatures, You.instance(), false));

			this.addEffect(createFloatingEffect("Griffin Protector gets +1/+1 until end of turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, +1, +1)));
		}
	}

	public GriffinProtector(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever another creature enters the battlefield under your control,
		// Griffin Protector gets +1/+1 until end of turn.
		this.addAbility(new GriffinProtectorAbility1(state));
	}
}
