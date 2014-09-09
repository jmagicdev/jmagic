package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Call to Serve")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class CalltoServe extends Card
{
	public static final class CalltoServeAbility1 extends StaticAbility
	{
		public CalltoServeAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+2, has flying, and is an Angel in addition to its other types.");
			SetGenerator who = EnchantedBy.instance(This.instance());
			this.addEffectPart(modifyPowerAndToughness(who, +1, +2));
			this.addEffectPart(addAbilityToObject(who, org.rnd.jmagic.abilities.keywords.Flying.class));
			this.addEffectPart(addType(who, SubType.ANGEL));
		}
	}

	public CalltoServe(GameState state)
	{
		super(state);

		// Enchant nonblack creature
		SetGenerator nonblackCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Final(state, "nonblack creature", nonblackCreatures));

		// Enchanted creature gets +1/+2, has flying, and is an Angel in
		// addition to its other types.
		this.addAbility(new CalltoServeAbility1(state));
	}
}
