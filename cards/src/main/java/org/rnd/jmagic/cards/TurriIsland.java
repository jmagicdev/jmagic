package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Turri Island")
@Types({Type.PLANE})
@SubTypes({SubType.IR})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class TurriIsland extends Card
{
	public static final class CheepCheepChicken extends StaticAbility
	{
		public CheepCheepChicken(GameState state)
		{
			super(state, "Creature spells cost (2) less to cast.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MANA_COST_REDUCTION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(Spells.instance(), HasType.instance(Type.CREATURE)));
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.fromCollection(new ManaPool("2")));
			this.addEffectPart(part);

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class ChaosBeastHunt extends EventTriggeredAbility
	{
		public ChaosBeastHunt(GameState state)
		{
			super(state, "Whenever you roll (C), reveal the top three cards of your library. Put all creature cards revealed this way into your hand and the rest into your graveyard.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			// Reveal the top three cards of your library.
			SetGenerator topThree = TopCards.instance(3, LibraryOf.instance(You.instance()));
			EventFactory reveal = new EventFactory(EventType.REVEAL, "Reveal the top three cards of your library.");
			reveal.parameters.put(EventType.Parameter.CAUSE, This.instance());
			reveal.parameters.put(EventType.Parameter.OBJECT, topThree);
			this.addEffect(reveal);

			// Put all creature cards revealed this way into your hand and the
			// rest into your graveyard.
			EventFactory moveCards = new EventFactory(BeastHunt.BEAST_HUNT_EVENT, "Put all creature cards revealed this way into your hand and the rest into your graveyard.");
			moveCards.parameters.put(EventType.Parameter.CAUSE, This.instance());
			moveCards.parameters.put(EventType.Parameter.OBJECT, topThree);
			moveCards.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(moveCards);

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public TurriIsland(GameState state)
	{
		super(state);

		this.addAbility(new CheepCheepChicken(state));

		this.addAbility(new ChaosBeastHunt(state));
	}
}
