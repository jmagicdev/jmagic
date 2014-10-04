package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Aegis of the Gods")
@Types({Type.CREATURE, Type.ENCHANTMENT})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class AegisoftheGods extends Card
{
	public static final class AegisoftheGodsAbility0 extends StaticAbility
	{
		public AegisoftheGodsAbility0(GameState state)
		{
			super(state, "You have hexproof.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_ABILITY_TO_PLAYER);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(new org.rnd.jmagic.engine.SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Hexproof.class)));
			this.addEffectPart(part);
		}
	}

	public AegisoftheGods(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// You have hexproof. (You can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new AegisoftheGodsAbility0(state));
	}
}
