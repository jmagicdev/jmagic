package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cranial Archive")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class CranialArchive extends Card
{
	public static final class CranialArchiveAbility0 extends ActivatedAbility
	{
		public CranialArchiveAbility0(GameState state)
		{
			super(state, "(2), Exile Cranial Archive: Target player shuffles his or her graveyard into his or her library. Draw a card.");

			this.setManaCost(new ManaPool("(2)"));
			this.addCost(exileThis("Cranial Archive"));

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

			EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Target player shuffles his or her graveyard into his or her library.");
			shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
			shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(target, InZone.instance(GraveyardOf.instance(target))));
			this.addEffect(shuffle);

			this.addEffect(drawACard());
		}
	}

	public CranialArchive(GameState state)
	{
		super(state);

		// (2), Exile Cranial Archive: Target player shuffles his or her
		// graveyard into his or her library. Draw a card.
		this.addAbility(new CranialArchiveAbility0(state));
	}
}
