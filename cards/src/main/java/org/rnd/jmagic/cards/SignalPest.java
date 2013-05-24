package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.keywords.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Signal Pest")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.PEST})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class SignalPest extends Card
{
	public static final class SignalPestAbility1 extends StaticAbility
	{
		public SignalPestAbility1(GameState state)
		{
			super(state, "Signal Pest can't be blocked except by creatures with flying or reach.");

			SetGenerator hasFlyingOrReach = Union.instance(HasKeywordAbility.instance(Flying.class), HasKeywordAbility.instance(Reach.class));
			SetGenerator notBlockingWithFlyingOrReach = RelativeComplement.instance(Blocking.instance(This.instance()), hasFlyingOrReach);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(notBlockingWithFlyingOrReach));
			this.addEffectPart(part);
		}
	}

	public SignalPest(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Battle cry (Whenever this creature attacks, each other attacking
		// creature gets +1/+0 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.BattleCry(state));

		// Signal Pest can't be blocked except by creatures with flying or
		// reach.
		this.addAbility(new SignalPestAbility1(state));
	}
}
