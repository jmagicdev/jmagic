package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Blood Moon")
@Types({Type.ENCHANTMENT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = EighthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = TheDark.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class BloodMoon extends Card
{
	// Nonbasic lands are Mountains.
	public static final class MakeMountains extends StaticAbility
	{
		public MakeMountains(GameState state)
		{
			super(state, "Nonbasic lands are Mountains.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			SetGenerator nonbasicLands = RelativeComplement.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, nonbasicLands);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.MOUNTAIN));
			this.addEffectPart(part);
		}
	}

	public BloodMoon(GameState state)
	{
		super(state);

		this.addAbility(new MakeMountains(state));
	}
}
