package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dread Return")
@Types({Type.SORCERY})
@ManaCost("2BB")
@ColorIdentity({Color.BLACK})
public final class DreadReturn extends Card
{
	public DreadReturn(GameState state)
	{
		super(state);

		// Return target creature card from your graveyard to the battlefield.
		Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(You.instance()))), "target creature from your graveyard");

		EventType.ParameterMap moveParameters = new EventType.ParameterMap();
		moveParameters.put(EventType.Parameter.CAUSE, This.instance());
		moveParameters.put(EventType.Parameter.CONTROLLER, You.instance());
		moveParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, moveParameters, "Return target creature card from your graveyard to the battlefield."));

		// Flashback-Sacrifice three creatures. (You may cast this card
		// from your graveyard for its flashback cost. Then exile it.)
		EventFactory sacrificeThreeCreatures = sacrifice(You.instance(), 3, CreaturePermanents.instance(), "Sacrifice three creatures.");
		CostCollection flashbackCost = new CostCollection(org.rnd.jmagic.abilities.keywords.Flashback.COST_TYPE, sacrificeThreeCreatures);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, flashbackCost));
	}
}
