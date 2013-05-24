package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Magus of the Moon")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class MagusoftheMoon extends Card
{
	public static final class MagusoftheMoonAbility0 extends StaticAbility
	{
		public MagusoftheMoonAbility0(GameState state)
		{
			super(state, "Nonbasic lands are Mountains.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_TYPES);
			SetGenerator nonbasicLands = RelativeComplement.instance(LandPermanents.instance(), HasSuperType.instance(SuperType.BASIC));
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, nonbasicLands);
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.MOUNTAIN));
			this.addEffectPart(part);
		}
	}

	public MagusoftheMoon(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Nonbasic lands are Mountains.
		this.addAbility(new MagusoftheMoonAbility0(state));
	}
}
