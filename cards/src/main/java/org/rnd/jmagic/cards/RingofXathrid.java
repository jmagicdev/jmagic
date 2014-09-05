package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ring of Xathrid")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class RingofXathrid extends Card
{
	public static final class RingofXathridAbility0 extends ActivatedAbility
	{
		public RingofXathridAbility0(GameState state)
		{
			super(state, "(2): Regenerate equipped creature.");
			this.setManaCost(new ManaPool("(2)"));

			SetGenerator equipped = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(regenerate(equipped, "Regenerate equipped creature."));
		}
	}

	public static final class RingofXathridAbility1 extends EventTriggeredAbility
	{
		public RingofXathridAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a +1/+1 counter on equipped creature if it's black.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator equipped = EquippedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(ifThen(Intersect.instance(equipped, HasColor.instance(Color.BLACK)), putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, equipped, "Put a +1/+1 counter on equipped creature."), "Put a +1/+1 counter on equipped creature if it's black."));
		}
	}

	public RingofXathrid(GameState state)
	{
		super(state);

		// (2): Regenerate equipped creature. (The next time that creature would
		// be destroyed this turn, it isn't. Instead tap it, remove all damage
		// from it, and remove it from combat.)
		this.addAbility(new RingofXathridAbility0(state));

		// At the beginning of your upkeep, put a +1/+1 counter on equipped
		// creature if it's black.
		this.addAbility(new RingofXathridAbility1(state));

		// Equip (1) ((1): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
