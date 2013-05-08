package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cleaver Riot")
@Types({Type.SORCERY})
@ManaCost("4R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class CleaverRiot extends Card
{
	public CleaverRiot(GameState state)
	{
		super(state);

		// Creatures you control gain double strike until end of turn. (They
		// deal both first-strike and regular combat damage.)
		SetGenerator creaturesYouControl = Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance()));
		this.addEffect(addAbilityUntilEndOfTurn(creaturesYouControl, org.rnd.jmagic.abilities.keywords.DoubleStrike.class, "Creatures you control gain double strike until end of turn."));
	}
}
