package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Rite of Replication")
@Types({Type.SORCERY})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class RiteofReplication extends Card
{
	public RiteofReplication(GameState state)
	{
		super(state);

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(5)");
		this.addAbility(kicker);

		EventFactory factory = new EventFactory(EventType.CREATE_TOKEN_COPY, "Put a token that's a copy of target creature onto the battlefield. If Rite of Replication was kicked, put five of those tokens onto the battlefield instead.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		factory.parameters.put(EventType.Parameter.NUMBER, IfThenElse.instance(ThisSpellWasKicked.instance(kicker.costCollections[0]), numberGenerator(5), numberGenerator(1)));
		factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(factory);
	}
}
