package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ethereal Usher")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class EtherealUsher extends Card
{
	public static final class NoOnePlaysThisGuyForThisAbility extends ActivatedAbility
	{
		public NoOnePlaysThisGuyForThisAbility(GameState state)
		{
			super(state, "(U), (T): Target creature is unblockable this turn.");
			this.setManaCost(new ManaPool("U"));
			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			ContinuousEffect.Part part = unblockable(targetedBy(target));

			this.addEffect(createFloatingEffect("Target creature is unblockable this turn.", part));
		}
	}

	public EtherealUsher(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// (U), (T): Target creature is unblockable this turn.
		this.addAbility(new NoOnePlaysThisGuyForThisAbility(state));

		// Transmute (1)(U)(U)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Transmute(state, "(1)(U)(U)"));
	}
}
