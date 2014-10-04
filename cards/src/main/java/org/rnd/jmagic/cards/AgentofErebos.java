package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Agent of Erebos")
@Types({Type.CREATURE, Type.ENCHANTMENT})
@SubTypes({SubType.ZOMBIE})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class AgentofErebos extends Card
{
	public static final class AgentofErebosAbility0 extends EventTriggeredAbility
	{
		public AgentofErebosAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Agent of Erebos or another enchantment enters the battlefield under your control, exile all cards from target player's graveyard.");
			this.addPattern(constellation());

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			SetGenerator cards = InZone.instance(GraveyardOf.instance(target));
			this.addEffect(exile(cards, "Exile all cards from target player's graveyard."));
		}
	}

	public AgentofErebos(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Constellation \u2014 Whenever Agent of Erebos or another enchantment
		// enters the battlefield under your control, exile all cards from
		// target player's graveyard.
		this.addAbility(new AgentofErebosAbility0(state));
	}
}
