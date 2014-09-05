package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Nature's Spiral")
@Types({Type.SORCERY})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class NaturesSpiral extends Card
{
	public NaturesSpiral(GameState state)
	{
		super(state);

		SetGenerator graveyard = GraveyardOf.instance(You.instance());

		Target target = this.addTarget(Intersect.instance(InZone.instance(graveyard), HasType.instance(Type.permanentTypes())), "target permanent card from your graveyard");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(new EventFactory(EventType.MOVE_OBJECTS, parameters, "Return target permanent card from your graveyard to your hand."));
	}
}
