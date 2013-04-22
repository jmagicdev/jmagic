package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ring of Kalonia")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class RingofKalonia extends Card
{
	public static final class RingofKaloniaAbility0 extends StaticAbility
	{
		public RingofKaloniaAbility0(GameState state)
		{
			super(state, "Equipped creature has trample.");

			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addEffectPart(addAbilityToObject(equipped, new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Trample.class)));
		}
	}

	public static final class RingofKaloniaAbility1 extends EventTriggeredAbility
	{
		public RingofKaloniaAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a +1/+1 counter on equipped creature if it's green.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator equipped = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(ifThen(Intersect.instance(equipped, HasColor.instance(Color.GREEN)), putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, equipped, "Put a +1/+1 counter on equipped creature."), "Put a +1/+1 counter on equipped creature if it's green."));
		}
	}

	public RingofKalonia(GameState state)
	{
		super(state);

		// Equipped creature has trample. (If it would assign enough damage to
		// its blockers to destroy them, you may have it assign the rest of its
		// damage to defending player or planeswalker.)
		this.addAbility(new RingofKaloniaAbility0(state));

		// At the beginning of your upkeep, put a +1/+1 counter on equipped
		// creature if it's green.
		this.addAbility(new RingofKaloniaAbility1(state));

		// Equip (1) ((1): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
