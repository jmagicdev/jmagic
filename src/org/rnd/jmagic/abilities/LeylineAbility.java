package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class LeylineAbility extends StaticAbility
{
	private final String cardName;

	public LeylineAbility(GameState state, String cardName)
	{
		super(state, "If " + cardName + " is in your opening hand, you may begin the game with it on the battlefield.");
		this.cardName = cardName;
		this.canApply = NonEmpty.instance();

		EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "Begin the game with " + cardName + " on the battlefield.");
		putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
		putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, This.instance());

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BEGIN_THE_GAME_EFFECT);
		this.addEffectPart(part);
	}

	@Override
	public LeylineAbility create(Game game)
	{
		return new LeylineAbility(game.physicalState, this.cardName);
	}
}