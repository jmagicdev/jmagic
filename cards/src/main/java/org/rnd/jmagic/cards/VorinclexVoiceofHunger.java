package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Vorinclex, Voice of Hunger")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.PRAETOR})
@ManaCost("6GG")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class VorinclexVoiceofHunger extends Card
{
	public static final class VorinclexVoiceofHungerAbility1 extends EventTriggeredAbility
	{
		public VorinclexVoiceofHungerAbility1(GameState state)
		{
			super(state, "Whenever you tap a land for mana, add one mana to your mana pool of any type that land produced.");

			this.addPattern(tappedForMana(You.instance(), new SimpleSetPattern(LandPermanents.instance())));

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator theAbility = EventResult.instance(triggerEvent);

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Add one mana to your mana pool of any type that land produced.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, ManaTypeOf.instance(ManaAddedBy.instance(theAbility)));
			addMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(addMana);
		}
	}

	public static final class VorinclexVoiceofHungerAbility2 extends EventTriggeredAbility
	{
		public VorinclexVoiceofHungerAbility2(GameState state)
		{
			super(state, "Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.");

			this.addPattern(tappedForMana(OpponentsOf.instance(You.instance()), new SimpleSetPattern(LandPermanents.instance())));

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator theAbility = EventResult.instance(triggerEvent);
			SetGenerator theLand = AbilitySource.instance(theAbility);

			EventFactory addMana = new EventFactory(EventType.TAP_HARD, "That land doesn't untap during its controller's next untap step.");
			addMana.parameters.put(EventType.Parameter.CAUSE, This.instance());
			addMana.parameters.put(EventType.Parameter.OBJECT, theLand);
			this.addEffect(addMana);
		}
	}

	public VorinclexVoiceofHunger(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(6);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever you tap a land for mana, add one mana to your mana pool of
		// any type that land produced.
		this.addAbility(new VorinclexVoiceofHungerAbility1(state));

		// Whenever an opponent taps a land for mana, that land doesn't untap
		// during its controller's next untap step.
		this.addAbility(new VorinclexVoiceofHungerAbility2(state));
	}
}
