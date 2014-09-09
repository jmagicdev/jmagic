package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sylvan Safekeeper")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class SylvanSafekeeper extends Card
{
	public static final class SylvanSafekeeperAbility0 extends ActivatedAbility
	{
		public SylvanSafekeeperAbility0(GameState state)
		{
			super(state, "Sacrifice a land: Target creature you control gains shroud until end of turn.");
			this.addCost(sacrifice(You.instance(), 1, LandPermanents.instance(), "Sacrifice a land"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Shroud.class, "Target creature you control gains shroud until end of turn."));
		}
	}

	public SylvanSafekeeper(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Sacrifice a land: Target creature you control gains shroud until end
		// of turn. (It can't be the target of spells or abilities.)
		this.addAbility(new SylvanSafekeeperAbility0(state));
	}
}
