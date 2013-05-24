package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Guide")
@Types({Type.CREATURE})
@SubTypes({SubType.SCOUT, SubType.GOBLIN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class GoblinGuide extends Card
{
	public static final class Guide extends EventTriggeredAbility
	{
		public Guide(GameState state)
		{
			super(state, "Whenever Goblin Guide attacks, defending player reveals the top card of his or her library. If it's a land card, that player puts it into his or her hand.");
			this.addPattern(whenThisAttacks());

			SetGenerator defendingPlayer = DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator library = LibraryOf.instance(defendingPlayer);
			SetGenerator topCard = TopCards.instance(1, library);

			EventType.ParameterMap revealParameters = new EventType.ParameterMap();
			revealParameters.put(EventType.Parameter.CAUSE, This.instance());
			revealParameters.put(EventType.Parameter.OBJECT, topCard);
			this.addEffect(new EventFactory(EventType.REVEAL, revealParameters, "Defending player reveals the top card of his or her library"));

			SetGenerator itsALand = Intersect.instance(HasType.instance(Type.LAND), topCard);

			EventType.ParameterMap moveParameters = new EventType.ParameterMap();
			moveParameters.put(EventType.Parameter.CAUSE, This.instance());
			moveParameters.put(EventType.Parameter.TO, HandOf.instance(defendingPlayer));
			moveParameters.put(EventType.Parameter.OBJECT, topCard);

			EventType.ParameterMap ifParameters = new EventType.ParameterMap();
			ifParameters.put(EventType.Parameter.IF, itsALand);
			ifParameters.put(EventType.Parameter.THEN, Identity.instance(new EventFactory(EventType.MOVE_OBJECTS, moveParameters, "That player puts it into his or her hand")));
			this.addEffect(new EventFactory(EventType.IF_CONDITION_THEN_ELSE, ifParameters, "If it's a land card, that player puts it into his or her hand"));
		}
	}

	public GoblinGuide(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
		this.addAbility(new Guide(state));
	}
}
