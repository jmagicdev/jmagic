package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Electrolyze")
@Types({Type.INSTANT})
@ManaCost("1UR")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Guildpact.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class Electrolyze extends Card
{
	public Electrolyze(GameState state)
	{
		super(state);

		// Electrolyze deals 2 damage divided as you choose among one or two
		// target creatures and/or players.
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "one or two target creatures and/or players");
		target.setNumber(1, 2);
		this.setDivision(Union.instance(numberGenerator(2), Identity.instance("damage")));

		EventType.ParameterMap damageParameters = new EventType.ParameterMap();
		damageParameters.put(EventType.Parameter.SOURCE, This.instance());
		damageParameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
		this.addEffect(new EventFactory(EventType.DISTRIBUTE_DAMAGE, damageParameters, "Electrolyze deals 2 damage divided as you choose among one or two target creatures and/or players."));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 2, "\n\nDraw a card."));
	}
}
