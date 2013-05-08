package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Archon of Redemption")
@Types({Type.CREATURE})
@SubTypes({SubType.ARCHON})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class ArchonofRedemption extends Card
{
	public static final class ArchonofRedemptionAbility1 extends EventTriggeredAbility
	{
		public ArchonofRedemptionAbility1(GameState state)
		{
			super(state, "Whenever Archon of Redemption or another creature with flying enters the battlefield under your control, you may gain life equal to that creature's power.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator flyers = Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			SetGenerator otherFlyers = RelativeComplement.instance(flyers, ABILITY_SOURCE_OF_THIS);
			ZoneChangePattern otherFlyersEnter = new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(null, Battlefield.instance(), otherFlyers, You.instance(), false);
			this.addPattern(otherFlyersEnter);

			SetGenerator amount = PowerOf.instance(NewObjectOf.instance(TriggerZoneChange.instance(This.instance())));
			EventFactory gainLife = gainLife(You.instance(), amount, "Gain life equal to that creature's power");
			this.addEffect(youMay(gainLife, "You may gain life equal to that creature's power."));
		}
	}

	public ArchonofRedemption(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Archon of Redemption or another creature with flying enters
		// the battlefield under your control, you may gain life equal to that
		// creature's power.
		this.addAbility(new ArchonofRedemptionAbility1(state));
	}
}
