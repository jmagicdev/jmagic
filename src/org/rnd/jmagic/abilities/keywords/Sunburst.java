package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class Sunburst extends Keyword
{
	public static final class SunburstAbility extends StaticAbility
	{
		public SunburstAbility(GameState state)
		{
			super(state, "If this object is entering the battlefield from the stack as a creature, it enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it. If this object is entering the battlefield from the stack and isn't entering the battlefield as a creature, it enters the battlefield with a charge counter on it for each color of mana spent to cast it.");

			ZoneChangeReplacementEffect creatureReplacement = new ZoneChangeReplacementEffect(state.game, "If this object is entering the battlefield from the stack as a creature, it enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.");
			creatureReplacement.addPattern(new SimpleZoneChangePattern(Stack.instance(), Battlefield.instance(), Intersect.instance(This.instance(), HasType.instance(Type.CREATURE)), false));

			SetGenerator replacedCreature = creatureReplacement.replacedByThis();

			EventFactory plusFactory = new EventFactory(EventType.PUT_COUNTERS, "It enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it");
			plusFactory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(replacedCreature));
			plusFactory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			plusFactory.parameters.put(EventType.Parameter.NUMBER, org.rnd.jmagic.engine.generators.Sunburst.instance());
			plusFactory.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(replacedCreature));
			creatureReplacement.addEffect(plusFactory);

			ZoneChangeReplacementEffect nonCreatureReplacement = new ZoneChangeReplacementEffect(state.game, "If this object is entering the battlefield from the stack and isn't entering the battlefield as a creature, it enters the battlefield with a charge counter on it for each color of mana spent to cast it.");
			nonCreatureReplacement.addPattern(new SimpleZoneChangePattern(Stack.instance(), Battlefield.instance(), RelativeComplement.instance(This.instance(), HasType.instance(Type.CREATURE)), false));

			SetGenerator replacedNonCreature = nonCreatureReplacement.replacedByThis();

			EventFactory chargeFactory = new EventFactory(EventType.PUT_COUNTERS, "It enters the battlefield with a charge counter on it for each color of mana spent to cast it");
			chargeFactory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(replacedNonCreature));
			chargeFactory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.CHARGE));
			chargeFactory.parameters.put(EventType.Parameter.NUMBER, org.rnd.jmagic.engine.generators.Sunburst.instance());
			chargeFactory.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(replacedNonCreature));
			nonCreatureReplacement.addEffect(chargeFactory);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REPLACEMENT_EFFECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Identity.instance(creatureReplacement, nonCreatureReplacement));
			this.addEffectPart(part);

			this.canApply = NonEmpty.instance();
		}
	}

	public Sunburst(GameState state)
	{
		super(state, "Sunburst");
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		java.util.List<StaticAbility> ret = new java.util.LinkedList<StaticAbility>();
		ret.add(new SunburstAbility(this.state));
		return ret;
	}
}
