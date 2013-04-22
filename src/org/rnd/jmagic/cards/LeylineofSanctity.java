package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Leyline of Sanctity")
@Types({Type.ENCHANTMENT})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class LeylineofSanctity extends Card
{
	public static final class HexproofForMe extends StaticAbility
	{
		public HexproofForMe(GameState state)
		{
			super(state, "You have hexproof.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_PLAYER);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(org.rnd.jmagic.abilities.keywords.Hexproof.class));
			this.addEffectPart(part);
		}
	}

	public LeylineofSanctity(GameState state)
	{
		super(state);

		// If Leyline of Sanctity is in your opening hand, you may begin the
		// game with it on the battlefield.
		this.addAbility(new org.rnd.jmagic.abilities.LeylineAbility(state, "Leyline of Sanctity"));

		// You have hexproof.
		this.addAbility(new HexproofForMe(state));
	}
}
