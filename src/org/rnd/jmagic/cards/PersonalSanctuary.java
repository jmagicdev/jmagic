package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Personal Sanctuary")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class PersonalSanctuary extends Card
{
	public static final class PersonalSanctuaryAbility0 extends StaticAbility
	{
		public PersonalSanctuaryAbility0(GameState state)
		{
			super(state, "During your turn, prevent all damage that would be dealt to you.");

			SetGenerator duringYourTurn = Intersect.instance(CurrentTurn.instance(), TurnOf.instance(You.instance()));
			this.canApply = Both.instance(this.canApply, duringYourTurn);

			ReplacementEffect effect = new org.rnd.jmagic.abilities.PreventAllTo(this.game, You.instance(), "you");
			this.addEffectPart(replacementEffectPart(effect));
		}
	}

	public PersonalSanctuary(GameState state)
	{
		super(state);

		// During your turn, prevent all damage that would be dealt to you.
		this.addAbility(new PersonalSanctuaryAbility0(state));
	}
}
