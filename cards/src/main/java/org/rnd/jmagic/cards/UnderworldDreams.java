package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Underworld Dreams")
@Types({Type.ENCHANTMENT})
@ManaCost("BBB")
@ColorIdentity({Color.BLACK})
public final class UnderworldDreams extends Card
{
	public static final class DrawDamage extends EventTriggeredAbility
	{
		public DrawDamage(GameState state)
		{
			super(state, "Whenever an opponent draws a card, Underworld Dreams deals 1 damage to him or her.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(ControllerOf.instance(ABILITY_SOURCE_OF_THIS)));
			this.addPattern(pattern);

			SetGenerator opponent = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);
			this.addEffect(permanentDealDamage(1, opponent, "Underworld Dreams deals 1 damage to him or her."));
		}
	}

	public UnderworldDreams(GameState state)
	{
		super(state);

		this.addAbility(new DrawDamage(state));
	}
}
