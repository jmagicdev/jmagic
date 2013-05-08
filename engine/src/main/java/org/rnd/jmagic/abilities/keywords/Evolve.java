package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Evolve")
public final class Evolve extends Keyword
{
	public Evolve(GameState state)
	{
		super(state, "Evolve");
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new EvolveAbility(this.state));
		return ret;
	}

	public static final class EvolveAbility extends EventTriggeredAbility
	{
		public EvolveAbility(GameState state)
		{
			super(state, "Whenever a creature enters the battlefield under your control, if that creature's power is greater than this creature's power and/or that creature's toughness is greater than this creature's toughness, put a +1/+1 counter on this creature.");

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance())), false));

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			SetGenerator thisCreaturesPowerPlusOne = Sum.instance(Union.instance(PowerOf.instance(ABILITY_SOURCE_OF_THIS), numberGenerator(1)));
			final SetGenerator greaterPower = Intersect.instance(PowerOf.instance(thatCreature), Between.instance(thisCreaturesPowerPlusOne, null));
			SetGenerator thisCreaturesToughnessPlusOne = Sum.instance(Union.instance(ToughnessOf.instance(ABILITY_SOURCE_OF_THIS), numberGenerator(1)));
			final SetGenerator greaterToughness = Intersect.instance(ToughnessOf.instance(thatCreature), Between.instance(thisCreaturesToughnessPlusOne, null));
			this.interveningIf = Union.instance(greaterPower, greaterToughness);

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on this creature."));
		}
	}
}
