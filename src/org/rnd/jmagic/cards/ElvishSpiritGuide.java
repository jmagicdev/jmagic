package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Elvish Spirit Guide")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SPIRIT})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.ALLIANCES, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class ElvishSpiritGuide extends Card
{
	public static final class ElvishSpiritGuideAbility0 extends ActivatedAbility
	{
		public ElvishSpiritGuideAbility0(GameState state)
		{
			super(state, "Exile Elvish Spirit Guide from your hand: Add (G) to your mana pool.");
			this.addCost(exile(ABILITY_SOURCE_OF_THIS, "Exile Elvish Spirit Guide"));
			this.activateOnlyFromHand();
			this.addEffect(addManaToYourManaPoolFromAbility("(G)"));
		}
	}

	public ElvishSpiritGuide(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Exile Elvish Spirit Guide from your hand: Add (G) to your mana pool.
		this.addAbility(new ElvishSpiritGuideAbility0(state));
	}
}
