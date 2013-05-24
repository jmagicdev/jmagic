package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ring of Evos Isle")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class RingofEvosIsle extends Card
{
	public static final class RingofEvosIsleAbility0 extends ActivatedAbility
	{
		public RingofEvosIsleAbility0(GameState state)
		{
			super(state, "(2): Equipped creature gains hexproof until end of turn.");
			this.setManaCost(new ManaPool("(2)"));

			SetGenerator equipped = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(addAbilityUntilEndOfTurn(equipped, org.rnd.jmagic.abilities.keywords.Hexproof.class, "Equipped creature gains hexproof until end of turn."));
		}
	}

	public static final class RingofEvosIsleAbility1 extends EventTriggeredAbility
	{
		public RingofEvosIsleAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a +1/+1 counter on equipped creature if it's blue.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator equipped = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(ifThen(Intersect.instance(equipped, HasColor.instance(Color.BLUE)), putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, equipped, "Put a +1/+1 counter on equipped creature."), "Put a +1/+1 counter on equipped creature if it's blue."));
		}
	}

	public RingofEvosIsle(GameState state)
	{
		super(state);

		// (2): Equipped creature gains hexproof until end of turn. (It can't be
		// the target of spells or abilities your opponents control.)
		this.addAbility(new RingofEvosIsleAbility0(state));

		// At the beginning of your upkeep, put a +1/+1 counter on equipped
		// creature if it's blue.
		this.addAbility(new RingofEvosIsleAbility1(state));

		// Equip (1) ((1): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
