package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Goblin Balloon Brigade")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = FourthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
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
