package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wall of Essence")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class WallofEssence extends Card
{
	public static final class WallofEssenceAbility1 extends EventTriggeredAbility
	{
		public WallofEssenceAbility1(GameState state)
		{
			super(state, "Whenever Wall of Essence is dealt combat damage, you gain that much life.");
			this.addPattern(whenIsDealtCombatDamage(ABILITY_SOURCE_OF_THIS));

			SetGenerator amount = Count.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(gainLife(You.instance(), amount, "You gain that much life."));
		}
	}

	public WallofEssence(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(4);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Whenever Wall of Essence is dealt combat damage, you gain that much
		// life.
		this.addAbility(new WallofEssenceAbility1(state));
	}
}
