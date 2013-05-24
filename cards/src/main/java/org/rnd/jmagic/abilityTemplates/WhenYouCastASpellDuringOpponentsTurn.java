package org.rnd.jmagic.abilityTemplates;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public abstract class WhenYouCastASpellDuringOpponentsTurn extends EventTriggeredAbility
{
	public WhenYouCastASpellDuringOpponentsTurn(GameState state, String effectText)
	{
		super(state, "Whenever you cast a spell during an opponent's turn, " + effectText);

		SimpleEventPattern whenYouCastASpell = new SimpleEventPattern(EventType.BECOMES_PLAYED);
		whenYouCastASpell.put(EventType.Parameter.PLAYER, You.instance());
		whenYouCastASpell.put(EventType.Parameter.OBJECT, Spells.instance());
		this.addPattern(whenYouCastASpell);

		SetGenerator duringOpponentsTurn = Intersect.instance(TurnOf.instance(OpponentsOf.instance(You.instance())), CurrentTurn.instance());
		this.canTrigger = Both.instance(this.canTrigger, duringOpponentsTurn);
	}
}