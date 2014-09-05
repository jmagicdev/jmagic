package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Konda's Banner")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = ChampionsOfKamigawa.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class KondasBanner extends Card
{
	public static final class KondasBannerAbility0 extends StaticAbility
	{
		public KondasBannerAbility0(GameState state)
		{
			super(state, "Konda's Banner can be attached only to a legendary creature.");

			SetGenerator legendaryCreatures = Intersect.instance(HasSuperType.instance(SuperType.LEGENDARY), CreaturePermanents.instance());
			SetGenerator restriction = RelativeComplement.instance(Permanents.instance(), legendaryCreatures);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CANT_BE_ATTACHED_TO);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(new SimpleSetPattern(restriction)));
			this.addEffectPart(part);
		}
	}

	public static final class KondasBannerAbility1 extends StaticAbility
	{
		public KondasBannerAbility1(GameState state)
		{
			super(state, "Creatures that share a color with equipped creature get +1/+1.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());
			SetGenerator sharesColors = HasColor.instance(ColorsOf.instance(equippedCreature));
			SetGenerator creaturesSharing = Intersect.instance(CreaturePermanents.instance(), sharesColors);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MODIFY_POWER_AND_TOUGHNESS);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, creaturesSharing);
			part.parameters.put(ContinuousEffectType.Parameter.POWER, numberGenerator(1));
			part.parameters.put(ContinuousEffectType.Parameter.TOUGHNESS, numberGenerator(1));
			this.addEffectPart(part);
		}
	}

	public static final class KondasBannerAbility2 extends StaticAbility
	{
		public KondasBannerAbility2(GameState state)
		{
			super(state, "Creatures that share a creature type with equipped creature get +1/+1.");

			SetGenerator equippedCreature = EquippedBy.instance(This.instance());
			SetGenerator sharesTypes = HasSubType.instance(SubTypesOf.instance(equippedCreature, Type.CREATURE));
			SetGenerator creaturesSharing = Intersect.instance(CreaturePermanents.instance(), sharesTypes);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MODIFY_POWER_AND_TOUGHNESS);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, creaturesSharing);
			part.parameters.put(ContinuousEffectType.Parameter.POWER, numberGenerator(1));
			part.parameters.put(ContinuousEffectType.Parameter.TOUGHNESS, numberGenerator(1));
			this.addEffectPart(part);
		}
	}

	public KondasBanner(GameState state)
	{
		super(state);

		// Konda's Banner can be attached only to a legendary creature.
		this.addAbility(new KondasBannerAbility0(state));

		// Creatures that share a color with equipped creature get +1/+1.
		this.addAbility(new KondasBannerAbility1(state));

		// Creatures that share a creature type with equipped creature get
		// +1/+1.
		this.addAbility(new KondasBannerAbility2(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
