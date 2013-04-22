package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cloud Sprite")
@Types({Type.CREATURE})
@SubTypes({SubType.FAERIE})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class CloudSprite extends Card
{
	public static final class StuckUp extends StaticAbility
	{
		public StuckUp(GameState state)
		{
			super(state, "Cloud Sprite can block only creatures with flying.");

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator blockingNonFlyer = RelativeComplement.instance(BlockedBy.instance(This.instance()), hasFlying);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingNonFlyer));
			this.addEffectPart(part);
		}
	}

	public CloudSprite(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new StuckUp(state));
	}
}
