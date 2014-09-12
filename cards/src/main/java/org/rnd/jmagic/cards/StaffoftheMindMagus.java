package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Staff of the Mind Magus")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class StaffoftheMindMagus extends Card
{
	public static final class StaffoftheMindMagusAbility0 extends EventTriggeredAbility
	{
		public StaffoftheMindMagusAbility0(GameState state)
		{
			super(state, "Whenever you cast a blue spell or an Island enters the battlefield under your control, you gain 1 life.");

			SimpleEventPattern cast = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			cast.put(EventType.Parameter.PLAYER, You.instance());
			cast.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasColor.instance(Color.BLUE)));
			this.addPattern(cast);

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasSubType.instance(SubType.ISLAND), You.instance(), false));

			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public StaffoftheMindMagus(GameState state)
	{
		super(state);

		// Whenever you cast a blue spell or an Island enters the battlefield
		// under your control, you gain 1 life.
		this.addAbility(new StaffoftheMindMagusAbility0(state));
	}
}
