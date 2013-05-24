package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Ancient Silverback")
@Types({Type.CREATURE})
@SubTypes({SubType.APE})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_DESTINY, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class AncientSilverback extends Card
{
	public static final class AncientSilverbackAbility0 extends ActivatedAbility
	{
		public AncientSilverbackAbility0(GameState state)
		{
			super(state, "(G): Regenerate Ancient Silverback.");
			this.setManaCost(new ManaPool("(G)"));
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Ancient Silverback."));
		}
	}

	public AncientSilverback(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// (G): Regenerate Ancient Silverback. (The next time this creature
		// would be destroyed this turn, it isn't. Instead tap it, remove all
		// damage from it, and remove it from combat.)
		this.addAbility(new AncientSilverbackAbility0(state));
	}
}
