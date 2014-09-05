package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Necrotic Plague")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class NecroticPlague extends Card
{
	public static final class SacrificeThisAtTheBeginningOfYourUpkeep extends EventTriggeredAbility
	{
		public SacrificeThisAtTheBeginningOfYourUpkeep(GameState state)
		{
			super(state, "At the beginning of your upkeep, sacrifice this creature.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(sacrificeThis("this"));
		}
	}

	public static final class NecroticPlagueAbility1 extends StaticAbility
	{
		public NecroticPlagueAbility1(GameState state)
		{
			super(state, "Enchanted creature has \"At the beginning of your upkeep, sacrifice this creature.\"");
			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), SacrificeThisAtTheBeginningOfYourUpkeep.class));
		}
	}

	public static final class NecroticPlagueAbility2 extends EventTriggeredAbility
	{
		public NecroticPlagueAbility2(GameState state)
		{
			super(state, "When enchanted creature dies, its controller chooses target creature one of his or her opponents controls. Return Necrotic Plague from its owner's graveyard to the battlefield attached to that creature.");

			SetGenerator enchantedCreature = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(whenXDies(enchantedCreature));

			SetGenerator itsController = ControllerOf.instance(OldObjectOf.instance(TriggerZoneChange.instance(This.instance())));
			SetGenerator opponentsControl = ControlledBy.instance(OpponentsOf.instance(itsController));
			Intersect creatureOpponentsControl = Intersect.instance(CreaturePermanents.instance(), opponentsControl);
			Target target = new Target(creatureOpponentsControl, itsController, "target creature one of his or her opponents controls");
			this.addTarget(target);

			SetGenerator futureNecroticPlague = FutureSelf.instance(ABILITY_SOURCE_OF_THIS);

			EventFactory returnToBattlefield = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD_ATTACHED_TO, "Return Necrotic Plague from its owner's graveyard to the battlefield attached to that creature");
			returnToBattlefield.parameters.put(EventType.Parameter.CAUSE, This.instance());
			returnToBattlefield.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			returnToBattlefield.parameters.put(EventType.Parameter.OBJECT, futureNecroticPlague);
			returnToBattlefield.parameters.put(EventType.Parameter.TARGET, targetedBy(target));

			SetGenerator itsOwnersGraveyard = GraveyardOf.instance(OwnerOf.instance(futureNecroticPlague));
			SetGenerator inItsOwnersGraveyard = Intersect.instance(futureNecroticPlague, InZone.instance(itsOwnersGraveyard));
			EventFactory ifInGraveyardReturnToBattlefield = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If Necrotic Plague is in its owner's graveyard, return Necrotic Plague from its owner's graveyard to the battlefield attached to that creature.");
			ifInGraveyardReturnToBattlefield.parameters.put(EventType.Parameter.IF, inItsOwnersGraveyard);
			ifInGraveyardReturnToBattlefield.parameters.put(EventType.Parameter.THEN, Identity.instance(returnToBattlefield));
			this.addEffect(ifInGraveyardReturnToBattlefield);
		}
	}

	public NecroticPlague(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has
		// "At the beginning of your upkeep, sacrifice this creature."
		this.addAbility(new NecroticPlagueAbility1(state));

		// When enchanted creature is put into a graveyard, its controller
		// chooses target creature one of his or her opponents controls. Return
		// Necrotic Plague from its owner's graveyard to the battlefield
		// attached to that creature.
		this.addAbility(new NecroticPlagueAbility2(state));
	}
}
