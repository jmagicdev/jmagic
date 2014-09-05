package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Repay in Kind")
@Types({Type.SORCERY})
@ManaCost("5BB")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class RepayinKind extends Card
{
	public RepayinKind(GameState state)
	{
		super(state);

		// Each player's life total becomes the lowest life total among all
		// players.
		SetGenerator lowestLife = Minimum.instance(LifeTotalOf.instance(Players.instance()));

		EventFactory setLife = new EventFactory(EventType.SET_LIFE, "Each player's life total becomes the lowest life total among all players.");
		setLife.parameters.put(EventType.Parameter.CAUSE, This.instance());
		setLife.parameters.put(EventType.Parameter.NUMBER, lowestLife);
		setLife.parameters.put(EventType.Parameter.PLAYER, Players.instance());
		this.addEffect(setLife);
	}
}
