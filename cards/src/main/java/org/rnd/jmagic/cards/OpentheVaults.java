package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Open the Vaults")
@Types({Type.SORCERY})
@ManaCost("4WW")
@ColorIdentity({Color.WHITE})
public final class OpentheVaults extends Card
{
	public OpentheVaults(GameState state)
	{
		super(state);

		// Return all artifact and enchantment cards from all graveyards

		SetGenerator cardTypes = HasType.instance(Type.ARTIFACT, Type.ENCHANTMENT);
		SetGenerator inGraveyards = InZone.instance(GraveyardOf.instance(Players.instance()));
		SetGenerator toReturn = Intersect.instance(cardTypes, inGraveyards);

		// to the battlefield under their owners' control.
		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.OBJECT, toReturn);
		this.addEffect(new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, parameters, "Return all artifact and enchantment cards from all graveyards to the battlefield under their owner's control."));
	}
}
