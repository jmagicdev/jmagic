package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Dearly Departed")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("4WW")
@ColorIdentity({Color.WHITE})
public final class DearlyDeparted extends Card
{
	public static final class DearlyDepartedAbility1 extends StaticAbility
	{
		public DearlyDepartedAbility1(GameState state)
		{
			super(state, "As long as Dearly Departed is in your graveyard, each Human creature you control enters the battlefield with an additional +1/+1 counter on it.");
			this.canApply = THIS_IS_IN_A_GRAVEYARD;

			SetGenerator humanCreatures = Intersect.instance(HasSubType.instance(SubType.HUMAN), HasType.instance(Type.CREATURE));

			ZoneChangeReplacementEffect replacement = new ZoneChangeReplacementEffect(this.game, this.getName());
			replacement.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), humanCreatures, You.instance(), false));

			SetGenerator zoneChange = ReplacedBy.instance(Identity.instance(replacement));

			EventFactory factory = new EventFactory(EventType.PUT_COUNTERS, this.getName());
			factory.parameters.put(EventType.Parameter.CAUSE, CauseOf.instance(zoneChange));
			factory.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			factory.parameters.put(EventType.Parameter.OBJECT, NewObjectOf.instance(zoneChange));
			replacement.addEffect(factory);

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public DearlyDeparted(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// As long as Dearly Departed is in your graveyard, each Human creature
		// you control enters the battlefield with an additional +1/+1 counter
		// on it.
		this.addAbility(new DearlyDepartedAbility1(state));
	}
}
