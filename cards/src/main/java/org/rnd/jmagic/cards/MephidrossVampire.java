package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mephidross Vampire")
@Types({Type.CREATURE})
@SubTypes({SubType.VAMPIRE})
@ManaCost("4BB")
@ColorIdentity({Color.BLACK})
public final class MephidrossVampire extends Card
{
	public static final class Hungry extends EventTriggeredAbility
	{
		public Hungry(GameState state)
		{
			super(state, "Whenever this creature deals damage to a creature, put a +1/+1 counter on this creature.");
			this.addPattern(whenDealsCombatDamageToACreature(ABILITY_SOURCE_OF_THIS));
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "this creature"));
		}
	}

	public static final class MoarVampires extends StaticAbility
	{
		public MoarVampires(GameState state)
		{
			super(state, "Each creature you control is a Vampire in addition to its other creature types and has \"Whenever this creature deals damage to a creature, put a +1/+1 counter on this creature.\"");

			ContinuousEffect.Part makeVampires = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			makeVampires.parameters.put(ContinuousEffectType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			makeVampires.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.VAMPIRE));
			this.addEffectPart(makeVampires);

			ContinuousEffect.Part giveCounters = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_OBJECT);
			giveCounters.parameters.put(ContinuousEffectType.Parameter.OBJECT, CREATURES_YOU_CONTROL);
			giveCounters.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(Hungry.class)));
			this.addEffectPart(giveCounters);
		}
	}

	public MephidrossVampire(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Each creature you control is a Vampire in addition to its other
		// creature types and has
		// "Whenever this creature deals damage to a creature, put a +1/+1 counter on this creature."
		this.addAbility(new MoarVampires(state));
	}
}
