package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Ancient Silverback")
@Types({Type.CREATURE})
@SubTypes({SubType.APE})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = NinthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UrzasDestiny.class, r = Rarity.RARE)})
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
