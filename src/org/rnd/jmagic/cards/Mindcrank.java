package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Mindcrank")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Mindcrank extends Card
{
	public static final class MindcrankAbility0 extends EventTriggeredAbility
	{
		public MindcrankAbility0(GameState state)
		{
			super(state, "Whenever an opponent loses life, that player puts that many cards from the top of his or her library into his or her graveyard.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.LOSE_LIFE_ONE_PLAYER);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			this.addPattern(pattern);

			SetGenerator trigger = TriggerEvent.instance(This.instance());
			this.addEffect(millCards(EventParameter.instance(trigger, EventType.Parameter.PLAYER), EventParameter.instance(trigger, EventType.Parameter.NUMBER), "That player puts that many cards from the top of his or her library into his or her graveyard."));
		}
	}

	public Mindcrank(GameState state)
	{
		super(state);

		// Whenever an opponent loses life, that player puts that many cards
		// from the top of his or her library into his or her graveyard. (Damage
		// dealt by sources without infect causes loss of life.)
		this.addAbility(new MindcrankAbility0(state));
	}
}
