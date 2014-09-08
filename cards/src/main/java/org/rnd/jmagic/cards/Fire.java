package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Fire")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Apocalypse.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Fire extends Card
{
	public Fire(GameState state)
	{
		super(state);

		// Fire deals 2 damage divided as you choose among one or two target
		// creatures and/or players.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "one or two target creatures and/or players");
		target.setNumber(1, 2);
		this.setDivision(Union.instance(numberGenerator(2), Identity.instance("damage")));

		SetGenerator takers = ChosenTargetsFor.instance(Identity.instance(target), This.instance());
		EventFactory damage = new EventFactory(EventType.DISTRIBUTE_DAMAGE, "Fire deals 2 damage divided as you choose among one or two target creatures and/or players.");
		damage.parameters.put(EventType.Parameter.SOURCE, This.instance());
		damage.parameters.put(EventType.Parameter.TAKER, takers);
		this.addEffect(damage);
	}
}
