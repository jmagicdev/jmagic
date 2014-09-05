package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Comet Storm")
@Types({Type.INSTANT})
@ManaCost("XRR")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.MYTHIC), @Printings.Printed(ex = Worldwake.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class CometStorm extends Card
{
	public CometStorm(GameState state)
	{
		super(state);

		// Multikicker (1)
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(1)");
		this.addAbility(ability);

		// Choose target creature or player, then choose another target creature
		// or player for each time Comet Storm was kicked.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "any number of target creatures and/or players");
		SetGenerator timesKicked = ThisSpellWasKicked.instance(ability.costCollections[0]);
		target.setSingleNumber(Sum.instance(Union.instance(numberGenerator(1), timesKicked)));

		// Comet Storm deals X damage to each of them.
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), targetedBy(target), "Choose target creature or player, then choose another target creature or player for each time Comet Storm was kicked. Comet Storm deals X damage to each of them."));
	}
}
