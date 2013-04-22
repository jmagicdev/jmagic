package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Angelic Armaments")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class AngelicArmaments extends Card
{
	public static final class AngelicArmamentsAbility0 extends StaticAbility
	{
		public AngelicArmamentsAbility0(GameState state)
		{
			super(state, "Equipped creature gets +2/+2, has flying, and is a white Angel in addition to its other colors and types.");

			SetGenerator equipped = EquippedBy.instance(This.instance());

			this.addEffectPart(modifyPowerAndToughness(equipped, +2, +2));
			this.addEffectPart(addAbilityToObject(equipped, org.rnd.jmagic.abilities.keywords.Flying.class));

			ContinuousEffect.Part color = new ContinuousEffect.Part(ContinuousEffectType.ADD_COLOR);
			color.parameters.put(ContinuousEffectType.Parameter.OBJECT, equipped);
			color.parameters.put(ContinuousEffectType.Parameter.COLOR, Identity.instance(Color.WHITE));
			this.addEffectPart(color);

			ContinuousEffect.Part type = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			type.parameters.put(ContinuousEffectType.Parameter.OBJECT, equipped);
			type.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.ANGEL));
			this.addEffectPart(type);
		}
	}

	public AngelicArmaments(GameState state)
	{
		super(state);

		// Equipped creature gets +2/+2, has flying, and is a white Angel in
		// addition to its other colors and types.
		this.addAbility(new AngelicArmamentsAbility0(state));

		// Equip (4)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(4)"));
	}
}
