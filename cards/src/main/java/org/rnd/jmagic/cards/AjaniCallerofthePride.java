package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ajani, Caller of the Pride")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.AJANI})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class AjaniCallerofthePride extends Card
{
	public static final class AjaniCallerofthePrideAbility0 extends LoyaltyAbility
	{
		public AjaniCallerofthePrideAbility0(GameState state)
		{
			super(state, +1, "Put a +1/+1 counter on up to one target creature.");
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			target.setNumber(0, 1);

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, targetedBy(target), "Put a +1/+1 counter on up to one target creature."));
		}
	}

	public static final class AjaniCallerofthePrideAbility1 extends LoyaltyAbility
	{
		public AjaniCallerofthePrideAbility1(GameState state)
		{
			super(state, -3, "Target creature gains flying and double strike until end of turn.");
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			ContinuousEffect.Part part = addAbilityToObject(target, org.rnd.jmagic.abilities.keywords.Flying.class, org.rnd.jmagic.abilities.keywords.DoubleStrike.class);
			this.addEffect(createFloatingEffect("Target creature gains flying and double strike until end of turn.", part));
		}
	}

	public static final class AjaniCallerofthePrideAbility2 extends LoyaltyAbility
	{
		public AjaniCallerofthePrideAbility2(GameState state)
		{
			super(state, -8, "Put X 2/2 white Cat creature tokens onto the battlefield, where X is your life total.");

			SetGenerator x = LifeTotalOf.instance(You.instance());
			SetGenerator two = numberGenerator(2);
			CreateTokensFactory tokens = new CreateTokensFactory(x, two, two, "Put X 2/2 white Cat creature tokens onto the battlefield, where X is your life total.");
			tokens.setColors(Color.WHITE);
			tokens.setSubTypes(SubType.CAT);
			this.addEffect(tokens.getEventFactory());
		}
	}

	public AjaniCallerofthePride(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(4);

		// +1: Put a +1/+1 counter on up to one target creature.
		this.addAbility(new AjaniCallerofthePrideAbility0(state));

		// -3: Target creature gains flying and double strike until end of turn.
		this.addAbility(new AjaniCallerofthePrideAbility1(state));

		// -8: Put X 2/2 white Cat creature tokens onto the battlefield, where X
		// is your life total.
		this.addAbility(new AjaniCallerofthePrideAbility2(state));
	}
}
