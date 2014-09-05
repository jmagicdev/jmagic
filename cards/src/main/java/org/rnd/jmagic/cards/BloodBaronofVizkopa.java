package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Blood Baron of Vizkopa")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("3WB")
@Printings({@Printings.Printed(ex = DragonsMaze.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class BloodBaronofVizkopa extends Card
{
	public static final class BloodBaronofVizkopaAbility1 extends StaticAbility
	{
		public BloodBaronofVizkopaAbility1(GameState state)
		{
			super(state, "As long as you have 30 or more life and an opponent has 10 or less life, Blood Baron of Vizkopa gets +6/+6 and has flying.");

			SetGenerator youAreHealthy = Intersect.instance(Between.instance(30, null), LifeTotalOf.instance(You.instance()));
			SetGenerator opponentIsWeak = Intersect.instance(Between.instance(null, 10), LifeTotalOf.instance(OpponentsOf.instance(You.instance())));
			SetGenerator criteria = Both.instance(youAreHealthy, opponentIsWeak);
			this.canApply = Both.instance(this.canApply, criteria);

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +6, +6));
			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public BloodBaronofVizkopa(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Lifelink, protection from white and from black
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));

		// As long as you have 30 or more life and an opponent has 10 or less
		// life, Blood Baron of Vizkopa gets +6/+6 and has flying.
		this.addAbility(new BloodBaronofVizkopaAbility1(state));
	}
}
