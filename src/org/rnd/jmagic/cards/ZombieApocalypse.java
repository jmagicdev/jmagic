package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Zombie Apocalypse")
@Types({Type.SORCERY})
@ManaCost("3BBB")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity(Color.BLACK)
public final class ZombieApocalypse extends Card
{
	public ZombieApocalypse(GameState state)
	{
		super(state);

		// Return all Zombie creature cards from your graveyard to the
		// battlefield tapped, then destroy all Humans.
		SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
		SetGenerator zombies = Intersect.instance(HasSubType.instance(SubType.ZOMBIE), HasType.instance(Type.CREATURE), Cards.instance(), inYourGraveyard);

		EventFactory returnZombies = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_TAPPED, "Return all Zombie creature cards from your graveyard to the battlefield tapped,");
		returnZombies.parameters.put(EventType.Parameter.CAUSE, This.instance());
		returnZombies.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		returnZombies.parameters.put(EventType.Parameter.OBJECT, zombies);
		this.addEffect(returnZombies);

		SetGenerator humans = Intersect.instance(Permanents.instance(), HasSubType.instance(SubType.HUMAN));
		this.addEffect(destroy(humans, "then destroy all Humans."));
	}
}
