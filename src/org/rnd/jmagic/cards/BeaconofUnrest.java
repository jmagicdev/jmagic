package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Beacon of Unrest")
@Types({Type.SORCERY})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class BeaconofUnrest extends Card
{
	public BeaconofUnrest(GameState state)
	{
		super(state);

		SetGenerator artifactsAndCreatures = Union.instance(HasType.instance(Type.ARTIFACT), HasType.instance(Type.CREATURE));
		SetGenerator graveyards = GraveyardOf.instance(Players.instance());
		SetGenerator targets = Intersect.instance(artifactsAndCreatures, InZone.instance(graveyards));

		Target target = this.addTarget(targets, "target artifact or creature card from a graveyard");

		EventType.ParameterMap moveParameters = new EventType.ParameterMap();
		moveParameters.put(EventType.Parameter.CAUSE, This.instance());
		moveParameters.put(EventType.Parameter.CONTROLLER, You.instance());
		moveParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, moveParameters, "Put target artifact or creature card from a graveyard onto the battlefield under your control."));

		EventType.ParameterMap shuffleParameters = new EventType.ParameterMap();
		shuffleParameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffleParameters.put(EventType.Parameter.OBJECT, Union.instance(This.instance(), OwnerOf.instance(This.instance())));
		this.addEffect(new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, shuffleParameters, "Shuffle Beacon of Unrest into its owner's library."));
	}
}
