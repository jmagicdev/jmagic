package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cruel Revival")
@Types({Type.INSTANT})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class CruelRevival extends Card
{
	public CruelRevival(GameState state)
	{
		super(state);

		SetGenerator zombies = HasSubType.instance(SubType.ZOMBIE);
		Target target1 = this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), zombies), "target non-Zombie creature");
		Target target2 = this.addTarget(Intersect.instance(InZone.instance(GraveyardOf.instance(You.instance())), zombies), "up to one target Zombie card from your graveyard");
		target2.setNumber(0, 1);

		// Destroy target non-Zombie creature. It can't be regenerated. Return
		// up to one target Zombie card from your graveyard to your hand.
		this.addEffects(bury(this, targetedBy(target1), "Destroy target non-Zombie creature. It can't be regenerated."));

		EventFactory returnZombie = new EventFactory(EventType.MOVE_OBJECTS, "Return up to one target Zombie card from your graveyard to your hand.");
		returnZombie.parameters.put(EventType.Parameter.CAUSE, This.instance());
		returnZombie.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		returnZombie.parameters.put(EventType.Parameter.OBJECT, targetedBy(target2));
		this.addEffect(returnZombie);
	}
}
