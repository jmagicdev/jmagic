package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Call to the Kindred")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class CalltotheKindred extends Card
{
	public static final class CalltotheKindredAbility1 extends EventTriggeredAbility
	{
		public CalltotheKindredAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, you may look at the top five cards of your library. If you do, you may put a creature card that shares a creature type with enchanted creature from among them onto the battlefield, then you put the rest of those cards on the bottom of your library in any order.");
			this.addPattern(atTheBeginningOfYourUpkeep());

			EventFactory look = look(You.instance(), TopCards.instance(5, LibraryOf.instance(You.instance())), "Look at the top five cards of your library");
			EventFactory youMayLook = youMay(look);

			EffectResult looked = EffectResult.instance(look);
			SetGenerator enchantedCreature = EnchantedBy.instance(AbilitySource.instance(This.instance()));
			SetGenerator creatureType = SubTypesOf.instance(enchantedCreature, Type.CREATURE);
			SetGenerator choosable = Intersect.instance(looked, HasType.instance(Type.CREATURE), HasSubType.instance(creatureType));

			EventFactory putOntoBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_CHOICE, "Put a creature card that shares a creature type with enchanted creature from among them onto the battlefield,");
			putOntoBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			putOntoBattlefield.parameters.put(EventType.Parameter.OBJECT, choosable);

			EventFactory putIntoLibrary = new EventFactory(EventType.MOVE_OBJECTS, "then you put the rest of those cards on the bottom of your library in any order.");
			putIntoLibrary.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putIntoLibrary.parameters.put(EventType.Parameter.TO, LibraryOf.instance(You.instance()));
			putIntoLibrary.parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			putIntoLibrary.parameters.put(EventType.Parameter.OBJECT, RelativeComplement.instance(looked, OldObjectOf.instance(EffectResult.instance(putOntoBattlefield))));

			EventFactory ifThen = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may look at the top five cards of your library. If you do, you may put a creature card that shares a creature type with enchanted creature from among them onto the battlefield, then you put the rest of those cards on the bottom of your library in any order.");
			ifThen.parameters.put(EventType.Parameter.IF, Identity.instance(youMayLook));
			ifThen.parameters.put(EventType.Parameter.THEN, Identity.instance(youMay(sequence(putOntoBattlefield, putIntoLibrary))));
			this.addEffect(ifThen);
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
