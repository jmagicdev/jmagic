package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mire's Toll")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class MiresToll extends Card
{
	public MiresToll(GameState state)
	{
		super(state);

		// Target player reveals a number of cards from his or her hand equal to
		// the number of Swamps you control. You choose one of them. That player
		// discards that card.
		Target target = this.addTarget(Players.instance(), "target player");
		EventFactory effect = new EventFactory(REVEAL_SOME_CARDS_AND_DISCARD_FORCE, "Target player reveals a number of cards from his or her hand equal to the number of Swamps you control. You choose one of them. That player discards that card.");
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.NUMBER, Count.instance(Intersect.instance(HasSubType.instance(SubType.SWAMP), ControlledBy.instance(You.instance()))));
		effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
		effect.parameters.put(EventType.Parameter.TARGET, targetedBy(target));
		this.addEffect(effect);
	}
}
