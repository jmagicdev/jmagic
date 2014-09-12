package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Staff of the Sun Magus")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class StaffoftheSunMagus extends Card
{
	public static final class StaffoftheSunMagusAbility0 extends EventTriggeredAbility
	{
		public StaffoftheSunMagusAbility0(GameState state)
		{
			super(state, "Whenever you cast a white spell or a Plains enters the battlefield under your control, you gain 1 life.");

			SimpleEventPattern cast = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			cast.put(EventType.Parameter.PLAYER, You.instance());
			cast.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasColor.instance(Color.WHITE)));
			this.addPattern(cast);

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasSubType.instance(SubType.PLAINS), You.instance(), false));

			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public StaffoftheSunMagus(GameState state)
	{
		super(state);

		// Whenever you cast a white spell or a Plains enters the battlefield
		// under your control, you gain 1 life.
		this.addAbility(new StaffoftheSunMagusAbility0(state));
	}
}
