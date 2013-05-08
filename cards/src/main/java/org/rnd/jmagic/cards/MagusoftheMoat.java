package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Magus of the Moat")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class MagusoftheMoat extends Card
{
	public static final class MagusoftheMoatAbility0 extends StaticAbility
	{
		public MagusoftheMoatAbility0(GameState state)
		{
			super(state, "Creatures without flying can't attack.");

			SetGenerator nonflyers = RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			SetGenerator nonflyersAttacking = Intersect.instance(nonflyers, Attacking.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(nonflyersAttacking));
			this.addEffectPart(part);
		}
	}

	public MagusoftheMoat(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Creatures without flying can't attack.
		this.addAbility(new MagusoftheMoatAbility0(state));
	}
}
