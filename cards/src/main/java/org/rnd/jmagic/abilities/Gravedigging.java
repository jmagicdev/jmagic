package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Gravedigging extends EventTriggeredAbility
{
	private final String creatureName;

	public Gravedigging(GameState state, String creatureName)
	{
		super(state, "When " + creatureName + " enters the battlefield, you may return target creature card from your graveyard to your hand.");

		this.creatureName = creatureName;

		this.addPattern(whenThisEntersTheBattlefield());

		SetGenerator yard = GraveyardOf.instance(You.instance());

		Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(yard)), "target creature card from your graveyard");

		EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return target creature card from your graveyard to your hand");
		move.parameters.put(EventType.Parameter.CAUSE, This.instance());
		move.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		move.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));

		this.addEffect(youMay(move, "You may return target creature card from your graveyard to your hand."));
	}

	@Override
	public Gravedigging create(Game game)
	{
		return new Gravedigging(game.physicalState, this.creatureName);
	}
}