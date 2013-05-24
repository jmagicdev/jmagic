package org.rnd.jmagic.cards;

import org.rnd.jmagic.abilities.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Leyline of Singularity")
@Types({Type.ENCHANTMENT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.GUILDPACT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class LeylineofSingularity extends Card
{
	public static final class SingularStuff extends StaticAbility
	{
		public SingularStuff(GameState state)
		{
			super(state, "All nonland permanents are legendary.");

			SetGenerator nonlandPermanents = RelativeComplement.instance(Permanents.instance(), LandPermanents.instance());

			ContinuousEffect.Part typesPart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
			typesPart.parameters.put(ContinuousEffectType.Parameter.OBJECT, nonlandPermanents);
			typesPart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(SuperType.LEGENDARY));
			this.addEffectPart(typesPart);
		}
	}

	public LeylineofSingularity(GameState state)
	{
		super(state);

		// If Leyline of Singularity is in your opening hand, you may begin the
		// game with it on the battlefield.
		this.addAbility(new LeylineAbility(state, this.getName()));

		// All nonland permanents are legendary.
		this.addAbility(new SingularStuff(state));
	}
}
