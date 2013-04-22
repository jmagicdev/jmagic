package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gravitational Shift")
@Types({Type.ENCHANTMENT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class GravitationalShift extends Card
{
	public static final class YouPeople extends StaticAbility
	{
		public YouPeople(GameState state)
		{
			super(state, "Creatures with flying get +2/+0.");

			this.addEffectPart(modifyPowerAndToughness(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), +2, +0));
		}
	}

	public static final class WhatDoYouMeanYouPeople extends StaticAbility
	{
		public WhatDoYouMeanYouPeople(GameState state)
		{
			super(state, "Creatures without flying get -2/-0.");

			this.addEffectPart(modifyPowerAndToughness(RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), -2, -0));
		}
	}

	public GravitationalShift(GameState state)
	{
		super(state);

		// Creatures with flying get +2/+0.
		this.addAbility(new YouPeople(state));

		// Creatures without flying get -2/-0.
		this.addAbility(new WhatDoYouMeanYouPeople(state));
	}
}
