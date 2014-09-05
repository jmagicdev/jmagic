package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Nim Deathmantle")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class NimDeathmantle extends Card
{
	public static final class NimDeathmantleAbility0 extends StaticAbility
	{
		public NimDeathmantleAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2, has intimidate, and is a black Zombie.");

			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(equipped, +2, +2));
			this.addEffectPart(addAbilityToObject(equipped, org.rnd.jmagic.abilities.keywords.Intimidate.class));

			ContinuousEffect.Part black = new ContinuousEffect.Part(ContinuousEffectType.SET_COLOR);
			black.parameters.put(ContinuousEffectType.Parameter.OBJECT, equipped);
			black.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(Color.BLACK));
			this.addEffectPart(black);

			ContinuousEffect.Part zombie = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			zombie.parameters.put(ContinuousEffectType.Parameter.OBJECT, equipped);
			zombie.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.ZOMBIE));
			this.addEffectPart(zombie);
		}
	}

	public static final class NimDeathmantleAbility1 extends EventTriggeredAbility
	{
		public NimDeathmantleAbility1(GameState state)
		{
			super(state, "Whenever a nontoken creature is put into your graveyard from the battlefield, you may pay (4). If you do, return that card to the battlefield and attach Nim Deathmantle to it.");
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(You.instance()), Intersect.instance(NonToken.instance(), HasType.instance(Type.CREATURE)), true));

			EventFactory mayPay = new EventFactory(EventType.PLAYER_MAY_PAY_MANA, "You may pay (4).");
			mayPay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			mayPay.parameters.put(EventType.Parameter.COST, Identity.fromCollection(new ManaPool("(4)")));
			mayPay.parameters.put(EventType.Parameter.PLAYER, You.instance());

			SetGenerator thatCard = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));

			EventFactory returnCreature = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return that card to the battlefield");
			returnCreature.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnCreature.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			returnCreature.parameters.put(EventType.Parameter.OBJECT, thatCard);

			EventFactory attach = new EventFactory(EventType.ATTACH, "and attach Nim Deathmantle to it.");
			attach.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			attach.parameters.put(EventType.Parameter.TARGET, NewObjectOf.instance(EffectResult.instance(returnCreature)));

			EventFactory factory = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may pay (4). If you do, return that card to the battlefield and attach Nim Deathmantle to it.");
			factory.parameters.put(EventType.Parameter.IF, Identity.instance(mayPay));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(sequence(returnCreature, attach)));
			this.addEffect(factory);
		}
	}

	public NimDeathmantle(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2, has intimidate, and is a black Zombie.
		this.addAbility(new NimDeathmantleAbility0(state));

		// Whenever a nontoken creature is put into your graveyard from the
		// battlefield, you may pay (4). If you do, return that card to the
		// battlefield and attach Nim Deathmantle to it.
		this.addAbility(new NimDeathmantleAbility1(state));

		// Equip (4)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(4)"));
	}
}
