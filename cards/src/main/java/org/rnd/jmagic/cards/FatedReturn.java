package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Fated Return")
@Types({Type.INSTANT})
@ManaCost("4BBB")
@ColorIdentity({Color.BLACK})
public final class FatedReturn extends Card
{
	public FatedReturn(GameState state)
	{
		super(state);

		// Put target creature card from a graveyard onto the battlefield under
		// your control.
		SetGenerator deadThings = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance())));
		SetGenerator target = targetedBy(this.addTarget(deadThings, "target creature card from a graveyar"));
		EventFactory drop = putOntoBattlefield(target, "Put target creature card from a graveyard onto the battlefield under your control.");
		this.addEffect(drop);

		// It gains indestructible.
		SetGenerator thatCreature = NewObjectOf.instance(EffectResult.instance(drop));
		this.addEffect(createFloatingEffect(Empty.instance(), "It gains indestructible.", //
				addAbilityToObject(thatCreature, org.rnd.jmagic.abilities.keywords.Indestructible.class)));

		// If it's your turn, scry 2.
		SetGenerator itsYourTurn = Intersect.instance(OwnerOf.instance(CurrentTurn.instance()), You.instance());
		this.addEffect(ifThen(itsYourTurn, scry(2, "Scry 2."), "If it's your turn, scry 2."));
	}
}
