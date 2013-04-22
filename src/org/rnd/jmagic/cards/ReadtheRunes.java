package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Read the Runes")
@Types({Type.INSTANT})
@ManaCost("XU")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class ReadtheRunes extends Card
{
	public static final class DrawnThisWay extends SetGenerator
	{
		private final EventFactory factory;

		public static final SetGenerator instance(EventFactory factory)
		{
			factory.preserveCreatedEvents();
			return new DrawnThisWay(factory);
		}

		private DrawnThisWay(EventFactory factory)
		{
			this.factory = factory;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			Event e = ((GameObject)thisObject).getEffectGenerated(state, this.factory);
			if(e == null)
				return Empty.set;

			return evaluateChildren(state, e);
		}

		private Set evaluateChildren(GameState state, Event parent)
		{
			Set ret = new Set();
			for(Event child: parent.children.keySet())
				ret.addAll(evaluateChildren(state, child));

			if(parent.type == EventType.DRAW_ONE_CARD)
			{
				Set zc = parent.getResult();
				int drawnID = zc.getOne(ZoneChange.class).newObjectID;
				ret.add(state.<GameObject>get(drawnID));
			}

			return ret;
		}
	}

	public ReadtheRunes(GameState state)
	{
		super(state);

		// Draw X cards.
		EventFactory drawCards = drawCards(You.instance(), ValueOfX.instance(This.instance()), "Draw X cards.");
		this.addEffect(drawCards);

		// For each card drawn this way, discard a card unless you sacrifice a
		// permanent.
		EventFactory sacrifice = sacrifice(You.instance(), 1, Permanents.instance(), "Sacrifice a permanent");
		EventFactory discard = discardCards(You.instance(), 1, "Discard a card");
		EventFactory unless = unless(You.instance(), discard, sacrifice, "Discard a card unless you sacrifice a permanent");

		EventFactory effect = new EventFactory(FOR_EACH, "For each card drawn this way, discard a card unless you sacrifice a permanent.");
		effect.parameters.put(EventType.Parameter.OBJECT, DrawnThisWay.instance(drawCards));
		effect.parameters.put(EventType.Parameter.TARGET, Identity.instance(DynamicEvaluation.instance()));
		effect.parameters.put(EventType.Parameter.EFFECT, Identity.instance(unless));
		this.addEffect(effect);
	}
}
