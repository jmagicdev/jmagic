package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Call to the Kindred")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3U")
@ColorIdentity({Color.BLUE})
public final class CalltotheKindred extends Card
{
	public static final class CalltotheKindredAbility1 extends EventTriggeredAbility
	{
		public CalltotheKindredAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may look at the top five cards of your library. If you do, you may put a creature card that shares a creature type with enchanted creature from among them onto the battlefield, then you put the rest of those cards on the bottom of your library in any order.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());
			SetGenerator creatureType = SubTypesOf.instance(enchantedCreature, Type.CREATURE);
			SetGenerator choosable = Intersect.instance(HasType.instance(Type.CREATURE), HasSubType.instance(creatureType));

			this.addEffect(Sifter.start().may().look(5).drop(1, choosable).dumpToBottom().getEventFactory("You may look at the top five cards of your library. If you do, you may put a creature card that shares a creature type with enchanted creature from among them onto the battlefield, then you put the rest of those cards on the bottom of your library in any order."));
		}
	}

	public CalltotheKindred(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// At the beginning of your upkeep, you may look at the top five cards
		// of your library. If you do, you may put a creature card that shares a
		// creature type with enchanted creature from among them onto the
		// battlefield, then you put the rest of those cards on the bottom of
		// your library in any order.
		this.addAbility(new CalltotheKindredAbility1(state));
	}
}
