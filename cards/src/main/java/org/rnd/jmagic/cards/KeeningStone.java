package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Keening Stone")
@Types({Type.ARTIFACT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({})
public final class KeeningStone extends Card
{
	public static final class KeeningStoneAbility0 extends ActivatedAbility
	{
		public KeeningStoneAbility0(GameState state)
		{
			super(state, "(5), (T): Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of cards in that player's graveyard.");
			this.setManaCost(new ManaPool("(5)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			EventFactory factory = new EventFactory(EventType.MILL_CARDS, "Target player puts the top X cards of his or her library into his or her graveyard, where X is the number of cards in that player's graveyard.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, target);
			factory.parameters.put(EventType.Parameter.NUMBER, Count.instance(InZone.instance(GraveyardOf.instance(target))));
			this.addEffect(factory);
		}
	}

	public KeeningStone(GameState state)
	{
		super(state);

		// (5), (T): Target player puts the top X cards of his or her library
		// into his or her graveyard, where X is the number of cards in that
		// player's graveyard.
		this.addAbility(new KeeningStoneAbility0(state));
	}
}
