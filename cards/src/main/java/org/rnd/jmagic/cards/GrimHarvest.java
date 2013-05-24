package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Grim Harvest")
@Types({Type.INSTANT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.COLDSNAP, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GrimHarvest extends Card
{
	public GrimHarvest(GameState state)
	{
		super(state);

		SetGenerator controllersGraveyard = GraveyardOf.instance(You.instance());

		Target target = this.addTarget(Intersect.instance(InZone.instance(controllersGraveyard), HasType.instance(Type.CREATURE)), "target creature card from your graveyard");

		EventType.ParameterMap returnParameters = new EventType.ParameterMap();
		returnParameters.put(EventType.Parameter.CAUSE, This.instance());
		returnParameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		returnParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(new EventFactory(EventType.MOVE_OBJECTS, returnParameters, "Return target creature card from your graveyard to your hand."));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Recover.ForMana(state, "(2)(B)"));
	}

}
