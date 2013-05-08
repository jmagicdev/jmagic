package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hammer of Ruin")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class HammerofRuin extends Card
{
	public static final class HammerofRuinAbility1 extends EventTriggeredAbility
	{
		public HammerofRuinAbility1(GameState state)
		{
			super(state, "Whenever equipped creature deals combat damage to a player, you may destroy target Equipment that player controls.");
			this.addPattern(whenDealsCombatDamageToAPlayer(EquippedBy.instance(ABILITY_SOURCE_OF_THIS)));

			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			SetGenerator thatPlayersEquipment = Intersect.instance(HasSubType.instance(SubType.EQUIPMENT), ControlledBy.instance(thatPlayer));
			SetGenerator target = targetedBy(this.addTarget(thatPlayersEquipment, "target Equipment that player controls"));
			EventFactory destroy = destroy(target, "Destroy target Equipment that player controls");
			this.addEffect(youMay(destroy, "You may destroy target Equipment that player controls."));
		}
	}

	public HammerofRuin(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+0.
		SetGenerator enchantedCreature = EquippedBy.instance(This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, enchantedCreature, "Equipped creature", +2, +0, false));

		// Whenever equipped creature deals combat damage to a player, you may
		// destroy target Equipment that player controls.
		this.addAbility(new HammerofRuinAbility1(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
