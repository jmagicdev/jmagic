package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Military Intelligence")
@Types({Type.ENCHANTMENT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class MilitaryIntelligence extends Card
{
	public static final class MilitaryIntelligenceAbility0 extends EventTriggeredAbility
	{
		public MilitaryIntelligenceAbility0(GameState state)
		{
			super(state, "Whenever you attack with two or more creatures, draw a card.");

			SimpleEventPattern attack = new SimpleEventPattern(EventType.DECLARE_ATTACKERS);
			attack.withResult((gameState, thisObject, set) -> set.size() >= 2);
			this.addPattern(attack);

			this.addEffect(drawACard());
		}
	}

	public MilitaryIntelligence(GameState state)
	{
		super(state);

		// Whenever you attack with two or more creatures, draw a card.
		this.addAbility(new MilitaryIntelligenceAbility0(state));
	}
}
