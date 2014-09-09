package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Armillary Sphere")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class ArmillarySphere extends Card
{
	public static final class LandFetch extends ActivatedAbility
	{
		public LandFetch(GameState state)
		{
			super(state, "(2), (T), Sacrifice Armillary Sphere: Search your library for up to two basic land cards, reveal them, and put them into your hand. Then shuffle your library.");

			this.setManaCost(new ManaPool("2"));
			this.costsTap = true;
			this.addCost(sacrificeThis("Armillary Sphere"));

			EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to two basic land cards, reveal them, and put them into your hand. Then shuffle your library.");
			search.parameters.put(EventType.Parameter.CAUSE, This.instance());
			search.parameters.put(EventType.Parameter.PLAYER, You.instance());
			search.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
			search.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			search.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			this.addEffect(search);
		}
	}

	public ArmillarySphere(GameState state)
	{
		super(state);

		// (2), (T), Sacrifice Armillary Sphere: Search your library for up to
		// two basic land cards, reveal them, and put them into your hand. Then
		// shuffle your library.
		this.addAbility(new LandFetch(state));
	}
}
