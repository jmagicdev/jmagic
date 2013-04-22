package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oak Street Innkeeper")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class OakStreetInnkeeper extends Card
{
	public static final class OakStreetInnkeeperAbility0 extends StaticAbility
	{
		public OakStreetInnkeeperAbility0(GameState state)
		{
			super(state, "As long as it's not your turn, tapped creatures you control have hexproof.");

			this.addEffectPart(addAbilityToObject(Intersect.instance(Tapped.instance(), CREATURES_YOU_CONTROL), org.rnd.jmagic.abilities.keywords.Hexproof.class));

			this.canApply = Both.instance(this.canApply, Not.instance(Intersect.instance(OwnerOf.instance(CurrentTurn.instance()), You.instance())));
		}
	}

	public OakStreetInnkeeper(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// As long as it's not your turn, tapped creatures you control have
		// hexproof.
		this.addAbility(new OakStreetInnkeeperAbility0(state));
	}
}
