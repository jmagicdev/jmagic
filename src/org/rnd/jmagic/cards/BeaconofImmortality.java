package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Beacon of Immortality")
@Types({Type.INSTANT})
@ManaCost("5W")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class BeaconofImmortality extends Card
{
	public BeaconofImmortality(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		SetGenerator targetPlayer = targetedBy(target);

		EventType.ParameterMap lifeParameters = new EventType.ParameterMap();
		lifeParameters.put(EventType.Parameter.CAUSE, This.instance());
		lifeParameters.put(EventType.Parameter.NUMBER, Multiply.instance(numberGenerator(2), LifeTotalOf.instance(targetPlayer)));
		lifeParameters.put(EventType.Parameter.PLAYER, targetPlayer);
		this.addEffect(new EventFactory(EventType.SET_LIFE, lifeParameters, "Double target player's life total."));

		EventType.ParameterMap shuffleParameters = new EventType.ParameterMap();
		shuffleParameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffleParameters.put(EventType.Parameter.OBJECT, Union.instance(This.instance(), OwnerOf.instance(This.instance())));
		this.addEffect(new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, shuffleParameters, "Shuffle Beacon of Immortality into its owner's library."));
	}
}
