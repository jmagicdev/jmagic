package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Praetor's Grasp")
@Types({Type.SORCERY})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class PraetorsGrasp extends Card
{
	public PraetorsGrasp(GameState state)
	{
		super(state);

		// Search target opponent's library for a card and exile it face down.
		// Then that player shuffles his or her library. You may look at and
		// play that card for as long as it remains exiled.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));

		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search target opponent's library for a card and exile it face down. Then that player shuffles his or her library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.TARGET, target);
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, ExileZone.instance());
		search.parameters.put(EventType.Parameter.HIDDEN, Empty.instance());
		this.addEffect(search);

		SetGenerator object = EffectResult.instance(search);

		ContinuousEffect.Part lookEffect = new ContinuousEffect.Part(ContinuousEffectType.LOOK);
		lookEffect.parameters.put(ContinuousEffectType.Parameter.OBJECT, object);
		lookEffect.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

		ContinuousEffect.Part playEffect = new ContinuousEffect.Part(ContinuousEffectType.MAY_PLAY_LOCATION);
		playEffect.parameters.put(ContinuousEffectType.Parameter.OBJECT, object);
		playEffect.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));

		this.addEffect(createFloatingEffect(Not.instance(Exists.instance(object)), "You may look at and play that card for as long as it remains exiled.", lookEffect, playEffect));
	}
}
