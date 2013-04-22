package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Will-o'-the-Wisp")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class WillotheWisp extends Card
{
	public static final class WillotheWispAbility1 extends ActivatedAbility
	{
		public WillotheWispAbility1(GameState state)
		{
			super(state, "(B): Regenerate Will-o'-the-Wisp.");
			this.setManaCost(new ManaPool("(B)"));
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Will-o'-the-Wisp."));
		}
	}

	public WillotheWisp(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Flying (This creature can't be blocked except by creatures with
		// flying or reach.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (B): Regenerate Will-o'-the-Wisp. (The next time this creature would
		// be destroyed this turn, it isn't. Instead tap it, remove all damage
		// from it, and remove it from combat.)
		this.addAbility(new WillotheWispAbility1(state));
	}
}
