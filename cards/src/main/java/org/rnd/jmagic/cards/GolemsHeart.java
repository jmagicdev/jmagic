package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Golem's Heart")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class GolemsHeart extends Card
{
	public static final class GolemsHeartAbility0 extends EventTriggeredAbility
	{
		public GolemsHeartAbility0(GameState state)
		{
			super(state, "Whenever a player casts an artifact spell, you may gain 1 life.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			pattern.put(EventType.Parameter.PLAYER, Players.instance());
			pattern.withResult(HasType.instance(Type.ARTIFACT));
			this.addPattern(pattern);

			this.addEffect(youMay(gainLife(You.instance(), 1, "Gain 1 life."), "You may gain 1 life."));
		}
	}

	public GolemsHeart(GameState state)
	{
		super(state);

		// Whenever a player casts an artifact spell, you may gain 1 life.
		this.addAbility(new GolemsHeartAbility0(state));
	}
}
