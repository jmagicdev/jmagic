package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Goblin Balloon Brigade")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.BETA, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class GoblinBalloonBrigade extends Card
{
	public static final class GoblinBalloonBrigadeAbility0 extends ActivatedAbility
	{
		public GoblinBalloonBrigadeAbility0(GameState state)
		{
			super(state, "(R): Goblin Balloon Brigade gains flying until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "Goblin Balloon Brigade gains flying until end of turn."));
		}
	}

	public GoblinBalloonBrigade(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (R): Goblin Balloon Brigade gains flying until end of turn.
		this.addAbility(new GoblinBalloonBrigadeAbility0(state));
	}
}
