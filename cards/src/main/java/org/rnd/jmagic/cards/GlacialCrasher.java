package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glacial Crasher")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class GlacialCrasher extends Card
{
	public static final class GlacialCrasherAbility1 extends StaticAbility
	{
		public GlacialCrasherAbility1(GameState state)
		{
			super(state, "Glacial Crasher can't attack unless there is a Mountain on the battlefield.");

			SetGenerator noMountains = Not.instance(Intersect.instance(HasSubType.instance(SubType.MOUNTAIN), Permanents.instance()));
			this.canApply = Both.instance(this.canApply, noMountains);

			ContinuousEffect.Part restrictions = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			restrictions.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(This.instance(), Attacking.instance())));
			this.addEffectPart(restrictions);
		}
	}

	public GlacialCrasher(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Trample (If this creature would assign enough damage to its blockers
		// to destroy them, you may have it assign the rest of its damage to
		// defending player or planeswalker.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Glacial Crasher can't attack unless there is a Mountain on the
		// battlefield.
		this.addAbility(new GlacialCrasherAbility1(state));
	}
}
