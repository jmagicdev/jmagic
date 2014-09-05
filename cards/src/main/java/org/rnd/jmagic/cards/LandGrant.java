package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Land Grant")
@Types({Type.SORCERY})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = MercadianMasques.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class LandGrant extends Card
{
	public static final class LandGrantAbility0 extends StaticAbility
	{
		public LandGrantAbility0(GameState state)
		{
			super(state, "If you have no land cards in hand, you may reveal your hand rather than pay Land Grant's mana cost.");
			SetGenerator yourHand = HandOf.instance(You.instance());
			SetGenerator landsInYourHand = Intersect.instance(HasType.instance(Type.LAND), InZone.instance(yourHand));
			this.canApply = Intersect.instance(numberGenerator(0), Count.instance(landsInYourHand));

			EventFactory reveal = reveal(yourHand, "Reveal your hand");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new CostCollection(CostCollection.TYPE_ALTERNATE, reveal)));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}

	public LandGrant(GameState state)
	{
		super(state);

		// If you have no land cards in hand, you may reveal your hand rather
		// than pay Land Grant's mana cost.
		this.addAbility(new LandGrantAbility0(state));

		// Search your library for a Forest card, reveal that card, and put it
		// into your hand. Then shuffle your library.
		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for a Forest card, reveal that card, and put it into your hand. Then shuffle your library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		search.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.FOREST)));
		this.addEffect(search);
	}
}
