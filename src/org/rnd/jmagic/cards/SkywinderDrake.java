package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Skywinder Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SkywinderDrake extends Card
{
	public static final class SkywinderDrakeAbility1 extends StaticAbility
	{
		public SkywinderDrakeAbility1(GameState state)
		{
			super(state, "Skywinder Drake can block only creatures with flying.");

			SetGenerator blockedByThis = BlockedBy.instance(This.instance());
			SetGenerator withFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator restriction = RelativeComplement.instance(blockedByThis, withFlying);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(restriction));
			this.addEffectPart(part);
		}
	}

	public SkywinderDrake(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Skywinder Drake can block only creatures with flying.
		this.addAbility(new SkywinderDrakeAbility1(state));
	}
}
