package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Deathcult Rogue")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("1(U/B)(U/B)")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class DeathcultRogue extends Card
{
	public static final class DeathcultRogueAbility0 extends StaticAbility
	{
		public DeathcultRogueAbility0(GameState state)
		{
			super(state, "Deathcult Rogue can't be blocked except by Rogues.");

			SetGenerator creatures = HasType.instance(Type.CREATURE);
			SetGenerator cantBlockThis = RelativeComplement.instance(creatures, HasSubType.instance(SubType.ROGUE));
			SetGenerator illegalBlock = Intersect.instance(Blocking.instance(This.instance()), cantBlockThis);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(illegalBlock));
			this.addEffectPart(part);
		}
	}

	public DeathcultRogue(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Deathcult Rogue can't be blocked except by Rogues.
		this.addAbility(new DeathcultRogueAbility0(state));
	}
}
