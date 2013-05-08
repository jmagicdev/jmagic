package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rhox")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST, SubType.RHINO})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NEMESIS, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Rhox extends Card
{
	public static final class FauxUnblockable extends StaticAbility
	{
		public FauxUnblockable(GameState state)
		{
			super(state, "You may have Rhox assign its combat damage as though it weren't blocked.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.DEAL_DAMAGE_AS_THOUGH_UNBLOCKED);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}

	public Rhox(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new FauxUnblockable(state));
		this.addAbility(new org.rnd.jmagic.abilities.Regenerate.Final(state, "(2)(G)", this.getName()));
	}
}
