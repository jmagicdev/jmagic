package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Guul Draz Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.VAMPIRE})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class GuulDrazVampire extends Card
{
	public static final class GuulDrazPump extends StaticAbility
	{
		public GuulDrazPump(GameState state)
		{
			super(state, "As long as an opponent has 10 or less life, Guul Draz Vampire gets +2/+1 and has intimidate.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), 2, 1));

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Intimidate.class));

			SetGenerator lifeTotals = LifeTotalOf.instance(OpponentsOf.instance(You.instance()));
			SetGenerator opponentHasTenOrLess = Intersect.instance(lifeTotals, Between.instance(null, 10));
			this.canApply = Both.instance(this.canApply, opponentHasTenOrLess);
		}
	}

	public GuulDrazVampire(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new GuulDrazPump(state));
	}
}
