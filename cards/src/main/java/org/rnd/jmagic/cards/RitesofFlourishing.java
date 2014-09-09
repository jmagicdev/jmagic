package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rites of Flourishing")
@Types({Type.ENCHANTMENT})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class RitesofFlourishing extends Card
{
	public static final class RitesofFlourishingAbility0 extends EventTriggeredAbility
	{
		public RitesofFlourishingAbility0(GameState state)
		{
			super(state, "At the beginning of each player's draw step, that player draws an additional card.");

			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, DrawStepOf.instance(Players.instance()));
			this.addPattern(pattern);

			this.addEffect(drawCards(thatPlayer, 1, "That player draws an additional card."));

		}
	}

	public RitesofFlourishing(GameState state)
	{
		super(state);

		// At the beginning of each player's draw step, that player draws an
		// additional card.
		this.addAbility(new RitesofFlourishingAbility0(state));

		// Each player may play an additional land on each of his or her turns.
		this.addAbility(new org.rnd.jmagic.abilities.PlayExtraLands.Final(state, 1, Players.instance(), "Each player may play an additional land on each of his or her turns."));
	}
}
