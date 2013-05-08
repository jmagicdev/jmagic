package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Horizon Boughs")
@Types({Type.PLANE})
@SubTypes({SubType.PYRULEA})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.SPECIAL)})
@ColorIdentity({})
public final class HorizonBoughs extends Card
{
	public static final class GlobalUntap extends StaticAbility
	{
		public GlobalUntap(GameState state)
		{
			super(state, "All permanents untap during each players untap step.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_UNTAP_EVENT);
			part.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(untap(Permanents.instance(), "All permanents untap.")));
			this.addEffectPart(part);

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class ChaosLandSearch extends EventTriggeredAbility
	{
		public ChaosLandSearch(GameState state)
		{
			super(state, "Whenever you roll (C), you may search your library for up to three basic land cards, put them onto the battlefield tapped, then shuffle your library.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to three basic land cards, put them onto the battlefield tapped. Then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(3));
			factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
			factory.parameters.put(EventType.Parameter.TAPPED, Empty.instance());
			factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			this.addEffect(youMay(factory, "You may search your library for up to three basic land cards, put them onto the battlefield tapped, then shuffle your library."));

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public HorizonBoughs(GameState state)
	{
		super(state);

		this.addAbility(new GlobalUntap(state));

		this.addAbility(new ChaosLandSearch(state));
	}
}
