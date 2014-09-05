package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Oxidda Daredevil")
@Types({Type.CREATURE})
@SubTypes({SubType.ARTIFICER, SubType.GOBLIN})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = ScarsOfMirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class OxiddaDaredevil extends Card
{
	public static final class OxiddaDaredevilAbility0 extends ActivatedAbility
	{
		public OxiddaDaredevilAbility0(GameState state)
		{
			super(state, "Sacrifice an artifact: Oxidda Daredevil gains haste until end of turn.");
			this.addCost(sacrifice(You.instance(), 1, ArtifactPermanents.instance(), "Sacrifice an artifact"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Haste.class, "Oxidda Daredevil gains haste until end of turn."));
		}
	}

	public OxiddaDaredevil(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Sacrifice an artifact: Oxidda Daredevil gains haste until end of
		// turn.
		this.addAbility(new OxiddaDaredevilAbility0(state));
	}
}
