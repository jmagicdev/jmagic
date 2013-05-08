package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dementia Bat")
@Types({Type.CREATURE})
@SubTypes({SubType.BAT})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class DementiaBat extends Card
{
	public static final class DementiaBatAbility1 extends ActivatedAbility
	{
		public DementiaBatAbility1(GameState state)
		{
			super(state, "(4)(B), Sacrifice Dementia Bat: Target player discards two cards.");
			this.setManaCost(new ManaPool("(4)(B)"));
			this.addCost(sacrificeThis("Dementia Bat"));
			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(discardCards(target, 2, "Target player discards two cards."));
		}
	}

	public DementiaBat(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (4)(B), Sacrifice Dementia Bat: Target player discards two cards.
		this.addAbility(new DementiaBatAbility1(state));
	}
}
