package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sudden Disappearance")
@Types({Type.SORCERY})
@ManaCost("5W")
@Printings({@Printings.Printed(ex = DarkAscension.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class SuddenDisappearance extends Card
{
	public SuddenDisappearance(GameState state)
	{
		super(state);

		// Exile all nonland permanents target player controls. Return the
		// exiled cards to the battlefield under their owner's control at the
		// beginning of the next end step.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		EventFactory exile = exile(RelativeComplement.instance(ControlledBy.instance(target), LandPermanents.instance()), "Exile all nonland permanents target player controls.");
		this.addEffect(exile);

		EventFactory returnFactory = putOntoBattlefield(NewObjectOf.instance(EventResult.instance(Identity.instance(exile))), "Return the exiled cards to the battlefield under their owner's control.");

		EventFactory factory = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Return the exiled cards to the battlefield under their owner's control at the beginning of the next end step.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfTheEndStep()));
		factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(returnFactory));
		this.addEffect(factory);
	}
}
