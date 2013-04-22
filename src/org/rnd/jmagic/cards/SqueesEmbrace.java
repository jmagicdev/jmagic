package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Squee's Embrace")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("RW")
@Printings({@Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class SqueesEmbrace extends Card
{
	public static final class NoDying extends EventTriggeredAbility
	{
		public NoDying(GameState state)
		{
			super(state, "When enchanted creature dies, return that card to its owner's hand.");
			this.addPattern(whenXDies(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS)));

			SetGenerator thatCard = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			EventFactory returnToHand = new EventFactory(EventType.MOVE_OBJECTS, "Return that card to its owner's hand.");
			returnToHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnToHand.parameters.put(EventType.Parameter.TO, HandOf.instance(OwnerOf.instance(thatCard)));
			returnToHand.parameters.put(EventType.Parameter.OBJECT, thatCard);
			this.addEffect(returnToHand);
		}
	}

	public SqueesEmbrace(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +2/+2.
		SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, enchantedCreature, "Enchanted creature", +2, +2, false));

		// When enchanted creature is put into a graveyard, return that card to
		// its owner's hand.
		this.addAbility(new NoDying(state));
	}
}
