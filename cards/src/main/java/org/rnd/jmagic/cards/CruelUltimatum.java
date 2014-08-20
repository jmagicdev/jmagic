package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cruel Ultimatum")
@Types({Type.SORCERY})
@ManaCost("UUBBBRR")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK, Color.RED})
public final class CruelUltimatum extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("CruelUltimatum", "Return a creature card from your graveyard to your hand.", true);

	/**
	 * @eparam CAUSE: Cruel Ultimatum
	 * @eparam PLAYER: controller of CAUSE
	 * @eparam CHOICE: creature cards in PLAYER's graveyard
	 */
	public static final EventType CRUEL_RETURN = new EventType("CRUEL_RETURN")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set ultimatum = parameters.get(Parameter.CAUSE);
			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);
			java.util.Set<GameObject> cards = parameters.get(Parameter.CHOICE).getAll(GameObject.class);

			java.util.List<GameObject> choice = you.sanitizeAndChoose(game.actualState, 1, cards, PlayerInterface.ChoiceType.OBJECTS, REASON);

			Zone hand = you.getHand(game.actualState);
			java.util.Map<Parameter, Set> handParameters = new java.util.HashMap<Parameter, Set>();
			handParameters.put(Parameter.CAUSE, ultimatum);
			handParameters.put(Parameter.OBJECT, Set.fromCollection(choice));
			handParameters.put(Parameter.TO, new Set(hand));
			createEvent(game, "Return " + choice + " to " + hand + ".", EventType.MOVE_OBJECTS, handParameters).perform(event, false);

			event.setResult(Empty.set);
			return true;
		}
	};

	public CruelUltimatum(GameState state)
	{
		super(state);

		// Target opponent
		Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");

		// sacrifices a creature,
		this.addEffect(sacrifice(targetedBy(target), 1, CreaturePermanents.instance(), "Target opponent sacrifices a creature,"));

		// discards three cards,
		this.addEffect(discardCards(targetedBy(target), 3, "discards three cards,"));

		// then loses 5 life.
		this.addEffect(loseLife(targetedBy(target), 5, "then loses 5 life."));

		// You return a creature card from your graveyard to your hand,
		SetGenerator yourYard = GraveyardOf.instance(You.instance());
		SetGenerator inYourYard = InZone.instance(yourYard);
		SetGenerator creaturesInYourYard = Intersect.instance(HasType.instance(Type.CREATURE), inYourYard);

		EventFactory returnToHand = new EventFactory(CRUEL_RETURN, "You return a creature card from your graveyard to your hand,");
		returnToHand.parameters.put(EventType.Parameter.CAUSE, This.instance());
		returnToHand.parameters.put(EventType.Parameter.PLAYER, You.instance());
		returnToHand.parameters.put(EventType.Parameter.CHOICE, creaturesInYourYard);
		this.addEffect(returnToHand);

		// draw three cards,
		this.addEffect(drawCards(You.instance(), 3, "draw three cards,"));

		// then gain 5 life.
		this.addEffect(gainLife(You.instance(), 5, "then gain 5 life."));
	}
}
