package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sorcerer's Strongbox")
@Types({Type.ARTIFACT})
@ManaCost("4")
@ColorIdentity({})
public final class SorcerersStrongbox extends Card
{
	public static final class SorcerersStrongboxAbility0 extends ActivatedAbility
	{
		public SorcerersStrongboxAbility0(GameState state)
		{
			super(state, "(2), (T): Flip a coin. If you win the flip, sacrifice Sorcerer's Strongbox and draw three cards.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;

			EventFactory flip = new EventFactory(EventType.FLIP_COIN, "Flip a coin");
			flip.parameters.put(EventType.Parameter.PLAYER, You.instance());

			EventFactory sac = sacrificeThis("Sorcerer's Strongbox");
			EventFactory draw = drawCards(You.instance(), 3, "draw three cards");

			EventFactory winFlipForDraw = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "Flip a coin. If you win the flip, sacrifice Sorcerer's Strongbox and draw three cards.");
			winFlipForDraw.parameters.put(EventType.Parameter.IF, Identity.instance(flip));
			winFlipForDraw.parameters.put(EventType.Parameter.THEN, Identity.instance(sequence(sac, draw)));
			this.addEffect(winFlipForDraw);
		}
	}

	public SorcerersStrongbox(GameState state)
	{
		super(state);

		// (2), (T): Flip a coin. If you win the flip, sacrifice Sorcerer's
		// Strongbox and draw three cards.
		this.addAbility(new SorcerersStrongboxAbility0(state));
	}
}
