package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Praetor's Counsel")
@Types({Type.SORCERY})
@ManaCost("5GGG")
@ColorIdentity({Color.GREEN})
public final class PraetorsCounsel extends Card
{
	public PraetorsCounsel(GameState state)
	{
		super(state);

		SetGenerator graveyard = GraveyardOf.instance(You.instance());

		// Return all cards from your graveyard to your hand. Exile Praetor's
		// Counsel. You have no maximum hand size for the rest of the game.
		EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return all cards from your graveyard to your hand.");
		move.parameters.put(EventType.Parameter.CAUSE, This.instance());
		move.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		move.parameters.put(EventType.Parameter.OBJECT, InZone.instance(graveyard));
		this.addEffect(move);

		this.addEffect(exile(This.instance(), "Exile Praetor's Counsel."));

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_MAX_HAND_SIZE);
		part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
		part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Empty.instance());
		this.addEffect(createFloatingEffect(Empty.instance(), "You have no maximum hand size for the rest of the game.", part));
	}
}
