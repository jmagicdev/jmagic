package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Jace, the Living Guildpact")
@Types({Type.PLANESWALKER})
@SubTypes({SubType.JACE})
@ManaCost("2UU")
@ColorIdentity({Color.BLUE})
public final class JacetheLivingGuildpact extends Card
{
	public static final class JacetheLivingGuildpactAbility0 extends LoyaltyAbility
	{
		public JacetheLivingGuildpactAbility0(GameState state)
		{
			super(state, +1, "Look at the top two cards of your library. Put one of them into your graveyard.");

			SetGenerator topTwo = TopCards.instance(2, LibraryOf.instance(You.instance()));
			this.addEffect(look(You.instance(), topTwo, "Look at the top two cards of your library."));

			EventFactory choose = playerChoose(You.instance(), 1, topTwo, PlayerInterface.ChoiceType.OBJECTS, PlayerInterface.ChooseReason.DISCARD, "Put one of them");
			this.addEffect(choose);
			this.addEffect(putIntoGraveyard(EffectResult.instance(choose), "into your graveyard."));
		}
	}

	public static final class JacetheLivingGuildpactAbility1 extends LoyaltyAbility
	{
		public JacetheLivingGuildpactAbility1(GameState state)
		{
			super(state, -3, "Return another target nonland permanent to its owner's hand.");

			SetGenerator nonlandPermanent = RelativeComplement.instance(Permanents.instance(), Union.instance(ABILITY_SOURCE_OF_THIS, HasType.instance(Type.LAND)));
			SetGenerator target = targetedBy(this.addTarget(nonlandPermanent, "another target nonland permanent"));
			this.addEffect(bounce(target, "Return another target nonland permanent to its owner's hand."));
		}
	}

	public static final class JacetheLivingGuildpactAbility2 extends LoyaltyAbility
	{
		public JacetheLivingGuildpactAbility2(GameState state)
		{
			super(state, -8, "Each player shuffles his or her hand and graveyard into his or her library. You draw seven cards.");

			SetGenerator handAndGraveyard = Union.instance(InZone.instance(HandOf.instance(Players.instance())), InZone.instance(GraveyardOf.instance(Players.instance())));

			EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Each player shuffles his or her hand and graveyard into his or her library,");
			shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
			shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(handAndGraveyard, Players.instance()));
			this.addEffect(shuffle);

			this.addEffect(drawCards(You.instance(), 7, "You draw seven cards."));
		}
	}

	public JacetheLivingGuildpact(GameState state)
	{
		super(state);

		this.setPrintedLoyalty(5);

		// +1: Look at the top two cards of your library. Put one of them into
		// your graveyard.
		this.addAbility(new JacetheLivingGuildpactAbility0(state));

		// -3: Return another target nonland permanent to its owner's hand.
		this.addAbility(new JacetheLivingGuildpactAbility1(state));

		// -8: Each player shuffles his or her hand and graveyard into his or
		// her library. You draw seven cards.
		this.addAbility(new JacetheLivingGuildpactAbility2(state));
	}
}
