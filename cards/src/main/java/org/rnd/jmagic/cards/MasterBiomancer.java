package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Master Biomancer")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.WIZARD})
@ManaCost("2GU")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class MasterBiomancer extends Card
{
	public static final class MasterBiomancerAbility0 extends StaticAbility
	{
		public MasterBiomancerAbility0(GameState state)
		{
			super(state, "Each other creature you control enters the battlefield with a number of additional +1/+1 counters on it equal to Master Biomancer's power and as a Mutant in addition to its other types.");

			SetGenerator otherCreatures = RelativeComplement.instance(HasType.instance(Type.CREATURE), This.instance());

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(state.game, this.getName());
			replacement.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), otherCreatures, You.instance(), false));

			SetGenerator zoneChange = ReplacedBy.instance(Identity.instance(replacement));
			SetGenerator thatCreature = NewObjectOf.instance(zoneChange);

			EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, "Each other creature you control enters the battlefield with a number of additional +1/+1 counters on it equal to Master Biomancer's power");
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
			factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			factory.parameters.put(EventType.Parameter.NUMBER, PowerOf.instance(This.instance()));
			factory.parameters.put(EventType.Parameter.OBJECT, thatCreature);
			replacement.addEffect(factory);

			// A ruling from Gatherer:
			// 2013/1/24 - The creature type Mutant becomes part of the
			// creature's copiable values.
			// COPY_OBJECT is currently the only way to add a sub-type as part
			// of a copy effect, so use it but retain every characteristic so it
			// doesn't use any effects from the original object
			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_COPIABLE_CHARACTERISTICS);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, thatCreature);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.MUTANT));
			replacement.addEffect(createFloatingEffect(Empty.instance(), "Each other creature you control enters the battlefield as a Mutant in addition to its other types", part));

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public MasterBiomancer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Each other creature you control enters the battlefield with a number
		// of additional +1/+1 counters on it equal to Master Biomancer's power
		// and as a Mutant in addition to its other types.
		this.addAbility(new MasterBiomancerAbility0(state));
	}
}
