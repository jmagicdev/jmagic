package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Arbiter of the Ideal")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class ArbiteroftheIdeal extends Card
{
	public static final class ArbiteroftheIdealAbility1 extends EventTriggeredAbility
	{
		public ArbiteroftheIdealAbility1(GameState state)
		{
			super(state, "Inspired \u2014 Whenever Arbiter of the Ideal becomes untapped, reveal the top card of your library. If it's an artifact, creature, or land card, you may put it onto the battlefield with a manifestation counter on it. That permanent is an enchantment in addition to its other types.");
			this.addPattern(inspired());

			SetGenerator top = TopCards.instance(1, LibraryOf.instance(You.instance()));
			this.addEffect(reveal(top, "Reveal the top card of your library."));

			SetGenerator isDroppable = Intersect.instance(top, HasType.instance(Type.ARTIFACT, Type.CREATURE, Type.LAND));

			EventFactory drop = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_WITH_COUNTERS, "Put it onto the battlefield with a manifestation counter on it.");
			drop.parameters.put(EventType.Parameter.CAUSE, This.instance());
			drop.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			drop.parameters.put(EventType.Parameter.OBJECT, top);
			drop.parameters.put(EventType.Parameter.COUNTER, Identity.instance(Counter.CounterType.MANIFESTATION));

			this.addEffect(ifThen(isDroppable, youMay(drop), "If it's an artifact, creature, or land card, you may put it onto the battlefield with a manifestation counter on it."));
			SetGenerator dropped = NewObjectOf.instance(EffectResult.instance(drop));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, dropped);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.ENCHANTMENT));
			this.addEffect(createFloatingEffect(Empty.instance(), "That permanent is an enchantment in addition to its other types.", part));
		}
	}

	public ArbiteroftheIdeal(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Arbiter of the Ideal becomes untapped, reveal the top card
		// of your library. If it's an artifact, creature, or land card, you may
		// put it onto the battlefield with a manifestation counter on it. That
		// permanent is an enchantment in addition to its other types.
		this.addAbility(new ArbiteroftheIdealAbility1(state));
	}
}
