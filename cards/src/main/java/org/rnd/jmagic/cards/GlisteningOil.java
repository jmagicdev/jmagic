package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Glistening Oil")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class GlisteningOil extends Card
{
	public static final class GlisteningOilAbility1 extends StaticAbility
	{
		public GlisteningOilAbility1(GameState state)
		{
			super(state, "Enchanted creature has infect.");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Infect.class));
		}
	}

	public static final class GlisteningOilAbility2 extends EventTriggeredAbility
	{
		public GlisteningOilAbility2(GameState state)
		{
			super(state, "At the beginning of your upkeep, put a -1/-1 counter on enchanted creature.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), "Put a -1/-1 counter on enchanted creature."));
		}
	}

	public static final class GlisteningOilAbility3 extends EventTriggeredAbility
	{
		public GlisteningOilAbility3(GameState state)
		{
			super(state, "When Glistening Oil is put into a graveyard from the battlefield, return Glistening Oil to its owner's hand.");
			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());

			SetGenerator owner = OwnerOf.instance(ABILITY_SOURCE_OF_THIS);

			EventFactory move = new EventFactory(EventType.MOVE_OBJECTS, "Return Glistening Oil to its owner's hand.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.TO, HandOf.instance(owner));
			move.parameters.put(EventType.Parameter.OBJECT, FutureSelf.instance(ABILITY_SOURCE_OF_THIS));
			this.addEffect(move);
		}
	}

	public GlisteningOil(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has infect.
		this.addAbility(new GlisteningOilAbility1(state));

		// At the beginning of your upkeep, put a -1/-1 counter on enchanted
		// creature.
		this.addAbility(new GlisteningOilAbility2(state));

		// When Glistening Oil is put into a graveyard from the battlefield,
		// return Glistening Oil to its owner's hand.
		this.addAbility(new GlisteningOilAbility3(state));
	}
}
