package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Silvos, Rogue Elemental")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3GGG")
@Printings({@Printings.Printed(ex = Onslaught.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class SilvosRogueElemental extends Card
{
	public static final class SilvosRogueElementalAbility1 extends ActivatedAbility
	{
		public SilvosRogueElementalAbility1(GameState state)
		{
			super(state, "(G): Regenerate Silvos, Rogue Elemental.");
			this.setManaCost(new ManaPool("(G)"));
			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Silvos, Rogue Elemental."));
		}
	}

	public SilvosRogueElemental(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(5);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// (G): Regenerate Silvos, Rogue Elemental.
		this.addAbility(new SilvosRogueElementalAbility1(state));
	}
}
