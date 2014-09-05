package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Break Open")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Onslaught.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BreakOpen extends Card
{
	public BreakOpen(GameState state)
	{
		super(state);

		SetGenerator faceDownCreatures = Intersect.instance(FaceDown.instance(), CreaturePermanents.instance());
		Target target = this.addTarget(Intersect.instance(faceDownCreatures, ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target face-down creature an opponent controls");

		EventFactory effect = new EventFactory(EventType.TURN_PERMANENT_FACE_UP, "Turn target face-down creature an opponent controls face up.");
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(effect);
	}
}
