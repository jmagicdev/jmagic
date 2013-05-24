package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Condemn")
@Types({Type.INSTANT})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class Condemn extends Card
{
	public Condemn(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(Attacking.instance(), CreaturePermanents.instance()), "target attacking creature");

		EventType.ParameterMap moveParameters = new EventType.ParameterMap();
		moveParameters.put(EventType.Parameter.CAUSE, This.instance());
		moveParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		moveParameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
		this.addEffect(new EventFactory(EventType.PUT_INTO_LIBRARY, moveParameters, "Put target attacking creature on the bottom of its owner's library."));

		this.addEffect(gainLife(ControllerOf.instance(targetedBy(target)), ToughnessOf.instance(targetedBy(target)), "Its controller gains life equal to its toughness."));
	}
}
