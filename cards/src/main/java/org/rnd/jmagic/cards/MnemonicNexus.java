package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mnemonic Nexus")
@Types({Type.INSTANT})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class MnemonicNexus extends Card
{
	public MnemonicNexus(GameState state)
	{
		super(state);

		EventFactory factory = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Each player shuffles his or her graveyard into his or her library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, Union.instance(InZone.instance(GraveyardOf.instance(Players.instance())), Players.instance()));
		this.addEffect(factory);
	}
}
