package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Fate Unraveler")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.HAG})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class FateUnraveler extends Card
{
	public static final class FateUnravelerAbility0 extends EventTriggeredAbility
	{
		public FateUnravelerAbility0(GameState state)
		{
			super(state, "Whenever an opponent draws a card, Fate Unraveler deals 1 damage to that player.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(ControllerOf.instance(ABILITY_SOURCE_OF_THIS)));
			this.addPattern(pattern);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);
			this.addEffect(permanentDealDamage(1, thatPlayer, "Fate Unraveler deals 1 damage to that player."));
		}
	}

	public FateUnraveler(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Whenever an opponent draws a card, Fate Unraveler deals 1 damage to
		// that player.
		this.addAbility(new FateUnravelerAbility0(state));
	}
}
