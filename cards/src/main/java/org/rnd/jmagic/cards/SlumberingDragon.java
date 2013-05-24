package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Slumbering Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class SlumberingDragon extends Card
{
	public static final class SlumberingDragonAbility1 extends StaticAbility
	{
		public SlumberingDragonAbility1(GameState state)
		{
			super(state, "Slumbering Dragon can't attack or block unless it has five or more +1/+1 counters on it.");

			ContinuousEffect.Part restrictions = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			restrictions.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(This.instance(), Attacking.instance())));
			this.addEffectPart(restrictions);

			ContinuousEffect.Part block = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			block.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(This.instance(), Blocking.instance())));
			this.addEffectPart(block);

			SetGenerator pairedWithSoulbond = Intersect.instance(Count.instance(CountersOn.instance(This.instance(), Counter.CounterType.PLUS_ONE_PLUS_ONE)), Between.instance(5, null));
			this.canApply = Both.instance(this.canApply, Not.instance(pairedWithSoulbond));
		}
	}

	public static final class SlumberingDragonAbility2 extends EventTriggeredAbility
	{
		public SlumberingDragonAbility2(GameState state)
		{
			super(state, "Whenever a creature attacks you or a planeswalker you control, put a +1/+1 counter on Slumbering Dragon.");

			SetGenerator planeswalkersYouControl = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.PLANESWALKER));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, CreaturePermanents.instance());
			pattern.put(EventType.Parameter.DEFENDER, Union.instance(You.instance(), planeswalkersYouControl));
			this.addPattern(pattern);

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Slumbering Dragon."));
		}
	}

	public SlumberingDragon(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Slumbering Dragon can't attack or block unless it has five or more
		// +1/+1 counters on it.
		this.addAbility(new SlumberingDragonAbility1(state));

		// Whenever a creature attacks you or a planeswalker you control, put a
		// +1/+1 counter on Slumbering Dragon.
		this.addAbility(new SlumberingDragonAbility2(state));
	}
}
