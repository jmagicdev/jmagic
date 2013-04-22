package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

public final class EldraziReshuffle extends EventTriggeredAbility
{
	private String spellName;

	public EldraziReshuffle(GameState state, String spellName)
	{
		super(state, "When " + spellName + " is put into a graveyard from anywhere, its owner shuffles his or her graveyard into his or her library.");
		this.spellName = spellName;

		this.triggersFromGraveyard();
		SetGenerator owner = OwnerOf.instance(ABILITY_SOURCE_OF_THIS);

		this.addPattern(new SimpleZoneChangePattern(null, GraveyardOf.instance(Players.instance()), ABILITY_SOURCE_OF_THIS, false));

		EventFactory factory = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle his or her graveyard into his or her library");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, Union.instance(owner, InZone.instance(GraveyardOf.instance(owner))));
		this.addEffect(factory);
	}

	@Override
	public EldraziReshuffle create(Game game)
	{
		return new EldraziReshuffle(game.physicalState, this.spellName);
	}
}
