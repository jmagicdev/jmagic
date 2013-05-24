package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reminisce")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Reminisce extends Card
{
	public Reminisce(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Target player shuffles his or her graveyard into his or her library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(InZone.instance(GraveyardOf.instance(targetedBy(target))), targetedBy(target)));
		this.addEffect(shuffle);
	}
}
