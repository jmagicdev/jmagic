package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Recollect")
@Types({Type.SORCERY})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Recollect extends Card
{
	public Recollect(GameState state)
	{
		super(state);

		SetGenerator graveyard = GraveyardOf.instance(You.instance());
		Target target = this.addTarget(InZone.instance(graveyard), "target card in a graveyard");

		EventType.ParameterMap moveParameters = new EventType.ParameterMap();
		moveParameters.put(EventType.Parameter.CAUSE, This.instance());
		moveParameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		moveParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(new EventFactory(EventType.MOVE_OBJECTS, moveParameters, "Return target card from your graveyard to your hand."));
	}
}
