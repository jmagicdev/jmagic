package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Moat")
@Types({Type.ENCHANTMENT})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Legends.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Moat extends Card
{
	public static final class MoatAbility0 extends StaticAbility
	{
		public MoatAbility0(GameState state)
		{
			super(state, "Creatures without flying can't attack.");

			SetGenerator nonflyers = RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			SetGenerator nonflyersAttacking = Intersect.instance(nonflyers, Attacking.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(nonflyersAttacking));
			this.addEffectPart(part);
		}
	}

	public Moat(GameState state)
	{
		super(state);

		// Creatures without flying can't attack.
		this.addAbility(new MoatAbility0(state));
	}
}
