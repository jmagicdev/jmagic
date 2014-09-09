package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ring of Valkas")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@ColorIdentity({})
public final class RingofValkas extends Card
{
	public static final class RingofValkasAbility0 extends StaticAbility
	{
		public RingofValkasAbility0(GameState state)
		{
			super(state, "Equipped creature has haste.");

			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addEffectPart(addAbilityToObject(equipped, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Haste.class)));
		}
	}

	public static final class RingofValkasAbility1 extends EventTriggeredAbility
	{
		public RingofValkasAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a +1/+1 counter on equipped creature if it's red.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator equipped = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(ifThen(Intersect.instance(equipped, HasColor.instance(Color.RED)), putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, equipped, "Put a +1/+1 counter on equipped creature."), "Put a +1/+1 counter on equipped creature if it's red."));
		}
	}

	public RingofValkas(GameState state)
	{
		super(state);

		// Equipped creature has haste. (It can attack and (T) no matter when it
		// came under your control.)
		this.addAbility(new RingofValkasAbility0(state));

		// At the beginning of your upkeep, put a +1/+1 counter on equipped
		// creature if it's red.
		this.addAbility(new RingofValkasAbility1(state));

		// Equip (1) ((1): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
