package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Dreamborn Muse")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.LEGIONS, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class DreambornMuse extends Card
{
	public static final class MuseMill extends EventTriggeredAbility
	{
		public MuseMill(GameState state)
		{
			super(state, "At the beginning of each player's upkeep, that player puts the top X cards of his or her library into his or her graveyard, where X is the number of cards in his or her hand.");

			this.addPattern(atTheBeginningOfEachPlayersUpkeep());

			SetGenerator thatPlayer = OwnerOf.instance(EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.STEP));

			EventFactory factory = new EventFactory(EventType.MILL_CARDS, "That player puts the top X cards of his or her library into his or her graveyard, where X is the number of cards in his or her hand");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, Count.instance(InZone.instance(HandOf.instance(thatPlayer))));
			factory.parameters.put(EventType.Parameter.PLAYER, thatPlayer);
			this.addEffect(factory);
		}
	}

	public DreambornMuse(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new MuseMill(state));
	}
}
