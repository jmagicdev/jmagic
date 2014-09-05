package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sadistic Sacrament")
@Types({Type.SORCERY})
@ManaCost("BBB")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class SadisticSacrament extends Card
{
	public SadisticSacrament(GameState state)
	{
		super(state);

		// Kicker (7) (You may pay an additional (7) as you cast this spell.)
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "7");
		this.addAbility(ability);
		CostCollection kickerCost = ability.costCollections[0];

		// Search target player's library for up to three cards, exile them,
		// then that player shuffles his or her library. If Sadistic Sacrament
		// was kicked, instead search that player's library for up to fifteen
		// cards, exile them, then that player shuffles his or her library.
		Target target = this.addTarget(Players.instance(), "target player");

		EventFactory search = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search target player's library for up to three cards, exile them, then that player shuffles his or her library. If Sadistic Sacrament was kicked, instead search that player's library for up to fifteen cards, exile them, then that player shuffles his or her library.");
		search.parameters.put(EventType.Parameter.CAUSE, This.instance());
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		search.parameters.put(EventType.Parameter.TARGET, targetedBy(target));
		search.parameters.put(EventType.Parameter.NUMBER, IfThenElse.instance(ThisSpellWasKicked.instance(kickerCost), numberGenerator(15), numberGenerator(3)));
		search.parameters.put(EventType.Parameter.TO, ExileZone.instance());
		this.addEffect(search);
	}
}
