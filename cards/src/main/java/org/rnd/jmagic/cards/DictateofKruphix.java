package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;

@Name("Dictate of Kruphix")
@Types({Type.ENCHANTMENT})
@ManaCost("1UU")
@ColorIdentity({Color.BLUE})
public final class DictateofKruphix extends Card
{
	public static final class DictateofKruphixAbility1 extends EventTriggeredAbility
	{
		public DictateofKruphixAbility1(GameState state)
		{
			super(state, "At the beginning of each player's draw step, that player draws an additional card.");

			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, DrawStepOf.instance(Players.instance()));
			this.addPattern(pattern);

			this.addEffect(drawCards(thatPlayer, 1, "That player draws an additional card."));
		}
	}

	public DictateofKruphix(GameState state)
	{
		super(state);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// At the beginning of each player's draw step, that player draws an
		// additional card.
		this.addAbility(new DictateofKruphixAbility1(state));
	}
}
