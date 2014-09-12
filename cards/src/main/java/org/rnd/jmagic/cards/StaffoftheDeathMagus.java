package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Staff of the Death Magus")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class StaffoftheDeathMagus extends Card
{
	public static final class StaffoftheDeathMagusAbility0 extends EventTriggeredAbility
	{
		public StaffoftheDeathMagusAbility0(GameState state)
		{
			super(state, "Whenever you cast a black spell or a Swamp enters the battlefield under your control, you gain 1 life.");

			SimpleEventPattern cast = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			cast.put(EventType.Parameter.PLAYER, You.instance());
			cast.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasColor.instance(Color.BLACK)));
			this.addPattern(cast);

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasSubType.instance(SubType.SWAMP), You.instance(), false));

			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public StaffoftheDeathMagus(GameState state)
	{
		super(state);

		// Whenever you cast a black spell or a Swamp enters the battlefield
		// under your control, you gain 1 life.
		this.addAbility(new StaffoftheDeathMagusAbility0(state));
	}
}
