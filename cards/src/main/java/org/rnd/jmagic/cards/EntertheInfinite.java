package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Enter the Infinite")
@Types({Type.SORCERY})
@ManaCost("8UUUU")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLUE})
public final class EntertheInfinite extends Card
{
	public static PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("EntertheInfinite", "Put a card from your hand on top of your library.", false);

	public static final class DelayedEnd extends UntilNextTurn.EventAndBeginTurnTracker
	{
		public DelayedEnd()
		{
			super(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT);
		}
	}

	public EntertheInfinite(GameState state)
	{
		super(state);

		// Draw cards equal to the number of cards in your library, then put a
		// card from your hand on top of your library. You have no maximum hand
		// size until your next turn.
		this.addEffect(drawCards(You.instance(), Count.instance(InZone.instance(LibraryOf.instance(You.instance()))), "Draw cards equal to the number of cards in your library,"));

		EventFactory move = new EventFactory(EventType.MOVE_CHOICE, "then put a card from your hand on top of your library.");
		move.parameters.put(EventType.Parameter.CAUSE, This.instance());
		move.parameters.put(EventType.Parameter.PLAYER, You.instance());
		move.parameters.put(EventType.Parameter.OBJECT, InZone.instance(HandOf.instance(You.instance())));
		move.parameters.put(EventType.Parameter.CHOICE, Identity.instance(REASON));
		move.parameters.put(EventType.Parameter.TO, LibraryOf.instance(You.instance()));
		move.parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		this.addEffect(move);

		SetGenerator expires = Not.instance(Intersect.instance(This.instance(), UntilNextTurn.instance(DelayedEnd.class)));
		state.ensureTracker(new DelayedEnd());

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_MAX_HAND_SIZE);
		part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Empty.instance());
		this.addEffect(createFloatingEffect(expires, "You have no maximum hand size until your next turn.", part));
	}
}
