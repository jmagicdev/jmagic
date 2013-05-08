package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sword of the Meek")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class SwordoftheMeek extends Card
{
	public static final class TheMeekShallInheritTheSword extends EventTriggeredAbility
	{
		public TheMeekShallInheritTheSword(GameState state)
		{
			super(state, "Whenever a 1/1 creature enters the battlefield under your control, you may return Sword of the Meek from your graveyard to the battlefield, then attach it to that creature.");

			SetGenerator meekPeople = Intersect.instance(CreaturePermanents.instance(), HasPower.instance(1), HasToughness.instance(1));
			this.addPattern(new SimpleZoneChangePattern(null, Battlefield.instance(), meekPeople, false));

			this.triggersFromGraveyard();

			EventFactory returnSword = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_UNDER_OWNER_CONTROL, "Return Sword of the Meek from your graveyard to the battlefield");
			returnSword.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnSword.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			EventFactory mayReturn = youMay(returnSword, "You may return Sword of the Meek from your graveyard to the battlefield");

			SetGenerator movedSword = FutureSelf.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator swordOnBattlefield = Intersect.instance(Permanents.instance(), movedSword);

			SetGenerator thatCreature = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			EventFactory attach = attach(swordOnBattlefield, thatCreature, "Attach Sword of the Meek to that creature");

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may return Sword of the Meek from your graveyard to the battlefield, then attach it to that creature.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(mayReturn));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(attach));
			this.addEffect(effect);
		}
	}

	public SwordoftheMeek(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+2.
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, EquippedBy.instance(This.instance()), "Equipped creature", +1, +2, false));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));

		// Whenever a 1/1 creature enters the battlefield under your control,
		// you may return Sword of the Meek from your graveyard to the
		// battlefield, then attach it to that creature.
		this.addAbility(new TheMeekShallInheritTheSword(state));
	}
}
