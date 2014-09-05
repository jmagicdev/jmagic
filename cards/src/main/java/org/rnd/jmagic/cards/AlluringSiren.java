package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Alluring Siren")
@Types({Type.CREATURE})
@SubTypes({SubType.SIREN})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Magic2012.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class AlluringSiren extends Card
{
	public static final class Allure extends ActivatedAbility
	{
		public Allure(GameState state)
		{
			super(state, "(T): Target creature an opponent controls attacks you this turn if able.");

			// (T):
			this.costsTap = true;

			// Target creature an opponent controls attacks you this turn if
			// able.
			SetGenerator opponentControls = ControlledBy.instance(OpponentsOf.instance(You.instance()));
			SetGenerator targetables = Intersect.instance(CreaturePermanents.instance(), opponentControls);
			Target target = this.addTarget(targetables, "target creature an opponent controls");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

			this.addEffect(createFloatingEffect("Target creature an opponent controls attacks you this turn if able.", part));
		}
	}

	public AlluringSiren(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new Allure(state));
	}
}
