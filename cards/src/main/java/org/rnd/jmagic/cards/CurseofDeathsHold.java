package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Curse of Death's Hold")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.CURSE, SubType.AURA})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class CurseofDeathsHold extends Card
{
	public static final class CurseofDeathsHoldAbility1 extends StaticAbility
	{
		public CurseofDeathsHoldAbility1(GameState state)
		{
			super(state, "Creatures enchanted player controls get -1/-1.");

			SetGenerator enchantedPlayer = EnchantedBy.instance(This.instance());
			SetGenerator affected = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(enchantedPlayer));
			this.addEffectPart(modifyPowerAndToughness(affected, -1, -1));
		}
	}

	public CurseofDeathsHold(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// Creatures enchanted player controls get -1/-1.
		this.addAbility(new CurseofDeathsHoldAbility1(state));
	}
}
