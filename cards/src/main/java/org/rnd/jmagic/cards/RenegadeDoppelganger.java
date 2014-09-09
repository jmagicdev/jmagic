package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Renegade Doppelganger")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class RenegadeDoppelganger extends Card
{
	public static final class RenegateCopy extends EventTriggeredAbility
	{
		public RenegateCopy(GameState state)
		{
			super(state, "Whenever another creature enters the battlefield under your control, you may have Renegade Doppelganger become a copy of that creature until end of turn.");
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), RelativeComplement.instance(HasType.instance(Type.CREATURE), ABILITY_SOURCE_OF_THIS), false));

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.COPY_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.ORIGINAL, thatCreature);
			EventFactory copy = createFloatingEffect("Renegade Doppelganger becomes a copy of that creature until end of turn", part);

			this.addEffect(youMay(copy, "You may have Renegade Doppelganger become a copy of that creature until end of turn."));
		}
	}

	public RenegadeDoppelganger(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Whenever another creature enters the battlefield under your control,
		// you may have Renegade Doppelganger become a copy of that creature
		// until end of turn.
		this.addAbility(new RenegateCopy(state));
	}
}
