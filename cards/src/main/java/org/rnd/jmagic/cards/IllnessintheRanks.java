package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Illness in the Ranks")
@Types({Type.ENCHANTMENT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class IllnessintheRanks extends Card
{
	public static final class IllnessintheRanksAbility0 extends StaticAbility
	{
		public IllnessintheRanksAbility0(GameState state)
		{
			super(state, "Creature tokens get -1/-1.");
			SetGenerator creatureTokens = Intersect.instance(CreaturePermanents.instance(), Tokens.instance());
			this.addEffectPart(modifyPowerAndToughness(creatureTokens, -1, -1));
		}
	}

	public IllnessintheRanks(GameState state)
	{
		super(state);

		// Creature tokens get -1/-1.
		this.addAbility(new IllnessintheRanksAbility0(state));
	}
}
