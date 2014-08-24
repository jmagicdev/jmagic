package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Warchief")
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN, SubType.WARRIOR})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.SCOURGE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class GoblinWarchief extends Card
{
	public static final class GoblinWarchiefAbility1 extends StaticAbility
	{
		public GoblinWarchiefAbility1(GameState state)
		{
			super(state, "Goblin creatures you control have haste.");

			this.addEffectPart(addAbilityToObject(Intersect.instance(HasSubType.instance(SubType.GOBLIN), CREATURES_YOU_CONTROL), org.rnd.jmagic.abilities.keywords.Haste.class));
		}
	}

	public GoblinWarchief(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Goblin spells you cast cost (1) less to cast.
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, HasSubType.instance(SubType.GOBLIN), "(1)", "Goblin spells you cast cost (1) less to cast."));

		// Goblin creatures you control have haste.
		this.addAbility(new GoblinWarchiefAbility1(state));
	}
}
