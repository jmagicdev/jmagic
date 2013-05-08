package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Postmortem Lunge")
@Types({Type.SORCERY})
@ManaCost("X(B/P)")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class PostmortemLunge extends Card
{
	public PostmortemLunge(GameState state)
	{
		super(state);

		// ((b/p) can be paid with either (B) or 2 life.)

		// Return target creature card with converted mana cost X from your
		// graveyard to the battlefield. It gains haste. Exile it at the
		// beginning of the next end step.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE), HasConvertedManaCost.instance(ValueOfX.instance(This.instance())), InZone.instance(GraveyardOf.instance(You.instance()))), "target creature card with converted mana cost X in your graveyard"));
		EventFactory move = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return target creature card with converted mana cost X from your graveyard to the battlefield.");
		move.parameters.put(EventType.Parameter.CAUSE, This.instance());
		move.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		move.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(move);

		SetGenerator it = NewObjectOf.instance(EffectResult.instance(move));
		this.addEffect(createFloatingEffect(Empty.instance(), "It gains haste.", addAbilityToObject(it, org.rnd.jmagic.abilities.keywords.Haste.class)));

		// The generators will be evaluated in terms of the delayed trigger, so
		// the effect result will be from the delayed trigger's source, this
		// card.
		it = NewObjectOf.instance(effectResultFrom(move, ABILITY_SOURCE_OF_THIS));
		EventFactory exile = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "Exile it at the beginning of the next end step.");
		exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
		exile.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfEachEndStep()));
		exile.parameters.put(EventType.Parameter.EFFECT, Identity.instance(exile(it, "Exile it.")));
		this.addEffect(exile);
	}
}
