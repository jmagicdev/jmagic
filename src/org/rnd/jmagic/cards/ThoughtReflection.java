package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Thought Reflection")
@Types({Type.ENCHANTMENT})
@ManaCost("4UUU")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ThoughtReflection extends Card
{
	public static final class DoubleDraw extends StaticAbility
	{
		public DoubleDraw(GameState state)
		{
			super(state, "If you would draw a card, draw two cards instead.");
			SetGenerator controller = ControllerOf.instance(This.instance());

			SimpleEventPattern drawOne = new SimpleEventPattern(EventType.DRAW_ONE_CARD);
			drawOne.put(EventType.Parameter.PLAYER, controller);

			EventType.ParameterMap drawTwoParameters = new EventType.ParameterMap();
			drawTwoParameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			EventReplacementEffect drawTwo = new EventReplacementEffect(this.game, "If you would draw a card, draw two cards instead", drawOne);
			drawTwo.addEffect(new EventFactory(EventType.DRAW_CARDS, drawTwoParameters, "Draw two cards"));

			this.addEffectPart(replacementEffectPart(drawTwo));
		}
	}

	public ThoughtReflection(GameState state)
	{
		super(state);

		this.addAbility(new DoubleDraw(state));
	}
}
