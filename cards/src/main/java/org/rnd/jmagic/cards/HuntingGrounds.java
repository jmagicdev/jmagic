package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Hunting Grounds")
@Types({Type.ENCHANTMENT})
@ManaCost("GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class HuntingGrounds extends Card
{
	public static final class DramaticThreshold extends StaticAbility
	{
		public DramaticThreshold(GameState state)
		{
			super(state, "As long as seven or more cards are in your graveyard, Hunting Grounds has \"Whenever an opponent casts a spell, you may put a creature card from your hand onto the battlefield.\"");

			this.addEffectPart(addAbilityToObject(This.instance(), ASeriesOfUnfortunateEvents.class));

			this.canApply = Both.instance(this.canApply, Threshold.instance());
		}

		public static final class ASeriesOfUnfortunateEvents extends EventTriggeredAbility
		{
			public ASeriesOfUnfortunateEvents(GameState state)
			{
				super(state, "Whenever an opponent casts a spell, you may put a creature card from your hand onto the battlefield.");

				SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
				pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
				pattern.withResult(Intersect.instance(Spells.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance())));
				this.addPattern(pattern);

				EventFactory factory = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a creature card from your hand onto the battlefield.");
				factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
				factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
				factory.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(Cards.instance(), Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(HandOf.instance(You.instance())))));
				this.addEffect(youMay(factory, "You may put a creature card from your hand onto the battlefield."));
			}
		}
	}

	public HuntingGrounds(GameState state)
	{
		super(state);

		this.addAbility(new DramaticThreshold(state));
	}
}
