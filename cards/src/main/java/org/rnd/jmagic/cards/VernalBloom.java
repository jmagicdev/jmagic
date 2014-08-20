package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vernal Bloom")
@Types({Type.ENCHANTMENT})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class VernalBloom extends Card
{
	public static final class Bloom extends EventTriggeredAbility
	{
		public Bloom(GameState state)
		{
			super(state, "Whenever a Forest is tapped for mana, its controller adds (G) to his or her mana pool.");

			this.addPattern(tappedForMana(Players.instance(), landPermanents(SubType.FOREST)));

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator manaAbility = EventResult.instance(triggerEvent);

			EventFactory addMana = new EventFactory(EventType.ADD_MANA, "Its controller adds (G) to his or her mana pool.");
			addMana.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			addMana.parameters.put(EventType.Parameter.MANA, Identity.fromCollection(new ManaPool("G")));
			addMana.parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(AbilitySource.instance(manaAbility)));
			this.addEffect(addMana);
		}
	}

	public VernalBloom(GameState state)
	{
		super(state);

		// Whenever a Forest is tapped for mana, its controller adds (G) to his
		// or her mana pool (in addition to the mana the land produces).
		this.addAbility(new Bloom(state));
	}
}
