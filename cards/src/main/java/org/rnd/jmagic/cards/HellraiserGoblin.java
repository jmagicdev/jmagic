package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Hellraiser Goblin")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.BERSERKER})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class HellraiserGoblin extends Card
{
	public static final class HellraiserGoblinAbility0 extends StaticAbility
	{
		public HellraiserGoblinAbility0(GameState state)
		{
			super(state, "Creatures you control have haste and attack each combat if able.");

			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Haste.class));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
			part.parameters.put(ContinuousEffectType.Parameter.ATTACKING, CREATURES_YOU_CONTROL);
			this.addEffectPart(part);
		}
	}

	public HellraiserGoblin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Creatures you control have haste and attack each combat if able.
		this.addAbility(new HellraiserGoblinAbility0(state));
	}
}
