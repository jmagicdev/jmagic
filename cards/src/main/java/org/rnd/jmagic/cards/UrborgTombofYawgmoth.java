package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Urborg, Tomb of Yawgmoth")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = PlanarChaos.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class UrborgTombofYawgmoth extends Card
{
	// Each land is a Swamp in addition to its other land types.
	public static final class MakeSwamps extends StaticAbility
	{
		public MakeSwamps(GameState state)
		{
			super(state, "Each land is a Swamp in addition to its other land types.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, LandPermanents.instance());
			part.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SubType.SWAMP));
			this.addEffectPart(part);
		}
	}

	public UrborgTombofYawgmoth(GameState state)
	{
		super(state);

		this.addAbility(new MakeSwamps(state));
	}
}
