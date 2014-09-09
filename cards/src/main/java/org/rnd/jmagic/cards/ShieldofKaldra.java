package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shield of Kaldra")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@ColorIdentity({})
public final class ShieldofKaldra extends Card
{
	public static final class KaldraEquipmentAreIndestructible extends StaticAbility
	{
		public KaldraEquipmentAreIndestructible(GameState state)
		{
			super(state, "Equipment named Sword of Kaldra, Shield of Kaldra, and Helm of Kaldra have indestructible.");

			SetGenerator equipment = HasSubType.instance(SubType.EQUIPMENT);
			SetGenerator sword = Intersect.instance(equipment, HasName.instance("Sword of Kaldra"));
			SetGenerator shield = Intersect.instance(equipment, HasName.instance("Shield of Kaldra"));
			SetGenerator helm = Intersect.instance(equipment, HasName.instance("Helm of Kaldra"));
			this.addEffectPart(addAbilityToObject(Union.instance(sword, shield, helm), org.rnd.jmagic.abilities.keywords.Indestructible.class));
		}
	}

	public static final class EquippedCreatureIsIndestructible extends StaticAbility
	{
		public EquippedCreatureIsIndestructible(GameState state)
		{
			super(state, "Equipped creature has indestructible.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Indestructible.class));
		}
	}

	public ShieldofKaldra(GameState state)
	{
		super(state);

		// Equipment named Sword of Kaldra, Shield of Kaldra, and Helm of Kaldra
		// are indestructible.
		this.addAbility(new KaldraEquipmentAreIndestructible(state));

		// Equipped creature is indestructible.
		this.addAbility(new EquippedCreatureIsIndestructible(state));

		// Equip (4)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(4)"));
	}
}
