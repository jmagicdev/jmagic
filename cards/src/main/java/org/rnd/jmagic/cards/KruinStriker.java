package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kruin Striker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class KruinStriker extends Card
{
	public static final class KruinStrikerAbility0 extends EventTriggeredAbility
	{
		public KruinStrikerAbility0(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield under your control, Kruin Striker gets +1/+0 and gains trample until end of turn.");

			SetGenerator otherCreatures = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, Battlefield.instance(), otherCreatures, You.instance(), false));

			this.addEffect(createFloatingEffect("Kruin Striker gets +1/+0 and gains trample until end of turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, +1, +0), addAbilityToObject(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Trample.class)));
		}
	}

	public KruinStriker(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever another creature enters the battlefield under your control,
		// Kruin Striker gets +1/+0 and gains trample until end of turn.
		this.addAbility(new KruinStrikerAbility0(state));
	}
}
