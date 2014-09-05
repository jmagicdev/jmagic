package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Disentomb")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2012.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Disentomb extends Card
{
	public Disentomb(GameState state)
	{
		super(state);

		// Return target creature card from your graveyard to your hand.
		SetGenerator yourYard = GraveyardOf.instance(You.instance());
		SetGenerator inYourYard = InZone.instance(yourYard);
		SetGenerator creaturesInYourYard = Intersect.instance(HasType.instance(Type.CREATURE), inYourYard);
		Target target = this.addTarget(creaturesInYourYard, "target creature card from your graveyard");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		this.addEffect(new EventFactory(EventType.MOVE_OBJECTS, parameters, "Return target creature card from your graveyard to your hand."));
	}
}
