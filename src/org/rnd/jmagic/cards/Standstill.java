package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Standstill")
@Types({Type.ENCHANTMENT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Standstill extends Card
{
	public static final class StandstillAbility0 extends EventTriggeredAbility
	{
		public StandstillAbility0(GameState state)
		{
			super(state, "When a player casts a spell, sacrifice Standstill. If you do, each of that player's opponents draws three cards.");

			SimpleEventPattern whenSomeoneCastsASpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			whenSomeoneCastsASpell.put(EventType.Parameter.OBJECT, Spells.instance());
			this.addPattern(whenSomeoneCastsASpell);

			EventFactory sacThis = sacrificeThis("Standstill");

			SetGenerator opponents = OpponentsOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER));
			EventFactory oppsDrawThree = drawCards(opponents, 3, "Each of that player's opponents draws three cards");

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Sacrifice Standstill. If you do, each of that player's opponents draws three cards.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(sacThis));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(oppsDrawThree));
			this.addEffect(effect);
		}
	}

	public Standstill(GameState state)
	{
		super(state);

		// When a player casts a spell, sacrifice Standstill. If you do, each of
		// that player's opponents draws three cards.
		this.addAbility(new StandstillAbility0(state));
	}
}
