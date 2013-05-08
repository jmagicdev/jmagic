package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Melira, Sylvok Outcast")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.HUMAN})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class MeliraSylvokOutcast extends Card
{
	public static final class MeliraSylvokOutcastAbility0 extends StaticAbility
	{
		public MeliraSylvokOutcastAbility0(GameState state)
		{
			super(state, "You can't get poison counters.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.PUT_ONE_COUNTER);
			pattern.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.POISON));
			pattern.put(EventType.Parameter.OBJECT, You.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));
			this.addEffectPart(part);
		}
	}

	public static final class MeliraSylvokOutcastAbility1 extends StaticAbility
	{
		public MeliraSylvokOutcastAbility1(GameState state)
		{
			super(state, "Creatures you control can't have -1/-1 counters placed on them.");

			SetPattern yourCreatures = new YouControlPattern(new TypePattern(Type.CREATURE));
			EventPattern pattern = new CounterPlacedPattern(Counter.CounterType.MINUS_ONE_MINUS_ONE, yourCreatures);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(pattern));
			this.addEffectPart(part);
		}
	}

	public static final class MeliraSylvokOutcastAbility2 extends StaticAbility
	{
		public MeliraSylvokOutcastAbility2(GameState state)
		{
			super(state, "Creatures your opponents control lose infect.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))));
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Infect.class));
			this.addEffectPart(part);
		}
	}

	public MeliraSylvokOutcast(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// You can't get poison counters.
		this.addAbility(new MeliraSylvokOutcastAbility0(state));

		// Creatures you control can't have -1/-1 counters placed on them.
		this.addAbility(new MeliraSylvokOutcastAbility1(state));

		// Creatures your opponents control lose infect.
		this.addAbility(new MeliraSylvokOutcastAbility2(state));
	}
}
