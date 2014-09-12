package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Staff of the Flame Magus")
@Types({Type.ARTIFACT})
@ManaCost("3")
@ColorIdentity({})
public final class StaffoftheFlameMagus extends Card
{
	public static final class StaffoftheFlameMagusAbility0 extends EventTriggeredAbility
	{
		public StaffoftheFlameMagusAbility0(GameState state)
		{
			super(state, "Whenever you cast a red spell or a Mountain enters the battlefield under your control, you gain 1 life.");

			SimpleEventPattern cast = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			cast.put(EventType.Parameter.PLAYER, You.instance());
			cast.put(EventType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasColor.instance(Color.RED)));
			this.addPattern(cast);

			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), HasSubType.instance(SubType.MOUNTAIN), You.instance(), false));

			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public StaffoftheFlameMagus(GameState state)
	{
		super(state);

		// Whenever you cast a red spell or a Mountain enters the battlefield
		// under your control, you gain 1 life.
		this.addAbility(new StaffoftheFlameMagusAbility0(state));
	}
}
