package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Dictate of Karametra")
@Types({Type.ENCHANTMENT})
@ManaCost("3GG")
@ColorIdentity({Color.GREEN})
public final class DictateofKarametra extends Card
{
	public static final class DictateofKarametraAbility1 extends EventTriggeredAbility
	{
		public DictateofKarametraAbility1(GameState state)
		{
			super(state, "Whenever a player taps a land for mana, that player adds one mana to his or her mana pool of any type that land produced.");

			this.addPattern(tappedForMana(Players.instance(), new SimpleSetPattern(LandPermanents.instance())));

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator theAbility = EventResult.instance(triggerEvent);
			SetGenerator player = EventParameter.instance(triggerEvent, EventType.Parameter.PLAYER);

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "That player adds one mana to his or her mana pool of any type that land produced.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, ManaTypeOf.instance(ManaAddedBy.instance(theAbility)));
			addMana.parameters.put(EventType.Parameter.PLAYER, player);
			this.addEffect(addMana);
		}
	}

	public DictateofKarametra(GameState state)
	{
		super(state);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Whenever a player taps a land for mana, that player adds one mana to
		// his or her mana pool of any type that land produced.
		this.addAbility(new DictateofKarametraAbility1(state));
	}
}
