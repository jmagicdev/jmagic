package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Wildblood Pack")
@Types({Type.CREATURE})
@SubTypes({SubType.WEREWOLF})
@Printings({@Printings.Printed(ex = Innistrad.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class WildbloodPack extends AlternateCard
{
	public static final class WildbloodPackAbility1 extends StaticAbility
	{
		public WildbloodPackAbility1(GameState state)
		{
			super(state, "Attacking creatures you control get +3/+0.");

			this.addEffectPart(modifyPowerAndToughness(Intersect.instance(Attacking.instance(), CREATURES_YOU_CONTROL), +3, +0));
		}
	}

	public WildbloodPack(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.setColorIndicator(Color.RED);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Attacking creatures you control get +3/+0.
		this.addAbility(new WildbloodPackAbility1(state));

		// At the beginning of each upkeep, if a player cast two or more spells
		// last turn, transform Wildblood Pack.
		this.addAbility(new org.rnd.jmagic.abilities.Werewolves.BecomeHuman(state, this.getName()));
	}
}
