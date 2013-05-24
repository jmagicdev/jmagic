package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Eon Hub")
@Types({Type.ARTIFACT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.RARE)})
@ColorIdentity({})
public final class EonHub extends Card
{
	public static final class SkipUpkeeps extends StaticAbility
	{
		public SkipUpkeeps(GameState state)
		{
			super(state, "Players skip their upkeep steps.");

			SimpleEventPattern upkeeps = new SimpleEventPattern(EventType.BEGIN_STEP);
			upkeeps.put(EventType.Parameter.STEP, UpkeepStepOf.instance(Players.instance()));

			EventReplacementEffect skipUpkeeps = new EventReplacementEffect(this.game, "Players skip their upkeep steps", upkeeps);

			this.addEffectPart(replacementEffectPart(skipUpkeeps));
		}
	}

	public EonHub(GameState state)
	{
		super(state);

		this.addAbility(new SkipUpkeeps(state));
	}
}
