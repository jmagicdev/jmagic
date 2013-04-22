package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ring of Thune")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class RingofThune extends Card
{
	public static final class RingofThuneAbility0 extends StaticAbility
	{
		public RingofThuneAbility0(GameState state)
		{
			super(state, "Equipped creature has vigilance.");

			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addEffectPart(addAbilityToObject(equipped, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Vigilance.class)));
		}
	}

	public static final class RingofThuneAbility1 extends EventTriggeredAbility
	{
		public RingofThuneAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a +1/+1 counter on equipped creature if it's white.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator equipped = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(ifThen(Intersect.instance(equipped, HasColor.instance(Color.WHITE)), putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, equipped, "Put a +1/+1 counter on equipped creature."), "Put a +1/+1 counter on equipped creature if it's white."));
		}
	}

	public RingofThune(GameState state)
	{
		super(state);

		// Equipped creature has vigilance. (Attacking doesn't cause it to tap.)
		this.addAbility(new RingofThuneAbility0(state));

		// At the beginning of your upkeep, put a +1/+1 counter on equipped
		// creature if it's white.
		this.addAbility(new RingofThuneAbility1(state));

		// Equip (1) ((1): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
