package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Replenish")
@Types({Type.SORCERY})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = UrzasDestiny.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Replenish extends Card
{
	public Replenish(GameState state)
	{
		super(state);

		// Return all enchantment cards from your graveyard to the battlefield.
		// (Auras with nothing to enchant remain in your graveyard.)
		SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
		SetGenerator toReturn = Intersect.instance(HasType.instance(Type.ENCHANTMENT), inYourGraveyard);

		EventFactory effect = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "Return all enchantment cards from your graveyard to the battlefield.");
		effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		effect.parameters.put(EventType.Parameter.OBJECT, toReturn);
		this.addEffect(effect);
	}
}
