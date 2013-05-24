package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Khalni Heart Expedition")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class KhalniHeartExpedition extends Card
{
	public static final class SoIHeardYouLikeLandfall extends ActivatedAbility
	{
		public SoIHeardYouLikeLandfall(GameState state)
		{
			super(state, "Remove three quest counters from Khalni Heart Expedition and sacrifice it: Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle your library.");

			this.addCost(removeCountersFromThis(3, Counter.CounterType.QUEST, "Khalni Heart Expedition"));

			this.addCost(sacrificeThis("Khalni Heart Expedition"));

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.NUMBER, Identity.instance(new org.rnd.util.NumberRange(0, 2)));
			parameters.put(EventType.Parameter.TO, Battlefield.instance());
			parameters.put(EventType.Parameter.TAPPED, Empty.instance());
			parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, parameters, "Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle your library."));
		}
	}

	public KhalniHeartExpedition(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.LandfallForQuestCounter(state, this.getName()));
		this.addAbility(new SoIHeardYouLikeLandfall(state));
	}
}
