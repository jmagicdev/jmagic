package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vile Rebirth")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class VileRebirth extends Card
{
	public VileRebirth(GameState state)
	{
		super(state);

		// Exile target creature card from a graveyard. Put a 2/2 black Zombie
		// creature token onto the battlefield.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance()))), "target creature card in a graveyard"));
		this.addEffect(exile(target, "Exile target creature card from a graveyard."));

		CreateTokensFactory factory = new CreateTokensFactory(1, 2, 2, "Put a 2/2 black Zombie creature token onto the battlefield.");
		factory.setColors(Color.BLACK);
		factory.setSubTypes(SubType.ZOMBIE);
		this.addEffect(factory.getEventFactory());
	}
}
