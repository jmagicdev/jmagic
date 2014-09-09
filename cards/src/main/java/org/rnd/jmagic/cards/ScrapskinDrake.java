package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scrapskin Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE, SubType.ZOMBIE})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class ScrapskinDrake extends Card
{
	public static final class ScrapskinDrakeAbility1 extends StaticAbility
	{
		public ScrapskinDrakeAbility1(GameState state)
		{
			super(state, "Scrapskin Drake can block only creatures with flying.");

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator blockingNonFlyer = RelativeComplement.instance(BlockedBy.instance(This.instance()), hasFlying);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingNonFlyer));
			this.addEffectPart(part);
		}
	}

	public ScrapskinDrake(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Scrapskin Drake can block only creatures with flying.
		this.addAbility(new ScrapskinDrakeAbility1(state));
	}
}
