package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mind Sludge")
@Types({Type.SORCERY})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TORMENT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class MindSludge extends Card
{
	public MindSludge(GameState state)
	{
		super(state);

		// Target player discards a card for each Swamp you control.
		Target target = this.addTarget(Players.instance(), "target player");

		SetGenerator swamps = HasSubType.instance(SubType.SWAMP);
		SetGenerator youControl = ControlledBy.instance(You.instance());
		SetGenerator swampsYouControl = Intersect.instance(swamps, youControl);

		EventFactory discard = new EventFactory(EventType.DISCARD_CHOICE, "Target player discards a card for each Swamp you control.");
		discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discard.parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
		discard.parameters.put(EventType.Parameter.NUMBER, Count.instance(swampsYouControl));
		this.addEffect(discard);
	}
}
