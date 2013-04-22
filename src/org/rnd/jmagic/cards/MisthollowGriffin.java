package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Misthollow Griffin")
@Types({Type.CREATURE})
@SubTypes({SubType.GRIFFIN})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class MisthollowGriffin extends Card
{
	public static final class MisthollowGriffinAbility1 extends StaticAbility
	{
		public MisthollowGriffinAbility1(GameState state)
		{
			super(state, "You may cast Misthollow Griffin from exile.");

			ContinuousEffect.Part castFromExile = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
			castFromExile.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			castFromExile.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			this.addEffectPart(castFromExile);

			this.canApply = Intersect.instance(InZone.instance(ExileZone.instance()), This.instance());
		}
	}

	public MisthollowGriffin(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// You may cast Misthollow Griffin from exile.
		this.addAbility(new MisthollowGriffinAbility1(state));
	}
}
