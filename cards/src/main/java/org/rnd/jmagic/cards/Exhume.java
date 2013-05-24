package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Exhume")
@Types({Type.SORCERY})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class Exhume extends Card
{
	public Exhume(GameState state)
	{
		super(state);

		// Each player puts a creature card from his or her graveyard onto the
		// battlefield.

		DynamicEvaluation player = DynamicEvaluation.instance();

		EventFactory individualEffect = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Each player puts a creature card from his or her graveyard onto the battlefield.");
		individualEffect.parameters.put(EventType.Parameter.CAUSE, This.instance());
		individualEffect.parameters.put(EventType.Parameter.CONTROLLER, player);
		individualEffect.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(player))));

		EventFactory factory = new EventFactory(FOR_EACH_PLAYER, "Each player puts a creature card from his or her graveyard onto the battlefield.");
		factory.parameters.put(EventType.Parameter.TARGET, Identity.instance(player));
		factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(individualEffect));
		this.addEffect(factory);
	}
}
