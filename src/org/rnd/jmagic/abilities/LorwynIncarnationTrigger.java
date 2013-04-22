package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class LorwynIncarnationTrigger extends EventTriggeredAbility
{
	private String cardName;

	public LorwynIncarnationTrigger(GameState state, String cardName)
	{
		super(state, "When " + cardName + " is put into a graveyard from anywhere, shuffle it into its owner's library.");
		this.cardName = cardName;
		this.triggersFromGraveyard();

		this.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), ABILITY_SOURCE_OF_THIS, false));

		EventFactory factory = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle it into its owner's library");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, Union.instance(OwnerOf.instance(ABILITY_SOURCE_OF_THIS), ABILITY_SOURCE_OF_THIS));
		this.addEffect(factory);
	}

	@Override
	public LorwynIncarnationTrigger create(Game game)
	{
		return new LorwynIncarnationTrigger(game.physicalState, this.cardName);
	}
}
